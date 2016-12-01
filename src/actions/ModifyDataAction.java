package actions;

import admin.CadastrarEmpresa;
import admin.CadastrarEncomenda;
import admin.CadastrarUsuario;
import admin.CadastrarVeiculo;
import admin.Principal;
import db.ConnectionFactory;
import info.Empresa;
import info.Encomenda;
import info.Usuario;
import info.Veiculo;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.AbstractAction;
import javax.swing.JTable;

public class ModifyDataAction extends AbstractAction {
    
    private JTable table;
    private String tableName;
    private Principal principal;

    public ModifyDataAction(JTable table, String tableName, Principal principal) {
        this.table = table;
        this.tableName = tableName;
        this.principal = principal;
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        int selectedRowIdx = table.getSelectedRow();
        String itemId = (String) table.getModel().getValueAt(selectedRowIdx, 0);
        
        Connection conn = ConnectionFactory.createConnection();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tableName + " WHERE id = ?");
            ps.setString(1, itemId);
            
            ResultSet result = ps.executeQuery();
            
            if (result.next()) {
                switch (tableName) {
                    case "empresa":
                        Empresa empresaObj = Empresa.createEmpresaObject(result);
                        new CadastrarEmpresa(principal, empresaObj).setVisible(true);
                        break;
                    case "encomenda":
                        Encomenda encomendaObj = Encomenda.createEncomendaObject(result);
                        new CadastrarEncomenda(principal, encomendaObj).setVisible(true);
                        break;
                    case "usuario":
                        Usuario usuarioObject = Usuario.createUsuarioObject(result);
                        new CadastrarUsuario(principal, usuarioObject).setVisible(true);
                        break;
                    case "veiculo":
                        Veiculo veiculoObject = Veiculo.createVeiculoObject(result);
                        new CadastrarVeiculo(principal, veiculoObject).setVisible(true);
                        break;
                }
            }
        } catch (SQLException ex) {
            System.err.println("Falha ao consultar banco de dados: " + ex.getMessage());
        } finally {
            ConnectionFactory.closeConnection(conn);
        }
    }
    
    public JTable getTable() {
        return table;
    }

    public void setTable(JTable table) {
        this.table = table;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Principal getPrincipal() {
        return principal;
    }

    public void setPrincipal(Principal principal) {
        this.principal = principal;
    }
}
