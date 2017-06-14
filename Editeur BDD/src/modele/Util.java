package modele;

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
	
}
