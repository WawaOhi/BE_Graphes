package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Node;

public class AStarAlgorithm extends DijkstraAlgorithm {
	

    public AStarAlgorithm(ShortestPathData data) {
        super(data);
    }
    
    //comparaison entre la vitesse donnée par les data et par celui du graph car quelques fois cela pose problème et génère des erreurs.
    private double return_Max_Speed() {
    	double max1 = (double)this.data.getMaximumSpeed();
    	double max2 = this.data.getGraph().getGraphInformation().getMaximumSpeed();
    	return Math.max(max1, max2);
    }
    
    protected Label New_Lab(Node noeud) {
    	ShortestPathData data = this.getInputData();
    	return new LabelStar(noeud, data, this.return_Max_Speed());
    }

}
