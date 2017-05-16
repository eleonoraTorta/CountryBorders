package it.polito.tdp.country.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CountryIdMap {
	
	private Map<Integer,Country> map ;
	
	public CountryIdMap() {
		map = new HashMap<>() ;
	}
	
	public Country get(Integer ccode) {
		return map.get(ccode) ;
	}
	
	/**
	 * Check whether the {@link Country} is already contained in the <em>Identity Map</em>.
	 * If yes, it returns the stored object. If no, it adds this new object to the map.
	 * In all cases, the "canonical" object is returned.
	 * 
	 * @param country the element to be added
	 * @return the canonical reference to the object
	 */
	public Country put(Country country) {
		Country old = map.get(country.getcCode()) ; 
		if(old==null) {
			map.put(country.getcCode(), country) ;
			return country ;							//se l'oggetto NON esiste: ritorna l'oggetto Country che viene passato
		} else {										//come parametro al metodo put
			return old ;								//se l'oggetto esiste: ritorna l'oggetto vecchio
		}
	}
	
}