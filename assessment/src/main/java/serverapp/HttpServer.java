package serverapp;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer{

    public static int port = 3000;
    public static String directories = ".\\target";
    public static String rootdirectory = "C:\\Users\\Ryzen\\Desktop\\Code\\Assessment\\assessment";
    public static String staticdirectory = "\\static";

    private String[] args; 
    private String[] listofDirectories;
    private HttpClientConnection hcc;
    private ServerSocket ss;
    private Socket s;
    private ExecutorService threadPool;

    public HttpServer(){
    }

    //Check each of the  docRoot  path; for each path verify...
    public void verifyPath() throws IOException{
        String[] listofDirectories = directories.split(":");
        for(String i: listofDirectories){
            File file = Paths.get(rootdirectory, i).toFile();
            if(!file.exists()){
                System.out.println("Task 4: FAIL the path exists\n");
                System.exit(1);
            }
            if(!file.isDirectory()){
                System.out.println("Task 4: FAIL the path is a directory\n");
                System.exit(1);
            }
            if(!file.canRead()){
                System.out.println("Task 4: FAIL the path readable by the server\n");
                System.exit(1);
            }else{
            System.out.printf("Task 4: %s exists, is a directory, is readable by the server\n", i);
            }
        }
    }

    public void createSinglethreadedServer() throws IOException{
        System.out.println("Starting single thread server");
        try{
            ServerSocket ss = new ServerSocket(HttpServer.port);
            Socket s = ss.accept();
            System.out.println("Waiting for client connection");
            System.out.println("Task 4: open a TCP connection and listen on the port from port option");
            System.out.println("Closing single thread server");
            s.close();
            ss.close();
        }catch(IOException e){
            System.out.println("Task 4: FAIL to open a TCP connection and listen on the port from port option");
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void createMultithreadedServer() throws IOException{
        System.out.println("Starting multi thread server");
        try{
            ExecutorService threadPool = Executors.newFixedThreadPool(3);
            System.out.println("Threadpool created with 3 threads");
            ServerSocket ss = new ServerSocket(HttpServer.port);
            while(true){
                Socket s = ss.accept();
                HttpClientConnection hcc = new HttpClientConnection(s);
                threadPool.submit(hcc);
                System.out.println("Thread submitted");
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void closeServer() throws IOException{
        s.close();
        ss.close();
    }



}