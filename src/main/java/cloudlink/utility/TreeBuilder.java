package cloudlink.utility;

import cloudlink.model.File;
import cloudlink.model.FinderItem;
import cloudlink.model.Folder;
import com.fasterxml.jackson.databind.util.TypeKey;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class TreeBuilder {
    /*
    * Class stub, yet to be written.*/
    private ArrayList<File> filesList = new ArrayList<>();
    private ArrayList<Folder> foldersList = new ArrayList<>();
    HashMap<Integer, HashMap<String, List<FinderItem>>> tree = new HashMap<>();
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

    private void sortFiles(){
        Collections.sort(filesList, new FinderItemComparator());
    }

    public void createFoldersList(){
        ArrayList<String> seenFolders = new ArrayList<>();
        for(File file : filesList){
            String folderPath = "";

            for(int i=0; i<file.getParents().size();i++){
                folderPath += file.getParents().get(i) + "/";


            }
            if(!seenFolders.contains(folderPath)) {
                System.out.println(folderPath);
                foldersList.add(new Folder(folderPath));
                seenFolders.add(folderPath);

            }

        }

    }

    public void buildTree(){
        ArrayList<String> seenFolders = new ArrayList<>();
       // sortFiles();
        for(File file : filesList){
            int depth = file.getParents().size();
            if(tree.containsKey(depth)){
                if(tree.get(depth).get(file.getDirectParent()) != null){
                    tree.get(depth).get(file.getDirectParent()).add(file);
                }
                tree.get(depth).put(file.getDirectParent(), new ArrayList<>());
                tree.get(depth).get(file.getDirectParent()).add(file);
            }else{
                tree.put(depth, new HashMap<>());
                tree.get(depth).put(file.getDirectParent(), new ArrayList<>());
                tree.get(depth).get(file.getDirectParent()).add(file);
            }
        }
        System.out.println("Adding Folders");
        for(Folder folder: foldersList){

            int depth = folder.getParents().size();
            if(tree.containsKey(depth)){
                if(tree.get(depth).get(folder.getDirectParent()) != null){
                    tree.get(depth).get(folder.getDirectParent()).add(folder);
                }
                tree.get(depth).put(folder.getDirectParent(), new ArrayList<>());
                tree.get(depth).get(folder.getDirectParent()).add(folder);
            }else{
                tree.put(depth, new HashMap<>());
                tree.get(depth).put(folder.getDirectParent(), new ArrayList<>());
                tree.get(depth).get(folder.getDirectParent()).add(folder);
            }
        }


    }
    public void printTree(){
        for(Integer key1 :tree.keySet() ){
            System.out.println(key1 + " {");
            for(String key2: tree.get(key1).keySet() ){
                System.out.println(tree.get(key1).get(key2).size());
                System.out.println(key2 +" {");
                for(FinderItem item :tree.get(key1).get(key2)){
                    System.out.println(item.getPath());
                }
                System.out.println("}");

            }
            System.out.println("}");
        }
    }
    //Do in layers by length of parents, group by lengths to have depth into 'tree' - 3 dimensional data structure
    //Then to traverse, go by depth into tree and extract by checking preceding parent item for if it matches the
    //selected file. Would need to store direct parent as a distinct string.
    //File tree will be the depth map
    // {depth:{preceding:{},preceding:{}, depth....)
    //Then provide the logic to traverse it.
    //Building the structure would be: get possible depths, for each one create a hash map of preceding and file item.
    //Check if depth exists, go to add, check if preceding exists, go to add. Use a ,merged list of folder and file items.

}
