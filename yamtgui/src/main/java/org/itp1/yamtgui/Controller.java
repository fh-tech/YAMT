package org.itp1.yamtgui;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;

import org.itp1.yamtlib.Yamt;
import org.itp1.yamtlib.config.*;
import org.itp1.yamtlib.errors.YamtException;


public class Controller {
    public TextField formatTextField;
    public TextField outputTextField;
    public CheckBox checkBoxRecursive;
    public RadioButton radioMove;
    public RadioButton radioCopy;
    public ChoiceBox choiceFetch;
    public Button buttonAddFile;
    public Button buttonRemoveFile;
    public Button buttonReformatFiles;

    public ListView listViewSelectedFiles;
    public TextArea textAreaOutput;

    public ObservableList musicFiles = FXCollections.observableArrayList();

    public Controller() {

        /*
        Console console = new Console(textAreaOutput);
        PrintStream ps = new PrintStream(console, true);
        System.setOut(ps);
        System.setErr(ps);
        */

    }

    @FXML
    public void initialize() {
        // init radioCopyOrMove
        radioCopy.setSelected(true);

        // init choiceFetch
        choiceFetch.setItems(FXCollections.observableArrayList(
                "never",
                "missing",
                "always"
        ));
        choiceFetch.getSelectionModel().selectFirst();

        // init focus on formatTextField, doesn't work
        formatTextField.requestFocus();

        // TODO: input validation, output folder with file chooser, console to output, ...
    }

    public void addFileOrFolder(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Music File or Folder");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Mp3 Files", "*.mp3"),
                new FileChooser.ExtensionFilter("Flac Files", "*.flac"),
                new FileChooser.ExtensionFilter("Ogg Files", "*.ogg"),
                new FileChooser.ExtensionFilter("Wav Files", "*.wav")
        );
        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(null);

        if (selectedFiles != null) {
            textAreaOutput.setText("Music files selected [" + selectedFiles.size() + "]");
            musicFiles.addAll(selectedFiles);
        } else {
            textAreaOutput.setText("Music file selection cancelled.");
        }

        listViewSelectedFiles.setItems(musicFiles);
    }

    public void removeFileOrFolder(ActionEvent actionEvent) {
         // listViewSelectedFiles.getSelectionModel().selectedItemProperty();
    }

    public void runYamt(ActionEvent actionEvent) throws YamtException {

        YamtConfig config = ConfigFactory.generate(() -> new IncompleteYamtConfig(
                musicFiles,
                new File(outputTextField.getText()), // TODO: path relative to project
                formatTextField.getText(),
                checkBoxRecursive.isSelected(),
                false, // TODO: moveOnRename
                MetaDataStrategy.fromString(choiceFetch.getValue().toString())
                ));
        Yamt yamt = new Yamt();

        /*
        new Thread(() -> {
            try {
                yamt.runYamt(config);
            } catch (YamtException e) {
                e.printStackTrace();
            }
        }).start();
        */

        try {
            yamt.runYamt(config);
        }catch (Exception e){
            e.printStackTrace();
            System.exit(1);
        }
    }

    /*
    public static class Console extends OutputStream {

        private TextArea output;

        public Console(TextArea ta) {
            this.output = ta;
        }

        @Override
        public void write(int i) throws IOException {
            output.appendText(String.valueOf((char) i));
        }
    }
    */
}
