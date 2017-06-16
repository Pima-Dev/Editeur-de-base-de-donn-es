package controleur.creation;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.*;

import vue.*;

public class CreationChampEmail implements FocusListener {

	VueDeCreationDUtilisateur vue;
	String email;
	
	public CreationChampEmail(VueDeCreationDUtilisateur vue) {
		this.vue = vue;
	}

	@Override
	public void focusGained(FocusEvent e) {
		
	}

	@Override
	public void focusLost(FocusEvent e) {
		email = vue.getfEmail().getText();
		ImageIcon vrai = new ImageIcon("src/ressources/check.png");
		ImageIcon faux = new ImageIcon("src/ressources/croix.png");
		if(valide()){
			vue.getiEmail().setIcon(vrai);
			vue.setvEmail(true);
		}
		else{
			vue.getiEmail().setIcon(faux);
			vue.setvEmail(false);
		}
	}
	
	private boolean valide() {
		boolean ret = false;
		if(email.contains("@") && email.contains(".")){
			ret = true;
		}
		return ret;
	}
}
