package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import modele.CustomException;
import modele.Table;
import vue.Fenetre;

/**
 * Cette classe est appelée lors de l'appuye d'une touche sur le clavier
 */
public class TouchePresseListener implements KeyListener {

	/**
	 * la racine de référence qui permet d'accéder à toutes les vues
	 */
	private Fenetre fenetre;

	/**
	 * construit le listener
	 * 
	 * @param fenetre Le main de l'application
	 */
	public TouchePresseListener(Fenetre fenetre) {
		this.fenetre = fenetre;
	}

	/**
	 * redéfinition de la méthode keyTyped qui s'éxécute après l'appuye d'une
	 * touche sur le clavier
	 */
	@Override
	public void keyTyped(KeyEvent e) {

	}

	/**
	 * redéfinition de la méthode keyPressed qui s'éxécute lors l'appuye d'une
	 * touche sur le clavier
	 */
	@Override
	public void keyPressed(KeyEvent e) {

	}

	/**
	 * redéfinition de la méthode keyPressed qui s'éxécute lors du relachement
	 * d'une touche sur le clavier
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getSource() instanceof JTextField) {
			String nom = ((JTextField) e.getSource()).getName();
			if (nom.equals("BarreRecherche")) {
				if (this.fenetre.getBDD() == null) {
					new CustomException("Erreur", "Aucune base de données n'est ouverte.");
					fenetre.getVuePrincipale().getfChercher().setText("");
					return;
				}
				Table table = this.fenetre.getBDD().getTable(this.fenetre.getVuePrincipale().getCurrentTable());
				if (table == null) {
					new CustomException("Erreur", "Une erreur est survenu, veuillez réassayer.");
					fenetre.getVuePrincipale().getfChercher().setText("");
					return;
				}

				fenetre.getBDD().getTable(fenetre.getVuePrincipale().getCurrentTable()).refreshTable();
				if (fenetre.getVuePrincipale().getfChercher().getText().length() > 0) {
					fenetre.getVuePrincipale().getDm().rechercher(fenetre.getVuePrincipale().getfChercher().getText());

				}
			} else if (nom.equals("Connexion")) {
				if (e.getKeyCode() == 10) {
					new PresserBoutonListener(fenetre).actionPerformed(new ActionEvent(e.getSource(), e.getID(), ""));
					;
				}
			}
		}
		if (e.getSource() instanceof JPasswordField) {
			String nom = ((JPasswordField) e.getSource()).getName();
			if (nom.equals("Connexion")) {
				if (e.getKeyCode() == 10) {
					new PresserBoutonListener(fenetre).actionPerformed(new ActionEvent(e.getSource(), e.getID(), ""));
					;
				}
			}
		}
	}

}
