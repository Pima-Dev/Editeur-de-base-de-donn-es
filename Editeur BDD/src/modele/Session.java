package modele;

public class Session {

	/**
	 * id unique pour l'utilisateur du logiciel
	 */
	private int uuid;
	
	/**
	 * Nom de l'utilisateur
	 */
	private String nom;
	
	/**
	 * Le mot de passe de l'utilisateur
	 */
	private String motDePasse;
	
	/**
	 * Initialise les attributs
	 * @param uuid L'uuid de l'utilisateur
	 * @param nom Le nom de l'utilisateur
	 * @param motDePasse Le mot de passe de l'utilisateur
	 */
	public Session(int uuid, String nom, String motDePasse){
		this.uuid = uuid;
		this.nom = nom;
		this.motDePasse = motDePasse;
	}
}
