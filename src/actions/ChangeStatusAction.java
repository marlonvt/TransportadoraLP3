package actions;

import admin.Principal;
import admin.PrincipalFunc;
import admin.TrocaStatus;
import db.ConnectionFactory;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.AbstractAction;
import javax.swing.JTable;

public class ChangeStatusAction extends AbstractAction {

    private JTable table;
    private PrincipalFunc principal;

    public ChangeStatusAction(JTable table, PrincipalFunc principal) {
        this.table = table;
        this.principal = principal;
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        int selectedRowIdx = table.getSelectedRow();
        String itemId = (String) table.getModel().getValueAt(selectedRowIdx, 0);
        
        Connection conn = ConnectionFactory.createConnection();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM encomenda WHERE id = ?");
            ps.setString(1, itemId);
            
            ResultSet result = ps.executeQuery();
            if (result.next()) {
                new TrocaStatus(principal, itemId).setVisible(true);
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

    public PrincipalFunc getPrincipal() {
        return principal;
    }

    public void setPrincipal(PrincipalFunc principal) {
        this.principal = principal;
    }

}
