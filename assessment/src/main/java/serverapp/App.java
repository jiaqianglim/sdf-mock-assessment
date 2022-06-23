package serverapp;

import java.io.IOException;

/**
 * sdf-mock-asessment
 *
 */
public class App 
{

    public static void main( String[] args ) throws IOException
    {

        Utilities utilities = new Utilities();
        System.out.println("App started");
        System.out.println( "Task 1 complete" );
        System.out.println( "Task 2 complete" );
        
        //Construct HttpServer
        System.out.println( "Task 3 start" );
        args = utilities.splitArgs(args);
        System.out.println( "Task 3 complete" );

        System.out.println( "Task 4 start" );
        HttpServer hs = new HttpServer(args);
        hs.verifyPath();
        System.out.println( "Task 4 complete" );

        System.out.println( "Task 5 start" );
        hs.createServerThreadPool();
        System.out.println( "Task 5 complete" );

        System.out.println( "Task 6 start" );
        //TODO
        System.out.println( "Task 6 complete" );

        System.out.println( "Task 7 start" );
        //TODO
        System.out.println( "Task 7 complete" );
    }
}
