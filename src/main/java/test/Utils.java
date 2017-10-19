package test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Utils {

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
