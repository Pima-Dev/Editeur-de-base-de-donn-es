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
import javax.swing.JRadioButton;

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

		if (e.getSource() instanceof JButton) {
			JButton bouton = (JButton) e.getSource();

			if (bouton.getName().equals("Connexion")) {
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
							ELFichier.setCle(nom + "/session", "MDP", ELFichier.cryptMDP(new String(
									this.fenetre.getVueCreationUtilisateur().getfMotDePasse().getPassword())));
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
			} else if (bouton.getName().equals("valider creation bdd")) {
				if(this.fenetre.getVueCreationBDD().getfNomBDD().getText().contains(" ")){
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
							while (!Util.isValidDate((String) s) && s != null  && !s.toString().equals(""))
								s = JOptionPane.showInputDialog(null,
										"Entrez une valeur de type '" + col.getTypeDonnees().getSQLType()
												+ "' (dd-MM-yyyy) pour \nl'attribut '" + col.getNom()
												+ "' ayant ces contraintes: " + contrainte,
										"Entrez un '" + col.getTypeDonnees().getSQLType() + "'",
										JOptionPane.QUESTION_MESSAGE);
							if (s == null)
								return;
							if(!s.toString().replaceAll(" ", "").equals(""))
								tuple.add(s);
							else
								tuple.add(null);
						}

						else if (col.getTypeDonnees() == TypeDonnee.INTEGER) {
							while (!Util.isInteger((String) s) && s != null  && !s.toString().equals(""))
								s = JOptionPane.showInputDialog(null,
										"Entrez une valeur de type '" + col.getTypeDonnees().getSQLType()
												+ "' pour \nl'attribut '" + col.getNom() + "' ayant ces contraintes: "
												+ contrainte,
										"Entrez un '" + col.getTypeDonnees().getSQLType() + "'",
										JOptionPane.QUESTION_MESSAGE);
							if (s == null)
								return;
							if(!s.toString().replaceAll(" ", "").equals(""))
								tuple.add(Integer.parseInt((String) s));
							else
								tuple.add(null);
						}

						else if (col.getTypeDonnees() == TypeDonnee.DOUBLE) {
							while (!Util.isDouble((String) s) && s != null  && !s.toString().equals(""))
								s = JOptionPane.showInputDialog(null,
										"Entrez une valeur de type '" + col.getTypeDonnees().getSQLType()
												+ "' pour \nl'attribut '" + col.getNom() + "' ayant ces contraintes: "
												+ contrainte,
										"Entrez un '" + col.getTypeDonnees().getSQLType() + "'",
										JOptionPane.QUESTION_MESSAGE);
							if (s == null)
								return;
							if(!s.toString().replaceAll(" ", "").equals(""))
								tuple.add(Double.parseDouble((String) s));
							else
								tuple.add(null);
						}

						else {
							while (s.toString().contains(" ") && s != null  && !s.toString().equals(""))
								s = JOptionPane.showInputDialog(null,
										"Entrez une valeur de type '" + col.getTypeDonnees().getSQLType()
												+ "' pour \nl'attribut '" + col.getNom() + "' ayant ces contraintes: "
												+ contrainte,
										"Entrez un '" + col.getTypeDonnees().getSQLType() + "'",
										JOptionPane.QUESTION_MESSAGE);
							if (s == null)
								return;
							if(!s.toString().replaceAll(" ", "").equals(""))
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
			
			else if(bouton.getName().equals("supprimer table")){
				int rep = JOptionPane.showConfirmDialog(null, "Voulez vous vraiment supprimer la table '"+this.fenetre.getVuePrincipale().getCurrentTable()+"' ?", "Supprimer la table '"+this.fenetre.getVuePrincipale().getCurrentTable()+"' ?", JOptionPane.CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
				if(rep == JOptionPane.OK_OPTION){
				}
			}

			else if (bouton.getName().equals("valider creation attribut")) {
				Table current = this.fenetre.getBDD().getTable(this.fenetre.getVuePrincipale().getCurrentTable());
				Table backup = (Table) current.clone();
				backup.setListeColonnes((ArrayList<Colonne>) current.getListeColonnes().clone());
				
				
				if (this.fenetre.getVueAjouterAttribut().gettNom().getText().isEmpty()) {
					new CustomException("Erreur", "Vous n'avez pas indiqué le nom de l'attribut.");
				} else {
					if (this.fenetre.getVueAjouterAttribut().gettNom().getText().contains(" ")){
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
							String referenceTable = this.fenetre.getVueAjouterAttribut().getReference()
									.getSelectedItem().toString();
							col.ajouterContrainte(new Contrainte(TypeContrainte.REFERENCEKEY,
									this.fenetre.getBDD().getTable(referenceTable)));
						}
						Object obj = " ";
						for (int i = 0; i < current.getClePrimaire()
								.getListeValeurs().size(); i++) {
							obj = " ";
							if (type.equals(TypeDonnee.INTEGER)) {
								while (!Util.isInteger((String) obj)&& obj != null  && !obj.toString().equals("")) {
									obj = JOptionPane.showInputDialog(null,
											"Entrez la " + i + "ème valeur de la colonne",
											"Entrez un " + type.getSQLType(), JOptionPane.QUESTION_MESSAGE);
								}
								if (obj == null)
									return;
								if(!obj.toString().replaceAll(" ", "").equals(""))
									col.ajouterValeur(Integer.parseInt((String)obj));
								else
									col.ajouterValeur(null);
							}
							else if (type.equals(TypeDonnee.DOUBLE)) {
								while (!Util.isDouble((String) obj) && obj != null  && !obj.toString().equals("")) {
									obj = JOptionPane.showInputDialog(null,
											"Entrez la " + i + "ème valeur de la colonne",
											"Entrez un " + type.getSQLType(), JOptionPane.QUESTION_MESSAGE);
								}
								if (obj == null)
									return;
								if(!obj.toString().replaceAll(" ", "").equals(""))
									col.ajouterValeur(Double.parseDouble((String)obj));
								else
									col.ajouterValeur(null);
							}
							else if (type.equals(TypeDonnee.DATE)) {
								while (!Util.isValidDate((String) obj) && obj != null  && !obj.toString().equals("")) {
									obj = JOptionPane.showInputDialog(null,
											"Entrez la " + i + "ème valeur de la colonne",
											"Entrez un " + type.getSQLType(), JOptionPane.QUESTION_MESSAGE);
								}
								if (obj == null)
									return;
								if(!obj.toString().replaceAll(" ", "").equals(""))	
									col.ajouterValeur(obj);
								else
									col.ajouterValeur(null);
							}
							else{
								if (obj == null)
									return;
								if(!obj.toString().replaceAll(" ", "").equals(""))
									col.ajouterValeur(obj);
								else
									col.ajouterValeur(null);
							}
						}
						current.ajouterAttribut(col);
						this.fenetre.getBDD().getServeur().supprimerTable(current.getNom());
						System.out.println("nombre colonnes :"+current.getListeColonnes().size());
						this.fenetre.getBDD().getServeur().creerTable(current.getNom(), current.getListeColonnes());
						this.fenetre.getVueAjouterAttribut().getFrame().dispose();
						current.refreshTable();
						
					} catch (CustomException e1) {
						new CustomException("Erreur", "Une erreur est survenu:\n"+e1.getMessage()+"\nTentative de recreation de la table sans le nouvelle attribut");
						try {
							this.fenetre.getBDD().remplacerTable(backup.getNom(), backup);
							if(this.fenetre.getBDD().tableExiste(backup.getNom()))
								this.fenetre.getBDD().getServeur().supprimerTable(backup.getNom());
							this.fenetre.getBDD().getServeur().creerTable(backup.getNom(), backup.getListeColonnes());
						} catch (CustomException e2) {
							new CustomException("Erreur fatale", "La table n'a pas pu être recréé et des données ont pû être supprimés\n"+e2.getMessage());
						}
						catch(SQLException e2){
							Util.logErreur(e2.getMessage());
						}
					}
				}
			} else if (bouton.getName().equals("OptionRecherche")) {
				if(fenetre.getVuePrincipale().getTable() != null){
					new VueRechercheAvance(fenetre);
					}else{
						JOptionPane.showMessageDialog(null, "Table inexistante", "Erreur", JOptionPane.WARNING_MESSAGE);
					}
			} else if (bouton.getName().equals("LancerRechercheAvance")) {
				((ModeleTable)fenetre.getVuePrincipale().getTable().getModel()).rechercher(fenetre.getVueRechercheAvance().getfRecherche().getText());
				fenetre.getVueRechercheAvance().getFenetreRechercheAvance().dispose();
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
				if(fenetre.getVuePrincipale().getDm() == null){
					JOptionPane.showMessageDialog(null, "Table inexistante", "Erreur", JOptionPane.ERROR_MESSAGE);
				}else{
					JFileChooser choix = new JFileChooser();
					choix.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					int retour = choix.showOpenDialog(new JFrame());
					if(retour == JFileChooser.APPROVE_OPTION) {
						((ModeleTable)fenetre.getVuePrincipale().getTable().getModel()).exporterPDF(choix.getSelectedFile().getAbsolutePath());
					} else {
						JOptionPane.showMessageDialog(null, "Le dossier n'a pas été choisi!", "Erreur", JOptionPane.ERROR_MESSAGE);
					}
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
}
