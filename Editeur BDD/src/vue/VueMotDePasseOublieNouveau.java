package vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;

import controleur.PressButtonListener;

public class VueMotDePasseOublieNouveau extends JPanel{
	
	private JLabel lTitre;
	private JLabel lNouveau;
	private JLabel lInfo;
	private JPasswordField fNouveau;
	private JLabel lConfirmation;
	private JPasswordField fConfirmation;
	private JButton bValider;
	private JPanel panneau;
	private JPanel panneauChamps;
	private JPanel panneauTitre;
	private Fenetre fenetre;
	
	public VueMotDePasseOublieNouveau(Fenetre fenetre){
		this.fenetre = fenetre;
		this.fenetre.setVueMDPOublieNouveau(this);
		decoration();
		panneauTitre.add(lTitre, BorderLayout.NORTH);
		panneauTitre.add(lInfo, BorderLayout.CENTER);
		panneauChamps.add(panneauTitre);
		panneauChamps.add(lNouveau);
		panneauChamps.add(fNouveau);
		panneauChamps.add(lConfirmation);
		panneauChamps.add(fConfirmation);
		panneauChamps.add(bValider);
		panneau.add(panneauChamps,BorderLayout.CENTER);
		this.add(new JLabel("       "),BorderLayout.WEST);
		this.add(panneau);
		this.add(new JLabel("       "),BorderLayout.EAST);
		this.bValider.setName("Valider mdp oublié");
		this.bValider.addActionListener(new PressButtonListener(fenetre));
	}
	
	public void decoration(){
		this.setLayout(new BorderLayout());
		panneauChamps = new JPanel();
		panneauChamps.setLayout(new GridLayout(6,1,5,5));
		panneau = new JPanel();
		panneau.setLayout(new BorderLayout());
		panneauTitre = new JPanel();
		panneauTitre.setLayout(new BorderLayout());
		lTitre = new JLabel("MOT DE PASSE OUBLIE");
		lTitre.setHorizontalAlignment(SwingConstants.CENTER);
		lInfo = new JLabel("");
		lInfo.setHorizontalAlignment(SwingConstants.CENTER);
		lInfo.setForeground(new Color(255,0,0));
		lNouveau = new JLabel("Nouveau mot de passe");
		fNouveau = new JPasswordField();
		lConfirmation = new JLabel("Confirmation de mot de passe");
		fConfirmation = new JPasswordField();
		bValider = new JButton("Valider");
		
	}

	/**
	 * @return the lInfo
	 */
	public JLabel getlInfo() {
		return lInfo;
	}

	/**
	 * @return the fNouveau
	 */
	public JPasswordField getfNouveau() {
		return fNouveau;
	}

	/**
	 * @return the fConfirmation
	 */
	public JPasswordField getfConfirmation() {
		return fConfirmation;
	}

	/**
	 * @return the bValider
	 */
	public JButton getbValider() {
		return bValider;
	}
}
