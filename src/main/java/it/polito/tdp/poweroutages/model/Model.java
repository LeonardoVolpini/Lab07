package it.polito.tdp.poweroutages.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import it.polito.tdp.poweroutages.DAO.PowerOutageDAO;

public class Model {
	
	PowerOutageDAO podao;
	private Set<PowerOutage> best;
	private List<PowerOutage> partenza;
	private int totCustomersBest;
	private long totOreGuasto;
	
	public Model() {
		podao = new PowerOutageDAO();
	}
	
	public List<Nerc> getNercList() {
		return podao.getNercList();
	}

	public List<PowerOutage> getAllPowerOutage(){
		return this.podao.getAllPowerOutage();
	}
	
	public Set<PowerOutage> trovaWorstCase(int idNerc, int anni, long ore) {
		Nerc nerc=null;
		for (Nerc n : this.getNercList())
			if (n.getId()==idNerc) {
				nerc= new Nerc(idNerc,n.getValue());
			}
		this.partenza= this.podao.getPowerOutageByNerc(nerc);
		Set<PowerOutage> parziale= new HashSet<PowerOutage>();
		this.best= new HashSet<PowerOutage>();
		this.totCustomersBest=0;
		
		ricorsione(parziale,0,ore,anni);
		return this.best;
	}
	
	private void ricorsione(Set<PowerOutage> parziale, int livello, long maxOre, int maxAnni) {
		long ore = calcolaOreStop(parziale);
		if (ore>maxOre) //ho superato le ore massime richieste
			return; 
		int customers= this.calcolaTotCustomers(parziale);
		if (customers>this.totCustomersBest) { //controllo se e' la soluzione con piu utenti colpiti
			this.best= new HashSet<>(parziale);
			this.totCustomersBest=customers;
			this.totOreGuasto = ore;
		}
		if (livello==this.partenza.size()) //non ci sono piu powerOutage da aggiungere per questo Nerc
			return;
		
		//ora faccio partire il calcolo della ricorsione:
		parziale.add(partenza.get(livello));
		if (this.diffAnniValida(parziale, maxAnni)) {
			ricorsione(parziale,livello+1,maxOre,maxAnni);
		}
		parziale.remove(partenza.get(livello)); //backtracking
		ricorsione(parziale,livello,maxOre,maxAnni); //provo anche senza questo blackout
	}
	
	private boolean diffAnniValida(Set<PowerOutage> parziale, int maxAnni) {
		int max=0;
		int min=99999999;
		for (PowerOutage p : parziale) {
			if ( p.getFine().getYear() > max) //del blackout piu recente guardo la fine
				max = p.getFine().getYear();
			if ( p.getInizio().getYear() < min) //del blackout piu vecchio guardo l'inizio
				min= p.getFine().getYear();
		}
		if ((max-min)<=maxAnni)
			return true;
		return false;
	}
	
	private long calcolaOreStop(Set<PowerOutage> parziale) {
		long tot=0;
		for (PowerOutage p : parziale) {
			tot += p.getDurata();
		}
		return tot;
	}
	
	private int calcolaTotCustomers(Set<PowerOutage> parziale) {
		int tot=0;
		for (PowerOutage p : parziale) {
			tot += p.getCustomersAffected();
		}
		return tot;
	}
	
	
}
