package pixelwar;

public  class Utils {

	  public static int log2(int x) {
      	double log = (Math.log(x) / Math.log(2));
      	Double logd = (Double) log;
      	return logd.intValue();
	  }
	  
	  public static int inverser(int x) {
		  /* rajouter l'ajouter la hauteur de l'image pour la boucle*/
		  String stx = String.format("%32s", Integer.toBinaryString(x)).replace(" ","0");
		  System.out.println(stx);
		  int r = 0;
		  for(int cpt = 0; cpt < 32; cpt++) {
			  r = r * 2;
			  r = r + x%2;
			  //String str = String.format("%4s", Integer.toBinaryString(r)).replace(" ","0");
			  //System.out.println(str + "\n");
			  x = x/2;
		  }
		  String str = String.format("%32s", Integer.toBinaryString(r)).replace(" ","0");
		  System.out.println(str + "\n");
		  return r;
	  }
	  
	    public static Pixel findPixel(int id, ImageTree tree) {
	    	return null;
	    }

	  
	  
}
