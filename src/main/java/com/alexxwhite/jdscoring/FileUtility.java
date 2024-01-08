package com.alexxwhite.jdscoring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;


/**
 * location
 * salary
 *
 * graph: horiz - score, vert - salary, size - same
 *
 */
@Component
public class FileUtility {

    public void copyAndDelete(Path sourceFile,
                                     Path destinationDir,
                                     String newFileName) {

        Path destinationFile = destinationDir.resolve(newFileName);
        try {
            // Copy the file to the new directory with the new name
            Files.copy(sourceFile, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("File copied successfully.");

            // Delete the original file
            Files.delete(sourceFile);
            System.out.println("Original file deleted successfully.");
        } catch (IOException e) {
            System.err.println("I/O Error occurred: " + e.getMessage());
        }
    }

}
