/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package chatapplication;

import com.mysql.cj.jdbc.StatementImpl;
import java.awt.BorderLayout;
import java.awt.Container;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author zIgma
 * Ref1: https://www.geeksforgeeks.org/java-swing-jpanel-with-examples/#:~:text=JPanel%2C%20a%20part%20of%20the,not%20have%20a%20title%20bar.
 * Ref2: https://stackoverflow.com/questions/8203824/problems-dynamically-changing-jpanels-on-jframe
 * Ref3: https://stackoverflow.com/questions/6714045/how-to-resize-jlabel-imageicon
 * Ref4: https://www.w3schools.com/java/java_files_read.asp
 */
public class Main extends javax.swing.JFrame {

    public JPanel chatPanel = new ChatPanel();
    public JPanel settingsPanel = new SettingsPanel();
    
    public Container contentPane = null;
    public JPanel activePanel = chatPanel;
    public JPanel previousPanel = chatPanel;
    
    public Connection conn = null;
    
    private static JFrame mainInstance = null;
    
    public int myUserId = -1;
    public int receiverId = -1;
    
    /**
     * Startup function
     */
    public Main() {
        initComponents();
        
        // Database initialization
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = (Connection)DriverManager.getConnection("jdbc:mysql://localhost:3306/chat_app", "admin", "1234");
            if (conn == null) {
                System.out.println("Database error!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("Database initialized successfully!");
        }
        
        // -1 means no user assigned
        if (myUserId == -1)
        {
            try {
                File config = new File("data.txt");
                Scanner sc = new Scanner(config);
                String line = sc.nextLine();
                System.out.println(line);
                myUserId = Integer.parseInt(line.split(":")[1]);
                line = sc.nextLine();
                System.out.println(line);
                receiverId = Integer.parseInt(line.split(":")[1]);
            } catch (FileNotFoundException ex) {
                System.out.println("Datafile not found!");
                // Show registration form
            }
        }
        
        if (myUserId == -1) {
            // Show error message
        } else {
            // TODO: check user-id is in database?, if not show register form
            try {
                StatementImpl stmt = (StatementImpl) conn.createStatement();
                String sql = "SELECT * FROM chat_user WHERE user_id=" + myUserId;
                ResultSet rSet = stmt.executeQuery(sql);
                if (!rSet.next()) {
                    // User is not is the db show registration
                    System.out.println("There is no user like this!");
                }
            } catch (SQLException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(activePanel, BorderLayout.CENTER);
    }
    
    public void setActivePanel(JPanel panel) {
        if (contentPane != null) {
            previousPanel = activePanel;
            contentPane.remove(activePanel);
            activePanel = panel;
            contentPane.add(activePanel, BorderLayout.CENTER);
            pack();
        }
        
        revalidate();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Simple Chat Application");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 665, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 498, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public static JFrame getInstance() {
        if (mainInstance == null) {
            mainInstance = new Main();
        }
        
        return mainInstance;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Main.getInstance().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}