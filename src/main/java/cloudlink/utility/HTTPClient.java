package cloudlink.utility;
import cloudlink.utility.GlobalValues;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.signalr.HubConnection;
import com.microsoft.signalr.HubConnectionBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public interface HTTPClient {
    /**
     * @param fileData HashMap of strings corresponding to the data for one file
     * @return Boolean confirmation of operation success/failure.
     * Sends a POST request to the Cloud Bridge, sending data in the JSON body of the request so that it can be processed on the server.
     * Operates asynchronously.
     */
    static Boolean uploadFileData(HashMap<String, String> fileData) {
        //Async execution of code
        CompletableFuture<Boolean> completableFuture = CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println("Upload File Data: Attempting");
                //Create URL for the correct endpoint
                URL url = new URL("http://192.168.1.163:5000/cloudlink/addfile/");
                //URL url = new URL("https://cloudlink.azurewebsites.net/api/addfile");
                //open and set up connection
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                connection.setRequestProperty("Accept", "application/json");
                connection.setDoOutput(true);
                //create a JSON object to represent the data
                JSONObject body = new JSONObject();
                body.put("_id", fileData.get("_id"));
                //In final implementation, user id will be extracted from the file path.
                body.put("_userid", GlobalValues.userid);
                body.put("path", fileData.get("path"));
                body.put("date-edited", fileData.get("date-edited"));
                body.put("online", fileData.get("online"));
                //set up data stream, write to it - sends data to server. Close and disconnect once completed
                DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());
                dataOutputStream.write(body.toString().getBytes());
                dataOutputStream.close();
                connection.disconnect();
                //Check for success
                if (connection.getResponseCode() == 200) {
                    System.out.println("File added to cloud system");
                    return true;
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return false;

        });
        //Call the async function and get result
        try {
            Boolean result = completableFuture.get();
            return result;
        } catch (InterruptedException e) {
            System.out.println(e.getStackTrace());
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * @param id
     * @param updateParameters
     * @return
     *
     * Updates the properties of a file already tracked by the cloud system. Uses the UUID and a HashMap of properties to update.
     */
    //Use to mark files as online when they are uploaded.
    static Boolean updateFileData(String id, HashMap<String, String> updateParameters ){
        CompletableFuture<Boolean> completableFuture = CompletableFuture.supplyAsync(()->{
            try{
                URL url = new URL("http://192.168.1.163:5000/cloudlink/updatefile/");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content=Type", "application/json; charset=UTF-8");
                connection.setRequestProperty("Accept", "application/json");
                connection.setDoOutput(true);
                JSONObject body = new JSONObject();
                body.put("fileID", id);
                body.put("updateKV", JSONObject.toJSONString(updateParameters));
                DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());
                dataOutputStream.write(body.toString().getBytes());
                dataOutputStream.close();
                connection.disconnect();
                if(connection.getResponseCode() == 200){
                    return true;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            return false;
        });
        try{
            Boolean result = completableFuture.get();
            return result;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;

    }
    /**
     * @param id UUID of file that is to be deleted
     * @return Boolean indicating success/failure of the operation.
     * Asynchronous HTTP DELETE request to the Cloud Bridge to remove a file that no longer exists on the server.
     */

     static Boolean deleteFileData(String id) {
        CompletableFuture<Boolean> completableFuture = CompletableFuture.supplyAsync(() -> {
            //String baseURL = "https://cloudlink.azurewebsites.net/api/delete-item?_id=";
            String baseURL = "http://192.168.1.163:5000/cloudlink/deletefile?id=";
            String requestURL = baseURL + id;
            try {
                URL url = new URL(requestURL);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("DELETE");
                connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                connection.setRequestProperty("Accept", "application/json");
                connection.setDoOutput(true);
//                JSONObject body = new JSONObject();
//                body.put("_id", id);
//                DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());
//                dataOutputStream.write(body.toString().getBytes());
//                dataOutputStream.close();
                connection.disconnect();
                if (connection.getResponseCode() == 404) {
                    System.out.println("error");
                    return true;
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return false;

        });
        try {
            Boolean result = completableFuture.get();
            return result;
        } catch (InterruptedException e) {
            System.out.println(e.getStackTrace());
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }
    /**
     * @param id UUID of file for which data is being requested.
     * @return Boolean indicating success/failure of the operation.
     * Asynchronous HTTP GET request to obtain file data for a file that has been uploaded by a client program.
     */
     static HashMap<String, String> getFileData(String id){
        CompletableFuture<HashMap<String, String>> completableFuture = CompletableFuture.supplyAsync(()->{
            try {
                //URL url = new URL("https://cloudlink.azurewebsites.net/api/get-single-file-data?");
                URL url = new URL("http://192.168.1.163:5000/cloudlink/file?fileid="+id);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                connection.setRequestProperty("Accept", "application/json");
                connection.setDoOutput(true);
//                JSONObject body = new JSONObject();
//                body.put("_userid", GlobalValues.userid);
//                body.put("_id", id);
//                DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());
//                dataOutputStream.write(body.toString().getBytes());
//                dataOutputStream.close();
                InputStream in = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                StringBuffer buf = new StringBuffer();
                String line;
                while ((line = reader.readLine())!=null) {
                    buf.append(line);
                }
                HashMap<String,String> fileData = new HashMap<>();
                ObjectMapper mapper = new ObjectMapper();
                try{
                    fileData = mapper.readValue(buf.toString(), new TypeReference<HashMap<String, String>>() {});
                }catch(Exception e){
                    e.printStackTrace();
                }
                return fileData;


            }catch (Exception e){
                e.printStackTrace();
            }
            return new HashMap<String, String >();
        });
        try {
            return completableFuture.get();
        }catch (Exception e){
            e.printStackTrace();
        }
        return new HashMap<String,String>();

    }

    /**
     * @return
     * Obtains a complete list of all file data linked to the signed in user.
     * Runs as an asynchronous HTTP GET request.
     * Returns as a list of file data, to be used by the rest of the program.
     */

    static List<HashMap<String,String>> getFilesData(){
        CompletableFuture<List<HashMap<String,String>>> completableFuture = CompletableFuture.supplyAsync(()->{
            try{
                List<HashMap<String, String>> readFilesData = new ArrayList<HashMap<String, String>>();
                URL url = new URL("http://192.168.1.163:5000/cloudlink/files?userid=" + GlobalValues.userid);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                connection.setRequestProperty("Accept", "application/json");
                connection.setDoOutput(true);
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuffer buf = new StringBuffer();
                String line;
                while ((line = reader.readLine())!=null) {
                    buf.append(line);
                }
                String jsonText = buf.toString();
                System.out.println(jsonText);
                JSONParser parser = new JSONParser();
                Object obj = parser.parse(jsonText);
                JSONArray fileList = (JSONArray) obj;
                fileList.forEach(fileData -> {
                    readFilesData.add(parseFileData((JSONObject) fileData));

                });
                return readFilesData;

            }catch (Exception e){
                e.printStackTrace();

            }
            return null;
        });

        try {
            return completableFuture.get();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param id
     * @param path
     * @return Boolean indicating success/failure of the operation.
     * Uses the Azure Blob API to upload a file to the Cloud Bridge asynchronously, so that it can be downloaded by a client.
     */
    static Boolean uploadFile(String id, String path) {
        CompletableFuture<Boolean> completableFuture = CompletableFuture.supplyAsync(() -> {
            String filepath = path;
            String connectStr = "DefaultEndpointsProtocol=https;AccountName=cloudlinkfilestore;AccountKey=c2CcTbSpewXh4jU85enZGpHYyiq2elYAUnVpKJLxIotTZWRoBFiQwcoj5kvaB4C6quaQ7KkifiJFdKXHGOPdWg==;EndpointSuffix=core.windows.net";
            BlobServiceClient blobServiceClient = new BlobServiceClientBuilder().connectionString(connectStr).buildClient();
            BlobContainerClient blobContainerClient = blobServiceClient.getBlobContainerClient("ytterbium");
            BlobClient blobClient = blobContainerClient.getBlobClient(id);
            blobClient.uploadFromFile(filepath, true);
            //send signalr message to notify client


            return true;
        });

        try {
            return completableFuture.get();
        }catch (ExecutionException e){
            e.printStackTrace();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return false;

    }
    /**
     * @param fileID
     * @param path
     * @return Boolean indicating success/failure of the operation.
     * Uses the Azure Blob API to upload a file to the Cloud Bridge asynchronously.
     * Will be used whenever a new file is uploaded by a client.
     */
    static Boolean downloadFile(String fileID, String path) {
        CompletableFuture<Boolean> completableFuture = CompletableFuture.supplyAsync(() -> {

            String connectStr = "DefaultEndpointsProtocol=https;AccountName=cloudlinkfilestore;AccountKey=c2CcTbSpewXh4jU85enZGpHYyiq2elYAUnVpKJLxIotTZWRoBFiQwcoj5kvaB4C6quaQ7KkifiJFdKXHGOPdWg==;EndpointSuffix=core.windows.net";
            BlobServiceClient blobServiceClient = new BlobServiceClientBuilder().connectionString(connectStr).buildClient();
            BlobContainerClient blobContainerClient = blobServiceClient.getBlobContainerClient("ytterbium");
            BlobClient blobClient = blobContainerClient.getBlobClient(fileID);
            blobClient.downloadToFile(path);
            return true;
        });
        try {
            return completableFuture.get();
        }catch (ExecutionException e){
            e.printStackTrace();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return false;

    }

    /**
     * @param id
     * @param passwordHash
     * @return
     * Provides a boolean response based on server repsonse to a GET request providing username and password.
     *
     */
    static Boolean verifyUser(String id, String passwordHash){
        CompletableFuture<Boolean> completableFuture = CompletableFuture.supplyAsync(() ->{
            try {
                URL url = new URL("https://cloudlink.azurewebsites.net/api/userverify");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                connection.setRequestProperty("Accept", "application/json");
                connection.setDoOutput(true);
                JSONObject body = new JSONObject();
                body.put("id", id);
                body.put("pass_hash", passwordHash);
                DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());
                dataOutputStream.write(body.toString().getBytes());
                dataOutputStream.close();
                connection.disconnect();
                if (connection.getResponseCode() == 400) {
                    System.out.println(connection.getOutputStream().toString());
                    System.out.println("error");
                    return false;
                }
                return true;

            }catch (Exception e){

            }
            return false;
        });
        try {
            return completableFuture.get();
        }catch (Exception e){
            return false;
        }
    }

    /**
     * @param fileData
     * @return
     * For use with the getFilesData function. Same as is used as a helper function in the JSONReader.
     * As file is tracked, it is added to the list of tracked files.
     */
    static HashMap<String, String> parseFileData(JSONObject fileData){
        HashMap<String, String> fileDataMap = new HashMap<String, String>();
        System.out.println(fileData.get("_id"));
        fileDataMap.put("_id", (String) fileData.get("_id"));
        fileDataMap.put("path", (String) fileData.get("path"));
        fileDataMap.put("date-edited", (String) fileData.get("date-edited"));
        fileDataMap.put("online", (String) fileData.get("online"));
        GlobalValues.trackedFiles.add(fileDataMap);
        GlobalValues.trackedUUIDS.add((String) fileData.get("_id"));
        GlobalValues.trackedPaths.add((String) fileData.get("path"));
         return fileDataMap;

    }


}

