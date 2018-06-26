package progetto1617;

import java.lang.Integer;
import java.util.Vector;
import java.io.*;



/**
 * Obiettivo, creare una classe che crei un grafo fittizio solo per il testing del tempo di esecuzione
 */

public class TempiDNA{

	public static RandomGenerator rg = new RandomGenerator(123456789);
	
	
	/*SEZIONE ALGORITMI GENERAZIONE CASUALE*/
	
	/**
	 * metodo che genera un grafo DAG casuale con numero di nodi e archi prefissato
	 * VINCOLO: il numero massimo di archi in un DAG è (n*(n-1))/2
	 * @param rg è il generatore di numeri pseudocasuali
	 * @param numnodi è il numero di nodi da generare sul grafo
	 * @param numarchi è il numero di archi casuali da generare NON DEVE VIOLARE IL VINCOLO
	 * @return un grafo diretto aciclico
	 */
	
	public static Grafo dagCasualeArchi(RandomGenerator rg, int numnodi, int numarchi) {
		if(numarchi>((numnodi*(numnodi-1))/2)) {
			//ERRORE, non esiste un dag con tutti quegli archi
			System.out.println("ERRORE:impossibile generare un DAG con tutti quegli archi");
			System.out.println("numero massimo di archi per " + numnodi + " nodi è :" + ((numnodi*(numnodi-1))/2));
			System.exit(1);
		}
		Grafo g = new Grafo();
		for(int i = 0; i<numnodi;i++) {
			g.add(Integer.toString(i));
		}
		long rgn;
		int i = 0;
		int k = 0;
		int place = 0;
		while(g.nArchi<numarchi) {
			for(k=0;k<place;k++) {
				rgn = Math.round((rg.get()*100)-1);
				if(rgn<=2) {
					g.add(Integer.toString(i),Integer.toString(k));
					if(g.nArchi==numarchi) {
						break;
					}
				}
			}
			i++;
			place++;
			if(i>=numnodi) {
				i=0;
				place=0;
			}
		}
		return g;
	}
	
	/**
	 * generazione di numseq sequenze di dna casuali di lunghezza compresa tra 1 e 25
	 * @param numseq è il numero di sequenze di DNA da generare 
	 * @return una Stringa come testo di sequenze generate
	 */
	public static String sequenzeDNA(RandomGenerator rg ,int numseq) {
		Vector<Character> total = new Vector<Character>(10);
		int numero = 0;
		for(int i = 0; i<numseq;i++) {
			long lunghezzaseq = Math.round((rg.get()*25)); //sequenze DNA lunghe tra 1 e 25		
			for(int k = 0;k<lunghezzaseq;k++) {
				long rn = Math.round((rg.get()*100)-1);
				if(rn>0 && rn<=25) {
					total.add('A');
				}else if(rn >25 && rn <=50) {
					total.add('C');
				}else if(rn >50 && rn<=75) {
					total.add('T');
				} else {
					total.add('G');
				}
			}
			total.add(' ');
			numero++;
		}
		// converto il vector in una stringa
		int dnaSize = total.size();
		char[] reschar = new char[dnaSize];
		for(int i = 0; i<dnaSize;i++) {
			reschar[i] = total.get(i);
		}
		return String.valueOf(reschar);
	}
	
	/*FINE GENERAZIONE CASUALE*/
	
	
	/*ALGORITMI DISPENSE PER IL CALCOLO DEI TEMPI*/
	/*GRAFI CON ARCHI E NODI DETERMINATI*/
	
	private static int calcolaRipLordo(int nodi, int archi, long tMin) {
		long t0=0;
		long t1=0;
		int rip=1;
		while(t1 - t0 <= tMin) {
			rip = rip * 2;  //rip esponenziale
			t0 = System.currentTimeMillis();
			for (int i = 0; i < rip; i++) {
				Grafo g = prepara(rg, nodi, archi);
				int k = Main.soluzioneGrafo(g); 
			}
			t1 = System.currentTimeMillis();
		}
		//ricerca esatta del numero di ripetizioni per bisezione
		//approssimazione a 5 cicli
		int max = rip;
		int min = rip/2;
		int cicliErrati = 5;
		while(max-min >= cicliErrati){
			rip = (max + min)/2;
			t0 = System.currentTimeMillis();
			for (int i = 0; i < rip; i++) {
				Grafo g = prepara(rg, nodi,archi);
				int k = Main.soluzioneGrafo(g); 
			}
			t1 = System.currentTimeMillis();
			if(t1 - t0 <= tMin) {
				min = rip;
			} else {
				max = rip;
			}
		}
		return max;
	}

