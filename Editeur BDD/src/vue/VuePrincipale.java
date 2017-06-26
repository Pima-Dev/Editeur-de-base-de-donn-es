package vue;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import controleur.ChampsListener;
import controleur.PresserBoutonListener;
import controleur.SelectionListener;
import controleur.TouchePresseListener;
/**
 * s'affiche lorsque l'utilisateur se connecte, elle permet d'accéder à toutes les options de gestion de la BDD
 */
public class VuePrincipale extends JPanel{
	 /**
	  * la racine de référence qui permet d'accéder à toutes les vues
	  */
	 private Fenetre fenetre;
	/**
	 * contient la liste des tables, le panneau du tableau et le panneau de boutons
	 */
	private JPanel panneauListeBoutonTable;
	
	/**
	 * contient le panneau du tableau et le panneau de boutons
	 */
	private JPanel panneauBoutonTable;
	//menu
	/**
	 *contient la barre de menus
	 */
	private JPanel panneauMenu;
	/**
	 * accéder aux menus fichier, outils et aide
	 */
	private JMenuBar menu;
	
	/**
	 * accéder aux options nouveau, supprimerBDD, ouvrir et exporter en pdf
	 */
	private JMenu fichier;
	/**
	 * créer une nouvelle BDD
	 */
	private JMenuItem nouveau;
	/**
	 * supprimer le BDD courante
	 */
	private JMenuItem supprimerBDD;
	/**
	 * ouvrir une BDD
	 */
	private JMenuItem ouvrir;
	/**
	 * exporter la table courante dans un fichier PDF
	 */
	private JMenuItem exporterEnPDF;
	
	/**
	 * accéder aux options console, recherche, ajouter un tuple et supprimer une table
	 */
	private JMenu outils;
	/**
	 * accéder à la console pour éxécuter du code SQL
	 */
	private JMenuItem accederALaConsole;
	/**
	 * rechercher une chaine de caractère dans la table courante
	 */
	private JMenuItem rechercher;
	/**
	 * ajouter un tuple dans la table courante
	 */
	private JMenuItem ajouterUnTuple;
	/**
	 * supprmier la table courante
	 */
	private JMenuItem supprimerUneTable;
	
	/**
	 * accéder à l'option aide en ligne
	 */
	private JMenu aide;
	/**
	 * ouvre un navigateur avec le manuel d'utilisation
	 */
	private JMenuItem aideEnLigne;
	
	//liste table
	/**
	 * affiche le titre de la BDD courante
	 */
	private JLabel lTitreBDD;
	/**
	 * contient la liste des tables et le titre de la BDD
	 */
	private JPanel panneauListeTable;
	/**
	 * contient la liste des tables et permet de la faire défiler
	 */
	private JScrollPane panneauTableau;
	/**
	 * contient la liste des tables de la BDD
	 */
	private JList<String> tableauListeTable;
	/**
	 * contient les information de construction de la liste de table
	 */
	private DefaultListModel<String> modeleListe;
	
	//boutons
	/**
	 * permet de laisser une marge entre le bord de la fenetre et les outils de gestion
	 */
	 private JPanel marge;
	/**
	 * contient les panneau ajouter, chercher, et boutonsDroite
	 */
	 private JPanel panneauBoutons;
	 
	 /**
	  * contient les boutons d'ajout d'attribut, d'ajout de tuple, de suppression de table et de suppression d'attribut
	  */
	 private JPanel ajouter;
	 /**
	  * ajouter un tuple
	  */
	 private JButton ajouterTuple;
	 /**
	  * ajouter un attribut
	  */
	 private JButton ajouterAttribut;
	 /**
	  * supprimer la table courante
	  */
	 private JButton supprimerTable;
	 /**
	  * supprimer un attribut
	  */
	 private JButton supprimerAttribut;
	 
	 /**
	  * permet d'avoir une marge entre les recherches et le reste de la fenetre
	  */
	 private JPanel margeRecherche;
	 /**
	  * contient la barre de recherche, le titre et les options supplémentaires
	  */
	 private JPanel chercher;
	 /**
	  * contient la barre de recherche et le bouton de recherche avancée
	  */
	 private JPanel barreRecherche;
	 /**
	  * affiche le titre des recherches
	  */
	 private JLabel lChercher;
	 /**
	  * rechercher des éléments dans la table courante
	  */
	 private JTextField fChercher;
	 /**
	  * accéder aux options de recherche avancées
	  */
	 private JButton optionRecherche;
	 
