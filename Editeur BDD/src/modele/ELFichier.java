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
import java.util.Properties;

public class ELFichier {

	private static String nomFile = "data/data.properties";
	
	public static void ecrire(String nomFile, String texte){
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(nomFile));
			writer.write(texte);
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
	
	public static String lire(String nomFile){
		String ret = null;
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(nomFile));
			ret = reader.readLine();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
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
	
	public static void setCle(String cle, String valeur){
		Properties prop = new Properties();
		OutputStream out = null;
		try{
			out = new FileOutputStream(nomFile, true);
			prop.setProperty(cle, valeur);
			prop.store(out, null);
		}
		catch(FileNotFoundException e){
			try{
				File file = new File("data");
				file.mkdir();
				out = new FileOutputStream(nomFile, false);
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
	
	public static String chargerValeur(String cle){
		Properties prop = new Properties();
		InputStream in = null;
		String ret = null;
		try{
			in = new FileInputStream(nomFile);
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
	
	public static void setNomFile(String nom){
		nomFile = "data/"+nom+".properties";
	}
	
	public static String getNomFile(){
		return nomFile;
	}
}
