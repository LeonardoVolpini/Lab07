package it.polito.tdp.poweroutages.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneOffset;

public class PowerOutage {

	private int customersAffected;
	private LocalDateTime inizio;
	private LocalDateTime fine;
	private int id;
	private Nerc nerc;
	private Duration durata;
	
	public PowerOutage(int id, int customersAffected, LocalDateTime inizio, LocalDateTime fine, Nerc nerc) {
		this.id=id;
		this.customersAffected = customersAffected;
		this.inizio = inizio;
		this.fine = fine;
		this.nerc=nerc;
		this.durata = Duration.between(inizio, fine);
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

	public LocalDateTime getInizio() {
		return inizio;
	}

	public void setInizio(LocalDateTime inizio) {
		this.inizio = inizio;
	}

	public LocalDateTime getFine() {
		return fine;
	}

	public void setFine(LocalDateTime fine) {
		this.fine = fine;
	}

	public Nerc getNerc() {
		return nerc;
	}

	public void setNerc(Nerc nerc) {
		this.nerc = nerc;
	}

	public long getDurata() {
		return durata.toHours();
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
		return "iniziato il: "+inizio+"; finito il: "+fine+"; durato: "+this.getDurata()+" ore ; Customers afflitti: "+this.customersAffected+"\n";
	}

}
