package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
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
			String nom = ((JTextField)e.getSource()).getName();
			if(nom.equals("BarreRecherche")){
				if(fenetre.getVuePrincipale().getDm() == null){
					 JOptionPane.showMessageDialog(null, "Table inexistante", "Erreur", JOptionPane.ERROR_MESSAGE);
					 fenetre.getVuePrincipale().getfChercher().setText("");
				}
				else {
					fenetre.getBDD().getTable(fenetre.getVuePrincipale().getCurrentTable()).refreshTable();
					if(fenetre.getVuePrincipale().getfChercher().getText().length()>0){
						fenetre.getVuePrincipale().getDm().rechercher(fenetre.getVuePrincipale().getfChercher().getText());
					}
				}
			}
			else if(nom.equals("Connexion")){
				if(e.getKeyCode() == 10){
					new PresserBoutonListener(fenetre).actionPerformed(new ActionEvent(e.getSource(), e.getID(), ""));;
				}
			}
		}
		if(e.getSource() instanceof JPasswordField){
			String nom = ((JPasswordField)e.getSource()).getName();
			if(nom.equals("Connexion")){
				if(e.getKeyCode() == 10){
					new PresserBoutonListener(fenetre).actionPerformed(new ActionEvent(e.getSource(), e.getID(), ""));;
				}
			}
		}
	}

}
