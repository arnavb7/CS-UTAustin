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

public class HuffmanTree {

    // instance variable representing root node of Huffman tree
    private TreeNode root;

    // instance variable representing total number of nodes in Huffman tree
    private int size;

    // instance variable representing total number of leaf nodes in Huffman tree
    private int numLeaves;

    /**
     * constructs a HuffmanTree based on frequencies of values in the frequency map array
     *
     * @param frequencyMap map of frequency nodes
     */
    public HuffmanTree(int[] frequencyMap) {
        PriorityQueue314<TreeNode> nodeQueue = new PriorityQueue314<>();
        numLeaves = 0;
        for (int i = 0; i < IHuffConstants.ALPH_SIZE; i++) {
            if (frequencyMap[i] > 0) {
                TreeNode node = new TreeNode(i, frequencyMap[i]);
                nodeQueue.enqueue(node);
                numLeaves++;
            }
        }
        TreeNode node = new TreeNode(IHuffConstants.PSEUDO_EOF, 1);
        nodeQueue.enqueue(node);
        numLeaves++;
        size = numLeaves;
        while (nodeQueue.size() >= 2) {
            TreeNode iNode = new TreeNode(nodeQueue.dequeue(), -1, nodeQueue.dequeue());
            size++;
            nodeQueue.enqueue(iNode);
        }
        root = nodeQueue.dequeue();
    }

    // constructor for decoding tree format from a bitInputStream
    public HuffmanTree(BitInputStream bitsIn) throws IOException {
        bitsIn.readBits(IHuffConstants.BITS_PER_INT);
        root = constructorHelp(bitsIn);
    }

    // constructor recursive helper method to create Huffman tree from tree sequence
    private TreeNode constructorHelp(BitInputStream bitsIn) throws IOException {
        int inbits = bitsIn.readBits(1);
        if (inbits == 0) {
            TreeNode iNode = new TreeNode(-1, 0);
            iNode.setLeft(constructorHelp(bitsIn));
            iNode.setRight(constructorHelp(bitsIn));
            return iNode;
        } else if (inbits == 1) {
            int value = bitsIn.readBits(IHuffConstants.BITS_PER_WORD + 1);
            return new TreeNode(value, 0);
        } else {
            throw new IOException("Invalid bit encountered: " + inbits);
        }
    }

    /**
     * @return root node of HuffmanTree
     */
    public TreeNode getRoot() {
        return root;
    }

    /**
     * @return total number of nodes in HuffmanTree
     */
    public int size() {
        return size;
    }

    /**
     * @return number of leaf nodes in HuffmanTree
     */
    public int getNumLeaves() {
        return numLeaves;
    }

    /**
     * changes root node to that in parameter
     *
     * @param rt node to change root to
     */
    public void add(TreeNode rt) {
        root = rt;
    }
}

