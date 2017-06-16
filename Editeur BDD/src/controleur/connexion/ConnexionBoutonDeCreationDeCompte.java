package controleur.connexion;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import vue.Fenetre;
import vue.VueDeConnexion;
import vue.VueDeCreationDUtilisateur;
import vue.VuePrincipale;

public class ConnexionBoutonDeCreationDeCompte implements ActionListener {

	private Fenetre fenetre;
	private VueDeConnexion vue;
	
	public ConnexionBoutonDeCreationDeCompte(VueDeConnexion vue, Fenetre fenetre) {
		this.vue = vue;
		this.fenetre = fenetre;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		fenetre.getFenetre().setContentPane(new VueDeCreationDUtilisateur(fenetre));
		fenetre.getFenetre().setVisible(true);
		fenetre.getFenetre().setSize(new Dimension(300,400));
	}

}
