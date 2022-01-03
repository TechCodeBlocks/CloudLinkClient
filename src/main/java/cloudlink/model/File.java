package cloudlink.model;

public class File extends FinderItem {
    private String dateEdited;
    private boolean online;
    private String uuid;
    private String localPath;

    public File(String path, String dateEdited, boolean online, String uuid){
        super(path);
        this.dateEdited = dateEdited;
        this.online = online;
        this.uuid = uuid;
        localPath = null;

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
}
