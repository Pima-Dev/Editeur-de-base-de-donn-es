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
		
		this.fUtilisateur.addFocusListener(new ChampsListener(this.fenetre));
		this.fEmail.addFocusListener(new ChampsListener(this.fenetre));
		this.fMotDePasse.addFocusListener(new ChampsListener(this.fenetre));
		this.fConfirmation.addFocusListener(new ChampsListener(this.fenetre));
		this.bCreation.setName("Valider creation nouvel utilisateur");
		this.bCreation.addActionListener(new PresserBoutonListener(this.fenetre));
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
		iUtilisateur = new JLabel(new ImageIcon(""));
		iEmail = new JLabel(new ImageIcon(""));
		iMotDePasse = new JLabel(new ImageIcon(""));
		iConfirmation = new JLabel(new ImageIcon(""));
		fUtilisateur = new JTextField();
		fEmail = new JTextField();
		fMotDePasse = new JPasswordField();
		fConfirmation = new JPasswordField();
		bCreation = new JButton("Créer");
		
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

	
}
