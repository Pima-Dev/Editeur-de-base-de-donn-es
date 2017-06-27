package vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
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
 * s'affiche dans une nouvelle fenêtre lorsque l'utilisateur clic sur l'option ouvrir du menu
 */
public class VueOuvrirBDD extends JPanel {

	/**
	 * affiche le titre
	 */
	private JLabel lTitre;
	/**
	 * affiche une erreur, il s'affiche grâce au listener
	 */
	private JLabel lErreur;
	/**
	 * affiche le titre du champ d'entrée de nom de BDD
	 */
	private JLabel lNomBDD;
	/**
	 * élément initial dans la JCombobox liste BDD
	 */
	private JLabel lNonSave;
	/**
	 * champ d'entrée de l'URL de la BDD
	 */
	private JTextField fURL;
	/**
	 * affiche le titre du champ d'entrée de nom d'utilisateur de la BDD
	 */
	private JLabel lNomUtilisateur;
	/**
	 * entrée de nom d'utilisateur de la BDD
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
	 * valide ou invalide l'ouverture de la BDD
	 */
	private JButton valider;
	/**
	 * contient les boutons de choix entre serveur distant et serveur local
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
	private JFrame fenetreOuvrirBDD;
	/**
	 * liste des BDD enregistrées dans le système
	 */
	private JComboBox<String> listeBDD;
	/**
	 * choix de l'option de chargement de la BDD, création sur un serveur local
	 */
	private JRadioButton boutonLocal;
	/**
	 * choix de l'option de chargement de la BDD, création sur un serveur distant
	 */
	private JRadioButton boutonServeur;
	/**
	 * affiche le titre du champ d'entrée de l'URL de la BDD
	 */
	private JLabel lUrl;
	/**
	 * affiche le titre du champ d'entrée du nom de la BDD
	 */
	private JLabel lNom;
	/**
	 * entrée du nom de la BDD
	 */
	private JTextField tNom;

