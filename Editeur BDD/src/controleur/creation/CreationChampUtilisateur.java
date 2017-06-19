package controleur.creation;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import modele.ELFichier;
import vue.VueDeCreationDUtilisateur;

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
		ELFichier.setNomFile(pseudo);
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
		boolean ret = true;
		if(pseudo.length()>5){
			File file = new File("data");
	        File[] files = file.listFiles();
	        if (files != null) {
	            for (int i = 0; i < files.length; i++) {
	            	if(files[i].getName().replaceAll(".properties", "").equals(pseudo)){
	            		JOptionPane.showMessageDialog(null, "Ce nom d'utilisateur existe déjà", "Erreur", JOptionPane.ERROR_MESSAGE);
	            		ret = false;
	            	}
	            }
	        }
		}
		else{
			ret = false;
		}
		return ret;
	}
}
