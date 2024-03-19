package pixelwar.strategy;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pixelwar.ImageTree;
import pixelwar.InterNode;
import pixelwar.InterNodeMutex;
import pixelwar.Node;
import pixelwar.Pixel;
import pixelwar.PixelMutex;
import pixelwar.Tile;

public class ImageTreeInterMutex extends ImageTree {
	
	public ImageTreeInterMutex(int N) {
		super(N);
	}

	@Override
	public Pixel createPixel(int id, int x, int y) {
		return new PixelMutex(id, x, y);
	}

	@Override
	public InterNode createInterNode() {
		return new InterNodeMutex();
	}
	
	public Map<Integer, byte[]> pathToNode(Tile t) {
		List<Pixel> l = t.getPixels(); //liste des pixels de la tuile
		int[] ids = new int[l.size()]; // ids
		
		for (int i = 0; i<ids.length; i++) {
			ids[i] = l.get(i).getId();
			//System.out.println(" id pixel : " + ids[i]);
		}
		
		boolean exit = false;
		int ref; // valeur du bit à la position courante pour le 1er pixel à comparer aux autres
		int length = 0; // hauteur de l'internode à locker
		byte[] path = new byte[h]; 
		int pos = 1; // position du bit courant
		while (pos != (int) Math.pow((double) 2, (double) h)) { // tant que le bit courant n'est pas nul
			//System.out.println(" pos & ids = " + (pos & ids[0]));
			ref = (pos & ids[0]); 

			for (int id : ids) {
				if ((pos&id) != ref) { // dès que le bit est différent on sort
					exit = true; 
					break;
				}
			}
			if (exit) {
				break;
			}
			//System.out.println(" ref : " + ref);
			//System.out.println(" pos : " +pos);

			if ((pos & ids[0]) > 0) { // comparaison pour chemin
				//System.out.println("je vais à droite");
				ref = 1;
			} else {
				//System.out.println("je vais à gauche");

				ref = 0;
			}
			//System.out.println("new ref : " + ref);

			//System.out.println(" taille du chemin : " + length+ " direction courant : "+ path[length]);
			path[length] = (byte) ref; 
			//System.out.println(" taille du chemin : " + length+ " chemin courant : "+ path[length]);
			length++;

			pos = pos << 1; //décalage du bit courant
		}
		HashMap<Integer, byte[]> m = new HashMap<>();
		m.put(length, path);
		//System.out.print("préfixe : ");
		for(byte e : path) {
			//System.out.print(e);
		}
		//System.out.println(" taille du chemin : " + length);
		//System.out.println(" InterNode à verrouiller pour lock la tuile : " + Arrays.toString(path));
		return m;
	}

	public Node findNode(Tile t){
		/*
		List<Pixel> l = t.getPixels(); //liste des pixels de la tuile
		int[] ids = new int[l.size()]; // ids
		
		for (int i = 0; i<ids.length; i++) {
			ids[i] = l.get(i).getId();
		}
		
		boolean exit = false;
		int ref; // valeur du bit à la position courante pour le 1er pixel à comparer aux autres
		int length = 0; // hauteur de l'internode à locker
		char[] path = new char[h]; 
		int pos = (int) Math.pow((double) 2, (double) h); // position du bit courant
		while (pos != 0) { // tant que le bit courant n'est pas nul
			ref = pos & ids[0]; 
			for (int id : ids) {
				if ((pos&id) != ref) { // dès que le bit est différent on sort
					exit = true; 
					break;
				}
			}
			pos = pos >> 1; //décalage du bit courant
			if (exit) {
				break;
			}
			path[length] = (char) ref; // 
			length++;
		}*/
		
		Map<Integer, byte[]> path = pathToNode(t);
		int length = 0;
		for (Integer i : path.keySet()) {
			length = i;
		}
		
		
		Node cur = root;
		int j = 0; 
		while(j < length) {
			if (path.get(length)[j] == 1) {
				cur = cur.getRight();
			} else {
				cur = cur.getLeft();
			}
			j++;
		}
		return cur;
	}
	
	public void verifySubTree(Node target) {
		if (target instanceof Pixel) {
			return;
		}
		if (target.getLeft().isLocked()) {
			target.getLeft().waitNode();
			
		}
		if (target.getRight().isLocked()) {
			target.getRight().waitNode();
		}
		verifySubTree(target.getLeft());
		verifySubTree(target.getRight());

	}

	@Override
	public void putTile(Tile t) {
		Node target = findNode(t);
		/*
		//verifier si les noeuds parents ne sont pas déjà en train de travailler sur un de nos pixels
		char[] path = null; //on l'obtient à partir d'un hashmap contenant le node, hashmap<integer, chemin>
		Node cur = root;
		Node next = root;
		int i = 0;
		
		while(cur != target) {
			cur.lockNode();
			if (path[i] == 0) {
				next = cur.getLeft();
			} else {
				next = cur.getRight();
			}
			next.lockNode();
			cur.unlockNode();
			cur = next;
			i++;
		}
		*/
		
		// si le noeud que l'on souhaite locker est déjà pris, on se met en attente
		target.lockNode();
		//
		// on vérifie que tous les autres sous noeuds ne sont pas verrouillés 
		//(s'il le sont on se met en attente sur ces noeuds là (systeme de sémaphore avec wait notify)
		verifySubTree(target);
		//	sinon on pose notre tuile
		List<Pixel> pixels = t.getPixels();
		
		for (Pixel p : pixels) {
			putPixel((PixelMutex) p);
		}
		System.out.println("\n");
		target.unlockNode();

	}
	

}
