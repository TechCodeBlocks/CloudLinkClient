package cloudlink.utility;

public interface FIleDataHandler {

    //Store a JSON list of only files that are to be tracked on the client device. These UUIDs will be kept the same, everything else will be refreshed when program is restarted.
    //Add uuid to this list  when a file is downloaded.

    //Parse the UUID/paths to the crawler on start.
    //Once all data is present, check the uuids present in both file trees and carry out the necessary logic to mark things
    //with correct synchronosity.
}
