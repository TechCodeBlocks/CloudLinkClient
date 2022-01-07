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
