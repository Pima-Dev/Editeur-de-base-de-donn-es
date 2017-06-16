package controleur.creation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import vue.Fenetre;
import vue.VueDeConnexion;
import vue.VueDeCreationDUtilisateur;

public class CreationBoutonCreer implements ActionListener {

	private Fenetre fenetre;
	private VueDeCreationDUtilisateur vue;
	private JOptionPane jop;
	private String code;
	private String entre;
	
	public CreationBoutonCreer(VueDeCreationDUtilisateur vue, Fenetre fenetre) {
		this.vue = vue;
		this.fenetre = fenetre;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		jop = new JOptionPane();
		if(vue.isvUtilisateur() && vue.isvEmail() && vue.isvMotDePasse() && vue.isvConfirmation()){
			envoyerCode();
			entre = jop.showInputDialog(null, "Veuillez entrer le code qui vous à été envoyé par mail", "Confirmation d'Email", JOptionPane.QUESTION_MESSAGE);
			if(entre != null){
				if(entre.equals(code)){
					fenetre.getFenetre().setContentPane(new VueDeConnexion(fenetre));
					fenetre.getFenetre().setVisible(true);
					fenetre.getFenetre().pack();
				}
			}
		}
	}


	private void envoyerCode() {
		this.code="14";
	}
}