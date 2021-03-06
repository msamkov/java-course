package net.azib.java.students.t092855.homework;

import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import static org.junit.Assert.assertEquals;

/**
 * InputConsoleTest
 * Unit tests for {@link InputConsole}
 *
 * @author t092855
 */
public class InputConsoleTest {

	/**
	 * Unit test for {@link InputConsole#getData(String...)}
	 */
	@Test
	public void athleteAddingStopWorks() {
		String inputString = "n" + System.getProperty("line.separator");
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

		InputConsole inputConsole =  new InputConsole();
		inputConsole.input = new Scanner(inputString);
		inputConsole.output = new PrintStream(outputStream);
		inputConsole.getData();

		assertEquals("Do you want to add an athlete? (y/n)" + System.getProperty("line.separator") +
				"Athlete adding completed." + System.getProperty("line.separator"), outputStream.toString());
	}

	/**
	 * Unit test for {@link InputConsole#getData(String...)}
	 * @throws java.text.ParseException if parsing date fails
	 */
	@Test
	public void athleteAddingWorks() throws ParseException {
		SimpleDateFormat dateFormat = (SimpleDateFormat) DateFormat.getDateInstance(DateFormat.MEDIUM);
		Date date = new SimpleDateFormat("dd.MM.yyyy").parse("23.04.1987");
		String dateString = dateFormat.format(date);

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		String inputString  = "y" + System.getProperty("line.separator") +
				"Siim Piim" + System.getProperty("line.separator") +
				"EE" +  System.getProperty("line.separator") +
				dateString + System.getProperty("line.separator") +
				"9.4" + System.getProperty("line.separator") +
				"5.67" + System.getProperty("line.separator") +
				"13.2" + System.getProperty("line.separator") +
				"2.27" + System.getProperty("line.separator") +
				"45.68" + System.getProperty("line.separator") +
				"13.47" + System.getProperty("line.separator") +
				"55.87" + System.getProperty("line.separator") +
				"5.76" + System.getProperty("line.separator") +
				"79.80" + System.getProperty("line.separator") +
				"3:58.07" + System.getProperty("line.separator") +
				"n" + System.getProperty("line.separator");

		InputConsole inputConsole =  new InputConsole();
		inputConsole.input = new Scanner(inputString);
		inputConsole.output = new PrintStream(outputStream);
		Competition competition = inputConsole.getData();

		Athlete athlete = competition.getCompetitors().iterator().next();
		assertEquals("Siim Piim", athlete.getName());
		assertEquals("EE", athlete.getCountry());
		assertEquals(dateString, dateFormat.format(athlete.getBirthday()));
		assertEquals(9727, athlete.getAthleteEvents().getTotalPoints());
		assertEquals(9.4, athlete.getAthleteEvents().getDecathlonResults()[0], 0.0);
		assertEquals(5.67, athlete.getAthleteEvents().getDecathlonResults()[1], 0.0);
		assertEquals(13.2, athlete.getAthleteEvents().getDecathlonResults()[2], 0.0);
		assertEquals(2.27, athlete.getAthleteEvents().getDecathlonResults()[3], 0.0);
		assertEquals(45.68, athlete.getAthleteEvents().getDecathlonResults()[4], 0.0);
		assertEquals(13.47, athlete.getAthleteEvents().getDecathlonResults()[5], 0.0);
		assertEquals(55.87, athlete.getAthleteEvents().getDecathlonResults()[6], 0.0);
		assertEquals(5.76, athlete.getAthleteEvents().getDecathlonResults()[7], 0.0);
		assertEquals(79.80, athlete.getAthleteEvents().getDecathlonResults()[8], 0.0);
		assertEquals(238.07, athlete.getAthleteEvents().getDecathlonResults()[9], 0.0);
	}
}
