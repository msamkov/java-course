package net.azib.java.students.t030633.homework.view.out;

/**
 * Decathlon output method - all possible output methods and classes that
 * implement them. Handles instantiation errors.
 * 
 * @author t030633
 */
public enum OutputMethod {

	CONSOLE(Console.class), CSV(CSV.class), XML(XML.class), HTML(HTML.class);

	private final Class<? extends Output> outputClass;

	private OutputMethod(Class<? extends Output> clazz) {
		this.outputClass = clazz;
	}

	/**
	 * @return instance of a class that implements Output, return null if unable
	 *         to instantiate
	 */
	public Output getOutput() {
		try {
			return outputClass.newInstance();
		}
		catch (Exception e) {
			System.err.println(outputClass.getSimpleName() + " instantiation error. " + e.getMessage());
			return null;
		}
	}

}