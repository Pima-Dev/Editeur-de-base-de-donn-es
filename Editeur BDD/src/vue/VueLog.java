package vue;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import modele.ELFichier;
/**
 * s'affiche lorsque l'utilisateur appuie sur le bouton voir les logs
 */
public class VueLog extends JPanel{

	/**
	 * contient les éléments de la fenêtre
	 */
	private JPanel panneauPrincipal;
	/**
	 * liste les logs
	 */
	private JList<String> log;
	/**
	 * la racine de référence qui permet d'accéder à toutes les vues
	 */
	private Fenetre fenetre;
	/**
	 * détermine le contenu de la liste de logs
	 */
	private DefaultListModel<String> modeleLog;
	/**
	 * permet de faire défilier les logs
	 */
	private JScrollPane scrollLog;
	/**
	 * fenêtre affichant les éléments graphiques
	 */
	private JFrame fenetreLog;
	
	/**
	 * construit le panneau
	 * @param fenetre la racine de référence qui permet d'accéder à toutes les vues
	 */
	public VueLog(Fenetre fenetre) {
		this.fenetre = fenetre;
		this.fenetre.setVueLog(this);
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				creer();
			}
		});
	}
	
	/**
	 * construction du panneau
	 */
	public void creer(){
		panneauPrincipal = new JPanel();
		modeleLog = new DefaultListModel<String>();
		log = new JList<String>(modeleLog);
		log.setEnabled(false);
		scrollLog = new JScrollPane(log);		
		panneauPrincipal.setLayout(new BorderLayout(0,10));
		panneauPrincipal.add(scrollLog,BorderLayout.CENTER);
		this.setLayout(new BorderLayout());
		this.add(new JLabel("      "),BorderLayout.SOUTH);
		this.add(new JLabel("      "),BorderLayout.WEST);
		this.add(new JLabel("      "),BorderLayout.EAST);
		this.add(new JLabel("      "),BorderLayout.NORTH);
		this.add(panneauPrincipal,BorderLayout.CENTER);
		
		for(String s : ELFichier.lireLog(this.fenetre.getSession().getNom())){
			modeleLog.addElement(s);
		}
		fenetreLog = new JFrame("Logs");
		fenetreLog.setContentPane(this);
		fenetreLog.setSize(700,500);
		fenetreLog.setLocationRelativeTo(null);
		fenetreLog.setVisible(true);
	}
	
	/**
	 * permet d'ajouter des logs à la liste
	 * @param logTexte texte à ajouter
	 */
	public void ajouterLog(String logTexte){
		modeleLog.addElement(logTexte);
	}
	
	/**
	 * accès à la liste des logs
	 * @return l'ArrayList contenant la liste des logs
	 */
	public ArrayList<String> getLogs(){
		ArrayList<String> ret = new ArrayList<String>();
		
		for(int i = 0; i<this.modeleLog.size(); i++){
			ret.add(this.modeleLog.getElementAt(i));
		}
		
		return ret;
	}
}
