package pixelwar.strategy;

import java.util.Arrays;

import pixelwar.ImageTree;
import pixelwar.InterNode;
import pixelwar.Pixel;
import pixelwar.PixelMutex;
import pixelwar.Tile;
import pixelwar.Utils;

public class ImageTreePixelMutex extends ImageTree {
	public ImageTreePixelMutex(int N) {
		super(N);
	}

	
	public void createTree(InterNode parent, int depth, int id_parent) {
    	
    	if(depth != 0) {
    		/* je pense que le plus simple pour id les pixels,
    		c'est que les noeuds internes aient aussi un id */
    		InterNode right = new InterNode();
    		cptIN++;
    		InterNode left = new InterNode();
    		cptIN++;
    		parent.set(left, right);
    		//System.out.println("" + cptIN);
    		 //on crée le sous-arbre droit
    		createTree(right, depth - 1, (id_parent*2)+1);
    		 //on crée le sous-arbre gauche
    		createTree(left, depth - 1, (id_parent*2));

    	} else {
    		Pixel pl = new PixelMutex((id_parent*2)); //id à calculer
    		Pixel pr = new PixelMutex((id_parent*2)+1); //id à calculer

    		cptP++;
    		parent.setPixel(pl, pr);
    	}
    }	
	
	@Override
	public void putPixel(int id) {
		PixelMutex p = (PixelMutex) Utils.findPixel(id, this);
		try {
			p.lockPixel();
			p.setOwner(Thread.currentThread().getId());
		} finally {
			p.unlockPixel();

		}	
	}
	

	@Override
	public void putTile(Tile t) {
		int[] ids = t.getIds();
		Arrays.sort(ids);
		for (int id : ids) {
			putPixel(id);
		}

	}




}
