package pixelwar;

public  class Utils {

	/* Calcule le log en base 2 de n et retourne la valeur entière inférieure */
	public static int log2int(int n) {
		double log = (Math.log(n) / Math.log(2));
		Double logd = (Double) log;
		return logd.intValue();
	}
	
	/* Calcule le log en base 2 de n */
	public static double log2double(int n) {
		return (Math.log(n) / Math.log(2));
	}
	
	
	/* Retourne 1 si n est une puissance de 2, 0 sinon */
	public static boolean isPowerOf2(int n) {
	    // cas particulier
	    if(n == 0) {
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

	  
	/*
	public static int inverser(int x) {
		// rajouter l'ajouter la hauteur de l'image pour la boucle
		//String stx = String.format("%32s", Integer.toBinaryString(x)).replace(" ","0");
		//System.out.println(stx);
		
		int r = 0;
		for(int cpt = 0; cpt < 32; cpt++) {
			r = r * 2;
			r = r + x%2;
			//String str = String.format("%4s", Integer.toBinaryString(r)).replace(" ","0");
			//System.out.println(str + "\n");
			x = x/2;
		}
		
		//String str = String.format("%32s", Integer.toBinaryString(r)).replace(" ","0");
		//System.out.println(str + "\n");
		return r;
	}
	*/ 
}
