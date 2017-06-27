package controleur;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import vue.Fenetre;
/**
 * Cette classe est appelée lors de l'interaction la fenêtre
 */
public class FenetreListener extends WindowAdapter{
	/**
	 * la racine de référence qui permet d'accéder à toutes les vues
	 */
	private Fenetre fenetre;
	
	/**
	 * construit le listener
	 * @param fenetre la racine de référence qui permet d'accéder à toutes les vues
	 */
	public FenetreListener(Fenetre fenetre){
		this.fenetre = fenetre;
	}
	
	/**
	 * redéfinition de la méthode windowClosing qui s'éxécute lors de la fermeture de la fenêtre
	 */
	@Override
	public void windowClosing(WindowEvent e){
		this.fenetre.getFenetre().setEnabled(true);
	}
}
