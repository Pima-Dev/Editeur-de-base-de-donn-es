package vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import controleur.FenetreListener;
import controleur.PresserBoutonListener;
/**
 * s'affiche lorsque l'utilisateur appuye sur l'option nouveau du menu fichier, elle permet de créer une nouvelle table
 */
public class VueCreationBDD extends JPanel{

	/**
	 * affiche le titre
	 */
	private JLabel lTitre;
	/**
	 * affiche l'erreur de création, il s'affiche grâce au listener
	 */
	private JLabel lErreur;
	/**
	 * affiche le titre du champ d'entrée du nom de la BDD
	 */
	private JLabel lNomBDD;
	/**
	 * entrée du nom de la BDD
	 */
	private JTextField fNomBDD;
	/**
	 * affiche le titre du champ d'entrée du code URL de la BDD
	 */
	private JLabel lCodeURL;
	/**
	 * entrée de l'URL de la BDD
	 */
	private JTextField fURL;
	/**
	 * choix de l'option de création de la BDD, création sur un serveur local
	 */
	private JRadioButton boutonLocal;
	/**
	 * choix de l'option de création de la BDD, création sur un serveur distant
	 */
	private JRadioButton boutonServeur;
	/**
	 * affiche le titre du champ d'entrée du pseudo utilisateur de la BDD
	 */
	private JLabel lNomUtilisateur;
	/**
	 * entrée du pseudo utilisateur de la BDD
	 */
	private JTextField fNomUtilisateur;
	/**
	 * affiche le titre du champ d'entrée du mot de passe de la BDD
	 */
	private JLabel lMotDePasse;
	/**
	 * entrée du mot de passe de la BDD
	 */
	private JPasswordField fMotDePasse;
	/**
	 * valider la création de la BDD
	 */
	private JButton valider;
	/**
	 * contient les radio-boutons 
	 */
	private JPanel panneauBoutons;
	/**
	 * contient les éléments de la fenêtre
	 */
	private JPanel panneauPrincipal;
	/**
	 * la racine de référence qui permet d'accéder à toutes les vues
	 */
	private Fenetre fenetre;
	/**
	 * entrée du port de la BDD
	 */
	private JTextField port;
	/**
	 * affiche le titre du champ d'entrée du port de la BDD
	 */
	private JLabel lPort;
	/**
	 * fenêtre affichant les éléments graphiques
	 */
	private JFrame fenetreNouvelleBDD;
	
	/**
	 * construit le panneau grâce aux méthodes creer() et decoration()
	 * @param fenetre la racine de référence qui permet d'accéder à toutes les vues
	 */
	public VueCreationBDD(Fenetre fenetre) {
		this.fenetre = fenetre;
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				creer();
			}
		});
	}
	
	/**
	 * création des éléments du panneau
	 */
	public void decoration(){
		lTitre = new JLabel("CREATION D'UNE BDD");
		lTitre.setHorizontalAlignment(SwingConstants.CENTER);
		lErreur = new JLabel("");
		lErreur.setForeground(new Color(255,0,0));
		lErreur.setHorizontalAlignment(SwingConstants.CENTER);
		lNomBDD = new JLabel("Nom de la BDD");
		fNomBDD = new JTextField();
		lCodeURL = new JLabel("URL du serveur distant");
		boutonLocal = new JRadioButton("Hebergement Local");
		boutonLocal.setName("creer bdd hebergement local");
		boutonLocal.addActionListener(new PresserBoutonListener(this.fenetre));
		boutonServeur = new JRadioButton("Hebergement Distant");
		boutonServeur.setName("creer bdd hebergement distant");
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
	 * construction du panneau
	 */
	public void creer(){
		this.fenetre.setVueCreationBDD(this);
		decoration();
		panneauPrincipal.setLayout(new GridLayout(0,1));
		panneauBoutons.setLayout(new GridLayout(1,2));
		this.setLayout(new BorderLayout());
		panneauPrincipal.add(lTitre);
		panneauPrincipal.add(lNomBDD);
		panneauPrincipal.add(fNomBDD);
		panneauBoutons.add(boutonLocal);
		panneauBoutons.add(boutonServeur);
		panneauPrincipal.add(panneauBoutons);
		panneauPrincipal.add(lCodeURL);
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
		fenetreNouvelleBDD = new JFrame("Créer une nouvelle base de données");
		fenetreNouvelleBDD.setContentPane(this);
		fenetreNouvelleBDD.setSize(new Dimension(400, 500));
		fenetreNouvelleBDD.setResizable(false);
		fenetreNouvelleBDD.setLocationRelativeTo(null);
		this.fenetreNouvelleBDD.addWindowListener(new FenetreListener(this.fenetre));
		this.fenetre.getFenetre().setEnabled(false);
		fenetreNouvelleBDD.setVisible(true);
	}
	
	/**
	 * accès au JLabel lErreur
	 * @return le JLabel lErreur
	 */
	public JLabel getlErreur() {
		return lErreur;
	}

	/**
	 * accès au JTextField fNomBDD
	 * @return le JTextField fNomBDD
	 */
	public JTextField getfNomBDD() {
		return fNomBDD;
	}

	/**
	 * accès au JRadioButton boutonLocal
	 * @return le JRadioButton boutonLocal
	 */
	public JRadioButton getBoutonLocal() {
		return boutonLocal;
	}

	/**
	 * accès au JRadioButton boutonServeur
	 * @return le JRadioButton boutonServeur
	 */
	public JRadioButton getBoutonServeur() {
		return boutonServeur;
	}

	/**
	 * accès au JTextField fURL
	 * @return le JTextField fURL
	 */
	public JTextField getfURL() {
		return fURL;
	}

	/**
	 * accès au JTextField fNomUtilisateur
	 * @return le JTextField fNomUtilisateur
	 */
	public JTextField getfNomUtilisateur() {
		return fNomUtilisateur;
	}

	/**
	 * accès au JPasswordField fMotDePasse
	 * @return le JPasswordField fMotDePasse
	 */
	public JPasswordField getfMotDePasse() {
		return fMotDePasse;
	}
	
	/**
	 * accès au JTextField port
	 * @return le JTextField port
	 */
	public JTextField getfPort(){
		return port;
	}
	
	/**
	 * accès au JFrame fenetreNouvelleBDD
	 * @return le JFrame fenetreNouvelleBDD
	 */
	public JFrame getFrame(){
		return fenetreNouvelleBDD;
	}
	
}