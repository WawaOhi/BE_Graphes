package org.insa.graphs.algorithm.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.algorithm.ArcInspectorFactory;
import org.insa.graphs.algorithm.shortestpath.BellmanFordAlgorithm;
import org.insa.graphs.algorithm.shortestpath.DijkstraAlgorithm;
import org.insa.graphs.algorithm.shortestpath.ShortestPathAlgorithm;
import org.insa.graphs.algorithm.shortestpath.ShortestPathData;
import org.insa.graphs.algorithm.shortestpath.ShortestPathSolution;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.GraphStatistics;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;
import org.insa.graphs.model.Point;
import org.insa.graphs.model.RoadInformation;
import org.insa.graphs.model.RoadInformation.RoadType;
import org.insa.graphs.model.io.BinaryGraphReader;
import org.insa.graphs.model.io.BinaryPathReader;
import org.insa.graphs.model.io.GraphReader;
import org.insa.graphs.model.io.PathReader;
import org.junit.BeforeClass;
import org.junit.Test;

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
    	RoadInformation speed10 = new RoadInformation(RoadType.MOTORWAY, null, true, 36, ""),
                		speed20 = new RoadInformation(RoadType.MOTORWAY, null, true, 72, "");

        // Create nodes
        nodes = new Node[6];
        nodes[0]=new Node(0,new Point(0,20));
        nodes[1]=new Node(1,new Point(20,0));
        nodes[2]=new Node(2,new Point(15,43));
        nodes[3]=new Node(3,new Point(12,16));
        nodes[4]=new Node(4,new Point(17,89));
        nodes[5]=new Node(5,new Point(43,45));
        
        // Add arcs...
        a2b = Node.linkNodes(nodes[0], nodes[1], 1, speed10, null);
        a2c = Node.linkNodes(nodes[0], nodes[2], 3, speed20, null);
        a2e = Node.linkNodes(nodes[0], nodes[4], 3, speed10, null);
        b2c = Node.linkNodes(nodes[1], nodes[2], 5, speed10, null);
        c2d_1 = Node.linkNodes(nodes[2], nodes[3], 7, speed20, null);
        c2d_2 = Node.linkNodes(nodes[2], nodes[3], 9, speed10, null);
        c2d_3 = Node.linkNodes(nodes[2], nodes[3], 4, speed20, null);
        d2a = Node.linkNodes(nodes[3], nodes[0], 4, speed20, null);
        d2e = Node.linkNodes(nodes[3], nodes[4], 9, speed10, null);
        e2d = Node.linkNodes(nodes[4], nodes[3], 10, speed20, null);
        
        graph = new Graph("ID", "", Arrays.asList(nodes), new GraphStatistics(null,9,1,72,1));     
    }
    
    @Test
    public void Chemin_Valide() {
    	ShortestPathData data = new ShortestPathData(graph,nodes[0],nodes[1],ArcInspectorFactory.getAllFilters().get(0));
    	ShortestPathAlgorithm Dijkstra = doAlgo(data);
    	ShortestPathSolution solution = Dijkstra.run();
    	assertTrue(solution.getPath().isValid());
    }
    
    @Test
    public void Comparaison_Bellman() {
    	ShortestPathData data = new ShortestPathData(graph,nodes[0],nodes[1],ArcInspectorFactory.getAllFilters().get(0));
    	ShortestPathAlgorithm Dijkstra = doAlgo(data);
    	ShortestPathAlgorithm Bellman = new BellmanFordAlgorithm(data);
    	ShortestPathSolution solution_dijkstra = Dijkstra.run();
    	ShortestPathSolution solution_bellman = Bellman.run();
    	assertEquals(solution_dijkstra.getPath().getLength(),solution_bellman.getPath().getLength(), 1e-6);
    }
    
    @Test
    public void Chemin_Inexistant() {
    	ShortestPathData data = new ShortestPathData(graph,nodes[0],nodes[5],ArcInspectorFactory.getAllFilters().get(0));
    	ShortestPathAlgorithm Dijkstra = doAlgo(data);
    	ShortestPathSolution solution = Dijkstra.run();
    	assertEquals(solution.getStatus(),Status.INFEASIBLE);
    }
    
    @Test
    public void Chemin_Nul() {
    	ShortestPathData data = new ShortestPathData(graph,nodes[0],nodes[0],ArcInspectorFactory.getAllFilters().get(0));
    	ShortestPathAlgorithm Dijkstra = doAlgo(data);
    	ShortestPathSolution solution = Dijkstra.run();
    	assertEquals(solution.getPath().size(),1); 	
    }
    
    @Test
    public void Carte_Length() throws Exception{
    	
    	
    	// On va aller chercher la map et le chemin que l'on souhaite analyser 
        final String map_c = "C:\\Users\\jordm\\Desktop\\Maps\\haute-garonne.mapgr";
        final String path_c = "C:\\Users\\jordm\\Desktop\\Maps\\path_fr31_insa_aeroport_length.path";
        
        // Create a graph reader and a path reader.
        final GraphReader reader = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(map_c))));
        final PathReader pathReader = new BinaryPathReader(new DataInputStream(new BufferedInputStream(new FileInputStream(path_c))));
        
        //read the graph and the path
        final Graph graph = reader.read();
        final Path path = pathReader.readPath(graph);
        
        ShortestPathData data = new ShortestPathData(graph,path.getOrigin(),path.getDestination(),ArcInspectorFactory.getAllFilters().get(1));
        ShortestPathAlgorithm Dijkstra = doAlgo(data);
        ShortestPathSolution solution = Dijkstra.run();
        assertEquals(solution.getPath().getLength(),path.getLength(), 1e-6);      
        
    }
    
    @Test
    public void Carte_Time() throws Exception{
    	
    	// On va aller chercher la map et le chemin que l'on souhaite analyser 
        final String map_c = "C:\\Users\\jordm\\Desktop\\Maps\\haute-garonne.mapgr";
        final String path_c = "C:\\Users\\jordm\\Desktop\\Maps\\path_fr31_insa_aeroport_time.path";
        
        // Create a graph reader and a path reader.
        final GraphReader reader = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(map_c))));
        final PathReader pathReader = new BinaryPathReader(new DataInputStream(new BufferedInputStream(new FileInputStream(path_c))));
        
        //read the graph and the path
        final Graph graph = reader.read();
        final Path path = pathReader.readPath(graph);
        
        ShortestPathData data = new ShortestPathData(graph,path.getOrigin(),path.getDestination(),ArcInspectorFactory.getAllFilters().get(3));
        ShortestPathAlgorithm Dijkstra = doAlgo(data);
        ShortestPathSolution solution = Dijkstra.run();
        assertEquals(solution.getPath().getMinimumTravelTime(),path.getMinimumTravelTime(), 1e-6);
        
    }
    
    protected ShortestPathAlgorithm doAlgo(ShortestPathData data) {
    	return new DijkstraAlgorithm(data);
    } 
        
}

