/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ChatApplication;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author lenovo
 */

public class MessagePage extends javax.swing.JFrame {
  
    private String n;
        private String u;
        private String p;
        private String r_u;
        private String r_n;
         private String lastCheckedTime = "2000-01-01 00:00:00"; 
    /**
     * Creates new form MessagePage
     */
    public MessagePage(String n,String p,String u,String r_u,String r_n) {
        this.n=n;
        this.u=u;
        this.p=p;
        this.r_u=r_u;
        this.r_n=r_n;
        initComponents();
        chatPanel.setLayout(new BoxLayout(chatPanel, BoxLayout.Y_AXIS));
        loadMessages(this.u,this.r_u); 
        checkNewMessages(u);
        l1.setText(r_u);
           l2.setText(r_n);
    }
    
       public void addMessage(String message, String timeStamp, boolean isSentByMe) {
           
        JPanel messagePanel = new JPanel(new FlowLayout(isSentByMe ? FlowLayout.RIGHT : FlowLayout.LEFT));
        JLabel messageLabel = new JLabel(message);
        messageLabel.setOpaque(true);
        messageLabel.setBackground(isSentByMe ? Color.GREEN : Color.GRAY);
        Font font = new Font("Arial", Font.PLAIN, 18);
        messageLabel.setFont(font);
        messageLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        JLabel timestampLabel = new JLabel("(" + timeStamp + ")");
        timestampLabel.setOpaque(true);
        timestampLabel.setBackground(isSentByMe ? Color.GREEN : Color.GRAY);
        Font timestampFont = new Font("Arial", Font.ITALIC, 12);  
        timestampLabel.setFont(timestampFont);
        messagePanel.add(messageLabel);
        messagePanel.add(timestampLabel);
        messagePanel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        
        chatPanel.add(messagePanel);

        chatPanel.revalidate();
        chatPanel.repaint();
    }

    
    
    public void sendMessage(String sender, String receiver, String message) {
        try {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            String formattedTime = now.format(formatter);

            Class.forName("org.sqlite.JDBC");
            Connection con = DriverManager.getConnection("dbc:sqlite:Chat.db");
            PreparedStatement ps = con.prepareStatement("insert into message (s_username, r_username, message_text, sent_time) values(?, ?, ?, ?)");
            ps.setString(1, sender);
            ps.setString(2, receiver);
            ps.setString(3, message);
            ps.setString(4, formattedTime);
            ps.executeUpdate();
            con.close();

            addMessage(message, formattedTime, true); 
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
       

public void checkNewMessages(String myUsername) {
    new Timer(500, new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            try {
                Class.forName("org.sqlite.JDBC");
                Connection con = DriverManager.getConnection("dbc:sqlite:Chat.db");
                PreparedStatement ps = con.prepareStatement( "SELECT * FROM message WHERE (s_username=? OR s_username=?) AND (r_username=? OR r_username=?) AND id > ? ORDER BY id DESC");
                ps.setString(1, u); 
                ps.setString(2, r_u);
                ps.setString(3, u); 
                ps.setString(4, r_u);
                ps.setString(5, lastCheckedTime);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String message = rs.getString("message_text");
                    String sender = rs.getString("s_username");
                    String timeStamp = rs.getString("sent_time");
                    if (u.equals(sender)) {
                        addMessage(message, timeStamp, true);
                    } else {
                        addMessage(message, timeStamp, false); 
                    }
                }
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                lastCheckedTime = now.format(formatter);

                con.close();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }).start();
}
        

   public void loadMessages(String u,String r_u) {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection con = DriverManager.getConnection("dbc:sqlite:Chat.db");
            PreparedStatement ps = con.prepareStatement("select * from message where (s_username=? or s_username=?) and (r_username=? or r_username=?) ORDER BY id asc");
            ps.setString(1, u); 
            ps.setString(2, r_u);
            ps.setString(3, u); 
            ps.setString(4, r_u);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String message = rs.getString("message_text");
                String sender = rs.getString("s_username");
                String timeStamp = rs.getString("sent_time");
                boolean isSentByMe = sender.equals(this.u); 
                addMessage(message, timeStamp, isSentByMe);  
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        l1 = new javax.swing.JLabel();
        l2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        msg = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        chatPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 255, 255));

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/back.png"))); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        l1.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        l1.setForeground(new java.awt.Color(51, 51, 51));

        l2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        l2.setForeground(new java.awt.Color(51, 51, 51));
        l2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(l1, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(109, 109, 109)
                .addComponent(l2, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(l1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(l2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
        );

        jPanel3.setBackground(new java.awt.Color(255, 153, 255));

        jButton1.setBackground(new java.awt.Color(204, 204, 204));
        jButton1.setText("SEND");
        jButton1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton1.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        msg.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        msg.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        msg.setMargin(new java.awt.Insets(0, 6, 2, 0));

        javax.swing.GroupLayout chatPanelLayout = new javax.swing.GroupLayout(chatPanel);
        chatPanel.setLayout(chatPanelLayout);
        chatPanelLayout.setHorizontalGroup(
            chatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 407, Short.MAX_VALUE)
        );
        chatPanelLayout.setVerticalGroup(
            chatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 449, Short.MAX_VALUE)
        );

        jScrollPane3.setViewportView(chatPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(msg, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jScrollPane3)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(msg, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        String message = msg.getText();
        if (!message.trim().isEmpty()) {
            sendMessage(this.u, this.r_u, message);  
            msg.setText("");
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        V_Rem_MSG v1=new V_Rem_MSG(n,u,p);
        v1.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

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
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MessagePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MessagePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MessagePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MessagePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel chatPanel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel l1;
    private javax.swing.JLabel l2;
    private javax.swing.JTextField msg;
    // End of variables declaration//GEN-END:variables
}


