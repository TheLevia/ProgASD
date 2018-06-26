package progetto1617;

import java.util.Vector;
import java.util.Iterator;

public class Main {


	/**
	 * Metodo per la creazione di un grafo da un testo in input
	 * @param testo è l'array che contiene il testo da convertire in grafo
	 * @return il grafo delle parole
	 */

	public static Grafo creaGrafo(String[] testo){
		Grafo g = new Grafo();
		for(int i=0;i<testo.length;i++){
			inserisci(testo[i],g);
		}
		return g;
	}
	
	/**
	*CORREZZIONE AL PROBLEMA DELLA MANCATA VISUALIZZAZIONE DEL FILE DOT, il metodo rimuove caratteri speciali utilizzati dalla notazione dot
	*@param testo è il testo su cui fare il find&replace dei caratteri speciali
	**/

	public static String parseTxt(String testo) {
		String res = new String(testo);
		res=res.replace(",", "");
		res=res.replace(";", "");
		res=res.replace(".", "");
		res=res.replace('-', ' ');
		res=res.replace("<", "");
		res=res.replace(">", "");
		res=res.replace("{", "");
		res=res.replace("}", "");
		res=res.replace("*","");
		res=res.replace("$","");
		res=res.replace("£","");
		res=res.replace("!","");
		res=res.replace(":","");
		res=res.replace("'","");
		res=res.replace("€","");
		res=res.replace("@","");
		res=res.replace("?","");
		res=res.replace("^","");
		res=res.replace("[","");
		res=res.replace("]","");
		res=res.replace("#","");
		res=res.replace("°","");
		res=res.replace("_","");
		res=res.replace(".", "");
		return res;
	}
	
	/**
	 * inserisce una parola nel grafo nella corretta posizione
	 * @param parola è la parola da inserire nel grafo
	 */

	public static void inserisci(String parola, Grafo g){
		if(g.nodesNumber()==0){ //caso grafo vuoto
			g.add(parola);
		}else{
			//Grafo non vuoto = bordello
			String[] n = (String[])g.getNodeSet();
			//Se il grafo contiene già la parola non devo fare nulla
			if(contains(parola,n)){
				return;
			}else{
				Vector<Arco> archi = new Vector<Arco>();
				for(int i=0; i<n.length;i++){
					if(contString(parola, n[i])||contString(n[i],parola)){
						//se la parola è contenuta in un'altra presente nel grafo aggiungo un arco alla lista di archi da aggiungere
						if(contString(parola, n[i])){
							//caso 1
							Arco y = new Arco(n[i],parola);
							archi.add(y);
						}

						if(contString(n[i],parola)){
							//caso2
							Arco y = new Arco(parola,n[i]);
							archi.add(y);
						}
					}
					if(archi.isEmpty()){ //se non c'è nessun arco aggiungo comunque il nodo al grafo ma scollegato
						g.add(parola);
					}else{ //se esistono archi li itero tutti e li aggiungo
						Iterator<Arco> x = archi.iterator();
						while(x.hasNext()){
							g.add(x.next());
						}
					}
				}
			}
		}
	}
	
	/**
	 * Metodo per la creazione di un grafo da un testo in input
	 * @param testo è l'array che contiene il testo da convertire in grafo
	 * @return il grafo delle parole
	 */
	
	public static Grafo creaGrafoV(String[] testo, int[][] vettoriCarattere){
		Grafo g = new Grafo();
		
		for(int i=0;i<testo.length;i++){
			inserisciNodi(testo[i], g);
		}
		
		return g;
	}
	
	/**
	 * inserisce una parola nel grafo come un nuovo nodo
	 * @param parola è la parola da inserire nel grafo
	 * @param g è il grafo dove inserire la parola in input
	 * @param vettoreCaratteri è il vettore dei caratteri della parola in input
	 */
	
	/*USANDO EQPAR E MAGSTRING*/
	
	public static void inserisciNodi(String parola, Grafo g){
		//grafo vuoto corrisponde a inserimento immediato della parola come nodo
		if(g.nodesNumber()==0){
			g.add(parola);
		}else{
			String[] n = g.getNodeSet();
			//Se il grafo contiene già la parola non devo fare nulla
			if(contains(parola,n)){
				return;
			}else{
				//se il grafo non contiene la parola semplicemente la inserisco
				g.add(parola);
			}
		}
	}
	
	/**
	 * questo metodo si occupa di generare tutti gli archi del grafo prendendo tutte le coppie possibili di nodi
	 * @param g è il grafo su cui generare gli archi
	 */
	
	public static void generaArchi(Grafo g, char[] alfabeto){
		String[] nodi1 = (String[])g.getNodeSet();
		for(int i = 0 ; i < nodi1.length; i++){
			for(int k = 0; k < nodi1.length; k++){
				if(i!=k) {
					if(magString(vettoreCaratteri((String)nodi1[i], alfabeto), vettoreCaratteri((String)nodi1[k], alfabeto))){
						g.add(nodi1[i], nodi1[k]);
					}
					if(magString(vettoreCaratteri((String)nodi1[k], alfabeto), vettoreCaratteri((String)nodi1[i], alfabeto))){
						g.add(nodi1[k], nodi1[i]);
					}
				}
			}
		}
	}
	
