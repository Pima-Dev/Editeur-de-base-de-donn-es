package controleur;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;

import modele.BaseDeDonnees;
import modele.CustomException;
import modele.ELFichier;
import modele.Session;
import modele.Util;
import vue.Fenetre;
import vue.VueAjouterAttribut;
import vue.VueCreationBDD;
import vue.VueDeConnexion;
import vue.VueDeCreationDUtilisateur;
import vue.VueOuvrirBDD;
import vue.VuePrincipale;

/**
 * @author Utilisateur
 *
 */
public class PresserBoutonListener implements ActionListener {

	private Fenetre fenetre;

	public PresserBoutonListener(Fenetre fenetre) {
		this.fenetre = fenetre;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() instanceof JButton){
			JButton bouton= (JButton)e.getSource();
			
			if(bouton.getName().equals("Connexion")){
				String nom = this.fenetre.getVueDeConnexion().getfPseudo().getText();
				String motDePasse = new String(this.fenetre.getVueDeConnexion().getfMotDePasse().getPassword());
				if(bonMDP(nom, motDePasse)){
					fenetre.getFenetre().setContentPane(new VuePrincipale(this.fenetre));
					fenetre.getFenetre().setVisible(true);
					Toolkit tk = Toolkit.getDefaultToolkit();
					int xSize = ((int) tk.getScreenSize().getWidth());
					int ySize = ((int) tk.getScreenSize().getHeight());
					fenetre.getFenetre().setSize(xSize,ySize);
					fenetre.getFenetre().setLocationRelativeTo(null);
					fenetre.setSesstion(new Session(nom));
				}
				else{
					this.fenetre.getVueDeConnexion().getlErreurIdentifiant().setText("<HTML><i>Erreur d'identifiant</i></HTML>");;
					fenetre.getFenetre().setVisible(true);
					fenetre.getFenetre().setLocationRelativeTo(null);
				}
			}
			else if(bouton.getName().equals("Nouvel utilisateur")){
				fenetre.getFenetre().setContentPane(new VueDeCreationDUtilisateur(fenetre));
				fenetre.getFenetre().setVisible(true);
				fenetre.getFenetre().setSize(new Dimension(400,600));
				fenetre.getFenetre().setLocationRelativeTo(null);
			}
			else if(bouton.getName().equals("Mot de passe oublie")){
				JOptionPane.showMessageDialog(null, "MDP oublié");
			}
			else if(bouton.getName().equals("Valider mdp oublié")){
				String nouveau = new String(this.fenetre.getVueMDPOublieNouveau().getfNouveau().getPassword());
				String confirmation = new String(this.fenetre.getVueMDPOublieNouveau().getfConfirmation().getPassword());
				if(!nouveau.equals("") && !confirmation.equals("") && nouveau.equals(confirmation)){
					VueDeConnexion vueCo = new VueDeConnexion(fenetre);
					this.fenetre.setVueDeConnexion(vueCo);
					fenetre.getFenetre().setContentPane(vueCo);
					fenetre.getFenetre().setVisible(true);
					fenetre.getFenetre().pack();
					fenetre.getFenetre().setLocationRelativeTo(null);
				}
				this.fenetre.getVueMDPOublieNouveau().getlInfo().setText("Erreur de mot de passe");
			}
			
			else if(bouton.getName().equals("Valider creation nouvel utilisateur")){
				JOptionPane jop = new JOptionPane();
				if(this.fenetre.getVueCreationUtilisateur().isvUtilisateur() && this.fenetre.getVueCreationUtilisateur().isvEmail() && this.fenetre.getVueCreationUtilisateur().isvMotDePasse() && this.fenetre.getVueCreationUtilisateur().isvConfirmation()){

					String entre = jop.showInputDialog(null, "Veuillez entrer le code qui vous à été envoyé par mail", "Confirmation d'Email", JOptionPane.QUESTION_MESSAGE);
					if(entre != null){
						if(entre.equals("")){
							String nom = this.fenetre.getVueCreationUtilisateur().getfUtilisateur().getText();
							ELFichier.creerDossier(nom);
							ELFichier.setCle(nom+"/session", "user", nom);
							ELFichier.setCle(nom+"/session", "MDP", ELFichier.cryptMDP(new String(this.fenetre.getVueCreationUtilisateur().getfMotDePasse().getPassword())));
							ELFichier.setCle(nom+"/session", "email", this.fenetre.getVueCreationUtilisateur().getfEmail().getText());
							ELFichier.setCle(nom+"/session", "Q1", this.fenetre.getVueCreationUtilisateur().gettQ1().getText());
							ELFichier.setCle(nom+"/session", "Q2", this.fenetre.getVueCreationUtilisateur().gettQ2().getText());
							ELFichier.setCle(nom+"/session", "Q3", this.fenetre.getVueCreationUtilisateur().gettQ3().getText());
							VueDeConnexion vueCo = new VueDeConnexion(this.fenetre);
							this.fenetre.setVueDeConnexion(vueCo);
							fenetre.getFenetre().setContentPane(vueCo);
							fenetre.getFenetre().setVisible(true);
							fenetre.getFenetre().pack();
							fenetre.getFenetre().setLocationRelativeTo(null);
						}
					}
				}
			}
			else if(bouton.getName().equals("valider creation bdd")){
				int port = modele.Util.isInteger(this.fenetre.getVueCreationBDD().getfPort().getText()) ? Integer.parseInt(this.fenetre.getVueCreationBDD().getfPort().getText()) : 3306;
				try {
					BaseDeDonnees bdd = new BaseDeDonnees(this.fenetre.getVueCreationBDD().getfNomBDD().getText(), this.fenetre.getVueCreationBDD().getfNomUtilisateur().getText(), new String(this.fenetre.getVueCreationBDD().getfMotDePasse().getPassword()), this.fenetre, this.fenetre.getVueCreationBDD().getfURL().getText(), port);
					bdd.creerBDD();
					this.fenetre.getVueCreationBDD().getFrame().dispose();
				} catch (CustomException e1) {
					Util.logErreur(e1.getMessage());
				}
			}
			
			else if(bouton.getName().equals("ajouter attribut")){
				try {
					new VueAjouterAttribut(this.fenetre);
				} catch (CustomException e1) {
					Util.logErreur(e1.getMessage());
				}
			}
			
			else if(bouton.getName().equals("valider ouverture bdd")){
				//BaseDeDonnees bdd = new BaseDeDonnees(this.fenetre.get, nomUtilisateur, motDePasse, fenetre, url, port)
			}
			
		}
		else if(e.getSource() instanceof JRadioButton){
			JRadioButton radioBouton= (JRadioButton)e.getSource();
			
			if(radioBouton.getName().equals("creer bdd hebergement local")){
				fenetre.getVueCreationBDD().getfURL().setEnabled(false);
				fenetre.getVueCreationBDD().getBoutonServeur().setSelected(false);
				fenetre.getVueCreationBDD().getfURL().setText("");
				fenetre.getVueCreationBDD().getfPort().setText("");
				fenetre.getVueCreationBDD().getfPort().setEnabled(false);
			}
			else if(radioBouton.getName().equals("creer bdd hebergement distant")){
				fenetre.getVueCreationBDD().getBoutonLocal().setSelected(false);
				fenetre.getVueCreationBDD().getfURL().setEnabled(true);
				fenetre.getVueCreationBDD().getfPort().setEnabled(true);
			}
			
			else if(radioBouton.getName().equals("ouvir bdd hebergement local")){
				fenetre.getVueOuvrirBDD().getfURL().setEnabled(false);
				fenetre.getVueOuvrirBDD().getBoutonServeur().setSelected(false);
				fenetre.getVueOuvrirBDD().getfURL().setText("");
				fenetre.getVueOuvrirBDD().getfPort().setText("");
				fenetre.getVueOuvrirBDD().getfPort().setEnabled(false);
			}
			else if(radioBouton.getName().equals("ouvrir bdd hebergement distant")){
				fenetre.getVueOuvrirBDD().getBoutonLocal().setSelected(false);
				fenetre.getVueOuvrirBDD().getfURL().setEnabled(true);
				fenetre.getVueOuvrirBDD().getfPort().setEnabled(true);
			}
		}
		
