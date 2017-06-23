package vue;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import controleur.PresserBoutonListener;
import modele.Colonne;
import modele.Contrainte;
import modele.CustomException;
import modele.Table;
import modele.TypeContrainte;

public class VueModifierContrainte extends JPanel {

	private Fenetre fenetre;
	private JLabel lNom;
	private JPanel panneauPrincipal;
	private JCheckBox notNull;
	private JCheckBox unique;
	private JCheckBox referencesKey;
	private JLabel lTitre;
	private JLabel lContrainte;
	private JLabel lReference;
	private JComboBox<String> reference;
	private JComboBox<String> colonnes;
	private JFrame frame;
	private JButton valider;

	public VueModifierContrainte(Fenetre fenetre) {
		this.fenetre = fenetre;
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				try {
					creer();
				} catch (CustomException e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void decoration() throws CustomException {
		if (this.fenetre.getBDD() == null) {
			throw new CustomException("Erreur",
					"Vous n'avez pas ouvert de base de données donc vous ne pouvez pas modifier les contraintes");
		}
		if(this.fenetre.getBDD().getTable((this.fenetre.getVuePrincipale().getCurrentTable())).getListeColonnes().size() == 0){
			throw new CustomException("Erreur", "Votre table ne contient aucune colonne");
		}
		this.notNull = new JCheckBox("NOT NULL");
		this.unique = new JCheckBox("UNIQUE");
		this.referencesKey = new JCheckBox("REFERENCE KEY");
		this.referencesKey.setName("checkbox reference key modifier contrainte");
		this.referencesKey.addActionListener(new PresserBoutonListener(this.fenetre));
		this.lTitre = new JLabel("MODIFIER LES CONTRAINTES");
		this.lTitre.setHorizontalAlignment(SwingConstants.CENTER);
		this.lNom = new JLabel("Colonne concerné par les modifications de contrainte: ");
		this.lNom.setHorizontalAlignment(SwingConstants.CENTER);

		this.lContrainte = new JLabel("Modifier les contraintes");
		this.lReference = new JLabel("Table référencé par l'attribut");
		this.reference = new JComboBox<String>();
		this.colonnes = new JComboBox<String>();
		this.colonnes.setName("combobox choix colonne pour modification contrainte");
		this.colonnes.addActionListener(new PresserBoutonListener(this.fenetre));
		ArrayList<Table> tables = this.fenetre.getBDD().getListeTable();
		for (Table table : tables) {
			if (!table.getNom().equals(this.fenetre.getVuePrincipale().getCurrentTable()))
				this.reference.addItem(table.getNom());
		}
		for(Colonne col : this.fenetre.getBDD().getTable((this.fenetre.getVuePrincipale().getCurrentTable())).getListeColonnes()){
			this.colonnes.addItem(col.getNom());
		}
		this.reference.setEnabled(false);
		this.valider = new JButton("Modifier les contraintes");
		this.valider.setName("valider modification contraintes");
		this.valider.addActionListener(new PresserBoutonListener(this.fenetre));
		Colonne colonne = this.fenetre.getBDD().getTable((this.fenetre.getVuePrincipale().getCurrentTable())).getColonne(this.colonnes.getItemAt(0).toString());
		for(Contrainte contrainte : (ArrayList<Contrainte>) colonne.getListeContraintes()){
			if(contrainte.getContrainteType() == TypeContrainte.NOTNULL){
				this.notNull.setSelected(true);
			}
			else if(contrainte.getContrainteType() == TypeContrainte.UNIQUE){
				this.unique.setSelected(true);
			}
			else if(contrainte.getContrainteType() == TypeContrainte.REFERENCEKEY){
				this.referencesKey.setSelected(true);
				this.reference.setEnabled(true);
			}
		}

	}

	public void creer() throws CustomException {
		this.fenetre.setVueModifierContrainte(this);
		this.panneauPrincipal = new JPanel();
		this.setLayout(new BorderLayout());
		this.decoration();
		this.panneauPrincipal.setLayout(new GridLayout(0, 1));
		this.panneauPrincipal.add(this.lTitre);
		this.panneauPrincipal.add(this.colonnes);
		this.panneauPrincipal.add(this.lNom);
		this.panneauPrincipal.add(this.lContrainte);
		this.panneauPrincipal.add(this.notNull);
		this.panneauPrincipal.add(this.unique);
		this.panneauPrincipal.add(this.referencesKey);
		this.panneauPrincipal.add(this.lReference);
		this.panneauPrincipal.add(this.reference);
		this.panneauPrincipal.add(this.valider);
		this.add(new JLabel("      "), BorderLayout.SOUTH);
		this.add(new JLabel("      "), BorderLayout.WEST);
		this.add(new JLabel("      "), BorderLayout.EAST);
		this.add(panneauPrincipal, BorderLayout.CENTER);
		this.frame = new JFrame("Modifier les contraintes");
		this.frame.setContentPane(this);
		this.frame.setSize(new Dimension(350, 400));
		this.frame.setLocationRelativeTo(null);
		this.frame.setResizable(false);
		this.frame.setVisible(true);
	}

	public Fenetre getFenetre() {
		return fenetre;
	}

	public JPanel getPanneauPrincipal() {
		return panneauPrincipal;
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

	public JComboBox<String> getColonnes() {
		return colonnes;
	}


}