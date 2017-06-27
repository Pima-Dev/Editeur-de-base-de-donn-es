package vue;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;

import controleur.PresserBoutonListener;
import modele.ELFichier;
/**
 * s'affiche lorsque l'utilisateur appuye sur le bouron console ou par l'option console du menu outils, elle permet d'éxécuter du code SQL
 */
public class VueLogConsole extends JPanel{

	/**
	 * contient les éléments de la fenêtre
	 */
	private JPanel panneauPrincipal;
	/**
	 * liste les logs
	 */
	private JList<String> log;
	/**
	 * entrée des commandes SQL
	 */
	private JTextArea console;
	/**
	 * éxécute le code SQL
	 */
	private JButton bouton;
	/**
	 * contient le champ de commande et le bouton d'exécution
	 */
	private JPanel commande;
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
	 * permet de faire défilier les lignes de la commande
	 */
	private JScrollPane scrollConsole;
	/**
	 * fenêtre affichant les éléments graphiques
	 */
	private JFrame fenetreConsole;
	/**
	 * permet de laisser une marge autour du bouton éxécuter
	 */
	private JPanel margeExecuter;
	
	/**
	 * construit le panneau
	 * @param fenetre la racine de référence qui permet d'accéder à toutes les vues
	 */
	public VueLogConsole(Fenetre fenetre) {
		this.fenetre = fenetre;
		this.fenetre.setVueLogConsole(this);
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
		commande = new JPanel();
		modeleLog = new DefaultListModel<String>();
		log = new JList<String>(modeleLog);
		log.setEnabled(false);
		JScrollPane sc = new JScrollPane(log,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		sc.setPreferredSize(new Dimension(sc.getWidth(), 200));
		scrollLog = sc;
		console = new JTextArea();
		console.setLineWrap(true);
		scrollConsole = new JScrollPane(console,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		bouton = new JButton("Executer");
		bouton.setName("executer code console");
		bouton.addActionListener(new PresserBoutonListener(this.fenetre));
		margeExecuter = new JPanel();
		margeExecuter.setLayout(new BorderLayout());
		margeExecuter.add(new JLabel("      "),BorderLayout.SOUTH);
		margeExecuter.add(new JLabel("      "),BorderLayout.WEST);
		margeExecuter.add(new JLabel("      "),BorderLayout.EAST);
		margeExecuter.add(new JLabel("      "),BorderLayout.NORTH);
		margeExecuter.add(bouton,BorderLayout.CENTER);
		commande.setLayout(new BorderLayout());
		commande.add(scrollConsole,BorderLayout.CENTER);
		commande.add(margeExecuter, BorderLayout.EAST);
		panneauPrincipal.setLayout(new BorderLayout(0,10));
		panneauPrincipal.add(scrollLog,BorderLayout.NORTH);
		panneauPrincipal.add(commande,BorderLayout.SOUTH);
		this.setLayout(new BorderLayout());
		this.add(new JLabel("      "),BorderLayout.SOUTH);
		this.add(new JLabel("      "),BorderLayout.WEST);
		this.add(new JLabel("      "),BorderLayout.EAST);
		this.add(new JLabel("      "),BorderLayout.NORTH);
		this.add(panneauPrincipal,BorderLayout.CENTER);
		
		for(String s : ELFichier.lireLog(this.fenetre.getSession().getNom())){
			modeleLog.addElement(s);
		}
		
		fenetreConsole = new JFrame("Console");
		fenetreConsole.setContentPane(this);
		fenetreConsole.setSize(700,330);
		fenetreConsole.setLocationRelativeTo(null);
		fenetreConsole.setVisible(true);
	}
	
	/**
	 * permet d'ajouter des logs à la liste
	 * @param logTexte texte à ajouter
	 */
	public void ajouterLog(String logTexte){
		modeleLog.addElement(logTexte);
	}
	
	/**
	 * accès à la commande SQL à éxécuter
	 * @return
	 */
	public JTextArea getTextArea(){
		return this.console;
	}
}
