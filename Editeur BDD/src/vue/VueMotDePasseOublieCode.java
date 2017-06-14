package vue;

import javax.swing.*;
import java.awt.*;

public class VueMotDePasseOublieCode extends JPanel{
	

	private JLabel lTitre;
	private JLabel lCode;
	private JTextField fCode;
	private JButton bValider;
	private JPanel panneau;
	private JPanel panneauChamps;
	
	public VueMotDePasseOublieCode(){
		decoration();
		panneauChamps.add(lTitre);
		panneauChamps.add(lCode);
		panneauChamps.add(fCode);
		panneauChamps.add(bValider);
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
		lCode = new JLabel("Entrez le code qui vous à été envoyé par mail");
		fCode = new JTextField();
		bValider = new JButton("Valider");
		
	}
	
	public static void main(String[] args){
		JFrame fenetre = new JFrame("Connexion");
		fenetre.setContentPane(new VueMotDePasseOublieCode());
		fenetre.setVisible(true);
		fenetre.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		fenetre.pack();
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
	
	
}
