package it.polito.tdp.artsmia.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.artsmia.db.Arco;
import it.polito.tdp.artsmia.db.ArtsmiaDAO;

public class Model {

	
	SimpleWeightedGraph<ArtObject, DefaultEdge> grafo;
	ArtsmiaDAO dao=new ArtsmiaDAO();
	Map<Integer, ArtObject> idMap;
	
	List<ArtObject> disponibili;
	List<ArtObjectPeso> parziale;
	
	List<ArtObjectPeso> camminoOttimo;
	int pesoMax;
	
	int best;
	
	public void creaGrafo() {
		
		
		grafo=new SimpleWeightedGraph<>(DefaultEdge.class);
		
		idMap=new TreeMap<>();
		
		for(ArtObject a: this.dao.listObjects()) {
			idMap.put(a.getId(), a);
		}
		
		Graphs.addAllVertices(this.grafo, idMap.values());
		
		for(Arco a: this.dao.getAllArchi(idMap)) {
			if(!this.grafo.containsVertex(a.getA1()))
				this.grafo.addVertex(a.getA1());
			if(!this.grafo.containsVertex(a.getA2()))
				this.grafo.addVertex(a.getA2());
			if(this.grafo.getEdge(a.getA1(), a.getA2())==null) {
				Graphs.addEdgeWithVertices(this.grafo, a.getA1(), a.getA2(), a.getPeso());
			}
			
		}
		
	}
	
	
	public String creaSoluzione() {
		return "GRAFO CREATO CON "+this.grafo.vertexSet().size()+" VERTICI E "+this.grafo.edgeSet().size()+" ARCHI \n";
	}
	
	public boolean verificaObj(int id) {
		if(idMap.containsKey(id))
			return true;
		return false;
		
	}


	public int calcolaComponenteConnessa(int id) {
		
		ArtObject cercato=idMap.get(id);
		
		return Graphs.neighborListOf(this.grafo, cercato).size();

	}
	
	public void cercaOttimo(int lunghezza, int id) {
		
		parziale= new LinkedList<>();
		camminoOttimo=new LinkedList<>();
		pesoMax=0;
		best=0;
		
		parziale.add(new ArtObjectPeso(this.idMap.get(id),0));
		disponibili=new LinkedList<>(this.dao.listObjectsByClassification((this.idMap.get(id).getClassification())));
		
		ricorsione(lunghezza, parziale);
		
	}
	
	public void ricorsione(int lunghezza, List<ArtObjectPeso> parziale) {
		
		//CONDIZIONE DI TERMINAZIONE
		if(parziale.size()==lunghezza) {
			
			pesoMax=0;
			
			for(ArtObjectPeso a: parziale) {
				
					pesoMax=pesoMax+a.getPeso();

				}
			
			if(pesoMax>best) {
				camminoOttimo=new LinkedList<>(parziale);
				best=pesoMax;
			}
			
			return;
			}
			
		
		
		
		for(ArtObject a: Graphs.neighborListOf(this.grafo, parziale.get(parziale.size()-1).getA()))
		{
			if(a.getClassification().equals(parziale.get(parziale.size()-1).getA().getClassification())) {
				ArtObjectPeso ap=new ArtObjectPeso(a, (int)this.grafo.getEdgeWeight(this.grafo.getEdge(a, parziale.get(parziale.size()-1).getA())));
				if(!parziale.contains(ap)) {
				//parziale.add(new ArtObjectPeso(a, (int)this.grafo.getEdgeWeight(this.grafo.getEdge(a, parziale.get(parziale.size()-1).getA()))));
				parziale.add(ap);
				ricorsione(lunghezza, parziale);
				parziale.remove(ap);	
				}
			}
				
		}
		
		
		
		
	}


	public List<ArtObjectPeso> getCamminoOttimo() {
		return camminoOttimo;
	}


	public void setCamminoOttimo(List<ArtObjectPeso> camminoOttimo) {
		this.camminoOttimo = camminoOttimo;
	}


	public int getBest() {
		return best;
	}


	public void setBest(int best) {
		this.best = best;
	}
	
	
	
	
	
	
}