	/**
	 *questa funzione calcola il numero di ripetiozioni per la preparazione dell'input (tara) o per l'algoritmo + tara (lordo)
	 *    @param n numero dei nodi del grafo
	 *    @param p probabilità che tra due nodi del grafo diretto acicico g esista un arco (da 1 a 100)
	 *    @param tMin minimo errore sulla rivelazione del tempo di esecuzione
	 *    @param flag valore booleano che indica se calcolare ripetizioni della tara (valore false)  o del lordo (valore true)
	 */
	private static int calcolaRipTara(int nodi, int archi, long tMin) {
		long t0=0;
		long t1=0;
		int rip=1;
		while(t1 - t0 <= tMin) {
			rip = rip * 2;  //rip esponenziale
			t0 = System.currentTimeMillis();
			for (int i = 0; i < rip; i++) {
				Grafo g = prepara(rg, nodi, archi);
			}
			t1 = System.currentTimeMillis();
		}
		//ricerca esatta del numero di ripetizioni per bisezione
		//approssimazione a 5 cicli
		int max = rip;
		int min = rip/2;
		int cicliErrati = 5;
		while(max - min >= cicliErrati){
			rip = (max + min)/2;
			t0 = System.currentTimeMillis();
			for (int i = 0; i < rip; i++) {
				Grafo g = prepara(rg, nodi,archi);
			}
			t1 = System.currentTimeMillis();
			if(t1 - t0 <= tMin) {
				min = rip;
			} else {
				max = rip;
			}
		}
		return max;
	}
	

	//tempoMedioNetto(g, d, p, tMin); con d = nodi e p = archi
	
	private static double tempoMedioNetto(int nodi, int archi, long tMin){
		int ripTara=calcolaRipTara(nodi, archi, tMin); //calcolo delle ripetizioni della tara
		int ripLordo=calcolaRipLordo(nodi, archi, tMin); //calcolo delle ripetizioni per il lordo
		long t0, t1;
		t0 = System.currentTimeMillis();
		/*TARA*/
		for(int i=0; i<ripTara; i++){
			Grafo g = prepara(rg,nodi,archi);
		}
		t1 = System.currentTimeMillis();
		long tTara = t1 - t0; // tempo totale di esecuzione della tara
		t0 = System.currentTimeMillis();
		/*LORDO*/
		for(int i=0; i<ripLordo; i++){
			Grafo g = prepara(rg, nodi, archi);
			int k = Main.soluzioneGrafo(g);
		}
		t1 = System.currentTimeMillis();
		long tLordo = t1 - t0;  // tempo totale di esecuzione del lordo
		long tMed = (tLordo/ripLordo)-(tTara/ripTara); // tempo medio di esecuzione
		return tMed;
	}
	
	/**
	 * implementazione dell'algoritmo 9 delle dispense per il calcolo del tempo di esecuzione medio con errore minore di delta
	 * @param d numero di nodi
	 * @param p numero di archi
	 * @param c cicli massimi per il calcolo del tempo medio
	 * @param tMin tempo minimo di rilevazione
	 * @param delta coefficiente massimo di errore accettato
	 */
	
	private static double[] misurazione (int nodi,int archi,int c, double za, long tMin, double delta) {
		Grafo g = new Grafo();
		double t=0;
		double sum2=0;
		int cn=0;
		double e;
		double s;
		double dlt;
		double[] varianza = new double[2];
		do {
			for (int i = 0; i < c; i++) {
				//misurazione del tempo medio in un DAG di d nodi e p archi
				double m = tempoMedioNetto(nodi, archi, tMin);
				//System.out.println("risultato di tempomedionetto2 = " + m);
				t = t + m;
				sum2 = sum2 + (m * m);
			}
			cn = cn + c;
			e = t / cn;
			s = Math.sqrt(sum2/cn - e*e);
			//System.out.println("s = "+ s + ", cn = " + cn + ", e = " + e +", t = " + t + ", sum2 = " + sum2);
			dlt = (1 / Math.sqrt(cn)) * za * s;
			System.out.println("dlt<delta? => "+dlt+" < "+delta );
		}
		while(!(dlt<delta));
		varianza[0]= e;
		varianza[1]= dlt;
		return varianza;
	}
	
	/*FINE ARCHI E NODI DETERMINATI*/
	
	
	/*CALCOLO DEI TEMPI SEQUENZE DNA CASUALI*/
	
