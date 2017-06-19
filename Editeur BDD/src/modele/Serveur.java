package modele;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import com.mysql.jdbc.CommunicationsException;
import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import com.mysql.jdbc.exceptions.MySQLSyntaxErrorException;

public class Serveur {
	
	/**
	 * Objet qui permet de réaliser la connexion et l'authentification à la base de données
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
	 * Initialise les informartions de connexion
	 * @param nomUtilisateur Le nom de l'utilisateur
	 * @param motDePasse Le mot de passe
	 */
	public Serveur(BaseDeDonnees bdd){
		this.BDD = bdd;
		this.nomBase = this.BDD.getNomBDD();
		this.nomUtilisateur = this.BDD.getNomUtilisateur();
		this.motDePasse = this.BDD.getMotDePasse();
		this.url = this.BDD.getUrlBDD();
		this.port = this.BDD.getPort();
	}
	
	/**
	 * Permet de faire la connexion entre la base de données et l'application
	 * @throws CustomException 
	 */
	private void connexion(String base) throws CustomException, MySQLSyntaxErrorException{
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			if(this.url == null || this.url.equals(""))
				this.connect = DriverManager.getConnection("jdbc:mysql://localhost/"+base, this.nomUtilisateur, this.motDePasse);
			else
				this.connect = DriverManager.getConnection("jdbc:mysql://"+this.url+":"+this.port+"/"+base+":"+this.port, this.nomUtilisateur, this.motDePasse);
			this.stmt = this.connect.createStatement();
		}
		
		catch(CommunicationsException e){
			throw new CustomException("Erreur", "Le serveur sql est introuvable.");
		}
		
