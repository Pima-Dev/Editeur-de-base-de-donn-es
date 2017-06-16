package vue;

import javax.swing.*;

import controleur.connexion.ConnexionBoutonMotDePasseOublie;
import controleur.motDePasseOublie.MotDePasseOublieBoutonCode;

import java.awt.*;

public class VueMotDePasseOublieCode extends JPanel{
	

	private JLabel lTitre;
	private JLabel lCode;
	private JLabel lInfo;
	private JTextField fCode;
	private JButton bValider;
	private JPanel panneau;
	private JPanel panneauChamps;
	private JPanel panneauTitre;
	private Fenetre fenetre;
	private String code;
	
	public VueMotDePasseOublieCode(Fenetre fenetre, String code){
		this.fenetre = fenetre;
		this.code = code;
		decoration();
		panneauTitre.add(lTitre,BorderLayout.NORTH);
		panneauTitre.add(lInfo, BorderLayout.CENTER);
		panneauChamps.add(panneauTitre);
		panneauChamps.add(lCode);
		panneauChamps.add(fCode);
		panneauChamps.add(bValider);
		panneau.add(panneauChamps,BorderLayout.CENTER);
		this.add(new JLabel("       "),BorderLayout.WEST);
		this.add(panneau);
		this.add(new JLabel("       "),BorderLayout.EAST);
		this.bValider.addActionListener(new MotDePasseOublieBoutonCode(this,fenetre));
	}
	
	public void decoration(){
		this.setLayout(new BorderLayout());
		panneauChamps = new JPanel();
		panneauChamps.setLayout(new GridLayout(0,1,5,5));
		panneau = new JPanel();
		panneau.setLayout(new BorderLayout());
		panneauTitre = new JPanel();
		panneauTitre.setLayout(new BorderLayout());
		lTitre = new JLabel("MOT DE PASSE OUBLIE");
		lTitre.setHorizontalAlignment(SwingConstants.CENTER);
		lCode = new JLabel("Entrez le code qui vous à été envoyé par mail");
		lInfo = new JLabel("");
		lInfo.setHorizontalAlignment(SwingConstants.CENTER);
		lInfo.setForeground(new Color(255,0,0));
		fCode = new JTextField();
		bValider = new JButton("Valider");
		
	}

	/**
	 * @return the lInfo
	 */
	public JLabel getlInfo() {
		return lInfo;
	}

	/**
	 * @return the fCode
	 */
	public JTextField getfCode() {
		return fCode;
	}

	/**
	 * @return the bValider
	 */
	public JButton getbValider() {
		return bValider;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	
	
}
