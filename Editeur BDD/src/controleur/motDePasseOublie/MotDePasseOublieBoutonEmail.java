package controleur.motDePasseOublie;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import vue.Fenetre;
import vue.VueMotDePasseOublieCode;
import vue.VueMotDePasseOublieEmail;
import vue.VueMotDePasseOublieNouveau;

public class MotDePasseOublieBoutonEmail implements ActionListener {

	private Fenetre fenetre;
	private VueMotDePasseOublieEmail vue;
	private String adresse;
	private String code;
	
	public MotDePasseOublieBoutonEmail(VueMotDePasseOublieEmail vue, Fenetre fenetre) {
		this.vue = vue;
		this.fenetre = fenetre;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		adresse = vue.getfEmail().getText();
		if(!adresse.equals("")){
			if(valide()){
				envoyerCode();
				fenetre.getFenetre().setContentPane(new VueMotDePasseOublieCode(fenetre, code));
				fenetre.getFenetre().setVisible(true);
				fenetre.getFenetre().setSize(new Dimension(350,190));
			}
			else{
				vue.getlInfo().setText("Email invalide");
			}
		}
		else{
			vue.getlInfo().setText("Veuillez entrer un email");
		}
	}

private void envoyerCode() {
		
		vue.setCode(code);
	}


	private boolean valide() {
		boolean ret = true;
		  try {
		      InternetAddress emailAddr = new InternetAddress(adresse);
		      emailAddr.validate();
		   } catch (AddressException ex) {
		      ret = false;
		   }
		   return ret;
	}
}