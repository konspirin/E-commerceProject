package application.dto;

import java.time.LocalDate;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class RandomLib {
	
	public static Random random = new Random();
	
	@SuppressWarnings("serial")
	private static class RandomLibException extends RuntimeException{
		public RandomLibException(String msg) {
			super(msg);
		}
	}
	
	public static LocalDate randomLocalDate(LocalDate start, LocalDate end) {
		if (!start.isBefore(end)) throw new RandomLibException(
				"randomLocalDate: Start not before end. start: " + start + "; end: " + end
															);
		return start.plusDays(ThreadLocalRandom.current().nextLong(end.toEpochDay() - start.toEpochDay()));
	}
	
	public static String randomString(int len) {
		
		if (len < 0) throw new RandomLibException("randomString: Negative string length: " + len);
		
		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0; i < len; i++)stringBuffer.append((char)ThreadLocalRandom.current().nextInt(64, 90));
		return stringBuffer.toString();
	}
	
	public static String randomStringOfArray(String[] arr) {
		int arrLength = arr.length;
		
		if (arrLength == 0) throw new RandomLibException("randomStringOfArray: Empty array");
		
		return arr[ThreadLocalRandom.current().nextInt(arrLength)];
	}
	
	public static boolean randomBoolean(double trueProbability) {
		return ThreadLocalRandom.current().nextDouble() < trueProbability;
	}
	
}
