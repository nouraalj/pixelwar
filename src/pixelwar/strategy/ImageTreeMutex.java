package pixelwar.strategy;

import java.util.concurrent.locks.ReentrantLock;

import pixelwar.ImageTree;
import pixelwar.InterNode;
import pixelwar.Pixel;
import pixelwar.Tile;
import pixelwar.Utils;

public class ImageTreeMutex extends ImageTree {
    private ReentrantLock mutex = new ReentrantLock();
    public ImageTreeMutex(int N) {
    	super(N);
    }    
    
    
	public void putPixel(int id) {
			Pixel p = this.findPixel(id);
            p.setOwner(Thread.currentThread().getId());
            System.out.println( "Pixel d'id : " + p.getId() + " posé par thread : " + p.getOwner());
	}
	

	@Override
	public void putTile(Tile t) {
		try {
			mutex.lock();
			for (int id : t.getIds()) {
				putPixel(id);
			}
			System.out.println("\n");
		} finally {
			mutex.unlock();
		}
	}

	
	@Override
	public Pixel createPixel(int id, int x, int y) {
		return new Pixel(id, x, y);
	}

	
	@Override
	public InterNode createInterNode() {
		return new InterNode();
	}
	
}




/*
 * public void createTree(InterNode parent, int depth, int id_parent) {
	
	if(depth != 0) {
		// je pense que le plus simple pour id les pixels,
		//c'est que les noeuds internes aient aussi un id
		InterNode right = new InterNode();
		super.cptIN++;
		InterNode left = new InterNode();
		super.cptIN++;
		parent.set(left, right);
		//System.out.println("" + cptIN);
		 //on crée le sous-arbre droit
		createTree(right, depth - 1, (id_parent*2)+1);
		 //on crée le sous-arbre gauche
		createTree(left, depth - 1, (id_parent*2));

	} else {
		Pixel pl = new Pixel((id_parent*2)); //id à calculer
		Pixel pr = new Pixel((id_parent*2)+1); //id à calculer

		cptP++;
		parent.setPixel(pl, pr);
	}
}
 */




/*
public void putPixel(int id) {
		//convertir en string
		Node cur = root;
		int tmpid = Utils.inverser(id);
       
       while(!cur.hasPixel()) {
       	if ((tmpid %2) == 0) {
       		cur = cur.getLeft();
       	} else {
       		cur = cur.getRight();
       	}
       	tmpid = tmpid >> 1;
       	// print tmp
       }
       //pixel :
       if ((tmpid & 2) == 0) {
       	cur = cur.getLeft();            
       } else {
       	cur = cur.getRight();            
       }
       ((Pixel)cur).setOwner(Thread.currentThread().getId());
       System.out.println( "Pixel d'id : " + ((Pixel)cur).getId() + " posé par thread : " + ((Pixel)cur).getOwner());
}
*/
