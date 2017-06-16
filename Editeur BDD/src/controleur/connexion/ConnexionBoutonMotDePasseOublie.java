package controleur.connexion;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import vue.Fenetre;
import vue.VueDeConnexion;
import vue.VueDeCreationDUtilisateur;
import vue.VueMotDePasseOublieEmail;

public class ConnexionBoutonMotDePasseOublie implements ActionListener {
	
	private Fenetre fenetre;
	private VueDeConnexion vue;
	
	public ConnexionBoutonMotDePasseOublie(VueDeConnexion vue, Fenetre fenetre) {
		this.vue = vue;
		this.fenetre = fenetre;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		fenetre.getFenetre().setContentPane(new VueMotDePasseOublieEmail(fenetre));
		fenetre.getFenetre().setVisible(true);
		fenetre.getFenetre().setSize(new Dimension(350,190));

	}

}
