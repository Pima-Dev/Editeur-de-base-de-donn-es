package vue;

import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
/**
 * Cet objet définie le contenu et les caractèristiques du tableau, il est utilisé pour le tableau principal
 */
public class ModeleTable extends DefaultTableModel {

	/**
	 * vérifie l'état de l'édition des cases
	 */
	private boolean editable;
	/**
	 * la ligne à éditer
	 */
	private int ligneAEditer;
	/**
	 * la racine de référence qui permet d'accéder à toutes les vues
	 */
	private Fenetre fenetre;

	/**
	 * construit l'objet modele table
	 * @param fenetre 
	 */
	public ModeleTable(int hauteur, int largeur,Fenetre fenetre) {
		this.fenetre = fenetre;
		editable = false;
	}

	/**
	 * redéfinition de la méthode getValueAt, qui permet d'obtenir une valeur dans le tableau
	 */
	public Object getValueAt(int rowIndex, int columnIndex) {
		return super.getValueAt(rowIndex, columnIndex);
	}

	/**
	 * redéfinition de la méthode isCellEditable qui définis les cellules modifiable dans le tableau
	 */
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		boolean ret = false;
		if (editable) {
			if (columnIndex > 0 && ligneAEditer == rowIndex) {
				ret = true;
			} else {
				ret = false;
			}
		} else {
			if (columnIndex > fenetre.getVuePrincipale().getTable().getColumnCount()-3) {
				ret = true;
			} else {
				ret = false;
			}
		
		}
		return ret;
	}

	/**
	 * accès au numéro de ligne à éditer
	 * @return le numéro de ligne à éditer
	 */
	public int getLigneAEditer() {
		return ligneAEditer;
	}

	/**
	 * rend une ligne modifiable
	 * @param row le numéro de la ligne modifiable
	 */
	public void setCellEditable(int row) {
		editable = true;
		this.ligneAEditer = row;
	}

	/**
	 * accès à l'état d'édition d'une cellule
	 * @return the editable
	 */
	public boolean isEditable() {
		return editable;
	}

	/**
	 * rend une ligne non modifiable
	 * @param row le numéro de la ligne non modifiable
	 */
	public void setCellNonEditable() {
		editable = false;
	}

	/**
	 * redéfinition de la méthode qui supprime une ligne du tableau
	 */
	public void removeRow(int index){
		super.removeRow(index);
		fireTableRowsDeleted(index, index);
	}
	
	/**
	 * recherche une chaine de caractère dans le tableau
	 * @param texte la chaine de caractère à rechercher
	 */
	public void rechercher(String texte) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				int colonnes = fenetre.getVuePrincipale().getTable().getColumnCount()-2;
				ArrayList<Integer> lignesValides = new ArrayList<Integer>();
				if(fenetre.getVueRechercheAvance() != null){
					boolean ok;
					int initMin = 0;
					int initMax = getRowCount();
					if(fenetre.getVueRechercheAvance().getSelectionnerLigne().isSelected()){
						initMin = (Integer)fenetre.getVueRechercheAvance().getLigneMin().getValue();
						initMax = (Integer)fenetre.getVueRechercheAvance().getLigneMax().getValue();;
					}
					for (int i = initMin; i < initMax; i++) {
						for (int j = 0; j < getColumnCount() - 2; j++) {
							if(fenetre.getVueRechercheAvance().getMotComplet().isSelected()){
								ok = (((String) getValueAt(i, j)).toUpperCase()).equals(texte.toUpperCase());
							}else if(fenetre.getVueRechercheAvance().getSensibleALaCasse().isSelected()){
								ok = ((String) getValueAt(i, j)).contains(texte) && !lignesValides.contains(i);
							}else if(fenetre.getVueRechercheAvance().getMotComplet().isSelected() && fenetre.getVueRechercheAvance().getSensibleALaCasse().isSelected()){
								ok = ((String) getValueAt(i, j)).equals(texte) && !lignesValides.contains(i);
							}
							else{
								ok = (((String) getValueAt(i, j)).toUpperCase()).contains(texte.toUpperCase());
							}
							if (ok && !lignesValides.contains(i)) {
								lignesValides.add(i);
							}
						}
					}
				} else{
					for (int i = 0; i < getRowCount(); i++) {
						for (int j = 0; j < getColumnCount() - 2; j++) {
							if ((((String) getValueAt(i, j)).toUpperCase()).contains(texte.toUpperCase()) && !lignesValides.contains(i)) {
								lignesValides.add(i);
							}
						}
					}
				}
				if(lignesValides.size()!=0){
					String[][] donnees = new String[lignesValides.size()][colonnes];
					String[] titres = new String[colonnes];
					int k = 0;
					for (Integer i : lignesValides) {
						for(int j = 0; j < colonnes ;j++){
							donnees[k][j] = (String)fenetre.getVuePrincipale().getTable().getValueAt(i, j);
							titres[j] = (String)fenetre.getVuePrincipale().getTable().getColumnName(j);
						}
						k++;
					}
					fenetre.getVuePrincipale().insererValeursDansTab(donnees, titres);
				}
				else{
					fenetre.getVuePrincipale().resetJTable();
					JOptionPane.showMessageDialog(null, "Votre recherche n'a aboutit à aucun résultat", "Resultat null", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});

	}
}
