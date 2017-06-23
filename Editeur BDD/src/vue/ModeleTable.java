package vue;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import org.apache.pdfbox.cos.COSDictionary;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

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
			for(int i = 1;i < fenetre.getVuePrincipale().getTable().getColumnCount()-2; i++){
				//modifier id ligne et num colone
				//id ligne: fenetre.getVuePrincipale().getTable().getValueAt(ligneAEditer, 0);
				//num colone: i
			}
			
		}
		return ret;
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
				}
				
			}
		});

	}
	
	public void exporterPDF(String destination){
		try{
			String titre = new String("test");
			PDDocument document = new PDDocument();
			PDPage PageDocument = new PDPage(new PDRectangle(5, 5));
			document.addPage(PageDocument);
			PDDocumentInformation info = document.getDocumentInformation();
			//info.setTitle(titre);
			PDPageContentStream contentStream = new PDPageContentStream(document, PageDocument);
			contentStream.beginText();
			contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
			//contentStream.newLineAtOffset(25, 25);
			contentStream.showText("test");
			contentStream.close();
			contentStream.endText();
			document.save(destination+"/"+titre+".pdf");
			document.close();
		}
		catch(IOException e){
			JOptionPane erreur = new JOptionPane();
			erreur.showMessageDialog(null, "Document de destination invalide", "Erreur", JOptionPane.WARNING_MESSAGE);
		}
	}
}
