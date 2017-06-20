package vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import modele.CustomException;
import modele.Table;

public class VueAjouterAttribut extends JPanel{
	
	private Fenetre fenetre;
	private JLabel lNom;
	private JTextField tNom;
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
	
	public VueAjouterAttribut(Fenetre fenetre) throws CustomException{
		this.fenetre = fenetre;
		this.fenetre.setVueAjouterAttribut(this);
		this.panneauPrincipal = new JPanel();
		this.setLayout(new BorderLayout());
		this.decoration();
		this.panneauPrincipal.setLayout(new GridLayout(0,1));
		this.panneauPrincipal.add(this.lTitre);
		this.panneauPrincipal.add(this.lErreur);
		this.panneauPrincipal.add(this.lNom);
		this.panneauPrincipal.add(this.tNom);
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
	
	private void decoration() throws CustomException{
		this.primaryKey = new JCheckBox("PRIMARY KEY");
		this.notNull = new JCheckBox("NOT NULL");
		this.unique = new JCheckBox("UNIQUE");
		this.referencesKey = new JCheckBox("REFERENCE KEY");
		this.lTitre = new JLabel("AJOUTER UN ATTRIBUT");
		this.lTitre.setHorizontalAlignment(SwingConstants.CENTER);
		this.lNom = new JLabel("Nom de l'attribut: ");
		this.lNom.setHorizontalAlignment(SwingConstants.CENTER);
		this.lErreur = new JLabel("");
		this.lErreur.setForeground(new Color(255,0,0));
		this.lErreur.setHorizontalAlignment(SwingConstants.CENTER);
		this.lContrainte = new JLabel("Ajouter des contraintes");
		this.lReference = new JLabel("Table référencé par l'attribut");
		this.tNom = new JTextField();
		this.reference = new JComboBox<String>();
		if(this.fenetre.getBDD() == null){
			throw new CustomException("Erreur", "Vous n'avez pas ouvert de base de données donc vous ne pouvez pas ajouter d'attribut");
		}
		ArrayList<Table> tables = this.fenetre.getBDD().getListeTable();
		for(Table table : tables){
			this.reference.addItem(table.getNom());
		}
		this.valider = new JButton("Créer la contrainte");
	}


	public Fenetre getFenetre() {
		return fenetre;
	}

	public JPanel getPanneauPrincipal() {
		return panneauPrincipal;
	}

	public JCheckBox getPrimaryKey() {
		return primaryKey;
	}

	public JCheckBox getNotNull() {
		return notNull;
	}

	public JCheckBox getUnique() {
		return unique;
	}

	public JCheckBox getReferencesKey() {
		return referencesKey;
	}

	public JLabel getlTitre() {
		return lTitre;
	}

	public JLabel getlErreur() {
		return lErreur;
	}

	public JLabel getlContrainte() {
		return lContrainte;
	}

	public JLabel getlReference() {
		return lReference;
	}

	public JComboBox<String> getReference() {
		return reference;
	}

	public JFrame getFrame() {
		return frame;
	}

	public JButton getValider() {
		return valider;
	}

	public JTextField gettNom() {
		return tNom;
	}
	
}
