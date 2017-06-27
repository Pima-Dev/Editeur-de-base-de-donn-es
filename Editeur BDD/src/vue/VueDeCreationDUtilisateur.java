package vue;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import controleur.ChampsListener;
import controleur.PresserBoutonListener;
import controleur.TouchePresseListener;
/**
 * s'affiche lorsque l'utilisateur appuye sur le bouton création de compte, elle permet de créer un nouveau compte
 */
public class VueDeCreationDUtilisateur extends JPanel{
	/**
	 * la racine de référence qui permet d'accéder à toutes les vues
	 */
	private Fenetre fenetre;
	
	/**
	 * affiche le titre
	 */
	private JLabel lTitre;
	/**
	 * affiche le titre du champ de pseudo
	 */
	private JLabel lUtilisateur;
	//private JLabel lEmail;
	/**
	 * affiche le titre du champ de mot de passe
	 */
	private JLabel lMotDePasse;
	/**
	 * affiche le titre du champ de confirmation de mot de passe
	 */
	private JLabel lConfirmation;
	
	/**
	 * s'affiche lors de l'entrée d'une valeur dans le champ de pseudo pour la valider ou l'invalider
	 */
	private JLabel iUtilisateur;
	//private JLabel iEmail;
	/**
	 * s'affiche lors de l'entrée d'une valeur dans le champ de mot de passe pour la valider ou l'invalider
	 */
	private JLabel iMotDePasse;
	/**
	 * s'affiche lors de l'entrée d'une valeur dans le champ de confirmation de mot de passe pour la valider ou l'invalider
	 */
	private JLabel iConfirmation;
	/**
	 * entrée du pseudo
	 */
	private JTextField fUtilisateur;
	//private JTextField fEmail;
	/**
	 * entrée du mot de passe
	 */
	private JPasswordField fMotDePasse;
	/**
	 * entrée de la confirmation de mot de passe
	 */
	private JPasswordField fConfirmation;
	/**
	 * accéder à la connexion et valider la création d'utilisateur
	 */
	private JButton bCreation;
	/**
	 * accéder à la connexion et invalder la création d'utilisateur
	 */
	private JButton bRetour;
	/**
	 * panneau contenant le panneau des champs, le panneau de vérification et les boutons retour et valider
	 */
	private JPanel panneau;
	/**
	 * panneau contenant les champs à remplir
	 */
	private JPanel panneauChamps;
	/**
	 * panneau contenat les boutons de validation et de retour
	 */
	private JPanel panneauBouton;
	/**
	 * panneau contenant les symboles de vérification de champ
	 */
	private JPanel verif;
	
	//private JLabel lQ1;
	//private JLabel lQ2;
	//private JLabel lQ3;
	//private JTextField tQ1;
	//private JTextField tQ2;
	//private JTextField tQ3;
	/**
	 * état de la validation du champ de pseudo
	 */
	private boolean vUtilisateur;
	//private boolean vEmail;
	/**
	 * état de la validation du champ de mot de passe
	 */
	private boolean vMotDePasse;
	/**
	 * état de la validation du champ de confirmation de mot de passe
	 */
	private boolean vConfirmation;
	
	/**
	 * construit le panneau de la fenêtre de création d'utilisateur
	 * @param fenetre la racine de référence qui permet d'accéder à toutes les vues
	 */
	public VueDeCreationDUtilisateur(Fenetre fenetre){
		this.fenetre = fenetre;
		this.fenetre.setVueCreationUtilisateur(this);
		vConfirmation = false;
		vMotDePasse = false;
		//vEmail = false;
		vUtilisateur = false;
		declaration();
		panneauBouton.add(bRetour);
		panneauBouton.add(bCreation);
		panneauChamps.add(lTitre);
		panneauChamps.add(lUtilisateur);
		panneauChamps.add(fUtilisateur);
		//panneauChamps.add(lEmail);
		//panneauChamps.add(fEmail);
		panneauChamps.add(lMotDePasse);
		panneauChamps.add(fMotDePasse);
		panneauChamps.add(lConfirmation);
		panneauChamps.add(fConfirmation);
		//panneauChamps.add(this.lQ1);
		//panneauChamps.add(this.tQ1);
		//panneauChamps.add(this.lQ2);
		//panneauChamps.add(this.tQ2);
		//panneauChamps.add(this.lQ3);
		//panneauChamps.add(this.tQ3);
		panneauChamps.add(panneauBouton);
		
		verif.add(new JLabel(""));
		verif.add(new JLabel(""));
		verif.add(iUtilisateur);
		verif.add(new JLabel(""));
		//verif.add(iEmail);
		//verif.add(new JLabel(""));
		verif.add(iMotDePasse);
		verif.add(new JLabel(""));
		verif.add(iConfirmation);
		verif.add(new JLabel(""));
		panneau.add(panneauChamps,BorderLayout.CENTER);
		panneau.add(verif,BorderLayout.EAST);
		add(new JLabel("       "),BorderLayout.WEST);
		add(panneau);
		add(new JLabel("       "),BorderLayout.EAST);
		fUtilisateur.setName("nom nouvel utilisateur");
		fUtilisateur.addFocusListener(new ChampsListener(this.fenetre));
		fUtilisateur.addKeyListener(new TouchePresseListener(fenetre));
		//fEmail.setName("email nouvel utilisateur");
		//fEmail.addFocusListener(new ChampsListener(this.fenetre));
		//fEmail.addKeyListener(new TouchePresseListener(fenetre));
		fMotDePasse.setName("mot de passe nouvel utilisateur");
		fMotDePasse.addFocusListener(new ChampsListener(this.fenetre));
		fMotDePasse.addKeyListener(new TouchePresseListener(fenetre));
		fConfirmation.setName("confirmation mot de passe nouvel utilisateur");
		fConfirmation.addFocusListener(new ChampsListener(this.fenetre));
		fConfirmation.addKeyListener(new TouchePresseListener(fenetre));
		bCreation.setName("Valider creation nouvel utilisateur");
		bCreation.addActionListener(new PresserBoutonListener(this.fenetre));
		bRetour.setName("Retour");
		bRetour.addActionListener(new PresserBoutonListener(this.fenetre));
	}
	
