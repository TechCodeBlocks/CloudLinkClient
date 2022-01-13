package cloudlink.view;

import cloudlink.Main;
import cloudlink.model.File;
import cloudlink.model.FinderItem;
import com.sun.tools.classfile.Dependency;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableStringValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class MainViewController {
    //Files list for remote tab
    @FXML
    private TableView<FinderItem> remoteFiles;
    @FXML
    private TableColumn<FinderItem, String> remoteFilesFname;
    @FXML
    private TableColumn<FinderItem, String> remoteFilesFdateEdited;
    @FXML
    private TableColumn<FinderItem, String> remoteFilesFtracked;
    //Files List for local tab
    @FXML
    private TableView<FinderItem> localFiles;
    @FXML
    private TableColumn localFilesFname;
    @FXML
    private TableColumn localFilesFdateEdited;
    @FXML
    private TableColumn localFilesFtracked;
    //Labels and text for the remote files tab
    @FXML
    private Label remoteFileNameLabel;
    @FXML
    private Label remoteFileName;
    @FXML
    private Label remoteFileSyncLabel;
    @FXML
    private Label remoteFileSync;
    @FXML
    private Label remoteFileOwnerLabel;
    @FXML
    private Label remoteFileOwner;
    //labels and text for the local files tab
    @FXML
    private Label localFileNameLabel;
    @FXML
    private Label localFileName;
    @FXML
    private Label localFileSyncLabel;
    @FXML
    private Label localFileSync;
    @FXML
    private Label localFileOwnerLabel;
    @FXML
    private Label localFileOwner;

    //Buttons
    @FXML
    private Button remoteFileForwardBtn;
    @FXML
    private Button remoteFileBackwardBtn;
    @FXML
    private Button remoteFileDownloadBtn;
    @FXML
    private Button remoteFileUpdateBtn;

    @FXML
    private Button localFileForwardBtn;
    @FXML
    private Button localFileBackwardBtn;
    @FXML
    private Button localFileUploadBtn;
    @FXML
    private Button localFileUpdateBtn;

    Main main;
    String selectedFolder;

    @FXML
    private void initialize(){
        remoteFilesFname.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        remoteFilesFdateEdited.setCellValueFactory(cellData -> {
            if(cellData.getValue() instanceof File){
                return ((File) cellData.getValue()).dateEditedProperty();
            }
            return null;
        });
        remoteFilesFtracked.setCellValueFactory(cellData -> {
            if(cellData.getValue() instanceof File){
                boolean online = (((File) cellData.getValue()).isOnline());
                if(online){

                    return new SimpleStringProperty("true");
                }else{
                    return new SimpleStringProperty("false");
                }

        }
        return null;
    });

        showRemoteFileDetails(null);


        remoteFiles.getSelectionModel().selectedItemProperty().addListener(
                ((observable, oldValue, newValue) -> {
                    if(newValue == null){
                        remoteFileForwardBtn.setDisable(true);
                        remoteFileDownloadBtn.setDisable(true);
                        remoteFileUpdateBtn.setDisable(true);
                        showRemoteFileDetails(null);
                    }else if(newValue instanceof File){
                    showRemoteFileDetails((File)newValue);
                    remoteFileForwardBtn.setDisable(true);
                    }else{
                        selectedFolder = newValue.getName();
                        remoteFileForwardBtn.setDisable(false);
                    }
                }
        ));
    }


    private void showRemoteFileDetails(File file){
        if(file != null){
            remoteFileName.setText(file.getName());
            switch (file.getStatus()){
                case OUTDATED:
                    remoteFileSync.setText("Outdated");
                    remoteFileUpdateBtn.setDisable(false);
                    remoteFileDownloadBtn.setDisable(true);
                    break;
                case UP_TO_DATE:
                    remoteFileSync.setText("Up to date");
                    remoteFileUpdateBtn.setDisable(true);
                    remoteFileDownloadBtn.setDisable(true);
                    break;
                case NOT_TRAKCED:
                    remoteFileSync.setText("Not tracked");
                    remoteFileUpdateBtn.setDisable(true);
                    remoteFileDownloadBtn.setDisable(false);
                    break;
            }
            remoteFileOwner.setText(file.getDirectParent());
            remoteFileNameLabel.setText("Name:");
            remoteFileSyncLabel.setText("Status:");
            remoteFileOwnerLabel.setText("Parent:");
        }else{
            remoteFileOwner.setText("");
            remoteFileNameLabel.setText("");
            remoteFileSyncLabel.setText("");
            remoteFileOwnerLabel.setText("");

            remoteFileName.setText("");
            remoteFileSync.setText("");

        }

    }

    public void setMain(Main main){
        this.main = main;
        main.getRemoteFiles().setSelectedKey("FilesTest");
        remoteFiles.setItems(main.getRemoteFilesData());
    }
    @FXML
    public void onRemoteIncrementPressed(){
        main.getRemoteFiles().setSelectedKey(selectedFolder);
        main.getRemoteFiles().incrementLayer();
        remoteFiles.setItems(main.getRemoteFilesData());
        selectedFolder ="";
    }
    @FXML
    public void onRemoteDecrementPressed(){
        if(main.getRemoteFiles().getCurrentLayer() != main.getRemoteFiles().getBaseLayer()) {
            main.getRemoteFiles().decrementLayer();
            remoteFiles.setItems(main.getRemoteFilesData());
        }
    }
}
