package cloudlink.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FinderItem {
    private String path;
    private List<String> parents;
    private String directParent;
    private String name;

    public FinderItem(String path){
        this.path = path;
        parents = new ArrayList<String>();
        parents = Arrays.asList(path.split("/"));
        name = parents.get(parents.size()-1);
        parents = parents.subList(1, parents.size()-1);
        directParent = parents.get(parents.size()-1);


    }
    public StringProperty nameProperty(){
        return new SimpleStringProperty(name);
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

    public String getDirectParent() {
        return directParent;
    }

    public void setDirectParent(String directParent) {
        this.directParent = directParent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
