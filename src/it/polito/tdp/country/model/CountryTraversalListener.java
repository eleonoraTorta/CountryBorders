package it.polito.tdp.country.model;

import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.event.ConnectedComponentTraversalEvent;
import org.jgrapht.event.EdgeTraversalEvent;
import org.jgrapht.event.TraversalListener;
import org.jgrapht.event.VertexTraversalEvent;
import org.jgrapht.graph.DefaultEdge;

public class CountryTraversalListener  implements TraversalListener<Country, DefaultEdge>{
	
	private Graph<Country, DefaultEdge> graph ;
	private Map<Country,Country> map ;
	
	public CountryTraversalListener(Graph<Country, DefaultEdge> graph, Map<Country, Country> map) {
		super();
		this.graph = graph;
		this.map = map;
	}

	@Override
	public void connectedComponentFinished(ConnectedComponentTraversalEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void connectedComponentStarted(ConnectedComponentTraversalEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	//edgeTraversed indica quale arco viene attraversato in quel momento, ovvero tra quali due nodi sta passando l'iteratore.
	//Io sono interessato a scoprire il CAMMINO che viene fatto e quindi registrare da che nodo si passa , man mano.
	//Poiche il grafo non e` orientato non e` intuitivo capire quale vertice e` quello nuovo e quale e` quello nuovo.
	//Dunque posso decidere di salvare tale informazione in una mappa <CountryNuovo, CountryVecchio> che mi fornisce l'info
	//"CountryNuovo e` stato scoperto da CountryVecchio"
	
	@Override
	public void edgeTraversed(EdgeTraversalEvent<DefaultEdge> evento) {
		// evento.getEdge() = l'arco appena attraversato
		// arco: graph.edgeSource/Dest( evento.getEdge() ) ; */
		
		Country c1 = graph.getEdgeSource(evento.getEdge()) ;
		Country c2 = graph.getEdgeTarget(evento.getEdge()) ;
		
		// l'algortimo di visita puo` attraversare un arco piu` volte, dunque in questo caso entrambi i nodi sono gia stati visti
		// qui gestisco che siamo in questo caso non deve essere fatto nulla per questo arco
		if(map.containsKey(c1) && map.containsKey(c2))
			return ;
		
		if( !map.containsKey(c1) ) {
			// c1 è quello nuovo
			map.put(c1,  c2) ;
		} else {
			// c2 è quello nuovo
			map.put(c2,  c1) ;
		}
	}

	@Override
	public void vertexFinished(VertexTraversalEvent<Country> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void vertexTraversed(VertexTraversalEvent<Country> arg0) {
		// TODO Auto-generated method stub
		
	}

}
