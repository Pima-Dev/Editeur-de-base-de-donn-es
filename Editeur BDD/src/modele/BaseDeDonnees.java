package modele;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;

import vue.Fenetre;

public class BaseDeDonnees {

	/**
	 * Le nom de la base de données
	 */
	private String nom;
	
	/**
	 * Le nom de l'utilisateur pour accéder à la BDD
	 */
	private String nomUtilisateur;
	
	/**
	 * Le mot de passe pour accéder à la BDD
	 */
	private String motDePasse;
	
	/**
	 * L'url de la BDD où elle est héberger
	 */
	private String url;
	
	/**
	 * Le serveur sur lequel la base de données est hebergé
	 */
	private Serveur serveur;
	/**
	 * La liste des tables dans la base de donnés
	 */
	private ArrayList<Table> listeTable;
	
	/**
	 * L'utilisateur possèdant la base de donnée
	 */
	private Session session;
	
	/**
	 * Le port de la BDD si elle est sur un serveur distant
	 */
	private int port;
	
	private Fenetre fenetre;
	
	/**
	 * Constructeur
	 * @param nom Nom de la base de données
	 * @throws CustomException 
	 */
	public BaseDeDonnees(String nom, String nomUtilisateur, String motDePasse, Fenetre fenetre, String url, int port) throws CustomException{
		this.nom = nom;
		this.nomUtilisateur = nomUtilisateur;
		this.motDePasse = motDePasse;
		this.fenetre = fenetre;
		this.serveur = new Serveur(this);
		this.listeTable = new ArrayList<Table>();
		this.session = this.fenetre.getSession();
		if(this.session.getListeBDD().contains(nom)){
			throw new CustomException("Erreur", "Cette base de données est déjà existente");
		}
		this.fenetre.setBDD(this);
	}
		
	/**
	 * Méthode qui retourne la liste des tables de la base de donnés
	 * @return Liste des tables de la base de données
	 */
	public ArrayList<Table> getListeTable(){
		return this.listeTable;
	}
	
	/**
	 * Permet d'ajouter une table à la base
	 * @param table La table qui sera ajouté à la base de donnés
	 * @throws CustomException 
	 */
	public void ajouterTable(Table table) throws CustomException{

		this.serveur.creerTable(table.getNom(), table.getListeColonnes());
			
		this.listeTable.add(table);
		
	}
	
	/**
	 * Accéder au serveur de la base de données
	 * @return
	 */
	public Serveur getServeur(){
		return this.serveur;
	}
	
	/**
	 * Supprimer la base de données courante
	 * @throws CustomException 
	 */
	public void supprimerBDD() throws CustomException{
		this.serveur.supprimerBaseDeDonnes();
		File file = new File(ELFichier.getRacine()+this.session.getBDDPath()+this.nom);
		if(file.exists())
			file.delete();
	}
	
	public Table getTable(String nom){
		Table table = null;
		for(Table t : this.listeTable){
			if(t.getNom().equals(nom)){
				table=t;
			}
		}
		return table;
	}
	
	/**
	 * Charger les données contenue dans la base de donnée
	 * @throws CustomException
	 * @throws SQLException
	 */
	public void chargerBDD() throws CustomException, SQLException{
		this.listeTable = this.serveur.getListeTables();
	}
	
	/**
	 * Définir la liste des table de la BDD
	 * @param tables La liste des tables de la BDD
	 */
	public void setListeTables(ArrayList<Table> tables){
		this.listeTable = tables;
	}
	
	public void creerBDD() throws CustomException{

		this.serveur.creerBaseDeDonnees();
		if(this.url != null)
			ELFichier.setCle(this.session.getBDDPath()+this.nom, "adresse", this.url);
		else
			ELFichier.setCle(this.session.getBDDPath()+this.nom, "adresse", "");
		ELFichier.setCle(this.session.getBDDPath()+this.nom, "MDP", this.motDePasse);
		ELFichier.setCle(this.session.getBDDPath()+this.nom, "port", this.port+"");
		
	}
	
	
	public String getNomBDD(){
		return this.nom;
	}
	
	public String getNomUtilisateur(){
		return this.nomUtilisateur;
	}
	
	public String getMotDePasse(){
		return this.motDePasse;
	}
	
	public String getUrlBDD(){
		return this.url;
	}
	public int getPort(){
		return this.port;
	}
}
