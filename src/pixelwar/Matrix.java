package pixelwar;
public class Matrix {

	private int sizeM;
	private Pixel[][] img;
	
	public Matrix(int size) {
		this.sizeM = size;
		img = new Pixel[sizeM][sizeM];
		for (int i = 0; i< sizeM; i++) {
			for (int j =0; j < sizeM; j++) {
				img[i][j] = null;	
			}
		}
	}

	public Pixel[][] getImg() {
		return img;
	}
	
	public void setPixel(Pixel p) {
		img[p.getX()][p.getY()] = p;
	}
}
