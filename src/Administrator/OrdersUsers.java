package Administrator;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.beans.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import java.sql.Connection;
import java.sql.DriverManager;
import BookBoutique.ConnectionDB;
import BookBoutique.Controlleur;

public class OrdersUsers extends JFrame {
	private static JPanel wrapper;
static ConnectionDB con=new ConnectionDB();

	public OrdersUsers(boolean visble)  {
		
		this.wrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
		setIconImage(Controlleur.logo.getImage());
		setTitle("BookBoutique");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setLayout(new FlowLayout(FlowLayout.CENTER));
		add(wrapper);
		setSize(600,600);
		
		// Ajout du tableau à un JScrollPane pour permettre le défilement si nécessaire
        JScrollPane scrollPane = new JScrollPane(createTable());

        // Ajout du JScrollPane à la JFrame
       getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		this.setVisible(visble);
		
	}
	
	public static JTable createTable()  {
    
        // Création du tableau
        DefaultTableModel model = new DefaultTableModel();
        
        // Ajout des colonnes au modèle
        model.addColumn("IDorders");
        model.addColumn("Libélé");
        model.addColumn("Nombre");
        
     // Chargement des données depuis la base de données
       // chargerDonneesDepuisBDD(model, name );
        
     // Création du tableau en utilisant le modèle
        JTable table = new JTable(model);
       
       return table;
		
	}
	
}
	
	


