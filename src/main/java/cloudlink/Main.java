package cloudlink;

import cloudlink.utility.*;
import cloudlink.view.MainViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.sun.javafx.scene.control.skin.Utils.getResource;

public class Main extends Application {
    private Stage primaryStage;
    private BorderPane rootLayout;
    //On start: pull the latest version of file list from cloud version.
    //Check for all online files, log these. Do the same with the local file list, for any local files are logged as online
    //check it's uuid to find online copy, compare date-edited values to work out whether synchronisation is required.
    //local files will be logged with less information by default, once added to system other information will be generated
    //should be building a merged list where files are updated with their local paths if they are tracked.

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("CloudLink");
        initRootLayout();
        showMainPage();

//        Parent root = FXMLLoader.load(getClass().getResource("view/sample.fxml"));
//        primaryStage.setTitle("Hello World");
//        primaryStage.setScene(new Scene(root, 300, 275));
//        primaryStage.show();
    }


    public static void main(String[] args) {
//        FileCrawler fileCrawler = new FileCrawler(GlobalValues.basePath);
//        List<HashMap<String,String>> oldFiles = new ArrayList<>();
//        List<HashMap<String,String>> newFiles = fileCrawler.crawl(oldFiles);
//        JSONWriter.write(newFiles);
//        TreeBuilder treeBuilder = new TreeBuilder(JSONReader.read());
//        treeBuilder.testConversion();
//        treeBuilder.createFoldersList();
//        treeBuilder.buildTree();
//        treeBuilder.printTree();
        launch(args);
    }

    public void initRootLayout(){
        try{

            URL fxmlLocation = getResource("/Users/benjaminsolomons/IdeaProjects/CloudLinkClient/src/main/java/cloudlink/view/RootView.fxml");
            URL url = new File("/Users/benjaminsolomons/IdeaProjects/CloudLinkClient/src/main/java/cloudlink/view/RootView.fxml").toURI().toURL();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(url);

            System.out.println( loader.getLocation());
//            loader.setLocation(getClass().getResource("view/RootView.fxml"));
            rootLayout = (BorderPane) loader.load();

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void showMainPage(){
        try {
            URL url = new File("/Users/benjaminsolomons/IdeaProjects/CloudLinkClient/src/main/java/cloudlink/view/MainView.fxml").toURI().toURL();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(url);
            AnchorPane personOverview = (AnchorPane) loader.load();
            rootLayout.setCenter(personOverview);

//            PersonOverviewController controller = loader.getController();
//            controller.setMain(this);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