	 /**
	  * contient les boutons modifier contrainte, console et log
	  */
	 private JPanel boutonsDroites;
	 /**
	  * modifier, ajouter et supprimer des contraintes sur la table courante
	  */
	 private JButton modifierContrainte;
	 /**
	  * accéder à la console pour éxécuter du code SQL
	  */
	 private JButton console;
	 /**
	  * accéder aux log SQL
	  */
	 private JButton log;
	 
	 //table
	 /**
	  * affiche la table courante sous forme de tableau
	  */
	 private JTable table;
	 /**
	  * contient le tableau et permet de le faire défiler
	  */
	 private JScrollPane scrollTable;
	 /**
	  * contient le tableau
	  */
	 private JPanel panneauTable;
	 /**
	  * contient les information pour créer le tableau
	  */
	 private ModeleTable dm;
	 	 
	 /**
	  * construit le panneau en appelant les methodes menu, liste table et boutons
	  * @param fenetre la racine de référence qui permet d'accéder à toutes les vues
	  */
	public VuePrincipale(Fenetre fenetre){
		this.fenetre = fenetre;
		this.fenetre.setVuePrincipale(this);
		this.panneauTable = new JPanel();
		menu();
		listeTable();
		boutons();
		//String[][] tab = new String[][]{ {"1", "1", "1", "1"}, {"2", "2", "2", "2"} };
		//table(tab, new String[]{"test1", "test2", "test3", "test4"});
		panneauBoutonTable = new JPanel();
		panneauBoutonTable.setLayout(new BorderLayout());
		panneauBoutonTable.add(marge,BorderLayout.SOUTH);
		panneauBoutonTable.add(panneauTable,BorderLayout.CENTER);
		panneauListeBoutonTable = new JPanel();
		panneauListeBoutonTable.setLayout(new BorderLayout());
		panneauListeBoutonTable.add(panneauListeTable,BorderLayout.WEST);
		panneauListeBoutonTable.add(panneauBoutonTable,BorderLayout.CENTER);
		this.setLayout(new BorderLayout());
		this.add(panneauListeBoutonTable, BorderLayout.CENTER);
		this.add(panneauMenu, BorderLayout.NORTH);

	}
	
	/**
	 * construit la barre de menu
	 */
	public void menu(){
		panneauMenu = new JPanel();
		
		menu = new JMenuBar();
		panneauMenu.setLayout(new FlowLayout(FlowLayout.LEFT));

		
		fichier = new JMenu("    Fichier    ");
		nouveau = new JMenuItem("Nouveau");
		nouveau.setName("MenuNouveau");
		nouveau.addActionListener(new PresserBoutonListener(this.fenetre));
		supprimerBDD = new JMenuItem("Supprimer la BDD");
		supprimerBDD.setName("MenuSupprimerBDD");
		supprimerBDD.addActionListener(new PresserBoutonListener(this.fenetre));
		ouvrir = new JMenuItem("Ouvrir");
		ouvrir.setName("MenuOuvrir");
		ouvrir.addActionListener(new PresserBoutonListener(this.fenetre));
		exporterEnPDF = new JMenuItem("Exporter en PDF");
		exporterEnPDF.setName("MenuExporterEnPDF");
		exporterEnPDF.addActionListener(new PresserBoutonListener(this.fenetre));
		
		outils = new JMenu("    Outils    ");
		accederALaConsole = new JMenuItem("Accéder à la console");
		accederALaConsole.setName("acceder a la console");
		accederALaConsole.addActionListener(new PresserBoutonListener(this.fenetre));
		rechercher = new JMenuItem("Rechercher");
		rechercher.setName("faire une recherche");
		rechercher.addActionListener(new PresserBoutonListener(this.fenetre));
		ajouterUnTuple = new JMenuItem("Ajouter un tuple");
		ajouterUnTuple.setName("ajouter un tuple");
		ajouterUnTuple.addActionListener(new PresserBoutonListener(this.fenetre));
		supprimerUneTable = new JMenuItem("Supprimer la table");
		supprimerUneTable.setName("supprimer la table");
		supprimerUneTable.addActionListener(new PresserBoutonListener(this.fenetre));
		
		aide = new JMenu("    Aide    ");
		aideEnLigne = new JMenuItem("Aide syntaxe MYSQL");
		aideEnLigne.setName("aide syntaxe MYSQL");
		aideEnLigne.addActionListener(new PresserBoutonListener(this.fenetre));
		
		menu.add(fichier);
		menu.add(outils);
		menu.add(aide);
		
		fichier.add(nouveau);
		fichier.add(ouvrir);
		fichier.add(supprimerBDD);
		fichier.add(exporterEnPDF);
		
		outils.add(accederALaConsole);
		outils.add(rechercher);
		outils.add(ajouterUnTuple);
		outils.add(supprimerUneTable);
		
		aide.add(aideEnLigne);
		
		panneauMenu.add(menu);
	}
	
