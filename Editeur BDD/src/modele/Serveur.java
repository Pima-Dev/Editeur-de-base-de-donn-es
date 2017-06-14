package modele;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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
	 * Initialise les informartions de connexion
	 * @param nomUtilisateur Le nom de l'utilisateur
	 * @param motDePasse Le mot de passe
	 */
	public Serveur(String nomBase, String nomUtilisateur, String motDePasse){
		this.nomBase = nomBase;
		this.nomUtilisateur = nomUtilisateur;
		this.motDePasse = motDePasse;
	}
	
	/**
	 * Permet de faire la connexion entre la base de données et l'application
	 * @throws CustomException 
	 */
	private void connexion(String base) throws CustomException, MySQLSyntaxErrorException{
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			this.connect = DriverManager.getConnection("jdbc:mysql://localhost/"+base, this.nomUtilisateur, this.motDePasse);
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
	 * Execute le code sql passé en paramètre si il peut compiler
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
	 * Envoyer toutes les valeurs contenue dans l'objet BaseDeDonnees dans la base de données
	 */
	public void exporterALaBaseDeDonnees(){
		
	}
	
	/**
	 * Importe toutes les données contenue dans la base de donnée et les met dans l'objet BaseDeDonnees
	 */
	public void importerDepuisBaseDeDonnees(){
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
				throw new CustomException("Erreur", "La table "+this.nomBase+" existe déjà.");
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
				if(listeColonnes.get(j).getTypeDonnees() == TypeDonnee.NOMBRE){
					sqlCode.append(listeColonnes.get(j).getListeValeurs().get(i)+", ");
				}
				else if(listeColonnes.get(j).getTypeDonnees() == TypeDonnee.CHAR || listeColonnes.get(j).getTypeDonnees() == TypeDonnee.DATE){
					sqlCode.append("'"+listeColonnes.get(j).getListeValeurs().get(i)+"', ");
				}
			}
			sqlCode.deleteCharAt(sqlCode.length()-2);
			sqlCode.append(")");
			try {
				this.executerCode(sqlCode.toString());
				Util.logSqlCode(sqlCode.toString());
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
			
}
