package modele.test;

import java.sql.SQLException;

import com.mysql.jdbc.exceptions.MySQLSyntaxErrorException;

import modele.CustomException;
import modele.Serveur;

public class TestServeur {

	public static void main(String[] args){
		
		Serveur serveur = new Serveur("root", "azerty");
		try{
			serveur.executerCode("", "test");
		}
		
		catch(CustomException e){}
		
		catch(SQLException e){
			e.printStackTrace();
		}
	}
	
}
