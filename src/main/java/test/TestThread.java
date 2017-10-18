package test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class TestThread {

	private static final int NTHREDS = 10;
	private static final int KPI_LIST = 20000;

	public static void main(String[] args) {

		ExecutorService executor = Executors.newFixedThreadPool(NTHREDS);
		List<Future<Integer>> list = new ArrayList<>();
		for (int i = 0; i < KPI_LIST; i++) {
			Callable<Integer> worker =  new KpiCompute(String.format("Batch n-%d", i), generateRandomCombination());
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

	static List<Integer> generateRandomCombination() {
		Random random = new Random();
		return random
				.ints(1, 999999999)
				.distinct()
				.limit(100)
				.boxed()
				.collect(Collectors.toCollection(ArrayList::new));
	}
}
