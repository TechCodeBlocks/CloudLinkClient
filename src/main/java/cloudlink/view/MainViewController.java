package cloudlink.view;

import cloudlink.Main;
import cloudlink.model.File;
import cloudlink.model.FinderItem;
import cloudlink.utility.GlobalValues;
import cloudlink.utility.HTTPClient;
import javafx.beans.property.SimpleStringProperty;
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
    private TableColumn<FinderItem, String> localFilesFname;
    @FXML
    private TableColumn<FinderItem, String> localFilesFdateEdited;
    @FXML
    private TableColumn<FinderItem, String> localFilesFtracked;
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
    String selectedRemoteFolder;
    String selectedLocalFolder;
    File selectedFile;

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
        localFilesFname.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        localFilesFdateEdited.setCellValueFactory(cellData -> {
            if(cellData.getValue() instanceof File){
                return ((File) cellData.getValue()).dateEditedProperty();
            }
            return null;
        });
        localFilesFtracked.setCellValueFactory(cellData -> {
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
        showLocalFileDetails(null);



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
                        selectedRemoteFolder = newValue.getName();
                        remoteFileForwardBtn.setDisable(false);
                    }
                }
        ));
        localFiles.getSelectionModel().selectedItemProperty().addListener(
                ((observable, oldValue, newValue) ->{
                    if(newValue == null){
                        localFileForwardBtn.setDisable(true);
                        localFileUploadBtn.setDisable(true);
                        localFileUpdateBtn.setDisable(true);
                        showLocalFileDetails(null);
                    }else if(newValue instanceof File){
                        showLocalFileDetails((File)newValue);
                        remoteFileForwardBtn.setDisable(true);
                    }else{
                        selectedLocalFolder = newValue.getName();
                        localFileForwardBtn.setDisable(false);
                    }
                } )
        );
    }


    private void showRemoteFileDetails(File file){
        if(file != null){
            selectedFile = file;
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

    private void showLocalFileDetails(File file){
        if(file != null){
            selectedFile = file;
            localFileName.setText(file.getName());
            switch (file.getStatus()){
                case OUTDATED:
                    localFileSync.setText("Outdated");
                    localFileUpdateBtn.setDisable(false);
                    localFileUploadBtn.setDisable(true);
                    break;
                case UP_TO_DATE:
                    localFileSync.setText("Up to date");
                    localFileUpdateBtn.setDisable(true);
                    localFileUploadBtn.setDisable(true);
                    break;
                case NOT_TRAKCED:
                    localFileSync.setText("Not tracked");
                    localFileUpdateBtn.setDisable(true);
                    localFileUploadBtn.setDisable(false);
                    break;
            }
            localFileOwner.setText(file.getDirectParent());
            localFileNameLabel.setText("Name:");
            localFileSyncLabel.setText("Status:");
            localFileOwnerLabel.setText("Parent:");
        }else{
            localFileOwner.setText("");
            localFileNameLabel.setText("");
            localFileSyncLabel.setText("");
            localFileOwnerLabel.setText("");

            localFileName.setText("");
            localFileSync.setText("");

        }

    }

    public void setMain(Main main){
        this.main = main;
        main.getRemoteFiles().setSelectedKey("FilesTest");
        remoteFiles.setItems(main.getRemoteFilesData());
    }
    @FXML
    public void onRemoteIncrementPressed(){
        main.getRemoteFiles().setSelectedKey(selectedRemoteFolder);
        main.getRemoteFiles().incrementLayer();
        remoteFiles.setItems(main.getRemoteFilesData());
        selectedRemoteFolder ="";
    }
    @FXML
    public void onRemoteDecrementPressed(){
        if(main.getRemoteFiles().getCurrentLayer() != main.getRemoteFiles().getBaseLayer()) {
            main.getRemoteFiles().decrementLayer();
            remoteFiles.setItems(main.getRemoteFilesData());
        }
    }
    @FXML
    public void onLocalIncrementPressed(){
        main.getLocalFiles().setSelectedKey(selectedLocalFolder);
        main.getRemoteFiles().incrementLayer();
        localFiles.setItems(main.getLocalFilesData());
        selectedLocalFolder = "";
    }

    @FXML
    public void onRemoteDownloadPressed(){
        String id = selectedFile.getUuid();
        String path = GlobalValues.basePath + selectedFile.getName();
        HTTPClient.downloadFile(id, path);
    }

    @FXML
    public void onRemoteUpdatePressed(){
        String id = selectedFile.getUuid();
        String path = selectedFile.getLocalPath();
        HTTPClient.downloadFile(id, path);
    }

    @FXML
    public void onLocalUploadPressed(){
        String id = selectedFile.getUuid();
        String path = GlobalValues.basePath + selectedFile.getName();
        HTTPClient.uploadFile(id, path);
    }

    @FXML
    public void onLocalUpdatePressed(){
        String id = selectedFile.getUuid();
        String path = GlobalValues.basePath + selectedFile.getName();
        HTTPClient.downloadFile(id, path);
    }

}
