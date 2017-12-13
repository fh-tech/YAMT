package org.itp1.yamtlib.files;
import lombok.NonNull;
import org.apache.commons.io.FilenameUtils;
import org.itp1.yamtlib.config.YamtConfig;
import org.itp1.yamtlib.errors.YamtException;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class FileHandler {

    public FileHandler() {

    }


    /***
     * Call function
     * @param cfg
     * @return
     * @throws YamtException.FilesException
     */
    /*
    public List<File> collectViaConfig(YamtConfig cfg) throws YamtException.FilesException {
        return collectViaConfig(cfg.getMusicDir(), cfg.recursive());
    }
    */


    /***
     * Call function
     * @param files
     * @return
     * @throws YamtException.FilesException
     */
    public List<File> collectFilesRecursive(List<File> files) throws YamtException.FilesException {
        return collectFiles(files, true, true);
    }

    public List<File> collectFilesRecursive(File file) throws YamtException.FilesException {
        return collectFilesRecursive(new ArrayList<>(Arrays.asList(file)));
    }

    /***
     * Call function
     * @param files
     * @return
     * @throws YamtException.FilesException
     */
    public List<File> collectFilesNonRecursive(List<File> files) throws YamtException.FilesException {
        return collectFiles(files, false, true);
    }

    public List<File> collectFilesNonRecursive(File file) throws YamtException.FilesException {
        return collectFilesNonRecursive(new ArrayList<>(Arrays.asList(file)));
    }

    /***
     * Collects all files from the filesystem that are included in files, if recursive is set
     * folders get searched recursively and files get collected
     * Files with an unsupported file extensions get ignored
     * @param files List of files and/or folders which get collected
     * @param searchRecursive Decides if folders get searched recursively
     * @param isTopLevel To collect from folders in param files by using the function once recursively with listFiles()
     * @return A collection of files
     * @throws YamtException.FilesException when file/folder doesn't exist, file is not readable, file is not writable
     */
    private List<File> collectFiles(@NonNull List<File> files, boolean searchRecursive, boolean isTopLevel) throws YamtException.FilesException {
        HashSet<File> collectedFiles = new HashSet<>();

        for (final File fileOrFolder : files) {
            if (fileOrFolder.canRead() && fileOrFolder.canWrite()) {
                if (fileOrFolder.isFile()) {
                    if (isFileExtensionSupported(fileOrFolder)){
                        collectedFiles.add(fileOrFolder);
                    }
                } else {
                    if (searchRecursive || isTopLevel) {
                        collectedFiles.addAll(collectFiles(Arrays.asList(fileOrFolder.listFiles()), searchRecursive, false));
                    }
                }
            } else {
                if (!fileOrFolder.exists()) {
                    throw new YamtException.FilesException("Nonexistent File or Folder [" + fileOrFolder.getAbsolutePath() + "]");
                } else if (!fileOrFolder.canRead()) {
                    throw new YamtException.FilesException("File is not readable [" + fileOrFolder.getAbsolutePath() + "]");
                } else if (!fileOrFolder.canWrite()) {
                    throw new YamtException.FilesException("File is not writable [" + fileOrFolder.getAbsolutePath() + "]");
                }
            }
        }
        return new ArrayList<File>(collectedFiles);
    }

    /***
     * Checks if file has a supported extension
     * @param f file of which to check extension
     * @return whether or not file extension is supported
     */
    private boolean isFileExtensionSupported(@NonNull File f) {
        // TODO: get real list of supported Extentions, all in lowercase
        String[] validExtentions = new String[]{"mp3", "wav", "ogg"};
        return Arrays.asList(validExtentions).contains(FilenameUtils.getExtension(f.getPath()).toLowerCase());
    }
}
