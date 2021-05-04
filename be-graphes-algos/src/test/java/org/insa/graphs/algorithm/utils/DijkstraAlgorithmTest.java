package org.insa.graphs.algorithm.utils;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Arrays;

import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.RoadInformation;
import org.insa.graphs.model.RoadInformation.RoadType;
import org.junit.BeforeClass;

public class DijkstraAlgorithmTest {
	
    // Small graph use for tests
    private static Graph graph;

    // List of nodes
    private static Node[] nodes;

    // List of arcs in the graph, a2b is the arc from node A (0) to B (1).
    @SuppressWarnings("unused")
    private static Arc a2b, a2c, a2e, b2c, c2d_1, c2d_2, c2d_3, c2a, d2a, d2e, e2d;
    
    @BeforeClass
    public static void initAll() throws IOException {

        // On définit les paramètres du chemin que nous allons emprunter
        RoadInformation Cheminement = new RoadInformation(RoadType.UNCLASSIFIED, null, true, 36, null);

        // Create nodes
        nodes = new Node[5];
        for (int i = 0; i < nodes.length; ++i) {
            nodes[i] = new Node(i, null);
        }
        
     // Add arcs...
        a2b = Node.linkNodes(nodes[0], nodes[1], 1, Cheminement, null);
        a2c = Node.linkNodes(nodes[0], nodes[2], 3, Cheminement, null);
        a2e = Node.linkNodes(nodes[0], nodes[4], 3, Cheminement, null);
        b2c = Node.linkNodes(nodes[1], nodes[2], 5, Cheminement, null);
        c2d_1 = Node.linkNodes(nodes[2], nodes[3], 7, Cheminement, null);
        c2d_2 = Node.linkNodes(nodes[2], nodes[3], 9, Cheminement, null);
        c2d_3 = Node.linkNodes(nodes[2], nodes[3], 4, Cheminement, null);
        d2a = Node.linkNodes(nodes[3], nodes[0], 4, Cheminement, null);
        d2e = Node.linkNodes(nodes[3], nodes[4], 9, Cheminement, null);
        e2d = Node.linkNodes(nodes[4], nodes[3], 10, Cheminement, null);
        
        graph = new Graph("ID", "", Arrays.asList(nodes), null);     
    }
    
    
        
}

