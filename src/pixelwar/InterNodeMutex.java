package pixelwar;

import java.util.concurrent.locks.ReentrantLock;

public class InterNodeMutex extends InterNode {

	 private ReentrantLock mutex = new ReentrantLock();
	 
	 public void lockNode() {
			mutex.lock();
	}
		
	public void unlockNode() {
			mutex.unlock();
			mutex.notifyAll();
	}
	
	public boolean isLocked() {
		return mutex.isLocked();
	}
	
	public void waitNode() {
		try {
			mutex.wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
