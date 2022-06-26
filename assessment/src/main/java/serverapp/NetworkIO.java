package serverapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class NetworkIO {
    private InputStream is;
    private InputStreamReader isr;
    private BufferedReader br;
    private OutputStream os;

    public NetworkIO(Socket sock) throws IOException{
        is = sock.getInputStream();
        isr = new InputStreamReader(is);
        br = new BufferedReader(isr);
        os = sock.getOutputStream();
    }

    public String read() throws IOException {
       return br.readLine();
    }

    public void write(byte[] msg) throws IOException {
        os.write(msg);
        os.flush();
     }

     public void close(){
         try{
            br.close();
            isr.close();
            is.close();
            os.close();
         }catch(IOException e){
             e.printStackTrace();
         }
     }
}