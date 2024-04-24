package pixelwar.tree;

/* Interface d'un noeud de l'arbre */

public abstract class Node {
	
	public abstract Node getLeft(); // retourne le fils gauche
	public abstract Node getRight(); // retourne le fils droit
	public abstract boolean hasPixel(); // retourne true si le noeud a pour enfants des pixels, et false si le noeud est lui-même un pixel 
	public abstract int getId(); // retourne l'identifiant hiérarchique (pour les pixels seulement)
	
	/* méthodes de synchronisation pour les noeuds verrouillables */
	public abstract boolean isLocked();
	public abstract void waitNode();
	public abstract void lockNode(); // verrouille le noeud
	public abstract void unlockNode(); // déverrouille le noeud
	public abstract void notifyNode();
	
}
