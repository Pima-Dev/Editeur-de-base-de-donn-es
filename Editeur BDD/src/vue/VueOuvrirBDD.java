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

import controleur.PresserBoutonListener;

public class VueOuvrirBDD extends JPanel {

	private JLabel lTitre;
	private JLabel lErreur;
	private JLabel lNomBDD;
	private JLabel lNonSave;
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
	private JFrame fenetreOuvrirBDD;
	private JComboBox<String> listeBDD;
	private JRadioButton boutonLocal;
	private JRadioButton boutonServeur;
	private JLabel lUrl;
	private JLabel lNom;
	private JTextField tNom;

	public VueOuvrirBDD(Fenetre fenetre) {
		this.fenetre = fenetre;
		this.fenetre.setVueOuvrirBDD(this);
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
		fenetreOuvrirBDD.setVisible(true);
	}

	public void decoration() {
		lTitre = new JLabel("OUVRIR UNE BDD SAUVEGARDE");
		lTitre.setHorizontalAlignment(SwingConstants.CENTER);
		lErreur = new JLabel("");
		lErreur.setForeground(new Color(255, 0, 0));
		lErreur.setHorizontalAlignment(SwingConstants.CENTER);
		lNomBDD = new JLabel("Liste BDD sauvegardés");
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
	 * @return the lErreur
	 */
	public JLabel getlErreur() {
		return lErreur;
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

	public JTextField getfPort() {
		return this.port;
	}

	public JFrame getFrame() {
		return this.fenetreOuvrirBDD;
	}

	public JLabel getlNomBDD() {
		return lNomBDD;
	}

	public JLabel getlNomUtilisateur() {
		return lNomUtilisateur;
	}

	public JLabel getlMotDePasse() {
		return lMotDePasse;
	}

	public JButton getValider() {
		return valider;
	}

	public Fenetre getFenetre() {
		return fenetre;
	}

	public JTextField getPort() {
		return port;
	}

	public JFrame getFenetreNouvelleBDD() {
		return fenetreOuvrirBDD;
	}

	public JComboBox<String> getListeBDD() {
		return listeBDD;
	}

	public JRadioButton getBoutonLocal() {
		return boutonLocal;
	}

	public JRadioButton getBoutonServeur() {
		return boutonServeur;
	}

	public JTextField gettNom() {
		return tNom;
	}
}