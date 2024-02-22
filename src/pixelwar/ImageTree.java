package pixelwar;

import pixelwar.strategy.ImageTreeMutex;

public abstract class ImageTree{
	protected Node root;
    public int N; // diamètre de l'image
    protected static int cptIN;
    protected static int cptP;
    protected int h; // hauteur de l'arbre
    
    public ImageTree(int N) {
    	this.N = N;
    	this.h = Utils.log2(2*N*N-1)-1; 
    	if (N == 1) {
    		root = new Pixel(0);
    	}
    	else {
    		 root = new InterNode();
    		 cptIN++;
    		createTree((InterNode) root, h, 0, 1);
    	}
    }
    
    public Node getroot() {
    	return root;
    }
	
    public static void showTree(Node cur) {
 		if(cur instanceof InterNode) {
 			//System.out.println("IN");
 		} else {
 			System.out.println("P : " + ((Pixel)cur).getId());
 			return;
 		}
 		
 		ImageTreeMutex.showTree(((InterNode)cur).getLeft());
 		ImageTreeMutex.showTree(((InterNode)cur).getRight());
     	//System.out.println("nombre IN : " + cptIN);
     	//System.out.println("P : " + cptP);
 		
     }
    public abstract void createTree(InterNode parent, int depth, int tmpid, int poids);
    public abstract void putPixel(int id);
    public abstract void putTile(Tile t);
    
    /*public String toString() {
    	StringBuilder s = new StringBuilder();
    	Node s;
    	while()
    } représentation graphique des pixels */ 
}
