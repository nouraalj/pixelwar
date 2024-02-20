package pixelwar;

import java.util.concurrent.locks.ReentrantLock;

public class PixelMutex extends Pixel {
    private ReentrantLock mutex = new ReentrantLock();

	public PixelMutex(int id) {
		super(id);
		
	}
	
	public void lockPixel() {
		mutex.lock();
	}
	
	public void unlockPixel() {
		mutex.unlock();
	}

}
