package progetto1617;

public class RandomGenerator {
   //
   // get(): restituisce un numero compreso tra 0 e 1 (e aggiorna il seme)
   //
   public double get()
   {
      //
      // Costanti
      //
      final int a = 16087;
      final int m = 2147483647;
      final int q = 127773;
      final int r = 2836;

      //
      // Variabili
      //
      double lo, hi, test;

      hi = Math.ceil(seed / q);
      lo = seed - q * hi;
      test = a * lo - r * hi;
      if (test < 0.0) {
         seed = test + m;
      } else {
         seed = test;
      }
      return seed / m;
   }

   //
   // setSeed(s): imposta il valore del seme a s
   //
   public void setSeed(double s)
   {
      seed = s;
   }
   
   /**
    * ritorna il valore del seme
    * @return un double che rappresenta il seme
    */
   public double getSeed() 
   {
	   return this.seed;
   }

   //
   // costruttore della classe, genera un'istanza di RandomGenerator,
   // fissando il seme iniziale a s.
   //
   public RandomGenerator(double s)
   {
      seed = s;
   }
   
   /**
    * Costruttore di passaggio
    * @param r
    */
   public RandomGenerator(RandomGenerator r) {
	   this.seed = r.getSeed();
   }

   //
   // variabile che tiene memorizzato il seme
   //
   private double seed;

   //
   // esempio di uso della classe RandomGenerator,
   // stampa 10 numeri casuali compresi tra 1 e 100
   //
   public static void main(String[] args)
   {
      long n;
      //
      // crea un istanza della classe RandomGenerator
      //
      RandomGenerator r = new RandomGenerator(123456789);

      System.out.println("Il valore iniziale del seme e': " + r.getSeed());

     /* boolean uno = false;
      for (i = 0; i < 10000; i++) {
    	  	 n = Math.round((r.get()*100)-1);
         if (n == 99) {
        	 uno = true;
         }
         System.out.print(n+" ");
         if(i%25==0) {
        	 	System.out.println();
         }
      }
      System.out.println();
      System.out.println("è uscito un uno? "+ uno);*/
      
      long max = 0;
      long min = 999999999;
      for (int l = 0 ; l<10000; l++) {
    	  	n = Math.round((r.get()*100)-1);
    	  	if(n>max) {
    	  		max = n;
    	  	}
    	  	if(n<min) {
    	  		min = n;
    	  	}
      }
      System.out.println("min = "+ min+ " , max = " + max);
   }

}