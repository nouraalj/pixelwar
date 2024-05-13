package pixelwar.tree;

import java.util.concurrent.locks.ReentrantLock;

/* Classe de représentation d'un noeud interne  verrouillable */

public class InterNodeMutex extends InterNode {
	private ReentrantLock mutex = new ReentrantLock();
	//private Condition notfree = mutex.newCondition();
	 
	@Override
	public void lockNode() {
		mutex.lock();
	}
	
	@Override
	public void unlockNode() {
		//notfree.signalAll();
		mutex.unlock();
	}
	
	@Override
	public boolean isLocked() {
		return mutex.isLocked();
	}
	
	/*
	@Override
	public void waitNode() {
		try {
			notfree.await();
		} catch (InterruptedException e) {
			//e.printStackTrace();
		}
	}
	*/
	
	/*
	@Override
	public void notifyNode() {
		notfree.signalAll();
	}
	*/
}
