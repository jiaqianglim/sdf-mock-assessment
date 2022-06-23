package serverapp;

import java.io.IOException;
import java.net.Socket;

public class HttpClientConnection implements Runnable{

    private Socket s; 
    private Utilities utilities;

    public HttpClientConnection(Socket sa) throws IOException{ 
        this.s = sa; 
        Utilities utilities = new Utilities();
    }

    @Override
    public void run(){
        NetworkIO networkIO = null;
        String request = "";
        String response = "";
        try{
            networkIO = new NetworkIO(s);
            request = networkIO.read();
            response = utilities.processRequest(request);
            byte[] bl = response.getBytes("UTF-8");
            networkIO.write(bl);
            networkIO.close();
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
