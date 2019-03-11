
package pojo;

import java.math.BigDecimal;
import java.util.Date;


public class ParcelaContaPagar {
    
    private    int   id;
    private    Date  vencimento;
    private    BigDecimal valor;
    private    BigDecimal  valorpendente;
    private    boolean  quitada;
    private    ContaPagar contapagar = new ContaPagar();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getVencimento() {
        return vencimento;
    }

    public void setVencimento(Date vencimento) {
        this.vencimento = vencimento;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public BigDecimal getValorpendente() {
        return valorpendente;
    }

    public void setValorpendente(BigDecimal valorpendente) {
        this.valorpendente = valorpendente;
    }

    public boolean isQuitada() {
        return quitada;
    }

    public void setQuitada(boolean quitada) {
        this.quitada = quitada;
    }

    public ContaPagar getContapagar() {
        return contapagar;
    }

    public void setContapagar(ContaPagar contapagar) {
        this.contapagar = contapagar;
    }

    
    
    
    
    
    
}
