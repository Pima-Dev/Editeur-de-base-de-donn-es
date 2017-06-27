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
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import controleur.FenetreListener;
import controleur.PresserBoutonListener;
import modele.CustomException;
import modele.Table;
import modele.TypeDonnee;
import modele.Util;
/**
 * s'affiche lorsque l'utilisateur appuye sur le bouton ajouter attribut ou l'option ajouter un attrbut dans le menu, elle permet d'ajouter un attribut à la table courante
 */
public class VueAjouterAttribut extends JPanel {

	/**
	 * la racine de référence qui permet d'accéder à toutes les vues
	 */
	private Fenetre fenetre;
	/**
	 * affiche le titre du champ du nom de l'attribut
	 */
	private JLabel lNom;
	/**
	 * entrée du nom de l'attribut
	 */
	private JTextField tNom;
	/**
	 * contient tous les élément de la fenêtre
	 */
	private JPanel panneauPrincipal;
	/**
	 * ajouter la contrainte Not Null au nouvel attribut
	 */
	private JCheckBox notNull;
	/**
	 * ajouter la contrainte Unique au nouvel attribut
	 */
	private JCheckBox unique;
	/**
	 * ajouter une contrainte de clée étrangère au nouvel attribut
	 */
	private JCheckBox referencesKey;
	/**
	 * affiche le titre
	 */
	private JLabel lTitre;
	/**
	 * affiche le titre des contraintes
	 */
	private JLabel lContrainte;
	/**
	 * affiche le titre des références de clée étrangère
	 */
	private JLabel lReference;
	/**
	 * choix de la référence de la clée étrangère
	 */
	private JComboBox<String> reference;
	/**
	 * affiche le panneauPrincipal
	 */
	private JFrame frame;
	/**
	 * permet de valider la création de nouvel attribut
	 */
	private JButton valider;
	/**
	 * affiche le titre des types de données
	 */
	private JLabel type;
	/**
	 * choix du type de donnée
	 */
	private JComboBox<String> comboboxType;

	/**
	 * construit le panneau principal en appelant les méthodes creer() et decoration()
	 * @param fenetre la racine de référence qui permet d'accéder à toutes les vues
	 */
	public VueAjouterAttribut(Fenetre fenetre) {
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
	 * @throws CustomException gère les cas d'erreur
	 */
	private void decoration() throws CustomException {
		this.notNull = new JCheckBox("NOT NULL");
		this.unique = new JCheckBox("UNIQUE");
		this.referencesKey = new JCheckBox("REFERENCE KEY");
		this.referencesKey.setName("checkbox reference key");
		this.referencesKey.addActionListener(new PresserBoutonListener(this.fenetre));
		this.lTitre = new JLabel("AJOUTER UN ATTRIBUT");
		this.lTitre.setHorizontalAlignment(SwingConstants.CENTER);
		this.lNom = new JLabel("Nom de l'attribut: ");
		this.lNom.setHorizontalAlignment(SwingConstants.CENTER);

		this.lContrainte = new JLabel("Ajouter des contraintes");
		this.lReference = new JLabel("Table référencé par l'attribut");
		this.tNom = new JTextField();
		this.reference = new JComboBox<String>();
		if (this.fenetre.getBDD() == null) {
			throw new CustomException("Erreur",
					"Vous n'avez pas ouvert de base de données donc vous ne pouvez pas ajouter d'attribut");
		}
		ArrayList<Table> tables = this.fenetre.getBDD().getListeTable();
		for (Table table : tables) {
			if (!table.getNom().equals(this.fenetre.getVuePrincipale().getCurrentTable()))
				this.reference.addItem(table.getNom());
		}
		this.reference.setEnabled(false);
		this.valider = new JButton("Créer l'attribut");
		this.valider.setName("valider creation attribut");
		this.valider.addActionListener(new PresserBoutonListener(this.fenetre));
		this.type = new JLabel("Type de l'attribut");
		this.comboboxType = new JComboBox<String>();
		this.comboboxType.addItem(TypeDonnee.INTEGER.getSQLType());
		this.comboboxType.addItem(TypeDonnee.DOUBLE.getSQLType());
		this.comboboxType.addItem(TypeDonnee.CHAR.getSQLType());
		this.comboboxType.addItem(TypeDonnee.DATE.getSQLType());
	}

	/**
	 * construit le panneau principal
	 * @throws CustomException gère les cas d'erreur
	 */
	public void creer() throws CustomException {
		this.fenetre.setVueAjouterAttribut(this);
		this.panneauPrincipal = new JPanel();
		this.setLayout(new BorderLayout());
		this.decoration();
		this.panneauPrincipal.setLayout(new GridLayout(0, 1));
		this.panneauPrincipal.add(this.lTitre);
		this.panneauPrincipal.add(this.lNom);
		this.panneauPrincipal.add(this.tNom);
		this.panneauPrincipal.add(this.type);
		this.panneauPrincipal.add(this.comboboxType);
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
		this.frame = new JFrame("Ajouter un attribut");
		this.frame.setContentPane(this);
		this.frame.setSize(new Dimension(350, 400));
		this.frame.setLocationRelativeTo(null);
		this.frame.setResizable(false);
		this.frame.setVisible(true);
		this.frame.addWindowListener(new FenetreListener(this.fenetre));
		this.fenetre.getFenetre().setEnabled(false);
	}

	/**
	 * accès à la fenetre
	 * @return la fenetre
	 */
	public Fenetre getFenetre() {
		return fenetre;
	}

	/**
	 * accès au JPanel panneauPrincipal
	 * @return le JPanel panneauPrincipal
	 */
	public JPanel getPanneauPrincipal() {
		return panneauPrincipal;
	}

	/**
	 * accès au JCheckBox notNull
	 * @return le JCheckBox notNull
	 */
	public JCheckBox getNotNull() {
		return notNull;
	}

	/**
	 * accès au JCheckBox unique
	 * @return le JCheckBox unique
	 */
	public JCheckBox getUnique() {
		return unique;
	}

	/**
	 * accès au JCheckBox referencesKey
	 * @return le JCheckBox referencesKey
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
	 * accès au JComboBox<String> reference
	 * @return le JComboBox<String> reference
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
	 * accès au JTextField tNom
	 * @return le JTextField tNom
	 */
	public JTextField gettNom() {
		return tNom;
	}

	/**
	 * accès au JComboBox<String> comboboxType
	 * @return le JComboBox<String> comboboxType
	 */
	public JComboBox<String> getComboboxType() {
		return comboboxType;
	}

}
