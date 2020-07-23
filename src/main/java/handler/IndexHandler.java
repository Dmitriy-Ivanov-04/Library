package handler;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import service.DBServiceImpl;

public class IndexHandler implements HttpHandler {
	
	public static DBServiceImpl app = new DBServiceImpl();
	
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
		String request = exchange.getRequestURI().getQuery();

        request = request.replace ("id=", "");
        request = request.replace ("bookName=", "");
        request = request.replace ("year=", "");
        request = request.replace ("nameAuthor=", "");
        request = request.replace ("idBook=", "");
        request = request.replace ("idAuthor=", "");
        String req[] = request.split("&");
        
        switch (req[0]) {
            case "printBase":
                String response = "";
                try{
                    response = app.printBase();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                exchange.sendResponseHeaders(200, response.length());
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
                break;

            case "printBaser":
                String response1 = "";
                try{
                	response1 = app.printBaser();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                exchange.sendResponseHeaders(200, response1.length());
                OutputStream os1 = exchange.getResponseBody();
                os1.write(response1.getBytes());
                os1.close();
                break;

            case "addBook":
                int year = Integer.parseInt(req[2]);
                try {
                	app.addBook(req[1], year, req[3]);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;

            case "changeTitleBook":
                int idBook = Integer.parseInt(req[1]);
                String nameBook = req[2];
                try {
                	app.changeTitleBook(idBook, nameBook);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;

            case "deleteBook":
                int idBook1 = Integer.parseInt(req[1]);
                try {
                	app.deleteBook(idBook1);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;

            case "printBookAuthor":
                int idAuthor = Integer.parseInt(req[1]);
                String response2 = "";
                try {
                	response2 = app.printBookAuthor(idAuthor);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                exchange.sendResponseHeaders(200, response2.length());
                OutputStream os2 = exchange.getResponseBody();
                os2.write(response2.getBytes());
                os2.close();
                break;

            case "Popular":
                String response3 = "";
                try {
                    response3 = app.Popular();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                exchange.sendResponseHeaders(200, response3.length());
                OutputStream os3 = exchange.getResponseBody();
                os3.write(response3.getBytes());
                os3.close();
                break;
        }
    }
}

