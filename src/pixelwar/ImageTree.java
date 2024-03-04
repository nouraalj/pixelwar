package pixelwar;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public abstract class ImageTree{
	protected Node root; // racine de l'arbre
    protected int N; // dimension du côté de l'image
    protected int h; // hauteur de l'arbre
    protected Matrix matrix = null; // matrice de représentation de l'image
    
    public ImageTree(int N) {
    	if(N < 1 || Utils.isPowerOf2(N) == false) {
    		throw new IllegalArgumentException("La dimension du côté de l'image doit être une puissance de 2 strictement positive");
    	}
    	this.N = N;
    	this.h = Utils.log2int(2*N*N-1);
    	
    	if (N == 1) {
    		root = createPixel(0, 0, 0);
    	}
    	else {
    		root = createInterNode();
    		//System.out.println("HAUTEUR : " + h);
    		//  paramètres : racine, hauteur, tmpid, poids, xmin, ymin, xmax, ymax
    		createTree((InterNode) root, h, 0, 1, 1, N, 1, N);
    	}
    }
    
    
    /* Retourne la racine de l'arbre */
    public Node getroot() {
    	return root;
    }
    
    /* Retourne la dimension du côté de l'image */
    public int getN() {
    	return this.N;
    }
    
    
    /* Crée l'arbre de hauteur depth enraciné sous le noeud parent */
    private void createTree(InterNode parent, int depth, int tmpid, int poids, int xmin, int xmax, int ymin, int ymax) {
    	// cas récursif
    	if(depth != 1) {
    		InterNode right = createInterNode();
    		InterNode left = createInterNode();
    		parent.set(left, right);
    		//System.out.println("depth : " + depth + " id du pixel : " + tmpid + " , xmin : " + xmin + " , xmax :" + xmax + " , ymin : " + ymin + " , ymax :" + ymax);
    		
    		// si profondeur impaire on modifie ymax et ymin
    		if (depth%2 == 1) {
    			// on crée le sous-arbre droit
    			createTree(right, depth - 1, (tmpid | poids), poids << 1, xmin, xmax, ymin, ymax-((ymax-ymin+1)/2));
    			//on crée le sous-arbre gauche
    			createTree(left, depth - 1, tmpid, poids << 1, xmin, xmax , ymin+((ymax-ymin+1)/2), ymax);

    		}
    		// si profondeur paire on modifie xmax et xmin
    		else {
    			// on crée le sous-arbre droit
    			createTree(right, depth - 1, (tmpid | poids), poids << 1, xmin+((xmax-xmin+1)/2), xmax, ymin, ymax);
    			//on crée le sous-arbre gauche
    			createTree(left, depth - 1, tmpid, poids << 1, xmin, xmax-((xmax-xmin+1)/2), ymin, ymax);
    		}	
    	}
    	// cas terminal
    	else {    		
    		Pixel pr = createPixel((tmpid | poids), xmax-1, ymin-1);
    		Pixel pl = createPixel((tmpid), xmin-1, ymax-1);
    		parent.setPixel(pl, pr);
    		
    		//System.out.println("depth : " + depth + " id du pixel : " + tmpid + " , xmin : " + xmin + " , xmax :" + xmax + " , ymin : " + ymin + " , ymax :" + ymax);
    		//System.out.println("pixel droit " + " d' id :" + pr.getId() + ", x : " + pr.getX() + " y : " + pr.getY());
    		//System.out.println("pixel gauche " + " d' id :" + pl.getId() + ", x : " + pl.getX() + " y : " + pl.getY());   		
    	}
    }
    
    
    /* Retrouve un pixel dans l'arbre à partir de son identifiant */
	public Pixel findPixel(int id) {
		if(this.N == 1) {
			return (Pixel)this.root;
		}
		
		Node cur = this.root;
		
		while(!cur.hasPixel()) {
			if ((id %2) == 0) {
				cur = cur.getLeft();
			} else {
				cur = cur.getRight();
			}
			id = id >> 1;
		}
		// pixel :
		if ((id%2) == 0) {
			cur = cur.getLeft();            
		} else {
			cur = cur.getRight();            
		}
		return (Pixel)cur;
	}
	
	
	/* Retrouve un pixel dans l'arbre à partir de ses coordonnées */
	public Pixel findPixel(int x, int y) {
		if(this.N == 1) {
			return (Pixel)this.root;
		}
		
		Node parent = this.root;
		Pixel p = null;
		int depth = this.h;
		int xmin = 1;
		int xmax = this.N;
		int ymin = 1;
		int ymax = this.N;
		
		// cas récursif
    	while(depth != 1) {
    		// si profondeur impaire on regarde les y
    		if (depth%2 == 1) {
    			if((y+1) >= ymin && (y+1) <= ymax-((ymax-ymin+1)/2)) {
    				parent = parent.getRight();
    				ymax = ymax-((ymax-ymin+1)/2);
    			} else {
    				parent = parent.getLeft();
    				ymin = ymin+((ymax-ymin+1)/2);
    			}
    		}
    		// si profondeur paire on regarde les x
    		else {
    			if((x+1) >= xmin+((xmax-xmin+1)/2) && (x+1) <= xmax) {
    				parent = parent.getRight();
    				xmin = xmin+((xmax-xmin+1)/2);
    			} else {
    				parent = parent.getLeft();
    				xmax = xmax-((xmax-xmin+1)/2);
    			}
    		}
    		depth--;
    	}
    	// cas terminal : choisir le pixel
		if(((Pixel)parent.getRight()).sameCoordinates(x, y)) {
			p = (Pixel)parent.getRight();
		}
		else if(((Pixel)parent.getLeft()).sameCoordinates(x, y)){
			p = (Pixel)parent.getLeft();
		}
		
		return p;
	}
    
    
    /* Crée la matrice associée à l'arbre */
    public Matrix createMatrix() {
    	this.matrix = new Matrix(N);
    	
    	for(int i=0; i< N*N; i++) {
    		matrix.setPixel(findPixel(i));
    	}
    	return matrix;
    }
    
    
    /* Affiche la matrice associée à l'arbre */
    public void showMatrix() {
    	if(this.matrix == null) {
    		createMatrix();    		
    	}
    	
    	Pixel[][] img = this.matrix.getImg();
    	for(int i = 0; i < N; i++) {
    		for(int j = 0; j < N; j++) {
    			if (img[i][j] != null) {
    				System.out.print(/*img[i][j].getId() + ":" + */ "(" + img[i][j].getX() + "," + img[i][j].getY() + ")");
    			} else {
    				System.out.print("(PN)");
    			}
    			System.out.print("\t");
    		}
    		System.out.println();
    	}	
    }
    
    
    /* Exporte la matrice représentant l'image dans le fichier de nom filename */
    public void exportImage(String filename) {
    	//BufferedWriter bw = new BufferedWriter(new FileWriter("resultat.txt"))
    	try(BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
    		if(this.matrix == null) {
        		createMatrix();    		
        	}
        	
        	Pixel[][] img = this.matrix.getImg();
        	for(int i = 0; i < N; i++) {
        		for(int j = 0; j < N; j++) {
        			if (img[i][j] != null) {
        				bw.write(String.valueOf(img[i][j].getOwner()));
        			} else {
        				bw.write("(PN)"); // pixel est null
        			}
        			bw.write("\t");
        		}
        		bw.write("\n");
        	}
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	
    
    /* Affiche l'arbre récursivement */
    public static void showTree(Node cur) {
 		if(cur instanceof InterNode) {
 		} else {
 			System.out.println("P : " + ((Pixel)cur).getId());
 			return;
 		}
 		
 		ImageTree.showTree(((InterNode)cur).getLeft());
 		ImageTree.showTree(((InterNode)cur).getRight());
     }
   
    
    
    // abstract methods
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


/*
    private void createTree(InterNode parent, int depth, int tmpid, int poids, int xmin, int xmax, int ymin, int ymax) {
    	// cas récursif
    	if(depth != 1) {
    		InterNode right = createInterNode();
    		InterNode left = createInterNode();
    		parent.set(left, right);
    		//System.out.println("depth : " + depth + " id du pixel : " + tmpid + " , xmin : " + xmin + " , xmax :" + xmax + " , ymin : " + ymin + " , ymax :" + ymax);
    		
    		// si profondeur impaire on modifie ymax et ymin
    		if (depth%2 == 1) {
    			// on crée le sous-arbre droit
    			createTree(right, depth - 1, (tmpid | poids), poids << 1, xmin, xmax, ymin, ymax-((ymax-ymin+1)/2));
    			//on crée le sous-arbre gauche
    			createTree(left, depth - 1, tmpid, poids << 1, xmin, xmax , ymin+((ymax-ymin+1)/2), ymax);

    		}
    		// si profondeur paire on modifie xmax et xmin
    		else {
    			// on crée le sous-arbre droit
    			createTree(right, depth - 1, (tmpid | poids), poids << 1, xmin+((xmax-xmin+1)/2), xmax, ymin, ymax);
    			//on crée le sous-arbre gauche
    			createTree(left, depth - 1, tmpid, poids << 1, xmin, xmax-((xmax-xmin+1)/2), ymin, ymax);
    		}	
    	}
    	// cas terminal
    	else {
    		//System.out.println("depth : " + depth + " id du pixel : " + tmpid + " , xmin : " + xmin + " , xmax :" + xmax + " , ymin : " + ymin + " , ymax :" + ymax);
    		// si hauteur de l'arbre paire on modifie ymax et ymin
    		if (h%2 == 0) {
        		Pixel pr = createPixel((tmpid | poids), xmax-1, ymin-1);
        		//System.out.println("pixel droit " + " d' id :" + pr.getId() + ", x : " + pr.getX() + " y : " + pr.getY());

        		Pixel pl = createPixel((tmpid), xmin-1, ymax-1); //xmax/2
        		//System.out.println("pixel gauche " + " d' id :" + pl.getId() + ", x : " + pl.getX() + " y : " + pl.getY());
        		parent.setPixel(pl, pr);

    		}
    		// si hauteur de l'arbre impaire on modifie xmax et xmin
    		// les deux cas sont les mêmes ???? VERIFIER
    		
    		else {
        		Pixel pr = createPixel((tmpid | poids), xmax-1, ymin-1);
        		//System.out.println("pixel droit " + " d' id :" + pr.getId() + ", x : " + pr.getX() + " y : " + pr.getY());

    			Pixel pl = createPixel((tmpid), xmin-1, ymax-1);
        		//System.out.println("pixel gauche " + " d' id :" + pl.getId() + ", x : " + pl.getX() + " y : " + pl.getY());
        		parent.setPixel(pl, pr);
    		}
    		
    	}
    }
*/
