package it.polito.tdp.artsmia.db;

import it.polito.tdp.artsmia.model.ArtObject;

public class Arco {
	
	private ArtObject a1;
	private ArtObject a2;
	private double peso;
	
	public Arco(ArtObject a1, ArtObject a2, double peso) {
	
		this.a1 = a1;
		this.a2 = a2;
		this.peso = peso;
	}
	public ArtObject getA1() {
		return a1;
	}
	public void setA1(ArtObject a1) {
		this.a1 = a1;
	}
	public ArtObject getA2() {
		return a2;
	}
	public void setA2(ArtObject a2) {
		this.a2 = a2;
	}
	public double getPeso() {
		return peso;
	}
	public void setPeso(double peso) {
		this.peso = peso;
	}
	
	

}
