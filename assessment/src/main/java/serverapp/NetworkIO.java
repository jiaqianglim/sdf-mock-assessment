package serverapp;

import java.io.BufferedOutputStream;
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
    private BufferedOutputStream bos;

    public NetworkIO(Socket sock) throws IOException{
        is = sock.getInputStream();
        isr = new InputStreamReader(is);
        br = new BufferedReader(isr);
        os = sock.getOutputStream();
        bos = new BufferedOutputStream(os);
        System.out.println("NetworkIO created");
    }

    public String read() throws IOException {
        System.out.println("Input read");
        System.out.println("Network IO read line: "+br.readLine());
        return br.readLine();
    }

    public void write(byte[] msg) throws IOException {
        System.out.println("Write message: ");
        bos.write(msg);
        bos.flush();
     }

     public void close(){
         try{
            br.close();
            isr.close();
            is.close();
            os.close();
            bos.close();
         }catch(IOException e){
             e.printStackTrace();
         }
     }
}