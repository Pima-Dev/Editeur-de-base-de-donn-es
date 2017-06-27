package vue;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import codeInutilise.VueMotDePasseOublieNouveau;
import modele.BaseDeDonnees;
import modele.Session;

/**
 * construit la fenêtre principale et est la racine de référence qui permet d'accéder à toutes les vues
 */
public class Fenetre{

	/**
	 * la fenêtre principale
	 */
	private JFrame fenetre;
	//private VueMotDePasseOublieNouveau vueMDPOublieNouveau;
	/**
	 * s'affiche lorsque l'utilisateur lance l'application, elle permet de se connecter à sont compte
	 */
	private VueDeConnexion vueDeConnexion;
	/**
	 * s'affiche lorsque l'utilisateur appuye sur le bouton création de compte, elle permet de créer un nouveau compte
	 */
	private VueDeCreationDUtilisateur vueCreationUtilisateur;
	/**
	 * instancie une session
	 */
	private Session session;
	/**
	 * 
	 */
	private VueCreationBDD vueCreationBDD;
	/**
	 * s'affiche lorsque l'utilisateur se connecte, elle permet d'accéder à toutes les options de gestion de la BDD
	 */
	private VuePrincipale vuePrincipale;
	/**
	 * 
	 */
	private BaseDeDonnees BDD;
	/**
	 * s'affiche lorsque l'utilisateur appuye sur le bouton ajouter attribut ou l'option ajouter un attrbut dans le menu, elle permet d'ajouter un attribut à la table courante
	 */
	private VueAjouterAttribut vueAjouterAttribut;
	/**
	 * 
	 */
	private VueOuvrirBDD vueOuvrirBDD;
	private VueLogConsole vueLogConsole;
	private VueRechercheAvance vueRechercheAvance;	
	private VueModifierContrainte vueModifierContrainte;
	private VueLog vueLog;
	
	private static Fenetre instanceFen;
	
	public Fenetre(){
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				creerFenetre();
			}
		});
		instanceFen = this;
	}
	
	public void creerFenetre(){
		this.fenetre = new JFrame("Connexion");
		fenetre.setVisible(true);
		fenetre.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		fenetre.setContentPane(new VueDeConnexion(this));
		fenetre.pack();
		fenetre.setResizable(false);
		fenetre.setLocationRelativeTo(null);
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

	/*public VueMotDePasseOublieNouveau getVueMDPOublieNouveau() {
		return vueMDPOublieNouveau;
	}

	public void setVueMDPOublieNouveau(VueMotDePasseOublieNouveau vueMDPOublieNouveau) {
		this.vueMDPOublieNouveau = vueMDPOublieNouveau;
	}*/

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
	
	public void setSession(Session session){
		this.session = session;
	}

	/**
	 * @return the vuePrincipale
	 */
	public VuePrincipale getVuePrincipale() {
		return vuePrincipale;
	}

	/**
	 * @param vuePrincipale the vuePrincipale to set
	 */
	public void setVuePrincipale(VuePrincipale vuePrincipale) {
		this.vuePrincipale = vuePrincipale;
	}

	public BaseDeDonnees getBDD() {
		return BDD;
	}

	public void setBDD(BaseDeDonnees bDD) {
		BDD = bDD;
	}

	public VueAjouterAttribut getVueAjouterAttribut() {
		return vueAjouterAttribut;
	}

	public void setVueAjouterAttribut(VueAjouterAttribut vueAjouterAttribut) {
		this.vueAjouterAttribut = vueAjouterAttribut;
	}

	public VueOuvrirBDD getVueOuvrirBDD() {
		return vueOuvrirBDD;
	}

	public void setVueOuvrirBDD(VueOuvrirBDD vueOuvrirBDD) {
		this.vueOuvrirBDD = vueOuvrirBDD;
	}

	/**
	 * @return the vueLogConsole
	 */
	public VueLogConsole getVueLogConsole() {
		return vueLogConsole;
	}

	/**
	 * @param vueLogConsole the vueLogConsole to set
	 */
	public void setVueLogConsole(VueLogConsole vueLogConsole) {
		this.vueLogConsole = vueLogConsole;
	}

	/**
	 * @return the vueRechercheAvance
	 */
	public VueRechercheAvance getVueRechercheAvance() {
		return vueRechercheAvance;
	}

	/**
	 * @param vueRechercheAvance the vueRechercheAvance to set
	 */
	public void setVueRechercheAvance(VueRechercheAvance vueRechercheAvance) {
		this.vueRechercheAvance = vueRechercheAvance;
	}

	public VueModifierContrainte getVueModifierContrainte() {
		return vueModifierContrainte;
	}

	public void setVueModifierContrainte(VueModifierContrainte vueModifierContrainte) {
		this.vueModifierContrainte = vueModifierContrainte;
	}
	
	public VueLog getVueLog() {
		return vueLog;
	}

	public void setVueLog(VueLog vueLog) {
		this.vueLog = vueLog;
	}

	public static Fenetre getInstance(){
		return instanceFen;
	}
	
}