	/**
	 * création des éléments du panneau
	 */
	public void declaration(){
		this.setLayout(new BorderLayout());
		this.add(new JLabel("      "), BorderLayout.SOUTH);
		panneauChamps = new JPanel();
		panneauChamps.setLayout(new GridLayout(0,1,5,5));
		panneauBouton = new JPanel();
		panneauBouton.setLayout(new GridLayout(1, 2, 10, 10));
		panneau = new JPanel();
		panneau.setLayout(new BorderLayout());
		verif = new JPanel();
		verif.setLayout(new GridLayout(0,1,5,10));
		lTitre = new JLabel("CREATION DE COMPTE");
		lTitre.setHorizontalAlignment(SwingConstants.CENTER);
		lUtilisateur = new JLabel("Utilisateur");
		//lEmail = new JLabel("E-Mail");
		lMotDePasse = new JLabel("Mot de passe");
		lConfirmation = new JLabel("Confirmation de mot de passe");
		iUtilisateur = new JLabel(new ImageIcon(""));
		//iEmail = new JLabel(new ImageIcon(""));
		iMotDePasse = new JLabel(new ImageIcon(""));
		iConfirmation = new JLabel(new ImageIcon(""));
		fUtilisateur = new JTextField();
		//fEmail = new JTextField();
		fMotDePasse = new JPasswordField();
		fConfirmation = new JPasswordField();
		bCreation = new JButton("Créer");
		bRetour = new JButton("Retour");
		//this.lQ1 = new JLabel("Quel est le nom de votre meilleur(e) ami(e) ?");
		//this.lQ2 = new JLabel("Dans quel ville êtes vous né ?");
		//this.lQ3 = new JLabel("Quel est le nom de votre mère ?");
		//this.tQ1 = new JTextField();
		//this.tQ2 = new JTextField();
		//this.tQ3 = new JTextField();
	}

	/**
	 * accès au JTextField fUtilisateur
	 * @return le JTextField fUtilisateur
	 */
	public JTextField getfUtilisateur() {
		return fUtilisateur;
	}

	//public JTextField getfEmail() {
	//	return fEmail;
	//}

	/**
	 * accès au JPasswordField fMotDePasse
	 * @return le JPasswordField fMotDePasse
	 */
	public JPasswordField getfMotDePasse() {
		return fMotDePasse;
	}

	/**
	 * accès au JPasswordField getfConfirmation
	 * @return le JPasswordField getfConfirmation
	 */
	public JPasswordField getfConfirmation() {
		return fConfirmation;
	}

	/**
	 * accès au JButton bCreation
	 * @return le JButton bCreation
	 */
	public JButton getbCreation() {
		return bCreation;
	}

	/**
	 * accès au JLabel iUtilisateur
	 * @return le JLabel iUtilisateur
	 */
	public JLabel getiUtilisateur() {
		return iUtilisateur;
	}

	/*
	public JLabel getiEmail() {
		return iEmail;
	}*/

	/**
	 * accès au JLabel iMotDePasse
	 * @return le JLabel iMotDePasse
	 */
	public JLabel getiMotDePasse() {
		return iMotDePasse;
	}

	/**
	 * accès au JLabel iConfirmation
	 * @return le JLabel iConfirmation
	 */
	public JLabel getiConfirmation() {
		return iConfirmation;
	}


	/**
	 * accès au boolean vUtilisateur
	 * @return le boolean vUtilisateur
	 */
	public boolean isvUtilisateur() {
		return vUtilisateur;
	}

	/*
	public boolean isvEmail() {
		return vEmail;
	}*/

	/**
	 * accès au boolean vMotDePasse
	 * @return le boolean vMotDePasse
	 */
	public boolean isvMotDePasse() {
		return vMotDePasse;
	}

	/**
	 * accès au boolean vConfirmation
	 * @return le boolean vConfirmation
	 */
	public boolean isvConfirmation() {
		return vConfirmation;
	}


	/**
	 * modification du boolean vUtilisateur
	 * @param vUtilisateur le boolean de validation de l'Utilisateur
	 */
	public void setvUtilisateur(boolean vUtilisateur) {
		this.vUtilisateur = vUtilisateur;
	}


	/*
	public void setvEmail(boolean vEmail) {
		this.vEmail = vEmail;
	}*/


	/**
	 * modification du boolean vMotDePasse
	 * @param vMotDePasse le boolean de validation du mot de passe
	 */
	public void setvMotDePasse(boolean vMotDePasse) {
		this.vMotDePasse = vMotDePasse;
	}


	/**
	 * modification du boolean vConfirmation
	 * @param vConfirmation le boolean de validation de la confirmation de mot de passe
	 */
	public void setvConfirmation(boolean vConfirmation) {
		this.vConfirmation = vConfirmation;
	}


	//public JTextField gettQ1() {
	//	return tQ1;
	//}


	//public JTextField gettQ2() {
	//	return tQ2;
	//}


	//public JTextField gettQ3() {
	//	return tQ3;
	//}


	//public JLabel getlQ1() {
	//	return lQ1;
	//}


	//public JLabel getlQ2() {
	//	return lQ2;
	//}


	//public JLabel getlQ3() {
	//	return lQ3;
	//}

	
}