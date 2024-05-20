package pixelwar.tree;

import java.awt.Color;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import pixelwar.drawing.Tile;
import pixelwar.utils.Matrix;
import pixelwar.utils.Utils;

/* Classe de représentation de l'arbre / de la toile */

public abstract class ImageTree{
	private Node root; // racine de l'arbre
    private int N; // dimension du côté de l'image
    private int h; // hauteur de l'arbre
    private Matrix matrix = null; // matrice de représentation de l'image
    
    public ImageTree(int N) {
    	if(N < 1 || Utils.isPowerOf2(N) == false) {
    		throw new IllegalArgumentException("La dimension du côté de l'image doit être une puissance de 2 strictement positive");
    	}
    	
    	this.N = N;
    	this.h = Utils.log2int(2*N*N-1); // calcul de la hauteur de l'arbre nécessaire pour représenter la toile
    	
    	/* cas où l'arbre se résume à un seul pixel */
    	if (N == 1) {
    		root = createPixel(0, 0, 0);
    	}
    	/* cas général */
    	else {
    		root = createInterNode(); // création de la racine
    		// paramètres passés pour la construction de l'arbre (dans l'ordre) : racine, hauteur, tmpid, poids, xmin, ymin, xmax, ymax
    		createTree((InterNode) root, h, 0, 1, 1, N, 1, N);
    	}
    }
    
    /* méthodes abstraites */
    public abstract Pixel createPixel(int id, int x, int y);
    public abstract InterNode createInterNode();
    public abstract Long putTile(Tile t, Color c);
    
    /* Retourne la racine de l'arbre */
    public Node getRoot() {
    	return root;
    }
    
    /* Retourne la dimension du côté de l'image */
    public int getN() {
    	return this.N;
    }
    
    public int getH() {
    	return this.h;
    }
    
    
    /* Crée récursivement l'arbre de hauteur depth enraciné sous le noeud parent 
     * Les autres paramètres servent à numéroter les pixels avec un identifiant hiérarchique et des coordonnées
     */
    private void createTree(InterNode parent, int depth, int tmpid, int poids, int xmin, int xmax, int ymin, int ymax) {
    	// cas récursif
    	if(depth != 1) {
    		InterNode right = createInterNode();
    		InterNode left = createInterNode();
    		parent.set(left, right);
    		
    		// construction des sous-arbres (appel récursif)
    		// calcul des coordonnées : si profondeur impaire on modifie ymax et ymin (divison des zones verticalement)
    		if (depth%2 == 1) {
    			createTree(right, depth - 1, (tmpid | poids), poids << 1, xmin, xmax, ymin, ymax-((ymax-ymin+1)/2));
    			createTree(left, depth - 1, tmpid, poids << 1, xmin, xmax , ymin+((ymax-ymin+1)/2), ymax);
    		}
    		// si profondeur paire on modifie xmax et xmin (divison des zones horizontalement)
    		else {
    			createTree(right, depth - 1, (tmpid | poids), poids << 1, xmin+((xmax-xmin+1)/2), xmax, ymin, ymax);
    			createTree(left, depth - 1, tmpid, poids << 1, xmin, xmax-((xmax-xmin+1)/2), ymin, ymax);
    		}	
    	}
    	
    	// cas terminal : on est arrivé on au niveau des feuilles
    	else {    		
    		Pixel pr = createPixel((tmpid | poids), xmax-1, ymin-1);
    		Pixel pl = createPixel((tmpid), xmin-1, ymax-1);
    		parent.setPixel(pl, pr);  		
    	}
    }
    
    
    /* Retrouve un pixel dans l'arbre à partir de son identifiant */
	public Pixel findPixel(int id) {
		if(id < 0 || id >= N*N) { // cas où l'identifiant ne peut pas être présent dans l'arbre
			return null;
		} else if (this.N == 1) { // cas où l'arbre se résume à un seul pixel
			return (Pixel)this.root;
		}
		
		Node cur = this.root; // curseur le long d'une branche
		
		// suivre le chemin indiqué par l'identifiant hiérarchique
		while(!cur.hasPixel()) {
			if ((id %2) == 0) {
				cur = cur.getLeft();
			} else {
				cur = cur.getRight();
			}
			id = id >> 1;
		}
		
		// niveau pixel :
		if ((id%2) == 0) {
			cur = cur.getLeft();            
		} else {
			cur = cur.getRight();            
		}
		
		return (Pixel)cur;
	}
	
	
	/* Retrouve un pixel dans l'arbre à partir de ses coordonnées */
	public Pixel findPixel(int x, int y) {
		if(x < 0 || y < 0 || x >= N || y >= N) { // cas où les coordonnées ne peuvent pas être présentes dans l'arbre
			return null;
		} else if (this.N == 1) { // cas où l'arbre se résume à un seul pixel
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
		// à chaque étape on compare l'identifiant du pixel à l'intervalle de coordonnées représentées par chaque sous-arbre (drout et gauche)
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
    
    /* Retourne la matrice associée à l'arbre */
    public Matrix getMatrix() {
    	if(this.matrix == null) {
    		createMatrix();    		
    	}
    	return matrix;
    }
    
    
    /* Affiche la matrice associée à l'arbre */
    public void showMatrix() {
    	Pixel[][] img = this.getMatrix().getImg(); // le getMatrix crée la matrice si elle est vide
    	
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
    	
    	try(BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {        	
        	Pixel[][] img = this.getMatrix().getImg(); // le getMatrix crée la matrice si elle est vide
        	
        	for(int i = 0; i < N; i++) {
        		for(int j = 0; j < N; j++) {
        			if (img[i][j] != null) {
        				bw.write(String.valueOf(img[i][j].getOwner()));
        			}
        			else {
        				bw.write("(PN)"); // pixel est null
        			}
        			bw.write("\t");
        		}
        		bw.write("\n");
        	}
		}
    	catch (IOException e) {
			e.printStackTrace();
		}
    	
    }
   
    
    public void putPixel(Pixel p) {
    	p.setOwner(Thread.currentThread().getId());
 
    }
    
    public void putPixelColor(Pixel p, Color c) {
    	p.setOwner(Thread.currentThread().getId());
    	p.setColor(c);
 
    }
    
}
