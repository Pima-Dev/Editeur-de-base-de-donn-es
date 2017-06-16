package controleur;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import vue.*;

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
				}
				else{
					this.fenetre.getVueDeConnexion().getlErreurIdentifiant().setText("<HTML><i>Erreur d'identifiant</i></HTML>");;
					fenetre.getFenetre().setVisible(true);
				}
			}
			else if(bouton.getName().equals("Nouvel utilisateur")){
				fenetre.getFenetre().setContentPane(new VueDeCreationDUtilisateur(fenetre));
				fenetre.getFenetre().setVisible(true);
				fenetre.getFenetre().setSize(new Dimension(300,400));
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
				}
				this.fenetre.getVueMDPOublieNouveau().getlInfo().setText("Erreur de mot de passe");
			}
			
			else if(bouton.getName().equals("Valider creation nouvel utilisateur")){
				JOptionPane jop = new JOptionPane();
				if(this.fenetre.getVueCreationUtilisateur().isvUtilisateur() && this.fenetre.getVueCreationUtilisateur().isvEmail() && this.fenetre.getVueCreationUtilisateur().isvMotDePasse() && this.fenetre.getVueCreationUtilisateur().isvConfirmation()){

					String entre = jop.showInputDialog(null, "Veuillez entrer le code qui vous à été envoyé par mail", "Confirmation d'Email", JOptionPane.QUESTION_MESSAGE);
					if(entre != null){
						if(entre.equals("")){
							VueDeConnexion vueCo = new VueDeConnexion(this.fenetre);
							this.fenetre.setVueDeConnexion(vueCo);
							fenetre.getFenetre().setContentPane(vueCo);
							fenetre.getFenetre().setVisible(true);
							fenetre.getFenetre().pack();
						}
					}
				}
			}
			
		}
		
		
	}

	public boolean bonMDP(String nom, String mdp){
		return true;
	}
}
