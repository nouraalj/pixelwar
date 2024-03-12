package pixelwar.strategy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pixelwar.ImageTree;
import pixelwar.InterNode;
import pixelwar.InterNodeMutex;
import pixelwar.Node;
import pixelwar.Pixel;
import pixelwar.Tile;

public class ImageTreeInterMutex extends ImageTree {

	public ImageTreeInterMutex(int N) {
		super(N);
	}

	@Override
	public Pixel createPixel(int id, int x, int y) {
		return new Pixel(id, x, y);
	}

	@Override
	public InterNode createInterNode() {
		return new InterNodeMutex();
	}

	public Node findNode(Tile t){
		List<Pixel> l = t.getPixels();
		int[] ids = new int[l.size()];
		
		for (int i = 0; i<ids.length; i++) {
			ids[i] = l.get(i).getId();
		}
		boolean exit = false;
		int ref; // valeur du bit à la position courante pour le 1er pixel à comparer aux autres
		int length = 0; // hauteur de l'internode à locker
		char[] path = new char[h];
		int pos = (int) Math.pow((double) 2, (double) h); // position du bit courant
		while (pos != 0) {
			ref = pos & ids[0];
			for (int id : ids) {
				if ((pos&id) != ref) {
					exit = true; 
					break;
				}
			}
			pos = pos >> 1;
			if (exit) {
				break;
			}
			path[length] = (char) ref;
			length++;
		}
		
		Node cur = root;
		int j = 0; 
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
	

	@Override
	public void putTile(Tile t) {
		
		
	}

}
