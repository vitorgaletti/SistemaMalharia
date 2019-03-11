
package pojo;

import java.math.BigDecimal;
import java.util.Date;


public class Orcamento {
    
    private   int  id;
    private   Date  data;
    private   String descricaoarte;
    private   BigDecimal valorarte;
    private   BigDecimal valortotal;
    private   BigDecimal desconto;
    private   BigDecimal valorliquido;
    private   String     status;
    private   Cliente cliente = new Cliente();
    private   CondicaoDePagamento condicaodepagamento = new CondicaoDePagamento();

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

    public String getDescricaoarte() {
        return descricaoarte;
    }

    public void setDescricaoarte(String descricaoarte) {
        this.descricaoarte = descricaoarte;
    }

    public BigDecimal getValorarte() {
        return valorarte;
    }

    public void setValorarte(BigDecimal valorarte) {
        this.valorarte = valorarte;
    }

    public BigDecimal getValortotal() {
        return valortotal;
    }

    public void setValortotal(BigDecimal valortotal) {
        this.valortotal = valortotal;
    }

    public BigDecimal getDesconto() {
        return desconto;
    }

    public void setDesconto(BigDecimal desconto) {
        this.desconto = desconto;
    }

    public BigDecimal getValorliquido() {
        return valorliquido;
    }

    public void setValorliquido(BigDecimal valorliquido) {
        this.valorliquido = valorliquido;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public CondicaoDePagamento getCondicaodepagamento() {
        return condicaodepagamento;
    }

    public void setCondicaodepagamento(CondicaoDePagamento condicaodepagamento) {
        this.condicaodepagamento = condicaodepagamento;
    }

    
    
    
    
}
