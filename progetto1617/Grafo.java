package progetto1617;

import java.util.Iterator;
import java.util.Vector;

/*CLEANED*/

/**
 * Grafo con liste di adiacenza (sia Stringhe che numeri interi mappati)
 */
public class Grafo {
	Mappa nodiArchi;
	Vector<Vector<ArcoIntero>> listaAdInt;
	int nArchi;

	/**
	 * Costruttore vuoto
	 */

	public Grafo() {
		nodiArchi = new Mappa();
		nArchi = 0;
		listaAdInt = new Vector<Vector<ArcoIntero>>();
	}

	public Grafo(Mappa tuttinodi,int numArchi,Vector<Vector<ArcoIntero>> listaAdInt) {
		this.nodiArchi = tuttinodi;
		this.nArchi = numArchi;
		this.listaAdInt = listaAdInt;
	}
	
	/**
	 * restituisce il numero di nodi 
	 * @return il numero di nodi
	 */

	public int nodesNumber() {
		return nodiArchi.size();
	}

	/**
	 * Restituisce il numero di archi
	 * @return il numero di archi
	 */

	public int edgesNumber() {
		return nArchi;
	}
	
	/**
	 * mappa i nodi come stringa in numeri interi
	 */
	
	public void mappaNodi() {
		String[] nodi = this.nodiArchi.keySet();
		Vector<Vector<ArcoIntero>> listaInteri = new Vector<Vector<ArcoIntero>>();
		for(int i = 0; i<nodi.length;i++) {
			Vector<Arco> dascansionare = this.nodiArchi.get(nodi[i]);
			Vector<ArcoIntero> dainserire = new Vector<ArcoIntero>();
			for(int j = 0; j<dascansionare.size();j++) {
				ArcoIntero a = new ArcoIntero(indexOf(nodi,dascansionare.elementAt(j).node1),indexOf(nodi,dascansionare.elementAt(j).node2));
				dainserire.add(a);
			}
			listaInteri.add(dainserire);
		}
		this.listaAdInt=listaInteri;
	}
	
	/**
	 * ricerca lineare parola in array di parole
	 * @param parole
	 * @param parola
	 * @return
	 */
	
	private int indexOf(String[] parole, String parola) {
		int index = -1;
		for (int i=0;i<parole.length;i++) {
			if (parole[i].equals(parola)) {
				index = i;
				break;
			}
		}
		return index;
	}


	/**
	 * aggiunge un nodo al grafo con valore x se x non e' presente nel grafo, nulla altrimenti
	 * L'aggiunta di un nodo significa aggiungere la coppia (x, lista) nella HashMap
	 * dove lista e' una HashSet vuota.
	 * 
	 * @param x il nodo da aggiungere
	 */

	public void add(String x) {
		if (!nodiArchi.containsKey(x)) {
			Vector<Arco> lista = new Vector<Arco>();
			nodiArchi.put(x,lista);
		}
	}

	/**
	 * rimuove il nodo x dal grafo se x e' presente nel grafo, 
	 * altrimenti non modifica il grafo.
	 * 
	 * @param x il nodo da rimuovere
	 */

	@SuppressWarnings("rawtypes")
	public void remove(String x) {
		nodiArchi.remove(x);
	}


	/**
	 * Aggiunge l'arco al grafo se non e' presente e restituisce true, altrimenti non lo inserisce e restituisce false
	 * @param x primo nodo
	 * @param y secondo nodo
	 * @return TRUE se aggiunto, FALSE altrimenti
	 */

	public boolean add(String x, String y) {
		boolean flag = false;
		if (!nodiArchi.containsKey(x))
			add(x);
		if (!nodiArchi.containsKey(y))
			add(y);
		Arco a = new Arco(x,y);
		if(!nodiArchi.get(x).contains(a)) {
			flag = true;
			nodiArchi.get(x).add(a);
		}
		if (flag) {
			nArchi++;
		}
		return flag;
	}

	/**
	 * Aggiunge l'arco al grafo se non e' presente e restituisce true, altrimenti non lo inserisce e restituisce false
	 * @param a arco da aggiungere
	 * @return TRUE se aggiunto, FALSE altrimenti
	 */
	public boolean add(Arco a) {
		return add(a.getNode1(),a.getNode2());
	}

	/**
	 * Rimuove l'arco tra i nodi x e y se e' presente e restituisce true, altrimenti nnon fa nulla e restituisce false. 
	 * @param x primo nodo
	 * @param y secondo nodo
	 * @return TRUE se rimosso, FALSE altrimenti
	 */

	public boolean remove(String x, String y) {
		Arco a = new Arco(x,y);
		return remove(a);
	}

	/**
	 * Rimuove l'arco tra i nodi x e y se e' presente e restituisce true, altrimenti nnon fa nulla e restituisce false. 
	 * @param a l'arco da aggiungere
	 * @return TRUE se l'arco e' stato aggiunto, FALSE altrimenti
	 */


	public boolean remove(Arco a) {
		boolean flag = false;
		if (nodiArchi.containsKey(a.getNode1()) && nodiArchi.containsKey(a.getNode2())) {
			flag = ( (Vector<Arco>) nodiArchi.get(a.getNode1()) ).remove(a);
		}
		return flag;
	}

	/**
	 * Restituisce tutti gli archi presenti nel grafo
	 * @return il vettore contenente tutti gli archi presenti nel grafo
	 */
	
