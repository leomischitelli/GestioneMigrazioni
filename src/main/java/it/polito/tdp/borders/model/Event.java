package it.polito.tdp.borders.model;

public class Event implements Comparable<Event> {
	private int time;
	private Country nazione;
	private int persone;
	
	public Event(int t, Country country, int n) {
		super();
		this.time = t;
		this.nazione = country;
		this.persone = n;
	}
	
	public int getTime() {
		return time;
	}
	public void setTime(int t) {
		this.time = t;
	}
	public Country getNazione() {
		return nazione;
	}
	public void setNazione(Country country) {
		this.nazione = country;
	}
	public int getPersone() {
		return persone;
	}
	public void setPersone(int n) {
		this.persone = n;
	}

	@Override
	public int compareTo(Event o) {
		return this.time - o.time;
	}

	@Override
	public String toString() {
		return "Event [time=" + time + ", nazione=" + nazione + ", persone=" + persone + "]";
	}
	
	
	
	
	
}
