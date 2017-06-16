package controleur.creation;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.*;

import vue.*;

public class CreationChampUtilisateur implements FocusListener {

	VueDeCreationDUtilisateur vue;
	String pseudo;
	
	public CreationChampUtilisateur(VueDeCreationDUtilisateur vue) {
		this.vue = vue;
	}

	@Override
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void focusLost(FocusEvent e) {
		pseudo = vue.getfUtilisateur().getText();
		ImageIcon vrai = new ImageIcon("src/ressources/check.png");
		ImageIcon faux = new ImageIcon("src/ressources/croix.png");
		if(valide()){
			vue.getiUtilisateur().setIcon(vrai);
			vue.setvUtilisateur(true);
		}
		else{
			vue.getiUtilisateur().setIcon(faux);
			vue.setvUtilisateur(false);
		}
	}
	
	private boolean valide() {
		boolean ret = false;
		if(pseudo.equals("michel")){
			ret = true;
		}
		return ret;
	}
}
