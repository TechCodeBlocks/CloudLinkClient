package cloudlink.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FileTree {
    HashMap<Integer, HashMap<String, List<FinderItem>>> tree;
    private int currentLayer = 1;
    private String selectedKey;

    public FileTree(HashMap<Integer, HashMap<String, List<FinderItem>>> tree){
        this.tree = tree;
    }

    public List<FinderItem> getFiles(){
        return tree.get(currentLayer).get(selectedKey);
    }

    public HashMap<Integer, HashMap<String, List<FinderItem>>> getTree() {
        return tree;
    }

    public FinderItem getFile(String uuid){
        for(Integer key1 :tree.keySet() ){

            for(String key2: tree.get(key1).keySet() ){

                for(FinderItem item :tree.get(key1).get(key2)){
                    if(item instanceof File){
                        if(((File) item).getUuid().equals(uuid)){
                            return item;
                        }
                    }
                }


            }

        }
        return null;
    }

    public void incrementLayer(){
        currentLayer++;
    }
    public void decrementLayer(){
        currentLayer--;
    }

    public void setSelectedKey(String selectedKey){
        this.selectedKey = selectedKey;
    }



    /*
    * Class stub*/
}
