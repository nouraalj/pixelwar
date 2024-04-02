package pixelwar;

public abstract class Node {
	
	public abstract Node getLeft();
	public abstract Node getRight();
	public abstract boolean hasPixel();
	public abstract int getId();
	
	public abstract boolean isLocked();
	public abstract void waitNode();
	public abstract void lockNode();
	public abstract void unlockNode();
	public abstract void notifyNode();
	
}
