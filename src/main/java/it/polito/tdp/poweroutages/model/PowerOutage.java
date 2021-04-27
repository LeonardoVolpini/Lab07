package it.polito.tdp.poweroutages.model;

import java.sql.Date;

//import java.time.LocalDateTime;

public class PowerOutage {

	private int customersAffected;
	//private LocalDateTime inizio;
	//private LocalDateTime fine;
	private Date inizio;
	private Date fine;
	private int id;
	private Nerc nerc;
	
	public PowerOutage(int id, int customersAffected, Date inizio, Date fine, Nerc nerc) {
		this.id=id;
		this.customersAffected = customersAffected;
		this.inizio = inizio;
		this.fine = fine;
		this.nerc=nerc;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCustomersAffected() {
		return customersAffected;
	}

	public void setCustomersAffected(int customersAffected) {
		this.customersAffected = customersAffected;
	}

	public Date getInizio() {
		return inizio;
	}

	public void setInizio(Date inizio) {
		this.inizio = inizio;
	}

	public Date getFine() {
		return fine;
	}

	public void setFine(Date fine) {
		this.fine = fine;
	}

	public Nerc getNerc() {
		return nerc;
	}

	public void setNerc(Nerc nerc) {
		this.nerc = nerc;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PowerOutage other = (PowerOutage) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "NERC: "+nerc.getValue()+"; Customers afflitti: "+this.customersAffected+"; iniziato il: "+inizio+"; finito il: "+fine;
	}
	
	
}
