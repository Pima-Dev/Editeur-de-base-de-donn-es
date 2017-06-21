package modele;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Util {

	/**
	 * Ecrit un message d'erreur dans les logs de la console
	 * @param message Le message à écrire dans les logs
	 */
	public static void logErreur(String message){
		
		Calendar calendar = Calendar.getInstance();
		String heure = ""+calendar.get(Calendar.HOUR_OF_DAY);
		String minute = ""+calendar.get(Calendar.MINUTE);
		String seconde = ""+calendar.get(Calendar.SECOND);
		
		while(heure.length() <2)
			heure="0"+heure;
		while(minute.length() <2)
			minute="0"+minute;
		while(seconde.length() <2)
			seconde="0"+seconde;
		System.out.println("["+heure+"."+minute+"."+seconde+"/ERREUR] "+message);
	}
	
	/**
	 * Ecrit un message dans les logs de la console
	 * @param message Le message à écrire dans les logs
	 */
	public static void log(String message){
		
		Calendar calendar = Calendar.getInstance();
		String heure = ""+calendar.get(Calendar.HOUR_OF_DAY);
		String minute = ""+calendar.get(Calendar.MINUTE);
		String seconde = ""+calendar.get(Calendar.SECOND);
		
		while(heure.length() <2)
			heure="0"+heure;
		while(minute.length() <2)
			minute="0"+minute;
		while(seconde.length() <2)
			seconde="0"+seconde;
		
		System.out.println("["+heure+"."+minute+"."+seconde+"/INFO] "+message);
	}
	
	public static void logSqlCode(String message){
		
		Calendar calendar = Calendar.getInstance();
		String heure = ""+calendar.get(Calendar.HOUR_OF_DAY);
		String minute = ""+calendar.get(Calendar.MINUTE);
		String seconde = ""+calendar.get(Calendar.SECOND);
		
		while(heure.length() <2)
			heure="0"+heure;
		while(minute.length() <2)
			minute="0"+minute;
		while(seconde.length() <2)
			seconde="0"+seconde;
		
		System.out.println("["+heure+"."+minute+"."+seconde+"/SQL] "+message);
	}
	
	public static boolean isValidDate(String inDate) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	    dateFormat.setLenient(false);
	    try {
	    	dateFormat.parse(inDate.trim());
	    } 
	    catch (ParseException pe) {
	    	return false;
	    }
	    catch(NullPointerException e){
	    	return false;
	    }
	    return true;
	}
	
	public static boolean isInteger(String s) {
	    try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    } catch(NullPointerException e) {
	        return false;
	    }
	    return true;
	}
	
	public static boolean isDouble(String s) {
	    try { 
	        Double.parseDouble(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    } catch(NullPointerException e) {
	        return false;
	    }
	    return true;
	}
}
