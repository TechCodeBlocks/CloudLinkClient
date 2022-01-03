package cloudlink.utility;

import cloudlink.model.File;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TreeBuilder {
    /*
    * Class stub, yet to be written.*/
    private ArrayList<File> filesList = new ArrayList<>();
    List<HashMap<String, String>> filesData;

    public TreeBuilder(List<HashMap<String,String>> filesData){
        this.filesData = filesData;
    }


    private void convertListToObjects(){
        for(HashMap<String,String> data : filesData){
            String id = data.get("_id");
            String path = data.get("path");
            String editDate = data.get("date-edited");
            String onlineString = data.get("online");
            boolean online = false;
            if(onlineString.equals("true")){
                online = true;
            }
            File file = new File(path,editDate,online,id);
            filesList.add(file);
        }
    }

    public void testConversion(){
        convertListToObjects();
        for(File file : filesList){
            System.out.println("+=============+");
            System.out.println(file.getUuid());
            System.out.println(file.getDateEdited());
            System.out.println(file.isOnline());
            System.out.println(file.getPath());
            System.out.println(file.getParents());
        }
    }
}
