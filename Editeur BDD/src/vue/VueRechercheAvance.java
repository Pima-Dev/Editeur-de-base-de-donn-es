package vue;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

import controleur.ChampsListener;
import controleur.TouchePresseListener;

public class VueRechercheAvance extends JPanel{

	private JLabel lTitre;
	private JTextField fRecherche;
	private JCheckBox sensibleALaCasse;
	private JCheckBox motComplet;
	private JCheckBox selectionnerLigne;
	private JSpinner ligneMin;
	private JSpinner ligneMax;
	private Fenetre fenetre;
	private JPanel panneauPrincipal;
	private JPanel panneauCasseMotComplet;
	private JPanel panneauSelectionnerLigne;
	private JSpinner.NumberEditor ligneMinEditeur;
	private JSpinner.NumberEditor ligneMaxEditeur;
	
	public VueRechercheAvance(Fenetre fenetre) {
		this.fenetre = fenetre;
		declaration();
		ligneMin.setEditor(ligneMinEditeur);
		ligneMax.setEditor(ligneMaxEditeur);
		ligneMinEditeur.getModel().setMaximum((int)ligneMaxEditeur.getModel().getNumber());
		ligneMinEditeur.getModel().setMinimum(0);
		ligneMaxEditeur.getModel().setMaximum(100);
		ligneMaxEditeur.getModel().setMinimum((int)ligneMinEditeur.getModel().getNumber());
		lTitre.setHorizontalAlignment(SwingConstants.CENTER);
		panneauCasseMotComplet.setLayout(new GridLayout(1,2));
		panneauCasseMotComplet.add(sensibleALaCasse);
		panneauCasseMotComplet.add(motComplet);
		panneauSelectionnerLigne.setLayout(new GridLayout(1,3));
		panneauSelectionnerLigne.add(selectionnerLigne);
		panneauSelectionnerLigne.add(ligneMin);
		panneauSelectionnerLigne.add(ligneMax);
		panneauPrincipal.setLayout(new GridLayout(0,1));
		panneauPrincipal.add(lTitre);
		panneauPrincipal.add(fRecherche);
		panneauPrincipal.add(panneauCasseMotComplet);
		panneauPrincipal.add(panneauSelectionnerLigne);
		fRecherche.setName("BarreRecherche");
		//fRecherche.addFocusListener(new ChampsListener(this.fenetre));
		//fRecherche.addKeyListener(new TouchePresseListener(this.fenetre));
		this.setLayout(new BorderLayout());
		this.add(new JLabel("      "),BorderLayout.SOUTH);
		this.add(new JLabel("      "),BorderLayout.WEST);
		this.add(new JLabel("      "),BorderLayout.EAST);
		this.add(panneauPrincipal,BorderLayout.CENTER);
	}

	private void declaration(){
		lTitre = new JLabel("Recherche Avancée");
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
	}
	
	public static void main(String[] args){
		JFrame fenetre = new JFrame("Connexion");
		fenetre.setVisible(true);
		fenetre.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		fenetre.setContentPane(new VueRechercheAvance(new Fenetre()));
		fenetre.setLocation(500, 200);
		fenetre.pack();
	}
}
