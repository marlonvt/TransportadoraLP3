package admin;

import actions.DeleteAction;
import actions.ModifyDataAction;
import db.ConnectionFactory;
import info.Usuario;
import java.awt.HeadlessException;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableModel;

public class Principal extends javax.swing.JFrame {
    
    private Usuario admin;

    public Usuario getAdmin() {
        return admin;
    }

    public void setAdmin(Usuario admin) {
        this.admin = admin;
    }

    /**
     * Creates new form Principal
     */
    public Principal() {
        initComponents();
    }

    public Principal(Usuario admin) throws HeadlessException {
        this.admin = admin;
        initComponents();
        
        lblGreeting.setText("Olá, " + admin.getNomeCompleto() + "!");
        
        populateEmpresasTable();
        populateVeiculosTable();
        populateUsuariosTable();
        populateEncomendasTable();
        
        tableEmpresas.getColumnModel().getColumn(0).setMaxWidth(50);
        tableEncomendas.getColumnModel().getColumn(0).setMaxWidth(50);
        tableUsuarios.getColumnModel().getColumn(0).setMaxWidth(50);
        tableVeiculos.getColumnModel().getColumn(0).setMaxWidth(50);
        
        KeyStroke enter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
        KeyStroke delete = KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0);
        
        String enterActionKey = "modifyData";
        String deleteActionKey = "deleteRow";
        
        tableEmpresas.getInputMap().put(enter, enterActionKey);
        tableEmpresas.getInputMap().put(delete, deleteActionKey);
        tableEmpresas.getActionMap().put(enterActionKey, new ModifyDataAction(tableEmpresas, "empresa", this));
        tableEmpresas.getActionMap().put(deleteActionKey, new DeleteAction(tableEmpresas, "empresa", this));
        
        tableEncomendas.getInputMap().put(enter, enterActionKey);
        tableEncomendas.getInputMap().put(delete, deleteActionKey);
        tableEncomendas.getActionMap().put(enterActionKey, new ModifyDataAction(tableEncomendas, "encomenda", this));
        tableEncomendas.getActionMap().put(deleteActionKey, new DeleteAction(tableEncomendas, "encomenda", this));
        
        tableUsuarios.getInputMap().put(enter, enterActionKey);
        tableUsuarios.getInputMap().put(delete, deleteActionKey);
        tableUsuarios.getActionMap().put(enterActionKey, new ModifyDataAction(tableUsuarios, "usuario", this));
        tableUsuarios.getActionMap().put(deleteActionKey, new DeleteAction(tableUsuarios, "usuario", this));
        
