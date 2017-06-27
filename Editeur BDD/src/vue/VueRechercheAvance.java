package vue;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

import controleur.ChampsListener;
import controleur.FenetreListener;
import controleur.PresserBoutonListener;
import controleur.ValeurListener;

/**
 * s'affiche dans une nouvelle fenêtre lorsque l'utilisateur appuye sur le bouton recherche avancée ou l'option recherche du menu
 */
public class VueRechercheAvance extends JPanel{

	/**
	 * affiche le titre
	 */
	private JLabel lTitre;
	/**
	 * affiche le titre de l'indice minimum
	 */
	private JLabel lMin;
	/**
	 * affiche le titre de l'indice maximum
	 */
	private JLabel lMax;
	/**
	 * valide la recherche
	 */
	private JButton bValider;
	/**
	 * entrée de l'élément à rechercher dans la table
	 */
	private JTextField fRecherche;
	/**
	 * choix de l'option sensible à la casse qui prend en compte les majuscules et minuscules
	 */
	private JCheckBox sensibleALaCasse;
	/**
	 * choix de l'option mot complet
	 */
	private JCheckBox motComplet;
	/**
	 * choix de l'option selectionner ligne
	 */
	private JCheckBox selectionnerLigne;
	/**
	 * choix de la plus petite ligne à partir de laquelle faire la recherche
	 */
	private JSpinner ligneMin;
	/**
	 * choix de la plus grande ligne jusqu'à laquelle faire la recherche
	 */
	private JSpinner ligneMax;
	/**
	 * la racine de référence qui permet d'accéder à toutes les vues
	 */
	private Fenetre fenetre;
	/**
	 * contient les éléments de la fenêtre
	 */
	private JPanel panneauPrincipal;
	/**
	 * contient les check box sensible à la casse et mot complet
	 */
	private JPanel panneauCasseMotComplet;
	/**
	 * contient les option de recherche sur des lignes définies
	 */
	private JPanel panneauSelectionnerLigne;
	/**
	 * contient le champ de recherche et le titre
	 */
	private JPanel panneauRecherche;
	/**
	 * éditeur du choix de ligne minimum
	 */
	private JSpinner.NumberEditor ligneMinEditeur;
	/**
	 * éditeur du choix de ligne maximum
	 */
	private JSpinner.NumberEditor ligneMaxEditeur;
	/**
	 * fenêtre affichant les éléments graphiques
	 */
	private JFrame fenetreRechercheAvance;
	
	/**
	 * construit le panneau
	 * @param fenetre la racine de référence qui permet d'accéder à toutes les vues
	 */
	public VueRechercheAvance(Fenetre fenetre) {
		this.fenetre = fenetre;
		fenetre.setVueRechercheAvance(this);
		declaration();
		bValider.setName("LancerRechercheAvance");
		bValider.addActionListener(new PresserBoutonListener(fenetre));
		ligneMin.setEnabled(false);
		ligneMax.setEnabled(false);
		selectionnerLigne.setName("selectionnerLigne");
		selectionnerLigne.addActionListener(new PresserBoutonListener(this.fenetre));
		ligneMin.setEditor(ligneMinEditeur);
		ligneMin.addChangeListener(new ValeurListener(fenetre));
		ligneMax.setEditor(ligneMaxEditeur);
		ligneMax.addChangeListener(new ValeurListener(fenetre));
		ligneMinEditeur.getModel().setMinimum(0);
		ligneMaxEditeur.getModel().setMaximum(fenetre.getVuePrincipale().getTable().getRowCount());
		lTitre.setHorizontalAlignment(SwingConstants.CENTER);
		panneauCasseMotComplet.setLayout(new GridLayout(1,2));
		panneauCasseMotComplet.add(sensibleALaCasse);
		panneauCasseMotComplet.add(motComplet);
		panneauSelectionnerLigne.setLayout(new GridLayout(3,2));
		panneauSelectionnerLigne.add(new JLabel(""));
		panneauSelectionnerLigne.add(selectionnerLigne);
		panneauSelectionnerLigne.add(lMin);
		panneauSelectionnerLigne.add(ligneMin);
		panneauSelectionnerLigne.add(lMax);
		panneauSelectionnerLigne.add(ligneMax);
		panneauRecherche.setLayout(new BorderLayout());
		panneauRecherche.add(fRecherche, BorderLayout.NORTH);
		panneauRecherche.add(panneauCasseMotComplet, BorderLayout.CENTER);
		panneauRecherche.add(panneauSelectionnerLigne, BorderLayout.SOUTH);
		panneauPrincipal.setLayout(new BorderLayout(10,10));
		panneauPrincipal.add(lTitre,BorderLayout.NORTH);
		panneauPrincipal.add(panneauRecherche,BorderLayout.CENTER);
		panneauPrincipal.add(bValider,BorderLayout.SOUTH);
		fRecherche.setName("BarreRecherche");
		fRecherche.addFocusListener(new ChampsListener(this.fenetre));
		this.setLayout(new BorderLayout());
		this.add(new JLabel("      "),BorderLayout.SOUTH);
		this.add(new JLabel("      "),BorderLayout.WEST);
		this.add(new JLabel("      "),BorderLayout.EAST);
		this.add(panneauPrincipal,BorderLayout.CENTER);
		fenetreRechercheAvance = new JFrame("Créer une nouvelle base de données");
		fenetreRechercheAvance.setContentPane(this);
		fenetreRechercheAvance.setSize(new Dimension(400, 250));
		fenetreRechercheAvance.setLocationRelativeTo(null);
		fenetreRechercheAvance.setResizable(false);
		this.fenetreRechercheAvance.addWindowListener(new FenetreListener(this.fenetre));
		this.fenetre.getFenetre().setEnabled(false);
		fenetreRechercheAvance.setVisible(true);
	}

