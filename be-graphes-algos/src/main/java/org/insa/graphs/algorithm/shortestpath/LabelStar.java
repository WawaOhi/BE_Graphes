package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.algorithm.AbstractInputData.Mode;
import org.insa.graphs.model.Node;


public class LabelStar extends Label {
	
	private double cout_estime;
	
	public LabelStar(Node noeud, ShortestPathData data, double Max_Speed) {
		super(noeud);
		
		if(data.getMode() == Mode.TIME) {
			this.cout_estime = 3.6*(noeud.getPoint().distanceTo(data.getDestination().getPoint()))/Max_Speed;		
		}
		else if(data.getMode() == Mode.LENGTH) {
			this.cout_estime = noeud.getPoint().distanceTo(data.getDestination().getPoint());	
		}
		
	}
	
	public double getCoutEstime() {
		return this.cout_estime;
	}
	
	public float getTotalCost() {
		return this.getCost() + (float)this.cout_estime;
	}
		
}
