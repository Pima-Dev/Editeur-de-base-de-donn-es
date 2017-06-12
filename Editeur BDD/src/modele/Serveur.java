package modele;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.CommunicationsException;
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
	 * Initialise les informartions de connexion
	 * @param nomUtilisateur Le nom de l'utilisateur
	 * @param motDePasse Le mot de passe
	 */
	public Serveur(String nomUtilisateur, String motDePasse){
		this.nomUtilisateur = nomUtilisateur;
		this.motDePasse = motDePasse;
	}
	
	/**
	 * Permet de faire la connexion entre la base de données et l'application
	 * @param base La base ou se connecter
	 * @throws CustomException 
	 */
	private void connexion(String base) throws CustomException{
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			this.connect = DriverManager.getConnection("jdbc:mysql://localhost/"+base, this.nomUtilisateur, this.motDePasse);
			this.stmt = this.connect.createStatement();
		}
		
		catch(CommunicationsException e){
			throw new CustomException("Erreur", "Le serveur sql est introuvable");
		}
		catch(SQLException e){
			if(e.getMessage().contains("Access denied"))
				throw new CustomException("Erreur", "Identifiant ou mot de passe incorrect");
			else
				e.printStackTrace();
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
	public void executerCode(String code, String base) throws SQLException, CustomException{
		
		try{
			this.connexion(base);
			this.stmt.executeUpdate(code);
		}
		catch(MySQLSyntaxErrorException e){
			throw new CustomException("Erreur de syntaxe", "Le code SQL n'est pas correcte syntaxiquement");
		}
		catch(CustomException e){
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
	 * @param nom Le nom de la base
	 * @throws CustomException Quand la table existe déjà
	 */
	public void creerBaseDeDonnees(String nom) throws CustomException{
		try {
			this.executerCode("CREATE DATABASE "+nom, "");
		} catch (SQLException e) {
			if(e.getMessage().contains("database exists")){
				throw new CustomException("Erreur", "La table "+nom+" existe déjà");
			}
			else{
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Supprimer une base de données
	 * @param nom Nom de la base
	 * @throws CustomException Quand la table n'existe pas
	 */
	public void supprimerBaseDeDonnes(String nom) throws CustomException {
		try {
			this.executerCode("DROP DATABASE "+nom, "");
		} catch (SQLException e) {
			if(e.getMessage().contains("database doesn't exist")){
				throw new CustomException("Erreur", "La table "+nom+" n'existe pas");
			}
			else{
				e.printStackTrace();
			}
		}
	}
		
}
