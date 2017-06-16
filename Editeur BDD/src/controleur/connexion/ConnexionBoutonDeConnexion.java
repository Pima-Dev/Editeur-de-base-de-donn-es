/**
 * 
 */
package controleur.connexion;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;

import vue.*;

/**
 * @author Utilisateur
 *
 */
public class ConnexionBoutonDeConnexion implements ActionListener {

	private Fenetre fenetre;
	private VueDeConnexion vue;
	private String nom;
	private String motDePasse;
	/**
	 * 
	 */
	public ConnexionBoutonDeConnexion(VueDeConnexion vue, Fenetre fenetre) {
		this.vue = vue;
		this.fenetre = fenetre;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		this.nom = this.vue.getfPseudo().getText();
		this.motDePasse = new String(this.vue.getfMotDePasse().getPassword());
		if(verifier() == true){
			fenetre.getFenetre().setContentPane(new VuePrincipale());
			fenetre.getFenetre().setVisible(true);
			fenetre.getFenetre().pack();
		}
		else{
			vue.getlErreurIdentifiant().setText("<HTML><i>Erreur d'identifiant</i></HTML>");;
			fenetre.getFenetre().setVisible(true);
		}
	}
	
	public boolean verifier(){
		return false;
	}
	
	

}
