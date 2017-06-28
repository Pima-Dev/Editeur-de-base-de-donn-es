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

import controleur.FenetreListener;
import controleur.PresserBoutonListener;
import modele.Colonne;
import modele.Contrainte;
import modele.CustomException;
import modele.Table;
import modele.TypeContrainte;
import modele.Util;
/**
 * s'affiche lorsque l'utilisateur appuie sur le bouton modifier contrainte
 */
public class VueModifierContrainte extends JPanel {

	/**
	 * la racine de référence qui permet d'accéder à toutes les vues
	 */
	private Fenetre fenetre;
	/**
	 * affiche le titre du chois d'attribut
	 */
	private JLabel lNom;
	/**
	 * contient les éléments de la fenêtre
	 */
	private JPanel panneauPrincipal;
	/**
	 * choix de la contrainte Not Null
	 */
	private JCheckBox notNull;
	/**
	 * choix de la contrainte Unique
	 */
	private JCheckBox unique;
	/**
	 * choix de la contrainte de clée étrangère
	 */
	private JCheckBox referencesKey;
	/**
	 * affiche le titre
	 */
	private JLabel lTitre;
	/**
	 * affiche le titre de modification de contrainte
	 */
	private JLabel lContrainte;
	/**
	 * affiche le titre de choix de l'origine de la clée étrangère
	 */
	private JLabel lReference;
	/**
	 * choix de l'origine de la clée étrangère
	 */
	private JComboBox<String> reference;
	/**
	 * choix de l'attribut ou appliquer la modification
	 */
	private JComboBox<String> colonnes;
	/**
	 * fenêtre affichant les éléments graphiques
	 */
	private JFrame frame;
	/**
	 * valider l'éxécution de la commande
	 */
	private JButton valider;

	/**
	 * construit le panneau
	 * @param fenetre la racine de référence qui permet d'accéder à toutes les vues
	 */
	public VueModifierContrainte(Fenetre fenetre) {
		this.fenetre = fenetre;
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				try {
					creer();
				} catch (CustomException e) {
					Util.logErreur(e.getMessage());
				}
			}
		});
	}

	/**
	 * création des éléments du panneau
	 * @throws CustomException cas d'erreur
	 */
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

	/**
	 * construction du panneau
	 * @throws CustomException cas d'erreur
	 */
	public void creer() throws CustomException {
		this.fenetre.setVueModifierContrainte(this);
		this.panneauPrincipal = new JPanel();
		this.setLayout(new BorderLayout());
		this.decoration();
		this.panneauPrincipal.setLayout(new GridLayout(0, 1));
		this.panneauPrincipal.add(this.lTitre);
		this.panneauPrincipal.add(this.lNom);
		this.panneauPrincipal.add(this.colonnes);
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
		this.frame.setSize(new Dimension(400, 450));
		this.frame.setLocationRelativeTo(null);
		this.frame.setResizable(false);
		this.frame.addWindowListener(new FenetreListener(this.fenetre));
		this.fenetre.getFenetre().setEnabled(false);
		this.frame.setVisible(true);
	}
	
	/**
	 * accès à la JCheckBox notNull
	 * @return la JCheckBox notNull
	 */
	public JCheckBox getNotNull() {
		return notNull;
	}

	/**
	 * accès à la JCheckBox unique
	 * @return la JCheckBox unique
	 */
	public JCheckBox getUnique() {
		return unique;
	}

	/**
	 * accès à la JCheckBox referencesKey
	 * @return la JCheckBox referencesKey
	 */
	public JCheckBox getReferencesKey() {
		return referencesKey;
	}

	/**
	 * accès au JLabel lTitre
	 * @return le JLabel lTitre
	 */
	public JLabel getlTitre() {
		return lTitre;
	}

	/**
	 * accès au JLabel lContrainte
	 * @return le JLabel lContrainte
	 */
	public JLabel getlContrainte() {
		return lContrainte;
	}

	/**
	 * accès au JLabel lReference
	 * @return le JLabel lReference
	 */
	public JLabel getlReference() {
		return lReference;
	}

	/**
	 * accès au JComboBox reference
	 * @return le JComboBox reference
	 */
	public JComboBox<String> getReference() {
		return reference;
	}

	/**
	 * accès au JFrame frame
	 * @return le JFrame frame
	 */
	public JFrame getFrame() {
		return frame;
	}

	/**
	 * accès au JButton valider
	 * @return le JButton valider
	 */
	public JButton getValider() {
		return valider;
	}

	/**
	 * accès au JComboBox colonnes
	 * @return le JComboBox colonnes
	 */
	public JComboBox<String> getColonnes() {
		return colonnes;
	}


}