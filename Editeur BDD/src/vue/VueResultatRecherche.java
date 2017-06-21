package vue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class VueResultatRecherche extends JPanel{

	Fenetre fenetre;
	JLabel lTitre;
	JTable resultats;
	int colonnes;
	
	public VueResultatRecherche(Fenetre fenetre) {
		this.fenetre = fenetre;
		colonnes = fenetre.getVuePrincipale().getTable().getColumnCount()-2;
		System.out.println(fenetre.getVuePrincipale());
		resultats = new JTable(0,colonnes);
	}
	
	public static void main(String[] args){
		JFrame fenetre = new JFrame();
		fenetre = new JFrame("Connexion");
		fenetre.setVisible(true);
		fenetre.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		fenetre.setContentPane(new VueResultatRecherche(new Fenetre()));
		fenetre.setLocation(500, 200);
		fenetre.pack();
	}

	public void addRow(int ligne){
		String[] donnees = new String[colonnes];
		for(int i = 0; i < colonnes ;i++){
			donnees[i] = (String)fenetre.getVuePrincipale().getTable().getValueAt(ligne, i);
		}
		((DefaultTableModel)resultats.getModel()).insertRow(ligne,donnees);
	}
}
