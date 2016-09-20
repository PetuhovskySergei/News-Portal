package project.java.dao;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class ConnectionAccess {
	
	private static String DB_URL = "jdbc:oracle:thin:@localhost:1521:xe";
	
	public static String getDB_URL() {
		return DB_URL;
	}

	public static void setDB_URL(String dB_URL) {
		DB_URL = dB_URL;
	}

	private static ThreadLocal<Connection> connectionHolder = new ThreadLocal<Connection>() {
        public Connection initialValue(){            
				try {
					return DriverManager.getConnection(DB_URL);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				}	
        }
    };
        
    public static Connection getConnection() throws SQLException, ClassNotFoundException, ParserConfigurationException, SAXException, IOException{
			
        return connectionHolder.get();
    }
}