	/**
	 * construit la liste des tables
	 */
	public void listeTable(){ 
		
		lTitreBDD = new JLabel("nouvelle BDD");
		lTitreBDD.setHorizontalAlignment(SwingConstants.CENTER);
		panneauListeTable = new JPanel();
		modeleListe = new DefaultListModel<String>();
		modeleListe.addElement("+ Nouvelle Table");
		tableauListeTable = new JList<String>(modeleListe);
		tableauListeTable.setName("jlist des tables");
		tableauListeTable.addListSelectionListener(new SelectionListener(this.fenetre));
		tableauListeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableauListeTable.setLayoutOrientation(JList.VERTICAL);
		tableauListeTable.setVisibleRowCount(5);
		panneauTableau = new JScrollPane(tableauListeTable);
		panneauTableau.setPreferredSize(new Dimension(250, 80));
		panneauListeTable.setLayout(new BorderLayout());
		panneauListeTable.add(panneauTableau, BorderLayout.CENTER);
		panneauListeTable.add(lTitreBDD, BorderLayout.NORTH);
		
		
	}
	
	/**
	 * construit les boutons
	 */
	 public void boutons(){
		 panneauBoutons = new JPanel();
		 panneauBoutons.setLayout(new GridLayout(1,3,20,10));
		 Font font = new Font("Arial",Font.BOLD,16);
		 
		 ajouter = new JPanel();
		 ajouter.setLayout(new GridLayout(2,2,10,10));
		 ajouterTuple = new JButton("Ajouter un tuple");
		 ajouterTuple.setName("ajouter un tuple");
		 ajouterTuple.addActionListener(new PresserBoutonListener(this.fenetre));
		 ajouter.add(ajouterTuple);
		 ajouterAttribut = new JButton("Ajouter un attribut");
		 ajouterAttribut.setName("ajouter attribut");
		 ajouterAttribut.addActionListener(new PresserBoutonListener(this.fenetre));
		 this.supprimerAttribut = new JButton("Supprimer un attribut");
		 this.supprimerAttribut.setName("supprimer attribut");
		 this.supprimerAttribut.addActionListener(new PresserBoutonListener(this.fenetre));
		 this.supprimerTable = new JButton("Supprimer la table");
		 this.supprimerTable.setName("supprimer table");
		 this.supprimerTable.addActionListener(new PresserBoutonListener(this.fenetre));
		 ajouter.add(ajouterAttribut);
		 ajouter.add(this.supprimerTable);
		 ajouter.add(this.supprimerAttribut);
		 panneauBoutons.add(ajouter);
		 
		 
		 chercher = new JPanel();
		 chercher.setLayout(new BorderLayout());
		 barreRecherche = new JPanel();
		 barreRecherche.setLayout(new BorderLayout());
		 lChercher = new JLabel("Chercher");
		 lChercher.setFont(font);
		 lChercher.setHorizontalAlignment(SwingConstants.CENTER);
		 fChercher = new JTextField("Chercher les occurences");
		 fChercher.setName("BarreRecherche");
		 fChercher.addFocusListener(new ChampsListener(this.fenetre));
		 fChercher.addKeyListener(new TouchePresseListener(this.fenetre));
		 optionRecherche = new JButton(new ImageIcon("src/ressources/parametre.png"));
		 optionRecherche.setBorderPainted(false);
		 optionRecherche.setContentAreaFilled(false);
		 optionRecherche.setName("OptionRecherche");
		 optionRecherche.addActionListener(new PresserBoutonListener(fenetre));
		 barreRecherche.add(fChercher, BorderLayout.CENTER);
		 barreRecherche.add(optionRecherche, BorderLayout.EAST);
		 margeRecherche = new JPanel();
		 margeRecherche.setLayout(new BorderLayout());
		 margeRecherche.add(new JLabel("        "), BorderLayout.NORTH);
		 margeRecherche.add(new JLabel("        "), BorderLayout.SOUTH);
		 margeRecherche.add(barreRecherche, BorderLayout.CENTER);
		 chercher.add(lChercher, BorderLayout.NORTH);
		 chercher.add(margeRecherche, BorderLayout.CENTER);
		 panneauBoutons.add(chercher);
		 
		 boutonsDroites = new JPanel();
		 boutonsDroites.setLayout(new GridLayout(3,1,5,5));
		 modifierContrainte = new JButton("Modifier les contraintes");
		 modifierContrainte.setName("modifier les contraintes");
		 modifierContrainte.addActionListener(new PresserBoutonListener(this.fenetre));
		 this.console = new JButton("Accéder à la console");
		 this.console.setName("acceder a la console");
		 this.console.addActionListener(new PresserBoutonListener(this.fenetre));
		 this.log = new JButton("Voir les logs");
		 this.log.setName("voir les logs");
		 this.log.addActionListener(new PresserBoutonListener(this.fenetre));
		 boutonsDroites.add(modifierContrainte);
		 boutonsDroites.add(this.console);
		 boutonsDroites.add(this.log);
		 panneauBoutons.add(boutonsDroites);
		 
		 
		 marge = new JPanel();
		 marge.setLayout(new BorderLayout());
		 marge.add(new JLabel("        "), BorderLayout.WEST);
		 marge.add(new JLabel("        "), BorderLayout.EAST);
		 marge.add(new JLabel("        "), BorderLayout.SOUTH);
		 marge.add(new JLabel("        "), BorderLayout.NORTH);
		 marge.add(panneauBoutons, BorderLayout.CENTER);
	}
	
