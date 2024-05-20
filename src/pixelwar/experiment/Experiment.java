package pixelwar.experiment;

import java.util.concurrent.ExecutionException;

public class Experiment {

 	public static void main(String[] args) throws InterruptedException, ExecutionException {
 		System.out.println("Expériences sur le délai d'attente de verrouillage : \n\n");
 		TimeExperiment1.main(null);
 		TimeExperiment2.main(null);
 		TimeExperiment3.main(null);
 		
 		System.out.println("Expériences sur le débit de pixel posés : \n\n");

 		PixelSumExperiment.main(null);
 	}
}