	private static double[] misurazioneDNA (int numseq,int c, double za, long tMin, double delta) {
		Grafo g = new Grafo();
		double t=0;
		double sum2=0;
		int cn=0;
		double e;
		double s;
		double dlt;
		double[] varianza = new double[2];
		do {
			for (int i = 0; i < c; i++) {
				//misurazione del tempo medio in un DAG di d nodi e p archi
				double m = tempoMedioNettoDNA(numseq, tMin);
				//System.out.println("risultato di tempomedionetto2 = " + m);
				t = t + m;
				sum2 = sum2 + (m * m);
			}
			cn = cn + c;
			e = t / cn;
			s = Math.sqrt(sum2/cn - e*e);
			//System.out.println("s = "+ s + ", cn = " + cn + ", e = " + e +", t = " + t + ", sum2 = " + sum2);
			dlt = (1 / Math.sqrt(cn)) * za * s;
			System.out.println("dlt<delta? => "+dlt+" < "+delta );
		}
		while(!(dlt<delta));
		varianza[0]= e;
		varianza[1]= dlt;
		return varianza;
	}
	
	
	private static int calcolaRipLordoDNA(int numseq, long tMin) {
		long t0=0;
		long t1=0;
		int rip=1;
		while(t1 - t0 <= tMin) {
			rip = rip * 2;  //rip esponenziale
			t0 = System.currentTimeMillis();
			for (int i = 0; i < rip; i++) {
				String s = preparaDNA(rg, numseq);
				Main.soluzioneTestoTempi(s); 
			}
			t1 = System.currentTimeMillis();
		}
		//ricerca esatta del numero di ripetizioni per bisezione
		//approssimazione a 5 cicli
		int max = rip;
		int min = rip/2;
		int cicliErrati = 5;
		while(max-min >= cicliErrati){
			rip = (max + min)/2;
			t0 = System.currentTimeMillis();
			for (int i = 0; i < rip; i++) {
				String s = preparaDNA(rg, numseq);
				Main.soluzioneTestoTempi(s); 
			}
			t1 = System.currentTimeMillis();
			if(t1 - t0 <= tMin) {
				min = rip;
			} else {
				max = rip;
			}
		}
		return max;
	}

	/**
	 *questa funzione calcola il numero di ripetiozioni per la preparazione dell'input (tara) o per l'algoritmo + tara (lordo)
	 *    @param n numero dei nodi del grafo
	 *    @param p probabilità che tra due nodi del grafo diretto acicico g esista un arco (da 1 a 100)
	 *    @param tMin minimo errore sulla rivelazione del tempo di esecuzione
	 *    @param flag valore booleano che indica se calcolare ripetizioni della tara (valore false)  o del lordo (valore true)
	 */
	private static int calcolaRipTaraDNA(int numseq, long tMin) {
		long t0=0;
		long t1=0;
		int rip=1;
		while(t1 - t0 <= tMin) {
			rip = rip * 2;  //rip esponenziale
			t0 = System.currentTimeMillis();
			for (int i = 0; i < rip; i++) {
				String s = preparaDNA(rg, numseq);
			}
			t1 = System.currentTimeMillis();
		}
		//ricerca esatta del numero di ripetizioni per bisezione
		//approssimazione a 5 cicli
		int max = rip;
		int min = rip/2;
		int cicliErrati = 5;
		while(max - min >= cicliErrati){
			rip = (max + min)/2;
			t0 = System.currentTimeMillis();
			for (int i = 0; i < rip; i++) {
				String s = preparaDNA(rg, numseq);
			}
			t1 = System.currentTimeMillis();
			if(t1 - t0 <= tMin) {
				min = rip;
			} else {
				max = rip;
			}
		}
		return max;
	}
	
	private static double tempoMedioNettoDNA(int numseq, long tMin){
		int ripTara=calcolaRipTaraDNA(numseq, tMin); //calcolo delle ripetizioni della tara
		int ripLordo=calcolaRipLordoDNA(numseq, tMin); //calcolo delle ripetizioni per il lordo
		long t0, t1;
		t0 = System.currentTimeMillis();
		/*TARA*/
		for(int i=0; i<ripTara; i++){
			String s = preparaDNA(rg, numseq);
		}
		t1 = System.currentTimeMillis();
		long tTara = t1 - t0; // tempo totale di esecuzione della tara
		t0 = System.currentTimeMillis();
		/*LORDO*/
		for(int i=0; i<ripLordo; i++){
			String s = preparaDNA(rg, numseq);
			Main.soluzioneTestoTempi(s); 
		}
		t1 = System.currentTimeMillis();
		long tLordo = t1 - t0;  // tempo totale di esecuzione del lordo
		long tMed = (tLordo/ripLordo)-(tTara/ripTara); // tempo medio di esecuzione
		return tMed;
	}
	
	
	
	/*SEZIONE DELLE FUNZIONI PREPARA*/
	
	public static Grafo prepara(RandomGenerator rg, int nodi, int archi) {
		Grafo g = new Grafo();
		g= dagCasualeArchi(rg,nodi,archi);
		g.mappaNodi();
		return g;
	}
	
