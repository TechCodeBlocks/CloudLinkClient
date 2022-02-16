package cloudlink;

import apple.laf.JRSUIUtils;
import cloudlink.model.FileTree;
import cloudlink.model.FinderItem;
import cloudlink.utility.HTTPClient;
import cloudlink.utility.JSONReader;
import cloudlink.utility.JSONWriter;
import cloudlink.utility.TreeBuilder;
import cloudlink.view.LoginViewController;
import cloudlink.view.MainViewController;
import com.microsoft.signalr.HubConnection;
import com.microsoft.signalr.HubConnectionBuilder;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Main extends Application {
    private Stage primaryStage;
    private BorderPane rootLayout;
    private FileTree remoteFiles;
    private FileTree localFiles;
    URL styleURL;
    private static HubConnection hubConnection;
    private static String hubConnectionURL = "https://cloudlinkmessage.azurewebsites.net/api";
    //On start: pull the latest version of file list from cloud version.
    //Check for all online files, log these. Do the same with the local file list, for any local files are logged as online
    //check it's uuid to find online copy, compare date-edited values to work out whether synchronisation is required.
    //local files will be logged with less information by default, once added to system other information will be generated
    //should be building a merged list where files are updated with their local paths if they are tracked.

    @Override
    public void start(Stage primaryStage) throws Exception{
        try{
            new File("src/main/java/cloudlink/view/style/style.css").toURI().toURL();
        }catch (Exception e){
            e.printStackTrace();
        }
        showLoginDialogue();
        getRemoteTree();
        getLocalTree();
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("CloudLink");

        initRootLayout();
        showMainPage();



//        Parent root = FXMLLoader.load(getClass().getResource("view/sample.fxml"));
//        primaryStage.setTitle("Hello World");
//        primaryStage.setScene(new Scene(root, 300, 275));
//        primaryStage.show();
    }
    @Override
    public void stop(){
        //Any wrap up that needs doing, not required in this iteration
    }


    public static void main(String[] args) {
        //will be used in final version, not required during testing.
//        FileCrawler fileCrawler = new FileCrawler(GlobalValues.basePath);
//        List<HashMap<String,String>> oldFiles = new ArrayList<>();
//        List<HashMap<String,String>> newFiles = fileCrawler.crawl(oldFiles);
//        JSONWriter.write(newFiles);

        launch(args);
    }

    private static void initialiseHubConnection(){
        hubConnection = HubConnectionBuilder.create(hubConnectionURL).build();
        hubConnection.start();
    }

    public void sendFileRequest(String fileID){
        hubConnection.send("newMessage", "file-req::"+fileID);
    }

    public void getRemoteTree(){


        TreeBuilder treeBuilder = new TreeBuilder(HTTPClient.getFilesData());
        try {
            TimeUnit.SECONDS.sleep(1);
        }catch (Exception e){
            e.printStackTrace();
        }
        //TreeBuilder treeBuilder = new TreeBuilder(JSONReader.read());
        treeBuilder.testConversion();
        treeBuilder.createFoldersList();
        remoteFiles = treeBuilder.buildTree();
        treeBuilder.printTree();

    }
    public void getLocalTree(){
        TreeBuilder treeBuilder = new TreeBuilder(JSONReader.read());
        treeBuilder.testConversion();
        treeBuilder.createFoldersList();
        localFiles = treeBuilder.buildTree();
    }

    public void initRootLayout(){
        try{

            URL url = new File("src/main/java/cloudlink/view/RootView.fxml").toURI().toURL();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(url);
            rootLayout = (BorderPane) loader.load();

            Scene scene = new Scene(rootLayout);
            File file = new File(System.getProperty("user.dir").toString() + "/src/main/java/cloudlink/view/style/style.css");

            scene.getStylesheets().add(file.toURI().toURL().toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void showMainPage(){
        try {
            URL url = new File("src/main/java/cloudlink/view/MainView.fxml").toURI().toURL();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(url);
            AnchorPane personOverview = (AnchorPane) loader.load();
            rootLayout.setCenter(personOverview);
            MainViewController controller = loader.getController();
            controller.setMain(this);

//            PersonOverviewController controller = loader.getController();
//            controller.setMain(this);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public ObservableList<FinderItem> getRemoteFilesData(){
        ObservableList<FinderItem> observableList = FXCollections.observableArrayList();
        System.out.println("getting levelled files");
        List<FinderItem> filesList = remoteFiles.getLevelledFiles();
        System.out.println(filesList.size());
        for(FinderItem item : filesList){
            System.out.println(item.getName());
            observableList.add(item);
        }
        return observableList;

    }
    public ObservableList<FinderItem> getLocalFilesData(){
        ObservableList<FinderItem> observableList = FXCollections.observableArrayList();
        List<FinderItem> filesList = localFiles.getLevelledFiles();
        for(FinderItem item: filesList){
            observableList.add(item);
        }
        //Do similar logic to other section but with remote data.
        return observableList;

    }

    public void showLoginDialogue(){
        try {
            URL url = new File("src/main/java/cloudlink/view/LoginView.fxml").toURI().toURL();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(url);
            AnchorPane loginPage = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Login");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(loginPage);
            File file = new File(System.getProperty("user.dir").toString() + "/src/main/java/cloudlink/view/style/style.css");

            scene.getStylesheets().add(file.toURI().toURL().toExternalForm());


//            scene.getStylesheets().add("Users/benjaminsolomons/Documents/style.css");
            dialogStage.setScene(scene);
            LoginViewController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            dialogStage.showAndWait();


//            PersonOverviewController controller = loader.getController();
//            controller.setMain(this);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public FileTree getRemoteFiles(){
        return remoteFiles;
    }

    public FileTree getLocalFiles() {
        return localFiles;
    }
}
