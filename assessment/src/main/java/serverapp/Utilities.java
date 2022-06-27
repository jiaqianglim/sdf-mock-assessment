package serverapp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Utilities {

    private String[] args;
    private String request;
    private String response;
    private String resource;
    private String directories;
    private Socket s;

    public void splitArgs(String[] argsa){
        args = argsa;
        //Default case no arguments
        //java -cp ./myserver.jar 
        //will start the server on port 3000; the  docRoot  directory  is  ./target

        //Special case with arguments
        //java -cp ./myserver.jar "command" "value"
        //args format: args[0/even] "command", args[odd] "value"
        if(args.length>0){
            System.out.println(args.length);
            for (int i=0; i<args.length; i+=2){
                if(args[i].equals("--port")){
                    System.out.println(args[i] +" "+ args[i+1]);
                    try{
                        HttpServer.port = Integer.parseInt(args[i+1]);
                        System.out.println("Port updated to "+HttpServer.port);
                    }catch(NumberFormatException e){
                        e.getMessage();
                        System.exit(1);
                    }
                }
                if(args[i].equals("--docRoot")){
                    HttpServer.directories = args[i+1];
                    System.out.println("docRoot updated to "+HttpServer.directories);
                }
            }
        }
        System.out.printf("Port is %s, docRoot is %s\n", HttpServer.port, HttpServer.directories);
    }

    public String processIncomingRequest(String requesta) throws IOException{

        System.out.println("processRequest");
        String request = requesta;
        System.out.println(request);
        String response;
        //Action 1
        String method = request.split(" ")[0];
        System.out.println(method);
        if(!method.equals("GET")){//Not a GET method
            return response = "HTTP/1.1 405 Method Not Allowed\r\n \r\n "+method+" not supported\r\n";
        }

        //Action 2
        String resource = request.split(" ")[1];
        System.out.println(resource);
        if (resource.equals("/")){
            resource.replace("/", "/index.html");
            System.out.println("Resource searched is "+ resource);
        }
        if (!searchForResource(resource)){ //Resource does not exists
            return response = "HTTP/1.1 404 Not Found\r\n \r\n "+resource+" not found\r\n";
        //Action 4
        }else{//Resource exist
            if(resource.endsWith(".png")){//is a PNG image
                return response = "HTTP/1.1 200 OK\r\n Content-Type: image/png\r\n \r\n";
            }else{//NOT PNG image
                if(method.equals("GET") && resource.equals("/index.html")){
                    return response = "HTTP/1.1 200 OK\r\n Content-Type: text/html\r\n";
                }else{
                    //Action 3
                    return response= "HTTP/1.1 200 OK\r\n \r\n";
                }
            }
        }

    }

    //processHelper() helper
    public boolean searchForResource(String resourcea){
        resource = resourcea;
        String[] listofDirectories = HttpServer.directories.split(":");
        for(String directory: listofDirectories){
            File file = Paths.get(HttpServer.rootdirectory, directory, resource).toFile();
            System.out.println(file.toString());
            if (file.exists()){
                return true;
            }
        }
        return false;
    }

    public byte[] appendRequestFiles(String requesta) throws IOException{
        request = requesta;
        byte[] response = null;
        String method = request.split(" ")[0];
        String resource = request.split(" ")[1];
        if (resource.equals("/")){
            resource.replace("/", "/index.html");
        }
        if(method.equals("GET") && resource.equals("/index.html")){
        try{
            Path path = Paths.get(HttpServer.rootdirectory, HttpServer.staticdirectory, resource);
            response = Files.readAllBytes(path);
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }
    }
    return response;
    }
}
