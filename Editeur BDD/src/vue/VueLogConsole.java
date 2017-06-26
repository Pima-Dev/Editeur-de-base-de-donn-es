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

public class VueLogConsole extends JPanel{

	private JPanel panneauPrincipal;
	private JList<String> log;
	private JTextArea console;
	private JButton bouton;
	private JPanel commande;
	private Fenetre fenetre;
	private DefaultListModel<String> modeleLog;
	private JScrollPane scrollLog;
	private JScrollPane scrollConsole;
	private JFrame fenetreConsole;
	private JPanel margeExecuter;
	
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
		panneauPrincipal.add(commande,BorderLayout.CENTER);
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
		fenetreConsole.setSize(700,500);
		fenetreConsole.setLocationRelativeTo(null);
		fenetreConsole.setVisible(true);
	}
	
	public void ajouterLog(String logTexte){
		modeleLog.addElement(logTexte);
	}
	
	public JTextArea getTextArea(){
		return this.console;
	}
}
