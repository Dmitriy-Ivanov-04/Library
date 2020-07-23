package main;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.sql.SQLException;
import java.util.logging.Logger;

import com.sun.net.httpserver.HttpServer;

import handler.IndexHandler;

public class Main {
	
    private static Logger logger = Logger.getLogger(Main.class.getName());
	
	public static void main( String[] args ) throws SQLException, IOException, ClassNotFoundException{
        HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 9090), 0);
        server.createContext("/", new IndexHandler());
        server.start();
        logger.info("Server started on port 9090");
    }
	
}