        tableVeiculos.getInputMap().put(enter, enterActionKey);
        tableVeiculos.getInputMap().put(delete, deleteActionKey);
        tableVeiculos.getActionMap().put(enterActionKey, new ModifyDataAction(tableVeiculos, "veiculo", this));
        tableVeiculos.getActionMap().put(deleteActionKey, new DeleteAction(tableVeiculos, "veiculo", this));
    }
    
    private void clearTable(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        int rowCount = model.getRowCount();
        for (int i = rowCount - 1; i >= 0; i--) {
            model.removeRow(i);
        }
    }
    
    public void populateEncomendasTable() {
        Connection conn = ConnectionFactory.createConnection();
        clearTable(tableEncomendas);
        DefaultTableModel model = (DefaultTableModel) tableEncomendas.getModel();
        try {
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM encomenda");
            while (result.next()) {
                model.addRow(new Object[] {result.getString("id"),
                                            result.getString("origem"),
                                            result.getString("destino"),
                                            getCarRepresentation(result),
                                            result.getString("status"),
                                            result.getDouble("valor")});
            }
        } catch (SQLException ex) {
            System.err.println("Falha ao consultar o banco de dados: " + ex.getMessage());
        } finally {
            ConnectionFactory.closeConnection(conn);
        }
    }
    
    public void populateUsuariosTable() {
        Connection conn = ConnectionFactory.createConnection();
        clearTable(tableUsuarios);
        DefaultTableModel model = (DefaultTableModel) tableUsuarios.getModel();
        try {
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM usuario");
            while (result.next()) {
                if (!"10001".equals(result.getString("id"))) {
                    model.addRow(new Object[] {result.getString("id"),
                                            result.getString("nome_completo"),
                                            getCarRepresentation(result),
                                            (result.getString("tipo_usuario").equals("admin")) ? "Administrador" : "Funcionário"});
                }
            }
        } catch (SQLException ex) {
            System.err.println("Falha ao consultar o banco de dados: " + ex.getMessage());
        } finally {
            ConnectionFactory.closeConnection(conn);
        }
    }
    
    private String getCarRepresentation(ResultSet rs) {
        String rep = null;
        
        Connection conn = ConnectionFactory.createConnection();
        try {
            
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM veiculo WHERE id = ?");
            statement.setString(1, rs.getString("id_veiculo"));
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                rep = result.getString("marca") + " " + result.getString("modelo") + ", " + result.getString("placa");
            }
        } catch (SQLException ex) {
            System.err.println("Falha ao consultar o banco de dados: " + ex.getMessage());
        } finally {
            ConnectionFactory.closeConnection(conn);
        }
        return rep;
    }
    
    public void populateEmpresasTable() {
        Connection conn = ConnectionFactory.createConnection();
        DefaultTableModel model = (DefaultTableModel) tableEmpresas.getModel();
        clearTable(tableEmpresas);
        try {
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM empresa");
            while (result.next()) {
                model.addRow(new Object[] {result.getString("id"),
                                            result.getString("razao_social"),
                                            result.getString("endereco"),
                                            result.getString("cnpj")});
            }
        } catch (SQLException ex) {
            System.err.println("Falha ao consultar o banco de dados: " + ex.getMessage());
        } finally {
            ConnectionFactory.closeConnection(conn);
        }
    }
    
    public void populateVeiculosTable() {
        Connection conn = ConnectionFactory.createConnection();
        DefaultTableModel model = (DefaultTableModel) tableVeiculos.getModel();
        clearTable(tableVeiculos);
        try {
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM veiculo");
            while (result.next()) {
                model.addRow(new Object[] {result.getString("id"),
                                            result.getString("marca"),
                                            result.getString("modelo"),
                                            result.getString("placa")});
            }
        } catch (SQLException ex) {
            System.err.println("Falha ao consultar o banco de dados: " + ex.getMessage());
        } finally {
            ConnectionFactory.closeConnection(conn);
        }
    }
    
    private void filterResults(JTable tabela, String nomeTabela, String campo, String valor) {
        if (!campo.isEmpty() && !valor.isEmpty()) {
            String column = defineTableColumn(campo);
            Connection conn = ConnectionFactory.createConnection();
            try {
                PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + nomeTabela + " WHERE " + column + " LIKE ?");
                ps.setString(1, valor + "%");
                ResultSet result = ps.executeQuery();
                clearTable(tabela);
                DefaultTableModel model = (DefaultTableModel) tabela.getModel();
                while(result.next()) {
                    if (nomeTabela.equals("empresa")) {
                        model.addRow(new Object[] {result.getString("id"),
                                            result.getString("razao_social"),
                                            result.getString("endereco"),
                                            result.getString("cnpj")});
                    } else if (nomeTabela.equals("encomenda")) {
                        model.addRow(new Object[] {result.getString("id"),
                                            result.getString("origem"),
                                            result.getString("destino"),
                                            getCarRepresentation(result),
                                            result.getString("status")});
                    } else if (nomeTabela.equals("usuario")) {
                        if (!"10001".equals(result.getString("id"))) {
                            model.addRow(new Object[] {result.getString("id"),
                                            result.getString("nome_completo"),
                                            getCarRepresentation(result),
                                            (result.getString("tipo_usuario").equals("admin")) ? "Administrador" : "Funcionário"});
                        }
                    } else {
                        model.addRow(new Object[] {result.getString("id"),
                                            result.getString("marca"),
                                            result.getString("modelo"),
                                            result.getString("placa")});
                    }
                }
            } catch (SQLException ex) {
                System.err.println("Falha ao consultar o banco de dados: " + ex.getMessage());
            } finally {
                ConnectionFactory.closeConnection(conn);
            }
        }
    }
    
    private String defineTableColumn(String field) {
        String column;
        switch (field) {
            case "Razão Social": column = "razao_social"; break;
            case "Nome": column = "nome_completo"; break;
            case "Veículo": column = "id_veiculo"; break;
            case "Função": column = "tipo_usuario"; break;
            default: column = field.toLowerCase();
        }
        return column;
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
        panelTabs = new javax.swing.JTabbedPane();
        panelTabEmpresas = new javax.swing.JPanel();
        lblEmpFiltrar = new javax.swing.JLabel();
        cbEmpFiltrar = new javax.swing.JComboBox<>();
        tfEmpFiltrar = new javax.swing.JTextField();
        btnEmpFiltrar = new javax.swing.JButton();
        btnEmpCadastrar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableEmpresas = new javax.swing.JTable();
        panelTabEncomendas = new javax.swing.JPanel();
        lblEncFiltrar = new javax.swing.JLabel();
        cbEncFiltrar = new javax.swing.JComboBox<>();
        tfEncFiltrar = new javax.swing.JTextField();
        btnEncFiltrar = new javax.swing.JButton();
        btnEncCadastrar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableEncomendas = new javax.swing.JTable();
        panelTabUsuarios = new javax.swing.JPanel();
        lblUsrFiltrar = new javax.swing.JLabel();
        cbUsrFiltrar = new javax.swing.JComboBox<>();
        tfUsrFiltrar = new javax.swing.JTextField();
        btnUsrFiltrar = new javax.swing.JButton();
        btnUsrCadastrar = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableUsuarios = new javax.swing.JTable();
        panelTabVeiculos = new javax.swing.JPanel();
        lblVecFiltrar = new javax.swing.JLabel();
        cbVecFiltrar = new javax.swing.JComboBox<>();
        tfVecFiltrar = new javax.swing.JTextField();
        btnVecFiltrar = new javax.swing.JButton();
        btnVecCadastrar = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tableVeiculos = new javax.swing.JTable();
        lblLogo = new javax.swing.JLabel();
        btnSair = new javax.swing.JButton();
        lblGreeting = new javax.swing.JLabel();
        btnHelp = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Transportadora JM");

        lblEmpFiltrar.setText("Filtrar por");

        cbEmpFiltrar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ID", "Razão Social", "CNPJ" }));

        btnEmpFiltrar.setText("Filtrar");
        btnEmpFiltrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEmpFiltrarActionPerformed(evt);
            }
        });

        btnEmpCadastrar.setText("Cadastrar...");
        btnEmpCadastrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEmpCadastrarActionPerformed(evt);
            }
        });

        tableEmpresas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Razão Social", "Endereço", "CNPJ"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tableEmpresas);

        javax.swing.GroupLayout panelTabEmpresasLayout = new javax.swing.GroupLayout(panelTabEmpresas);
        panelTabEmpresas.setLayout(panelTabEmpresasLayout);
        panelTabEmpresasLayout.setHorizontalGroup(
            panelTabEmpresasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTabEmpresasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelTabEmpresasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(panelTabEmpresasLayout.createSequentialGroup()
                        .addComponent(lblEmpFiltrar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbEmpFiltrar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfEmpFiltrar, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEmpFiltrar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEmpCadastrar)))
                .addContainerGap())
        );
        panelTabEmpresasLayout.setVerticalGroup(
            panelTabEmpresasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTabEmpresasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelTabEmpresasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEmpFiltrar)
                    .addComponent(cbEmpFiltrar, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfEmpFiltrar, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEmpFiltrar)
                    .addComponent(btnEmpCadastrar))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelTabs.addTab("Empresas", new javax.swing.ImageIcon(getClass().getResource("/images/supermarket.png")), panelTabEmpresas); // NOI18N

        lblEncFiltrar.setText("Filtrar por");

        cbEncFiltrar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ID", "Origem", "Destino", "Status" }));

        btnEncFiltrar.setText("Filtrar");
        btnEncFiltrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEncFiltrarActionPerformed(evt);
            }
        });

        btnEncCadastrar.setText("Cadastrar...");
        btnEncCadastrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEncCadastrarActionPerformed(evt);
            }
        });

        tableEncomendas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Origem", "Destino", "Veículo", "Status", "Valor"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tableEncomendas);

        javax.swing.GroupLayout panelTabEncomendasLayout = new javax.swing.GroupLayout(panelTabEncomendas);
        panelTabEncomendas.setLayout(panelTabEncomendasLayout);
        panelTabEncomendasLayout.setHorizontalGroup(
            panelTabEncomendasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTabEncomendasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelTabEncomendasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(panelTabEncomendasLayout.createSequentialGroup()
                        .addComponent(lblEncFiltrar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbEncFiltrar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfEncFiltrar, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEncFiltrar, javax.swing.GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEncCadastrar)))
                .addContainerGap())
        );
        panelTabEncomendasLayout.setVerticalGroup(
            panelTabEncomendasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTabEncomendasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelTabEncomendasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEncFiltrar)
                    .addComponent(cbEncFiltrar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfEncFiltrar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEncFiltrar)
                    .addComponent(btnEncCadastrar))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 263, Short.MAX_VALUE))
        );

        panelTabs.addTab("Encomendas", new javax.swing.ImageIcon(getClass().getResource("/images/delivery.png")), panelTabEncomendas); // NOI18N

        lblUsrFiltrar.setText("Filtrar por");

        cbUsrFiltrar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ID", "Nome", "Veículo", "Função" }));

        btnUsrFiltrar.setText("Filtrar");
        btnUsrFiltrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsrFiltrarActionPerformed(evt);
            }
        });

        btnUsrCadastrar.setText("Cadastrar...");
        btnUsrCadastrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsrCadastrarActionPerformed(evt);
            }
        });

        tableUsuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nome", "Veículo", "Função"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tableUsuarios);

        javax.swing.GroupLayout panelTabUsuariosLayout = new javax.swing.GroupLayout(panelTabUsuarios);
        panelTabUsuarios.setLayout(panelTabUsuariosLayout);
        panelTabUsuariosLayout.setHorizontalGroup(
            panelTabUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTabUsuariosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelTabUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addGroup(panelTabUsuariosLayout.createSequentialGroup()
                        .addComponent(lblUsrFiltrar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbUsrFiltrar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfUsrFiltrar, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnUsrFiltrar, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnUsrCadastrar)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelTabUsuariosLayout.setVerticalGroup(
            panelTabUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTabUsuariosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelTabUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUsrFiltrar)
                    .addComponent(cbUsrFiltrar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfUsrFiltrar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUsrFiltrar)
                    .addComponent(btnUsrCadastrar))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 263, Short.MAX_VALUE))
        );

        panelTabs.addTab("Usuários", new javax.swing.ImageIcon(getClass().getResource("/images/manager (1).png")), panelTabUsuarios); // NOI18N

        lblVecFiltrar.setText("Filtrar por");

        cbVecFiltrar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ID", "Marca", "Modelo", "Placa" }));

        btnVecFiltrar.setText("Filtrar");
        btnVecFiltrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVecFiltrarActionPerformed(evt);
            }
        });

        btnVecCadastrar.setText("Cadastrar...");
        btnVecCadastrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVecCadastrarActionPerformed(evt);
            }
        });

        tableVeiculos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Marca", "Modelo", "Placa"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(tableVeiculos);

        javax.swing.GroupLayout panelTabVeiculosLayout = new javax.swing.GroupLayout(panelTabVeiculos);
        panelTabVeiculos.setLayout(panelTabVeiculosLayout);
        panelTabVeiculosLayout.setHorizontalGroup(
            panelTabVeiculosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTabVeiculosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelTabVeiculosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4)
                    .addGroup(panelTabVeiculosLayout.createSequentialGroup()
                        .addComponent(lblVecFiltrar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbVecFiltrar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfVecFiltrar, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnVecFiltrar, javax.swing.GroupLayout.DEFAULT_SIZE, 62, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnVecCadastrar)))
                .addContainerGap())
        );
        panelTabVeiculosLayout.setVerticalGroup(
            panelTabVeiculosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTabVeiculosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelTabVeiculosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblVecFiltrar)
                    .addComponent(cbVecFiltrar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfVecFiltrar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnVecFiltrar)
                    .addComponent(btnVecCadastrar))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 263, Short.MAX_VALUE))
        );

        panelTabs.addTab("Veículos", new javax.swing.ImageIcon(getClass().getResource("/images/truck (1).png")), panelTabVeiculos); // NOI18N

        javax.swing.GroupLayout panelPrincipalLayout = new javax.swing.GroupLayout(panelPrincipal);
        panelPrincipal.setLayout(panelPrincipalLayout);
        panelPrincipalLayout.setHorizontalGroup(
            panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelTabs)
        );
        panelPrincipalLayout.setVerticalGroup(
            panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPrincipalLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panelTabs, javax.swing.GroupLayout.PREFERRED_SIZE, 353, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        lblLogo.setFont(new java.awt.Font("Corbel", 1, 14)); // NOI18N
        lblLogo.setText("Transportadora JM");

        btnSair.setText("Sair");
        btnSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSairActionPerformed(evt);
            }
        });

        btnHelp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon.png"))); // NOI18N
        btnHelp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHelpActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblLogo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblGreeting)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSair)
                        .addGap(10, 10, 10)
                        .addComponent(btnHelp)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblLogo)
                    .addComponent(btnSair)
                    .addComponent(lblGreeting)
                    .addComponent(btnHelp))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSairActionPerformed
        System.exit(WIDTH);
    }//GEN-LAST:event_btnSairActionPerformed

    private void btnEmpCadastrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEmpCadastrarActionPerformed
        new CadastrarEmpresa(this).setVisible(true);
    }//GEN-LAST:event_btnEmpCadastrarActionPerformed

    private void btnVecCadastrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVecCadastrarActionPerformed
        new CadastrarVeiculo(this).setVisible(true);
    }//GEN-LAST:event_btnVecCadastrarActionPerformed

    private void btnUsrCadastrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsrCadastrarActionPerformed
        new CadastrarUsuario(this).setVisible(true);
    }//GEN-LAST:event_btnUsrCadastrarActionPerformed

    private void btnEncCadastrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEncCadastrarActionPerformed
        new CadastrarEncomenda(this).setVisible(true);
    }//GEN-LAST:event_btnEncCadastrarActionPerformed

    private void btnEmpFiltrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEmpFiltrarActionPerformed
        filterResults(tableEmpresas, "empresa", String.valueOf(cbEmpFiltrar.getSelectedItem()), tfEmpFiltrar.getText());
    }//GEN-LAST:event_btnEmpFiltrarActionPerformed

    private void btnEncFiltrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEncFiltrarActionPerformed
        filterResults(tableEncomendas, "encomenda", String.valueOf(cbEncFiltrar.getSelectedItem()), tfEncFiltrar.getText());
    }//GEN-LAST:event_btnEncFiltrarActionPerformed

    private void btnUsrFiltrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsrFiltrarActionPerformed
        filterResults(tableUsuarios, "usuario", String.valueOf(cbUsrFiltrar.getSelectedItem()), tfUsrFiltrar.getText());
    }//GEN-LAST:event_btnUsrFiltrarActionPerformed

    private void btnVecFiltrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVecFiltrarActionPerformed
        filterResults(tableVeiculos, "veiculo", String.valueOf(cbVecFiltrar.getSelectedItem()), tfVecFiltrar.getText());
    }//GEN-LAST:event_btnVecFiltrarActionPerformed

    private void btnHelpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHelpActionPerformed
        String message = "<html>Selecione uma linha de uma tabela de dados e pressione<ul><li>Enter: para modificar os dados</li><li>Delete: para deletar a linha</li></ul></html>";
        JOptionPane.showMessageDialog(null, message, "Informações", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_btnHelpActionPerformed

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
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Principal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEmpCadastrar;
    private javax.swing.JButton btnEmpFiltrar;
    private javax.swing.JButton btnEncCadastrar;
    private javax.swing.JButton btnEncFiltrar;
    private javax.swing.JButton btnHelp;
    private javax.swing.JButton btnSair;
    private javax.swing.JButton btnUsrCadastrar;
    private javax.swing.JButton btnUsrFiltrar;
    private javax.swing.JButton btnVecCadastrar;
    private javax.swing.JButton btnVecFiltrar;
    private javax.swing.JComboBox<String> cbEmpFiltrar;
    private javax.swing.JComboBox<String> cbEncFiltrar;
    private javax.swing.JComboBox<String> cbUsrFiltrar;
    private javax.swing.JComboBox<String> cbVecFiltrar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lblEmpFiltrar;
    private javax.swing.JLabel lblEncFiltrar;
    private javax.swing.JLabel lblGreeting;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblUsrFiltrar;
    private javax.swing.JLabel lblVecFiltrar;
    private javax.swing.JPanel panelPrincipal;
    private javax.swing.JPanel panelTabEmpresas;
    private javax.swing.JPanel panelTabEncomendas;
    private javax.swing.JPanel panelTabUsuarios;
    private javax.swing.JPanel panelTabVeiculos;
    private javax.swing.JTabbedPane panelTabs;
    private javax.swing.JTable tableEmpresas;
    private javax.swing.JTable tableEncomendas;
    private javax.swing.JTable tableUsuarios;
    private javax.swing.JTable tableVeiculos;
    private javax.swing.JTextField tfEmpFiltrar;
    private javax.swing.JTextField tfEncFiltrar;
    private javax.swing.JTextField tfUsrFiltrar;
    private javax.swing.JTextField tfVecFiltrar;
    // End of variables declaration//GEN-END:variables
}
