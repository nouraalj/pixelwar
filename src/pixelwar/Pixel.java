package pixelwar;

public class Pixel extends Node {
	protected int id;
	protected long ownerT = -1;
	public Pixel(int id) {
		this.id = id;
	}
	
	public int getId() {
		return this.id;
	}

	@Override
	public Node getLeft() {
		return null;
	}

	@Override
	public Node getRight() {
		return null;
	}

	@Override
	public boolean hasPixel() {
		return false;
	}
	
	public void setOwner(long idO) {
		this.ownerT = idO;
	}
	public long getOwner() {
		return ownerT;
	}
}
