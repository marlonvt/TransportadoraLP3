package info;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Encomenda {
    
    private String id;
    private String origem;
    private String destino;
    private String idVeiculo;
    private String status;
    private Double valor;

    public static Encomenda createEncomendaObject(ResultSet rs) throws SQLException {
        Encomenda encomenda = new Encomenda();
        
        encomenda.setId(rs.getString("id"));
        encomenda.setOrigem(rs.getString("origem"));
        encomenda.setDestino(rs.getString("destino"));
        encomenda.setIdVeiculo(rs.getString("id_veiculo"));
        encomenda.setStatus(rs.getString("status"));
        encomenda.setValor(rs.getDouble("valor"));
        return encomenda;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getIdVeiculo() {
        return idVeiculo;
    }

    public void setIdVeiculo(String idVeiculo) {
        this.idVeiculo = idVeiculo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
}
