package vue;

import javax.swing.*;
import java.awt.*;

public class ConnectionView extends JPanel{
	
	private JLabel lConnection;
	private JLabel lPseudo;
	private JLabel lMotDePasse;
	private JTextField fPseudo;
	private JTextField fMotDePasse;
	private JButton bConnection;
	private JButton bCreationUtilisateur;
	private JButton lMotDePasseOublie;
	private JPanel panneau;
	
	public ConnectionView(){
		decoration();
		panneau.add(bConnection);
		panneau.add(bCreationUtilisateur);
		panneau.add(lMotDePasseOublie);
		this.add(lConnection);
		this.add(lPseudo);
		this.add(fPseudo);
		this.add(lMotDePasse);
		this.add(fMotDePasse);
		this.add(panneau);
	}
	
	public void decoration(){
		lConnection = new JLabel("<html><center>CONNEXION</center></html>");
		lConnection.setHorizontalAlignment(SwingConstants.CENTER);
		lPseudo = new JLabel("Utilisateur");
		lMotDePasse = new JLabel("Mot de passe");
		fPseudo = new JTextField();
		fMotDePasse = new JTextField();
		bConnection = new JButton("Connexion");
		bCreationUtilisateur = new JButton("Creation de compte");
		lMotDePasseOublie = new JButton("<html><u>Mot de passe oublie</u></html>");
		panneau = new JPanel();
		panneau.setLayout(new GridLayout(2,2));
		lMotDePasseOublie.setBorderPainted(false);
		lMotDePasseOublie.setContentAreaFilled(false);
		lMotDePasseOublie.setForeground(new Color(142,162,198));
		this.setLayout(new GridLayout(6,1));
	}
	
	public static void main(String[] args){
		JFrame fenetre = new JFrame("Connexion");
		fenetre.setContentPane(new ConnectionView());
		fenetre.setVisible(true);
		fenetre.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		fenetre.pack();
	}
}
