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
