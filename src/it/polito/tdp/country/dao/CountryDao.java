package it.polito.tdp.country.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.country.model.Country;
import it.polito.tdp.country.model.CountryPair;

public class CountryDao {
	
	public List<Country> listCountry() {

		final String sql = "SELECT CCode, StateAbb, StateNme "+
							"FROM country "+
							"ORDER BY CCode ASC";

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			ResultSet res = st.executeQuery() ;
			
			List<Country> list = new ArrayList<>() ;
			
			while(res.next()) {
				list.add(new Country(res.getInt("CCode"), res.getString("StateAbb"), res.getString("StateNme"))) ;
			}
			
			res.close();
			conn.close();
			
			return list ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}		
	}

	
	//per versione 1
	public boolean confinanti (Country c1, Country c2){
		
		final String sql = "SELECT state1no, state2no " +
							"FROM contiguity " +
							"WHERE state1no =? AND state2no =? AND contype =1";
		try{
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setInt(1,  c1.getcCode());
			st.setInt(2, c2.getcCode());
			
			ResultSet res = st.executeQuery();
			
			boolean result = false;
			
			//esiste almeno una riga?
			if(res.next()){
				result = true;
			}
			res.close();
			conn.close();
			return result;
			
		} catch ( SQLException e){
			//TODO Auto - generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	
	//per versione2
	public List <Country> listAdiacenti(Country c){
		
		//con join
		final String sql = "SELECT country.CCcode, country.StateAbb, country.StateNme "+
							"FROM contiguity, country "+
							"WHERE contiguity.state2no = country.CCode "+   //join
							"AND contiguity.state1no = ? "+
							"AND contiguity.conttype= 1";
		try{
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setInt(1,  c.getcCode());
			
			ResultSet res = st.executeQuery();
			
			List <Country> list = new ArrayList <>();
			while(res.next()){
				list.add(new Country (res.getInt("CCode"), res.getString("StateAbb") , res.getString("StateNme")));
			}
			
			res.close();
			conn.close();
			
			return list;
		
		} catch (SQLException e){
			//TODO Auto=generated catch block
			e.printStackTrace();
			return null;
			
		}
	}
	
	
	//per versione 3
	public List<CountryPair> listCoppieCountryAdiacenti(){
		final String sql = "SELECT c1.CCcode as CCode1, c1.StateAbb as StateAbb1, c1.StateNme as StateNme1, "+
				"c2.CCcode as CCode2, c12.StateAbb as StateAbb2, c2.StateNme as StateNme2 "+
				"FROM contiguity, country c1, country c2 "+
				"WHERE contiguity.state1no = c1.CCode "+   //join
				"AND contiguity.state2no = c2.CCode "+     //join
				"AND contiguity.conttype= 1";
		try{
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			ResultSet res = st.executeQuery();
			
			List <CountryPair> list = new ArrayList <>();
			
			while(res.next()){
				list.add(new CountryPair 
						(new Country (res.getInt("CCode1"), res.getString("StateAbb1") , res.getString("StateNme1")),
						new Country (res.getInt("CCode2"), res.getString("StateAbb2") , res.getString("StateNme2"))));
			}
			
			res.close();
			conn.close();
			
			return list;
		
		} catch (SQLException e){
			//TODO Auto=generated catch block
			e.printStackTrace();
			return null;
			
		}
		
	}

}