	/**
	 * création des éléments
	 */
	private void declaration(){
		lTitre = new JLabel("Recherche Avancée");
		lMin = new JLabel("Indice minimum");
		lMax = new JLabel("Indice maximum");
		bValider = new JButton("Valider recherche");
		fRecherche = new JTextField("Chercher les occurences");
		sensibleALaCasse = new JCheckBox("Sensible à la casse");
		motComplet = new JCheckBox("Mot complet");
		selectionnerLigne = new JCheckBox("Selectionner des lignes");
		ligneMin = new JSpinner(new SpinnerNumberModel());
		ligneMax = new JSpinner(new SpinnerNumberModel());
		ligneMinEditeur = new JSpinner.NumberEditor(ligneMin);
		ligneMaxEditeur = new JSpinner.NumberEditor(ligneMax);
		panneauPrincipal = new JPanel();
		panneauCasseMotComplet = new JPanel();
		panneauSelectionnerLigne = new JPanel();
		panneauRecherche = new JPanel();
	}

	/**
	 * accès au JSpinner.NumberEditor ligneMinEditeur
	 * @return le JSpinner.NumberEditor ligneMinEditeur
	 */
	public JSpinner.NumberEditor getLigneMinEditeur() {
		return ligneMinEditeur;
	}

	/**
	 * accès au JSpinner.NumberEditor ligneMaxEditeur
	 * @return le JSpinner.NumberEditor ligneMaxEditeur
	 */
	public JSpinner.NumberEditor getLigneMaxEditeur() {
		return ligneMaxEditeur;
	}

	/**
	 * accès au JSpinner ligneMin
	 * @return le JSpinner ligneMin
	 */
	public JSpinner getLigneMin() {
		return ligneMin;
	}

	/**
	 * accès au JSpinner ligneMax
	 * @return le JSpinner ligneMax
	 */
	public JSpinner getLigneMax() {
		return ligneMax;
	}

	/**
	 * accès au JCheckBox sensibleALaCasse
	 * @return le JCheckBox sensibleALaCasse
	 */
	public JCheckBox getSensibleALaCasse() {
		return sensibleALaCasse;
	}

	/**
	 * accès au JCheckBox motComplet
	 * @return le JCheckBox motComplet
	 */
	public JCheckBox getMotComplet() {
		return motComplet;
	}

	/**
	 * accès au JCheckBox selectionnerLigne
	 * @return le JCheckBox selectionnerLigne
	 */
	public JCheckBox getSelectionnerLigne() {
		return selectionnerLigne;
	}

	/**
	 * accès au JTextField fRecherche
	 * @return le JTextField fRecherche
	 */
	public JTextField getfRecherche() {
		return fRecherche;
	}

	/**
	 * accès au JFrame fenetreRechercheAvance
	 * @return le JFrame fenetreRechercheAvance
	 */
	public JFrame getFenetreRechercheAvance() {
		return fenetreRechercheAvance;
	}
}
