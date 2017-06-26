package controleur;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import vue.Fenetre;

public class FenetreListener extends WindowAdapter{
	
	private Fenetre fenetre;
	
	public FenetreListener(Fenetre fenetre){
		this.fenetre = fenetre;
	}
	
	@Override
	public void windowClosing(WindowEvent e){
		this.fenetre.getFenetre().setEnabled(true);
	}
}
