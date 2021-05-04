package org.insa.graphs.algorithm.shortestpath;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.model.*;



public class DijkstraAlgorithm extends ShortestPathAlgorithm {
	protected int nbSommets;
	protected int nbSommetsVus;

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
        this.nbSommetsVus = 0;
    }

    @Override
    protected ShortestPathSolution doRun() {
    	
    	//on initialise un boolean pour mettre fin à la boucle 
    	boolean fin = false;
    	
    	//On récupère les differentes informations du Graph
        final ShortestPathData data = getInputData();     
        Graph graph = data.getGraph();
        int tailleGraph = graph.size();
        
        //on initialise la solution
        ShortestPathSolution solution = null;
        
        //On crée un tableau de Label pour associer chaque label à chaque Node
        Label tabLabel[] = new Label[tailleGraph]; 
        
        //On crée un tas pour les labels
        BinaryHeap<Label> tas = new BinaryHeap<Label>();
        
        //On crée également un tableau qui va contenir les prédescesseurs du noeud considéré
        Arc[] Arc_precedent = new Arc[tailleGraph];
        
        //On commence par ajouter le premier sommet 
        Label premier = new Label(data.getOrigin());
        tabLabel[premier.getNode().getId()] = premier;
        tas.insert(premier);
        premier.setState(); //permet de noter le label comme étant dans le tas 
        premier.setCost(0); //on met le prix initialement à 0
        
        //On notifie que le premier élement à été introduit 
        notifyOriginProcessed(data.getOrigin());
        
        //tant que nous avons des sommets qui ne sont pas marqués on avance
        while(!tas.isEmpty() && !fin) {
        	Label actuel = null;
        	actuel = tas.deleteMin();
        	actuel.setMark();
        	
        	//On notifie que le noeud que nous considérons à été marqué 
        	notifyNodeMarked(actuel.getNode());
        	
        	//on vérifie si on n'est pas arrivé à la fin 
        	if(actuel.getNode() == data.getDestination()) {
        		fin = true;
        	}
        	
        	//on parcours les successeurs du sommet que l'on traite 
        	Iterator<Arc> arc = actuel.getNode().getSuccessors().iterator();
        	
        	while(arc.hasNext()) {
        		Arc iterArc = arc.next();
        		
        		if(!data.isAllowed(iterArc)) {
        			continue;
        		}
        		
        		//on récupère le label et le node successeur 
        		Node successeur = iterArc.getDestination();
        		
        		//si nous avons un successeur alors nous récuperons son label
        		Label Success_Lab = tabLabel[successeur.getId()];
        		
        		//si le label n'existe pas alors nous devons le créer
        		if(Success_Lab == null) {
        			//on notifie que l'on viens d'arriver à un noeud qui n'avais pas été marqué 
        			notifyNodeReached(iterArc.getDestination());
        			Success_Lab = new Label (successeur);
        			tabLabel[Success_Lab.getNode().getId()] = Success_Lab;
        			this.nbSommetsVus++;
        		}
        		
        		//On vérifie que c'est bien marqué 
        		if(!Success_Lab.getMark()) {
        			//On vérifie la valeur du cout pour voir si il est meilleur ou non
        			if(Success_Lab.getCost()>(actuel.getCost()+data.getCost(iterArc))) {
        				
        				Success_Lab.setCost(actuel.getCost() + (float)data.getCost(iterArc));
        				Success_Lab.setFather(actuel.getNode());
        				
        				//on vérifie la position du Label dans le tas 
        				if(Success_Lab.getMark()) {
        					tas.remove(Success_Lab);
        				}
        				//sinon on le met dans le tas
        				else {
        					Success_Lab.setMark();
        				}
        				tas.insert(Success_Lab);
        				Arc_precedent[iterArc.getDestination().getId()] = iterArc;
        			}
        		}
        		
        	}
        	
        }
        
        //Or, nous avons le cas ou la destination n'a pas de prédecesseurs donc pas de solution 
        if(Arc_precedent[data.getDestination().getId()] == null) {
        	solution = new ShortestPathSolution(data,Status.INFEASIBLE);
        }
        else {
        	
        	//on notifie que la déstination à été atteinte
        	notifyDestinationReached(data.getDestination());
        	
        	//on va créer un chemin des points que nous avons créé
        	ArrayList<Arc> arcs = new ArrayList<>();
        	Arc n_arc = Arc_precedent[data.getDestination().getId()];
        	
        	while(n_arc != null) {
        		arcs.add(n_arc);
        		n_arc = Arc_precedent[n_arc.getOrigin().getId()];		
        	}
        	
        	//on inverse ce que nous venons de créer pour avoir le chemin du point de départ jusqu'a la destination 
        	Collections.reverse(arcs);
        	
        	//finalement, nous creons la solution finale 
        	solution = new ShortestPathSolution(data, Status.OPTIMAL,new Path(graph,arcs));
        }
        return solution;
    }

}
