package pixelwar;

import java.util.concurrent.locks.ReentrantLock;

public class PixelMutex extends Pixel {
    private ReentrantLock mutex = new ReentrantLock();

	public PixelMutex(int id, int x, int y) {
		super(id, x, y);
	}
	
	public void lockNode() {
		mutex.lock();
	}
	
	public void unlockNode() {
		mutex.unlock();
	}

	@Override
	public boolean isLocked() {
		return mutex.isLocked();
	}
}
