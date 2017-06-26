package modele;

import java.sql.SQLException;
import java.util.ArrayList;

public class Table implements Cloneable {

	/**
	 * Le nom de la table
	 */
	private String nom;

	/**
	 * La liste des colonnes de la table
	 */
	private ArrayList<Colonne> listeColonnes;

	/**
	 * La base de donnée ou est la table
	 */
	private BaseDeDonnees BDD;

	/**
	 * Initialise le nom et la liste des colonnes
	 * 
	 * @param nom
	 *            Le nom de la table
	 */
	public Table(BaseDeDonnees BDD, String nom) {
		this.BDD = BDD;
		this.nom = nom;
		this.listeColonnes = new ArrayList<Colonne>();
	}

	/**
	 * Ajouter une colonne
	 * 
	 * @param colonne
	 *            La colonne à ajouter
	 */
	public void ajouterAttribut(Colonne colonne) {
		this.listeColonnes.add(colonne);
	}

	/**
	 * Inserer un tuple dans la table. Un seul tuples peut etre ajouté.
	 * 
	 * @param attributs
	 *            Liste des attributs
	 * @throws CustomException
	 */
	private void insererUnTuple(ArrayList<Colonne> attributs, boolean ajouterListe) throws CustomException {

		if (attributs.size() != this.listeColonnes.size()) {
			throw new CustomException("Erreur", "Le tuple est incomplet ou contient trop d'attribut");
		}

		for (Colonne colonne : attributs) {
			if (colonne.getListeValeurs().size() != 1) {
				throw new CustomException("Erreur", "Les tuples ne peuvent être inséré qu'un par un");
			}
		}

		for (int i = 0; i < attributs.size(); i++) {
			if (attributs.get(i).getListeValeurs().get(0) != null
					&& this.listeColonnes.get(i).getTypeDonnees() != attributs.get(i).getTypeDonnees()) {
				throw new CustomException("Erreur de type",
						"La valeur '" + attributs.get(i).getListeValeurs().get(0) + "'  est de type "
								+ attributs.get(i).getTypeDonnees() + " alors qu'il est attendu un type "
								+ this.listeColonnes.get(i).getTypeDonnees() + ".");
			}
		}

		this.BDD.getServeur().insererTuples(this.nom, attributs);
		if (ajouterListe) {
			for (int i = 0; i < attributs.size(); i++) {
				this.listeColonnes.get(i).getListeValeurs().add(attributs.get(i).getValue(0));
			}
		}

	}

	/**
	 * Méthode public qui va appeller inserUnTuple pour inserer un tuple
	 * 
	 * @param attributs
	 *            La liste des objects formant le tuple
	 * @throws CustomException
	 */
	public void insererTuple(ArrayList<Object> attributs, boolean ajouterListe) throws CustomException {

		ArrayList<Colonne> tuple = new ArrayList<Colonne>();

		for (Object obj : attributs) {
			if (obj == null) {
				Colonne<Integer> col = new Colonne<Integer>("colonne", null);
				col.ajouterValeur(null);
				tuple.add(col);
			} else if (Util.isInteger(obj.toString())) {
				Colonne<Integer> col = new Colonne<Integer>("colonne", TypeDonnee.INTEGER);
				col.ajouterValeur((int) obj);
				tuple.add(col);
			}

			else if (Util.isDouble(obj.toString())) {
				Colonne<Double> col = new Colonne<Double>("colonne", TypeDonnee.DOUBLE);
				col.ajouterValeur((double) obj);
				tuple.add(col);

			} else if (obj instanceof String && Util.isValidDate((String) obj)) {
				Colonne<String> col = new Colonne<String>("colonne", TypeDonnee.DATE);
				col.ajouterValeur((String) obj);
				tuple.add(col);
			} else if (obj instanceof String) {
				Colonne<String> col = new Colonne<String>("colonne", TypeDonnee.CHAR);
				col.ajouterValeur((String) obj);
				tuple.add(col);
			} else {
				throw new CustomException("Erreur", obj + " est de type incompatible pour être inséré dans un tuple");
			}
		}
		this.insererUnTuple(tuple, ajouterListe);

	}