	public Vector<Arco> getArchi() {
		Vector<Arco> VettoreArchi = new Vector<Arco>();
		Iterator<Vector<Arco>> ItArchi = nodiArchi.valueIterator();
		while (ItArchi.hasNext()) {
			Iterator<Arco> impressive = ItArchi.next().iterator();
			while(impressive.hasNext()) {
				Arco arco = impressive.next();
				if(!arcoContains(arco, VettoreArchi)) {
					VettoreArchi.add(arco);
				}
			}
		}
		return VettoreArchi;
	}
	
	/**
	 * Restituisce il vettore contenente i nodi del grafo
	 * @return il vettore di nodi del grafo
	 */

	public String[] getNodeSet() {
		return (String[])nodiArchi.keySet();
	}

	/**
	 * calcolo indegree nodi del grafo
	 * @param nodo è il nodo di cui calcolare l'indegree
	 * @return un numero intero rappresentante l'indegree del nodo
	 */
	public int inDegree(String nodo){ //costo pari agli archi del nodo
		int counter = 0;
		Vector<Arco> archi = (Vector<Arco>)nodiArchi.get(nodo); //lineare sulle chiavi della mappa
		if(archi!=null) {
			if(archi.size()==0){
				return 0;
			}else{
				Iterator<Arco> itarco = archi.iterator();
				while(itarco.hasNext()){
					Arco x = itarco.next();
					if(x.node2.equals(nodo)){
						counter++;
					}
				}
				return counter;
			}
		}else{
			return -1;
		}
	}
	
	/**
	 * stampa il grafo
	 */
	
	@SuppressWarnings("rawtypes")
	public String toString() {
		StringBuffer out = new StringBuffer();
		String nodo;
		Arco a;
		Iterator arcoI;
		Iterator<String> nodoI = nodiArchi.keyIterator();
		while (nodoI.hasNext()) {
			arcoI = ((Vector<Arco>)nodiArchi.get( nodo = nodoI.next() )).iterator();
			out.append("Nodo " + nodo.toString() + ": ");
			while (arcoI.hasNext()) {
				a = (Arco)arcoI.next();
				out.append(a.toString()+", ");
			}
			out.append("\n");
		}
		return out.toString();
	}

	/**
	 * deep copy grafo
	 */
	
	public Grafo clone() {
		Mappa newnodi = new Mappa();
		Iterator<String> itChiavi = nodiArchi.keyIterator();
		while(itChiavi.hasNext()) {
			String chiave = itChiavi.next();
			newnodi.put(chiave, nodiArchi.get(chiave));
		}
		@SuppressWarnings("unchecked")
		Vector<Vector<ArcoIntero>> newArchi = (Vector<Vector<ArcoIntero>>) this.listaAdInt.clone();
		Grafo k = new Grafo(newnodi,this.nArchi,newArchi);
		return k;
	}
	
	/**
	 * metodo che testa il contenimento di un arco in un vettore di archi
	 * @param arco è l'arco di cui controllare il contenimento
	 * @param vettoreArchi è il vettore in cui cercare il nodo
	 * @return TRUE se vettoreArchi contiene l'arco
	 */
	
	public static boolean arcoContains(Arco arco, Vector<Arco> vettoreArchi) {
		boolean flag = false;
		Iterator<Arco> tuttiArchi = vettoreArchi.iterator();
		while(tuttiArchi.hasNext() && flag == false) {
			Arco prossimo = tuttiArchi.next();
			if(arco.equals(prossimo)) {
				flag = true;
			}
		}
		return flag;
	}
	
	/**
	 * ritorna la lista di adiacenza del nodo indicato come intero
	 * @param nodo è un numero intero che mappa un nodo
	 * @return la lista degli archi del nodo
	 */
	public Vector<ArcoIntero> getArchiInt(int nodo){
		return this.listaAdInt.get(nodo);
	}
	
	/**
	 * computa l'ingrado di tutti i nodi del grafo rappresentati come numeri interi
	 * @return l'elenco di tutti gli indegree di tutti i nodi
	 */
	public Integer[] ingradoIntero() {
		Integer[] ingradi = new Integer[this.listaAdInt.size()];
		for(int i = 0;i<this.listaAdInt.size();i++) {
			ingradi[i] = 0;
		}
		Vector<ArcoIntero> letturaArchi = new Vector<ArcoIntero>();
		for(int i = 0; i<listaAdInt.size();i++) {
			letturaArchi = listaAdInt.get(i);
			for(int k = 0; k<letturaArchi.size();k++) {
				ArcoIntero a = letturaArchi.get(k);
				ingradi[a.node2] = ingradi[a.node2]+1;
			}
		}
		return ingradi;
	}

	public static void main(String[] args) {
		//scommentare per DEBUG
		/*Arco a = new Arco();
	    Grafo g = new Grafo();
		System.out.println("numero di nodi in un grafo vuoto: " + g.nodesNumber());
	    g.add("Ciao");
	    System.out.println("numero di nodi nel grafo dopo l'aggiunta: " + g.nodesNumber());
		g.add("a","b");
	    g.add("a","c");
	    g.add("a","e");
	    g.add("c","d");
	    g.add("c","e");
	    g.add("b","d");

		System.out.println("Il grafo G e':\n" + g);
	    System.out.println("L'insieme di archi e': " + g.getArchi());
	    
	    g.mappaNodi();
	    System.out.println("mappaNodi = "+ g.listaAdInt);
	    Integer[] ingradi = g.ingradoIntero();
	    for(int i =0;i<ingradi.length;i++) {
	    		System.out.print(ingradi[i] + " ");
	    }*/
	}


}

