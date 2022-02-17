package cloudlink.view;

import cloudlink.Main;
import cloudlink.model.File;
import cloudlink.model.FinderItem;
import cloudlink.utility.GlobalValues;
import cloudlink.utility.JSONWriter;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.HashMap;

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

    /**
     * JavaFX boilerplate to set up the view.
     * Establishes data profiles for the table of files for both the local and the remote lists.
     * Initialises other values with empty contents.
     * Establishes the selection model: what happens when a table row is selected (ie a file is selected)
     */
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


    /**
     * @param file File that is selected.
     * Sets values in the right hand side details pane to provide the user with more in depth information about the file.
     * Handles details for remote file data.
     */
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
                case NOT_TRACKED:
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
    /**
     * @param file File that is selected.
     * Sets values in the right hand side details pane to provide the user with more in depth information about the file.
     * Handles details for local file data.
     */

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
                case NOT_TRACKED:
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

    /**
     * @param main
     * Use data stored in the main class (file trees) to initialise file explorers
     * Selected key is used to establish the entry point for the program into the file structure.
     */
    public void setMain(Main main){
        this.main = main;
        main.getRemoteFiles().setSelectedKey("servertest2");
        remoteFiles.setItems(main.getRemoteFilesData());
        main.getLocalFiles().setSelectedKey("FilesTest");
        localFiles.setItems(main.getLocalFilesData());
    }

    /**
     * Move down a layer in the file tree.
     * Changes the directory used as parent to the one that has been selected.
     * Refreshes contents of table.
     */
    @FXML
    public void onRemoteIncrementPressed(){
        main.getRemoteFiles().setSelectedKey(selectedRemoteFolder);
        main.getRemoteFiles().incrementLayer();
        remoteFiles.setItems(main.getRemoteFilesData());
        GlobalValues.currentRemoteFolder = main.getRemoteFiles().getPath();
        selectedRemoteFolder ="";
    }

    /**
     * Moves up a layer in the file tree and refreshes the table contents.
     */
    @FXML
    public void onRemoteDecrementPressed(){
        if(main.getRemoteFiles().getCurrentLayer() != main.getRemoteFiles().getBaseLayer()) {
            main.getRemoteFiles().decrementLayer();
            remoteFiles.setItems(main.getRemoteFilesData());
            GlobalValues.currentRemoteFolder = main.getRemoteFiles().getPath();
        }
    }

    /**
     * Ditto, but for the local file tree.
     */
    @FXML
    public void onLocalIncrementPressed(){
        main.getLocalFiles().setSelectedKey(selectedLocalFolder);
        main.getLocalFiles().incrementLayer();
        localFiles.setItems(main.getLocalFilesData());
        GlobalValues.currentLocalFolder = main.getLocalFiles().getPath();
        selectedLocalFolder = "";
    }

    /**
     * Ditto, but for the local file tree.
     */
    @FXML void onLocalDecrementPressed(){
        if(main.getLocalFiles().getCurrentLayer() != main.getLocalFiles().getBaseLayer()){
            main.getLocalFiles().decrementLayer();
            localFiles.setItems(main.getLocalFilesData());
            GlobalValues.currentLocalFolder = main.getLocalFiles().getPath();
        }
    }

    /**
     * Download a file to the client device. File will be stored in the current directory open in the local tab.
     * Stores file data in persistent storage (changes not currently synchronised instantaneously.
     * Uses Azure Blob API to download file.
     * Notifies user.
     */
    @FXML
    public void onRemoteDownloadPressed(){
        String id = selectedFile.getUuid();
        String path = GlobalValues.currentLocalFolder + java.io.File.separator + selectedFile.getName();
        HashMap<String, String> fileDetails = new HashMap<>();
        fileDetails.put("_id", selectedFile.getUuid());
        fileDetails.put("path", selectedFile.getPath());
        fileDetails.put("date-edited", selectedFile.getDateEdited());
        fileDetails.put("online", "true");
        JSONWriter.writeOne(fileDetails);
        //HTTPClient.downloadFile(id, path);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Uploaded file successfully!");
        alert.setContentText("This file is now tracked by the Cloudlink system");
        alert.showAndWait();
    }

    /**
     * File is already tracked, but remote version is identified as outdated.
     * To update the file: upload the local copy, and update the date edited property stored in the cloud.
     * Notifies user.
     */
    @FXML
    public void onRemoteUpdatePressed(){
        String id = selectedFile.getUuid();
        String path = selectedFile.getLocalPath();
        //HTTPClient.uploadFile(id, path);
        HashMap<String,String> newdetails = new HashMap<>();
        newdetails.put("date-edited", selectedFile.getDateEdited());
        //HTTPClient.updateFileData(id,newdetails);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Uploaded file successfully!");
        alert.setContentText("The latest version of this file is now on the Cloudlink System!");
        alert.showAndWait();
    }

    /**
     * Gets data for the selected file, uploads the file data to the cloud database and then uploads a copy of the file
     * to the Azure blob system.
     * Notifies user.
     */
    @FXML
    public void onLocalUploadPressed(){
        String id = selectedFile.getUuid();
        String path = GlobalValues.basePath + selectedFile.getName();
        HashMap<String,String> fileData = new HashMap<>();
        fileData.put("_id",selectedFile.getUuid());
        fileData.put("path", selectedFile.getLocalPath());
        fileData.put("date-edited", selectedFile.getDateEdited());
        fileData.put("online", "true");
        //HTTPClient.uploadFileData(fileData);
        //HTTPClient.uploadFile(id, path);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Uploaded file successfully!");
        alert.setContentText("This file is now tracked by the Cloudlink system!");
        alert.showAndWait();
    }

    /**
     * Local file is outdated, up to date version needs to be downloaded from the cloud system.
     * Notifies user.
     */
    @FXML
    public void onLocalUpdatePressed(){
        String id = selectedFile.getUuid();
        String path = selectedFile.getLocalPath();
        //HTTPClient.downloadFile(id, path);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Updated file successfully!");
        alert.setContentText("The latest version of this file is now on your system!");
        alert.showAndWait();
    }

}
