package controleur;

import java.awt.event.ActionEvent;
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
			else if(nom.equals("Valider creation nouvel utilisateur")){
				if(e.getKeyCode() == 10){
					new PresserBoutonListener(fenetre).actionPerformed(new ActionEvent(e.getSource(), e.getID(), ""));;
				}
			}else if(nom.equals("nom nouvel utilisateur") || nom.equals("email nouvel utilisateur") || nom.equals("mot de passe nouvel utilisateur") || nom.equals("confirmation mot de passe nouvel utilisateur")){
				if(e.getKeyCode() == 10){
					((JTextField)e.getSource()).setName("Valider creation nouvel utilisateur");
					new PresserBoutonListener(fenetre).actionPerformed(new ActionEvent(e.getSource(), e.getID(), ""));;
				}
			}
		}
	}

}
