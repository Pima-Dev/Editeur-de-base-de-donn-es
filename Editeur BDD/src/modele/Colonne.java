package modele;

import java.sql.SQLException;
import java.util.ArrayList;

public class Colonne<V> implements Cloneable{

	/**
	 * Nom de la colonne
	 */
	private String nom;
	
	/**
	 * Les valeurs contenue dans la colonne
	 */
	private ArrayList<V> listeValeurs;
	
	/**
	 * La liste des contrainte
	 */
	private ArrayList<Contrainte> listeContraintes;
	 
	/**
	 * Table référencé si clé étrangère. Null si aucune clé étrangère.
	 */
	
	/**
	 * Le type de donnée dans la colonne
	 */
	private TypeDonnee type;
	
	/**
	 * Initialise le nom de la colonne
	 * @param nom Le nom de la colonne
	 * @param type Le type de donnee de la colonne
	 */
	public Colonne(String nom, TypeDonnee type){
		this.nom = nom;
		this.type = type;
		this.listeContraintes = new ArrayList<Contrainte>();
		this.listeValeurs = new ArrayList<V>();
	}
	
	/**
	 * Accéder au nom de la colonne
	 * @return Le nom de la colonne
	 */
	public String getNom(){
		return this.nom;
	}
	
	/**
	 * Définir le nom de la colonne
	 * @param nom Le nom de la colonne
	 */
	public void setNom(String nom){
		this.nom = nom;
	}
	

	/**
	 * Permet d'accéder à une valeur avec son index
	 * @param index L'index dans la liste des valeurs
	 * @return La valeur contenue a l'index passé en parametre
	 */
	public V getValue(int index){
		return this.listeValeurs.get(index);
	}
	
	/**
	 * Ajouter une valeur à la colonne
	 * @param valeur La valeur à ajouter
	 * @throws CustomException Erreur
	 */
	public void ajouterValeur(V valeur) throws CustomException{
		if(valeur != null){
			if(this.type == TypeDonnee.INTEGER && !Util.isInteger(valeur.toString())){
				throw new CustomException("Erreur de type", "'"+valeur+"' ne peut pas être ajouté dans une colonne de type "+this.type+".");
			}
			else if(this.type == TypeDonnee.DOUBLE && !Util.isDouble((valeur.toString()))){
				throw new CustomException("Erreur de type", "'"+valeur+"' ne peut pas être ajouté dans une colonne de type "+this.type+".");
			} 
			else if(this.type == TypeDonnee.DATE && (!(valeur instanceof String) || !Util.isValidDate(valeur.toString()))){
				throw new CustomException("Erreur de type", "'"+valeur+"' ne peut pas être ajouté dans une colonne de type "+this.type+".");
			}
			else if(this.type == TypeDonnee.CHAR && (!(valeur instanceof String))){
				throw new CustomException("Erreur de type", "'"+valeur+"' ne peut pas être ajouté dans une colonne de type "+this.type+".");
			}
		}
		
		this.listeValeurs.add(valeur);
	}
	
	/**
	 * Ajouter une contrainte à la colonne. Verifie qu'elle n'existe pas déjà sauf si c'est une contrainte de clé étrangère
	 * @param contrainte La contrainte à ajouter
	 * @throws CustomException Erreur
	 */
	public void ajouterContrainte(Contrainte contrainte) throws CustomException{
		if(contrainte.getContrainteType() != TypeContrainte.REFERENCEKEY){
			for(Contrainte cont : this.listeContraintes){
				if(cont.getContrainteType() == contrainte.getContrainteType()){
					throw new CustomException("Erreur", "La contrainte "+contrainte.getContrainteType()+" est déjà présente pour cette attribut.");
				}
			}
			this.listeContraintes.add(contrainte);
		}
		else {
			if(contrainte.getReferenceTable() == null){
				throw new CustomException("Erreur", "La Table que l'attribut référence ne peut pas être null");
			}
			else{
				this.listeContraintes.add(contrainte);
			}
		}
	}
	
	/**
	 * Récupérer le type des données de la colonne
	 * @return Le type des données de la colonne
	 */
	public TypeDonnee getTypeDonnees(){
		return this.type;
	}
	
	/**
	 * Récupérer la liste des contraintes sur la colonne
	 * @return La liste des contraintes sur la colonne
	 */
	public ArrayList<Contrainte> getListeContraintes(){
		return this.listeContraintes;
	}
	
	/**
	 * Récupérer les valeurs contenues dans la colonne
	 * @return La liste des valeurs contenues dans la colonne
	 */
	public ArrayList<V> getListeValeurs(){
		return this.listeValeurs;
	}
	
	public void setListeValeurs(ArrayList<V> list){
		this.listeValeurs = list;
	}
	
	/**
	 * Récupérer un résumé de la colonne
	 * @return un résumé de la colonne
	 */
	public String toString(){
		String ret = "";
		
		ret+="\nNom: "+this.nom;
		ret+="\nType de données: "+this.type;
		ret+="\nContraintes: ";
		for(Contrainte c : this.listeContraintes){
			ret+="\n"+c.getContrainteType();
		}
		ret+="\nValeurs: ";
		for(Object obj : this.listeValeurs){
			ret+="\n"+obj;
		}
		
		return ret;
	}
	
	/**
	 * Cloner la colonne
	 */
	@Override
	public Object clone(){
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}

