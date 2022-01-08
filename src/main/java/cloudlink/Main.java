package cloudlink;

import cloudlink.utility.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main extends Application {
    //On start: pull the latest version of file list from cloud version.
    //Check for all online files, log these. Do the same with the local file list, for any local files are logged as online
    //check it's uuid to find online copy, compare date-edited values to work out whether synchronisation is required.
    //local files will be logged with less information by default, once added to system other information will be generated
    //should be building a merged list where files are updated with their local paths if they are tracked.

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("view/sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
//        FileCrawler fileCrawler = new FileCrawler(GlobalValues.basePath);
//        List<HashMap<String,String>> oldFiles = new ArrayList<>();
//        List<HashMap<String,String>> newFiles = fileCrawler.crawl(oldFiles);
//        JSONWriter.write(newFiles);
        TreeBuilder treeBuilder = new TreeBuilder(JSONReader.read());
        treeBuilder.testConversion();
        treeBuilder.createFoldersList();
        treeBuilder.buildTree();
        treeBuilder.printTree();
        //launch(args);
    }
}
