package serverapp;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

import javax.security.sasl.SaslClient;

public class NetworkIO {
    private Socket s;
    private InputStream is;
    private InputStreamReader isr;
    private BufferedReader br;
    private OutputStream os;
    private BufferedInputStream bis;
    private BufferedOutputStream bos;
    private String request;
    private DataInputStream dis;


    public NetworkIO(Socket sa) throws IOException{
        InputStream is = s.getInputStream();
        DataInputStream dis = new DataInputStream(is);
        // bis = new BufferedInputStream(is);
        OutputStream os = s.getOutputStream();
        BufferedOutputStream bos = new BufferedOutputStream(os);
        System.out.println("NetworkIO created");
    }

    public String read() throws IOException {
        //request = new String(bis.readAllBytes());
        request = dis.readUTF();
        return request;
    }

    public void write(byte[] response) throws IOException {
        bos.write(response);
        bos.flush();
     }

     public void close(){
         try{
            bis.close();
            is.close();
            os.close();
            bos.close();
         }catch(IOException e){
             e.printStackTrace();
         }
     }
}