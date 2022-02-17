package cloudlink.model;

import cloudlink.utility.GlobalValues;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Custom Tree implementation, using a multi dimensional array/hashmap as an underlying data structure.
 */
public class FileTree {
    HashMap<Integer, HashMap<String, List<FinderItem>>> tree;
    private int currentLayer;
    private int baseLayer;
    private String selectedKey;
    private ArrayList<String> currentPath = new ArrayList<>();

    public FileTree(HashMap<Integer, HashMap<String, List<FinderItem>>> tree){
        this.tree = tree;
        for(Integer key1 :tree.keySet() ){

            for(String key2: tree.get(key1).keySet() ){

                if(key2.equals(GlobalValues.baseFolder)){
                    baseLayer = key1;
                    currentLayer = baseLayer;
                    //selectedKey = GlobalValues.baseFolder;
                    break;
                }else if(key2.equals(GlobalValues.remoteBaseFolder)){
                    baseLayer = key1;
                    currentLayer = baseLayer;
                    //selectedKey = GlobalValues.remoteBaseFolder;
                    break;
                }


            }

        }

    }

    public List<FinderItem> getFiles(){
        return tree.get(currentLayer).get(selectedKey);
    }

    public HashMap<Integer, HashMap<String, List<FinderItem>>> getTree() {
        return tree;
    }

    /**
     * @param uuid
     * @return
     * Returns a file using UUID as key
     */
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

    /**
     * @return
     * Provides a full path to the currently selected path within the tree
     */
    public String getPath(){
        String completePath = "";
        for(String comp : currentPath){
            completePath = comp + java.io.File.separator;
        }
        return  completePath;

    }

    /**
     * Contains some logging.
     * Moves one layer deeper into the tree
     */
    public void incrementLayer(){
        System.out.println("Incrementing");
        System.out.println(selectedKey);
        currentLayer++;
        System.out.println(currentLayer);
    }

    /**
     * Contains some logging.
     * Moves one layer up the tree
     */
    public void decrementLayer(){
        System.out.println("decrementing");
        System.out.println(currentPath);
        currentLayer--;
        currentPath.remove(selectedKey);
        selectedKey = currentPath.get(currentPath.size()-1);
    }

    /**
     * @param selectedKey
     * Updates the selectedKey -> representative of the desired parent folder.
     */
    public void setSelectedKey(String selectedKey){
        this.selectedKey = selectedKey;
        currentPath.add(selectedKey);
    }

    /**
     * @return
     * Returns a list of finder items that are at the selected level and share the same immediate parent key,
     */
    public List<FinderItem> getLevelledFiles(){

            return tree.get(currentLayer).get(selectedKey);

    }

    public int getCurrentLayer() {
        return currentLayer;
    }

    public int getBaseLayer() {
        return baseLayer;
    }
}
