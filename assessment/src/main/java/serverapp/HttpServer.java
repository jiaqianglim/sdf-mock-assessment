package serverapp;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer{

    private String[] args; 
    private int port;
    private String directories;
    private String[] listofDirectories;
    private HttpClientConnection hcc;
    private ServerSocket ss;
    private Socket s;
    private ExecutorService threadPool;

    public HttpServer(String[] argsa){
        String[] args = argsa;
        try{
            port = Integer.parseInt(args[0]);
        }catch(NumberFormatException e){
            e.getMessage();
            System.exit(1);
        }
        directories = args[1];
    }

    //Check each of the  docRoot  path; for each path verify...
    public void verifyPath() throws IOException{
        String[] listofDirectories = this.directories.split(":");
        for(String i: listofDirectories){
            File file = Paths.get(i).toFile();
            if(!file.exists()){
                System.out.println("Task 4: FAIL the path exists");
                System.exit(1);
            }
            if(!file.isDirectory()){
                System.out.println("Task 4: FAIL the path is a directory ");
                System.exit(1);
            }
            if(!file.canRead()){
                System.out.println("Task 4: FAIL the path readable by the server ");
                System.exit(1);
            }else{
                System.out.printf("Task 4: %s exists, is a directory, is readable by the server", i);
            }
        }
    }

    public void startServer() throws IOException{
        try{
            ServerSocket ss = new ServerSocket(this.port);
            Socket s = ss.accept();
            System.out.println("Waiting for client connection");
            System.out.println("Task 4: open a TCP connection and listen on the port from port option");
            
        }catch(IOException e){
            System.out.println("Task 4: FAIL to open a TCP connection and listen on the port from port option");
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void createServerThreadPool() throws IOException{
        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        System.out.println("Threadpool created with 3 threads");
        while(true){
            HttpClientConnection hcc = new HttpClientConnection(this.s);
            threadPool.submit(hcc);
            System.out.println("Submitted to threadpool");
        }
    }



}