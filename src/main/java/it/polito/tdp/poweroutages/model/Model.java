package it.polito.tdp.poweroutages.model;

import java.util.List;

import it.polito.tdp.poweroutages.DAO.PowerOutageDAO;

public class Model {
	
	PowerOutageDAO podao;
	Integer worstPers = 0;
	Integer ore = 0;
	
	public Model() {
		podao = new PowerOutageDAO();
	}
	
	public List<Nerc> getNercList() {
		return podao.getNercList();
	}
	
	public List<PowerOutages> trovaWorstCase(Nerc n, int anniMax, int oreMax) {
		
		List<PowerOutages> allPO = this.podao.getAllPONerc(n);
		
		Ricorsione r = new Ricorsione();
		r.trovaPercorso(allPO, anniMax, oreMax);
		
		worstPers = r.getWorstPers();
		
		ore = r.calcolaOre(r.getWorstCase());
		
		return r.getWorstCase();
	}

	public Integer getWorstPers() {
		return worstPers;
	}
	
	public Integer getOre() {
		return ore;
	}

}
