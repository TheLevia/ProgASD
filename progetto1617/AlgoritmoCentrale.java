package progetto1617;

import java.util.Vector;
import java.util.Iterator;

public class AlgoritmoCentrale {
	
	/*CLEANED*/
	
	/*QUESTA CLASSE CONTIENE L'IMPLEMENTAZIONE DELL'ALGORITMO ATTO A RISOLVERE IL PROBLEMA DEL PERCORSO PIù LUNGO IN UN DAG*/
	
	/**
	 * Topologiacal sort utilizzando algoritmo di Kahn con complessità O(|V|+|E|) utilizzando una coda per i nodi con inDegree=0
	 * @param g è il grafo da ordinare topologicamente
	 * @return una coda di nodi ordinata topologicamente
	 */
	public static Coda<Integer> kahn(Grafo g){
		Integer[] indegrees = g.ingradoIntero();                 //O(V+E)     calcolo grado in entrata ogni nodo O(V+E)
		Coda<Integer> Nodizero = nodizero(indegrees);     	   //O(V)       seleziona i nodi che hanno ingrado 0 e li inserisce in una coda
		Coda<Integer> Topologico = new Coda<Integer>();
		while(!Nodizero.isEmpty()) {                             //O(V) ciclo volte
			Integer nodo = Nodizero.dequeue();                         //costante   prelievo testa della coda
			Vector<ArcoIntero> archidelnodo = g.getArchiInt(nodo);      //lettura in tempo lineare della lista di adiacenza del nodo 
			Topologico.enqueue(nodo);                                  //costante   inserimento in coda al risultato
			Iterator<ArcoIntero> itArchi = archidelnodo.iterator();    
			while(itArchi.hasNext()) {                                 //ciclo tanti quanti sono gli archi del nodo eliminato
				ArcoIntero x = itArchi.next();
				int nuovoGrado = indegrees[x.node2]-1;                 //costante, diminuzione del grado in entrata di un nodo nell'array
				indegrees[x.node2] = indegrees[x.node2]-1;            
				if(nuovoGrado==0) {
					Nodizero.enqueue(x.node2);                         //costante, messa in coda dei nuovi nodi zero
				}
			}
		}
		return Topologico;
	}
	
	/**
	 * creazione della coda di nodi con archi entranti = 0
	 * @param indegrees è l'array che contiene il numero di archi entranti per ogni nodo
	 * @return una coda che contiene i nodi con indegree = 0
	 */
	
	public static Coda<Integer> nodizero(Integer[] indegrees){
		Coda<Integer> q = new Coda<Integer>();
		for(int i = 0;i<indegrees.length;i++) {
			if(indegrees[i]==0) {
				q.enqueue(i);
			}
		}
		return q;
	}
	
	/**
	 * questo metodo risolve il problema di calcolare la lunghezza del percorso massima tra due nodi nel grafo
	 * @param topologico è l'ordinamento topologico del grafo g
	 * @param g è il grafo su cui calcolare la lunghezza massima
	 * @return un numero rappresentante la lunghezza di un cammino massimo nel grafo (potrebbero essercene molti di questa lunghezza)
	 */
	public static int lunghezzaMassima(Coda<Integer> topologico, Grafo g) {
		int res = 0;
		Integer[] distanze = inizializzadistanze(g.listaAdInt.size()); // istanziazione vettore distanze O(V)
		while(!topologico.isEmpty()) {                                 //O(V)
			Integer nodo = topologico.dequeue();                       //costante
			visitaDistanze(nodo, g, distanze, -1);                     //chiamata iniziale per la procedura di visita ricorsiva
		}
		for(int k = 0; k<distanze.length;k++) {   //ricerca del massimo lineare sul numero di nodi O(|V|)
			if(distanze[k]>res) {
				res = distanze[k];
			}
		}
		return res;
	}
	
	/**
	 * questo medoto serve a effettuare la visita rispettando l'ordinamento topologico per ottenere le distanze massime
	 * @param g è il grafo 
	 * @param nodo è il nodo del grafo rappresentato dal numero intero
	 * @param distanze è il vettore di interi che contiene le distanze
	 * @param start è la distanza attuale di visita
	 */
	public static void visitaDistanze(Integer nodo, Grafo g, Integer[] distanze, int start) {
		start=start+1;
		Integer distanzaNodo = distanze[nodo];    //costante, lettura della distanza dall'array
		if(distanzaNodo<start) {      //se la distanza del nodo di partenza è minore di start significa che non devo fare nulla
			distanze[nodo] = start;
		} else if(distanzaNodo>0 && distanzaNodo>=start) {
			//se la distanza del nodo nella mappa è già maggiore di start non devi fare nulla
			//in quanto una visita partendo dal nodo sarebbe uno spreco di tempo e non ci sarebbero modifiche nelle distanze
			return;
		}
		//prendi la lista di adiacenza del NODO
		Vector<ArcoIntero> archiuscenti = g.listaAdInt.get(nodo);
		Iterator<ArcoIntero> itArchiUscenti = archiuscenti.iterator();
		Coda<Integer> nodiAdiacenti = new Coda<Integer>();
		while(itArchiUscenti.hasNext()) {       //aggiunta dei nodi adiacenti alla coda da visitare
			nodiAdiacenti.enqueue(itArchiUscenti.next().node2);
		}
		while(!nodiAdiacenti.isEmpty()) {
			visitaDistanze((Integer)nodiAdiacenti.dequeue(),g,distanze,start);
		}
	}
	
	/**
	 * inizializzazione del vettore delle distanze, ponendo a zero tutte le distanze dei nodi all'interno del vettore delle distanze
	 * @param dimensione è la dimensione che rappresenta il numero di nodi all'interno del grafo
	 * @return un vettore di interi inizializzato
	 */
	public static Integer[] inizializzadistanze (int dimensione) {
		Integer[] vettore = new Integer[dimensione];
		for(int i = 0; i<dimensione; i++) {
			vettore[i] = 0;
		}
		return vettore;
	}
	
}
