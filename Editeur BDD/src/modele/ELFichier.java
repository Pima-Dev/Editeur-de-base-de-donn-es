package modele;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.util.Properties;

public class ELFichier {
	
	private static String racine = "data/";
	
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
				File file = new File("data");
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

}
