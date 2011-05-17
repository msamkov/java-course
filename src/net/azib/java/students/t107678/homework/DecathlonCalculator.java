package net.azib.java.students.t107678.homework;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.sql.SQLException;

public class DecathlonCalculator {

    final static String CONSOLE = "-console";
    final static String CSV = "-csv";

    final static String DB = "-db";
    final static String XML = "-xml";
    final static String HTML = "-html";


    public static void main(String[] args) throws IOException, RecordFormatException, SQLException {

        ValidationOfProgramArgs validationOfProgramArgs = new ValidationOfProgramArgs();

        if (validationOfProgramArgs.validateProgramArguments(args)==false) {
            System.exit(1);
        }

        if (args[0].trim().compareToIgnoreCase(CONSOLE) == 0) {      //our input is CONSOLE

            ConsoleReader reader = new ConsoleReader();

            ResultsComputation resultsComputation = new ResultsComputation();
            resultsComputation.readRecords(reader);
            resultsComputation.computeRecordsResults();

            if (args[1].trim().compareToIgnoreCase(CONSOLE) == 0) {

                ConsoleWriter writer = new ConsoleWriter();
                writer.writeOutput(resultsComputation);
                System.out.println("Decathlon competition results: ");

            }

            if (args[1].trim().compareToIgnoreCase(CSV) == 0) {

                CSVWriter writer = new CSVWriter(args[2]);
                writer.writeOutput(resultsComputation);
                System.out.println("Decathlon results are stored in specified file");  //to be removed in final code
            }

            if (args[1].trim().compareToIgnoreCase(XML) == 0) {

                XMLWriter writer = new XMLWriter(args[2]);
                writer.writeOutput(resultsComputation);
                System.out.println("Decathlon results are stored in specified XML file");  //to be removed in final code

            }

            if (args[1].trim().compareToIgnoreCase(HTML) == 0) {

                HTMLWriter writer = new HTMLWriter(args[2]);
                writer.writeOutput(resultsComputation);
                System.out.println("Decathlon results are stored in specified HTML file");  //to be removed in final code
            }

            // IOUtils.closeQuietly((Closeable) reader);
        } else if (args[0].trim().compareToIgnoreCase(CSV) == 0) {        //Our input is CSV file

            CSVReader reader = new CSVReader(args[1]);

            ResultsComputation resultsComputation = new ResultsComputation();
            resultsComputation.readRecords(reader);
            resultsComputation.computeRecordsResults();

            if (args[2].trim().compareToIgnoreCase(CSV) == 0) {

                CSVWriter writer = new CSVWriter(args[3]);
                writer.writeOutput(resultsComputation);
                System.out.println("Decathlon results are stored in specified CSV file");  //to be removed in final code
            }
            if (args[2].trim().compareToIgnoreCase(CONSOLE) == 0) {

                ConsoleWriter writer = new ConsoleWriter();
                writer.writeOutput(resultsComputation);
                System.out.println("Decathlon competition results: ");
            }
            if (args[2].trim().compareToIgnoreCase(XML) == 0) {

                XMLWriter writer = new XMLWriter(args[3]);
                writer.writeOutput(resultsComputation);
                System.out.println("Decathlon results are stored in specified XML file");  //to be removed in final code

            }
            if (args[2].trim().compareToIgnoreCase(HTML) == 0) {

                HTMLWriter writer = new HTMLWriter(args[3]);
                writer.writeOutput(resultsComputation);
                System.out.println("Decathlon competition results are stored in specified HTML file");  //to be removed in final code
            }

            //IOUtils.closeQuietly((Closeable) reader);


        } else if (args[0].trim().compareToIgnoreCase(DB) == 0) {   //We take data from database

            DataBaseReader reader = new DataBaseReader(new DataBaseConnector(), args[1]);

            ResultsComputation resultsComputation = new ResultsComputation();
            resultsComputation.readRecords(reader);
            resultsComputation.computeRecordsResults();

            if (args[2].trim().compareToIgnoreCase(CONSOLE) == 0) {

                ConsoleWriter writer = new ConsoleWriter();
                writer.writeOutput(resultsComputation);
                System.out.println("Decathlon competition results: ");
            }
            if (args[2].trim().compareToIgnoreCase(CSV) == 0) {

                CSVWriter writer = new CSVWriter(args[3]);
                writer.writeOutput(resultsComputation);
                System.out.println("Decathlon competition results are stored in specified CSV  file");  //to be removed in final code
            }
            if (args[2].trim().compareToIgnoreCase(XML) == 0) {

                XMLWriter writer = new XMLWriter(args[3]);
                writer.writeOutput(resultsComputation);
                System.out.println("Decathlon competition results are stored in specified XML file");  //to be removed in final code

            }

            if (args[2].trim().compareToIgnoreCase(HTML) == 0) {

                HTMLWriter writer = new HTMLWriter(args[3]);
                writer.writeOutput(resultsComputation);
                System.out.println("Decathlon competition results are stored in specified HTML file");  //to be removed in final code
            }

            reader.connector.getConnection().close();
        } else {
            System.out.println("The input or output is not recognized/specified correctly. Try again!");
        }


    }


}





