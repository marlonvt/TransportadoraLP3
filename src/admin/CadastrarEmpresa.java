package admin;

import db.ConnectionFactory;
import info.Empresa;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Random;
import javax.swing.JOptionPane;
import javax.swing.border.TitledBorder;
import javax.swing.text.MaskFormatter;

public class CadastrarEmpresa extends javax.swing.JFrame {
    
    private Principal principal;
    private Empresa empresa;
    private String action;

    /**
     * Creates new form CadastrarEmpresa
     */
    public CadastrarEmpresa(Principal principal) {
        this.principal = principal;
        initComponents();
        try {
            MaskFormatter mask = new MaskFormatter("##.###.###/####-##");
            mask.install(tfCnpj);
        } catch (ParseException ex) {
            System.err.println("Falha ao criar mascara de campo: " + ex.getMessage());
        }
    }
    
    public CadastrarEmpresa(Principal principal, Empresa empresa) {
        this.principal = principal;
        initComponents();
        
        try {
            MaskFormatter mask = new MaskFormatter("##.###.###/####-##");
            mask.install(tfCnpj);
        } catch (ParseException ex) {
            System.err.println("Falha ao criar mascara de campo: " + ex.getMessage());
        }
        
        tfRzSocial.setText(empresa.getRazaoSocial());
        tfEndereco.setText(empresa.getEndereco());
        tfCnpj.setText(empresa.getCnpj());
        
        this.setTitle("Atualizar Empresa");
        TitledBorder border = (TitledBorder) panelPrincipal.getBorder();
        border.setTitle("Atualizar Empresa");
        
        this.action = "update";
        this.empresa = empresa;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelPrincipal = new javax.swing.JPanel();
        lblRzSocial = new javax.swing.JLabel();
        tfRzSocial = new javax.swing.JTextField();
        lblEndereco = new javax.swing.JLabel();
        tfEndereco = new javax.swing.JTextField();
        lblCnpj = new javax.swing.JLabel();
        tfCnpj = new javax.swing.JFormattedTextField();
        btnCancelar = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Transportadora JM - Cadastrar Empresa");
        setResizable(false);
        setType(java.awt.Window.Type.POPUP);

        panelPrincipal.setBorder(javax.swing.BorderFactory.createTitledBorder("Cadastrar Empresa"));

        lblRzSocial.setText("Razão Social");

        lblEndereco.setText("Endereço");

        lblCnpj.setText("CNPJ");

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnSalvar.setText("Salvar");
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelPrincipalLayout = new javax.swing.GroupLayout(panelPrincipal);
        panelPrincipal.setLayout(panelPrincipalLayout);
        panelPrincipalLayout.setHorizontalGroup(
            panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPrincipalLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tfRzSocial)
                    .addComponent(tfEndereco)
                    .addGroup(panelPrincipalLayout.createSequentialGroup()
                        .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblRzSocial)
                            .addComponent(lblEndereco)
                            .addComponent(lblCnpj))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(tfCnpj)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPrincipalLayout.createSequentialGroup()
                        .addGap(0, 130, Short.MAX_VALUE)
                        .addComponent(btnSalvar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelar)))
                .addContainerGap())
        );
        panelPrincipalLayout.setVerticalGroup(
            panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPrincipalLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblRzSocial)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tfRzSocial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblEndereco)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tfEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblCnpj)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tfCnpj, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar)
                    .addComponent(btnSalvar))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        String razaoSocial = tfRzSocial.getText();
        String endereco = tfEndereco.getText();
        String cnpj = tfCnpj.getText();
        
        if (razaoSocial.isEmpty() || endereco.isEmpty() || cnpj.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Todos os campos devem ser preenchidos.", "Erro", JOptionPane.ERROR_MESSAGE);
        } else {
            Connection conn = ConnectionFactory.createConnection();
            try {
                
                if ("update".equals(action)) {
                    PreparedStatement ps = conn.prepareStatement("UPDATE empresa SET razao_social = ?, endereco = ?, cnpj = ? WHERE id = ?");
                    ps.setString(1, razaoSocial);
                    ps.setString(2, endereco);
                    ps.setString(3, cnpj.replaceAll("[./-]", ""));
                    ps.setString(4, empresa.getId());
                    
                    int result = ps.executeUpdate();
                    if (result> 0) {
                        this.dispose();
                        principal.populateEmpresasTable();
                    }
                } else {
                    PreparedStatement statement = conn.prepareStatement("INSERT INTO empresa VALUES (?, ?, ?, ?)");
                    Random random = new Random();
                    int empId = random.nextInt(1000) + 1001;
                    statement.setString(1, String.valueOf(empId));
                    statement.setString(2, razaoSocial);
                    statement.setString(3, endereco);
                    statement.setString(4, cnpj.replaceAll("[./-]", ""));

                    int result = statement.executeUpdate();
                    if (result> 0) {
                        this.dispose();
                        principal.populateEmpresasTable();
                    }
                }
            } catch (SQLException ex) {
                System.err.println("Falha ao executar operacao no banco de dados: " + ex.getMessage());
            } finally {
                ConnectionFactory.closeConnection(conn);
            }
        }
    }//GEN-LAST:event_btnSalvarActionPerformed

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
                    javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CadastrarEmpresa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CadastrarEmpresa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CadastrarEmpresa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CadastrarEmpresa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
//                new CadastrarEmpresa().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JLabel lblCnpj;
    private javax.swing.JLabel lblEndereco;
    private javax.swing.JLabel lblRzSocial;
    private javax.swing.JPanel panelPrincipal;
    private javax.swing.JFormattedTextField tfCnpj;
    private javax.swing.JTextField tfEndereco;
    private javax.swing.JTextField tfRzSocial;
    // End of variables declaration//GEN-END:variables
}