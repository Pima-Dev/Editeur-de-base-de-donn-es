package vue;

import javax.swing.*;
import java.awt.*;

public class CreationUserView extends JPanel{
	
	private JLabel lTitre;
	private JLabel lUtilisateur;
	private JLabel lEmail;
	private JLabel lMotDePasse;
	private JLabel lConfirmation;
	
	private JLabel iUtilisateur;
	private JLabel iEmail;
	private JLabel iMotDePasse;
	private JLabel iConfirmation;
	private JTextField fUtilisateur;
	private JTextField fEmail;
	private JPasswordField fMotDePasse;
	private JPasswordField fConfirmation;
	private JButton bCreation;
	private JPanel panneau;
	private JPanel panneauChamps;
	private JPanel verif;
	
	public CreationUserView(){
		decoration();
		panneauChamps.add(lTitre);
		panneauChamps.add(lUtilisateur);
		panneauChamps.add(fUtilisateur);
		panneauChamps.add(lEmail);
		panneauChamps.add(fEmail);
		panneauChamps.add(lMotDePasse);
		panneauChamps.add(fMotDePasse);
		panneauChamps.add(lConfirmation);
		panneauChamps.add(fConfirmation);
		panneauChamps.add(bCreation);
		
		verif.add(new JLabel(""));
		verif.add(new JLabel(""));
		verif.add(iUtilisateur);
		verif.add(new JLabel(""));
		verif.add(iEmail);
		verif.add(new JLabel(""));
		verif.add(iMotDePasse);
		verif.add(new JLabel(""));
		verif.add(iConfirmation);
		verif.add(new JLabel(""));
		panneau.add(panneauChamps,BorderLayout.CENTER);
		panneau.add(verif,BorderLayout.EAST);
		this.add(new JLabel("       "),BorderLayout.WEST);
		this.add(panneau);
		this.add(new JLabel("       "),BorderLayout.EAST);
	}
	
	public void decoration(){
		this.setLayout(new BorderLayout());
		panneauChamps = new JPanel();
		panneauChamps.setLayout(new GridLayout(10,1,5,5));
		panneau = new JPanel();
		panneau.setLayout(new BorderLayout());
		verif = new JPanel();
		verif.setLayout(new GridLayout(10,1,5,10));
		lTitre = new JLabel("CREATION DE COMPTE");
		lTitre.setHorizontalAlignment(SwingConstants.CENTER);
		lUtilisateur = new JLabel("Utilisateur");
		lEmail = new JLabel("E-Mail");
		lMotDePasse = new JLabel("Mot de passe");
		lConfirmation = new JLabel("Confirmation de mot de passe");
		iUtilisateur = new JLabel(new ImageIcon("../ressources/check.png"));
		iEmail = new JLabel();
		iMotDePasse = new JLabel();
		iConfirmation = new JLabel();
		fUtilisateur = new JTextField();
		fEmail = new JTextField();
		fMotDePasse = new JPasswordField();
		fConfirmation = new JPasswordField();
		bCreation = new JButton("Créer");
		
	}
	
	public static void main(String[] args){
		JFrame fenetre = new JFrame("Connexion");
		fenetre.setContentPane(new CreationUserView());
		fenetre.setVisible(true);
		fenetre.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		fenetre.pack();
	}
}
