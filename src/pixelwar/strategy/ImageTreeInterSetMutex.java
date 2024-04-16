package pixelwar.strategy;

import java.util.List;
import java.util.Map;

import pixelwar.ImageTree;
import pixelwar.Node;
import pixelwar.Pixel;
import pixelwar.PixelMutex;
import pixelwar.Tile;

public class ImageTreeInterSetMutex extends ImageTreeInterMutex {

	public ImageTreeInterSetMutex(int N) {
		super(N);
	}
		
	
	@Override
	public void putTile(Tile t) {
		System.out.println("Thread " + Thread.currentThread().getId() + " va poser la tuile " + t.toString());
		
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
		
		// la partie qui pose problème : la vérification du chemin depuis la racine jusqu'au noeud cible
		cur.lockNode();
		System.err.println("Je suis " + Thread.currentThread().getId() + " et je lock la racine");

		// suivre le chemin jusqu'à trouver le noeud
		while(i < length) {
			if (path[i] == 1) {
				next = cur.getRight();
			} else {
				next = cur.getLeft();
			}
			try {
				next.lockNode();
				System.out.println("Je suis " + Thread.currentThread().getId() + " et je lock le noeud next : " + path[i] + " d'hauteur : " + i + " et d'id : " + next.getId());
			} finally {
				cur.unlockNode();
				System.out.println("Je suis "+ Thread.currentThread().getId() + " et j'unlock le noeud next : " + path[i] + " d'hauteur : " + i + " et d'id : " + next.getId());
				//cur.notifyNode();
			}

			cur = next;
			i++;
		}
		
		
		// verrouiller le noeud cible
		Node target = cur;
		System.out.println("Je suis " + Thread.currentThread().getId() + " et le noeud "+ target.getId() +" est locké : "+ target.isLocked());
		//target.lockNode(); // normalement déjà locké dans la boucle
		
		
		// attendre que tous les sous-noeuds de target soient déverrouillés
		System.out.println("Je suis " + Thread.currentThread().getId() + " et je vérifie mes sous-arbres: ");
		Node guilty = verifySubTree(target);
		while(guilty != null) {
			System.out.println("Je suis " + Thread.currentThread().getId() + " et guilty = " +  guilty.getId());
			guilty.lockNode();
			guilty.unlockNode();
			guilty = verifySubTree(target);
		}
		
		
		// une fois que tous les noeuds sont libres on pose notre tuile
		List<Pixel> pixels = t.getPixels();
		for (Pixel p : pixels) {
			putPixel((PixelMutex) p);
		}
		System.out.println("\n");
		
		// on libère le noeud
		target.unlockNode();
		System.out.println("Je suis " + Thread.currentThread().getId() + " et j'unlock le noeud target");
		//target.notifyNode();
		System.out.println("Je suis " + Thread.currentThread().getId() + " et je préviens les autres threads");

	}
	

}
