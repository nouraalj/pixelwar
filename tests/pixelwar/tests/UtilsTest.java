package pixelwar.tests;

import pixelwar.utils.Utils;

public class UtilsTest {

	public static void main(String[] args) {
		
		int[] ints = {-1, 0, 1, 2, 3, 4, 8, 15};
		System.out.println("\nTest de log2int, log2double, isPowerOf2 :\n ");
		for(int i : ints) {
			System.out.println("log2int(" + i + ") = " + Utils.log2int(i));
		}
		System.out.println("");
		
		for(int i : ints) {
			System.out.println("log2double(" + i + ") = " + String.format("%.2f", Utils.log2double(i)));
		}
		System.out.println("");
		
		for(int i : ints) {
			System.out.println("isPowerOf2(" + i + ") = " + Utils.isPowerOf2(i));
		}
		
	}

}
