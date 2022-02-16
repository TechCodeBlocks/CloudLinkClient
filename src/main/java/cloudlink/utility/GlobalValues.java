package cloudlink.utility;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GlobalValues {
    public static String userid = "101";
    public static String basePath = "/Users/benjaminsolomons/Documents/FilesTest";
    public static String baseFolder = "FilesTest";
    public static String remoteBaseFolder ="servertest2";
    public static String currentLocalFolder = "";
    public static String currentRemoteFolder = "";
    public static List<HashMap<String,String>> trackedFiles = new ArrayList<HashMap<String,String>>();
    public static List<String> trackedUUIDS = new ArrayList<String>();
    public static List<String> trackedPaths = new ArrayList<String>();
}
