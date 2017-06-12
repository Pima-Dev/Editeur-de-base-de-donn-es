package modele;

import javax.swing.JOptionPane;

public class CustomException extends Exception{
	
	public CustomException(String titre, String message){
		super(message);
		JOptionPane.showMessageDialog(null, message, titre, JOptionPane.ERROR_MESSAGE);
	}

}
