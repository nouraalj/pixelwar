package pixelwar.strategy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pixelwar.Tile;
import pixelwar.tree.ImageTree;
import pixelwar.tree.InterNode;
import pixelwar.tree.InterNodeMutex;
import pixelwar.tree.Node;
import pixelwar.tree.Pixel;
import pixelwar.tree.PixelMutex;

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
	
	// calculer le chemin vers le noeud cible pour verrouiller la tuile t
	public Map<Integer, byte[]> pathToNode(Tile t) {
		List<Pixel> l = t.getPixels(); //liste des pixels de la tuile
		
		// récupérer la liste de id des pixels de la tuile
		int[] ids = new int[l.size()];
		for (int i = 0; i<ids.length; i++) {
			ids[i] = l.get(i).getId();
			//System.out.println(" id pixel : " + ids[i]);
		}
		
		boolean exit = false; // indique si on a trouvé le noeud cible et qu'il faut sortir de la boucle
		int ref; // valeur du bit à la position courante pour le 1er pixel (à comparer aux autres)
		int length = 0; // longueur du chemin jusqu'au noeud cible
		byte[] path = new byte[getH()]; // chemin jusqu'au noeud cible
		int pos = 1; // position du bit courant que l'on regarde
		
		while (pos != (int) Math.pow((double) 2, (double) getH())) {
			//System.out.println(" pos & ids = " + (pos & ids[0]));
			ref = (pos & ids[0]);

			// on compare le bit à la même position pour tous les autres pixels
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

			if ((pos & ids[0]) > 0) { // réécriture du chemin avec un 0 ou un 1
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

			pos = pos << 1; // nouvelle position du bit courant à regarder
		}
		HashMap<Integer, byte[]> m = new HashMap<>();
		m.put(length, path);
		
		//System.out.println(" taille du chemin : " + length);
		//System.out.println(" InterNode à verrouiller pour lock la tuile : " + Arrays.toString(path));
		return m;
	}
	
	/*
	// Retourne le noeud cible à verrouiller pour pouvoir poser la tuile t
	public Node findNode(Tile t){
		
		// calculer le chemin vers le noeud cible et récupérer la longueur de ce chemin
		Map<Integer, byte[]> path_ = pathToNode(t);
		int length = 0;
		for (Integer i : path_.keySet()) { // supercherie pour récupérer l'unique clé de la map
			length = i;
		}
		byte[] path = path_.get(length);
		
		Node cur = getRoot();
		int j = 0;
		
		// suivre le chemin jusqu'à trouver le noeud
		while(j < length) {
			if (path[j] == 1) {
				cur = cur.getRight();
			} else {
				cur = cur.getLeft();
			}
			j++;
		}
		return cur;
	}
	*/
	
	
	/*     ne fonctione pas 
	 * 
	 * Vérifie qu'il n'existe aucun noeud déjà verouillé sans le sous-arbre de target
	 * La fonction retourne quand on a finit d'attendre que tous les noeuds soient libres
	 
	public void verifySubTreeOld(Node target) {
		// si le noeud cible est pixel, il n'y pas de sous-arbre à vérifier
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
	*/
	
	
	// Retourne null si les noeuds du sous-arbre sont tous libres, sinon retourne le 1er noeud non libre trouvé
	public Node verifySubTree(Node target) {
		// si le noeud cible est pixel, il n'y pas de sous-arbre à vérifier
		if (target instanceof Pixel) {
			return null;
		}
		
		if (target.getLeft().isLocked()) {
			return target.getLeft();
		}
		if (target.getRight().isLocked()) {
			return target.getRight();
		}
		
		Node vleft = verifySubTree(target.getLeft());
		Node vright = verifySubTree(target.getRight());
		return vleft == null ? vright : vleft;
	}
	

	@Override
	public Long putTile(Tile t) {
		//System.out.println("Thread " + Thread.currentThread().getId() + " va poser la tuile " + t.toString());
		
		// calculer le chemin vers le noeud cible et récupérer la longueur de ce chemin
		Map<Integer, byte[]> path_ = pathToNode(t);
		int length = 0;
		for (Integer i : path_.keySet()) { // supercherie pour récupérer l'unique clé de la map
			length = i;
		}
		byte[] path = path_.get(length);
		
		Node cur = getRoot();
		Node next = null;
		int i = 0;
		

		long debut = System.nanoTime();
		cur.lockNode(); // verrouillage de la racine		System.err.println("Je suis " + Thread.currentThread().getId() + " et je lock la racine");

		// suivre le chemin jusqu'à trouver le noeud
		while(i < length) {
			if (path[i] == 1) {
				next = cur.getRight();
			} else {
				next = cur.getLeft();
			}
			try {
				next.lockNode();
				//System.out.println("Je suis " + Thread.currentThread().getId() + " et je lock le noeud next : " + path[i] + " d'hauteur : " + i + " et d'id : " + next.getId());
			} finally {
				cur.unlockNode();
				//System.out.println("Je suis "+ Thread.currentThread().getId() + " et j'unlock le noeud next : " + path[i] + " d'hauteur : " + i + " et d'id : " + next.getId());
				//cur.notifyNode();
			}

			cur = next;
			i++;
		}
		
		
		// verrouiller le noeud cible
		Node target = cur;
		//System.out.println("Je suis " + Thread.currentThread().getId() + " et le noeud "+ target.getId() +" est locké : "+ target.isLocked());
		//target.lockNode(); // normalement déjà locké dans la boucle
		
		
		// attendre que tous les sous-noeuds de target soient déverrouillés
		//System.out.println("Je suis " + Thread.currentThread().getId() + " et je vérifie mes sous-arbres: ");
		Node guilty = verifySubTree(target);
		while(guilty != null) {
			//System.out.println("Je suis " + Thread.currentThread().getId() + " et guilty = " +  guilty.getId());
			guilty.lockNode();
			guilty.unlockNode();
			guilty = verifySubTree(target);
		}
		long fin = System.nanoTime();
		
		// une fois que tous les noeuds sont libres on pose notre tuile
		List<Pixel> pixels = t.getPixels();
		for (Pixel p : pixels) {
			putPixel((PixelMutex) p);
		}
		System.out.println("\n");
		
		// on libère le noeud
		target.unlockNode();
		//System.out.println("Je suis " + Thread.currentThread().getId() + " et j'unlock le noeud target");
		//target.notifyNode();
		//System.out.println("Je suis " + Thread.currentThread().getId() + " et je préviens les autres threads");

		return fin - debut;
	}
	
	
	/*
	 * @Override
	public void putTile(Tile t) {
		// trouver le noeud cible à verrouiller
		Node target = findNode(t);
		
		//vérifier si les noeuds parents ne sont pas déjà en train de travailler sur un de nos pixels
		char[] path = null; //on l'obtient à partir d'un hashmap contenant le node, hashmap<integer, chemin>
		Node cur = getRoot();
		Node next = null;
		int i = 0;
		
		while(cur != target) { // à fusionner avec findNode ? (on fait la même chose deux fois)
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
		
		// verrouiller le noeud cible
		target.lockNode();
		
		// on vérifie que tous les sous-noeuds de target ne sont pas déjà verrouillés
		verifySubTree(target);
		
		// une fois que tous les noeuds sont libres on pose notre tuile
		List<Pixel> pixels = t.getPixels();
		for (Pixel p : pixels) {
			putPixel((PixelMutex) p);
		}
		System.out.println("\n");
		
		// on libère le noeud
		target.unlockNode();

	}
	 */
	

}
