package service;

import java.sql.SQLException;

public interface DBService {
	
	public String printBase() throws SQLException;
	
	public String printBaser() throws SQLException;
	
	public void addBook(String bookName, int year, String nameAuthor) throws SQLException;
	
	public void changeTitleBook(int idBook, String nameBook) throws SQLException;
	
	public void deleteBook(int idBook) throws SQLException;
	
	public String printBookAuthor(int idAuthor) throws SQLException;
	
	public String Popular() throws SQLException;

}
