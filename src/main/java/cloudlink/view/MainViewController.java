package cloudlink.view;

import cloudlink.model.FinderItem;
import com.sun.tools.classfile.Dependency;
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
    private TableColumn remoteFilesFname;
    @FXML
    private TableColumn remoteFilesFdateEdited;
    @FXML
    private TableColumn remoteFilesFtracked;
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
}
