package it.polito.tdp.artsmia.model;

public class ArtObjectPeso implements Comparable<ArtObjectPeso> {
	
	private ArtObject a;
	private int peso;
	public ArtObjectPeso(ArtObject a, int peso) {
		super();
		this.a = a;
		this.peso = peso;
	}
	public ArtObject getA() {
		return a;
	}
	public void setA(ArtObject a) {
		this.a = a;
	}
	public int getPeso() {
		return peso;
	}
	public void setPeso(int peso) {
		this.peso = peso;
	}
	@Override
	public int compareTo(ArtObjectPeso o) {
		
		return this.getA().getName().compareTo(o.getA().getName());
	}
	
	

}
