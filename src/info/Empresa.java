package info;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Empresa {

    private String id;
    private String razaoSocial;
    private String endereco;
    private String cnpj;
    
    public static Empresa createEmpresaObject(ResultSet rs) throws SQLException {
        Empresa empresa = new Empresa();
        
        empresa.setId(rs.getString("id"));
        empresa.setRazaoSocial(rs.getString("razao_social"));
        empresa.setEndereco(rs.getString("endereco"));
        empresa.setCnpj(rs.getString("cnpj"));
        return empresa;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
    
    
}
