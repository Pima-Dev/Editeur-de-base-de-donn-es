package vue;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.lang.model.util.ElementFilter;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import modele.ELFichier;

public class VueLog extends JPanel{

	private JPanel panneauPrincipal;
	private JList<String> log;
	private JPanel commande;
	private Fenetre fenetre;
	private DefaultListModel<String> modeleLog;
	private JScrollPane scrollLog;
	private JFrame fenetreLog;
	
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
	
	public void creer(){
		panneauPrincipal = new JPanel();
		commande = new JPanel();
		modeleLog = new DefaultListModel<String>();
		log = new JList<String>(modeleLog);
		log.setEnabled(false);
		scrollLog = new JScrollPane(log);		
		commande.setLayout(new BorderLayout());
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
	
	public void ajouterLog(String logTexte){
		modeleLog.addElement(logTexte);
	}
	
	public ArrayList<String> getLogs(){
		ArrayList<String> ret = new ArrayList<String>();
		
		for(int i = 0; i<this.modeleLog.size(); i++){
			ret.add(this.modeleLog.getElementAt(i));
		}
		
		return ret;
	}
}
