package vue;

import javax.swing.*;
import java.awt.*;

public class VueMotDePasseOublieEmail extends JPanel{
	
	private JLabel lTitre;
	private JLabel lEmail;
	private JTextField fEmail;
	private JButton bEnvoyer;
	private JPanel panneau;
	private JPanel panneauChamps;
	
	public VueMotDePasseOublieEmail(){
		decoration();
		panneauChamps.add(lTitre);
		panneauChamps.add(lEmail);
		panneauChamps.add(fEmail);
		panneauChamps.add(bEnvoyer);
		panneau.add(panneauChamps,BorderLayout.CENTER);
		this.add(new JLabel("       "),BorderLayout.WEST);
		this.add(panneau);
		this.add(new JLabel("       "),BorderLayout.EAST);
	}
	
	public void decoration(){
		this.setLayout(new BorderLayout());
		panneauChamps = new JPanel();
		panneauChamps.setLayout(new GridLayout(4,1,5,5));
		panneau = new JPanel();
		panneau.setLayout(new BorderLayout());
		lTitre = new JLabel("MOT DE PASSE OUBLIE");
		lTitre.setHorizontalAlignment(SwingConstants.CENTER);
		lEmail = new JLabel("E-Mail");
		fEmail = new JTextField();
		bEnvoyer = new JButton("Envoyer");
		
	}
	
	public static void main(String[] args){
		JFrame fenetre = new JFrame("Connexion");
		fenetre.setContentPane(new VueMotDePasseOublieEmail());
		fenetre.setVisible(true);
		fenetre.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		fenetre.pack();
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
}
