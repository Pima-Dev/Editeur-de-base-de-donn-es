package vue;

import javax.swing.*;

import controleur.connexion.ConnexionBoutonDeConnexion;
import controleur.connexion.ConnexionBoutonDeCreationDeCompte;
import controleur.connexion.ConnexionBoutonMotDePasseOublie;

import java.awt.*;

public class VueDeConnexion extends JPanel{
	
	private Fenetre fenetre;

	private JLabel lErreurIdentifiant;
	private JLabel lConnection;
	private JLabel lPseudo;
	private JLabel lMotDePasse;
	private JTextField fPseudo;
	private JPasswordField fMotDePasse;
	private JButton bConnection;
	private JButton bCreationUtilisateur;
	private JButton lMotDePasseOublie;
	private JPanel panneau;
	private JPanel panneauBouton;
	private JPanel panneauConnexion;
	
	public VueDeConnexion(Fenetre fenetre){
		this.fenetre = fenetre;
		decoration();
		panneauBouton.add(bConnection);
		panneauBouton.add(bCreationUtilisateur);
		panneau.add(panneauConnexion);
		panneau.add(lPseudo);
		panneau.add(fPseudo);
		panneau.add(lMotDePasse);
		panneau.add(fMotDePasse);
		panneau.add(panneauBouton);
		panneau.add(lMotDePasseOublie);
		this.add(new JLabel("       "),BorderLayout.WEST);
		this.add(panneau);
		this.add(new JLabel("       "),BorderLayout.EAST);
		this.bConnection.addActionListener(new ConnexionBoutonDeConnexion(this,fenetre));
		this.bCreationUtilisateur.addActionListener(new ConnexionBoutonDeCreationDeCompte(this,fenetre));
		this.lMotDePasseOublie.addActionListener(new ConnexionBoutonMotDePasseOublie(this,fenetre));
	}
	
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
		fMotDePasse = new JPasswordField();
		bConnection = new JButton("Connexion");
		bCreationUtilisateur = new JButton("Creation de compte");
		lMotDePasseOublie = new JButton("<html><u>Mot de passe oublie</u></html>");
		lMotDePasseOublie.setBorderPainted(false);
		lMotDePasseOublie.setContentAreaFilled(false);
		lMotDePasseOublie.setForeground(new Color(142,162,198));
		lMotDePasseOublie.setHorizontalAlignment(SwingConstants.LEFT);
		panneauBouton = new JPanel();
		panneauBouton.setLayout(new GridLayout(1,2,10,5));
		
	}
	
	

	/**
	 * @return the fPseudo
	 */
	public JTextField getfPseudo() {
		return fPseudo;
	}

	/**
	 * @return the fMotDePasse
	 */
	public JPasswordField getfMotDePasse() {
		return fMotDePasse;
	}

	/**
	 * @return the lConnection
	 */
	public JLabel getlConnection() {
		return lConnection;
	}

	/**
	 * @return the lErreurIdentifiant
	 */
	public JLabel getlErreurIdentifiant() {
		return lErreurIdentifiant;
	}
	
}
