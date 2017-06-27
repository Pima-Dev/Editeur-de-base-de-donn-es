package vue;

import javax.swing.*;

import controleur.PresserBoutonListener;
import controleur.TouchePresseListener;

import java.awt.*;

/**
 * s'affiche lorsque l'utilisateur lance l'application, elle permet de se connecter à sont compte
 */
public class VueDeConnexion extends JPanel{

	/**
	  * la racine de référence qui permet d'accéder à toutes les vues
	  */
	private Fenetre fenetre;

	/**
	 * affiche l'erreur d'identifiant, il s'affiche grâce au listener
	 */
	private JLabel lErreurIdentifiant;
	/**
	 * affiche le titre
	 */
	private JLabel lConnection;
	/**
	 * affiche le titre du champ de pseudo
	 */
	private JLabel lPseudo;
	/**
	 * affiche le titre du champ de mot de passe
	 */
	private JLabel lMotDePasse;
	/**
	 * entrée du pseudo
	 */
	private JTextField fPseudo;
	/**
	 * entrée du mot de passe
	 */
	private JPasswordField fMotDePasse;
	/**
	 * accéder au panneau principal
	 */
	private JButton bConnection;
	/**
	 * accéder au panneau de création d'utilisateur
	 */
	private JButton bCreationUtilisateur;
	
	//private JButton bMotDePasseOublie;
	/**
	 * contient les panneau bouton et connexion
	 */
	private JPanel panneau;
	/**
	 * contient les boutons connexion et création d'utilisateur
	 */
	private JPanel panneauBouton;
	/**
	 * contient les champ de connexion
	 */
	private JPanel panneauConnexion;
	
	/**
	 * construit le panneau de la fenêtre de connexion
	 * @param fenetre la racine de référence qui permet d'accéder à toutes les vues
	 */
	public VueDeConnexion(Fenetre fenetre){
		this.fenetre = fenetre;
		this.fenetre.setVueDeConnexion(this);
		decoration();
		panneauBouton.add(bConnection);
		panneauBouton.add(bCreationUtilisateur);
		panneau.add(panneauConnexion);
		panneau.add(lPseudo);
		panneau.add(fPseudo);
		panneau.add(lMotDePasse);
		panneau.add(fMotDePasse);
		panneau.add(panneauBouton);
		//panneau.add(bMotDePasseOublie);
		this.add(new JLabel("       "),BorderLayout.WEST);
		this.add(panneau);
		this.add(new JLabel("       "),BorderLayout.EAST);
		this.add(new JLabel("       "),BorderLayout.SOUTH);
		this.bConnection.setName("Connexion");
		this.bConnection.addActionListener(new PresserBoutonListener(this.fenetre));
		this.bCreationUtilisateur.setName("Nouvel utilisateur");
		this.bCreationUtilisateur.addActionListener(new PresserBoutonListener(this.fenetre));
		//this.bMotDePasseOublie.setName("Mot de passe oublie");
		//this.bMotDePasseOublie.addActionListener(new PresserBoutonListener(this.fenetre));
	}
	
	/**
	 * création des éléments du panneau
	 */
	public void decoration(){
		this.setLayout(new BorderLayout());
		panneau = new JPanel();
		panneau.setLayout(new GridLayout(0,1,5,10));
		lErreurIdentifiant = new JLabel("");
		lErreurIdentifiant.setForeground(new Color(255,0,0));
		lErreurIdentifiant.setHorizontalAlignment(SwingConstants.CENTER);
		lConnection = new JLabel("CONNEXION");
		lConnection.setHorizontalAlignment(SwingConstants.CENTER);
		panneauConnexion = new JPanel();
		panneauConnexion.setLayout(new BorderLayout());
		panneauConnexion.add(lErreurIdentifiant, BorderLayout.CENTER);
		panneauConnexion.add(lConnection, BorderLayout.NORTH);
		lPseudo = new JLabel("Utilisateur");
		lPseudo.setHorizontalAlignment(SwingConstants.CENTER);
		lMotDePasse = new JLabel("Mot de passe");
		lMotDePasse.setHorizontalAlignment(SwingConstants.CENTER);
		fPseudo = new JTextField();
		fPseudo.setName("Connexion");
		fPseudo.addKeyListener(new TouchePresseListener(fenetre));
		fMotDePasse = new JPasswordField();
		fMotDePasse.setName("Connexion");
		fMotDePasse.addKeyListener(new TouchePresseListener(fenetre));
		bConnection = new JButton("Connexion");
		bCreationUtilisateur = new JButton("Creation de compte");
		//bMotDePasseOublie = new JButton("<html><u>Mot de passe oublie</u></html>");
		//bMotDePasseOublie.setBorderPainted(false);
		//bMotDePasseOublie.setContentAreaFilled(false);
		//bMotDePasseOublie.setForeground(new Color(142,162,198));
		//bMotDePasseOublie.setHorizontalAlignment(SwingConstants.LEFT);
		panneauBouton = new JPanel();
		panneauBouton.setLayout(new GridLayout(1,2,10,5));
	}
	
	

	/**
	 * accès au JTextField fPseudo
	 * @return le JTextField fPseudo
	 */
	public JTextField getfPseudo() {
		return fPseudo;
	}

	/**
	 * accès au JPasswordField fMotDePasse
	 * @return le JPasswordField fMotDePasse
	 */
	public JPasswordField getfMotDePasse() {
		return fMotDePasse;
	}

	/**
	 * accès au JLabel lConnection
	 * @return le JLabel lConnection
	 */
	public JLabel getlConnection() {
		return lConnection;
	}

	/**
	 * accès au JLabel lErreurIdentifiant
	 * @return le JLabel lErreurIdentifiant
	 */
	public JLabel getlErreurIdentifiant() {
		return lErreurIdentifiant;
	}
	
}
