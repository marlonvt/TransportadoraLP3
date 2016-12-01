package info;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Veiculo {

    private String id;
    private String marca;
    private String modelo;
    private String placa;
    
    public static Veiculo createVeiculoObject(ResultSet rs) throws SQLException {
        Veiculo veiculo = new Veiculo();
        
        veiculo.setId(rs.getString("id"));
        veiculo.setMarca(rs.getString("marca"));
        veiculo.setModelo(rs.getString("modelo"));
        veiculo.setPlaca(rs.getString("placa"));
        return veiculo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    @Override
    public String toString() {
        return marca + " " + modelo + ", " + placa;
    }
    
}
