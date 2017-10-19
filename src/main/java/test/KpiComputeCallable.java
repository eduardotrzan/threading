package test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

public class KpiComputeCallable implements Callable<Integer> {

	private static final int NTHREDS = 10;
	private static final int KPI_LIST = 20000;

	private String callName;
	private List<Integer> numbers;
	private int total = 0;

	public KpiComputeCallable(String name, List<Integer> numbers) {
		this.callName = name;
		this.numbers = numbers;
		System.out.println("Creating " +  this.callName);
	}

	@Override
	public Integer call() throws Exception {
		System.out.println("Running " +  this.callName);
		for (Integer number : this.numbers) {
			this.total += number;
		}
		System.out.println("Callable " +  this.callName + " exiting.");
		return this.total;
	}

	public static void callableRun() {
		ExecutorService executor = Executors.newFixedThreadPool(NTHREDS);
		List<Future<Integer>> list = new ArrayList<>();
		for (int i = 0; i < KPI_LIST; i++) {
			Callable<Integer> worker =  new KpiComputeCallable(String.format("Batch n-%d", i), Utils.generateRandomCombination());
			Future<Integer> submit = executor.submit(worker);
			list.add(submit);
		}

		long sum = 0;
		for (Future<Integer> future : list) {
			try {
				sum += future.get();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println(String.format("KPI Total %d, average %d", sum, sum/KPI_LIST));
		executor.shutdown();
	}
}
