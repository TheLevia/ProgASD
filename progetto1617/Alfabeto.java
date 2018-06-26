package progetto1617;

import java.util.LinkedList;

public class Alfabeto {
	
	/**
	 * dato in input un testo t
	 * @param t è il testo da cui creare l'alfabeto
	 * @return il vettore di interi contenente l'alfabeto estrapolato dal testo T
	 */
	
	public static Integer[] creaAlfabeto(String[] t){
		LinkedList<Integer> alfabetoTesto = new LinkedList<Integer>();
		for(int k = 0; k<t.length;k++){ //ciclo tutte le parole
			char[] arrayCaratteri = t[k].toCharArray();
			int lunghezzaChar = arrayCaratteri.length;
			for(int i = 0; i<lunghezzaChar; i++){
				//se il carattere è già presente non serve inserirlo
				if(!(alfabetoTesto.contains(carattereInAscii(String.valueOf(arrayCaratteri[i]))))){
					alfabetoTesto.add(carattereInAscii(Character.toString(arrayCaratteri[i])));
				}
			}
		}
		Integer[] res = new Integer[alfabetoTesto.size()];
		res = (Integer[]) alfabetoTesto.toArray(new Integer[alfabetoTesto.size()]);
		return res;
	}
	
	/**
	 * prende un carattere e restituisce l'ascii decimale
	 * @param c una strinca che contiene il carattere da convertire
	 * @return un intero che rappresenta codice ascii decimale di c
	 */
	public static int carattereInAscii(String c){
		int ascii = c.toCharArray()[0];
		return ascii;
	}
	
	/**
	 * metodo che converte l'alfabeto di codici ascii in alfabeto di caratteri
	 * @param alfabeth è l'array di interi che contiene l'alfabeto
	 * @return l'array di caratteri dell'alfabeto in input
	 */
	
	public static char[] charalfabeto(Integer[] alfabeth){
		char[] res = new char[alfabeth.length];
		for(int i = 0; i<alfabeth.length;i++){
			res[i] = Character.toChars(alfabeth[i])[0];
		}
		return res;
	}
	
	
	/**
	 * semplicemente ritorna la cardinalità dell'alfabeto
	 * @param alfabeto è l'array che contiene l'alfabeto
	 * @return la cardinalità dell'alfabeto in input
	 */
	
	public static int cardinalitaAlf(char[] alfabeto){
		return alfabeto.length;
	}
	
	public static void main (String[] args){
		//DEBUG
		/*Integer[] result = creaAlfabeto(args);
		int size = result.length;
		for(int i = 0; i<size;i++){
			System.out.println(result[i]);
		}
		Quicksort.quickSort(result, 0, size-1);
		System.out.println();
		for(int i = 0; i<size;i++){
			System.out.println(result[i]);
		}*/
	}
	
}