	/**
	 * Supprimer un tuple avec son identifiant de clé primaire
	 * 
	 * @param id
	 *            La clé primaire
	 * @throws CustomException
	 */
	public void supprimerTupleById(Object id) throws CustomException {

		Util.log("Suppression de la ligne ayant comme id " + id + "...");

		this.BDD.getServeur().supprimerTupleById(this, id);
		int index = -1;
		int i = 0;
		for (Object valeur : this.getClePrimaire().getListeValeurs()) {
			if (valeur.equals(id)) {
				index = i;
			}
			i++;
		}
		if (index == -1) {
			throw new CustomException("Erreur", "Il n'existe pas de tuple avec '" + id + "' comme identifiant.");
		} else {
			for (Colonne colonne : this.getListeColonnes()) {
				colonne.getListeValeurs().remove(index);
			}
		}
		Util.log("Suppression de la ligne ayant comme id " + id + " effectué.");
	}

	/**
	 * Editer un tuple avec l'id du tuple, le nom de la colonne à modifier et la
	 * nouvelle valeur
	 * 
	 * @param id
	 *            L'identifiant PRIMARY KEY du tuple
	 * @param nomColonne
	 *            Le nom de la colonne ou la valeur est a modifier
	 * @param newValeur
	 *            La nouvelle valeur
	 * @throws CustomException
	 */
	public void editerTuple(Object id, String nomColonne, Object newValeur) throws CustomException {
		Util.log("Modification de la valeur du tuple ayant pour identifiant '" + id + "' de la colonne '" + nomColonne
				+ "' par la nouvelle valeur '" + newValeur + "'...");

		if (this.getColonne(nomColonne) == null) {
			throw new CustomException("Erreur de colonne",
					"La colonne ayant pour nom '" + nomColonne + "' n'existe pas");
		}
		if(newValeur != null){
			if (this.getColonne(nomColonne).getTypeDonnees() == TypeDonnee.DOUBLE && !Util.isDouble((String) newValeur)) {
				throw new CustomException("Erreur de type",
						"La valeur " + newValeur + " ne peut pas être inséré dans la colonne'" + nomColonne
								+ "'qui est de type " + this.getColonne(nomColonne).getTypeDonnees());
			} else if (this.getColonne(nomColonne).getTypeDonnees() == TypeDonnee.INTEGER
					&& !Util.isInteger((String) newValeur)) {
				throw new CustomException("Erreur de type", "La valeur " + newValeur + " ne peut pas être inséré dans la colonne'" + nomColonne
						+ "'qui est de type " + this.getColonne(nomColonne).getTypeDonnees());
			} else if (this.getColonne(nomColonne).getTypeDonnees() == TypeDonnee.DATE
					&& !Util.isValidDate((String) newValeur)) {
				throw new CustomException("Erreur de type", "La valeur " + newValeur + " ne peut pas être inséré dans la colonne'" + nomColonne
						+ "'qui est de type " + this.getColonne(nomColonne).getTypeDonnees());
			}
		}

		this.BDD.getServeur().editerTuple(this, id, nomColonne, newValeur);

		int index = -1;
		int i = 0;
		for (Object valeur : this.getClePrimaire().getListeValeurs()) {
			if (valeur.equals(id)) {
				index = i;
			}
			i++;
		}
		if (index == -1) {
			throw new CustomException("Erreur", "Il n'existe pas de tuple avec '" + id + "' comme identifiant.");
		} else {
			for (Colonne colonne : this.getListeColonnes()) {
				if (colonne.getNom().equals(nomColonne)) {
					colonne.getListeValeurs().set(index, newValeur);
				}
			}
		}
		Util.log("Modification de la valeur du tuple ayant pour identifiant '" + id + "' de la colonne '" + nomColonne
				+ "' par la nouvelle valeur '" + newValeur + "' effectué.");

	}

	/**
	 * Ajouter une contrainte a une colonne
	 * 
	 * @param colonne
	 *            La colonne concerné
	 * @param contrainte
	 *            La contrainte
	 */
	public void AjouterContrainte(int colonne, TypeContrainte contrainte) {

	}

