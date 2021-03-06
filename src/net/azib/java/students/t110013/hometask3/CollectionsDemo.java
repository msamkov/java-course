package net.azib.java.students.t110013.hometask3;

import java.util.Arrays;
import java.util.Map;

/**
 * @author Vadim
 */
public class CollectionsDemo {

	public static void main(String[] args) {
		DuplicateRemoverImpl dr = new DuplicateRemoverImpl();
		String[] processedString = dr.removeDuplicateStrings("a", "b", "c", "c", "b", "a", "c", "c", "b", "a", "v", "f");
		System.out.println(Arrays.toString(processedString));

		WordFrequencyCalculatorImpl wfc = new WordFrequencyCalculatorImpl();
		Map wordsMap = wfc.calculateFrequenciesOf("the to be was go be Was go To The no yes no yes maybe be the");
		System.out.println(wordsMap);
	}
}
