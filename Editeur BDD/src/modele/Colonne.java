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
	 * La table dans laquel est la colonne
	 */
	private Table table;
	
	/**
	 * Initialise le nom de la colonne
	 * @param nom Le nom de la colonne
	 */
	public Colonne(String nom, TypeDonnee type, Table table){
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
	 * @param index
	 * @return
	 */
	public V getValue(int index){
		return this.listeValeurs.get(index);
	}
	
	/**
	 * Ajouter une valeur à la colonne
	 * @param valeur La valeur à ajouter
	 * @throws CustomException 
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
	 * @throws CustomException 
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
	 * 
	 * Ajouter une contrainte check sur la colonne
	 * @param operateur L'operateur peut etre: <, >, <=, >=, ==, != 
	 * @param valeur La valeur à mettre à droite de l'opérateur
	 */
	public void ajouterCheck(String operateur, V valeur){
		
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
	
	@Override
	public Object clone(){
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private void modifierContraintes(ArrayList<Contrainte> contraintes) throws SQLException, CustomException{
		Colonne col = new Colonne<>(this.nom, this.type, this.table);
		Contrainte ref = null;
		for(Contrainte c : contraintes){
			if(c.getContrainteType() != TypeContrainte.REFERENCEKEY)	
				col.ajouterContrainte(c);
			else
				ref = c;
		}
		this.table.getBDD().getServeur().modifierContrainte(this.table.getNom(), col);
		this.listeContraintes.clear();
		for(Contrainte c : contraintes){
			if(c.getContrainteType() != TypeContrainte.REFERENCEKEY)	
				this.ajouterContrainte(c);
		}
		if(ref != null){
			this.table.getBDD().getServeur().ajouterFKColExistente(this.table.getNom(), this.nom, ref);
			this.ajouterContrainte(ref);
		}
	}
	
	public void ajouterContrainteExisteCol(Contrainte contrainte) throws CustomException, SQLException{
		for(Contrainte c : this.listeContraintes){
			if(c.getContrainteType() == contrainte.getContrainteType()){
				throw new CustomException("Erreur", "Cette contrainte est déjà présente dans cette colonne.");
			}
		}
		ArrayList<Contrainte> contraintes = (ArrayList<Contrainte>) this.listeContraintes.clone();
		contraintes.add(contrainte);
		this.modifierContraintes(contraintes);
	}
	
	public void supprimerContrainteExisteCol(Contrainte contrainte) throws CustomException, SQLException{
		boolean existePas = true;
		for(Contrainte c : this.listeContraintes){
			if(c.getContrainteType() == contrainte.getContrainteType()){
				existePas = false;
			}
		}
		if(existePas){
			throw new CustomException("Erreur", "Cette contrainte n'existe pas dans cette colonne et ne peut donc pas être supprimé.");
		}
		ArrayList<Contrainte> contraintes = (ArrayList<Contrainte>) this.listeContraintes.clone();
		for(int i =0; i<contraintes.size(); i++){
			if(contraintes.get(i).getContrainteType() == contrainte.getContrainteType()){
				contraintes.remove(i);
			}
		}
		this.modifierContraintes(contraintes);
	}
}

