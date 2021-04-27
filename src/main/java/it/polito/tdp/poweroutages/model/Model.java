package it.polito.tdp.poweroutages.model;

import java.util.List;

import it.polito.tdp.poweroutages.DAO.PowerOutageDAO;

public class Model {
	
	PowerOutageDAO podao;
	
	public Model() {
		podao = new PowerOutageDAO();
	}
	
	public List<Nerc> getNercList() {
		return podao.getNercList();
	}

	//public List<PowerOutage> trovaWorstCase(Nerc nerc, int anni, int ore) {
		
	//}
}
