package pixelwar;

import java.awt.Color;

public class Pixel extends Node {
	protected int id;
	protected int x;
	protected int y;
	protected long ownerT = -1;
	protected Color color;
	
	public Pixel(int id, int x, int y) {
		this.id = id;
		this.x = x;
		this.y = y;
		color = Color.WHITE;
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

	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	public void setOwner(long idO) {
		this.ownerT = idO;
	}
	public long getOwner() {
		return ownerT;
	}
}
