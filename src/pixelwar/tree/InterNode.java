package pixelwar.tree;

/* Classe de représentation d'un noeud interne de l'arbre */

public class InterNode extends Node {
	private Node left; // fils gauche
	private Node right; // fils droit
		
	public InterNode() {
		this.left = null;
		this.right = null;
	}
	
	/* Initialise les enfants du noeuds avec les InterNode left et right */
	public void set(InterNode left, InterNode right) {
		this.left = left;
		this.right = right;

	}
	
	/* Initialise les enfants du noeuds avec les pixels left et right */
	public void setPixel(Pixel left, Pixel right) {
		this.left = left;
		this.right = right;

	}
	
	public Node getLeft() {
		return left;
	}
	
	public Node getRight() {
		return right;
	}
	
	public boolean hasPixel() {
		return left instanceof Pixel;
	}
	
	@Override
	public int getId() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isLocked() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void waitNode() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void lockNode() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void unlockNode() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void notifyNode() {
		throw new UnsupportedOperationException();		
	}
	
}
