package controleur;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;

import modele.ELFichier;
import vue.Fenetre;
import vue.VueDeConnexion;
import vue.VueDeCreationDUtilisateur;
import vue.VuePrincipale;

/**
 * @author Utilisateur
 *
 */
public class PressButtonListener implements ActionListener {

	private Fenetre fenetre;

	public PressButtonListener(Fenetre fenetre) {
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
					fenetre.getFenetre().setContentPane(new VuePrincipale());
					fenetre.getFenetre().setVisible(true);
					fenetre.getFenetre().pack();
					fenetre.getFenetre().setLocationRelativeTo(null);
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
				fenetre.getFenetre().setSize(new Dimension(300,400));
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
							ELFichier.setNomFile(nom);
							ELFichier.setCle("user", nom);
							ELFichier.setCle("MDP", ELFichier.cryptMDP(new String(this.fenetre.getVueCreationUtilisateur().getfMotDePasse().getPassword())));
							ELFichier.setCle("email", this.fenetre.getVueCreationUtilisateur().getfEmail().getText());
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
			else if(bouton.getName().equals("valider")){
				if(true){
					
				}
				else if(false){
					fenetre.getVueCreationBDD().getlErreur().setText("message d'erreur");
				}
			}
			
		}
		if(e.getSource() instanceof JRadioButton){
			JRadioButton radioBouton= (JRadioButton)e.getSource();
			
			if(radioBouton.getName().equals("hebergement local")){
				fenetre.getVueCreationBDD().getfURL().setEnabled(false);
				fenetre.getVueCreationBDD().getBoutonServeur().setSelected(false);
			}
			else if(radioBouton.getName().equals("hebergement distant")){
				fenetre.getVueCreationBDD().getBoutonLocal().setSelected(false);
			}
		}
		
		
	}

	public boolean bonMDP(String nom, String mdp){
		boolean ret = false;
		ELFichier.setNomFile(nom);
		String encrypt = ELFichier.cryptMDP(mdp);
		if(encrypt.equals(ELFichier.chargerValeur("MDP")) && nom.equals(ELFichier.chargerValeur("user"))){
			ret = true;
		}
		return ret;
	}
}
