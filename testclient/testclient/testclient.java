package testclient;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class testclient {
    public static void main(String[] args) throws IOException{

        Socket s = new Socket("localhost", 3000);
        boolean stop = false;
        InputStream is = null;
        DataInputStream dis = null;
        OutputStream os = null;
        DataOutputStream dos = null;
        
        try{
            while(!stop){
                String request = "GET /index.html HTTP/1.1";
                String response = null;
                os = s.getOutputStream();
                // BufferedOutputStream bos = new BufferedOutputStream(os);
                // bos.write("GET /index.html HTTP/1.1".getBytes());
                dos = new DataOutputStream(os);    
                dos.writeUTF(request);
                System.out.println("Wrote: "+request);
                // bos.flush();
                // bos.close();
                os.flush();
                is = s.getInputStream();
                dis = new DataInputStream(is);
                response = dis.readUTF();
                // BufferedInputStream bis = new BufferedInputStream(is);
                // String request = new String(bis.readAllBytes());
                System.out.println("Recieved: "+response);
                stop = true;
            }
        }catch(EOFException e){

        }catch(IOException e){

        }
        os.close();
        dos.close();
        dis.close();
        is.close();
        s.close();
    }
}
