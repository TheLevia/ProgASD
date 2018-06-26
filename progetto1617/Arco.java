package progetto1617;


/**
 * Classe per Archi come stringhe
 */

public class Arco {

	
 protected String node1, node2;

 /**
  * Costruttore senza argomenti
  */
 
 public Arco() {
   node1 = node2 = "";
 }

 /**
  * Costruttore con nodi stringa
  * @param n1 primo nodo
  * @param n1 secondo nodo
  */
 
 public Arco(String n1, String n2) {
   node1 = n1;
   node2 = n2;
 }

 /**
  * ritorna il primo nodo
  * 
  * @return il primo nodo
  */
 public String getNode1() { 
	 return node1; 
 }

 /**
  * ritorna il secondo nodo
  * 
  * @return il secondo nodo
  */
 public String getNode2() {
	 return node2;
 }

 /**
  * test di eguaglianza tra archi
  */
 
 public boolean equals(Object a) {
	  if (a instanceof Arco) {
		Arco arc = (Arco) a;
		return (node1.equals(arc.node1) && node2.equals(arc.node2));		
	  }
	  return false;
 }
 
/**
 * conversione a stringa per pretty printing
 */
 public String toString() {
   return "<"+node1.toString()+", "+node2.toString()+">";
 }
}
