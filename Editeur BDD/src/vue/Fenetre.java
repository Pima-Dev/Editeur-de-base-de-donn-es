package vue;
import javax.swing.JFrame;

import modele.Session;


public class Fenetre{

	private JFrame fenetre;
	private VueMotDePasseOublieNouveau vueMDPOublieNouveau;
	private VueDeConnexion vueDeConnexion;
	private VueDeCreationDUtilisateur vueCreationUtilisateur;
	private Session session;
	private VueCreationBDD vueCreationBDD;
	
	public Fenetre(){
		this.fenetre = new JFrame("Connexion");
		fenetre.setVisible(true);
		fenetre.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		fenetre.setContentPane(new VueDeConnexion(this));
		fenetre.setLocation(500, 200);
		fenetre.pack();
	}
	
	/**
	 * @return the vueCreationBDD
	 */
	public VueCreationBDD getVueCreationBDD() {
		return vueCreationBDD;
	}

	/**
	 * @param vueCreationBDD the vueCreationBDD to set
	 */
	public void setVueCreationBDD(VueCreationBDD vueCreationBDD) {
		this.vueCreationBDD = vueCreationBDD;
	}

	public JFrame getFenetre(){
		return fenetre;
	}
	
	public static void main(String[] args){
		Fenetre fenetre = new Fenetre();
	}

	public VueMotDePasseOublieNouveau getVueMDPOublieNouveau() {
		return vueMDPOublieNouveau;
	}

	public void setVueMDPOublieNouveau(VueMotDePasseOublieNouveau vueMDPOublieNouveau) {
		this.vueMDPOublieNouveau = vueMDPOublieNouveau;
	}

	public VueDeConnexion getVueDeConnexion() {
		return vueDeConnexion;
	}

	public void setVueDeConnexion(VueDeConnexion vueDeConnexion) {
		this.vueDeConnexion = vueDeConnexion;
	}

	public VueDeCreationDUtilisateur getVueCreationUtilisateur() {
		return vueCreationUtilisateur;
	}

	public void setVueCreationUtilisateur(VueDeCreationDUtilisateur vueCreationUtilisateur) {
		this.vueCreationUtilisateur = vueCreationUtilisateur;
	}
	
	public Session getSession(){
		return this.session;
	}
	
	public void setSesstion(Session session){
		this.session = session;
	}
	
}