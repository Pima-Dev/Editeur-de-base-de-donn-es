package vue;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import modele.Table;

public class ModeleTable extends DefaultTableModel {

	private boolean editable;
	private int ligneAEditer;
	private Fenetre fenetre;
	private int initMin;
	private int initMax;

	public ModeleTable(int hauteur, int largeur, Fenetre fenetre) {
		this.fenetre = fenetre;
		editable = false;
		this.fenetre = fenetre;
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		return super.getValueAt(rowIndex, columnIndex);
	}

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
	 * @return the ligneAEditer
	 */
	public int getLigneAEditer() {
		return ligneAEditer;
	}

	public void setCellEditable(int row) {
		editable = true;
		this.ligneAEditer = row;
	}

	/**
	 * @return the editable
	 */
	public boolean isEditable() {
		return editable;
	}

	public void setCellNonEditable() {
		editable = false;
	}

	public void removeRow(int index){
		super.removeRow(index);
		fireTableRowsDeleted(index, index);
	}
	
	public void rechercher(String texte) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				int colonnes = fenetre.getVuePrincipale().getTable().getColumnCount()-2;
				ArrayList<Integer> lignesValides = new ArrayList<Integer>();
				if(fenetre.getVueRechercheAvance() != null){
					boolean ok;
					initMin = 0;
					initMax = getRowCount();
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
