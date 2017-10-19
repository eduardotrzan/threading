package test;

public class TestThread {


	public static void main(String[] args) {
		KpiComputeCallable.callableRun();
		KpiComputeRunnable.runnableRun();
	}
}