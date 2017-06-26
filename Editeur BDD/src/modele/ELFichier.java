package modele;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Properties;

import vue.Fenetre;

public class ELFichier {
	
	private static String racine = "data/";
	private static String nomLog;
	
	public static void ecrireLog(String nomFile, String texte){
		
		if(nomLog == null || nomLog.replace(" ", "").equals("")){
			nomLog = getNomLog();
		}
		
		BufferedWriter writer = null;
		try {
			File file = new File(racine+nomFile+"/logs");
			if(!file.exists())
				file.mkdirs();
			writer = new BufferedWriter(new FileWriter(racine+nomFile+"/logs/"+nomLog, true));
			writer.write("\n"+texte);
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			if(writer != null){
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static ArrayList<String> lireLog(String nomFile){
		ArrayList<String> ret = new ArrayList<String>();
		BufferedReader reader = null;
		String ligne = "";
		try {
			reader = new BufferedReader(new FileReader(racine+nomFile+"/logs/"+nomLog));
			while((ligne = reader.readLine()) != null){
				ret.add(ligne);
			}
			
		} catch (FileNotFoundException e) {
			return ret;
		}
		catch(IOException e){
			e.printStackTrace();
		}
		finally{
			if(reader != null){
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return ret;
}
	
	public static void setCle(String fichier, String cle, String valeur){
		Properties prop = new Properties();
		OutputStream out = null;
		try{
			out = new FileOutputStream(racine+fichier, true);
			prop.setProperty(cle, valeur);
			prop.store(out, null);
		}
		catch(FileNotFoundException e){
			try{
				File file = new File(racine+fichier.replaceAll(fichier.split("/")[fichier.split("/").length-1], ""));
				file.mkdir();
				out = new FileOutputStream(racine+fichier, false);
				prop.setProperty(cle, valeur);
				prop.store(out, null);
			}
			catch(IOException ex){
				ex.printStackTrace();
			}
		}
		catch(IOException e){
			e.printStackTrace();
		}
		finally{
			if(out != null){
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static String chargerValeur(String fichier, String cle){
		Properties prop = new Properties();
		InputStream in = null;
		String ret = null;
		try{
			in = new FileInputStream(racine+fichier);
			prop.load(in);
			ret = prop.getProperty(cle);
		}
		catch(FileNotFoundException e){
			return null;
		}
		catch(IOException e){
			e.printStackTrace();
		}
		finally{
			if (in != null){
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return ret;
	}
	
	public static void creerDossier(String dossier){
		File file = new File(racine+dossier);
		if(!file.exists())
			file.mkdirs();
	}
	
	public static String cryptMDP(String mdp){
	    try{
	        MessageDigest digest = MessageDigest.getInstance("SHA-256");
	        byte[] hash = digest.digest(mdp.getBytes("UTF-8"));
	        StringBuffer hexString = new StringBuffer();

	        for (int i = 0; i < hash.length; i++) {
	            String hex = Integer.toHexString(0xff & hash[i]);
	            if(hex.length() == 1) hexString.append('0');
	            hexString.append(hex);
	        }

	        return hexString.toString();
	    } catch(Exception ex){
	       throw new RuntimeException(ex);
	    }
	}

	public static String getRacine() {
		return racine;
	}
	
	private static String getNomLog(){
		String ret = "";
		int nb = 0;
		File file = new File(racine+Fenetre.getInstance().getSession().getNom()+"/logs");
		File[] files = file.listFiles();
		if(files!=null && files.length>0){
			String nombre = files[files.length-1].getName().replaceAll(" ", "").replaceAll(".log", "").split("-")[1];
			if(Util.isInteger(nombre)){
				nb = Integer.parseInt(nombre)+1;
			}
		}
		
		Calendar calendar = Calendar.getInstance();
		String annee = ""+calendar.get(Calendar.YEAR);
		String mois = ""+calendar.get(Calendar.MONTH);
		String jour = ""+calendar.get(Calendar.DATE);
		String heure = "" + calendar.get(Calendar.HOUR_OF_DAY);

		while (mois.length() < 2)
			mois = "0" + mois;
		while (jour.length() < 2)
			jour = "0" + jour;
		while (heure.length() < 2)
			heure = "0" + heure;
		
		ret = annee+"."+mois+"."+jour+"."+heure+"-"+nb+".log";
		
		return ret;
	}

}
