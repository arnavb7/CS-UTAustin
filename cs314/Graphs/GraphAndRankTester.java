/* CS 314 STUDENTS: FILL IN THIS HEADER.
 *
 * Student information for assignment:
 *
 *  On my honor, Arnav Bhasin, this programming assignment is my own work
 *  and I have not provided this code to any other student.
 *
 *  UTEID: ab78845
 *  email address: bhasin.arnav07@gmail.com
 *  TA name: Brad
 */

/*
 * Question.
 *
 * 1. The assignment presents three ways to rank teams using graphs.
 * The results, especially for the last two methods are reasonable.
 * However if all results from all college football teams are included
 * some unexpected results occur.
 *
 * Explain the unexpected results. You may
 * have to do some research on the various college football divisions to
 * make an informed answer. (What are the divisions within college
 * football? Who do teams play? How would this affect the
 * structure of the graph?)
 *
 * 
 */

public class GraphAndRankTester {

    /**
     * Runs tests on Graph classes and FootballRanker class.
     * Experiments involve results from college football
     * teams. Central nodes in the graph are compared to
     * human rankings of the teams.
     * @param args None expected.
     */
    public static void main(String[] args)  {
        graphTests();

        String actual = "2008ap_poll.txt";
        String gameResults = "div12008.txt";

        FootballRanker ranker = new FootballRanker(gameResults, actual);

        ranker.doUnweighted(true);
        ranker.doWeighted(true);
        ranker.doWeightedAndWinPercentAdjusted(true);

        System.out.println();
        doRankTests(ranker);

        System.out.println();

    }

    // tests on various simple Graphs
    private static void graphTests() {
        System.out.println("PERFORMING TESTS ON SIMPLE GRAPHS\n");
        personalGraphTest1();
        personalGraphTest2();
        // graph0Tests();
        // graph1Tests();
        // graph2Tests();
        // graph3Tests();
    }

    private static void personalGraphTest1() {
        System.out.println("Personal Graph Test #1:");
        // first a simple path test
        // Graph #1
        String [][] g1Edges =  {{"A", "B", "15"},
                {"B", "C", "31"},
                {"A", "D", "20"},
                {"B", "D", "21"},
                {"C", "G", "13"},
                {"A", "G", "17"},
                {"D", "F", "15"},
                {"D", "G", "5"},
                {"D", "E", "61"}};
        Graph g1 = getGraph(g1Edges, false);

        g1.dijkstra("A");
        String actualPath = g1.findPath("F").toString();
        String expected = "[A, D, F]";
        if (actualPath.equals(expected)) {
            System.out.println("Passed dijkstra path test graph 1.");
        } else {
            System.out.println("Failed dijkstra path test graph 1. Expected: " + expected + " " +
                    "actual " + actualPath);
        }

        // now do all paths unweighted
        String[] expectedPaths = {"Name: D                    cost per path: 1.1667, num paths: 6",
                "Name: A                    cost per path: 1.5000, num paths: 6",
                "Name: B                    cost per path: 1.5000, num paths: 6",
                "Name: G                    cost per path: 1.5000, num paths: 6",
                "Name: C                    cost per path: 2.0000, num paths: 6",
                "Name: E                    cost per path: 2.0000, num paths: 6",
                "Name: F                    cost per path: 2.0000, num paths: 6"};
        doAllPathsTests("Personal Graph 1", g1, false, 3, 3.0, expectedPaths);

        // now do all paths weighted
        expectedPaths = new String[] {  "Name: D                    cost per path: 23.3333, num " +
                "paths: 6",
                "Name: G                    cost per path: 24.5000, num paths: 6",
                "Name: A                    cost per path: 33.0000, num paths: 6",
                "Name: C                    cost per path: 34.0000, num paths: 6",
                "Name: B                    cost per path: 35.1667, num paths: 6",
                "Name: F                    cost per path: 35.8333, num paths: 6",
                "Name: E                    cost per path: 74.1667, num paths: 6"};
        doAllPathsTests("Personal Graph 1", g1, true, 2, 82.0, expectedPaths);
    }

