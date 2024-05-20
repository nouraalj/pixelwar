package pixelwar.strategy;

import java.awt.Color;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pixelwar.drawing.Tile;
import pixelwar.tree.ImageTree;
import pixelwar.tree.InterNode;
import pixelwar.tree.InterNodeMutex;
import pixelwar.tree.Node;
import pixelwar.tree.Pixel;
import pixelwar.tree.PixelMutex;

/* Classe de représentation de l'arbre pour la stratégie 2 */ 

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
	
	
	/* Calcule le chemin vers le noeud cible pour verrouiller la tuile t */
	public Map<Integer, byte[]> pathToNode(Tile t) {
		List<Pixel> l = t.getPixels(); //liste des pixels de la tuile
		
		// récupérer la liste des identifiants des pixels de la tuile
		int[] ids = new int[l.size()];
		for (int i = 0; i<ids.length; i++) {
			ids[i] = l.get(i).getId();
		}
		
		boolean exit = false; // indique si on a trouvé le noeud cible et qu'il faut sortir de la boucle
		int ref; // valeur du bit à la position courante pour le 1er pixel de la tuile (à comparer aux autres)
		int length = 0; // longueur du chemin jusqu'au noeud cible
		byte[] path = new byte[getH()]; // chemin jusqu'au noeud cible
		int pos = 1; // position du bit courant que l'on regarde (poids du bit dans l'entier)
		
		/* déterminer le préfixe commun entre tous les identifiants des pixels */
		while (pos != (int) Math.pow((double) 2, (double) getH())) {
			ref = (pos & ids[0]);

			// on compare le bit à la même position pour tous les autres pixels
			for (int id : ids) {
				if ((pos&id) != ref) { // dès que le bit est différent on sort, c'est que le préfixe commun est terminé
					exit = true; 
					break;
				}
			}
			
			if (exit) {
				break;
			}
			
			// réécriture de la direction (droite ou gauche) avec un 0 ou un 1
			if ((pos & ids[0]) > 0) {
				ref = 1;
			} else {
				ref = 0;
			}
			
			// mise à jour du chemin
			path[length] = (byte) ref; 
			length++;

			pos = pos << 1; // nouvelle position du bit courant à regarder
		}
		
		/* mapper la longueur du chemin et le chemin */
		HashMap<Integer, byte[]> m = new HashMap<>();
		m.put(length, path);
		
		return m;
	}
	
	
	// Retourne null si les noeuds du sous-arbre sont tous libres, sinon retourne un noeud non libre trouvé
	public Node verifySubTree(Node target) {
		// si le noeud cible est un pixel, il n'y pas de sous-arbre à vérifier
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
	
	
	/* Pose la tuile et retourne le temps d'attente du thread pour obtenir les verrous nécessaires et que le sous-arbre soit libre */
	@Override
	public Long putTile(Tile t, Color c) {
		
		// calculer le chemin vers le noeud cible et récupérer la longueur de ce chemin
		Map<Integer, byte[]> path_ = pathToNode(t);
		int length = 0;
		for (Integer i : path_.keySet()) { // récupérer l'unique clé de la map
			length = i;
		}
		byte[] path = path_.get(length);
		
		Node cur = getRoot(); // noeud curseur
		Node next = null; // noeud suivant du noeud curseur
		int i = 0;
		
		try {
			long debut = System.nanoTime();
			cur.lockNode(); // verrouillage de la racine

			// suivre le chemin jusqu'à trouver le noeud cible (en verrouillant les noeuds rencontrés le long du chemin pour vérifier que la zone est libre)
			while(i < length) {
				if (path[i] == 1) {
					next = cur.getRight();
				} else {
					next = cur.getLeft();
				}
				try {
					next.lockNode();
				} finally {
					cur.unlockNode();
				}

				cur = next;
				i++;
			}
			
			// à ce stade cur pointe sur le noeud cible et il est bien verrouillé
			
			// attendre que tous les sous-noeuds de target soient déverrouillés
			Node guilty = verifySubTree(cur);
			while(guilty != null) {
				try {
					guilty.lockNode();
				} finally {
					guilty.unlockNode();
				}
				guilty = verifySubTree(cur);
			}
			long fin = System.nanoTime();
			
			// une fois que tous les noeuds sont libres on pose notre tuile
			List<Pixel> pixels = t.getPixels();
			for (Pixel p : pixels) {
				putPixelColor(p, c);
			}

			return fin - debut;
			
		} finally {
			// on libère le noeud cible
			cur.unlockNode();
		}
	}	

}
