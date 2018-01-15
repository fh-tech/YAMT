package org.itp1.yamtgui;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
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
    public Button buttonChooseOutputFolder;
    public Button buttonAddFile;
    public Button buttonAddFolder;
    public Button buttonRemoveFile;
    public Button buttonReformatFiles;

    public ListView listViewSelectedFiles;
    public TextArea textAreaOutput;

    private ObservableList musicFiles = FXCollections.observableArrayList();
    private File chosenOutputFolder;

    public Controller() {}

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

        // init listViewSelectedFiles
        listViewSelectedFiles.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        // init focus on formatTextField
        Platform.runLater(() -> formatTextField.requestFocus());

        // init console output to textAreaOutput
        OutputStream out = new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                appendText(String.valueOf((char) b));
            }
        };
        System.setOut(new PrintStream(out, true));
    }

    private void appendText(String str) {
        Platform.runLater(() -> textAreaOutput.appendText(str));
    }

    public void chooseOutputFolder(ActionEvent actionEvent) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Choose Output Folder");

        chosenOutputFolder = directoryChooser.showDialog(null);

        if (chosenOutputFolder != null) {
            outputTextField.setText(chosenOutputFolder.getPath());
        }
    }

    public void addFile(ActionEvent actionEvent) {
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
            musicFiles.addAll(selectedFiles);
        }

        listViewSelectedFiles.setItems(musicFiles);
    }

    public void addFolder(ActionEvent actionEvent) {
        // Javafx directly only supports single folder choosers

        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Music Folder");
        // directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        File selectedFolder = directoryChooser.showDialog(null);

        if (selectedFolder != null) {
            musicFiles.addAll(selectedFolder);
        }

        listViewSelectedFiles.setItems(musicFiles);
    }

    public void removeFileOrFolder(ActionEvent actionEvent) {
        ObservableList selectedFiles = listViewSelectedFiles.getSelectionModel().getSelectedItems();
        musicFiles.removeAll(selectedFiles);

        listViewSelectedFiles.setItems(musicFiles);
    }

    public void runYamt(ActionEvent actionEvent) throws YamtException {

        YamtConfig config = ConfigFactory.generate(() -> new IncompleteYamtConfig(
                musicFiles,
                chosenOutputFolder,
                formatTextField.getText(),
                checkBoxRecursive.isSelected(),
                radioMove.isSelected(),
                MetaDataStrategy.fromString(choiceFetch.getValue().toString())
                ));
        Yamt yamt = new Yamt();

        Platform.runLater(() -> {
            try {
                yamt.runYamt(config);
                System.out.println("Music Files successfully reformatted.");
                musicFiles.clear();
                listViewSelectedFiles.setItems(musicFiles);
            }catch (Exception e){

                e.printStackTrace();
                e.getCause().printStackTrace();
                e.getCause().getCause().printStackTrace();

                //System.exit(1);
        }});

    }
}
