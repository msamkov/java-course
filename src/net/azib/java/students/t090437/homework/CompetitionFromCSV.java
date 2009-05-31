package net.azib.java.students.t090437.homework;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.SortedSet;
import java.util.TreeSet;


/**
 * CompetitorsFromCSV
 *
 * @author Ronald
 */
public class CompetitionFromCSV implements CompetitionDataLoader {
	BufferedReader reader;
	String fileName;
	SortedSet<Competitor> competitors;
	
	public CompetitionFromCSV(String fileName) {
		this.fileName = fileName;
		competitors = new TreeSet<Competitor>();
	}
	
	private Competitor parseCompetitor(String csvLine) throws BadDataFormatException {
		Competitor comp = new Competitor();
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		String[] fields = csvLine.split(",");
		
		if(fields.length != 13) {
			throw
			new BadDataFormatException("Wrong number of fields in CSV line : " + csvLine);
		}
		comp.setName(fields[0].substring(1, fields[0].length() - 1));
		try {
			comp.setBirthday(sdf.parse(fields[1]));
		}
		catch (ParseException e) {
			throw new BadDataFormatException("Bad format for date field :" + fields[2]);
		}
		comp.setCountry(fields[2]);
		comp.setSprint_100m_s(MyTime.createObj(fields[3]));
		comp.setLong_jump_m(MyDouble.parseDouble(fields[4]));
		comp.setShot_put_m(MyDouble.parseDouble(fields[5]));
		comp.setHigh_jump_m(MyDouble.parseDouble(fields[6]));
		comp.setSprint_400m_m_s(MyTime.createObj(fields[7]));
		comp.setHurdles_s(MyTime.createObj(fields[8]));
		comp.setDiscus(MyDouble.parseDouble(fields[9]));
		comp.setPole_vault(MyDouble.parseDouble(fields[10]));
		comp.setJavelin_throw(MyDouble.parseDouble(fields[11]));
		comp.setRace_1500m_m_s(MyTime.createObj(fields[12]));		
		
		return comp;
	}

	public SortedSet<Competitor> getResults() {
		return competitors;
	}

	public void loadAndProcessData() throws DataLoadException {
		Competitor comp = null;
		boolean firstQuoteFound = false;
		try {
			reader = new BufferedReader(new FileReader(fileName));
			while(reader.ready()) {
				// Hack for getting rid of BOM
				while(reader.ready() && !firstQuoteFound) {
					if(reader.read() == '"') {
						firstQuoteFound = true;
						comp = parseCompetitor("\"" + reader.readLine().trim());
						comp.calcScore();
						competitors.add(comp);
						continue;
					}
				} // End of hack
				comp = parseCompetitor(reader.readLine().trim());
				comp.calcScore();
				competitors.add(comp);
			}
			PositionCalculator.calcPositions(competitors);
		} catch(FileNotFoundException exc) {
			throw new DataLoadException("File \"" + fileName +"\" not found.");
		}
		catch(IOException exc) {
			throw new DataLoadException("Failed to read file : " + exc);
		}
		catch (BadDataFormatException e) {
			throw new DataLoadException("Error in parsing : " + e);
		}
		finally {
			try {
				reader.close();
			}
			catch (IOException e) {
			}
		}
	}
}
