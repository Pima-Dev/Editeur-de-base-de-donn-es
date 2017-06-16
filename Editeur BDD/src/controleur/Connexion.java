package controleur;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;

import vue.*;

/**
 * @author Utilisateur
 *
 */
public class Connexion implements ActionListener {

	private Fenetre fenetre;
	private VueDeConnexion vue;
	private String nom;
	private String motDePasse;
	private JButton bouton;
	/**
	 * 
	 */
	public Connexion(VueDeConnexion vue, Fenetre fenetre) {
		this.vue = vue;
		this.fenetre = fenetre;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton bouton= (JButton)e.getSource();
		if(e.getSource().getText().equals("Connexion")){
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
		else if(e.getSource().getText().equals("Creation de compte")){
			fenetre.getFenetre().setContentPane(new VueDeCreationDUtilisateur(fenetre));
			fenetre.getFenetre().setVisible(true);
			fenetre.getFenetre().setSize(new Dimension(300,400));
		}
		else if(e.getSource().getText().equals("Mot de passe oublie")){
			
		}
	}
	
	public boolean verifier(){
		return false;
	}
	
	

}
