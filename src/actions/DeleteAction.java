package actions;

import admin.Principal;
import db.ConnectionFactory;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.JTable;

public class DeleteAction extends AbstractAction {
    
    private JTable table;
    private String tableName;
    private Principal principal;

    public DeleteAction(JTable table, String tableName, Principal principal) {
        this.table = table;
        this.tableName = tableName;
        this.principal = principal;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        int response = JOptionPane.showConfirmDialog(null, "Deseja excluir a linha selecionada?", "Confirmar exclusão", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (response == JOptionPane.YES_OPTION) {
            String itemId = (String) table.getModel().getValueAt(table.getSelectedRow(), 0);
            
            Connection conn = ConnectionFactory.createConnection();
            try {
                PreparedStatement ps = conn.prepareStatement("DELETE FROM " + tableName + " WHERE id = ?");
                ps.setString(1, itemId);
                
                int result = ps.executeUpdate();
                if (result > 0) {
                    switch (tableName) {
                        case "empresa":
                            principal.populateEmpresasTable();
                            break;
                        case "encomenda":
                            principal.populateEncomendasTable();
                            break;
                        case "usuario":
                            principal.populateUsuariosTable();
                            break;
                        case "veiculo":
                            principal.populateVeiculosTable();
                            break;
                    }
                }
            } catch (SQLException ex) {
                System.err.println("Falha ao consultar banco de dados: " + ex.getMessage());
            } finally {
                ConnectionFactory.closeConnection(conn);
            }
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