	public static String preparaDNA(RandomGenerator rg, int sequenze) {
		String DNAgenerato = sequenzeDNA(rg,sequenze);
		return DNAgenerato;
		
		//String[] parole = DNAgenerato.split(" ");
		
		/*GENERAZIONE ALFABETO*/
		//Integer[] alfabeto = Alfabeto.creaAlfabeto(parole);
		
		/*CREAZIONE GRAFO*/
		//Grafo l = new Grafo();
		
		/*INSERIMENTO NODI NEL GRAFO*/
		
		/*for(int i = 0; i<parole.length; i++){
			CreazioneGrafo.inserisciNodi(parole[i], l);
		}*/
		/*GENERAZIONE ARCHI*/
	   
	    //char[] calfabeto = Alfabeto.charalfabeto(alfabeto); 
	    //CreazioneGrafo.generaArchi(l, calfabeto);
	    
	    /*genera la mappa interi per i nodi*/
	    //l.mappaNodi();
	    //return l;
	}
	
	/*FINE SEZIONE PREPARA*/
	
	/**
	 *Metodo principale della classe TimeAnalysis 
	 *@param args vettore di stringhe REQUIRE: 1^ argomento Un intero che indica il numero di nodi del grafo da testare, 2^ argomento: un valore float che indica il grado di connessione del grafo compreso tra 0 e 1
	 */
	public static void main(String[] args){
		/*FORSE ELIMINA*/
		/*if(args.length == 0) {
			System.out.println("argomenti errati");
			System.out.println("SINTASSI: java CalcoloDeiTempi NumNodi ProbabilitàConnessione");
			System.exit(1);
		}	*/	
		
		
		/*GRAFO CON NODI E ARCHI DETERMINATI*/
		
		/*long t0, t1, gran, tMin;
		double [] risultato = new double [2];
		double err = 0.05; //coefficiente alfa
		
		//GRANULARITà
		t0=System.currentTimeMillis();
		t1=System.currentTimeMillis();
		while(t0==t1){
		    t1=System.currentTimeMillis();
		}
		gran = t1-t0;
		
		tMin= Math.round(gran/err); //tempo minimo da misurare nel calcolo delle ripetizioni (per errore di misurazione)
		
		System.out.println("Granularità = " + gran + " , tMin = "+ tMin);
		
		int nodi = 1000;
		int archi = 500;
		for(int i = 0; i<14;i++) {
			risultato = misurazione(nodi,archi, 5 , 1.96, tMin, gran);
			System.out.println("numero archi= " + archi +", Nodi= " + nodi + " , tMed = " + risultato[0] + ", Errore = " + risultato[1]);
			nodi=nodi+1000;
			//archi = archi + 1000;          
			//archi = ((nodi*(nodi-1))/2);   DAG pieno al 100%
		}*/
		
		/*Grafo g = prepara(rg,10000,10000);
		System.out.println("soluzione = "+CreazioneGrafo.soluzioneGrafo(g));*/
		/*FINE ARCHI E NODI DETERMINATI*/

		
		/*TEMPO SEQUENZE DNA*/

		long t0, t1, gran, tMin;
		double [] risultato = new double [2];
		double err = 0.05; //coefficiente alfa
		
		//GRANULARITà
		t0=System.currentTimeMillis();
		t1=System.currentTimeMillis();
		while(t0==t1){
		    t1=System.currentTimeMillis();
		}
		gran = t1-t0;
		
		tMin= Math.round(gran/err); //tempo minimo da misurare nel calcolo delle ripetizioni (per errore di misurazione)
		
		System.out.println("Granularità = " + gran + " , tMin = "+ tMin);
		
		int numerosequenze = 50;
		for(int i = 0; i<2;i++) {
			risultato = misurazioneDNA(numerosequenze, 5 , 1.96, tMin, gran);
			System.out.println("numero sequenze= " + numerosequenze + " , tMed = " + risultato[0] + ", Errore = " + risultato[1]);
			numerosequenze = numerosequenze+50;
			/*for(int k = 0; k<10;k++) {
				String s = sequenzeDNA(rg,numerosequenze);
				int archi = CreazioneGrafo.soluzioneTestoConto(s);
				System.out.println("con " + numerosequenze + " sequenze sono generati " + archi + " archi");
			}
			
			numerosequenze = numerosequenze+50;*/
			//archi = archi + 1000;          
			//archi = ((nodi*(nodi-1))/2);   DAG pieno al 100%
		}
		
		/*FINE TEMPO SEQUENZE DNA*/
		
		/*int n = 400;
		int a = ((n*(n-1))/2);
		Grafo g = dagCasualeArchi(rg, n, a);
		g.mappaNodi();
		CreazioneGrafo.soluzioneGrafo(g); 
		System.out.println("done");*/
	}
}
