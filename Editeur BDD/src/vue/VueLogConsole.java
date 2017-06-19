package vue;

import java.awt.BorderLayout;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class VueLogConsole extends JPanel{

	private JPanel panneauPrincipal;
	private JList<String> log;
	private JTextArea console;
	private JButton bouton;
	private JPanel commande;
	private Fenetre fenetre;
	private DefaultListModel<String> modeleLog;
	
	public VueLogConsole(Fenetre fenetre) {
		panneauPrincipal = new JPanel();
		commande = new JPanel();
		modeleLog = new DefaultListModel<String>();
		log = new JList<String>(modeleLog);
		console = new JTextArea();
		bouton = new JButton("Executer");
		commande.setLayout(new BorderLayout());
		commande.add(console,BorderLayout.CENTER);
		commande.add(bouton, BorderLayout.EAST);
		panneauPrincipal.setLayout(new BorderLayout());
		panneauPrincipal.add(log,BorderLayout.NORTH);
		panneauPrincipal.add(commande,BorderLayout.CENTER);
		this.add(new JLabel("      "),BorderLayout.SOUTH);
		this.add(new JLabel("      "),BorderLayout.WEST);
		this.add(new JLabel("      "),BorderLayout.EAST);
		this.add(panneauPrincipal,BorderLayout.CENTER);
	}
	
	public static void main(String[] args){
		JFrame fenetre = new JFrame("Connexion");
		fenetre.setContentPane(new VueLogConsole(new Fenetre()));
		fenetre.setVisible(true);
		fenetre.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		fenetre.pack();
	}

}
