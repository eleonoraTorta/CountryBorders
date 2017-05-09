package it.polito.tdp.country.model;

public class TestModel {

	public static void main(String[] args) {
		Model m = new Model() ;
		
		long start, end ;
		
		System.out.println("Creo il grafo CountryBorders usando i 3 metodi");
		
		start=System.nanoTime() ;
		m.creaGrafo1();
		end=System.nanoTime() ;
		System.out.format("Metodo 1: %d ms\n", (end-start)/1000000) ;
		m.printStats();
		
		start=System.nanoTime() ;
		m.creaGrafo2();
		end=System.nanoTime() ;
		System.out.format("Metodo 2: %d ms\n", (end-start)/1000000) ;
		m.printStats();

		start=System.nanoTime() ;
		m.creaGrafo3();
		end=System.nanoTime() ;
		System.out.format("Metodo 3: %d ms\n", (end-start)/1000000) ;
		m.printStats();

	}

}