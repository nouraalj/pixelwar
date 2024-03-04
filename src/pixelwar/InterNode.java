package pixelwar;

public class InterNode extends Node {
	private Node left;
	private Node right;
	
	public InterNode(InterNode left, InterNode right) {
		this.left = left;
		this.right = right;
	}
	
	public InterNode() {
		this.left = null;
		this.right = null;
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

	
}
