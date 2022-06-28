package it.polito.tdp.borders.model;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;

public class Simulatore {

	//coda degli eventi
	private PriorityQueue<Event> queue;
	//parametri di simulazione
	private int numeroInizialeMigranti;
	private Country nazioneIniziale;
	//output di simulazione
	private int numeroPassi; //T del testo
	private Map<Country, Integer> persone; //per ogni stato, numero migranti stanziali alla fine della sim
	
	//stato del mondo simulato
	private Graph<Country, DefaultEdge> grafo;
	//map persone Country -> Integer per lavoro interno al programma

	
	public Simulatore(Graph<Country, DefaultEdge> grafo) {
		super();
		this.grafo = grafo;
	}
	
	public void init(Country partenza, int migranti) {
		this.nazioneIniziale = partenza;
		this.numeroInizialeMigranti = migranti;
		this.persone = new HashMap<>(); //il new garantisce l'eliminazione dei residui
		
		for(Country c : this.grafo.vertexSet()) {
			this.persone.put(c, 0);
		}
		
		this.queue = new PriorityQueue<>();
		this.queue.add(new Event(1, this.nazioneIniziale, this.numeroInizialeMigranti));
		
	}
	
	public void run() {
		while(!this.queue.isEmpty()) {
			Event e = this.queue.poll();
//			System.out.println(e);
			processEvent(e);
			
		}
	}

	private void processEvent(Event e) {
		//faccio i conti, analizzo cosa succede
		int stanziali = e.getPersone() / 2; //divisione intera
		int migranti = e.getPersone() - stanziali;
		int confinanti = this.grafo.degreeOf(e.getNazione());
		int gruppiMigranti = migranti / confinanti;
		//devo gestire i resti
		stanziali += (migranti % confinanti);
		
		//aggiorno lo stato del mondo
		this.persone.put(e.getNazione(), 
				this.persone.get(e.getNazione()) + stanziali);
		this.numeroPassi = e.getTime(); //lo aggiorno ogni volta
		
		//predispongo nuovi eventi
		if(gruppiMigranti != 0) {
			for(Country vicino : Graphs.neighborListOf(this.grafo, e.getNazione())) {
				this.queue.add(new Event(e.getTime() + 1, vicino, gruppiMigranti));
			}
		}
	}
	
	public int getNumeroPassi() {
		return numeroPassi;
	}

	public Map<Country, Integer> getPersone() {
		return persone;
	}
}
