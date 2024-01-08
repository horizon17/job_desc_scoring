package com.alexxwhite.jdscoring;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * location
 * salary
 *
 * graph: horiz - score, vert - salary, size - same
 *
 */
@Component
public class FileController {

    @Autowired
    MainComponent mainComponent;

    public void openLocalFile(final String fileId) {

        String fileName = mainComponent.sharedMap.get(fileId);
        if (fileName == null) {
            System.out.println("file not found");
            return;
        }

        fileName = fileName.replace("\\","\\\\");

        try {
            //String filePath = "c:\\temp\\ab81_and_dev\\Hybrid onsite role -- Role_ Sr. Java Developer -- Location_ Coral Springs, FL _ Berkeley Heights, NJ -- Duration_ Contract __ Fulltime-20240105-9773.eml";
            String command = "cmd /c start \"\" \"" + fileName + ".eml\"";

            Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            System.out.println(fileName);
            e.printStackTrace();
        }
    }

}
