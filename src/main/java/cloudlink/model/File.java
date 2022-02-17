package cloudlink.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Child of FinderItem. Provides File specific functionality.
 */
public class File extends FinderItem {
    private String dateEdited;
    private boolean online;
    private String uuid;
    private String localPath;
    private Synchronicity status;

    public File(String path, String dateEdited, boolean online, String uuid){
        super(path);
        this.dateEdited = dateEdited;
        this.online = online;
        this.uuid = uuid;
        localPath = null;
        status = Synchronicity.NOT_TRACKED;

    }

    public StringProperty dateEditedProperty(){
        return new SimpleStringProperty(dateEdited);
    }

    public StringProperty trackedProperty(){
        if(status == Synchronicity.NOT_TRACKED){
            return new SimpleStringProperty("No");
        }else{
            return new SimpleStringProperty("Yes");
        }
    }

    public String getDateEdited() {
        return dateEdited;
    }

    public void setDateEdited(String dateEdited) {
        this.dateEdited = dateEdited;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

    public Synchronicity getStatus() {
        return status;
    }

    public void setStatus(Synchronicity status) {
        this.status = status;
    }
}
