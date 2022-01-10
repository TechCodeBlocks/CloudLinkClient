package cloudlink.utility;

import cloudlink.model.File;
import cloudlink.model.FileTree;
import cloudlink.model.FinderItem;
import cloudlink.model.Sychnronosity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

public interface FileDataHandler {

    //Store a JSON list of only files that are to be tracked on the client device. These UUIDs will be kept the same, everything else will be refreshed when program is restarted.
    //Add uuid to this list  when a file is downloaded.

    //Parse the UUID/paths to the crawler on start.
    //Once all data is present, check the uuids present in both file trees and carry out the necessary logic to mark things
    //with correct synchronosity.

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
                                ((File)item).setStatus(Sychnronosity.OUTDATED);
                            }else if(dateRemote.isAfter(dateLocal)){
                                ((File)local.getFile(uuid)).setStatus(Sychnronosity.OUTDATED);
                            }else{
                                ((File)item).setStatus(Sychnronosity.UP_TO_DATE);
                                ((File)local.getFile(uuid)).setStatus(Sychnronosity.UP_TO_DATE);
                            }

                        }

                    }
                }

            }
        }

    }
}
