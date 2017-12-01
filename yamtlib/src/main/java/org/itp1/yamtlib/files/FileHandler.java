package org.itp1.yamtlib.files;
import java.io.*;

public class FileHandler {

    public static void printFilesFromFolder(String path){
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();

        for (final File fileEntry : listOfFiles) {
            if (fileEntry.isFile()) {
                System.out.println("File " + fileEntry.getName());
            } else if (fileEntry.isDirectory()) {
                System.out.println("Directory " + fileEntry.getName());
            }
        }
    }

}