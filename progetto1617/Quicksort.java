package progetto1617;



public class Quicksort {
	
	/*classe che implementa l'algoritmo di ordinamento quicksort su array di Integer*/
	
	
	static int partition(Integer arr[], int left, int right){
      int i = left, j = right;
      int tmp;
      int pivot = arr[(left + right) / 2];
      while (i <= j) {
	      while (arr[i] < pivot)
             i++;
	      while (arr[j] > pivot)
             j--;
	      if (i <= j) {
            tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
            i++;
            j--;
          }
      };
      return i;
	}

	
	static void quickSort(Integer arr[], int left, int right) {
		int index = partition(arr, left, right);
	      if (left < index - 1)
	    	  quickSort(arr, left, index - 1);
	      if (index < right)
	    	  quickSort(arr, index, right);
	}
	
	public static void main(String[] args){
	}

}
