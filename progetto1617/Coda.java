package progetto1617;
import java.util.NoSuchElementException;
import java.util.Vector;

/**
	implementazione coda con vector
 */
public class Coda<E> {
  
	Vector<E> v;
 
    /**
     * costruttore vuoto della coda
     */
    public Coda() {
        this.v = new Vector<E>();
    }

    /**
     * Inserisce un oggetto in fondo alla coda.
     * @param x  l'oggetto da inserire.
     * @return   l'oggetto inserito.
     * @exception IllegalArgumentException se l'argomento passato
     *            &egrave; <code>null</code>.  
     */
    public E enqueue(E x) {
	if (x == null)
	    throw new IllegalArgumentException();
         v.addElement(x);
         return x;
    }

    /**
     * ritorna l'oggetto contenuto nella testa della coda e lo rimuove (shifta gli elementi del vector automaticamente con il remove)
     * @return   l'oggetto presente in testa alla coda.
     * @exception EmptyQueueException eccezzione generata nel caso la coda sia vuota
     */
    public E dequeue() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }
        return v.remove(0);
    }
    
    /**
     * controllo sulla coda vuota
     * @return TRUE se la coda è vuota, FALSE altrimenti.
     */
    public boolean isEmpty() {
        return v.isEmpty();
    }
    
    /**
     * metodo che effettua un pretty print della coda 
     */
   
    public void print() {
    	System.out.print("[");
    	for(int i = 0; i<v.size();i++) {
    		System.out.print(v.get(i));
    		if(i!=v.size()-1) {
    			System.out.print(", ");
    		}
    	}
    	System.out.print("]");
    }
}
