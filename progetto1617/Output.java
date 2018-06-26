package progetto1617;

import java.util.Iterator;
import java.util.Vector;

public class Output {

	/*CLASSE DI OUTPUT CHE SERVE A RITORNARE UN GRAFO DEFINITO DALLA CLASSE GRAFO IN FORMATO DOT*/
	
	/*NON MODIFICARE NULLA CHE FUNZIONA TUTTO PERFETTAMENTE*/
	
	/**
	 * pretty print del grafo g
	 * @param g è il grafo da stampare
	 */
	
	public static void StampaGrafo(Grafo g){
		System.out.println("digraph G_T {");
		String[] nodi = (String[])g.getNodeSet();
		for(int i = 0;i<nodi.length;i++){
			System.out.println((String) nodi[i]+" ;");
		}
		Vector<Arco> archi = g.getArchi();
		Iterator<Arco> itarchi = archi.iterator();
		while(itarchi.hasNext()){
			Arco a = itarchi.next();
			System.out.println(a.getNode1()+"->"+a.getNode2()+";");
		}
		System.out.print("}");
	}
	
}