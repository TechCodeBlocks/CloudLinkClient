package cloudlink.utility;

import cloudlink.model.File;
import cloudlink.model.FileTree;
import cloudlink.model.FinderItem;
import cloudlink.model.Synchronicity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public interface FileDataHandler {


    /**
     * @param local
     * @param remote
     * Provides synchronicity information for all files that are tracked in the system.
     * Tracking in the proof of concept is only added to the remote files tree. Full (non PoC) implementations
     * could add it to both trees.
     */
    static void insertTracking(FileTree local, FileTree remote){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
        for(Integer key1 :remote.getTree().keySet()){
            for(String key2: remote.getTree().get(key1).keySet() ){
                for(FinderItem item :remote.getTree().get(key1).get(key2)){
                    if(item instanceof File){
                        String uuid = ((File) item).getUuid();
                        if(GlobalValues.trackedUUIDS.contains(uuid)){
                            ((File) item).setLocalPath(local.getFile(uuid).getPath());
                            ((File) item).setOnline(true);
                            LocalDate dateLocal = LocalDate.parse(((File)local.getFile(uuid)).getDateEdited(), formatter);
                            LocalDate dateRemote = LocalDate.parse(((File) item).getDateEdited(), formatter);
                            if(dateLocal.isAfter(dateRemote)){
                                ((File)item).setStatus(Synchronicity.OUTDATED);
                            }else if(dateRemote.isAfter(dateLocal)){
                                ((File)local.getFile(uuid)).setStatus(Synchronicity.OUTDATED);
                            }else{
                                ((File)item).setStatus(Synchronicity.UP_TO_DATE);
                                ((File)local.getFile(uuid)).setStatus(Synchronicity.UP_TO_DATE);
                            }

                        }

                    }
                }

            }
        }

    }
}
