package controleur.creation;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.*;

import vue.*;

public class CreationChampMotDePasse implements FocusListener {

	VueDeCreationDUtilisateur vue;
	String motDePasse;
	
	public CreationChampMotDePasse(VueDeCreationDUtilisateur vue) {
		this.vue = vue;
	}

	@Override
	public void focusGained(FocusEvent e) {
	}

	@Override
	public void focusLost(FocusEvent e) {
		motDePasse = new String(vue.getfMotDePasse().getPassword());
		
		ImageIcon vrai = new ImageIcon("src/ressources/check.png");
		ImageIcon faux = new ImageIcon("src/ressources/croix.png");
		if(valide()){
			vue.getiMotDePasse().setIcon(vrai);
			vue.setvMotDePasse(true);
		}
		else{
			vue.getiMotDePasse().setIcon(faux);
			vue.setvMotDePasse(false);
		}
	}
	
	private boolean valide() {
		boolean ret=true;
		if(motDePasse.isEmpty() || motDePasse.length() < 6){
			ret = false;
		}
		return ret;
	}
}