		else if(e.getSource() instanceof JMenuItem){
			JMenuItem item = (JMenuItem) e.getSource();
			if(item.getName().equals("MenuNouveau")){
				new VueCreationBDD(this.fenetre);
			}
			else if(item.getName().equals("MenuOuvrir")){
				new VueOuvrirBDD(this.fenetre);
			}
			else if(item.getName().equals("MenuSupprimerBDD")){
				JOptionPane messageComfirmation = new JOptionPane();
				int validation = messageComfirmation.showConfirmDialog(null,"Voulez-vous supprimer la BDD courante?","Suppression de la BDD",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
				if(validation == JOptionPane.OK_OPTION){
					
				}
			}
		}
		
		else if(e.getSource() instanceof JComboBox){
			JComboBox box = (JComboBox) e.getSource();
			if(box.getName().equals("ouvrir bdd liste bdd")){
				if(box.getSelectedItem().toString().equals("")){
					fenetre.getVueOuvrirBDD().getfURL().setEnabled(true);
					fenetre.getVueOuvrirBDD().getfPort().setEnabled(true);
					fenetre.getVueOuvrirBDD().getfNomUtilisateur().setEnabled(true);
					fenetre.getVueOuvrirBDD().getfMotDePasse().setEnabled(true);
					fenetre.getVueOuvrirBDD().gettNom().setEnabled(true);
					fenetre.getVueOuvrirBDD().getBoutonLocal().setEnabled(true);
					fenetre.getVueOuvrirBDD().getBoutonServeur().setEnabled(true);

				}
				else {
					fenetre.getVueOuvrirBDD().getfURL().setEnabled(false);
					fenetre.getVueOuvrirBDD().getfPort().setEnabled(false);
					fenetre.getVueOuvrirBDD().getfNomUtilisateur().setEnabled(false);
					fenetre.getVueOuvrirBDD().getfMotDePasse().setEnabled(false);
					fenetre.getVueOuvrirBDD().gettNom().setEnabled(false);
					fenetre.getVueOuvrirBDD().getBoutonLocal().setEnabled(false);
					fenetre.getVueOuvrirBDD().getBoutonServeur().setEnabled(false);
					
					fenetre.getVueOuvrirBDD().getfURL().setText("");
					fenetre.getVueOuvrirBDD().getfPort().setText("");
					fenetre.getVueOuvrirBDD().getfNomUtilisateur().setText("");
					fenetre.getVueOuvrirBDD().getfMotDePasse().setText("");
					fenetre.getVueOuvrirBDD().gettNom().setText("");
					fenetre.getVueOuvrirBDD().getBoutonServeur().setSelected(false);
					fenetre.getVueOuvrirBDD().getBoutonLocal().setSelected(false);
				}
			}
		}
		
		
	}

	public boolean bonMDP(String nom, String mdp){
		boolean ret = false;
		String encrypt = ELFichier.cryptMDP(mdp);
		if(encrypt.equals(ELFichier.chargerValeur(nom+"/session", "MDP")) && nom.equals(ELFichier.chargerValeur(nom+"/session", "user"))){
			ret = true;
		}
		return ret;
	}
}
