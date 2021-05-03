package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Node;


public class Label implements Comparable<Label> {
	public Node sommet_courant;
	public boolean marque; 
	public float cout;
	public Node pere;
	public boolean dans_tas;
	
	public Label(Node noeud) {
		this.sommet_courant = noeud;
		this.marque=false;
		this.cout= Float.POSITIVE_INFINITY;
		this.pere = null;
		this.dans_tas = false;
	}
	
	//les differentes méthodes suivantes sont utilisées pour obtenir des informations sur le label.
	
	public Node getNode() {
		return this.sommet_courant;
	}
	
	public float getCost() {
		return this.cout;
	}
	
	public boolean getMark() {
		return this.marque;
	}
	
	public Node getFather() {
		return this.pere;
	}
	
	public boolean getState() {
		return this.dans_tas;
	}
	
	//les differentes méthodes suivantes sont utilisées pour modifier le label.
	
	public void setNode(Node node) {
		this.sommet_courant = node;
	}
	
	public void setCost(float prix) {
		this.cout = prix;
	}
	
	public void setMark() {
		this.marque = true;
	}
	
	public void setFather(Node geniteur) {
		this.pere = geniteur;
	}
	
	public void setState() {
		this.dans_tas=true;
	}

	//méthode necesaire à a creation de BinaryHeap contenant des labels.
	
	@Override
	public int compareTo(Label autre) {
		// TODO Auto-generated method stub
		int resultat;
		if(this.getCost()<autre.getCost()) {
			resultat = -1;
		}
		
		else if (this.getCost() == autre.getCost()) {
			resultat = 0;
		}
		
		else {
			resultat = 1;
		}
		return resultat;
	}
		
}
