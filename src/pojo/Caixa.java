
package pojo;

import java.math.BigDecimal;
import java.util.Date;


public class Caixa {
    
    private    int  id;
    private    Date data;
    private    BigDecimal  saldoinicial;
    private    BigDecimal  saldofinal;
    private    BigDecimal  diferenca;
    private    boolean  aberto;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public BigDecimal getSaldoinicial() {
        return saldoinicial;
    }

    public void setSaldoinicial(BigDecimal saldoinicial) {
        this.saldoinicial = saldoinicial;
    }

    public BigDecimal getSaldofinal() {
        return saldofinal;
    }

    public void setSaldofinal(BigDecimal saldofinal) {
        this.saldofinal = saldofinal;
    }

    public BigDecimal getDiferenca() {
        return diferenca;
    }

    public void setDiferenca(BigDecimal diferenca) {
        this.diferenca = diferenca;
    }

    public boolean isAberto() {
        return aberto;
    }

    public void setAberto(boolean aberto) {
        this.aberto = aberto;
    }

   
    
    
    
    
    
}
