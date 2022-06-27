package serverapp;

import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;

public class HttpClientConnection implements Runnable{

    private Socket s; 

    public HttpClientConnection(Socket sa){ 
        s = sa; 
    }

    @Override
    public void run(){
        System.out.println("Starting client thread");
        NetworkIO netIO = null;
        try{
            netIO = new NetworkIO(s);
            String request="";
            String response="";
            Utilities utilities = new Utilities();
            while(true){
                request = netIO.read();
                System.out.println("Request is "+request);
                response = utilities.processIncomingRequest(request);
                netIO.write(response.getBytes());
                break;
            }
            netIO.close();
            s.close();
        }catch(EOFException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

}
