package modele;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.mysql.jdbc.CommunicationsException;
import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import com.mysql.jdbc.exceptions.MySQLSyntaxErrorException;

public class Serveur {

	/**
	 * Objet qui permet de réaliser la connexion et l'authentification à la base
	 * de données
	 */
	private Connection connect;

	/**
	 * Objet permettant de transmettre du code sql à la base de données
	 */
	private Statement stmt;

	/**
	 * Le nom d'utilisateur pour se connecter à la base
	 */
	private String nomUtilisateur;

	/**
	 * Le mot de passe pour se connecter à la base
	 */
	private String motDePasse;

	/**
	 * Le nom de la base de données
	 */
	private String nomBase;

	/**
	 * L'url où est hébergé la BDD ou "localhost" si hébergé en local
	 */
	private String url;

	/**
	 * L'object BaseDeDonnees
	 */
	private BaseDeDonnees BDD;

	/**
	 * Le port si la BDD est sur un serveur distant
	 */
	private int port;

	/**
	 * Constructeur du Serveur
	 * @param bdd La base de donnée qui sera stocké sur le serveur
	 */
	public Serveur(BaseDeDonnees bdd) {
		this.BDD = bdd;
		this.nomBase = this.BDD.getNomBDD();
		this.nomUtilisateur = this.BDD.getNomUtilisateur();
		this.motDePasse = this.BDD.getMotDePasse();
		this.url = this.BDD.getUrlBDD();
		this.port = this.BDD.getPort();
	}

