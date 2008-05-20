package net.azib.java.students.t072054.homework;

import java.util.ArrayList;
import java.util.Map;

import java.util.LinkedHashMap;

/**
 * DecathlonCalculator
 * 
 * @author r_vassiljev
 */

public class DecathlonCalculator {
	// Input/output methods
	private static final int CONSOLE = 1;
	private static final int CSV = 2;
	private static final int DB = 3;
	private static final int XML = 4;
	private static final int HTML = 5;

	// Stages of parsing file
	private static final int INPUT_METHOD = 0;
	private static final int INPUT_STRING = 1;
	private static final int INPUT_INTEGER = 2;
	private static final int OUTPUT_METHOD = 3;
	private static final int OUTPUT_STRING = 4;
	private static final int PARSE_FINISHED = 5;

	private static int inputMethod;
	private static int outputMethod;
	private static String inputString;
	private static int inputNum;
	private static String inputRoute;
	private static String outputRoute;

	/**
	 * Starting point of application
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		Map<Integer, String[]> resultMap1 = new LinkedHashMap<Integer, String[]>();
		Map<Integer, String[]> resultMap2 = new LinkedHashMap<Integer, String[]>();
		Map<Integer, String[]> resultMap3 = new LinkedHashMap<Integer, String[]>();

		// Initial value
		inputNum = 1;

		ResultShower resShow = new ShowResults();

		LoadNewResults lnr = new LoadNewResults();

		ArrayList<Integer> resultsArray = new ArrayList<Integer>();

		commandLineParsing(args);

		switch (inputMethod) {
		case DB:
			lnr.loadResultsDB(resultMap1, resultMap2, resultMap3);
			break;
		case CSV:
			lnr.loadResultsCSV(resultMap1, resultMap2, resultMap3, inputRoute);
			break;
		case CONSOLE:
			lnr.loadResultsConsole(resultMap1, resultMap2, resultMap3);
			break;
		}

		// Check for name of competition
		try {
			if (inputMethod == DB) {
				inputNum = getEventID(resultMap2, inputString);
				if (inputNum == -1)
					inputNum = Integer.valueOf(inputString);
			}
		}
		catch (Exception e) {
			System.out.println("Competition name not found exception");
		}

		switch (outputMethod) {
		case CONSOLE:
			if (inputNum == 0)
				inputNum = 1;
			resultsArray = SortingID.sortByID(resultMap1, resultMap2, resultMap3, inputNum);
			if (inputNum == 0)
				inputNum = 1;
			resShow.showResultsConsole(resultMap1, resultMap2, resultMap3, resultsArray, inputNum);
			break;
		case XML:
			if (inputNum == 0)
				inputNum = 1;
			resultsArray = SortingID.sortByID(resultMap1, resultMap2, resultMap3, inputNum);
			if (inputNum == 0)
				inputNum = 1;
			try {
				resShow.showResultsXML(resultMap1, resultMap2, resultMap3, resultsArray, inputNum, outputRoute);
			}
			catch (Exception e) {
				System.out.println("XML creating exception!");
			}
			break;
		case CSV:
			if (inputNum == 0)
				inputNum = 1;
			resultsArray = SortingID.sortByID(resultMap1, resultMap2, resultMap3, inputNum);
			if (inputNum == 0)
				inputNum = 1;
			try {
				resShow.showResultsCSV(resultMap1, resultMap2, resultMap3, resultsArray, inputNum, outputRoute);
			}
			catch (Exception e) {
				System.out.println("CSV creating exception!");
			}
			break;
		case HTML:
			System.out.println("HTML is not supported!");
			break;
		}
	}

	/**
	 * Command line parsing
	 * 
	 * @param args
	 * @return Test string input and output routes and parameters
	 */
	public static String commandLineParsing(String args[]) {
		int stage = INPUT_METHOD;
		String return_value;

		// Initialization of static members
		inputRoute = null;
		outputRoute = null;
		inputMethod = 0;
		outputMethod = 0;
		inputNum = 0;

		for (String s : args) {
			if (stage == INPUT_METHOD) {
				if (s.equals("-console")) {
					inputMethod = CONSOLE;
					stage = OUTPUT_METHOD;
				}
				else if (s.equals("-csv")) {
					inputMethod = CSV;
					stage = INPUT_STRING;
				}
				else if (s.equals("-db")) {
					inputMethod = DB;
					stage = INPUT_INTEGER;
				}
			}
			else if (stage == INPUT_STRING) {
				inputRoute = s;
				stage = OUTPUT_METHOD;
			}
			else if (stage == INPUT_INTEGER) {
				inputString = s;
				stage = OUTPUT_METHOD;
			}
			else if (stage == OUTPUT_METHOD) {
				if (s.equals("-console")) {
					outputMethod = CONSOLE;
					stage = OUTPUT_STRING;
				}
				else if (s.equals("-csv")) {
					outputMethod = CSV;
					stage = OUTPUT_STRING;
				}
				else if (s.equals("-xml")) {
					outputMethod = XML;
					stage = OUTPUT_STRING;
				}
				else if (s.equals("-html")) {
					outputMethod = HTML;
					stage = OUTPUT_STRING;
				}
			}
			else if (stage == OUTPUT_STRING) {
				outputRoute = s;
				stage = PARSE_FINISHED;
			}
		}

		return_value = inputRoute + " " + outputRoute + " " + inputMethod + " " + outputMethod + " " + inputString;

		return return_value;
	}

	/**
	 * Returns event ID by it's name
	 * 
	 * @param resultMap2
	 * @param name
	 * @return Event ID
	 */
	public static int getEventID(Map<Integer, String[]> resultMap2, String name) {
		int id = 0;

		for (int i = 0; i < resultMap2.size(); i++) {
			if (resultMap2.get(i)[3].equalsIgnoreCase(name)) {
				id = Integer.parseInt((resultMap2.get(i)[0]));
				return id;
			}
		}

		return -1;
	}
}
