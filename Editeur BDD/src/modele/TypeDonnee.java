package modele;

public enum TypeDonnee {

	  CHAR("VARCHAR(100)"), INTEGER("INTEGER"), DATE("DATE"), DOUBLE("DOUBLE");

	  private final String symbole;
	  
	  private TypeDonnee(String symbole) {
		this.symbole = symbole;
	  }

	  public String getSQLType() {
		  return symbole;
	  }
}
