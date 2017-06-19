package vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import controleur.PresserBoutonListener;

public class VueCreationBDD extends JPanel{

	private JLabel lTitre;
	private JLabel lErreur;
	private JLabel lNomBDD;
	private JTextField fNomBDD;
	private JLabel lCodeURL;
	private JRadioButton boutonLocal;
	private JRadioButton boutonServeur;
	private JTextField fURL;
	private JLabel lNomUtilisateur;
	private JTextField fNomUtilisateur;
	private JLabel lMotDePasse;
	private JPasswordField fMotDePasse;
	private JButton valider;
	private JPanel panneauBoutons;
	private JPanel panneauPrincipal;
	private Fenetre fenetre;
	private JTextField port;
	private JLabel lPort;
	private JFrame fenetreNouvelleBDD;
	
	public VueCreationBDD(Fenetre fenetre) {
		this.fenetre = fenetre;
		this.fenetre.setVueCreationBDD(this);
		decoration();
		panneauPrincipal.setLayout(new GridLayout(0,1));
		panneauBoutons.setLayout(new GridLayout(1,2));
		this.setLayout(new BorderLayout());
		panneauPrincipal.add(lTitre);
		panneauPrincipal.add(lNomBDD);
		panneauPrincipal.add(fNomBDD);
		panneauPrincipal.add(lCodeURL);
		panneauBoutons.add(boutonLocal);
		panneauBoutons.add(boutonServeur);
		panneauPrincipal.add(panneauBoutons);
		panneauPrincipal.add(fURL);
		panneauPrincipal.add(lNomUtilisateur);
		panneauPrincipal.add(fNomUtilisateur);
		panneauPrincipal.add(lMotDePasse);
		panneauPrincipal.add(fMotDePasse);
		panneauPrincipal.add(this.lPort);
		panneauPrincipal.add(this.port);
		panneauPrincipal.add(valider);
		this.add(new JLabel("      "),BorderLayout.SOUTH);
		this.add(new JLabel("      "),BorderLayout.WEST);
		this.add(new JLabel("      "),BorderLayout.EAST);
		this.add(panneauPrincipal,BorderLayout.CENTER);
		fenetreNouvelleBDD = new JFrame("Connexion");
		fenetreNouvelleBDD.setContentPane(this);
		fenetreNouvelleBDD.pack();
		fenetreNouvelleBDD.setLocationRelativeTo(null);
		fenetreNouvelleBDD.setVisible(true);
	}
	
	public void decoration(){
		lTitre = new JLabel("CREATION D'UNE BDD");
		lTitre.setHorizontalAlignment(SwingConstants.CENTER);
		lErreur = new JLabel("");
		lErreur.setForeground(new Color(255,0,0));
		lErreur.setHorizontalAlignment(SwingConstants.CENTER);
		lNomBDD = new JLabel("Nom de la BDD");
		fNomBDD = new JTextField();
		lCodeURL = new JLabel("Serveur");
		boutonLocal = new JRadioButton("Hebergement Local");
		boutonLocal.setName("hebergement local");
		boutonLocal.addActionListener(new PresserBoutonListener(this.fenetre));
		boutonServeur = new JRadioButton("Hebergement Distant");
		boutonServeur.setName("hebergement distant");
		boutonServeur.addActionListener(new PresserBoutonListener(this.fenetre));
		fURL = new JTextField();
		lNomUtilisateur = new JLabel("Nom de l'utilisateur");
		fNomUtilisateur = new JTextField();
		lMotDePasse = new JLabel("Mot de passe");
		fMotDePasse = new JPasswordField();
		valider = new JButton("Valider");
		valider.setName("valider creation bdd");
		valider.addActionListener(new PresserBoutonListener(this.fenetre));
		panneauBoutons = new JPanel();
		panneauPrincipal = new JPanel();
		this.port = new JTextField();
		this.lPort = new JLabel("Port du serveur distant");
	}

	/**
	 * @return the lErreur
	 */
	public JLabel getlErreur() {
		return lErreur;
	}

	/**
	 * @return the fNomBDD
	 */
	public JTextField getfNomBDD() {
		return fNomBDD;
	}

	/**
	 * @return the boutonLocal
	 */
	public JRadioButton getBoutonLocal() {
		return boutonLocal;
	}

	/**
	 * @return the boutonServeur
	 */
	public JRadioButton getBoutonServeur() {
		return boutonServeur;
	}

	/**
	 * @return the fURL
	 */
	public JTextField getfURL() {
		return fURL;
	}

	/**
	 * @return the fNomUtilisateur
	 */
	public JTextField getfNomUtilisateur() {
		return fNomUtilisateur;
	}

	/**
	 * @return the fMotDePasse
	 */
	public JPasswordField getfMotDePasse() {
		return fMotDePasse;
	}
	
	public JTextField getfPort(){
		return this.port;
	}
	
	public JFrame getFrame(){
		return this.fenetreNouvelleBDD;
	}
	
}