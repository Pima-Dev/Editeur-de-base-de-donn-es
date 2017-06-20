package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import modele.ELFichier;
import vue.Fenetre;
/**
 * Cette classe est appelée lors de l'interaction avec un objet JTextField
 * @author Utilisateur
 *
 */
public class ChampsListener implements FocusListener{

	/**
	 * L'objet fenetre est la racine d'où tout le code hérite
	 */
	private Fenetre fenetre;

	/**
	 * Constructeur qui initialise la fenêtre
	 * @param fenetre la racine d'ou tout le code herite
	 */
	public ChampsListener(Fenetre fenetre) {
		this.fenetre = fenetre;
	}

	/**
	 * Redéfinition de la méthode Focus lost qui s'active lorsque l'utilisateur quitte un champ
	 */
	@Override
	public void focusLost(FocusEvent e) {
		if(e.getSource() instanceof JTextField){
			JTextField champ= (JTextField)e.getSource();
			ImageIcon vrai = new ImageIcon("src/ressources/check.png");
			ImageIcon faux = new ImageIcon("src/ressources/croix.png");
			if(champ.getName().equals("nom nouvel utilisateur")){
				String pseudo = fenetre.getVueCreationUtilisateur().getfUtilisateur().getText();
				if(validePseudo(pseudo)){
					fenetre.getVueCreationUtilisateur().getiUtilisateur().setIcon(vrai);
					fenetre.getVueCreationUtilisateur().setvUtilisateur(true);
				}
				else{
					fenetre.getVueCreationUtilisateur().getiUtilisateur().setIcon(faux);
					fenetre.getVueCreationUtilisateur().setvUtilisateur(false);
				}
			}
			else if(champ.getName().equals("email nouvel utilisateur")){
				String email = fenetre.getVueCreationUtilisateur().getfEmail().getText();
				if(email.contains("@") && email.contains(".")){
					fenetre.getVueCreationUtilisateur().getiEmail().setIcon(vrai);
					fenetre.getVueCreationUtilisateur().setvEmail(true);
				}
				else{
					fenetre.getVueCreationUtilisateur().getiEmail().setIcon(faux);
					fenetre.getVueCreationUtilisateur().setvEmail(false);
				}
			}
			else if(champ.getName().equals("mot de passe nouvel utilisateur")){
				String motDePasse = new String(fenetre.getVueCreationUtilisateur().getfMotDePasse().getPassword());
				if(motDePasse.length() >= 5){
					fenetre.getVueCreationUtilisateur().getiMotDePasse().setIcon(vrai);
					fenetre.getVueCreationUtilisateur().setvMotDePasse(true);
				}
				else{
					fenetre.getVueCreationUtilisateur().getiMotDePasse().setIcon(faux);
					fenetre.getVueCreationUtilisateur().setvMotDePasse(false);
				}
			}
			else if(champ.getName().equals("confirmation mot de passe nouvel utilisateur")){
				String confirmation = new String(fenetre.getVueCreationUtilisateur().getfConfirmation().getPassword());
				String motDePasse = new String(fenetre.getVueCreationUtilisateur().getfMotDePasse().getPassword());
				if(motDePasse.equals(confirmation)){
					fenetre.getVueCreationUtilisateur().getiConfirmation().setIcon(vrai);
					fenetre.getVueCreationUtilisateur().setvConfirmation(true);
				}
				else{
					fenetre.getVueCreationUtilisateur().getiConfirmation().setIcon(faux);
					fenetre.getVueCreationUtilisateur().setvConfirmation(true);
				}
			}
			else if(champ.getName().equals("BarreRecherche") && fenetre.getVuePrincipale().getfChercher().getText().equals("")){
				fenetre.getVuePrincipale().getfChercher().setText("Chercher les occurences");
			}
		}
	}

	/**
	 * ValidePseudo renvoie vrai si le pseudo n'existe pas et si il est plus grand que 5
	 * @param pseudo le nouveau pseudo
	 * @return vrai si le pseudo est correct
	 */
	private boolean validePseudo(String pseudo) {
		boolean ret = true;
		if(pseudo.length()>5){
			File file = new File(ELFichier.getRacine());
	        File[] files = file.listFiles();
	        if (files != null) {
	            for (int i = 0; i < files.length; i++) {
	            	if(files[i].getName().equals(pseudo)){
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

	/**
	 * Redéfinition de la méthode Focus gained qui s'active lorsque l'utilisateur entre dans un champ
	 */
	@Override
	public void focusGained(FocusEvent e) {
		if(e.getSource() instanceof JTextField){
			JTextField champ= (JTextField)e.getSource();
			if(champ.getName().equals("BarreRecherche") && fenetre.getVuePrincipale().getfChercher().getText().equals("Chercher les occurences")){
				fenetre.getVuePrincipale().getfChercher().setText("");
			}
		}
	}

}
