package vue;

import javax.swing.*;
import java.awt.*;

public class ConnectionView extends JPanel{
	
	private JLabel lConnection;
	private JLabel lPseudo;
	private JLabel lMotDePasse;
	private JTextField fPseudo;
	private JPasswordField fMotDePasse;
	private JButton bConnection;
	private JButton bCreationUtilisateur;
	private JButton lMotDePasseOublie;
	private JPanel panneau;
	private JPanel panneauBouton;
	
	public ConnectionView(){
		decoration();
		panneauBouton.add(bConnection);
		panneauBouton.add(bCreationUtilisateur);
		panneauBouton.add(lMotDePasseOublie);
		panneau.add(lConnection);
		panneau.add(lPseudo);
		panneau.add(fPseudo);
		panneau.add(lMotDePasse);
		panneau.add(fMotDePasse);
		panneau.add(panneauBouton);
		this.add(new JLabel("       "),BorderLayout.WEST);
		this.add(panneau);
		this.add(new JLabel("       "),BorderLayout.EAST);
	}
	
	public void decoration(){
		this.setLayout(new BorderLayout());
		panneau = new JPanel();
		panneau.setLayout(new GridLayout(6,1,5,10));
		lConnection = new JLabel("CONNEXION</center></html>");
		lConnection.setHorizontalAlignment(SwingConstants.CENTER);
		lPseudo = new JLabel("Utilisateur");
		lPseudo.setHorizontalAlignment(SwingConstants.CENTER);
		lMotDePasse = new JLabel("Mot de passe");
		lMotDePasse.setHorizontalAlignment(SwingConstants.CENTER);
		fPseudo = new JTextField();
		fMotDePasse = new JPasswordField();
		bConnection = new JButton("Connexion");
		bCreationUtilisateur = new JButton("Creation de compte");
		lMotDePasseOublie = new JButton("<html><u>Mot de passe oublie</u></html>");
		panneauBouton = new JPanel();
		panneauBouton.setLayout(new GridLayout(2,2,5,5));
		lMotDePasseOublie.setBorderPainted(false);
		lMotDePasseOublie.setContentAreaFilled(false);
		lMotDePasseOublie.setForeground(new Color(142,162,198));
	}
	
	public static void main(String[] args){
		JFrame fenetre = new JFrame("Connexion");
		fenetre.setContentPane(new ConnectionView());
		fenetre.setVisible(true);
		fenetre.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		fenetre.pack();
	}
}
