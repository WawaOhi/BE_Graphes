package org.insa.graphs.algorithm.shortestpath;
import java.util.ArrayList;
import java.util.Iterator;
import org.insa.graphs.*;


public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    @Override
    protected ShortestPathSolution doRun() {
        final ShortestPathData data = getInputData();
        ShortestPathSolution solution = null;
        ArrayList<Label> listLab = new ArrayList<Label>();
        // TODO:
        return solution;
    }

}
