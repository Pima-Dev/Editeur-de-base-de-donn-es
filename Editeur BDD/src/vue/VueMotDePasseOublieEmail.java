package vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import controleur.PressButtonListener;

public class VueMotDePasseOublieEmail extends JPanel{
	
	private JLabel lTitre;
	private JLabel lEmail;
	private JLabel lInfo;
	private JTextField fEmail;
	private JButton bEnvoyer;
	private JPanel panneau;
	private JPanel panneauChamps;
	private JPanel panneauTitre;
	private Fenetre fenetre;
	private String code;
	
	public VueMotDePasseOublieEmail(Fenetre fenetre){
		this.fenetre = fenetre;
		decoration();
		panneauChamps.add(panneauTitre);
		panneauChamps.add(lEmail);
		panneauChamps.add(fEmail);
		panneauChamps.add(bEnvoyer);
		panneau.add(panneauChamps,BorderLayout.CENTER);
		this.add(new JLabel("       "),BorderLayout.WEST);
		this.add(panneau);
		this.add(new JLabel("       "),BorderLayout.EAST);
		this.bEnvoyer.setName("envoyer mail");
		this.bEnvoyer.addActionListener(new PressButtonListener(fenetre));
	}
	
	public void decoration(){
		this.setLayout(new BorderLayout());
		panneauChamps = new JPanel();
		panneauChamps.setLayout(new GridLayout(4,1,5,5));
		panneau = new JPanel();
		panneau.setLayout(new BorderLayout());
		panneauTitre = new JPanel();
		panneauTitre.setLayout(new BorderLayout());
		lTitre = new JLabel("MOT DE PASSE OUBLIE");
		lTitre.setHorizontalAlignment(SwingConstants.CENTER);
		lInfo = new JLabel("");
		lInfo.setHorizontalAlignment(SwingConstants.CENTER);
		lInfo.setForeground(new Color(255,0,0));
		panneauTitre.add(lTitre, BorderLayout.NORTH);
		panneauTitre.add(lInfo, BorderLayout.CENTER);
		lEmail = new JLabel("E-Mail");
		fEmail = new JTextField();
		bEnvoyer = new JButton("Envoyer");
		
	}

	/**
	 * @return the lInfo
	 */
	public JLabel getlInfo() {
		return lInfo;
	}

	/**
	 * @return the fEmail
	 */
	public JTextField getfEmail() {
		return fEmail;
	}

	/**
	 * @return the bEnvoyer
	 */
	public JButton getbEnvoyer() {
		return bEnvoyer;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}
}
