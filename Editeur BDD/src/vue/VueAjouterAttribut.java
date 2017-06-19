package vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class VueAjouterAttribut extends JPanel{
	
	private Fenetre fenetre;
	private JPanel panneauPrincipal;
	private JCheckBox primaryKey;
	private JCheckBox notNull;
	private JCheckBox unique;
	private JCheckBox referencesKey;
	private JLabel lTitre;
	private JLabel lErreur;
	private JLabel lContrainte;
	private JLabel lReference;
	private JComboBox<String> reference;
	private JFrame frame;
	private JButton valider;
	
	public VueAjouterAttribut(Fenetre fenetre){
		this.fenetre = fenetre;
		//this.fenetre.setVueAjouterAttribut(this);
		this.panneauPrincipal = new JPanel();
		this.setLayout(new BorderLayout());
		this.decoration();
		this.panneauPrincipal.setLayout(new GridLayout(0,1));
		this.panneauPrincipal.add(this.lTitre);
		this.panneauPrincipal.add(this.lErreur);
		this.panneauPrincipal.add(this.lContrainte);
		this.panneauPrincipal.add(this.primaryKey);
		this.panneauPrincipal.add(this.notNull);
		this.panneauPrincipal.add(this.unique);
		this.panneauPrincipal.add(this.referencesKey);
		this.panneauPrincipal.add(this.lReference);
		this.panneauPrincipal.add(this.reference);
		this.add(new JLabel("      "),BorderLayout.SOUTH);
		this.add(new JLabel("      "),BorderLayout.WEST);
		this.add(new JLabel("      "),BorderLayout.EAST);
		this.add(panneauPrincipal,BorderLayout.CENTER);
		this.frame = new JFrame("Ajouter un attribut");
		this.frame.setContentPane(this);
		this.frame.setSize(new Dimension(250, 400));
		this.frame.setLocationRelativeTo(null);
		this.frame.setResizable(false);
		this.frame.setVisible(true);
	}
	
	private void decoration(){
		this.primaryKey = new JCheckBox("PRIMARY KEY");
		this.notNull = new JCheckBox("NOT NULL");
		this.unique = new JCheckBox("UNIQUE");
		this.referencesKey = new JCheckBox("REFERENCE KEY");
		this.lTitre = new JLabel("AJOUTER UN ATTRIBUT");
		this.lTitre.setHorizontalAlignment(SwingConstants.CENTER);
		lErreur = new JLabel("");
		lErreur.setForeground(new Color(255,0,0));
		lErreur.setHorizontalAlignment(SwingConstants.CENTER);
		this.lContrainte = new JLabel("Ajouter des contraintes");
		this.lReference = new JLabel("Table référencé par l'attribut");
		this.reference = new JComboBox<>();
		this.reference.addItem("test");
		this.reference.addItem("test2");
		this.reference.addItem("test3");
		this.valider = new JButton("Créer la contrainte");
	}
	
	public static void main(String[] args){
		new VueAjouterAttribut(new Fenetre());
	}
	
}