	/**
	 * Permet de faire la connexion entre la base de données et l'application
	 * 
	 * @throws CustomException
	 */
	private void connexion(String base) throws CustomException, MySQLSyntaxErrorException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			if (this.url == null || this.url.equals(""))
				this.connect = DriverManager.getConnection("jdbc:mysql://localhost/" + base, this.nomUtilisateur,
						this.motDePasse);
			else
				this.connect = DriverManager.getConnection(
						"jdbc:mysql://" + this.url + ":" + this.port + "/" + base,
						this.nomUtilisateur, this.motDePasse);
			this.stmt = this.connect.createStatement();
		}

		catch (CommunicationsException e) {
			e.printStackTrace();
			throw new CustomException("Erreur", "Le serveur sql est introuvable.");
		}

		catch (SQLException e) {

			if (e.getMessage().contains("Unknown database")) {
				throw new CustomException("Erreur", "La base " + this.nomBase + " n'existe pas.");
			}

			else if (e.getMessage().contains("Access denied")) {
				throw new CustomException("Erreur", "Identifiant ou mot de passe incorrect.");
			} else {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Fermer la connexion sql
	 */
	public void fermerConnexion() {

		if (this.stmt != null) {
			try {
				this.stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if (this.connect != null) {
			try {
				this.connect.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Execute le code sql passé en paramètre si il peut compiler. Seulemement
	 * pour faire des actions sur les tables mais pas requêter
	 * 
	 * @param code
	 *            Le code à executer
	 * @throws SQLException Erreur
	 * @throws CustomException
	 *             Si syntaxe non correcte
	 */
	public void executerCode(String code) throws SQLException, CustomException {

		try {
			this.connexion(this.nomBase);
			this.stmt.executeUpdate(code);
		}

		catch (MySQLIntegrityConstraintViolationException e) {
			throw new CustomException("Erreur de contrainte",
					"1 tuple inséré ne respecte pas les contraintes de la table.\n" + e.getMessage());
		}

		catch (MySQLSyntaxErrorException e) {
			if (e.getMessage().contains("Table") && e.getMessage().contains("already exists")) {
				throw new CustomException("Erreur", "Ce nom de table est déjà utilisé.\n" + e.getMessage());
			} else if (e.getMessage().contains("Unknown table")) {
				throw new CustomException("Erreur", "Cette table n'existe pas.\n" + e.getMessage());
			} else {
				throw new CustomException("Erreur de syntaxe",
						"Le code SQL n'est pas correcte syntaxiquement. \n" + e.getMessage());
			}
		} finally {
			this.fermerConnexion();
		}
	}

	/**
	 * Executer une requête sql. La connexion sql doit être fermé après avoir lu
	 * le ResultSet
	 * 
	 * @param code
	 *            Le code sql à executer
	 * @return Le résultat de la requête
	 * @throws CustomException Erreur
	 * @throws SQLException Erreur
	 */
	public ResultSet executeRequete(String code) throws CustomException, SQLException {

		ResultSet ret = null;

		try {
			this.connexion(this.nomBase);
			ret = this.stmt.executeQuery(code);
		} catch (MySQLSyntaxErrorException e) {
			if (e.getMessage().contains("Table") && e.getMessage().contains("already exists")) {
				throw new CustomException("Erreur", "Ce nom de table est déjà utilisé.\n" + e.getMessage());
			} else if (e.getMessage().contains("Unknown table")) {
				throw new CustomException("Erreur", "Cette table n'existe pas.\n" + e.getMessage());
			} else {
				throw new CustomException("Erreur de syntaxe",
						"Le code SQL n'est pas correcte syntaxiquement. \n" + e.getMessage());
			}
		}

		return ret;
	}

	/**
	 * Creer une nouvelle base de donnée
	 * 
	 * @throws CustomException
	 *             Quand la table existe déjà
	 */
	public void creerBaseDeDonnees() throws CustomException {
		try {
			Util.log("Création de la base de données " + this.nomBase + "...");
			this.connexion("");
			this.stmt.executeUpdate("CREATE DATABASE " + this.nomBase);
			this.fermerConnexion();
			Util.log("Création de la base de données " + this.nomBase + " réalisé.");
		} catch (SQLException e) {
			if (e.getMessage().contains("database exists")) {
				throw new CustomException("Erreur", "La base " + this.nomBase + " existe déjà.");
			} else {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Supprimer une base de données
	 * 
	 * @throws CustomException
	 *             Quand la table n'existe pas
	 */
	public void supprimerBaseDeDonnes() throws CustomException {
		try {
			Util.log("Suppréssion de la BDD " + this.nomBase + "...");
			this.executerCode("DROP DATABASE " + this.nomBase);
			Util.log("Suppression de la BDD " + this.nomBase + " réalisé.");
		} catch (SQLException e) {
			if (e.getMessage().contains("database doesn't exist")) {
				throw new CustomException("Erreur", "La table " + this.nomBase + " n'existe pas.");
			} else {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Créer une table dans le serveur avec ses contraintes et ses valeurs
	 * @param table La table a créer
	 * @throws CustomException Erreur
	 */
	public void creerTable(Table table) throws CustomException {
		try {
			Util.log("Création de la table " + table.getNom() + "...");
			StringBuilder sqlCode = new StringBuilder(" (");
			StringBuilder foreignKey = new StringBuilder(",");
			for (Colonne colonne : table.getListeColonnes()) {
				sqlCode.append(colonne.getNom() + " " + colonne.getTypeDonnees().getSQLType());
				for (Contrainte contrainte : (ArrayList<Contrainte>) colonne.getListeContraintes()) {
					if (contrainte.getContrainteType() == TypeContrainte.PRIMARYKEY) {
						sqlCode.append(" " + contrainte.getContrainteType().getSQLContrainte());
					} else if (contrainte.getContrainteType() == TypeContrainte.NOTNULL) {
						sqlCode.append(" " + contrainte.getContrainteType().getSQLContrainte());
					} else if (contrainte.getContrainteType() == TypeContrainte.UNIQUE) {
						sqlCode.append(" " + contrainte.getContrainteType().getSQLContrainte());
					} else if (contrainte.getContrainteType() == TypeContrainte.REFERENCEKEY) {
						if (contrainte.getReferenceTable() != null) {
							foreignKey.append(" FOREIGN KEY(" + colonne.getNom() + ") REFERENCES "
									+ contrainte.getReferenceTable().getNom() + "("
									+ contrainte.getReferenceTable().getClePrimaire().getNom() + "),");
						}
					}
				}
				sqlCode.append(", ");
			}
			if (sqlCode.length() > 0) {
				sqlCode.deleteCharAt(sqlCode.length() - 2);
			}
			if (foreignKey.length() > 0) {
				foreignKey.deleteCharAt(foreignKey.length() - 1);
			}
			sqlCode.append(foreignKey);
			sqlCode.append(");");
			String sql = "CREATE TABLE " + table.getNom() + sqlCode;
			Util.logSqlCode(sql);
			this.executerCode(sql);

			if (table.getListeColonnes().size() > 0) {
				int size = table.getListeColonnes().get(0).getListeValeurs().size();
				for (int i = 0; i < size; i++) {
					ArrayList<Object> tuple = new ArrayList<Object>();
					for (Colonne col : table.getListeColonnes()) {
						tuple.add(col.getListeValeurs().get(i));
					}
					table.insererTuple(tuple, false);
				}
			}

			Util.log("Création de la table " + table.getNom() + " effectué.");
		} catch (SQLException e) {
			if (e.getMessage().contains("Cannot add foreign key")) {
				throw new CustomException("Erreur de contrainte", "Un tuple viole une contrainte.\n" + e.getMessage());
			}
			e.printStackTrace();
		}
	}

	/**
	 * Supprimer une table dans une base de données
	 * 
	 * @param nom
	 *            Nom de la table à supprimer
	 * @throws CustomException Erreur
	 */
	public void supprimerTable(String nom) throws CustomException {
		try {
			Util.log("Suppression de la table " + nom + "...");
			this.executerCode("DROP TABLE " + nom);
			Util.log("Suppression de la table " + nom + " réalisé.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Inserer des tuples dans une table
	 * 
	 * @param table
	 *            La table où les tuples seront ajoutés
	 * @param listeColonnes
	 *            La liste des colonnes formant les tuples
	 * @throws CustomException
	 *             Si il n'y a aucun tuples à ajouter ou si il n'y a pas le même
	 *             nombre de valeurs dans les colonnes
	 */
	public void insererTuples(String table, ArrayList<Colonne> listeColonnes) throws CustomException {

		if (listeColonnes == null || listeColonnes.size() <= 0) {
			throw new CustomException("Erreur", "Il n'y a aucune valeur à ajouter");
		}

		int nbValeurs = listeColonnes.get(0).getListeValeurs().size();

		for (Colonne colonne : listeColonnes) {
			if (colonne.getListeValeurs().size() != nbValeurs) {
				throw new CustomException("Erreur",
						"Toutes les colonnes doivent contenir un même nombre de valeurs à ajouter");
			}
		}

		ArrayList<String> valeurs = listeColonnes.get(0).getListeValeurs();

		StringBuilder sqlCode = new StringBuilder();
		Util.log("Insertion des tuples dans la Table " + table + "...");
		for (int i = 0; i < valeurs.size(); i++) {
			sqlCode = new StringBuilder("INSERT INTO " + table + " VALUES (");
			for (int j = 0; j < listeColonnes.size(); j++) {
				if (listeColonnes.get(j).getTypeDonnees() == null) {
					sqlCode.append("NULL, ");
				} else if (listeColonnes.get(j).getTypeDonnees() == TypeDonnee.INTEGER
						|| listeColonnes.get(j).getTypeDonnees() == TypeDonnee.DOUBLE) {
					sqlCode.append(listeColonnes.get(j).getListeValeurs().get(i) + ", ");
				} else if (listeColonnes.get(j).getTypeDonnees() == TypeDonnee.DATE) {
					sqlCode.append(
							"STR_TO_DATE('" + listeColonnes.get(j).getListeValeurs().get(i) + "', '%m-%d-%Y'), ");
				} else if (listeColonnes.get(j).getTypeDonnees() == TypeDonnee.CHAR) {
					sqlCode.append("'" + listeColonnes.get(j).getListeValeurs().get(i) + "', ");
				}
			}
			sqlCode.deleteCharAt(sqlCode.length() - 2);
			sqlCode.append(")");
			try {
				Util.logSqlCode(sqlCode.toString());
				this.executerCode(sqlCode.toString());
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		Util.log("Insertion des " + valeurs.size() + " tuples dans la table " + table + " effectué.");
	}

	/**
	 * Supprimer un tuple grâce à son identifiant
	 * 
	 * @param table
	 *            La table ou se situe le tuple à supprimer
	 * @param id
	 *            la clé primaire du tuple a supprimer
	 * @throws CustomException Erreur
	 * @throws SQLException Erreur
	 */
	public void supprimerTupleById(Table table, Object id) throws CustomException, SQLException {

		if (id instanceof String) {
			id = "'" + id + "'";
		}
		String sqlCode = "DELETE FROM " + table.getNom() + " WHERE " + table.getClePrimaire().getNom() + "=" + id;
		Util.logSqlCode(sqlCode);
		this.executerCode(sqlCode);
		Util.log("Suppression de la ligne ayant pour id '" + id + "' dans la table '" + table.getNom() + "' réalisé");
	}

	/**
	 * Editer une valeur d'un tuple
	 * 
	 * @param table
	 *            La tuple où se situe le tuple à modifier
	 * @param id
	 *            L'id du tuple à modifier
	 * @param nomColonne
	 *            Le nom de la colonne à modifier
	 * @param newValeur
	 *            La nouvelle valeur à insérer à la place de l'ancienne
	 * @throws CustomException Erreur
	 * @throws SQLException Erreur
	 */
	public void editerTuple(Table table, Object id, String nomColonne, Object newValeur) throws CustomException, SQLException {

		if (id instanceof String) {
			id = "'" + id + "'";
		}
		if (newValeur instanceof String) {
			newValeur = "'" + newValeur + "'";
		}
		String sqlCode = "UPDATE " + table.getNom() + " SET " + nomColonne + " =" + newValeur + " WHERE "
				+ table.getClePrimaire().getNom() + "=" + id;
		Util.logSqlCode(sqlCode);
		this.executerCode(sqlCode);
		Util.log("Edition de l'attribut ayant pour id '"+id+"' de la colonne '"+nomColonne+"' réalisé.");
	}

	/**
	 * Récupérer la liste des tables présentent dans la base de données
	 * 
	 * @return la liste des tables présentent dans la base de données
	 * @throws CustomException Erreur
	 * @throws SQLException Erreur
	 */
	public ArrayList<Table> getListeTables() throws CustomException, SQLException {
		ArrayList<Table> ret = new ArrayList<Table>();

		ResultSet rs = this.executeRequete("SHOW TABLES from " + this.nomBase);

		while (rs.next()) {
			String nomTable = rs.getString("Tables_in_" + this.nomBase);
			Table table = new Table(this.BDD, nomTable);
			ArrayList<Colonne> listeColonnes = this.getListeColonnes(nomTable);
			for (Colonne col : listeColonnes) {
				table.ajouterAttribut(col);
			}
			ret.add(table);
		}
		this.BDD.setListeTables(ret);
		this.ajouterFK();
		this.fermerConnexion();

		return ret;
	}

	/**
	 * Récupérer la liste des Colonne présentent dans une table
	 * 
	 * @param nomTable
	 *            Table Le nom de la table ou récupérer les colonnes
	 * @return la liste des Colonne présentent dans une table
	 * @throws CustomException Erreur
	 * @throws SQLException Erreur
	 */
	public ArrayList<Colonne> getListeColonnes(String nomTable) throws CustomException, SQLException {
		ArrayList<Colonne> ret = new ArrayList<Colonne>();

		ResultSet rs = this.executeRequete("SHOW COLUMNS from " + nomTable);

		while (rs.next()) {
			String nom = rs.getString("Field");
			TypeDonnee type = rs.getString("Type").contains("int") ? TypeDonnee.INTEGER
					: (rs.getString("Type").contains("double") ? TypeDonnee.DOUBLE
							: (rs.getString("Type").contains("date") ? TypeDonnee.DATE : TypeDonnee.CHAR));
			Colonne colonne = new Colonne(nom, type);
			if (rs.getString("Null").equalsIgnoreCase("NO") && !rs.getString("Key").equalsIgnoreCase("PRI")) {
				colonne.ajouterContrainte(new Contrainte(TypeContrainte.NOTNULL, null));
			}
			if (rs.getString("Key").equalsIgnoreCase("UNI")) {
				colonne.ajouterContrainte(new Contrainte(TypeContrainte.UNIQUE, null));
			} else if (rs.getString("Key").equalsIgnoreCase("PRI")) {
				colonne.ajouterContrainte(new Contrainte(TypeContrainte.PRIMARYKEY, null));
			}
			colonne.setListeValeurs(this.getListeValeurs(nomTable, colonne.getNom()));
			ret.add(colonne);
		}
		this.fermerConnexion();
		return ret;
	}

	/**
	 * Récupérer la liste des valeurs d'une colonne d'une table
	 * 
	 * @param nomTable
	 *            Le nom de la table ou se situe la colonne
	 * @param nomColonne
	 *            Le nom de la colonne où se situe les valeurs
	 * @return la liste des valeurs d'une colonne d'une table
	 * @throws CustomException Erreur
	 * @throws SQLException Erreur
	 */
	private ArrayList<Object> getListeValeurs(String nomTable, String nomColonne) throws CustomException, SQLException {
		ArrayList<Object> ret = new ArrayList<Object>();

		ResultSet rs = this.executeRequete("SELECT " + nomColonne + " FROM " + nomTable);

		while (rs.next()) {
			ret.add(rs.getObject(nomColonne));
		}
		return ret;
	}

	/**
	 * Ajouter les contraintes de clés étrangères présentent dans une BDD
	 * 
	 * @throws CustomException Erreur
	 * @throws SQLException Erreur
	 */
	private void ajouterFK() throws CustomException, SQLException {

		ResultSet rs = this.executeRequete(
				"SELECT CONCAT(table_name, '.', column_name) AS 'foreign key', CONCAT(referenced_table_name, '.', referenced_column_name) AS 'references' FROM information_schema.key_column_usage WHERE referenced_table_name IS NOT NULL  AND TABLE_SCHEMA = '"
						+ this.BDD.getNomBDD() + "';");

		while (rs.next()) {
			String nomTable = rs.getString("foreign key").split("\\.")[0];
			String nomColonne = rs.getString("foreign key").split("\\.")[1];
			Colonne colonne = this.BDD.getTable(nomTable).getColonne(nomColonne);
			String referenceTable = rs.getString("references").split("\\.")[0];
			colonne.ajouterContrainte(new Contrainte(TypeContrainte.REFERENCEKEY, this.BDD.getTable(referenceTable)));
		}
		this.fermerConnexion();
	}

	/**
	 * Récupérer le nom de toutes les BDD du serveur
	 * 
	 * @return le nom de toutes les BDD du serveur
	 * @throws CustomException Erreur
	 */
	public ArrayList<String> getListeBDD() throws CustomException {
		ArrayList<String> ret = new ArrayList<String>();
		try {
			this.connexion("");
			ResultSet rs = this.stmt.executeQuery("SHOW DATABASES");
			while (rs.next()) {
				if (!rs.getString("Database").equals("information_schema") && !rs.getString("Database").equals("mysql")
						&& !rs.getString("Database").equals("performance_schema")
						&& !rs.getString("Database").equals("sys")) {
					ret.add(rs.getString("Database"));
				}
			}
			this.fermerConnexion();
		} catch (SQLException e) {
			if (e.getMessage().contains("database exists")) {
				throw new CustomException("Erreur", "La base " + this.nomBase + " existe déjà.");
			} else {
				e.printStackTrace();
			}
		}

		return ret;
	}

	/**
	 * Permet de savoir si une table existe
	 * 
	 * @param table
	 *            La table à tester
	 * @return True si la table existe dans la BDD
	 * @throws CustomException Erreur
	 * @throws SQLException Erreur
	 */
	public boolean tableExiste(String table) throws CustomException, SQLException {
		ResultSet rs = this.executeRequete("SHOW TABLES LIKE '" + table + "'");
		if (rs.next()) {
			return true;
		}
		return false;
	}

	/**
	 * Savoir si la BDD est créé dans le serveur
	 * 
	 * @return True si la BDD est créé
	 * @throws CustomException Erreur
	 * @throws SQLException Erreur
	 */
	public boolean bddExiste() throws CustomException, SQLException {
		ResultSet rs = null;

		try {
			this.connexion("");
			rs = this.stmt.executeQuery("SHOW DATABASES LIKE '" + this.BDD.getNomBDD() + "'");
			if (rs.next()) {
				return true;
			}
			this.fermerConnexion();
		} catch (SQLException e) {
			return false;
		}
		return false;
	}

	/**
	 * Ajouter une colonne à une table de la BDD
	 * 
	 * @param nomTable
	 *            Le nom de la table où la colonne sera ajouté
	 * @param defautValeur
	 *            La valeur par defaut qui sera ajouté pour chaque ligne de la
	 *            colonne
	 * @param colonne
	 *            La colonne à ajouter avec ses contraintes
	 * @throws SQLException Erreur
	 * @throws CustomException Erreur
	 */
	public void ajouterColonne(String nomTable, Object defautValeur, Colonne colonne)
			throws SQLException, CustomException {
		TypeDonnee type;
		TypeDonnee typeDonnee = colonne.getTypeDonnees();
		String nomColonne = colonne.getNom();
		if (defautValeur != null) {
			type = Util.isInteger(defautValeur.toString()) ? TypeDonnee.INTEGER
					: (Util.isDouble(defautValeur.toString()) ? TypeDonnee.DOUBLE
							: (Util.isValidDate(defautValeur.toString()) ? TypeDonnee.DATE : TypeDonnee.CHAR));
			if (typeDonnee != type) {
				throw new CustomException("Erreur",
						"Le type '" + type + "' n'est pas du même type que '" + typeDonnee + "'.");
			}
		} else
			type = null;

		if (this.BDD.getTable(nomTable) == null) {
			throw new CustomException("Erreur", "La table '" + nomColonne + "' n'existe pas et la colonne '" + nomTable
					+ "' ne peut pas être ajouté.");
		}

		StringBuilder sqlCode = new StringBuilder(" ");
		StringBuilder foreignKey = new StringBuilder(", ADD ");
		for (Contrainte contrainte : (ArrayList<Contrainte>) colonne.getListeContraintes()) {
			if (contrainte.getContrainteType() == TypeContrainte.NOTNULL) {
				sqlCode.append(" " + contrainte.getContrainteType().getSQLContrainte());
			} else if (contrainte.getContrainteType() == TypeContrainte.UNIQUE) {
				sqlCode.append(" " + contrainte.getContrainteType().getSQLContrainte());
			} else if (contrainte.getContrainteType() == TypeContrainte.REFERENCEKEY) {
				if (contrainte.getReferenceTable() != null) {
					foreignKey.append(" FOREIGN KEY(" + colonne.getNom() + ") REFERENCES "
							+ contrainte.getReferenceTable().getNom() + "("
							+ contrainte.getReferenceTable().getClePrimaire().getNom() + "),");
				}
			}
		}
		sqlCode.append(", ");
		if (sqlCode.length() > 0) {
			sqlCode.deleteCharAt(sqlCode.length() - 2);
		}
		if (foreignKey.length() > 0) {
			foreignKey.deleteCharAt(foreignKey.length() - 1);
		}
		if (foreignKey.length() > 5)
			sqlCode.append(foreignKey);

		if(defautValeur instanceof String){
			defautValeur = "'"+defautValeur+"'";
		}
		
		if (type != null) {
			Util.logSqlCode("ALTER TABLE " + this.BDD.getTable(nomTable).getNom() + " ADD COLUMN " + nomColonne + " "
					+ typeDonnee.getSQLType() + " DEFAULT " + defautValeur + " " + sqlCode);
			this.executerCode("ALTER TABLE " + this.BDD.getTable(nomTable).getNom() + " ADD COLUMN " + nomColonne + " "
					+ typeDonnee.getSQLType() + " DEFAULT " + defautValeur + " " + sqlCode);
		} else {
			Util.logSqlCode("ALTER TABLE " + this.BDD.getTable(nomTable).getNom() + " ADD COLUMN " + nomColonne + " "
					+ typeDonnee.getSQLType() + " DEFAULT NULL" + sqlCode);
			this.executerCode("ALTER TABLE " + this.BDD.getTable(nomTable).getNom() + " ADD COLUMN " + nomColonne + " "
					+ typeDonnee.getSQLType() + " DEFAULT NULL" + sqlCode);
		}
		Util.log("Ajout de la colonne '"+nomColonne+"' réalisé.");
	}

	/**
	 * Modifier les contraintes d'une colonne
	 * 
	 * @param nomTable
	 *            Le nom de la table où modifier la colonne
	 * @param colonne
	 *            Les contraintes de la colonne qui seront remplacés
	 * @throws SQLException Erreur
	 * @throws CustomException Erreur
	 */
	public void modifierContrainte(String nomTable, Colonne colonne) throws SQLException, CustomException {
		StringBuilder sqlCode = new StringBuilder();
		for (Contrainte contrainte : (ArrayList<Contrainte>) colonne.getListeContraintes()) {
			if (contrainte.getContrainteType() == TypeContrainte.NOTNULL) {
				sqlCode.append(" " + contrainte.getContrainteType().getSQLContrainte());
			} else if (contrainte.getContrainteType() == TypeContrainte.UNIQUE) {
				sqlCode.append(" " + contrainte.getContrainteType().getSQLContrainte());
			}
		}
		this.executerCode("ALTER TABLE " + nomTable + " MODIFY " + colonne.getNom() + " "
				+ colonne.getTypeDonnees().getSQLType() + " " + sqlCode);
		Util.log("Modification de la contrainte de la colonne '"+colonne.getNom()+"' réalisé.");
	}

	/**
	 * Ajouter une clé étrangère à une colonne existente
	 * 
	 * @param nomTable
	 *            Le nom de la table où se situe la colonne
	 * @param nomColonne
	 *            Le nom de la colonne où il faut ajouter une clé étrangère
	 * @param contrainte
	 *            La contrainte de clé étrangère
	 * @throws SQLException Erreur
	 * @throws CustomException Erreur
	 */
	public void ajouterFKColExistente(String nomTable, String nomColonne, Contrainte contrainte)
			throws SQLException, CustomException {

		if (contrainte != null && contrainte.getContrainteType() == TypeContrainte.REFERENCEKEY) {
			if (contrainte.getReferenceTable() == null) {
				throw new CustomException("Erreur",
						"La table à référencer n'existe pas, impossible de rajouter la clé étrangère");
			}
			String sqlCode = "ALTER TABLE " + nomTable + " ADD FOREIGN KEY(" + nomColonne + ") REFERENCES "
					+ contrainte.getReferenceTable().getNom() + "("
					+ contrainte.getReferenceTable().getClePrimaire().getNom() + ")";
			Util.logSqlCode(sqlCode);
			this.executerCode(sqlCode);
		} else {
			throw new CustomException("Erreur", "La contrainte à ajouter est null ou n'est pas une clé étrangère");
		}
	}

	/**
	 * Supprimer une colonne d'une tabme
	 * 
	 * @param nomTable
	 *            Le nom de la table où supprimer la colonne
	 * @param nomColonne
	 *            Le nom de la colonne à supprimer
	 * @throws SQLException Erreur
	 * @throws CustomException Erreur
	 */
	public void supprimerColonne(String nomTable, String nomColonne) throws SQLException, CustomException {
		this.executerCode("ALTER TABLE " + nomTable + " DROP COLUMN " + nomColonne);
		Util.log("Suppression de la colonne '"+nomColonne+"' réalisé.");
	}

	/**
	 * Executer un code SQL par l'utilisateur dans la console
	 * 
	 * @param code
	 *            Le code de l'utilisateur à executer
	 */
	public void executerCodeConsole(String code) {

		try {
			this.connexion(this.nomBase);
			this.stmt.executeUpdate(code);
			Util.logSqlCode(code);
			Util.log("Action bien executé");
		}

		catch (MySQLIntegrityConstraintViolationException e) {
			Util.logErreur(e.getMessage());
		}

		catch (MySQLSyntaxErrorException e) {
			Util.logErreur(e.getMessage());
		} catch (CustomException e) {
			Util.logErreur(e.getMessage());
		} catch (SQLException e) {
			Util.logErreur(e.getMessage());
		} finally {
			this.fermerConnexion();
		}
	}

	/**
	 * Executer une requete par l'utilisateur dans la console
	 * 
	 * @param code
	 *            La requete que l'utilisateur va executer
	 * @return Le ResultSet de la requete
	 */
	public ResultSet executeRequeteConsole(String code) {

		ResultSet ret = null;

		try {
			this.connexion(this.nomBase);
			ret = this.stmt.executeQuery(code);
			Util.logSqlCode(code);
			ResultSetMetaData rsmd = ret.getMetaData();
			int columnsNumber = rsmd.getColumnCount();

			while (ret.next()) {
				StringBuilder print = new StringBuilder();
				for (int i = 1; i < columnsNumber; i++)
					print.append(ret.getString(i) + " ");
				Util.logSqlCode(print.toString());
				Util.log("");
			}
		} catch (MySQLIntegrityConstraintViolationException e) {
			Util.logErreur(e.getMessage());
		}

		catch (MySQLSyntaxErrorException e) {
			Util.logErreur(e.getMessage());
		} catch (CustomException e) {
			Util.logErreur(e.getMessage());
		} catch (SQLException e) {
			Util.logErreur(e.getMessage());
		}

		return ret;
	}

	/**
	 * Renvoie la valeur contenue dans une colonne avec un certains id de ligne
	 * 
	 * @param table
	 *            La table où se trouve la colonne
	 * @param id
	 *            L'identifiant de la ligne
	 * @param nomColonne
	 *            Le nom de la colonne
	 * @return La valeur identifié par l'id et le nom de la colonne
	 * @throws CustomException Erreur
	 * @throws SQLException Erreur
	 */
	public Object getValeurAt(Table table, Object id, String nomColonne) throws CustomException, SQLException {
		Object ret = null;

		ResultSet rs = this.executeRequete("SELECT " + nomColonne + " FROM " + table.getNom() + " WHERE "
				+ table.getClePrimaire().getNom() + "=" + id);
		if (rs.next())
			ret = rs.getObject(nomColonne);
		return ret;
	}

	/**
	 * 
	 * @return L'object BDD
	 */
	public BaseDeDonnees getBDD() {
		return this.BDD;
	}

	/**
	 * 
	 * @return L'object connect
	 */
	public Connection getConnect() {
		return connect;
	}

	/**
	 * 
	 * @return L'object Statement
	 */
	public Statement getStmt() {
		return stmt;
	}

	/**
	 * 
	 * @return Le nom de l'utilisateur du serveur
	 */
	public String getNomUtilisateur() {
		return nomUtilisateur;
	}

	/**
	 * 
	 * @return Le mot de passe du serveur
	 */
	public String getMotDePasse() {
		return motDePasse;
	}

	/**
	 * 
	 * @return Le nom de la BDD
	 */
	public String getNomBase() {
		return nomBase;
	}

	/**
	 * 
	 * @return L'url du serveur
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * 
	 * @return Le port du serveur
	 */
	public int getPort() {
		return port;
	}
}