	/**
	 * metodo che si occupa di controllare se la stringa x è contenuta nella stringa y
	 * @param x è la stringa da testare
	 * @param y è la stringa candidata al contenimento
	 * @return TRUE se x contenuto in y, FALSE altrimenti
	 */
	
	public static boolean contString(String x, String y){
		if(y.contains(x)){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * controllo se x<y
	 * @param x è la stringa minorata
	 * @param y è la stringa minorante
	 * @return TRUE se x<y, FALSE se x>y or x!=y
	 */
	
	public static boolean minorString(String x, String y){
		char[] xc = x.toCharArray();
		char[] yc = y.toCharArray();
		boolean res = true;
		int xlen = xc.length;
		int ylen = yc.length;
		int minlen = Math.min(xlen, ylen);
		if(x.equals(y)){
			return false;
		}else{
			for(int i=0;i<minlen;i++){
				if(Alfabeto.carattereInAscii(x)<Alfabeto.carattereInAscii(y)){
				res=false;
				}
			}
		}
		return res;
	}
	
	/**
	 * trasforma una stringa nell'array di interi contenente il numero di occorrenze di ogni lettera
	 * @param s è la stringa per riempire l'array
	 * @param alfabeto è l'array contenente l'alfabeto
	 * @return un array di interi contenente le occorrenze dei caratteri ordinati secondo l'alfabeto
	 */
	
	public static int[] vettoreCaratteri(String s, char[] alfabeto){
		char[] sChar = s.toCharArray();
		int cardAlf = alfabeto.length;
		int[] res = new int[cardAlf];
		int count = 0;
		for(int x = 0; x<cardAlf;x++){
			for(int y = 0; y<sChar.length;y++){
				if(sChar[y] == alfabeto[x]){
					count=count+1;
				}
			}
			res[x]=count;
			count=0;
		}
		return res;
	}
	
	/**
	 * è il metodo che crea una lista dei vettori di caratteri dell'intero testo
	 * @param testo è il testo in input al progetto
	 * @param alfabeto è l'array di caratteri che contiene l'alfabeto ordinato
	 * @return l'array che contiene tutti i vettori di caratteri di tutte le parole del testo (ordinate in ordine di comparizione nel testo)
	 */
	
	public static int[][] listaVettoriCaratteri(String[] testo, char[] alfabeto){
		int[][] res = new int[testo.length][alfabeto.length];
		for(int i = 0; i<testo.length;i++){
			res[i] = vettoreCaratteri(testo[i],alfabeto);
		}
		return res;
	}
	
	/**
	 * metodo che mi dice se il vettore di caratteri di 2 parole date è esattamente lo stesso
	 * @param p1 è il vettore di caratteri della prima stringa
	 * @param p2 è il vettore di caratteri della seconda stringa
	 * @return TRUE se i due vettori sono equivalenti, FALSE altrimenti
	 */
	
	public static boolean eqPar(int[] u, int[] v){
		boolean eq = true;
		for(int i = 0; i<u.length;i++){
			if(u[i]!=v[i]){
				eq=false;
			}
		}
		return eq;
	}
	
	/**
	 * metodo che mi dice se un vettore caratteri di una stringa è maggiore di quello di un'altra (PROPRIETà DI GENERAZIONE)
	 * @param u è il vettore caratteri della prima stringa
	 * @param v è il vettore caratteri della seconda stringa
	 * @return FALSE se i vettori caratteri sono equivalenti oppure se v>u; TRUE se u>v
	 */
	public static boolean magString(int[] u, int[] v){
		boolean res = true;
		if(eqPar(u,v)){
			return false;
		}
		for(int i = 0; i<u.length;i++){
			if(u[i]<v[i]){
				res=false;
			}
		}
		return res;
	}
	
	/**
	 * ritorna true se trova s nel vettore, false altrimenti
	 * @param s è la stringa da cercare nel vettore
	 * @param vettore è il vettore in cui cercare s
	 * @return TRUE se s è contenuta in vettore, FALSE altrimenti
	 */
	public static boolean contains(String s, String[] vettore) {
		boolean trovato = false;
		int size = vettore.length;
		int i = 0;
		while(i<size && trovato==false) {
			if(s.equals(vettore[i])) {
				trovato = true;
			}
			i++;
		}
		return trovato;
	}
	
	/**Metodo che lancia tutti gli algoritmo per risolvere il problema del progetto
	 * @param g è il grafo su cui eseguire gli algoritmi
	 */
	public static int soluzioneGrafo(Grafo g) {
		Coda<Integer> topologico = AlgoritmoCentrale.kahn(g);
		int length = AlgoritmoCentrale.lunghezzaMassima(topologico, g);
		return length;
	}
	
	public static void soluzioneTesto(String t) {
		String[] parole = t.split(" ");
		
		/*GENERAZIONE ALFABETO*/
		Integer[] alfabeto = Alfabeto.creaAlfabeto(parole);
		
		/*CREAZIONE GRAFO*/
		Grafo g = new Grafo();
		
		/*INSERIMENTO NODI NEL GRAFO*/
		
		for(int i = 0; i<parole.length; i++){
			inserisciNodi(parole[i], g);
		}
		/*GENERAZIONE ARCHI*/
	   
	    char[] calfabeto = Alfabeto.charalfabeto(alfabeto); 
	    generaArchi(g, calfabeto);
	    /*MAPPA NODI STRINGA IN INTERI*/
	    g.mappaNodi();
	    
	    Coda<Integer> topologico = AlgoritmoCentrale.kahn(g);
		int length = AlgoritmoCentrale.lunghezzaMassima(topologico, g);
		System.out.println(length);
		Output.StampaGrafo(g);
	}
	
	public static void soluzioneTestoTempi(String t) {
		String[] parole = t.split(" ");
		
		/*GENERAZIONE ALFABETO*/
		Integer[] alfabeto = Alfabeto.creaAlfabeto(parole);
		
		/*CREAZIONE GRAFO*/
		Grafo g = new Grafo();
		
		/*INSERIMENTO NODI NEL GRAFO*/
		for(int i = 0; i<parole.length; i++){
			inserisciNodi(parole[i], g);
		}
		/*GENERAZIONE ARCHI*/
	    char[] calfabeto = Alfabeto.charalfabeto(alfabeto); 
	    generaArchi(g, calfabeto);
	    /*MAPPA NODI STRINGHE IN INTERI*/
	    g.mappaNodi();
	    
	    Coda<Integer> topologico = AlgoritmoCentrale.kahn(g);
		int length = AlgoritmoCentrale.lunghezzaMassima(topologico, g);
	}
	
	/*conta gli archi generati da una soluzione*/
	public static int soluzioneTestoConto(String t) {
		String[] parole = t.split(" ");
		
		/*GENERAZIONE ALFABETO*/
		Integer[] alfabeto = Alfabeto.creaAlfabeto(parole);
		
		/*CREAZIONE GRAFO*/
		Grafo g = new Grafo();
		
		/*INSERIMENTO NODI NEL GRAFO*/
		
		for(int i = 0; i<parole.length; i++){
			inserisciNodi(parole[i], g);
		}
		/*GENERAZIONE ARCHI*/
	   
	    char[] calfabeto = Alfabeto.charalfabeto(alfabeto); 
	    generaArchi(g, calfabeto);
	    return g.nArchi;
	}
	
	
	public static void main(String[] args){
		
		/*DEBUG
		String parolaprova = "ciao.";
		System.out.println("ciao.replace(.,spazio) = "+parolaprova.replace('.',' '));
		
		String prova = new String("Many researchers in the computational field are focused on efficiently finding a pattern in text. We are interested in bioinformatics applications. One area to consider is computational genomics such as the genome annotation or finding patterns in genomic sequences. Although many algorithms are currently being implemented, newer ideas can be implemented to find patterns better or in simpler ways. To find the clustered-clumps in the text, we propose a new algorithm in this study. We study the LPF table computation with suffix tray data structure, which this structure includes the suffix link and the attribute m. We present the proof of the algorithm, and this solution runs in linear time");
		String[] paroleprova = prova.split(" ");
		String[] paroleprovaParsate = checkDotSyntax(paroleprova);
		for(int i = 0; i<paroleprovaParsate.length;i++) {
			System.out.println(paroleprovaParsate[i]);
		}
		*/
		
		int nparams = args.length;
		if(nparams>1) {
			System.out.println("ERRORE:troppi argomenti");
			System.out.println("sintassi corretta: java progetto \"testo\"");
			System.exit(1);
		} else if (nparams == 0) {
			System.out.println("ERRORE: nessun argomento in input");
			System.out.println("sintassi corretta: java progetto \"testo\"");
			System.exit(1);
		}
		
		String testo = parseTxt(args[0]);
		
		//String testo = "a ab b c d e f g";
		//String testo = "se sandro non si sbrigava a passare ennitor enitore gli esami serena non si gennitor rasserenava e i genitori di serena si susseguivano nel sussudiario";
		//String testo = "se nonna non era serena non si rasserenava nonno";
		
		String[] parole = testo.split(" ");
		
		//String[] paroleParsate = checkDotSyntax(parole);
		
		/*GENERAZIONE ALFABETO*/
		Integer[] alfabeto = Alfabeto.creaAlfabeto(parole);
		
		/*CREAZIONE GRAFO*/
		Grafo g = new Grafo();
		
		/*INSERIMENTO NODI NEL GRAFO*/
		
		for(int i = 0; i<parole.length; i++){
			inserisciNodi(parole[i], g);
		}
		
		/*GENERAZIONE ARCHI*/
	    
	    char[] calfabeto = Alfabeto.charalfabeto(alfabeto);
	    
	    generaArchi(g, calfabeto);
	    g.mappaNodi();
	    
	    Coda<Integer> topologico = AlgoritmoCentrale.kahn(g);
	    

	    int length = AlgoritmoCentrale.lunghezzaMassima(topologico, g);
	    System.out.println(length);
	    Output.StampaGrafo(g);
	    System.out.println();
		
	    
	}
	
}
