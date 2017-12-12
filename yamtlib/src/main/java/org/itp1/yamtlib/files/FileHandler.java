package org.itp1.yamtlib.files;
import lombok.NonNull;
import org.apache.commons.io.FilenameUtils;
import org.itp1.yamtlib.config.YamtConfig;
import org.itp1.yamtlib.errors.YamtException;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileHandler {

    public FileHandler() {

    }

    public FileHandler(YamtConfig config) {
        // TODO: save config in FileHandler class?
    }

    /***
     *
     * @param paths
     * @return
     * @throws YamtException.FilesException
     */
    public List<File> collectViaConfig(String paths[]) throws YamtException.FilesException {
        boolean config_boolean_placeholder = false;
        return collectFiles(paths, config_boolean_placeholder);
    }

    public List<File> collectViaConfig(String path) throws YamtException.FilesException {
        return collectViaConfig(new String[]{path});
    }

    /***
     *
     * @param paths
     * @return
     * @throws YamtException.FilesException
     */
    public List<File> collectFilesRecursive(String paths[]) throws YamtException.FilesException {
        return collectFiles(paths, true);
    }

    public List<File> collectFilesRecursive(String path) throws YamtException.FilesException {
        return collectFilesRecursive(new String[]{path});
    }

    /***
     *
     * @param paths
     * @return
     * @throws YamtException.FilesException
     */
    public List<File> collectFilesNonRecursive(String paths[]) throws YamtException.FilesException {
        return collectFiles(paths, false);
    }

    public List<File> collectFilesNonRecursive(String path) throws YamtException.FilesException {
        return collectFilesNonRecursive(new String[]{path});
    }

    /***
     *
     * @param paths
     * @param searchRecursive
     * @return
     * @throws YamtException.FilesException
     */
    private List<File> collectFiles(@NonNull String paths[], boolean searchRecursive) throws YamtException.FilesException {
        List<File> collectedFiles = new ArrayList<>();

        try {
            for (final String path : paths) {
                File fileOrFolder = new File(path);

                if (fileOrFolder.exists()) {
                    if (fileOrFolder.isFile()) {
                        addFileToCollection(collectedFiles, fileOrFolder);
                    } else {
                        if (searchRecursive) {
                            collectFiles(new String[]{fileOrFolder.getAbsolutePath()}, searchRecursive);
                        }
                    }
                }
                else {
                    // TODO: error message for nonexistent path?
                    throw new YamtException.FilesException("Nonexistent File or Folder");
                }
            }
        } catch (YamtException.FilesException fe) {
            throw fe;
        }
        return collectedFiles;
    }

    /***
     *
     * @param collection
     * @param f
     */
    private void addFileToCollection(List<File> collection, File f) {
        try {
            if (isFileTypeSupported(f) && !collection.contains(f)) {
                collection.add(f);
            }
        } catch (NullPointerException e) {
            throw e;
        }
    }

    /***
     *
     * @param f
     * @return
     */
    private boolean isFileTypeSupported(File f) {
        try {
            // TODO: get real list of supported Extentions, all in lowercase
            String[] validExtentions = new String[]{"mp3", "wav", "ogg"};
            return Arrays.asList(validExtentions).contains(FilenameUtils.getExtension(f.getPath()).toLowerCase());
        } catch (NullPointerException e) {
            throw e;
        }
    }

}

