package progetto1617;

/**
 * copia della classe Arco, solo che usa archi interi
 */

@SuppressWarnings("rawtypes")
public class ArcoIntero {

	
 protected int node1, node2;

 /**
  * Costruttore senza argomenti
  */
 
 public ArcoIntero() {
   node1 = node2 = -1;
 }

 /**
  * Costruttore con tre argomenti
  * 
  * @param n1 primo nodo
  * @param n2 secondo nodo
  */
 
 public ArcoIntero(int n1, int n2) {
   node1 = n1;
   node2 = n2;
 }

 /**
  * ritorna il primo nodo
  * 
  * @return il primo nodo
  */
 public int getNode1() { return node1; }
 
 /**
  * ritorna il secondo nodo
  * 
  * @return il secondo nodo
  */
 public int getNode2() { return node2; }

 
 public boolean equals(Object a) {
	  if (a instanceof ArcoIntero) {
		ArcoIntero arc = (ArcoIntero) a;
		return (this.node1 == arc.node1) && (this.node2==arc.node2);		
	  }
	  return false;
 }

 public String toString() {
   return "<"+Integer.toString(node1)+", "+Integer.toString(node2)+">";
 }
}
