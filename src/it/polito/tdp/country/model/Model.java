package it.polito.tdp.country.model;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.BreadthFirstIterator;

import it.polito.tdp.country.dao.CountryDao;
import it.polito.tdp.country.dao.DBConnect;

public class Model {
	
	//quando creo un grafo devo passare nel costruttore il tipo di arco che voglio usare (.class)
	private UndirectedGraph <Country,DefaultEdge> graph; 
	private CountryDao dao;
	private List<Country> countries;
	private Map<Country, Country> alberoVisita;
	private CountryIdMap countryIdMap;				//ID MAP
//	private CountryDaoIdMap dao_id;
	
	public Model(){
		dao = new CountryDao();
		this.countryIdMap = new CountryIdMap();		//ID MAP
	//  dao_id = new CountryDaoIdMap();
	}
	
	public List<Country> getCountries(){
		//implementazione banale (richiama ogni volta il database!!!!)
		//return dao.listCountry();
		
		if(this.countries==null){
			this.countries = dao.listCountry();
	//		this.countries = dao_id.listCountry(countryIdMap);    ID MAP
		}
		return this.countries;
	}
	
	public List<Country> getRaggiungibili(Country partenza){
		
		UndirectedGraph <Country, DefaultEdge> g = this.getGrafo();
		
		//faccio una visita
		BreadthFirstIterator <Country, DefaultEdge> bfi = new BreadthFirstIterator <Country, DefaultEdge>(g, partenza);
		
		// aggiungo a una lista gli stati raggiungibili
		List <Country> list = new ArrayList<>();
		
		//aggancio un Traversal Listener all'iteratore.
		//l'iteratore, man mano che visita il grafo, scatena degli eventi che vengono intercettati dal Listener.
		//il listener (ovvero la classe CountryTraversalListener che ho creato e che implementa l'interfaccia TraversalListener
		//esegue automaticamente dei metodi di default che mi possono fornire informazioni:
		//es. edgeTraversed indica quale arco viene attraversato in quel momento, ovvero tra quali due nodi sta passando l'iteratore
		
		//Poiche il metodo edgeTraversed del Listener deve raccogliere delle info in una mappa, posso creare qui nel model
		//una mappa vuota che passo nel contruttore del lister (oltre al grafo) che a sua volta la popolera` automaticamente.
		//se avessi creato la mappa nel listener "si sarebbe persa"/ non sarebbe stata recuperabile perche il listener lavora di nascosto!
		
		Map <Country, Country> albero = new HashMap();
		albero.put(partenza, null); //il valore di partenza deve esserci gia
		
		bfi.addTraversalListener(new CountryTraversalListener(g, albero));
		
		while(bfi.hasNext()){
			list.add(bfi.next());
		}
		
		//salvo l'albero di visita che a questo punto sara` riempito
		this.alberoVisita = albero;
		
		return list;
	}
	
	private UndirectedGraph <Country, DefaultEdge> getGrafo(){
		if(this.graph==null){
			this.creaGrafo3();
		}
		return this.graph;
	}
	
	public void creaGrafo1(){
		
		this.graph = new SimpleGraph<>(DefaultEdge.class) ;
		
		//crea i vertici del grafo
		Graphs.addAllVertices(graph, this.getCountries());  //non passo this.countries perche non so se e` gia stata chiamata!		
		
		//crea gli archi del grafo -- VERSIONE 1
		for(Country c1 : graph.vertexSet()){
			for(Country c2 : graph.vertexSet()){
				if(!c1.equals(c2)){
					if(dao.confinanti(c1,c2)){   // la query viene effettuata N^2 volte, precisamente 218 x 217 = 47.306 query (N= numero di country)
						graph.addEdge(c1, c2);
					}
				}
			}
		}
		//--> la versione 1 e` molto lenta perche` ogni volta che effettuo una query apro un socket = (connessione locale del sistema 
		// operativo con il database), poi chiudo la connessione subito (perche il tempo di esecuzione di questa query e` brevissimo)
		//ma un socket richiede piu` tempo per chiudersi. Dunque poiche apro molte connessioni una di seguito all'altra, i vari socket 
		//non fanno in tempo a chiudersi. Cosi rimangono in sospeso molti socket fino a che non raggiungono il numero massimo di socket
		// possibili aperti supportato dal sistema operativo. Una volta raggiunto questo numero il programma si intasa.
		
		// Dunque il problema e` che chiamo TROPPE VOLTE e TROPPO RAPIDAMENTE il metodo getConnection 
		// (che eseguo nel dao ogni volta che faccio la query) e dunque non riesce a finire di girare poiche apre molti socket:
		// Connection conn = DBConnect.getConnection();
		
	}
		
