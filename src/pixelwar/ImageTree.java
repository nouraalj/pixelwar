package pixelwar;

import pixelwar.strategy.ImageTreeMutex;

public abstract class ImageTree{
	protected Node root;
    public static int N; // diamètre de l'image
    protected static int cptIN;
    protected static int cptP;
    protected int h; // hauteur de l'arbre
    protected Matrix matrix = null;
    public ImageTree(int N) {
    	this.N = N;
    	this.h = Utils.log2(2*N*N-1)-1; 
    	System.out.println(h);
    	if (N == 1) {
    		root = createPixel(0, 0, 0);
    	}
    	else {
    		 root = createInterNode();
    		 cptIN++;
    		 //xmin, ymin, xmax, ymax
    		createTree((InterNode) root, h, 0, 1, 1, N, 1, N);
    	}
    }
    
    public Node getroot() {
    	return root;
    }
    public void createTree(InterNode parent, int depth, int tmpid, int poids, int xmin, int xmax, int ymin, int ymax) {
    	if(depth != 0) {
    		InterNode right = createInterNode();
    		InterNode left = new InterNode();
    		parent.set(left, right);
    		System.out.println("depth : " + depth + " id du pixel : " + tmpid + " , xmin : " + xmin + " , xmax :" + xmax + " , ymin : " + ymin + " , ymax :" + ymax);
    		// on modifie ymax et ymin
    		
    		if (depth%2 == 1) {
    			// on crée le sous-arbre droit
    		
    			createTree(right, depth - 1, (tmpid | poids), poids << 1, xmin, xmax, ymin, ((ymax/2)));
    			//on crée le sous-arbre gauche
    			createTree(left, depth - 1, tmpid, poids << 1, xmin, xmax , (ymax/2)+1, ymax);

    		} else {
    			// si profondeur impair on modifie xmax et xmin
    			
    			// on crée le sous-arbre droit
    			createTree(right, depth - 1, (tmpid | poids), poids << 1, (xmax/2)+1, xmax, ymin, ymax);
    			
    			//on crée le sous-arbre gauche
    			createTree(left, depth - 1, tmpid, poids << 1, xmin, ((xmax/2)) , ymin, ymax);
    			
    		}
    			
    	} else {
    		
    		if (h%2 == 0) {
    			// on crée le sous-arbre droit
        		Pixel pr = createPixel((tmpid | poids), xmin-1, ymin-1);
        		System.out.println("pixel droit " + " d' id :" + pr.getId() + ", x : " + pr.getX() + " y : " + pr.getY());

        		Pixel pl = createPixel((tmpid), xmin-1, (ymax/2));
        		System.out.println("pixel gauche " + " d' id :" + pl.getId() + ", x : " + pr.getX() + " y : " + pr.getY());
        		parent.setPixel(pl, pr);


    		} else {
    			System.out.println("depth : " + depth + " id du pixel : " + tmpid + " , xmin : " + xmin + " , xmax :" + xmax + " , ymin : " + ymin + " , ymax :" + ymax);
    			// si profondeur impair on modifie xmax et xmin
        		Pixel pr = createPixel((tmpid | poids), xmax-1, ymin-1);
        		System.out.println("pixel droit " + " d' id :" + pr.getId() + ", x : " + pr.getX() + " y : " + pr.getY());

    			Pixel pl = createPixel((tmpid), xmin-1, ymin-1);
        		System.out.println("pixel gauche " + " d' id :" + pl.getId() + ", x : " + pr.getX() + " y : " + pr.getY());
        		parent.setPixel(pl, pr);

    		}
    	}
    }
    
    public Matrix createMatrix() {
    	matrix = new Matrix(N);
    	for(int i=0; i< N*N; i++) {
    		System.out.println(" i : "+i);
    		matrix.setPixel(Utils.findPixel(i, this));
    	}
    	return matrix;
    }
    
    public void showMatrix() {
    	Pixel[][] img = this.matrix.getImg();
    	for(int i = 0; i < N; i++) {
    		for(int j = 0; j < N; j++) {
    			if (img[i][j] != null) {
    				System.out.println(img[i][j].getX() + " " + img[i][j].getY() + " " + img[i][j].getId() );
    			} else {
    				System.out.println("Pixel nul");
    			}
    		}
    		System.out.println();
    	}
    	
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
   
    
    
    
    // abstract method
    public abstract Pixel createPixel(int id, int x, int y);
    public abstract InterNode createInterNode();

    public abstract void putPixel(int id);
    public abstract void putTile(Tile t);
    
    
    /*public String toString() {
    	StringBuilder s = new StringBuilder();
    	Node s;
    	while()
    } représentation graphique des pixels */ 
}
