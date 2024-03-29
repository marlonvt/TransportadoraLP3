package admin;

import db.ConnectionFactory;
import info.Encomenda;
import info.Veiculo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Random;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.border.TitledBorder;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import javax.swing.text.NumberFormatter;

public class CadastrarEncomenda extends javax.swing.JFrame {
    
    private Principal principal;
    private Encomenda encomenda;
    private String action;

    /**
     * Creates new form CadastrarEncomenda
     */
    public CadastrarEncomenda(Principal principal) {
        this.principal = principal;
        initComponents();
        
        Connection conn = ConnectionFactory.createConnection();
        Vector<Veiculo> allVeiculos = new Vector();
        try {
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM veiculo");
            while (result.next()) {
                allVeiculos.add(createVeiculoObject(result));
            }
        } catch (SQLException ex) {
            System.err.println("Falha ao consultar o banco de dados: " + ex.getMessage());
        } finally {
            ConnectionFactory.closeConnection(conn);
        }
        
        if (!allVeiculos.isEmpty()) {
            cbVeiculo.setModel(new DefaultComboBoxModel(allVeiculos));
        }
        
        DecimalFormat dFormat = new DecimalFormat("#,###,###.00");
        NumberFormatter formatter = new NumberFormatter(dFormat);
        formatter.setFormat(dFormat);
        formatter.setAllowsInvalid(false);
        
        tfValor.setFormatterFactory(new DefaultFormatterFactory(formatter));
    }
    
    public CadastrarEncomenda(Principal principal, Encomenda encomenda) {
        this.principal = principal;
        initComponents();
        
        Connection conn = ConnectionFactory.createConnection();
        Vector<Veiculo> allVeiculos = new Vector();
        try {
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM veiculo");
            while (result.next()) {
                allVeiculos.add(createVeiculoObject(result));
            }
        } catch (SQLException ex) {
            System.err.println("Falha ao consultar o banco de dados: " + ex.getMessage());
        } finally {
            ConnectionFactory.closeConnection(conn);
        }
        
        if (!allVeiculos.isEmpty()) {
            cbVeiculo.setModel(new DefaultComboBoxModel(allVeiculos));
        }
        
        DecimalFormat dFormat = new DecimalFormat("#,###,###.00");
        NumberFormatter formatter = new NumberFormatter(dFormat);
        formatter.setFormat(dFormat);
        formatter.setAllowsInvalid(false);
        
        tfValor.setFormatterFactory(new DefaultFormatterFactory(formatter));
        
        tfOrigem.setText(encomenda.getOrigem());
        tfDestino.setText(encomenda.getDestino());
        tfValor.setText(String.valueOf(encomenda.getValor()).replace(".", ","));
        
        this.setTitle("Atualizar Encomenda");
        TitledBorder border = (TitledBorder) panelPrincipal.getBorder();
        border.setTitle("Atualizar Encomenda");
        
        this.action = "update";
        this.encomenda = encomenda;
    }
    
    private Veiculo createVeiculoObject(ResultSet rs) throws SQLException {
        Veiculo veiculo = new Veiculo();
        
        veiculo.setId(rs.getString("id"));
        veiculo.setMarca(rs.getString("marca"));
        veiculo.setModelo(rs.getString("modelo"));
        veiculo.setPlaca(rs.getString("placa"));
        return veiculo;
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
        lblOrigem = new javax.swing.JLabel();
        tfOrigem = new javax.swing.JTextField();
        lblDestino = new javax.swing.JLabel();
        tfDestino = new javax.swing.JTextField();
        lblVeiculo = new javax.swing.JLabel();
        cbVeiculo = new javax.swing.JComboBox<>();
        btnCancelar = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        lblValor = new javax.swing.JLabel();
        tfValor = new javax.swing.JFormattedTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Transportadora JM - Cadastrar Encomenda");
        setResizable(false);

        panelPrincipal.setBorder(javax.swing.BorderFactory.createTitledBorder("Cadastrar Encomenda"));

        lblOrigem.setText("Origem");

        lblDestino.setText("Destino");

        lblVeiculo.setText("Veículo");

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

        lblValor.setText("Valor");

        tfValor.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getCurrencyInstance())));

        javax.swing.GroupLayout panelPrincipalLayout = new javax.swing.GroupLayout(panelPrincipal);
        panelPrincipal.setLayout(panelPrincipalLayout);
        panelPrincipalLayout.setHorizontalGroup(
            panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPrincipalLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tfValor)
                    .addComponent(tfOrigem)
                    .addComponent(tfDestino)
                    .addComponent(cbVeiculo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPrincipalLayout.createSequentialGroup()
                        .addGap(0, 140, Short.MAX_VALUE)
                        .addComponent(btnSalvar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelar))
                    .addGroup(panelPrincipalLayout.createSequentialGroup()
                        .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblOrigem)
                            .addComponent(lblDestino)
                            .addComponent(lblVeiculo)
                            .addComponent(lblValor))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelPrincipalLayout.setVerticalGroup(
            panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPrincipalLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblOrigem)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tfOrigem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblDestino)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tfDestino, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblVeiculo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbVeiculo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblValor)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tfValor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
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
        String origem = tfOrigem.getText();
        String destino = tfDestino.getText();
        String veiculoId = ((Veiculo) cbVeiculo.getSelectedItem()).getId();
        
        if (origem.isEmpty() || destino.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Todos os campos devem ser preenchidos.", "Erro", JOptionPane.ERROR_MESSAGE);
        } else {
            Connection conn = ConnectionFactory.createConnection();
            try {
                
                if ("update".equals(action)) {
                    PreparedStatement ps = conn.prepareStatement("UPDATE encomenda SET origem = ?, destino = ?, id_veiculo = ?, valor = ? WHERE id = ?");
                    ps.setString(1, origem);
                    ps.setString(2, destino);
                    ps.setString(3, veiculoId);
                    ps.setDouble(4, Double.valueOf(tfValor.getText().replace(".", "").replace(",", ".")));
                    ps.setString(5, encomenda.getId());
                    
                    int result = ps.executeUpdate();
                    if (result> 0) {
                        this.dispose();
                        principal.populateEncomendasTable();
                    }
                } else {
                    PreparedStatement statement = conn.prepareStatement("INSERT INTO encomenda VALUES (?, ?, ?, ?, ?, ?)");
                    Random random = new Random();
                    int encId = random.nextInt(1000) + 1001;
                    statement.setString(1, String.valueOf(encId));
                    statement.setString(2, origem);
                    statement.setString(3, destino);
                    statement.setString(4, veiculoId);
                    statement.setString(5, "Em Transporte");
                    statement.setDouble(6, Double.valueOf(tfValor.getText().replace(".", "").replace(",", ".")));

                    int result = statement.executeUpdate();
                    if (result> 0) {
                        this.dispose();
                        principal.populateEncomendasTable();
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
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CadastrarEncomenda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CadastrarEncomenda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CadastrarEncomenda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CadastrarEncomenda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
//                new CadastrarEncomenda().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JComboBox<String> cbVeiculo;
    private javax.swing.JLabel lblDestino;
    private javax.swing.JLabel lblOrigem;
    private javax.swing.JLabel lblValor;
    private javax.swing.JLabel lblVeiculo;
    private javax.swing.JPanel panelPrincipal;
    private javax.swing.JTextField tfDestino;
    private javax.swing.JTextField tfOrigem;
    private javax.swing.JFormattedTextField tfValor;
    // End of variables declaration//GEN-END:variables
}
