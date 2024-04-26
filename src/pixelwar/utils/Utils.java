package pixelwar.utils;

import java.util.Arrays;

public  class Utils {

	/* Calcule le log en base 2 de n et retourne la valeur entière inférieure (ou -1 si non applicable) */
	public static int log2int(int n) {
		if(n < 1) {
			return -1;
		}
		
		double log = (Math.log(n) / Math.log(2));
		Double logd = (Double) log;
		return logd.intValue();
	}
	
	/* Calcule le log en base 2 de n (ou -1 si non applicable) */
	public static double log2double(int n) {
		if(n < 1) {
			return -1;
		}
		return (Math.log(n) / Math.log(2));
	}
	
	
	/* Retourne 1 si n est une puissance de 2, 0 sinon */
	public static boolean isPowerOf2(int n) {
		// cas particulier
	    if(n <= 0) {
	        return false;
	    }

	    // on regarde si n est une puissance de 2
	    double h = Utils.log2double(n);
	    double h2 = Math.floor(h);
	    if(h == h2) {
	        return true;
	    }
	    return false;
	}
	
	/* retourne une chaîne de caractères contenant des statistiques sur la collection de valeurs passées en paramètre */
	public static String stats(Long[] arr) {
		Arrays.sort(arr);
		int n = arr.length;
		double q1,median,q3;
		
		/* calcul de la médiane */
        if( (n%2) != 0 ){
            median = arr[n/2];
        } else {
            median = ((double)arr[n/2] + (double)arr[(n/2) - 1]) / 2.0;
        }
        
        /* calcul des quartiles */
		if( ((n/2)%2) != 0 ){
            q1 = arr [ ((n/2)/2) ];
            q3 = arr [ (n/2) + ((n/2)/2) ];
        } else {
            q1 = ((double)arr[((n/2)/2)] + (double)arr[((n/2)/2) - 1]) / 2.0;
            if( (n%2) == 0 )
                q3 = ((double)arr[(n/2) + ((n/2)/2)] + (double)arr[(n/2) + ((n/2)/2) - 1]) / 2.0;
            else
                q3 = ((double)arr[(n/2) + ((n/2)/2)] + (double)arr[(n/2) + ((n/2)/2) + 1]) / 2.0;
        }
		return arr[0] + " " + q1 + " " + median + " " + q3 + " "+ arr[n-1];
	}

}
