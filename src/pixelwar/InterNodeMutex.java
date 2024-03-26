package pixelwar;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class InterNodeMutex extends InterNode {
	private ReentrantLock mutex = new ReentrantLock();
	private Condition notfree = mutex.newCondition();
	 
	@Override
	public void lockNode() {
		mutex.lock();
	}
	
	@Override
	public void unlockNode() {
		notfree.signalAll();
		mutex.unlock();
		System.out.println("Je suis " + Thread.currentThread().getId() + " et j' UNLOCK le noeud");
		
		//mutex.notifyAll(); // pas de notifyAll dans la classe ReentrantLock !!
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
