package modele;

import javax.swing.JOptionPane;

import vue.Fenetre;

/**
 * Classe d'exception qui indique directement à l'utilisateur l'erreur dans une JOptionPane
 */
public class CustomException extends Exception{
	
	/**
	 * Le message d'erreur
	 */
	private String message;
	
	/**
	 * Constructeur qui appelle le super et affiche une JOptionPane d'erreur à l'utilisateur
	 * @param titre Le titre de la fenetre
	 * @param message Le message d'erreur
	 */
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
