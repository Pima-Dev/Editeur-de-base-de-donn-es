package controleur.motDePasseOublie;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import vue.Fenetre;
import vue.VueDeConnexion;
import vue.VueMotDePasseOublieCode;
import vue.VueMotDePasseOublieNouveau;

public class MotDePasseOublieBoutonNouveau implements ActionListener {

	private VueMotDePasseOublieNouveau vue;
	private Fenetre fenetre;
	private String nouveau;
	private String confirmation;
	
	public MotDePasseOublieBoutonNouveau(VueMotDePasseOublieNouveau vue, Fenetre fenetre) {
		this.vue = vue;
		this.fenetre = fenetre;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		nouveau = new String(vue.getfNouveau().getPassword());
		confirmation = new String(vue.getfConfirmation().getPassword());
		if(!nouveau.equals("") && !confirmation.equals("") && nouveau.equals(confirmation)){
			fenetre.getFenetre().setContentPane(new VueDeConnexion(fenetre));
			fenetre.getFenetre().setVisible(true);
			fenetre.getFenetre().pack();
		}
		vue.getlInfo().setText("Erreur de mot de passe");
	}

}
