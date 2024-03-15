/*  Student information for assignment:
 *
 *  On OUR honor, Arnav Bhasin and Aaron Zhao, this programming assignment is OUR own work
 *  and WE have not provided this code to any other student.
 *
 *  Number of slip days used: 0
 *
 *  Student 1 (Student whose Canvas account is being used)
 *  UTEID: ab78845
 *  email address: bhasin.arnav07@gmail.com
 *  Grader name: Brad
 *  Section number: 52665
 *
 *  Student 2
 *  UTEID: yz28676
 *  email address: aaron.zhao.a.z@gmail.com
 */

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class SimpleHuffProcessor implements IHuffProcessor {
    // instance variable to associate this processor with a specific GUI/model
    private IHuffViewer myViewer;

    // instance variable to store the size of original file in bits
    private int originalBitSize;

    // instance variable to store the size of compressed file in bits
    private int newBitSize;

    // instance variable to store the number of bits saved by compressing the file
    private int numBitsSaved;

    // instance variable to store the frequencies of every possible value
    private int[] frequencyMap;

    // instance variable to store the HuffmanTree with all the values and codes
    private HuffmanTree huffmanTree;

    // instance variable HashMap to store all the values and their codes
    private HashMap<Integer, String> huffmanCodes;

    // instance variable to store the header format constant type for file
    private int headerFormat;

    /**
     * Preprocess data so that compression is possible ---
     * count characters/create tree/store state so that1
     * a subsequent call to compress will work. The InputStream
     * is <em>not</em> a BitInputStream, so wrap it int one as needed.
     *
     * @param in           is the stream which could be subsequently compressed
     * @param headerFormat a constant from IHuffProcessor that determines what kind of
     *                     header to use, standard count format, standard tree format, or
     *                     possibly some format added in the future.
     * @return number of bits saved by compression or some other measure
     * Note, to determine the number of
     * bits saved, the number of bits written includes
     * ALL bits that will be written including the4
     * magic number, the header format number, the header to
     * reproduce the tree, AND the actual data.
     * @throws IOException if an error occurs while reading from the input file.
     */
    // method to calculate original and compressed size of file using frequency map and Huffman tree
    public int preprocessCompress(InputStream in, int headerFormat) throws IOException {
        originalBitSize = 0;
        this.headerFormat = headerFormat;
        BitInputStream bitsIn = new BitInputStream(in);
        // creates frequency map of all 256 possible integer values that can be read
        frequencyMap = new int[ALPH_SIZE];
        int inbits = bitsIn.readBits(BITS_PER_WORD);
        while (inbits != -1) {
            // updates frequency map based on value read by inputStream
            frequencyMap[inbits]++;
            originalBitSize += BITS_PER_WORD;
            inbits = bitsIn.readBits(BITS_PER_WORD);
        }
        bitsIn.close();
        huffmanTree = new HuffmanTree(frequencyMap);
        // generates huffman codes from finished huffman tree
        huffmanCodes = generateCodes(huffmanTree);
        int headerSize;
        if (headerFormat == STORE_COUNTS) {
            headerSize = BITS_PER_INT * (ALPH_SIZE + 2);
        } else {
            headerSize = (BITS_PER_INT * 2) + (BITS_PER_INT) +
                    (huffmanTree.getNumLeaves() * (BITS_PER_WORD + 1)) + huffmanTree.size();
        }
        // takes each possible value in file, gets code for value, gets length of code
        // number of bits code takes up), multiplies length by how many times value appears
        // in original file, and adds it to total compressed data size
        int compressedDataSize = compressedDataSize();
        // adds length of PSEUDO_EOF code which only appears once
        compressedDataSize += huffmanCodes.get(PSEUDO_EOF).length();
        newBitSize = headerSize + compressedDataSize;
        numBitsSaved = originalBitSize - newBitSize;
        return numBitsSaved;
    }

    // helper method to calculate the size of compressed data in bits
    private int compressedDataSize() {
        int compressedDataSize = 0;
        for (Map.Entry<Integer, String> entry : huffmanCodes.entrySet()) {
            int value = entry.getKey();
            if (value != PSEUDO_EOF) {
                String code = entry.getValue();
                int frequency = frequencyMap[value];
                compressedDataSize += code.length() * frequency;
            }
        }
        return compressedDataSize;
    }

    // helper method to generate and store all the codes for values of Huffman tree in hashmap
    private HashMap<Integer, String> generateCodes(HuffmanTree tree) {
        HashMap<Integer, String> huffmanCodes = new HashMap<>();
        codesHelper(tree.getRoot(), "", huffmanCodes);
        return huffmanCodes;
    }

    // recursive helper method to help generate Huffman codes
    private void codesHelper(TreeNode node, String code,
                             HashMap<Integer, String> huffmanCodes) {
        if (node != null) {
            if (node.isLeaf()) {
                huffmanCodes.put(node.getValue(), code);
            }
            codesHelper(node.getLeft(), code + "0", huffmanCodes);
            codesHelper(node.getRight(), code + "1", huffmanCodes);
        }
    }

    /**
     * Compresses input to output, where the same InputStream has
     * previously been pre-processed via <code>preprocessCompress</code>
     * storing state used by this call.
     * <br> pre: <code>preprocessCompress</code> must be called before this method
     *
     * @param in    is the stream being compressed (NOT a BitInputStream)
     * @param out   is bound to a file/stream to which bits are written
     *              for the compressed file (not a BitOutputStream)
     * @param force if this is true create the output file even if it is larger than the input file.
     *              If this is false do not create the output file if it is larger than the input file.
     * @return the number of bits written.
     * @throws IOException if an error occurs while reading from the input file or
     *                     writing to the output file.uii
     */
    // method to compress file using frequency map and Huffman tree
    public int compress(InputStream in, OutputStream out, boolean force) throws IOException {
        // if force is true, always compress, if false, only compress if numBitsSaved is positive
        if (!force && numBitsSaved < 0) {
            throw new IOException("Compression increases size of file!");
        }
        BitInputStream bitsIn = new BitInputStream(in);
        BitOutputStream bitsOut = new BitOutputStream(out);
        bitsOut.writeBits(BITS_PER_INT, MAGIC_NUMBER);
        // calls helper method to write out bits for header depending on if file is in STF or SCF
        headerHelper(bitsOut);
        // compressing the actual data
        int inbits = bitsIn.readBits(BITS_PER_WORD);
        while (inbits != -1) {
            // get value from next 8 bits, get code for it, write out each code 1 bit at a time
            for (int i = 0; i < huffmanCodes.get(inbits).length(); i++) {
                bitsOut.writeBits(1, huffmanCodes.get(inbits).charAt(i));
            }
            inbits = bitsIn.readBits(BITS_PER_WORD);
        }
        bitsIn.close();
        // write out PSEUDO_EOF code 1 bit at a time
        String EOFCode = huffmanCodes.get(PSEUDO_EOF);
        for (int i = 0; i < EOFCode.length(); i++) {
            bitsOut.writeBits(1, EOFCode.charAt(i));
        }
        // to make sure all bits are written, we explicitly flush the last bits by closing stream
        bitsOut.close();
        System.out.println(newBitSize);
        return newBitSize;
    }

    // helper method to write out bits for header depending on if file is in STF or SCF
    private void headerHelper(BitOutputStream bitsOut) {
        if (headerFormat == STORE_COUNTS) {
            // write out bits for header format constant
            bitsOut.writeBits(BITS_PER_INT, STORE_COUNTS);
            // write out bits for each value's frequency from frequency counts array
            for (int i = 0; i < ALPH_SIZE; i++) {
                bitsOut.writeBits(BITS_PER_INT, frequencyMap[i]);
            }
        }
        // header format is always either SCF or STF, so not SCF automatically means STF
        else {
            // write out bits for header format constant
            bitsOut.writeBits(BITS_PER_INT, STORE_TREE);
            // write out size of tree in bits as part of tree bit representation
            int treeBitSize = huffmanTree.getNumLeaves() * (BITS_PER_WORD + 1) + huffmanTree.size();
            bitsOut.writeBits(BITS_PER_INT, treeBitSize);
            // STFBuilder automatically updates the string builder with the pre-order traversal tree
            // bit representation
            StringBuilder treeBuilder = new StringBuilder();
            STFBuilder(huffmanTree.getRoot(), treeBuilder);
            // puts the tree bit representation into a string and writes it out 1 bit at a time
            String treeHeader = treeBuilder.toString();
            for (int i = 0; i < treeHeader.length(); i++) {
                bitsOut.writeBits(1, treeHeader.charAt(i));
            }
        }
    }

    // recursive helper method to build tree sequence from Huffman tree
    private void STFBuilder(TreeNode node, StringBuilder treeBuilder) {
        if (node != null) {
            if (node.isLeaf()) {
                treeBuilder.append("1");
                String binaryValue = Integer.toBinaryString(node.getValue());
                for (int i = 0; i < (BITS_PER_WORD + 1) - binaryValue.length(); i++) {
                    treeBuilder.append("0");
                }
                treeBuilder.append(binaryValue);
            } else {
                treeBuilder.append("0");
                STFBuilder(node.getLeft(), treeBuilder);
                STFBuilder(node.getRight(), treeBuilder);
            }
        }
    }

    /**
     * Uncompress a previously compressed stream in, writing the
     * uncompressed bits/data to out.
     *
     * @param in  is the previously compressed data (not a BitInputStream)
     * @param out is the uncompressed file/stream
     * @return the number of bits written to the uncompressed file/stream
     * @throws IOException if an error occurs while reading from the input file or
     *                     writing to the output file.
     */
    // method to uncompress file using frequency map or Huffman tree depending on header format
    public int uncompress(InputStream in, OutputStream out) throws IOException {
        originalBitSize = 0;
        BitInputStream bitsIn = new BitInputStream(in);
        BitOutputStream bitsOut = new BitOutputStream(out);
        // declares variable that will store reconstructed frequency map
        int[] newFrequencyMap;
        // declares variable that will store reconstructed Huffman tree
        HuffmanTree newHuffmanTree;
        int magicNumber = bitsIn.readBits(BITS_PER_INT);
        if (magicNumber != MAGIC_NUMBER) {
            throw new IOException("Cannot uncompress, non magic number detected.");
        }
        // checks format header, recreates frequency map or HuffmanTree depending on if header is
        // the SCF or STF header format constant
        int formatConstant = bitsIn.readBits(BITS_PER_INT);
        if (formatConstant == STORE_COUNTS) {
            newFrequencyMap = new int[ALPH_SIZE];
            for (int i = 0; i < ALPH_SIZE; i++) {
                // rebuilds frequency map by reading value of every 32 bits for all 256 values
                newFrequencyMap[i] = bitsIn.readBits(BITS_PER_INT);
            }
            // recreates huffman tree from stored frequencies
            newHuffmanTree = new HuffmanTree(newFrequencyMap);
        } else {
            newHuffmanTree = new HuffmanTree(bitsIn);
        }
        // uncompress the data
        if (uncompressHelper(bitsIn, bitsOut, newHuffmanTree)) {
            return originalBitSize;
        }
        bitsIn.close();
        bitsOut.close();
        return originalBitSize;
    }

    // helper method to uncompress the data one bit at a time
    private boolean uncompressHelper(BitInputStream bitsIn, BitOutputStream bitsOut,
                                     HuffmanTree newHuffmanTree) throws IOException {
        TreeNode tempNode = newHuffmanTree.getRoot();
        while (tempNode != null) {
            int inbits = bitsIn.readBits(1);
            if (inbits == 0) {
                tempNode = tempNode.getLeft();
            } else if (inbits == 1) {
                tempNode = tempNode.getRight();
            }
            if (tempNode.isLeaf()) {
                if (tempNode.getValue() == ALPH_SIZE) {
                    bitsIn.close();
                    bitsOut.close();
                    return true;
                }
                bitsOut.writeBits(BITS_PER_WORD, tempNode.getValue());
                originalBitSize += BITS_PER_WORD;
                tempNode = newHuffmanTree.getRoot();
            }
        }
        return false;
    }

    // sets GUI viewer to inputted viewer
    public void setViewer(IHuffViewer viewer) {
        myViewer = viewer;
    }

    // shows String through viewer based on parameter s
    private void showString(String s) {
        if (myViewer != null) {
            myViewer.update(s);
        }
    }
}



