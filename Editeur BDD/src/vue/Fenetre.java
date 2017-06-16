package vue;
import javax.swing.JFrame;

/**
 * 
 */

/**
 * @author Utilisateur
 *
 */
public class Fenetre{

	private JFrame fenetre;
	
	public Fenetre(){
		this.fenetre = new JFrame("Connexion");
		fenetre.setVisible(true);
		fenetre.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		fenetre.setContentPane(new VueDeConnexion(this));
		fenetre.setLocation(500, 200);
		fenetre.pack();
	}
	
	public JFrame getFenetre(){
		return fenetre;
	}
	
	public static void main(String[] args){
		Fenetre fenetre = new Fenetre();
	}
}