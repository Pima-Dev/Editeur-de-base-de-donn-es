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

public class VueDeCreationDUtilisateur extends JPanel{
	private Fenetre fenetre;
	
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
	private JLabel lQ1;
	private JLabel lQ2;
	private JLabel lQ3;
	private JTextField tQ1;
	private JTextField tQ2;
	private JTextField tQ3;
	
	private boolean vUtilisateur;
	private boolean vEmail;
	private boolean vMotDePasse;
	private boolean vConfirmation;
	
	public VueDeCreationDUtilisateur(Fenetre fenetre){
		this.fenetre = fenetre;
		this.fenetre.setVueCreationUtilisateur(this);
		vConfirmation = false;
		vMotDePasse = false;
		vEmail = false;
		vUtilisateur = false;
		declaration();
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
		panneauChamps.add(bCreation);
		
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
		fEmail.setName("email nouvel utilisateur");
		fEmail.addFocusListener(new ChampsListener(this.fenetre));
		fEmail.addKeyListener(new TouchePresseListener(fenetre));
		fMotDePasse.setName("mot de passe nouvel utilisateur");
		fMotDePasse.addFocusListener(new ChampsListener(this.fenetre));
		fMotDePasse.addKeyListener(new TouchePresseListener(fenetre));
		fConfirmation.setName("confirmation mot de passe nouvel utilisateur");
		fConfirmation.addFocusListener(new ChampsListener(this.fenetre));
		fConfirmation.addKeyListener(new TouchePresseListener(fenetre));
		bCreation.setName("Valider creation nouvel utilisateur");
		bCreation.addActionListener(new PresserBoutonListener(this.fenetre));
	}
	

	public void declaration(){
		this.setLayout(new BorderLayout());
		panneauChamps = new JPanel();
		panneauChamps.setLayout(new GridLayout(8,1,5,5));
		panneau = new JPanel();
		panneau.setLayout(new BorderLayout());
		verif = new JPanel();
		verif.setLayout(new GridLayout(8,1,5,10));
		lTitre = new JLabel("CREATION DE COMPTE");
		lTitre.setHorizontalAlignment(SwingConstants.CENTER);
		lUtilisateur = new JLabel("Utilisateur");
		lEmail = new JLabel("E-Mail");
		lMotDePasse = new JLabel("Mot de passe");
		lConfirmation = new JLabel("Confirmation de mot de passe");
		iUtilisateur = new JLabel(new ImageIcon(""));
		iEmail = new JLabel(new ImageIcon(""));
		iMotDePasse = new JLabel(new ImageIcon(""));
		iConfirmation = new JLabel(new ImageIcon(""));
		fUtilisateur = new JTextField();
		fEmail = new JTextField();
		fMotDePasse = new JPasswordField();
		fConfirmation = new JPasswordField();
		bCreation = new JButton("Créer");
		this.lQ1 = new JLabel("Quel est le nom de votre meilleur(e) ami(e) ?");
		this.lQ2 = new JLabel("Dans quel ville êtes vous né ?");
		this.lQ3 = new JLabel("Quel est le nom de votre mère ?");
		this.tQ1 = new JTextField();
		this.tQ2 = new JTextField();
		this.tQ3 = new JTextField();
	}

	/**
	 * @return the fUtilisateur
	 */
	public JTextField getfUtilisateur() {
		return fUtilisateur;
	}

	/**
	 * @return the fEmail
	 */
	public JTextField getfEmail() {
		return fEmail;
	}

	/**
	 * @return the fMotDePasse
	 */
	public JPasswordField getfMotDePasse() {
		return fMotDePasse;
	}

	/**
	 * @return the fConfirmation
	 */
	public JPasswordField getfConfirmation() {
		return fConfirmation;
	}

	/**
	 * @return the bCreation
	 */
	public JButton getbCreation() {
		return bCreation;
	}

	/**
	 * @return the iUtilisateur
	 */
	public JLabel getiUtilisateur() {
		return iUtilisateur;
	}

	/**
	 * @return the iEmail
	 */
	public JLabel getiEmail() {
		return iEmail;
	}

	/**
	 * @return the iMotDePasse
	 */
	public JLabel getiMotDePasse() {
		return iMotDePasse;
	}

	/**
	 * @return the iConfirmation
	 */
	public JLabel getiConfirmation() {
		return iConfirmation;
	}


	/**
	 * @return the vUtilisateur
	 */
	public boolean isvUtilisateur() {
		return vUtilisateur;
	}

	/**
	 * @return the vEmail
	 */
	public boolean isvEmail() {
		return vEmail;
	}

	/**
	 * @return the vMotDePasse
	 */
	public boolean isvMotDePasse() {
		return vMotDePasse;
	}

	/**
	 * @return the vConfirmation
	 */
	public boolean isvConfirmation() {
		return vConfirmation;
	}


	/**
	 * @param vUtilisateur the vUtilisateur to set
	 */
	public void setvUtilisateur(boolean vUtilisateur) {
		this.vUtilisateur = vUtilisateur;
	}


	/**
	 * @param vEmail the vEmail to set
	 */
	public void setvEmail(boolean vEmail) {
		this.vEmail = vEmail;
	}


	/**
	 * @param vMotDePasse the vMotDePasse to set
	 */
	public void setvMotDePasse(boolean vMotDePasse) {
		this.vMotDePasse = vMotDePasse;
	}


	/**
	 * @param vConfirmation the vConfirmation to set
	 */
	public void setvConfirmation(boolean vConfirmation) {
		this.vConfirmation = vConfirmation;
	}


	public JTextField gettQ1() {
		return tQ1;
	}


	public JTextField gettQ2() {
		return tQ2;
	}


	public JTextField gettQ3() {
		return tQ3;
	}


	public JLabel getlQ1() {
		return lQ1;
	}


	public JLabel getlQ2() {
		return lQ2;
	}


	public JLabel getlQ3() {
		return lQ3;
	}

	
}
