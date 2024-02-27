package pixelwar;

import java.util.concurrent.locks.ReentrantLock;

public class PixelMutex extends Pixel {
    private ReentrantLock mutex = new ReentrantLock();

	public PixelMutex(int id, int x, int y) {
		super(id, x, y);
		
	}
	
	public void lockPixel() {
		mutex.lock();
	}
	
	public void unlockPixel() {
		mutex.unlock();
	}

}
