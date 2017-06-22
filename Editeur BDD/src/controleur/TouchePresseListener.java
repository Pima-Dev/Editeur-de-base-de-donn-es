package controleur;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

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
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getSource() instanceof JTextField){
			String nom = this.fenetre.getVuePrincipale().getfChercher().getName();
			if(nom.equals("BarreRecherche")){
				if(fenetre.getVuePrincipale().getDm() == null){
					JOptionPane messageTableau = new JOptionPane();
					messageTableau.showMessageDialog(null, "Table inexistante", "Erreur", JOptionPane.ERROR_MESSAGE);
				}
				else {
					if(fenetre.getVuePrincipale().getfChercher().getText().length()>0)
						fenetre.getVuePrincipale().getDm().rechercher(fenetre.getVuePrincipale().getfChercher().getText());
				}
			}
		}
	}

}
