package progetto1617;

import java.util.Iterator;
import java.util.Vector;


public class Mappa{
	Vector<String> chiavi;
	Vector<Vector<Arco>> valori;
	
	/**
	 * costruttore vuoto di una mappa formata da 2 vector "sincronizzati"
	 * i valori questa volta sono a loro volta vector
	 */
	public Mappa() {
		this.chiavi = new Vector<String>();
		this.valori = new Vector<Vector<Arco>>();
	}
	
	public Mappa(Mappa m) {
		this.chiavi=m.chiavi;
		this.valori=m.valori;
	}
	
	/**
	 * ritorna la dimensione della mappa
	 * @return un intero rappresentante la dimensione della mappa
	 */
	public int size() {
		return chiavi.size();
	}
	
	/**
	 * ritorna TRUE se la mappa contiene la chiave k, FALSE altrimenti
	 * @param k è a chiave da cercare all'interno del vector delle chiavi
	 * @return TRUE se la mappa contiene chiave k, FALSE altrimenti
	 */
	public boolean containsKey(String k) {
		return chiavi.contains(k);
	}
	
	/**
	 * ritorna l'elemento mappato dalla chiave k
	 * @param k è la chiave dell'elemento mappato da ritornare
	 * @return l'elemento mappato da k se k viene trovata nelle chiavi, altrimenti ritorna NULL
	 */
	public Vector<Arco> get(String k) {   //O(|k|)
		int index = chiavi.indexOf(k);
		if(index!=-1) {
			return valori.elementAt(index);
		}else {
			return null;
		}
	}
	
	
	/**
	 * operazione opzionale di rimozione di un elemento dalla map
	 * l'operazione ritorna il valore mappato dalla chiave k se l'operazione di rimozione è andata a buon fine, null altrimenti
	 * @param k è la chiave dell'elemento da rimuovere
	 * @return l'elemento eliminato se viene trovata la chiave, NULL altrimenti
	 */
	public Vector<Arco> remove(String k) {
		int index = chiavi.indexOf(k);
		if(index!=-1) {
			Vector<Arco> res = valori.get(index);
			chiavi.remove(index);
			valori.remove(index);
			return res;
		}else {
			return null;
		}
	}
	 
	/**
	 * ritorna il vettore delle chiavi in un array
	 * @return l'array contenente tutte le chiavi
	 */
	public String[] keySet() {
		return this.chiavi.toArray(new String[this.chiavi.size()]);
	}
	
	/**
	 * ritorna un vettore con tutti i valori della mappa
	 * @return
	 */
	public Object[] valueSet() {
		return valori.toArray();
	}
	
	public Iterator<Vector<Arco>> valueIterator(){
		return this.valori.iterator();
	}
	
	public Iterator<String> keyIterator(){
		return this.chiavi.iterator();
	}
	
	/**
	 * inserisce all'interno della mappa il valore v mappato dalla chiave k, se k è già presente nelle chiavi viene semplicemente modificato il valore mappato con v
	 * @param k è la chiave dell'elemento v
	 * @param v è il valore mappato da k
	 */
	
	public void put(String k, Vector<Arco> v) {
		int index = this.chiavi.indexOf(k);
		if(index!=-1) {
			//chiave già presente
			Vector<Arco> nuoviArchi = new Vector<Arco>();
			Iterator<Arco> itArchiVecchi = this.valori.elementAt(index).iterator();
			Iterator<Arco> itArchiNuovi = v.iterator();
			while(itArchiVecchi.hasNext()) {
				nuoviArchi.add(itArchiVecchi.next());
			}
			while(itArchiNuovi.hasNext()) {
				nuoviArchi.add(itArchiNuovi.next());
			}
			valori.set(index, nuoviArchi);
		}else {
			//chiave non presente
			chiavi.add(k);
			valori.add(v);
		}
	}
	
}
