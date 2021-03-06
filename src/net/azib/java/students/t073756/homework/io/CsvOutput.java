package net.azib.java.students.t073756.homework.io;

import net.azib.java.students.t073756.homework.Athlete;

import java.io.File;
import java.util.List;

/**
 * Class respond for creating csv output.
 */
public class CsvOutput extends AbstractFileOutput {
    public CsvOutput(File outputFile) {
        super(outputFile);
    }

    /**
     * writes athletes in csv format to file
     *
     * @param athletes athletes data
     */
    public void writeAthletes(List<Athlete> athletes) {
        StringBuilder content = new StringBuilder();
        for (Athlete a : athletes) {
            content.append(a.toString()).append("\n");
        }
        writeToFile(content.toString());
    }
}
