package net.azib.java.students.t092861.homework;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class IOcsvTest {
	
	private String outFile = "outputTest.csv";
	private File f;
	private File f_in;

	private String[] lines = {
	"\"Siim Susi\",01.01.1976,EE,12.61,5.00,9.22,1.50,59.39,16.43,21.60,2.60,35.81,5:25.72",
	"\"José Mañana Peréz\",03.04.1972,ES,13.75,4.84,10.12,1.50,1:08.44,19.18,30.85,2.80,33.88,6:22.75",
	"\"Jeremy Dunkleverylongname\",21.05.1981,US,11.23,6.62,11.49,1.75,51.97,14.89,33.37,3.98,45.60,5:24.26",
	"\"Jürgen Schult\",11.05.1960,DE,11.39,6.69,10.00,2.05,49.52,15.72,24.39,3.38,30.37,4:41.38"
	};

	@Test
	public void testInputResultNotEmpty() {
		IOcsv csv = new IOcsv(f_in.getPath(), new Controller());
		ArrayList<Athlete> athletes = csv.input();
		assertTrue(athletes.size()>0);
	}
	
	private void createTestInputFile(){
		Writer writer = null;
		try {
			f_in = new File("IOcsvTestInput.csv");
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f_in), "UTF8"));
			for (int i = 0; i < lines.length; i++) {
				writer.write(lines[i]);
				((BufferedWriter) writer).newLine();
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				writer.close();
			} catch (IOException e) {
				System.out.println("\nERROR! Cannot close the file! "  + e.toString());
			}
		}
	}
	
	@Test
	public void testInputResultArraySize() throws IOException{
		IOcsv csv = new IOcsv(f_in.getPath(), new Controller());
		ArrayList<Athlete> list = csv.input();
		assertEquals("Arrays are not the same length", getNumberOfLinesInFile(f_in.getPath()), list.size());
	}
	
	private int getNumberOfLinesInFile(String file)  throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(file));
		int numberOfAthletes = 0;
		while ((in.readLine()) != null) {
			numberOfAthletes++;
		}
		in.close();
		return numberOfAthletes;
	}
	
	@Test
	public void testInputResultsCorrect() throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(f_in));
		
		IOcsv csv = new IOcsv(f_in.getPath(), new Controller());
		ArrayList<Athlete> list = csv.input();
		
		String[] array = null;
		int index = 0;
		String s = null;
		while ((s = in.readLine()) != null) {
			array = s.split(",");
			compare(list.get(index++), array);
		}
		in.close();
	}

	private void compare(Athlete athlete, String[] array2) {
		Object[] array = athlete.toString().split(",");
		for (int i = 0; i < array.length; i++) {
			assertEquals(array2[i], (String) array[i]);
		}
	}

    @Before
    public void createTestOutputData() throws IOException {
    	createTestInputFile();
		ArrayList<Athlete> athletes = new ArrayList<Athlete>();
		for (int i = 0; i < lines.length; i++) {
			ArrayList<String> inputData = new ArrayList<String>(Arrays.asList(lines[i].split(",")));
			athletes.add(new Controller().readData(inputData));
		}
        f = new IOcsv(outFile, new Controller()).output(athletes);
    }

	@Test
	public void testCsvOutputFileExists() throws IOException {
		assertThat(f.exists(), is(true));
	}
	
	@Test
	public void testCsvOutputResultsCount() throws IOException {
		assertThat(getNumberOfLinesInFile(f.getName()), is(lines.length));
	}

	@After
	public void cleanUp() {
		if (f.exists()) {
			f.deleteOnExit();
		}
		if (f_in.exists()) {
			f_in.deleteOnExit();
		}
	}
}
