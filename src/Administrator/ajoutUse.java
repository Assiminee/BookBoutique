package Administrator;




import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ajoutUse extends JFrame {
    private JTextField textFieldIdentifiant;
    private JTextField textFieldNom;
    private JTextField textFieldEmail;

    public ajoutUse() {
        // Utilisation du look and feel Nimbus pour un aspect moderne
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        setTitle("Ajout d'Utilisateur");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel labelIdentifiant = new JLabel("Identifiant:");
        textFieldIdentifiant = new JTextField(15);

        JLabel labelNom = new JLabel("Nom:");
        textFieldNom = new JTextField(15);

        JLabel labelEmail = new JLabel("Email:");
        textFieldEmail = new JTextField(15);

        JButton boutonAjoutUtilisateur = new JButton("Ajouter Utilisateur");
        boutonAjoutUtilisateur.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ajouter l'utilisateur ici
                String identifiant = textFieldIdentifiant.getText();
                String nom = textFieldNom.getText();
                String email = textFieldEmail.getText();

            }
        });

        // Ajouter des composants avec GridBagConstraints pour un meilleur contrôle de la mise en page
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(labelIdentifiant, gbc);

        gbc.gridx = 1;
        panel.add(textFieldIdentifiant, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(labelNom, gbc);

        gbc.gridx = 1;
        panel.add(textFieldNom, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(labelEmail, gbc);

        gbc.gridx = 1;
        panel.add(textFieldEmail, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(boutonAjoutUtilisateur, gbc);

        getContentPane().add(panel);
        setSize(400, 300);
        setLocationRelativeTo(null); // Centrer la fenêtre
        setVisible(true);
    }

    
  
}

