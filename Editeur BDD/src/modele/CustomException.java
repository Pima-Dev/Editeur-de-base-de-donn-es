package modele;

import javax.swing.JOptionPane;

public class CustomException extends Exception{
	
	private String message;
	
	public CustomException(String titre, String message){
		super(message);
		this.message = message;
		JOptionPane.showMessageDialog(null, message, titre, JOptionPane.ERROR_MESSAGE);
	}
	
	@Override
	public String getMessage(){
		return this.message;
	}

}
