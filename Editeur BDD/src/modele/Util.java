package modele;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JTable;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import vue.Fenetre;

public class Util {

	/**
	 * Ecrit un message d'erreur dans les logs de la console
	 * 
	 * @param message
	 *            Le message à écrire dans les logs
	 */
	public static void logErreur(String message) {

		Calendar calendar = Calendar.getInstance();
		String heure = "" + calendar.get(Calendar.HOUR_OF_DAY);
		String minute = "" + calendar.get(Calendar.MINUTE);
		String seconde = "" + calendar.get(Calendar.SECOND);

		while (heure.length() < 2)
			heure = "0" + heure;
		while (minute.length() < 2)
			minute = "0" + minute;
		while (seconde.length() < 2)
			seconde = "0" + seconde;
		
		String log = "[" + heure + "." + minute + "." + seconde + "/ERREUR] " + message;
		System.out.println(log);
		
		if(Fenetre.getInstance() == null || Fenetre.getInstance().getSession() == null){
			return;
		}
		
		ELFichier.ecrireLog(Fenetre.getInstance().getSession().getNom(), log);
		if(Fenetre.getInstance().getVueLog() != null){
			Fenetre.getInstance().getVueLog().ajouterLog(log);
		}
		if(Fenetre.getInstance().getVueLogConsole() != null){
			Fenetre.getInstance().getVueLogConsole().ajouterLog(log);
		}
	}

	/**
	 * Ecrit un message dans les logs de la console
	 * 
	 * @param message
	 *            Le message à écrire dans les logs
	 */
	public static void log(String message) {

		Calendar calendar = Calendar.getInstance();
		String heure = "" + calendar.get(Calendar.HOUR_OF_DAY);
		String minute = "" + calendar.get(Calendar.MINUTE);
		String seconde = "" + calendar.get(Calendar.SECOND);

		while (heure.length() < 2)
			heure = "0" + heure;
		while (minute.length() < 2)
			minute = "0" + minute;
		while (seconde.length() < 2)
			seconde = "0" + seconde;

		String log = "[" + heure + "." + minute + "." + seconde + "/INFO] " + message;
		System.out.println(log);
		
		if(Fenetre.getInstance() == null || Fenetre.getInstance().getSession() == null){
			return;
		}
		
		ELFichier.ecrireLog(Fenetre.getInstance().getSession().getNom(), log);
		if(Fenetre.getInstance().getVueLog() != null){
			Fenetre.getInstance().getVueLog().ajouterLog(log);
		}
		if(Fenetre.getInstance().getVueLogConsole() != null){
			Fenetre.getInstance().getVueLogConsole().ajouterLog(log);
		}
	}

	public static void logSqlCode(String message) {

		Calendar calendar = Calendar.getInstance();
		String heure = "" + calendar.get(Calendar.HOUR_OF_DAY);
		String minute = "" + calendar.get(Calendar.MINUTE);
		String seconde = "" + calendar.get(Calendar.SECOND);

		while (heure.length() < 2)
			heure = "0" + heure;
		while (minute.length() < 2)
			minute = "0" + minute;
		while (seconde.length() < 2)
			seconde = "0" + seconde;

		String log = "[" + heure + "." + minute + "." + seconde + "/SQL] " + message;
		System.out.println(log);
		
		if(Fenetre.getInstance() == null || Fenetre.getInstance().getSession() == null){
			return;
		}
		
		ELFichier.ecrireLog(Fenetre.getInstance().getSession().getNom(), log);
		if(Fenetre.getInstance().getVueLog() != null){
			Fenetre.getInstance().getVueLog().ajouterLog(log);
		}
		if(Fenetre.getInstance().getVueLogConsole() != null){
			Fenetre.getInstance().getVueLogConsole().ajouterLog(log);
		}
	}

	public static boolean isValidDate(String inDate) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		dateFormat.setLenient(false);
		try {
			dateFormat.parse(inDate.trim());
		} catch (ParseException pe) {
			return false;
		} catch (NullPointerException e) {
			return false;
		}
		return true;
	}

	public static boolean isInteger(String s) {
		try {
			Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return false;
		} catch (NullPointerException e) {
			return false;
		}
		return true;
	}

	public static boolean isDouble(String s) {
		try {
			Double.parseDouble(s);
		} catch (NumberFormatException e) {
			return false;
		} catch (NullPointerException e) {
			return false;
		}
		return true;
	}

	public static void exporterPDF(JTable table, String file) {
		try {
			Document doc = new Document();
			PdfWriter.getInstance(doc, new FileOutputStream(file));
			doc.open();
			PdfPTable pdfTable = new PdfPTable(table.getColumnCount()-2);
			for (int i = 0; i < table.getColumnCount()-2; i++) {
				pdfTable.addCell(table.getColumnName(i));
			}
			for (int rows = 0; rows < table.getRowCount(); rows++) {
				for (int cols = 0; cols < table.getColumnCount()-2; cols++) {
					pdfTable.addCell((String)table.getModel().getValueAt(rows, cols));

				}
			}
			doc.add(pdfTable);
			doc.close();
		} catch (DocumentException ex) {
			logErreur(ex.getMessage());
		} catch (FileNotFoundException ex) {
			logErreur(ex.getMessage());
		}
	}
}
