package modele;

import javax.swing.JOptionPane;

import vue.Fenetre;

public class CustomException extends Exception{
	
	private String message;
	
	public CustomException(String titre, String message){
		super(message);
		this.message = message;
		if(Fenetre.getInstance() != null)
			JOptionPane.showMessageDialog(null, message, titre, JOptionPane.ERROR_MESSAGE);
	}
	
	@Override
	public String getMessage(){
		return this.message;
	}

}
