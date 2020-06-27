package it.polito.tdp.food.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.food.db.FoodDao;

public class Model {
	
	private FoodDao dao;
	private Graph<String,DefaultWeightedEdge> grafo ;
	private List<String>vertici;
	private ArrayList<String> best;
	private int pesoTot;
	
	public Model() {
		dao=new FoodDao();

	}
	
	public void creaGrafo(int calorie) {
		
		grafo=new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		vertici=new LinkedList<>(dao.getPorzioni(calorie));
		
		Graphs.addAllVertices(grafo,vertici);
		
		for(Adiacenza a :dao.getAdiacenze(vertici)) {
			if(a!=null)
			  Graphs.addEdge(grafo, a.getP1(), a.getP2(), a.getPeso());
		}
	}
	
	public int getNumVertici() {
		return this.grafo.vertexSet().size();
	}
	
	public int getNumArchi() {
		return this.grafo.edgeSet().size();
	}
	
	public Graph<String,DefaultWeightedEdge>getGrafo(){
		return this.grafo;
	}
	public Collection<Adiacenza>getConnessi(String s){
		List<Adiacenza>l=new LinkedList<>();
		List<String>vicini=new LinkedList<>(Graphs.neighborListOf(grafo, s));
		for(String vicino:vicini) {
			l.add(new Adiacenza(s,vicino,(int) grafo.getEdgeWeight(grafo.getEdge(s, vicino))));
		}
		return l;	
	}
	
	
	
	
	public List<String> trovaPercorso(String sorgente,int NPassi) {
		List<String> parziale = new ArrayList<>();
		this.best = new ArrayList<>();
		parziale.add(sorgente);
		int pesoParziale=0;
		trovaRiscorsivo(NPassi,pesoParziale,parziale, 0);
		return this.best;
	}

	private void trovaRiscorsivo(int NPassi,int pesoParziale,List<String> parziale, int P) {

		//CASO TERMINALE? -> quando l'ultimo vertice inserito in parziale è uguale alla destinazione
		if(P==NPassi) {
			if(pesoParziale>pesoTot) {
				this.best = new ArrayList<>(parziale);
				this.pesoTot=pesoParziale;
			}
			return;
		}
		
		//scorro i vicini dell'ultimo vertice inserito in parziale
		for(String vicino : Graphs.neighborListOf(this.grafo, parziale.get(parziale.size() -1 ))) {
			//cammino aciclico -> controllo che il vertice non sia già in parziale
			if(!parziale.contains(vicino)) {
				//provo ad aggiungere
				pesoParziale+=grafo.getEdgeWeight(grafo.getEdge(parziale.get(parziale.size() -1), vicino));
				parziale.add(vicino);
				//continuo la ricorsione
				this.trovaRiscorsivo(NPassi,pesoParziale, parziale, P+1);
				//faccio backtracking
				parziale.remove(parziale.size() -1);
				pesoParziale-=grafo.getEdgeWeight(grafo.getEdge(parziale.get(parziale.size() -1), vicino));
				
			}
		}
	}
	
	public int getPesoTot() {
		return this.pesoTot;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
