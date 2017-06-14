package modele;

public enum TypeDonnee {

	  CHAR("VARCHAR(100)"), NOMBRE("INTEGER"), DATE("DATE");

	  private final String symbole;
	  
	  private TypeDonnee(String symbole) {
		this.symbole = symbole;
	  }

	  public String getSQLType() {
		  return symbole;
	  }
}
