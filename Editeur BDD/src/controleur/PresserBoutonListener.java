package controleur;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.jws.soap.SOAPBinding.Style;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import modele.BaseDeDonnees;
import modele.Colonne;
import modele.Contrainte;
import modele.CustomException;
import modele.ELFichier;
import modele.Session;
import modele.Table;
import modele.TypeContrainte;
import modele.TypeDonnee;
import modele.Util;
import vue.Fenetre;
import vue.ModeleTable;
import vue.VueAjouterAttribut;
import vue.VueCreationBDD;
import vue.VueDeConnexion;
import vue.VueDeCreationDUtilisateur;
import vue.VueLog;
import vue.VueLogConsole;
import vue.VueModifierContrainte;
import vue.VueOuvrirBDD;
import vue.VuePrincipale;
import vue.VueRechercheAvance;

/**
 * @author Utilisateur
 *
 */
public class PresserBoutonListener implements ActionListener {

	private Fenetre fenetre;

	public PresserBoutonListener(Fenetre fenetre) {
		this.fenetre = fenetre;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() instanceof JTextField) {
			JTextField field = (JTextField) e.getSource();

			if (field.getName().equals("Connexion")) {
				connexion();
			}
			if (field.getName().equals("Valider creation nouvel utilisateur")) {
				creationUtilisateur();
			}
		}
		if (e.getSource() instanceof JPasswordField) {
			JPasswordField field = (JPasswordField) e.getSource();

			if (field.getName().equals("Connexion")) {
				connexion();
			}
			if (field.getName().equals("Valider creation nouvel utilisateur")) {
				creationUtilisateur();
			}
		} else if (e.getSource() instanceof JButton) {
			JButton bouton = (JButton) e.getSource();

			if (bouton.getName().equals("Connexion")) {
				connexion();
			} else if (bouton.getName().equals("Nouvel utilisateur")) {
				fenetre.getFenetre().setContentPane(new VueDeCreationDUtilisateur(fenetre));
				fenetre.getFenetre().setVisible(true);
				fenetre.getFenetre().setSize(new Dimension(400, 600));
				fenetre.getFenetre().setLocationRelativeTo(null);
			} else if (bouton.getName().equals("Mot de passe oublie")) {
				JOptionPane.showMessageDialog(null, "MDP oublié");
			} else if (bouton.getName().equals("Valider mdp oublié")) {
				String nouveau = new String(this.fenetre.getVueMDPOublieNouveau().getfNouveau().getPassword());
				String confirmation = new String(
						this.fenetre.getVueMDPOublieNouveau().getfConfirmation().getPassword());
				if (!nouveau.equals("") && !confirmation.equals("") && nouveau.equals(confirmation)) {
					VueDeConnexion vueCo = new VueDeConnexion(fenetre);
					this.fenetre.setVueDeConnexion(vueCo);
					fenetre.getFenetre().setContentPane(vueCo);
					fenetre.getFenetre().setVisible(true);
					fenetre.getFenetre().pack();
					fenetre.getFenetre().setLocationRelativeTo(null);
				}
				this.fenetre.getVueMDPOublieNouveau().getlInfo().setText("Erreur de mot de passe");
			}

			else if (bouton.getName().equals("Valider creation nouvel utilisateur")) {
				creationUtilisateur();
			} else if (bouton.getName().equals("valider creation bdd")) {
				if (this.fenetre.getVueCreationBDD().getfNomBDD().getText().contains(" ")) {
					new CustomException("Erreur", "Le nom de la BDD ne doit pas contenir d'espaces");
					return;
				}
				int port = modele.Util.isInteger(this.fenetre.getVueCreationBDD().getfPort().getText())
						? Integer.parseInt(this.fenetre.getVueCreationBDD().getfPort().getText()) : 3306;
				try {
					BaseDeDonnees bdd = new BaseDeDonnees(this.fenetre.getVueCreationBDD().getfNomBDD().getText(),
							this.fenetre.getVueCreationBDD().getfNomUtilisateur().getText(),
							new String(this.fenetre.getVueCreationBDD().getfMotDePasse().getPassword()), this.fenetre,
							this.fenetre.getVueCreationBDD().getfURL().getText(), port);
					bdd.creerBDD();
					bdd.refreshAllBDD();
					this.fenetre.getVueCreationBDD().getFrame().dispose();
				} catch (CustomException e1) {
					Util.logErreur(e1.getMessage());
				}
			}

			else if (bouton.getName().equals("ajouter attribut")) {
				new VueAjouterAttribut(this.fenetre);

			}

			else if (bouton.getName().equals("valider ouverture bdd")) {
				if (this.fenetre.getVueOuvrirBDD().getListeBDD().getSelectedItem().toString().equals("")) {
					int port = modele.Util.isInteger(this.fenetre.getVueOuvrirBDD().getfPort().getText())
							? Integer.parseInt(this.fenetre.getVueOuvrirBDD().getfPort().getText()) : 3306;
					try {
						BaseDeDonnees bdd = new BaseDeDonnees(this.fenetre.getVueOuvrirBDD().gettNom().getText(),
								this.fenetre.getVueOuvrirBDD().getfNomUtilisateur().getText(),
								new String(this.fenetre.getVueOuvrirBDD().getfMotDePasse().getPassword()), this.fenetre,
								this.fenetre.getVueOuvrirBDD().getfURL().getText(), port);
						bdd.chargerBDD();
						bdd.refreshAllBDD();
						bdd.saveDonneesBDD();
						this.fenetre.getVueOuvrirBDD().getFrame().dispose();
					} catch (CustomException e1) {
						Util.logErreur(e1.getMessage());
					} catch (SQLException e1) {
						new CustomException("Erreur", e1.getMessage());
						e1.printStackTrace();
					}
				} else {
					String nomBdd = this.fenetre.getVueOuvrirBDD().getListeBDD().getSelectedItem().toString();
					String path = this.fenetre.getSession().getBDDPath() + nomBdd;
					try {
						BaseDeDonnees bdd = new BaseDeDonnees(nomBdd, ELFichier.chargerValeur(path, "user"),
								ELFichier.chargerValeur(path, "MDP"), this.fenetre,
								ELFichier.chargerValeur(path, "url"),
								Integer.parseInt(ELFichier.chargerValeur(path, "port")));
						bdd.saveDonneesBDD();
						bdd.chargerBDD();
						bdd.refreshAllBDD();
						this.fenetre.getVueOuvrirBDD().getFrame().dispose();
					} catch (NumberFormatException e1) {
						Util.logErreur(e1.getMessage());
						e1.printStackTrace();
					} catch (CustomException e1) {
						Util.logErreur(e1.getMessage());
						e1.printStackTrace();
					} catch (SQLException e1) {
						Util.logErreur(e1.getMessage());
						e1.printStackTrace();
					}
				}

			}

			else if (bouton.getName().equals("ajouter un tuple")) {
				if (this.fenetre.getBDD() == null) {
					new CustomException("Erreur", "Aucune table n'est ouverte.");
					throw new IllegalArgumentException("Aucune BDD d'ouverte");
				}
				Table table = this.fenetre.getBDD().getTable(this.fenetre.getVuePrincipale().getCurrentTable());
				ArrayList<Object> tuple = new ArrayList<Object>();

				if (table == null) {
					new CustomException("Erreur", "Aucune table n'est ouverte.");
					throw new IllegalArgumentException("Aucune BDD d'ouverte");
				} else {
					for (Colonne col : table.getListeColonnes()) {
						String contrainte = "\n";
						for (Contrainte c : (ArrayList<Contrainte>) col.getListeContraintes()) {
							contrainte += c.getContrainteType() + "\n";
						}

						Object s = " ";

						if (col.getTypeDonnees() == TypeDonnee.DATE) {
							while (!Util.isValidDate((String) s) && s != null && !s.toString().equals(""))
								s = JOptionPane.showInputDialog(null,
										"Entrez une valeur de type '" + col.getTypeDonnees().getSQLType()
												+ "' (dd-MM-yyyy) pour \nl'attribut '" + col.getNom()
												+ "' ayant ces contraintes: " + contrainte,
										"Entrez un '" + col.getTypeDonnees().getSQLType() + "'",
										JOptionPane.QUESTION_MESSAGE);
							if (s == null)
								return;
							if (!s.toString().replaceAll(" ", "").equals(""))
								tuple.add(s);
							else
								tuple.add(null);
						}

						else if (col.getTypeDonnees() == TypeDonnee.INTEGER) {
							while (!Util.isInteger((String) s) && s != null && !s.toString().equals(""))
								s = JOptionPane.showInputDialog(null,
										"Entrez une valeur de type '" + col.getTypeDonnees().getSQLType()
												+ "' pour \nl'attribut '" + col.getNom() + "' ayant ces contraintes: "
												+ contrainte,
										"Entrez un '" + col.getTypeDonnees().getSQLType() + "'",
										JOptionPane.QUESTION_MESSAGE);
							if (s == null)
								return;
							if (!s.toString().replaceAll(" ", "").equals(""))
								tuple.add(Integer.parseInt((String) s));
							else
								tuple.add(null);
						}

						else if (col.getTypeDonnees() == TypeDonnee.DOUBLE) {
							while (!Util.isDouble((String) s) && s != null && !s.toString().equals(""))
								s = JOptionPane.showInputDialog(null,
										"Entrez une valeur de type '" + col.getTypeDonnees().getSQLType()
												+ "' pour \nl'attribut '" + col.getNom() + "' ayant ces contraintes: "
												+ contrainte,
										"Entrez un '" + col.getTypeDonnees().getSQLType() + "'",
										JOptionPane.QUESTION_MESSAGE);
							if (s == null)
								return;
							if (!s.toString().replaceAll(" ", "").equals(""))
								tuple.add(Double.parseDouble((String) s));
							else
								tuple.add(null);
						}

						else {
							while (s.toString().contains(" ") && s != null && !s.toString().equals(""))
								s = JOptionPane.showInputDialog(null,
										"Entrez une valeur de type '" + col.getTypeDonnees().getSQLType()
												+ "' pour \nl'attribut '" + col.getNom() + "' ayant ces contraintes: "
												+ contrainte,
										"Entrez un '" + col.getTypeDonnees().getSQLType() + "'",
										JOptionPane.QUESTION_MESSAGE);
							if (s == null)
								return;
							if (!s.toString().replaceAll(" ", "").equals(""))
								tuple.add(s);
							else
								tuple.add(null);
						}
					}

					try {
						table.insererTuple(tuple, true);
						table.refreshTable();
					} catch (CustomException e1) {
						Util.logErreur(e1.getMessage());
					}
				}
			}

			else if (bouton.getName().equals("supprimer table")) {
				int rep = JOptionPane.showConfirmDialog(null,
						"Voulez vous vraiment supprimer la table '" + this.fenetre.getVuePrincipale().getCurrentTable()
								+ "' ?",
						"Supprimer la table '" + this.fenetre.getVuePrincipale().getCurrentTable() + "' ?",
						JOptionPane.CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (rep == JOptionPane.OK_OPTION) {
					Table table = this.fenetre.getBDD().getTable(this.fenetre.getVuePrincipale().getCurrentTable());
					try {
						table.supprimerTable();
						this.fenetre.getBDD().chargerBDD();
						this.fenetre.getBDD().refreshAllBDD();
					} catch (CustomException e1) {
						Util.logErreur(e1.getMessage());
					} catch (SQLException e1) {
						Util.logErreur(e1.getMessage());
					}

				}
			}

			else if (bouton.getName().equals("valider creation attribut")) {
				Table current = this.fenetre.getBDD().getTable(this.fenetre.getVuePrincipale().getCurrentTable());

				if (this.fenetre.getVueAjouterAttribut().gettNom().getText().isEmpty()) {
					new CustomException("Erreur", "Vous n'avez pas indiqué le nom de l'attribut.");

					return;
				}

				if (this.fenetre.getVueAjouterAttribut().gettNom().getText().contains(" ")) {
					new CustomException("Erreur", "Le nom de l'attribut ne doit pas contenir d'espace.");
					return;
				}
				String sType = this.fenetre.getVueAjouterAttribut().getComboboxType().getSelectedItem().toString();
				TypeDonnee type = sType.equals("INTEGER") ? TypeDonnee.INTEGER
						: (sType.equals("DOUBLE") ? TypeDonnee.DOUBLE
								: (sType.equals("VARCHAR(100)") ? TypeDonnee.CHAR : TypeDonnee.DATE));
				Colonne col = new Colonne(this.fenetre.getVueAjouterAttribut().gettNom().getText(), type);
				try {
					if (this.fenetre.getVueAjouterAttribut().getNotNull().isSelected()) {
						col.ajouterContrainte(new Contrainte(TypeContrainte.NOTNULL, null));
					}
					if (this.fenetre.getVueAjouterAttribut().getUnique().isSelected()) {
						col.ajouterContrainte(new Contrainte(TypeContrainte.UNIQUE, null));
					}
					if (this.fenetre.getVueAjouterAttribut().getReferencesKey().isSelected()) {
						String referenceTable = this.fenetre.getVueAjouterAttribut().getReference().getSelectedItem()
								.toString();
						col.ajouterContrainte(new Contrainte(TypeContrainte.REFERENCEKEY,
								this.fenetre.getBDD().getTable(referenceTable)));
					}
					Object obj = " ";
					if (type.equals(TypeDonnee.INTEGER)) {
						while (!Util.isInteger((String) obj) && obj != null && !obj.toString().equals("")) {
							obj = JOptionPane.showInputDialog(null,
									"Entrez la valeur par défault de votre nouvelle colonne de type "
											+ type.getSQLType(),
									"Entrez une valeur de type " + type.getSQLType(), JOptionPane.QUESTION_MESSAGE);
						}
					} else if (type.equals(TypeDonnee.DOUBLE)) {
						while (!Util.isDouble((String) obj) && obj != null && !obj.toString().equals("")) {
							obj = JOptionPane.showInputDialog(null,
									"Entrez la valeur par défault de votre nouvelle colonne de type "
											+ type.getSQLType(),
									"Entrez une valeur de type " + type.getSQLType(), JOptionPane.QUESTION_MESSAGE);
						}
					} else if (type.equals(TypeDonnee.DATE)) {
						while (!Util.isValidDate((String) obj) && obj != null && !obj.toString().equals("")) {
							obj = JOptionPane.showInputDialog(null,
									"Entrez la valeur par défault de votre nouvelle colonne de type "
											+ type.getSQLType(),
									"Entrez une valeur de type " + type.getSQLType(), JOptionPane.QUESTION_MESSAGE);
						}
					} else {
						obj = JOptionPane.showInputDialog(null,
								"Entrez la valeur par défault de votre nouvelle colonne de type " + type.getSQLType(),
								"Entrez une valeur de type " + type.getSQLType(), JOptionPane.QUESTION_MESSAGE);
					}
					if (obj == null)
						return;
					if (obj.toString().replaceAll(" ", "").equals(""))
						obj = null;

					if (current.getListeColonnes().size() > 0) {
						for (int i = 0; i < current.getListeColonnes().get(0).getListeValeurs().size(); i++) {
							col.ajouterValeur(obj);
						}
					}
					current.ajouterColonneATableDejaExistente(col, obj);
					this.fenetre.getVueAjouterAttribut().getFrame().dispose();
					current.refreshTable();

				} catch (CustomException e1) {
					Util.logErreur(e1.getMessage());
				} catch (SQLException e1) {
					Util.logErreur(e1.getMessage());
				}

			} else if (bouton.getName().equals("OptionRecherche")) {
				if (fenetre.getVuePrincipale().getTable() != null) {
					fenetre.getVuePrincipale().getfChercher().setText("Chercher les occurences");
					((ModeleTable) fenetre.getVuePrincipale().getTable().getModel()).rechercher("");
					new VueRechercheAvance(fenetre);
				} else {
					JOptionPane.showMessageDialog(null, "Table inexistante", "Erreur", JOptionPane.WARNING_MESSAGE);
				}
			} else if (bouton.getName().equals("LancerRechercheAvance")) {
				((ModeleTable) fenetre.getVuePrincipale().getTable().getModel())
						.rechercher(fenetre.getVueRechercheAvance().getfRecherche().getText());
				fenetre.getVueRechercheAvance().getFenetreRechercheAvance().dispose();
			} else if (bouton.getName().equals("modifier les contraintes")) {
				new VueModifierContrainte(this.fenetre);
			}

			else if (bouton.getName().equals("valider modification contraintes")) {
				ArrayList<Contrainte> contraintes = new ArrayList<Contrainte>();
				Colonne col = this.fenetre.getBDD().getTable((this.fenetre.getVuePrincipale().getCurrentTable()))
						.getColonne(this.fenetre.getVueModifierContrainte().getColonnes().getSelectedItem().toString());
				if (this.fenetre.getVueModifierContrainte().getNotNull().isSelected()) {
					contraintes.add(new Contrainte(TypeContrainte.NOTNULL, null));
				}
				if (this.fenetre.getVueModifierContrainte().getUnique().isSelected()) {
					contraintes.add(new Contrainte(TypeContrainte.UNIQUE, null));
				}
				if (this.fenetre.getVueModifierContrainte().getReferencesKey().isSelected()) {
					Table reference = this.fenetre.getBDD().getTable(
							this.fenetre.getVueModifierContrainte().getReference().getSelectedItem().toString());
					contraintes.add(new Contrainte(TypeContrainte.REFERENCEKEY, reference));
				}
				try {
					this.fenetre.getBDD().getTable((this.fenetre.getVuePrincipale().getCurrentTable()))
							.modifierContraintes(contraintes, col);
					this.fenetre.getVueModifierContrainte().getFrame().dispose();
				} catch (SQLException e1) {
					Util.logErreur(e1.getMessage());
					new CustomException("Erreur", e1.getMessage());
				} catch (CustomException e1) {
					Util.logErreur(e1.getMessage());
				}
			}

			else if (bouton.getName().equals("supprimer attribut")) {
				if (this.fenetre.getBDD() == null) {
					new CustomException("Erreur", "Aucune base de données n'est ouverte.");
					return;
				}
				Table table = this.fenetre.getBDD().getTable(this.fenetre.getVuePrincipale().getCurrentTable());
				if (table == null) {
					new CustomException("Erreur", "Une erreur est survenu, veuillez réassayer.");
					return;
				}
				String[] colonnes = new String[table.getListeColonnes().size()];
				for (int i = 0; i < table.getListeColonnes().size(); i++) {
					if (!table.getListeColonnes().get(i).getNom().equals(table.getClePrimaire().getNom()))
						colonnes[i] = table.getListeColonnes().get(i).getNom();
				}
				String colonne = (String) JOptionPane.showInputDialog(null, "Sélectionnez l'attribut à supprimer",
						"Supprimer un attribut", JOptionPane.QUESTION_MESSAGE, null, colonnes, colonnes[0]);

				if (colonne == null) {
					return;
				}
				try {
					table.supprimerColonne(colonne);
					table.refreshTable();
				} catch (SQLException e1) {
					Util.logErreur(e1.getMessage());
				} catch (CustomException e1) {
					Util.logErreur(e1.getMessage());
				}
			} else if(bouton.getName().equals("acceder a la console")){
				new VueLogConsole(this.fenetre);
			} else if (bouton.getName().equals("voir les logs")){
				new VueLog(this.fenetre);
			}
			

		} else if (e.getSource() instanceof JRadioButton) {
			JRadioButton radioBouton = (JRadioButton) e.getSource();

			if (radioBouton.getName().equals("creer bdd hebergement local")) {
				fenetre.getVueCreationBDD().getfURL().setEnabled(false);
				fenetre.getVueCreationBDD().getBoutonServeur().setSelected(false);
				fenetre.getVueCreationBDD().getfURL().setText("");
				fenetre.getVueCreationBDD().getfPort().setText("");
				fenetre.getVueCreationBDD().getfPort().setEnabled(false);
			} else if (radioBouton.getName().equals("creer bdd hebergement distant")) {
				fenetre.getVueCreationBDD().getBoutonLocal().setSelected(false);
				fenetre.getVueCreationBDD().getfURL().setEnabled(true);
				fenetre.getVueCreationBDD().getfPort().setEnabled(true);
			}

			else if (radioBouton.getName().equals("ouvir bdd hebergement local")) {
				fenetre.getVueOuvrirBDD().getfURL().setEnabled(false);
				fenetre.getVueOuvrirBDD().getBoutonServeur().setSelected(false);
				fenetre.getVueOuvrirBDD().getfURL().setText("");
				fenetre.getVueOuvrirBDD().getfPort().setText("");
				fenetre.getVueOuvrirBDD().getfPort().setEnabled(false);
			} else if (radioBouton.getName().equals("ouvrir bdd hebergement distant")) {
				fenetre.getVueOuvrirBDD().getBoutonLocal().setSelected(false);
				fenetre.getVueOuvrirBDD().getfURL().setEnabled(true);
				fenetre.getVueOuvrirBDD().getfPort().setEnabled(true);
			}

		}

		else if (e.getSource() instanceof JMenuItem) {
			JMenuItem item = (JMenuItem) e.getSource();
			if (item.getName().equals("MenuNouveau")) {
				new VueCreationBDD(this.fenetre);
			} else if (item.getName().equals("MenuOuvrir")) {
				new VueOuvrirBDD(this.fenetre);
			} else if (item.getName().equals("MenuSupprimerBDD")) {
				JOptionPane messageComfirmation = new JOptionPane();
				if (this.fenetre.getBDD() == null) {
					new CustomException("Erreur", "Aucune BDD d'ouverte à supprimer.");
				} else {
					int validation = messageComfirmation.showConfirmDialog(null,
							"Voulez-vous supprimer la BDD courante?", "Suppression de la BDD",
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if (validation == JOptionPane.OK_OPTION) {
						try {
							this.fenetre.getBDD().supprimerBDD();
							this.fenetre.getVuePrincipale().resetJTable();
							this.fenetre.getVuePrincipale().resetListeTable();
						} catch (CustomException e1) {
							e1.printStackTrace();
						}
					}
				}
			} else if (item.getName().equals("MenuExporterEnPDF")) {

				if(this.fenetre.getBDD() == null){
					new CustomException("Erreur", "Aucune base de données n'est ouverte.");
					return;
				}
				
				if(this.fenetre.getVuePrincipale().getCurrentTable() == null){
					new CustomException("Erreur", "Il n'y a pas de table d'ouverte");
				}
				
				JFileChooser choix = new JFileChooser();
				choix.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				int retour = choix.showOpenDialog(new JFrame());
				if (retour == JFileChooser.APPROVE_OPTION) {
					Util.exporterPDF(this.fenetre.getVuePrincipale().getTable(),
							choix.getSelectedFile().getAbsolutePath());

				}
			}
		}

		else if (e.getSource() instanceof JComboBox) {
			JComboBox box = (JComboBox) e.getSource();
			if (box.getName().equals("ouvrir bdd liste bdd")) {
				if (box.getSelectedItem().toString().equals("")) {
					fenetre.getVueOuvrirBDD().getfURL().setEnabled(true);
					fenetre.getVueOuvrirBDD().getfPort().setEnabled(true);
					fenetre.getVueOuvrirBDD().getfNomUtilisateur().setEnabled(true);
					fenetre.getVueOuvrirBDD().getfMotDePasse().setEnabled(true);
					fenetre.getVueOuvrirBDD().gettNom().setEnabled(true);
					fenetre.getVueOuvrirBDD().getBoutonLocal().setEnabled(true);
					fenetre.getVueOuvrirBDD().getBoutonServeur().setEnabled(true);

				} else {
					fenetre.getVueOuvrirBDD().getfURL().setEnabled(false);
					fenetre.getVueOuvrirBDD().getfPort().setEnabled(false);
					fenetre.getVueOuvrirBDD().getfNomUtilisateur().setEnabled(false);
					fenetre.getVueOuvrirBDD().getfMotDePasse().setEnabled(false);
					fenetre.getVueOuvrirBDD().gettNom().setEnabled(false);
					fenetre.getVueOuvrirBDD().getBoutonLocal().setEnabled(false);
					fenetre.getVueOuvrirBDD().getBoutonServeur().setEnabled(false);

					fenetre.getVueOuvrirBDD().getfURL().setText("");
					fenetre.getVueOuvrirBDD().getfPort().setText("");
					fenetre.getVueOuvrirBDD().getfNomUtilisateur().setText("");
					fenetre.getVueOuvrirBDD().getfMotDePasse().setText("");
					fenetre.getVueOuvrirBDD().gettNom().setText("");
					fenetre.getVueOuvrirBDD().getBoutonServeur().setSelected(false);
					fenetre.getVueOuvrirBDD().getBoutonLocal().setSelected(false);
				}
			}

			else if (box.getName().equals("combobox choix colonne pour modification contrainte")) {
				Colonne col = this.fenetre.getBDD().getTable((this.fenetre.getVuePrincipale().getCurrentTable()))
						.getColonne(box.getSelectedItem().toString());
				this.fenetre.getVueModifierContrainte().getNotNull().setSelected(false);
				this.fenetre.getVueModifierContrainte().getUnique().setSelected(false);
				this.fenetre.getVueModifierContrainte().getReferencesKey().setSelected(false);
				this.fenetre.getVueModifierContrainte().getReference().setEnabled(false);
				for (Contrainte contrainte : (ArrayList<Contrainte>) col.getListeContraintes()) {
					if (contrainte.getContrainteType() == TypeContrainte.NOTNULL) {
						this.fenetre.getVueModifierContrainte().getNotNull().setSelected(true);
					} else if (contrainte.getContrainteType() == TypeContrainte.UNIQUE) {
						this.fenetre.getVueModifierContrainte().getUnique().setSelected(true);
					} else if (contrainte.getContrainteType() == TypeContrainte.REFERENCEKEY) {
						this.fenetre.getVueModifierContrainte().getReferencesKey().setSelected(true);
						this.fenetre.getVueModifierContrainte().getReference().setEnabled(true);
					}
				}
			}
		}

		if (e.getSource() instanceof JCheckBox) {
			JCheckBox check = (JCheckBox) e.getSource();
			if (check.getName().equals("selectionnerLigne")) {
				if (check.isSelected()) {
					fenetre.getVueRechercheAvance().getLigneMax().setEnabled(true);
					fenetre.getVueRechercheAvance().getLigneMin().setEnabled(true);
				} else {
					fenetre.getVueRechercheAvance().getLigneMax().setEnabled(false);
					fenetre.getVueRechercheAvance().getLigneMin().setEnabled(false);
				}
			} else if (check.getName().equals("checkbox reference key")) {
				if (check.isSelected()) {
					this.fenetre.getVueAjouterAttribut().getReference().setEnabled(true);
				} else {
					this.fenetre.getVueAjouterAttribut().getReference().setEnabled(false);
				}
			}

			else if (check.getName().equals("checkbox reference key modifier contrainte")) {
				if (check.isSelected()) {
					this.fenetre.getVueModifierContrainte().getReference().setEnabled(true);
				} else {
					this.fenetre.getVueModifierContrainte().getReference().setEnabled(false);
				}
			}
		}
	}

	public boolean bonMDP(String nom, String mdp) {
		boolean ret = false;
		String encrypt = ELFichier.cryptMDP(mdp);
		if (encrypt.equals(ELFichier.chargerValeur(nom + "/session", "MDP"))
				&& nom.equals(ELFichier.chargerValeur(nom + "/session", "user"))) {
			ret = true;
		}
		return ret;
	}

	public void connexion() {
		String nom = this.fenetre.getVueDeConnexion().getfPseudo().getText();
		String motDePasse = new String(this.fenetre.getVueDeConnexion().getfMotDePasse().getPassword());
		// if(bonMDP(nom, motDePasse)){
		if (true) {
			fenetre.getFenetre().setContentPane(new VuePrincipale(this.fenetre));
			fenetre.getFenetre().setExtendedState(JFrame.MAXIMIZED_BOTH);
			fenetre.getFenetre().setTitle("Editeur de base de données");
			fenetre.getFenetre().setLocationRelativeTo(null);
			fenetre.getFenetre().setVisible(true);
			fenetre.setSesstion(new Session(nom));
		} else {
			this.fenetre.getVueDeConnexion().getlErreurIdentifiant()
					.setText("<HTML><i>Erreur d'identifiant</i></HTML>");
			;
			fenetre.getFenetre().setVisible(true);
			fenetre.getFenetre().setLocationRelativeTo(null);
		}
	}

	public void creationUtilisateur() {
		JOptionPane jop = new JOptionPane();
		if (this.fenetre.getVueCreationUtilisateur().isvUtilisateur()
				&& this.fenetre.getVueCreationUtilisateur().isvEmail()
				&& this.fenetre.getVueCreationUtilisateur().isvMotDePasse()
				&& this.fenetre.getVueCreationUtilisateur().isvConfirmation()) {

			String entre = jop.showInputDialog(null, "Veuillez entrer le code qui vous à été envoyé par mail",
					"Confirmation d'Email", JOptionPane.QUESTION_MESSAGE);
			if (entre != null) {
				if (entre.equals("")) {
					String nom = this.fenetre.getVueCreationUtilisateur().getfUtilisateur().getText();
					ELFichier.creerDossier(nom);
					ELFichier.setCle(nom + "/session", "user", nom);
					ELFichier.setCle(nom + "/session", "MDP", ELFichier.cryptMDP(
							new String(this.fenetre.getVueCreationUtilisateur().getfMotDePasse().getPassword())));
					ELFichier.setCle(nom + "/session", "email",
							this.fenetre.getVueCreationUtilisateur().getfEmail().getText());
					ELFichier.setCle(nom + "/session", "Q1",
							this.fenetre.getVueCreationUtilisateur().gettQ1().getText());
					ELFichier.setCle(nom + "/session", "Q2",
							this.fenetre.getVueCreationUtilisateur().gettQ2().getText());
					ELFichier.setCle(nom + "/session", "Q3",
							this.fenetre.getVueCreationUtilisateur().gettQ3().getText());
					VueDeConnexion vueCo = new VueDeConnexion(this.fenetre);
					this.fenetre.setVueDeConnexion(vueCo);
					fenetre.getFenetre().setContentPane(vueCo);
					fenetre.getFenetre().setVisible(true);
					fenetre.getFenetre().pack();
					fenetre.getFenetre().setLocationRelativeTo(null);
				}
			}
		}
	}
}
