
package pojo;

import java.math.BigDecimal;
import java.util.Date;


public class Recebimento {
    
    
    private   int   id;
    private   Date  data;
    private   BigDecimal  valor;
    private   BigDecimal  desconto;
    private   BigDecimal  juros;
    private   BigDecimal  multa;
    private   BigDecimal  valortotal;
    private   ParcelaContaReceber parcelacontareceber = new ParcelaContaReceber();

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

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public BigDecimal getDesconto() {
        return desconto;
    }

    public void setDesconto(BigDecimal desconto) {
        this.desconto = desconto;
    }

    public BigDecimal getJuros() {
        return juros;
    }

    public void setJuros(BigDecimal juros) {
        this.juros = juros;
    }

    public BigDecimal getMulta() {
        return multa;
    }

    public void setMulta(BigDecimal multa) {
        this.multa = multa;
    }

    public BigDecimal getValortotal() {
        return valortotal;
    }

    public void setValortotal(BigDecimal valortotal) {
        this.valortotal = valortotal;
    }

    public ParcelaContaReceber getParcelacontareceber() {
        return parcelacontareceber;
    }

    public void setParcelacontareceber(ParcelaContaReceber parcelacontareceber) {
        this.parcelacontareceber = parcelacontareceber;
    }
    
    
}
