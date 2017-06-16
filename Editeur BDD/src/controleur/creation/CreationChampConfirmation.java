package controleur.creation;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.*;

import vue.*;

public class CreationChampConfirmation implements FocusListener {

	VueDeCreationDUtilisateur vue;
	String confirmation;
	String motDePasse;
	
	public CreationChampConfirmation(VueDeCreationDUtilisateur vue) {
		this.vue = vue;
	}

	@Override
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void focusLost(FocusEvent e) {
		confirmation = new String(vue.getfConfirmation().getPassword());
		motDePasse = new String(vue.getfMotDePasse().getPassword());
		
		ImageIcon vrai = new ImageIcon("src/ressources/check.png");
		ImageIcon faux = new ImageIcon("src/ressources/croix.png");
		if(valide()){
			vue.getiConfirmation().setIcon(vrai);
			vue.setvConfirmation(true);
		}
		else{
			vue.getiConfirmation().setIcon(faux);
			vue.setvConfirmation(true);
		}
	}
	
	private boolean valide() {
		boolean ret=false;
		if(motDePasse.equals(confirmation)){
			ret = true;
		}
		return ret;
	}
}
