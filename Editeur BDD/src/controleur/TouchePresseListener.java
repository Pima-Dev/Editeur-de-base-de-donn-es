package controleur;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextField;

import vue.Fenetre;

public class TouchePresseListener implements KeyListener{

	
	private Fenetre fenetre;

	public TouchePresseListener(Fenetre fenetre) {
		this.fenetre = fenetre;
	}

	@Override
	public void keyTyped(KeyEvent e) {
	
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getSource() instanceof JTextField){
			String nom = this.fenetre.getVuePrincipale().getfChercher().getName();
			if(nom.equals("BarreRecherche")){
				if(e.getKeyCode()== KeyEvent.VK_ENTER){
					
				}
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

}
