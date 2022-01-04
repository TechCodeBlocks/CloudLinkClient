package cloudlink.model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FinderItem {
    private String path;
    private List<String> parents;
    private FinderItem directParent;
    private ArrayList<FinderItem> children;

    public FinderItem(String path){
        this.path = path;
        parents = new ArrayList<String>();
        parents = Arrays.asList(path.split("/"));
        parents = parents.subList(0, parents.size()-1);

    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<String> getParents() {
        return parents;
    }

    public void setParents(List<String> parents) {
        this.parents = parents;
    }

    public FinderItem getDirectParent() {
        return directParent;
    }

    public void setDirectParent(FinderItem directParent) {
        this.directParent = directParent;
    }


}