		catch(SQLException e){
			
			if(e.getMessage().contains("Unknown database")){
				throw new CustomException("Erreur", "La base "+this.nomBase+" n'existe pas.");
			}
			
			else if(e.getMessage().contains("Access denied")){
				throw new CustomException("Erreur", "Identifiant ou mot de passe incorrect.");
			}
			else{
				e.printStackTrace();
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Fermer la connexion sql
	 */
	public void fermerConnexion(){
		
		if(this.stmt != null){
			try {
				this.stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		if(this.connect != null){
			try {
				this.connect.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}	
	}
	
	
	/**
	 * Execute le code sql passé en paramètre si il peut compiler. Seulemement pour faire des actions sur les tables mais pas requêter
	 * @param code Le code à executer
	 * @throws SQLException 
	 * @throws CustomException Si syntaxe non correcte
	 */
	public void executerCode(String code) throws SQLException, CustomException{
		
		try{
			this.connexion(this.nomBase);
			this.stmt.executeUpdate(code);
		}
		
		catch(MySQLIntegrityConstraintViolationException e){
			throw new CustomException("Erreur de contrainte", "1 tuple inséré ne respecte pas les contraintes de la table.\n"+e.getMessage());
		}
		
		catch(MySQLSyntaxErrorException e){
			if(e.getMessage().contains("Table") && e.getMessage().contains("already exists")){
				throw new CustomException("Erreur", "Ce nom de table est déjà utilisé.\n"+e.getMessage());
			}
			else if(e.getMessage().contains("Unknown table")){
				throw new CustomException("Erreur", "Cette table n'existe pas.\n"+e.getMessage());
			}
			else{
				throw new CustomException("Erreur de syntaxe", "Le code SQL n'est pas correcte syntaxiquement. \n"+e.getMessage());
			}
		}
		finally{
			this.fermerConnexion();
		}
	}
	
	/**
	 * Executer une requête sql. La connexion sql doit être fermé après avoir lu le ResultSet
	 * @param code Le code sql à executer
	 * @return Le résultat de la requête
	 * @throws CustomException
	 * @throws SQLException
	 */
	public ResultSet executeRequete(String code) throws CustomException, SQLException{
		
		ResultSet ret = null;
		
		try {
			this.connexion(this.nomBase);
			ret = this.stmt.executeQuery(code);
		} catch (MySQLSyntaxErrorException e) {
			if(e.getMessage().contains("Table") && e.getMessage().contains("already exists")){
				throw new CustomException("Erreur", "Ce nom de table est déjà utilisé.\n"+e.getMessage());
			}
			else if(e.getMessage().contains("Unknown table")){
				throw new CustomException("Erreur", "Cette table n'existe pas.\n"+e.getMessage());
			}
			else{
				throw new CustomException("Erreur de syntaxe", "Le code SQL n'est pas correcte syntaxiquement. \n"+e.getMessage());
			}
		}
		
		return ret;
	}
	
	/**
	 * Importe toutes les données contenue dans la base de données et les met dans l'objet BaseDeDonnees
	 * @throws CustomException 
	 */
	public void importerDepuisBaseDeDonnees() throws CustomException{
		
	}
	 
	/**
	 * Creer une nouvelle base de donnée
	 * @throws CustomException Quand la table existe déjà
	 */
	public void creerBaseDeDonnees() throws CustomException{
		try {
			Util.log("Création de la base de données "+this.nomBase+"...");
			this.connexion("");
			this.stmt.executeUpdate("CREATE DATABASE "+this.nomBase);
			this.fermerConnexion();
			Util.log("Création de la base de données "+this.nomBase+" réalisé.");
		} catch (SQLException e) {
			if(e.getMessage().contains("database exists")){
				throw new CustomException("Erreur", "La base "+this.nomBase+" existe déjà.");
			}
			else{
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Supprimer une base de données
	 * @throws CustomException Quand la table n'existe pas
	 */
	public void supprimerBaseDeDonnes() throws CustomException {
		try {
			Util.log("Suppréssion de la BDD "+this.nomBase+"...");
			this.executerCode("DROP DATABASE "+this.nomBase);
			Util.log("Suppression de la BDD "+this.nomBase+" réalisé.");
		} catch (SQLException e) {
			if(e.getMessage().contains("database doesn't exist")){
				throw new CustomException("Erreur", "La table "+this.nomBase+" n'existe pas.");
			}
			else{
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Créer une table dans une base de données
	 * @param nom Nom de la table à créeer
	 * @param id Nom de l'identifiant de la table
	 * @throws CustomException
	 */
	public void creerTable(String nom, ArrayList<Colonne> colonnes) throws CustomException{
		try {
			Util.log("Création de la table "+nom+"...");
			StringBuilder sqlCode = new StringBuilder(" (");
			StringBuilder foreignKey = new StringBuilder(",");
			for(Colonne colonne : colonnes){
				sqlCode.append(colonne.getNom()+" "+colonne.getTypeDonnees().getSQLType());
				for (Contrainte contrainte : (ArrayList<Contrainte>)colonne.getListeContraintes()){
					if(contrainte.getContrainteType() == TypeContrainte.PRIMARYKEY){
						sqlCode.append(" "+contrainte.getContrainteType().getSQLContrainte());
					}
					else if(contrainte.getContrainteType() == TypeContrainte.NOTNULL){
						sqlCode.append(" "+contrainte.getContrainteType().getSQLContrainte());
					}
					else if(contrainte.getContrainteType() == TypeContrainte.UNIQUE){
						sqlCode.append(" "+contrainte.getContrainteType().getSQLContrainte());
					}
					else if(contrainte.getContrainteType() == TypeContrainte.REFERENCEKEY){
						if(contrainte.getReferenceTable() != null){
							foreignKey.append(" FOREIGN KEY("+colonne.getNom()+") REFERENCES "+contrainte.getReferenceTable().getNom()+"("+contrainte.getReferenceTable().getClePrimaire().getNom()+"),");
						}
					}
				}
				sqlCode.append(", ");
			}
			if(sqlCode.length() > 0){
				sqlCode.deleteCharAt(sqlCode.length()-2);
			}
			if(foreignKey.length() >0){
				foreignKey.deleteCharAt(foreignKey.length()-1);
			}
			sqlCode.append(foreignKey);
			sqlCode.append(");");
			String sql = "CREATE TABLE "+nom +sqlCode;
			this.executerCode(sql);
			Util.logSqlCode(sql);
			Util.log("Création de la table "+nom+" effectué.");
		} 
		catch (SQLException e) {
			if(e.getMessage().contains("Cannot add foreign key")){
				throw new CustomException("Erreur de contrainte", "Un tuple viole un contrainte.\n"+e.getMessage());
			}
			e.printStackTrace();
		}
	}
	
	/**
	 * Supprimer une table dans une base de données
	 * @param nom Nom de la table à supprimer 
	 * @throws CustomException 
	 */
	public void supprimerTable(String nom) throws CustomException{
		try {
			Util.log("Suppression de la table "+nom+"...");
			this.executerCode("DROP TABLE "+nom);
			Util.log("Suppression de la table "+nom+" réalisé.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Inserer des tuples dans une table
	 * @param table La table où les tuples seront ajoutés
	 * @param listeColonnes La liste des colonnes formant les tuples
	 * @throws CustomException Si il n'y a aucun tuples à ajouter ou si il n'y a pas le même nombre de valeurs dans les colonnes
	 */
	public void insererTuples(String table, ArrayList<Colonne> listeColonnes) throws CustomException{
		
		if(listeColonnes == null || listeColonnes.size() <=0){
			throw new CustomException("Erreur", "Il n'y a aucune valeur à ajouter");
		}
		
		int nbValeurs = listeColonnes.get(0).getListeValeurs().size();
		
		for(Colonne colonne : listeColonnes){
			if(colonne.getListeValeurs().size() != nbValeurs){
				throw new CustomException("Erreur", "Toutes les colonnes doivent contenir un même nombre de valeurs à ajouter");
			}
		}

		ArrayList<String> valeurs = listeColonnes.get(0).getListeValeurs();
		
		StringBuilder sqlCode = new StringBuilder();
		Util.log("Insertion des tuples dans la Table "+table+"...");
		for(int i = 0; i<valeurs.size(); i++){
			sqlCode = new StringBuilder("INSERT INTO "+table+ " VALUES (");
			for(int j = 0; j<listeColonnes.size(); j++){
				if(listeColonnes.get(j).getTypeDonnees() == TypeDonnee.INTEGER || listeColonnes.get(j).getTypeDonnees() == TypeDonnee.DOUBLE){
					sqlCode.append(listeColonnes.get(j).getListeValeurs().get(i)+", ");
				} 
				else if(listeColonnes.get(j).getTypeDonnees() == TypeDonnee.DATE){
					sqlCode.append("STR_TO_DATE('"+listeColonnes.get(j).getListeValeurs().get(i)+"', '%m-%d-%Y'), ");
				}
				else if(listeColonnes.get(j).getTypeDonnees() == TypeDonnee.CHAR){
					sqlCode.append("'"+listeColonnes.get(j).getListeValeurs().get(i)+"', ");
				}
			}
			sqlCode.deleteCharAt(sqlCode.length()-2);
			sqlCode.append(")");
			try {
				Util.logSqlCode(sqlCode.toString());
				this.executerCode(sqlCode.toString());
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		Util.log("Insertion des "+valeurs.size()+" tuples dans la table "+table+" effectué.");
	}
	
	/**
	 * Supprimer un tuple grâce à son identifiant
	 * @param table La table ou se situe le tuple à supprimer
	 * @param id la clé primaire du tuple a supprimer
	 * @throws CustomException
	 */
	public void supprimerTupleById(Table table, Object id) throws CustomException{
		try {
			if(id instanceof String){
				id = "'"+id+"'";
			}
			String sqlCode = "DELETE FROM "+table.getNom()+" WHERE "+table.getClePrimaire().getNom()+"="+id;
			this.executerCode(sqlCode);
			Util.log(sqlCode);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Editer une valeur d'un tuple
	 * @param table La tuple où se situe le tuple à modifier
	 * @param id L'id du tuple à modifier
	 * @param nomColonne Le nom de la colonne à modifier
	 * @param newValeur La nouvelle valeur à insérer à la place de l'ancienne
	 * @throws CustomException
	 */
	public void editerTuple(Table table, Object id, String nomColonne, Object newValeur) throws CustomException{
		try {
			if(id instanceof String){
				id = "'"+id+"'";
			}
			if(newValeur instanceof String){
				newValeur = "'"+newValeur+"'";
			}
			String sqlCode = "UPDATE "+table.getNom()+" SET "+nomColonne+" ="+newValeur+" WHERE "+table.getClePrimaire().getNom()+"="+id;
			this.executerCode(sqlCode);
			Util.logSqlCode(sqlCode);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Récupérer la liste des tables présentent dans la base de données
	 * @return la liste des tables présentent dans la base de données
	 * @throws CustomException
	 * @throws SQLException
	 */
	public ArrayList<Table> getListeTables() throws CustomException, SQLException{
		ArrayList<Table> ret = new ArrayList<Table>();
		
		ResultSet rs = this.executeRequete("SHOW TABLES from "+this.nomBase);

		while(rs.next()){
			String nomTable = rs.getString("Tables_in_"+this.nomBase);
			Table table = new Table(this.BDD, nomTable);
			ArrayList<Colonne> listeColonnes = this.getListeColonnes(nomTable);
			for(Colonne col : listeColonnes){
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
	 * @param nom Table Le nom de la table ou récupérer les colonnes
	 * @return la liste des Colonne présentent dans une table
	 * @throws CustomException
	 * @throws SQLException
	 */
	private ArrayList<Colonne> getListeColonnes(String nomTable) throws CustomException, SQLException{
		ArrayList<Colonne> ret = new ArrayList<Colonne>();
		
		ResultSet rs = this.executeRequete("SHOW COLUMNS from "+nomTable);
		
		while(rs.next()){
			String nom = rs.getString("Field");
			TypeDonnee type = rs.getString("Type").contains("int") ? TypeDonnee.INTEGER : (rs.getString("Type").contains("float") ? TypeDonnee.DOUBLE : (rs.getString("Type").contains("date") ? TypeDonnee.DATE : TypeDonnee.CHAR));
			Colonne colonne = new Colonne(nom, type);
			if(rs.getString("Null").equalsIgnoreCase("NO") && !rs.getString("Key").equalsIgnoreCase("PRI")){
				colonne.ajouterContrainte(new Contrainte(TypeContrainte.NOTNULL, null));
			}
			if(rs.getString("Key").equalsIgnoreCase("UNI")){
				colonne.ajouterContrainte(new Contrainte(TypeContrainte.UNIQUE, null));
			}
			else if(rs.getString("Key").equalsIgnoreCase("PRI")){
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
	 * @param nomTable Le nom de la table ou se situe la colonne
	 * @param nomColonne Le nom de la colonne où se situe les valeurs
	 * @return la liste des valeurs d'une colonne d'une table
	 * @throws CustomException
	 * @throws SQLException
	 */
	private ArrayList<Object> getListeValeurs(String nomTable, String nomColonne) throws CustomException, SQLException{
		ArrayList<Object> ret = new ArrayList<Object>();
		
		ResultSet rs = this.executeRequete("SELECT "+nomColonne+" FROM "+nomTable);
		
		while(rs.next()){
			ret.add(rs.getObject(nomColonne));
		}
		return ret;
	}
	
	/**
	 * Ajouter les contraintes de clés étrangères présentent dans une BDD
	 * @throws CustomException
	 * @throws SQLException
	 */
	private void ajouterFK() throws CustomException, SQLException{
		
		ResultSet rs = this.executeRequete("SELECT CONCAT(table_name, '.', column_name) AS 'foreign key', CONCAT(referenced_table_name, '.', referenced_column_name) AS 'references' FROM information_schema.key_column_usage WHERE referenced_table_name IS NOT NULL;");
		
		while(rs.next()){
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
	 * @return le nom de toutes les BDD du serveur
	 * @throws CustomException
	 */
	public ArrayList<String> getListeBDD() throws CustomException{
		ArrayList<String> ret = new ArrayList<String>();
		try {
			this.connexion("");
			ResultSet rs = this.stmt.executeQuery("SHOW DATABASES");
			while(rs.next()){
				if(!rs.getString("Database").equals("information_schema") && !rs.getString("Database").equals("mysql") && !rs.getString("Database").equals("performance_schema")  && !rs.getString("Database").equals("sys")){
					ret.add(rs.getString("Database"));
				}
			}
			this.fermerConnexion();
		} catch (SQLException e) {
			if(e.getMessage().contains("database exists")){
				throw new CustomException("Erreur", "La base "+this.nomBase+" existe déjà.");
			}
			else{
				e.printStackTrace();
			}
		}
		
		return ret;
	}
	
	
}
