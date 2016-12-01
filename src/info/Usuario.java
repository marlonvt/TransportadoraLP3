package info;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Usuario {
    
    private String id;
    private String login;
    private String senha;
    private String nomeCompleto;
    private String idVeiculo;
    private String tipo;
    
    public static Usuario createUsuarioObject(ResultSet result) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setId(result.getString("id"));
        usuario.setLogin(result.getString("login"));
        usuario.setSenha(result.getString("senha"));
        usuario.setNomeCompleto(result.getString("nome_completo"));
        usuario.setIdVeiculo(result.getString("id_veiculo"));
        usuario.setTipo(result.getString("tipo_usuario"));
        
        return usuario;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getIdVeiculo() {
        return idVeiculo;
    }

    public void setIdVeiculo(String idVeiculo) {
        this.idVeiculo = idVeiculo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    
}
