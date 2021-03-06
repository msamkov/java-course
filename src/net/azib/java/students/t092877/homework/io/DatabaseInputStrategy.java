package net.azib.java.students.t092877.homework.io;

import net.azib.java.students.t092877.homework.util.Utils;
import net.azib.java.students.t092877.homework.model.Athlete;
import net.azib.java.students.t092877.homework.model.Competition;
import net.azib.java.students.t092877.homework.model.Event;
import net.azib.java.students.t092877.homework.model.Result;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * DatabaseInputStrategy.java
 * Purpose: provides implementation for database input
 *
 * @author Artjom Kruglenkov / 092877
 * @version 1.0 20.05.2011
 */
public class DatabaseInputStrategy implements Strategy {

	private String dbQueryId;

	/**
	 * Creates a new DatabaseInputStrategy instance from provided parameter string.
	 *
	 * @param parameter a competition id or competition name
	 */
	public DatabaseInputStrategy(String parameter) {

		if (parameter.matches("[1-9][0-9]*")) {
			this.dbQueryId = parameter;
		} else if (parameter.matches("\\w+")) {
			this.dbQueryId = "SELECT id FROM competitions WHERE name = '" + parameter + "'";
		}
	}


	/**
	 * Executes the implementation for database input.
	 *
	 * @param competition an instance of decathlon competition
	 */
	@Override
	public void execute(Competition competition) {

		try {

			Connection con = establishConnection(loadProperties());

			ResultSet competitionInfo = getCompetitionInfo(con, dbQueryId);
			ResultSet athletesResults = getAthletesData(con, dbQueryId);
			setDecathlonEventData(competition, competitionInfo, athletesResults);

		} catch (NullPointerException e) {
			System.err.println("\n>>> ERROR: the db.properties file is missing or has an invalid format.");
			competition.setAthletesList(null);
			System.exit(1);
		}
	}


	/**
	 * Loads the file with db.properties.
	 *
	 * @return db.properties object
	 * @throws NullPointerException if the db.properties file doesn't exist or empty
	 */
	private Properties loadProperties() throws NullPointerException {

		Properties props = new Properties();

		try {
			props.load(DatabaseInputStrategy.class.getResourceAsStream("db.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return props;
	}

	/**
	 * Establishes a connection to database.
	 *
	 * @param propslist the db.properties
	 * @return the database connection
	 */
	private Connection establishConnection(Properties propslist) {

		String driver = propslist.getProperty("db.driver");
		String url = propslist.getProperty("db.url");
		String username = propslist.getProperty("db.username");
		String password = propslist.getProperty("db.password");

		Connection con = null;

		try {

			Class.forName(driver);
		    con = DriverManager.getConnection(url, username, password);

		    if(!con.isClosed())
		    	System.out.println("Successfully connected to the database - " + url);

	    } catch(Exception e) {
	      System.err.println("Exception: " + e.getMessage());

	      try {
		        if(con != null)
		        	con.close();
		  } catch(SQLException exc) {}
	    }

	    return con;
	}

	/**
	 * Returns result set with decathlon competition data.
	 *
	 * @param con the database connection
	 * @param dbQueryId the database query
	 * @return result set with decathlon competition data
	 */
	private ResultSet getCompetitionInfo(Connection con, String dbQueryId) {

		ResultSet competitionRs = null;
		Statement st;

		try {

			st = con.createStatement();
			competitionRs = st.executeQuery("SELECT competitions.name," +
					  							   "competitions.location," +
					  							   "competitions.date " +
					  					    "FROM competitions " +
					  					    "WHERE competitions.id IN (" + dbQueryId + ");");

		} catch (SQLException e) {
			System.err.println("\n>>> ERROR: while processing SQL query");
			e.printStackTrace();
		}

		return competitionRs;
	}

	/**
	 * Returns result set with athletes data.
	 *
	 * @param con the database connection
	 * @param dbQueryId the database query
	 * @return result set with athletes data
	 */
	private ResultSet getAthletesData(Connection con, String dbQueryId) {

		ResultSet athletesRs = null;
		Statement st;

		try {
			st = con.createStatement();
			athletesRs = st.executeQuery("SELECT results.id," +
												"athletes.name athlete_name," +
												"athletes.dob date_of_birth," +
												"athletes.country_code," +
											 	"results.race_100m," +
											 	"results.long_jump," +
											 	"results.shot_put," +
												"results.high_jump," +
												"results.race_400m," +
												"results.hurdles_110m," +
												"results.discus_throw," +
												"results.pole_vault," +
												"results.javelin_throw," +
												"results.race_1500m " +
										 "FROM athletes," +
											  "results " +
										 "WHERE athletes.id = results.athlete_id " +
										 "AND results.competition_id IN (" + dbQueryId + ");");

		} catch (SQLException e) {
			System.err.println("\n>>> ERROR: while processing SQL query");
			e.printStackTrace();
		}

		return athletesRs;
	}

	/**
	 * Sets the data for specified Competition instance with athletes and competition result sets.
	 *
	 * @param comp an instance of decathlon competition
	 * @param compRs result set with decathlon competition data
	 * @param athRs  result set with athletes data
	 */
	private void setDecathlonEventData(Competition comp, ResultSet compRs, ResultSet athRs) {

		List<Athlete> athletes = comp.getAthletesList();
		List<Result> results;
		Athlete athlete;

		try {

			if (compRs.next()) {

				do {

					comp.setName(compRs.getString("name"));
					comp.setLocation(compRs.getString("location"));
					comp.setDate(compRs.getDate("date").toString());

				} while (compRs.next());

			} else {
				System.err.println("\n>>> ERROR: the competition with specified id or name is not available in the database");
				comp.setAthletesList(null);
				System.exit(1);
			}

		} catch (SQLException e) {
			System.err.println("\n>>> ERROR: while getting competition data from db");
			e.printStackTrace();
		}

		try {

			while (athRs.next()) {

				athlete = new Athlete(athRs.getString("athlete_name"),
									  athRs.getDate("date_of_birth").toString(),
									  athRs.getString("country_code"));

				results = new ArrayList<Result>();

				int i = 5;
				for (Event event : Event.values()) {
					double value = Utils.convertToProperUnits(String.valueOf(athRs.getDouble(i)), event.getType());
					results.add(new Result(event, value));
					i++;
				}

				athlete.setResults(results);
				athletes.add(athlete);
			}
		} catch (SQLException e) {
			System.err.println("\n>>> ERROR: while getting athletes data from db");
			e.printStackTrace();
		}
	}
}