	public void creaGrafo2(){
			
		this.graph = new SimpleGraph<>(DefaultEdge.class) ;
		
		//crea i vertici del grafo
		Graphs.addAllVertices(graph, this.getCountries());
		
		//crea gli archi del grafo -- VERSIONE 2
		for(Country c: graph.vertexSet()){						//query eseguita N volte
			List <Country> adiacenti = dao.listAdiacenti(c);   // la query viene effettuata (N * grado medio del grafo) volte
	//		List <Country> adiacenti = dao_id.listAdiacenti(c,countryIdMap); 		ID MAP
			for(Country c2: adiacenti){
				graph.addEdge(c,c2);
			}
		}
		// il tempo di esecuzione di una query sul database equivale circa a 1 millisecondo. Poiche devo eseguire N= 218 query, 
		//mi aspetto ch eil tempo totale di esecuzione del metodo sia di circa 218 x 1 millisec = 218 millisec.
		// Invece ci impiega 1000 millisecondi, ovvero e` 5 volte piu` lento = 80% del tempo e` perso
		
		// Dunque il problema in questo caso e` sempre il metodo getConnection che, benche arrivi alla fine della sua operazione,
		// SPRECA L'80% del TEMPO che impiega 
		
	}
	
	

	public void creaGrafo3() {
			
			this.graph = new SimpleGraph<>(DefaultEdge.class) ;
	
			
			// crea i vertici del grafo
			Graphs.addAllVertices(graph, this.getCountries()) ;
		
			// crea gli archi del grafo -- versione 3
			for(CountryPair cp : dao.listCoppieCountryAdiacenti()) {
		//	for(CountryPair cp : dao_id.listCoppieCountryAdiacenti(countryIdMap)) {     ID MAP
				graph.addEdge(cp.getC1(), cp.getC2()) ;
			}
		}

		
	public void printStats() {
			System.out.format("Grafo: Vertici %d, Archi %d\n", graph.vertexSet().size(), graph.edgeSet().size());
		}
		
	/*	
		//Per fare una VISITA (= dato un vertice se voglio trovare tutti i vertici raggiungibili da quello)
		BreadthFirstIterator <Country, DefaultEdge> bfi = new BreadthFirstIterator <> (graph, c1); // l'esplorazione parte da c1
		while(bfi.hasNext()){
			//System.out.println(bfv.next());
			visited.add(bfv.next());   // il metodo .next() restituisce un elemento di tipo vertice (qui, Country)
		}
	*/	
		
	
	
	
	// esplora l'albero (cioe la mappa alberoVisita) a partire dal country destinazione e risale fino allo stato di partenza.
	// Abbiamo deciso di esplorare l'albero analizzando ogni volta quale fosse il nodo da cui e` nato il nodo  in considerazione.
	//Infatti partendo da un nodo B viene generata una lista di nodi che seguono il nodo stesso B, ma solo un nodo A lo ha generato!
	//Quindi partendo da un nodo destinazione, questo avra` per forza uno e uno solo nodo da cui deriva (mentre non e` vero viceversa!)
	
	public List<Country> getPercorso(Country destinazione) {
		
		List<Country> percorso = new ArrayList<Country>() ;
		
		Country c = destinazione ;
		while(c!=null) {
			percorso.add(c) ;
			c = alberoVisita.get(c) ;
		}
		
		return percorso ;
	}

}