	/**
	 * Récupérer une colonne à partir de son nom
	 * 
	 * @param nom
	 *            Le nom de la colonne à récupérer
	 * @return L'attribut de la colonne
	 */
	public Colonne getColonne(String nom) {

		Colonne ret = null;

		for (Colonne colonne : this.listeColonnes) {
			if (colonne.getNom().equals(nom)) {
				ret = colonne;
			}
		}
		return ret;
	}

	/**
	 * Récupérer le nom de la table
	 * 
	 * @return Le nom de la table
	 */
	public String getNom() {
		return this.nom;
	}

	/**
	 * Récupérer la liste des colonnes
	 * 
	 * @return la liste des colonnes
	 */
	public ArrayList<Colonne> getListeColonnes() {
		return this.listeColonnes;
	}

	/**
	 * Permet de récupérer la table qui contient la contrainte de PRIMARY KEY
	 * 
	 * @return La table qui contient la contrainte de PRIMARY KEY
	 */
	public Colonne getClePrimaire() {
		Colonne colonne = null;

		for (Colonne col : this.listeColonnes) {
			ArrayList<Contrainte> listeContraintes = col.getListeContraintes();
			for (Contrainte contrainte : listeContraintes) {
				if (contrainte.getContrainteType() == TypeContrainte.PRIMARYKEY) {
					colonne = col;
				}
			}
		}

		return colonne;
	}

	public void refreshTable() {
		this.BDD.getFenetre().getVuePrincipale().insererValeursDansTab(this.BDD.formatValeurs(this.nom),
				this.BDD.formatTitres(this.nom));
	}

	public void supprimerTable() throws CustomException {
		this.BDD.getServeur().supprimerTable(this.nom);
	}

	/**
	 * Récupérer un résumé d'une Table
	 * 
	 * @return Un résumé d'une Table
	 */
	public String toString() {
		String ret = "";
		ret += "\nDans la BDD: " + this.BDD.getNomBDD();
		ret += "\nNom: " + this.nom;
		ret += "\nListe colonne:";
		for (Colonne col : this.listeColonnes) {
			ret += "\n" + col.getNom();
		}

		return ret;
	}

	@Override
	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void setListeColonnes(ArrayList<Colonne> colonnes) {
		this.listeColonnes = colonnes;
	}

	public void ajouterColonneATableDejaExistente(Colonne col, Object defautValeur)
			throws SQLException, CustomException {
		this.BDD.getServeur().ajouterColonne(this.nom, defautValeur, col);
		this.ajouterAttribut(col);
	}

	public BaseDeDonnees getBDD() {
		return this.BDD;
	}

	public void modifierContraintes(ArrayList<Contrainte> contraintes, Colonne colonne)
			throws SQLException, CustomException {
		Colonne col = new Colonne<>(colonne.getNom(), colonne.getTypeDonnees());
		Contrainte ref = null;
		for (Contrainte c : contraintes) {
			if (c.getContrainteType() != TypeContrainte.REFERENCEKEY)
				col.ajouterContrainte(c);
			else
				ref = c;
		}
		this.BDD.getServeur().modifierContrainte(this.nom, col);
		colonne.getListeContraintes().clear();
		for (Contrainte c : contraintes) {
			if (c.getContrainteType() != TypeContrainte.REFERENCEKEY)
				colonne.ajouterContrainte(c);
		}
		if (ref != null) {
			this.BDD.getServeur().ajouterFKColExistente(this.nom, colonne.getNom(), ref);
			colonne.ajouterContrainte(ref);
		}
	}

	public void supprimerColonne(String nomColonne) throws SQLException, CustomException {
		this.BDD.getServeur().supprimerColonne(this.nom, nomColonne);
		for (int i = 0; i < this.listeColonnes.size(); i++) {
			if (this.listeColonnes.get(i).getNom().equals(nomColonne)) {
				this.listeColonnes.remove(i);
				return;
			}
		}
	}

}
