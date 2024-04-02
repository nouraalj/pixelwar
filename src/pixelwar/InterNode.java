package pixelwar;

public class InterNode extends Node {
	private int id;
	private static int cpt = 0;
	private Node left;
	private Node right;
		
	public InterNode() {
		this.left = null;
		this.right = null;
		cpt++;
		id = cpt;
	}
	
	public void set(InterNode left, InterNode right) {
		this.left = left;
		this.right = right;

	}
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
	
	public int getId() {
		return id;
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
