package org.itp1.yamtlib.files;
import org.apache.commons.io.FilenameUtils;
import org.itp1.yamtlib.errors.YamtException;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileHandler {

    public List<File> collectedFiles = new ArrayList<>();

    public boolean searchRecursive;

    public FileHandler() {

    }

    public void collectFilesFromFolder(String path) {
        File folder = new File(path);

        if (folder.exists()) {
            File[] listOfFiles = folder.listFiles();

            for (final File fileEntry : listOfFiles) {
                if (fileEntry.isFile()) {
                    addFileToCollection(fileEntry);
                } else if (fileEntry.isDirectory()) {
                    if (searchRecursive) {
                        collectFilesFromFolder(fileEntry.getPath());
                    }
                }
            }
        }
    }

    public void collectFilesFromFileList(String paths[]) {
        for (String path : paths) {
            File file = new File(path);

            if (file.exists()) {
                addFileToCollection(file);
            }
        }
    }

    public void addFileToCollection(File file) {
        // TODO: error handling, duplicates
        if (isFileTypeSupported(file)) {
            collectedFiles.add(file);
        }
    }

    public void removeFileFromCollection(File file) {
        collectedFiles.remove(file);
    }

    public void clearCollection() {
        collectedFiles.clear();
    }

    private boolean isFileTypeSupported(File file) {
        String[] validExtentions = new String[]{"mp3", "wav", "ogg"};
        return Arrays.asList(validExtentions).contains(FilenameUtils.getExtension(file.getPath()));
    }

    public void printCollection() {
        for (File file : collectedFiles) {
            System.out.println(file.getPath());
        }
    }
}

