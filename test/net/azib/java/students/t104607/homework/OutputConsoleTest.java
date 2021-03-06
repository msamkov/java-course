package net.azib.java.students.t104607.homework;

import org.apache.log4j.PropertyConfigurator;
import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import static junit.framework.Assert.assertEquals;

/**
 * @author 104607 IASM
 */
public class OutputConsoleTest {
	@Before
	public void disableLog4J() {
		PropertyConfigurator.configure(OutputConsole.class.getResource("log4j.disable.properties"));
	}

	@Test
	public void testSave() throws Exception {
		List<Athlete> athletes = new ArrayList<Athlete>();
		DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		StringWriter outputWriter = new StringWriter();

		athletes.add(new Athlete("I.Murumüts",dateFormat.parse("19.06.1980"),"EE",
				9.58, 8.95, 23.12, 2.45, 43.18, 12.87, 74.08, 6.14, 98.48, 206.0));

		OutputConsole outputConsole = new OutputConsole();
		outputConsole.out = new PrintWriter(outputWriter);
		outputConsole.save(athletes);

		assertEquals(true, outputWriter.toString().endsWith("I.Murumüts\n"));
	}
}
