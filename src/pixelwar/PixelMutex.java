package pixelwar;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class PixelMutex extends Pixel {
    private ReentrantLock mutex = new ReentrantLock();
    private Condition notfree = mutex.newCondition();

	public PixelMutex(int id, int x, int y) {
		super(id, x, y);
	}
	
	@Override
	public void lockNode() {
		mutex.lock();
	}
	
	@Override
	public void unlockNode() {
		mutex.unlock();
		notfree.signalAll();
		//mutex.notifyAll(); // pas de méthode notifyAll sur un ReetrantLock !!! ça utilise celle de Object à la place
	}

	@Override
	public boolean isLocked() {
		return mutex.isLocked();
	}
	
	@Override
	public void waitNode() {
		try {
			notfree.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void notifyNode() {
		notfree.signalAll();
	}
}
