package cloudlink.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FileTree {
    HashMap<Integer, HashMap<String, List<FinderItem>>> tree;
    private int currentLayer = 3;
    private String selectedKey;
    private ArrayList<String> currentPath = new ArrayList<>();

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
        System.out.println(selectedKey);
        currentLayer++;
        System.out.println(currentLayer);
//        currentPath.add(selectedKey);
    }
    public void decrementLayer(){
        //reduce layer, remove end of path, get the new preceding layer from the current path value
        currentLayer--;
        currentPath.remove(selectedKey);
        selectedKey = currentPath.get(currentPath.size()-1);
    }

    public void setSelectedKey(String selectedKey){
        this.selectedKey = selectedKey;
        currentPath.add(selectedKey);
    }

    public List<FinderItem> getLevelledFiles(){
        System.out.println(currentLayer);
        System.out.println(selectedKey);
        return tree.get(currentLayer).get(selectedKey);
    }



    /*
    * Class stub*/
}
