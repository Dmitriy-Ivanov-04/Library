package service;

import java.sql.*;

import application.AppConnection;

public class DBServiceImpl implements DBService {
	
	public static AppConnection app = new AppConnection();
	
	@SuppressWarnings("static-access")
	@Override
    public String printBase() throws SQLException {
        PreparedStatement ps = app.connection.prepareStatement("select * from library.library");
        ResultSet rs = ps.executeQuery();
        String response = "[";
        while (rs.next()) {
            response += "{\"id\": \"" + rs.getInt(1) + "\", \"title\": \"" + rs.getString(2) + "\", \"year\": \"" +
                    rs.getInt(3) + "\", \"idauthor\": \"" + rs.getInt(4) + "\"}, ";
        }
        if(response.length() > 1) {
            response = response.substring(0, response.length() - 2);
        }
        response += "]";
        System.out.println("response" + response);
        return response;
    }
    
	@SuppressWarnings("static-access")
	@Override
    public String printBaser() throws SQLException {
        PreparedStatement ps = app.connection.prepareStatement("select * from library.author");
        ResultSet rs = ps.executeQuery();
        String response = "[";
        while (rs.next()) {
            response += "{\"Id\": \"" + rs.getInt(1) + "\", \"NameAuthor\": \"" + rs.getString(2) + "\"}, ";
        }
        if(response.length() > 1) {
            response = response.substring(0, response.length() - 2);
        }
        response += "]";
        return response;
    }

	@SuppressWarnings("static-access")
	@Override
    public void addBook(String bookName, int year, String nameAuthor) throws SQLException {
    	boolean flag = false;
    	int thisAuthor = 0;
        int idAuthor = 0;
        if (year < 0) {
        	year = 0;
        }
        PreparedStatement ps = app.connection.prepareStatement("select * from library.author");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
                    idAuthor = rs.getInt(1);
                    if(nameAuthor.equals(rs.getString(2))) {
                    	thisAuthor = idAuthor;
                    	flag = true;
                    }
        }
        if(flag) {
            PreparedStatement ps1 = app.connection.prepareStatement("INSERT INTO library.library (title, year_pub, authors) VALUES (?, ?, ?)");
            ps1.setString(1, bookName);
            ps1.setInt(2, year);
            ps1.setInt(3, thisAuthor);
            ps1.executeQuery();
        } else {
        	idAuthor++;
        	PreparedStatement ps1 = app.connection.prepareStatement("INSERT INTO library.library (title, year_pub, authors) VALUES (?, ?, ?)");
        	PreparedStatement ps2 = app.connection.prepareStatement("INSERT INTO library.author (name_author) VALUES (?)");
        	ps1.setString(1, bookName);
            ps1.setInt(2, year);
            ps1.setInt(3, idAuthor);
            ps1.executeQuery();
            ps2.setString(1, nameAuthor);
            ps2.executeQuery();
        }
    }

	@SuppressWarnings("static-access")
	@Override
    public void changeTitleBook(int idBook, String nameBook) throws SQLException {
        if(idBook < 0) {
        	idBook = 0;
        }
        PreparedStatement ps = app.connection.prepareStatement("UPDATE library.library set title = ? where library.library.id = ?");
        ps.setString(1, nameBook);
        ps.setInt(2, idBook);
        ps.executeQuery();
    }

	@Override
    @SuppressWarnings({ "resource", "static-access" })
	public void deleteBook(int idBook) throws SQLException {
    	if(idBook < 0) {
        	idBook = 0;
        }
    	PreparedStatement ps = app.connection.prepareStatement("SELECT authors from library.library where library.library.id = ?");
    	ps.setInt(1, idBook);
    	ResultSet rs = ps.executeQuery();
    	while(rs.next()) {
    		ps = app.connection.prepareStatement("SELECT count(*) from library.library where library.library.authors = ?");
    		ps.setInt(1, rs.getInt(1));
    		int authorid = rs.getInt(1);
    		rs = ps.executeQuery();
    		    while(rs.next()) {
        	         int count = rs.getInt(1);
        	             if (count <= 1) {
    	                 ps = app.connection.prepareStatement("delete from library.author where library.author.id = ?");
    	                 ps.setInt(1, authorid);
    	                 ps.executeQuery();
        	}
        	ps = app.connection.prepareStatement("DELETE from library.library where library.library.id = ?");
            ps.setInt(1, idBook);
            ps.executeQuery();
        	}
    	}
    }

	@SuppressWarnings("static-access")
	@Override
    public String printBookAuthor(int idAuthor) throws SQLException {
        if(idAuthor < 0) {
        	idAuthor = 0;
        }
        PreparedStatement ps = app.connection.prepareStatement("select * from library.library where library.library.authors = ?");
        ps.setInt(1, idAuthor);
        ResultSet rs = ps.executeQuery();
        String response = "[";
        while (rs.next()) {
            response += "{\"Id\": \"" + rs.getInt(1) + "\", \"NameBook\": \"" + rs.getString(2) + "\", \"Year\": \"" +
                    rs.getInt(3) + "\", \"AuthorID\": \"" + rs.getInt(4) + "\"}, ";
        }
        if(response.length() > 1) {
            response = response.substring(0, response.length() - 2);
        }
        response += "]";
        return response;
    }

	@SuppressWarnings("static-access")
	@Override
    public String Popular() throws SQLException {
        PreparedStatement ps = app.connection.prepareStatement("select library.author.name_author, count(*) as quantity from library.library inner join library.author on library.library.authors = library.author.id group by library.author.id order by quantity desc");
        ResultSet rs = ps.executeQuery();
        String response = "[";
        while (rs.next()) {
            response += "{\"NameAuthor\": \"" + rs.getString(1) + "\", \"Count\": \""  + rs.getInt(2) + "\"}, ";
        }
        if(response.length() > 1) {
            response = response.substring(0, response.length() - 2);
        }
        response += "]";
        return response;
    }
}