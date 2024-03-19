package pixelwar;

public class InterNode extends Node {
	private Node left;
	private Node right;
		
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

	@Override
	public boolean isLocked() {
		return false; //notSupportedException
	}

	@Override
	public void waitNode() {
		// TODO Auto-generated method stub
		return;
	}

	@Override
	public void lockNode() {
		// TODO Auto-generated method stub
		//notSupportedException
	}

	@Override
	public void unlockNode() {
		// TODO Auto-generated method stub
		//notSupportedException
	}

	
}
