package test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class KpiComputeRunnable implements Runnable {

	private static final int NTHREDS = 10;
	private static final int KPI_LIST = 20000;

	private String runName;
	private List<Integer> numbers;
	private int total = 0;

	public KpiComputeRunnable(String name, List<Integer> numbers) {
		this.runName = name;
		this.numbers = numbers;
		System.out.println("Creating " +  this.runName);
	}

	@Override
	public void run() {
		System.out.println("Running " +  this.runName);
		for (Integer number : this.numbers) {
			this.total += number;
		}
		System.out.println("Runnable " +  this.runName + " exiting and total " + this.total + ".");
	}

	static void runnableRun() {
		try {
			ExecutorService executor = Executors.newFixedThreadPool(NTHREDS);
			List<Future<Integer>> list = new ArrayList<>();
			for (int i = 0; i < KPI_LIST; i++) {
				KpiComputeRunnable worker = new KpiComputeRunnable(String.format("Batch n-%d", i), Utils.generateRandomCombination());
				executor.execute(worker);
			}

			executor.shutdown();
			// Wait until all threads are finish
			executor.awaitTermination(10, TimeUnit.MINUTES);
			System.out.println("Finished all threads");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
