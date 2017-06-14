package vue;

import javax.swing.*;
import java.awt.*;

public class VueMotDePasseOublieNouveau extends JPanel{
	
	private JLabel lTitre;
	private JLabel lNouveau;
	private JPasswordField fNouveau;
	private JLabel lConfirmation;
	private JPasswordField fConfirmation;
	private JButton bValider;
	private JPanel panneau;
	private JPanel panneauChamps;
	
	public VueMotDePasseOublieNouveau(){
		decoration();
		panneauChamps.add(lTitre);
		panneauChamps.add(lNouveau);
		panneauChamps.add(fNouveau);
		panneauChamps.add(lConfirmation);
		panneauChamps.add(fConfirmation);
		panneauChamps.add(bValider);
		panneau.add(panneauChamps,BorderLayout.CENTER);
		this.add(new JLabel("       "),BorderLayout.WEST);
		this.add(panneau);
		this.add(new JLabel("       "),BorderLayout.EAST);
	}
	
	public void decoration(){
		this.setLayout(new BorderLayout());
		panneauChamps = new JPanel();
		panneauChamps.setLayout(new GridLayout(6,1,5,5));
		panneau = new JPanel();
		panneau.setLayout(new BorderLayout());
		lTitre = new JLabel("MOT DE PASSE OUBLIE");
		lTitre.setHorizontalAlignment(SwingConstants.CENTER);
		lNouveau = new JLabel("Nouveau mot de passe");
		fNouveau = new JPasswordField();
		lConfirmation = new JLabel("Confirmation de mot de passe");
		fConfirmation = new JPasswordField();
		bValider = new JButton("Valider");
		
	}
	
	public static void main(String[] args){
		JFrame fenetre = new JFrame("Connexion");
		fenetre.setContentPane(new VueMotDePasseOublieNouveau());
		fenetre.setVisible(true);
		fenetre.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		fenetre.pack();
	}

	public JPasswordField getfNouveau() {
		return fNouveau;
	}

	public JPasswordField getfConfirmation() {
		return fConfirmation;
	}

	public JButton getbValider() {
		return bValider;
	}
}
