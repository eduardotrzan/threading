package test;

import java.util.List;
import java.util.concurrent.Callable;

public class KpiCompute implements Callable<Integer> {

	private String callName;
	private List<Integer> numbers;
	private int total = 0;

	public KpiCompute(String name, List<Integer> numbers) {
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
}
