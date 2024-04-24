package pixelwar.tree;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/* Classe de représentation d'un pixel verrouillable */

public class PixelMutex extends Pixel {
    private ReentrantLock mutex = new ReentrantLock(); // verrou pour l'accès à ce pixel
    //private Condition notfree = mutex.newCondition();
    
	public PixelMutex(int id, int x, int y) {
		super(id, x, y);
	}
	
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
			e.printStackTrace();
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