    private static void personalGraphTest2() {
        System.out.println("Personal Graph Test #2:");
        // first a simple path test
        // Graph #2
        String [][] g1Edges =  {{"A", "B", "2"},
                {"B", "C", "2"},
                {"A", "D", "2"},
                {"B", "E", "2"},
                {"C", "E", "2"},
                {"A", "G", "2"},
                {"D", "F", "2"},
                {"D", "G", "2"}};
        Graph g1 = getGraph(g1Edges, false);

        g1.dijkstra("A");
        String actualPath = g1.findPath("F").toString();
        String expected = "[A, D, F]";
        if (actualPath.equals(expected)) {
            System.out.println("Passed dijkstra path test graph 2.");
        } else {
            System.out.println("Failed dijkstra path test graph 2. Expected: " + expected + " " +
                    "actual " + actualPath);
        }

        // now do all paths unweighted
        String[] expectedPaths = {"Name: A                    cost per path: 1.5000, num paths: 6",
                "Name: B                    cost per path: 1.6667, num paths: 6",
                "Name: D                    cost per path: 1.8333, num paths: 6",
                "Name: G                    cost per path: 2.0000, num paths: 6",
                "Name: C                    cost per path: 2.3333, num paths: 6",
                "Name: E                    cost per path: 2.3333, num paths: 6",
                "Name: F                    cost per path: 2.6667, num paths: 6"};
        doAllPathsTests("Personal Graph 2", g1, false, 4, 4.0, expectedPaths);

        // now do all paths weighted
        expectedPaths = new String[] {  "Name: A                    cost per path: 3.0000, num " +
                "paths: 6",
                "Name: B                    cost per path: 3.3333, num paths: 6",
                "Name: D                    cost per path: 3.6667, num paths: 6",
                "Name: G                    cost per path: 4.0000, num paths: 6",
                "Name: C                    cost per path: 4.6667, num paths: 6",
                "Name: E                    cost per path: 4.6667, num paths: 6",
                "Name: F                    cost per path: 5.3333, num paths: 6"};
        doAllPathsTests("Personal Graph 2", g1, true, 4, 8.0, expectedPaths);
    }

    // return a Graph based on the given edges
    private static Graph getGraph(String[][] edges, boolean directed) {
        Graph result = new Graph();
        for (String[] edge : edges) {
            result.addEdge(edge[0], edge[1], Double.parseDouble(edge[2]));
            // If edges are for an undirected graph add edge in other direction too.
            if (!directed) {
                result.addEdge(edge[1], edge[0], Double.parseDouble(edge[2]));
            }
        }
        return result;
    }

    // Tests the all paths method. Run each set of tests twice to ensure the Graph
    // is correctly reseting each time
    private static void doAllPathsTests(String graphNumber, Graph g, boolean weighted,
                                        int expectedDiameter, double expectedCostOfLongestShortestPath,
                                        String[] expectedPaths) {

        System.out.println("\nTESTING ALL PATHS INFO ON " + graphNumber + ". ");
        for (int i = 0; i < 2; i++) {
            System.out.println("Test run = " + (i + 1));
            System.out.println("Find all paths weighted = " + weighted);
            g.findAllPaths(weighted);
            int actualDiameter = g.getDiameter();
            double actualCostOfLongestShortesPath = g.costOfLongestShortestPath();
            if (actualDiameter == expectedDiameter) {
                System.out.println("Passed diameter test.");
            } else {
                System.out.println("FAILED diameter test. "
                        + "Expected = "  + expectedDiameter +
                        " Actual = " + actualDiameter);
            }
            if (actualCostOfLongestShortesPath == expectedCostOfLongestShortestPath) {
                System.out.println("Passed cost of longest shortest path. test.");
            } else {
                System.out.println("FAILED cost of longest shortest path. "
                        + "Expected = "  + expectedCostOfLongestShortestPath +
                        " Actual = " + actualCostOfLongestShortesPath);
            }
            testAllPathsInfo(g, expectedPaths);
            System.out.println();
        }

    }

    // Compare results of all paths info on Graph to expected results.
    private static void testAllPathsInfo(Graph g, String[] expectedPaths) {
        int index = 0;

        for (AllPathsInfo api : g.getAllPaths()) {
            if (expectedPaths[index].equals(api.toString())) {
                System.out.println(expectedPaths[index] + " is correct!!");
            } else {
                System.out.println("ERROR IN ALL PATHS INFO: ");
                System.out.println("index: " + index);
                System.out.println("EXPECTED: " + expectedPaths[index]);
                System.out.println("ACTUAL: " + api.toString());
                System.out.println();
            }
            index++;
        }
        System.out.println();
    }

    // Test the FootballRanker on the given file.
    private static void doRankTests(FootballRanker ranker) {
        System.out.println("\nTESTS ON FOOTBALL TEAM GRAPH WITH FootBallRanker CLASS: \n");
        double actualError = ranker.doUnweighted(false);
        if (actualError == 13.7) {
            System.out.println("Passed unweighted test");
        } else {
            System.out.println("FAILED UNWEIGHTED ROOT MEAN SQUARE ERROR TEST. Expected 13.7, actual: " + actualError);
        }

        actualError = ranker.doWeighted(false);
        if (actualError == 12.6) {
            System.out.println("Passed weigthed test");
        } else {
            System.out.println("FAILED WEIGHTED ROOT MEAN SQUARE ERROR TEST. Expected 12.6, actual: " + actualError);
        }


        actualError = ranker.doWeightedAndWinPercentAdjusted(false);
        if (actualError == 6.3) {
            System.out.println("Passed unweighted win percent test");
        } else {
            System.out.println("FAILED WEIGHTED  AND WIN PERCENT ROOT MEAN SQUARE ERROR TEST. Expected 6.3, actual: " + actualError);
        }
    }
}