	/**
	 * constructeur du panneau
	 * @param fenetre la racine de référence qui permet d'accéder à toutes les vues
	 */
	public VueOuvrirBDD(Fenetre fenetre) {
		this.fenetre = fenetre;
		this.fenetre.setVueOuvrirBDD(this);
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				creer();
			}
		});
	}

	/**
	 * construction du panneau
	 */
	public void creer(){
		decoration();
		panneauPrincipal.setLayout(new GridLayout(0, 1));
		panneauBoutons.setLayout(new GridLayout(1, 2));
		panneauBoutons.add(boutonLocal);
		panneauBoutons.add(boutonServeur);
		this.setLayout(new BorderLayout());
		panneauPrincipal.add(lTitre);
		panneauPrincipal.add(this.lErreur);
		panneauPrincipal.add(lNomBDD);
		panneauPrincipal.add(this.listeBDD);
		panneauPrincipal.add(lNonSave);
		panneauPrincipal.add(panneauBoutons);
		panneauPrincipal.add(this.lNom);
		panneauPrincipal.add(this.tNom);
		panneauPrincipal.add(this.lUrl);
		panneauPrincipal.add(fURL);
		panneauPrincipal.add(lNomUtilisateur);
		panneauPrincipal.add(fNomUtilisateur);
		panneauPrincipal.add(lMotDePasse);
		panneauPrincipal.add(fMotDePasse);
		panneauPrincipal.add(this.lPort);
		panneauPrincipal.add(this.port);
		panneauPrincipal.add(valider);
		this.add(new JLabel("      "), BorderLayout.SOUTH);
		this.add(new JLabel("      "), BorderLayout.WEST);
		this.add(new JLabel("      "), BorderLayout.EAST);
		this.add(panneauPrincipal, BorderLayout.CENTER);
		fenetreOuvrirBDD = new JFrame("Ouvrir une base de données");
		fenetreOuvrirBDD.setContentPane(this);
		fenetreOuvrirBDD.setSize(new Dimension(400, 500));
		fenetreOuvrirBDD.setResizable(false);
		fenetreOuvrirBDD.setLocationRelativeTo(null);
		this.fenetreOuvrirBDD.addWindowListener(new FenetreListener(this.fenetre));
		this.fenetre.getFenetre().setEnabled(false);
		fenetreOuvrirBDD.setVisible(true);
	}
	
	/**
	 * création des éléments du panneau
	 */
	public void decoration() {
		lTitre = new JLabel("OUVRIR UNE BDD SAUVEGARDE");
		lTitre.setHorizontalAlignment(SwingConstants.CENTER);
		lErreur = new JLabel("");
		lErreur.setForeground(new Color(255, 0, 0));
		lErreur.setHorizontalAlignment(SwingConstants.CENTER);
		lNomBDD = new JLabel("Liste BDD sauvegardées");
		lNonSave = new JLabel("OUVRIR UNE BDD NON SAUVEGARDE");
		lNonSave.setHorizontalAlignment(SwingConstants.CENTER);
		boutonLocal = new JRadioButton("Hebergement Local");
		boutonLocal.setName("ouvir bdd hebergement local");
		boutonLocal.addActionListener(new PresserBoutonListener(this.fenetre));
		boutonServeur = new JRadioButton("Hebergement Distant");
		boutonServeur.setName("ouvrir bdd hebergement distant");
		boutonServeur.addActionListener(new PresserBoutonListener(this.fenetre));
		fURL = new JTextField();
		lNomUtilisateur = new JLabel("Nom de l'utilisateur");
		fNomUtilisateur = new JTextField();
		lMotDePasse = new JLabel("Mot de passe");
		fMotDePasse = new JPasswordField();
		valider = new JButton("Valider");
		valider.setName("valider ouverture bdd");
		valider.addActionListener(new PresserBoutonListener(this.fenetre));
		panneauBoutons = new JPanel();
		panneauPrincipal = new JPanel();
		this.port = new JTextField();
		this.lPort = new JLabel("Port du serveur distant");
		this.listeBDD = new JComboBox<String>();
		this.listeBDD.addItem("");
		this.listeBDD.setName("ouvrir bdd liste bdd");
		this.listeBDD.addActionListener(new PresserBoutonListener(this.fenetre));
		for (String s : this.fenetre.getSession().getListeBDD()) {
			this.listeBDD.addItem(s);
		}
		this.lUrl = new JLabel("URL du serveur distant");
		this.lNom = new JLabel("Nom de la base de données");
		this.tNom = new JTextField();
	}

	/**
	 * accès au JLabel lErreur
	 * @return le JLabel lErreur
	 */
	public JLabel getlErreur() {
		return lErreur;
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
	public JTextField getfPort() {
		return this.port;
	}

	/**
	 * accès au JFrame fenetreOuvrirBDD
	 * @return le JFrame fenetreOuvrirBDD
	 */
	public JFrame getFrame() {
		return this.fenetreOuvrirBDD;
	}

	/**
	 * accès au JLabel lNomBDD
	 * @return le JLabel lNomBDD
	 */
	public JLabel getlNomBDD() {
		return lNomBDD;
	}

	/**
	 * accès au JLabel lNomUtilisateur
	 * @return le JLabel lNomUtilisateur
	 */
	public JLabel getlNomUtilisateur() {
		return lNomUtilisateur;
	}

	/**
	 * accès au JLabel lMotDePasse
	 * @return le JLabel lMotDePasse
	 */
	public JLabel getlMotDePasse() {
		return lMotDePasse;
	}

	/**
	 * accès au JButton valider
	 * @return le JButton valider
	 */
	public JButton getValider() {
		return valider;
	}

	/**
	 * accès au JTextField port
	 * @return le JTextField port
	 */
	public JTextField getPort() {
		return port;
	}

	/**
	 * accès au JFrame fenetreOuvrirBDD
	 * @return le JFrame fenetreOuvrirBDD
	 */
	public JFrame getFenetreNouvelleBDD() {
		return fenetreOuvrirBDD;
	}

	/**
	 * accès au JComboBox<String> listeBDD
	 * @return le JComboBox<String> listeBDD
	 */
	public JComboBox<String> getListeBDD() {
		return listeBDD;
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
	 * accès au JTextField tNom
	 * @return le JTextField tNom
	 */
	public JTextField gettNom() {
		return tNom;
	}
}