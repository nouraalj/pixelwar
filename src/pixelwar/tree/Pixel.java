package pixelwar.tree;

import java.awt.Color;

/* Classe de représentation d'un pixel de l'arbre */

public class Pixel extends Node {
	private int id; // identifiant hiérarchique du pixel
	private int x; // ordonnée du pixel
	private int y; // abscisse du pixel
	private long ownerT; // thread qui a posé ce pixel en dernier
	private Color color;
	
	public Pixel(int id, int x, int y) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.ownerT = -1;
		color = Color.WHITE;
	}
	
	@Override
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
	
	public void setOwner(long id) {
		this.ownerT = id;
	}
	
	public long getOwner() {
		return ownerT;
	}

	public Color getColor() {
		return this.color;
	}
	
	/* Retourne true si le pixel a les mêmes coordonnées que le pixel passé en paramètre */
	public boolean sameCoordinates(int x, int y) {
		return (this.x == x && this.y == y) ? true : false;
	}
	
	public String toString() {
		return this.id + ":(" + this.x + "," + this.y + ")";
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
