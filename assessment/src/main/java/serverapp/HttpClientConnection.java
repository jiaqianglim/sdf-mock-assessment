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
        String request;
        byte[] response;
        byte[] responseresource;
        try{
            System.out.println("hcc run");
            networkIO = new NetworkIO(s);
            request = networkIO.read();
            System.out.println("Print line at hcc run"+ request);
            response = utilities.processIncomingRequest(request);
            System.out.println(response.toString());
            networkIO.write(response);
            responseresource = utilities.appendRequestFiles(request);
            if(responseresource!=null)
                networkIO.write(responseresource);
            networkIO.close();
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
