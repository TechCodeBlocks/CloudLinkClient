package cloudlink.model;

import java.util.ArrayList;

public class Folder extends FinderItem {
    private ArrayList<File> children;

    public Folder(String path){
        super(path);
        children = new ArrayList<File>();
    }

    public ArrayList<File> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<File> children) {
        this.children = children;
    }

    public void addChild(File file){
        children.add(file);
    }

    public void removeChild(File file){
        children.remove(file);
    }
}