	 /**
	  * permet de créer et de modifier les valeurs du tableau affiché
	  * @param tab un tableau de chaine de caractères qui contient les valeurs à insérer dans le tableau affiché
	  * @param titre un tableau contenant les titres des attributs du tableau
	  */
	public void insererValeursDansTab(String[][] tab, String[] titre){
		this.resetJTable();
		dm = new ModeleTable(tab.length, tab[0].length+2,fenetre);
		String[] lesTitres = new String[titre.length+2];
		
		for(int i = 0; i<titre.length; i++){
			lesTitres[i]=titre[i];
		}
		lesTitres[lesTitres.length-2] = "Modifier";
		lesTitres[lesTitres.length-1] = "Supprimer";
		
        dm.setDataVector(tab, lesTitres);
        table = new JTable(dm);
        table.getColumn("Modifier").setCellRenderer(new RenduCellule("modifier", this.fenetre));
        table.getColumn("Modifier").setCellEditor(new EditeurCellule(new JCheckBox(),"modifier",dm,fenetre));
        table.getColumn("Supprimer").setCellRenderer(new RenduCellule("supprimer", this.fenetre));
        table.getColumn("Supprimer").setCellEditor(new EditeurCellule(new JCheckBox(),"supprimer",dm,fenetre));
        table.setRowHeight(30);
        if(table.getColumnCount()>15){
        	table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        }
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);
		scrollTable = new JScrollPane(table);
		scrollTable.createVerticalScrollBar();
		scrollTable.createHorizontalScrollBar();
		panneauTable.setLayout(new BorderLayout());
		panneauTable.add(scrollTable, BorderLayout.CENTER);
		this.fenetre.getFenetre().setVisible(true);
	}
	
	/**
	 * réinitialise la table
	 */
	public void resetJTable(){
		this.panneauTable.removeAll();
		this.panneauTable.repaint();
	}
	
	/**
	 * réinitialise la liste de table
	 */
	public void resetListeTable(){
		this.modeleListe.removeAllElements();
		this.modeleListe.addElement("+ Nouvelle Table");
	}
	
	/**
	 * ajoute un nom de table à la liste
	 * @param table le nom de la table à ajouter
	 */
	public void ajouterTable(String table){
		modeleListe.set(tableauListeTable.getModel().getSize()-1,table);
		modeleListe.addElement("+ Nouvelle Table");
	}
	
	/**
	 * accès à la table courante dans la JList
	 * @return la table courante dans la JList
	 */
	public String getCurrentTable(){
		return this.tableauListeTable.getSelectedValue();
	}
	
	/**
	 * accès au jMenuItem accederALaConsole
	 * @return le jMenuItem accederALaConsole
	 */
	public JMenuItem getAccederALaConsole() {
		return accederALaConsole;
	}


	/**
	 * accès au jMenu aide
	 * @return le jMenu aide
	 */
	public JMenu getAide() {
		return aide;
	}

	/**
	 * accès au JMenuItem fichier
	 * @return le JMenuItem fichier
	 */
	public JMenu getFichier() {
		return fichier;
	}

	/**
	 * accès au JMenuItem supprimerBDD
	 * @return le JMenuItem supprimerBDD
	 */
	public JMenuItem getSupprimerBDD() {
		return supprimerBDD;
	}

	/**
	 * accès au JMenuItem rechercher
	 * @return le JMenuItem rechercher
	 */
	public JMenuItem getRechercher() {
		return rechercher;
	}

	/**
	 * accès au jMenuItem aideEnLigne
	 * @return le jMenuItem aideEnLigne
	 */
	public JMenuItem getAideEnLigne() {
		return aideEnLigne;
	}


	/**
	 * accès au jMenuItem nouveau
	 * @return le jMenuItem nouveau
	 */
	public JMenuItem getNouveau() {
		return nouveau;
	}


	/**
	 * accès au jMenuItem ouvrir
	 * @return le jMenuItem ouvrir
	 */
	public JMenuItem getOuvrir() {
		return ouvrir;
	}


	/**
	 * accès au jMenuItem exporterEnPDF
	 * @return le jMenuItem exporterEnPDF
	 */
	public JMenuItem getExporterEnPDF() {
		return exporterEnPDF;
	}

	/**
	 * accès au jMenuItem ajouterUnTuple
	 * @return le jMenuItem ajouterUnTuple
	 */
	public JMenuItem getAjouterUnTuple() {
		return ajouterUnTuple;
	}

	/**
	 * accès au jMenuItem gererDesAttributs
	 * @return le jMenuItem gererDesAttributs
	 */
	public JMenuItem getSupprimerUneTable() {
		return supprimerUneTable;
	}


	/**
	 * accès au JButton ajouterTuple
	 * @return le JButton ajouterTuple
	 */
	public JButton getAjouterTuple() {
		return ajouterTuple;
	}


	/**
	 * accès au JButton ajouterAttribut
	 * @return le JButton ajouterAttribut
	 */
	public JButton getAjouterAttribut() {
		return ajouterAttribut;
	}

	/**
	 * accès au JButton optionRecherche
	 * @return le JButton optionRecherche
	 */
	public JButton getOptionRecherche() {
		return optionRecherche;
	}


	/**
	 * accès au JButton ajouterContrainte
	 * @return le JButton ajouterContrainte
	 */
	public JButton getAjouterContrainte() {
		return modifierContrainte;
	}
	
	/**
	 * accès au JButton console
	 * @return le JButton console
	 */
	public JButton getConsole(){
		return this.console;
	}
	
	/**
	 * accès au JButton log
	 * @return le JButton log
	 */
	public JButton getLog(){
		return this.log;
	}

	/**
	 * accès au JTextField fChercher
	 * @return le JTextField fChercher
	 */
	public JTextField getfChercher() {
		return fChercher;
	}
	
	/**
	 * accès à la JTable table
	 * @return la JTable table
	 */
	public JTable getTable() {
		return table;
	}
	
	/**
	 * accès au modèle de la liste de table
	 * @return le modèle de la liste de table, modeleListe
	 */
	public DefaultListModel getListModel(){
		return this.modeleListe;
	}
	
	/**
	 * accès au modèle de la table
	 * @return le modèle de la table, dm
	 */
	public ModeleTable getDm() {
		return dm;
	}
	
	/**
	 * accès à la liste de table
	 * @return la JList des table, tableauListeTable
	 */
	public JList<String> getJList(){
		return this.tableauListeTable;
	}
}