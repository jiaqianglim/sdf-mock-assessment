package serverapp;

import java.io.File;
import java.nio.file.Paths;

public class Utilities {

    private String[] args;
    private String[] updatedargs;
    private String port = "3000";
    private String docRoot = "./target";
    private String response;
    private String resource;

    public String[] splitArgs(String[] argsa){
        args = argsa;
        //Default case no arguments
        //java -cp ./myserver.jar 
        //will start the server on port 3000; the  docRoot  directory  is  ./target

        //Special case with arguments
        //java -cp ./myserver.jar "command" "value"
        //args format: args[0/even] "command", args[odd] "value"
        if(args.length>0){
            for (int i=0; i<args.length; i+=2){
                if(args[i]=="--port"){
                    port = args[i+1];
                    System.out.println("Port updated to "+port);
                }
                if(args[i]=="--docRoot"){
                    docRoot = args[i+1];
                    System.out.println("docRoot updated to "+docRoot);
                }
            }
        }
        System.out.printf("Port is %d, docRoot is %s", port, docRoot);
        String[] updatedargs = new String[]{port, docRoot};
        return updatedargs;
    }

    public String processRequest(String request){
        String method = request.split(" ")[0];
        if(method!="GET"){//Not a GET method
            return "HTTP/1.1 405 Method Not Allowed\r\n \r\n <method name> not supported\r\n";
        }

        String resource = request.split(" ")[1];
        if (resource=="/"){
            resource.replace("/", "/index.html");
            System.out.println("Resource searched is "+ resource);
        }

        if (searchForResource(resource, updatedargs)==false){ //Resource does not exists
            return "HTTP/1.1 404 Not Found\r\n \r\n <resource name> not found\r\n";
        }else{//Resource exist
            if(resource.contains(".png")){//is a PNG image
                return "HTTP/1.1 200 OK\r\n Content-Type: image/png\r\n \r\n <resource contents as bytes>";
            }else{//NOT PNG image
                return "HTTP/1.1 200 OK\r\n \r\n <resource contents as bytes>";
            }
        }
    }

    public boolean searchForResource(String resourcea, String[] updatedargsa){
        resource = resourcea;
        updatedargs = updatedargsa;
        String[] listofDirectories = updatedargs[1].split(":");
        for(String i: listofDirectories){
            File file = Paths.get(i,resource).toFile();
            System.out.println(file.toString());
            if (file.exists()){
                return true;
            }
        }
        return false;
    }
}
