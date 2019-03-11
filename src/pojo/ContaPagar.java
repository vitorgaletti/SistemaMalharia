
package pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


public class ContaPagar {
    
    private     int   id;
    private     Date  data;
    private     BigDecimal valortotal;
    private     BigDecimal desconto;
    private     BigDecimal valorliquido;
    private     BigDecimal valorpendente;
    private     String  descricao;
    private     boolean  quitada;
    private     Compra compra = new Compra();
    private     Fornecedor fornecedor = new Fornecedor();
    private     CondicaoDePagamento condicaodepagamento = new CondicaoDePagamento();
    private     List<ParcelaContaPagar> parcelaContaPagar;
    private List<Integer> controlePksItens;

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

    public BigDecimal getValorpendente() {
        return valorpendente;
    }

    public void setValorpendente(BigDecimal valorpendente) {
        this.valorpendente = valorpendente;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isQuitada() {
        return quitada;
    }

    public void setQuitada(boolean quitada) {
        this.quitada = quitada;
    }

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public CondicaoDePagamento getCondicaodepagamento() {
        return condicaodepagamento;
    }

    public void setCondicaodepagamento(CondicaoDePagamento condicaodepagamento) {
        this.condicaodepagamento = condicaodepagamento;
    }

    public List<ParcelaContaPagar> getParcelaContaPagar() {
        return parcelaContaPagar;
    }

    public void setParcelaContaPagar(List<ParcelaContaPagar> parcelaContaPagar) {
        this.parcelaContaPagar = parcelaContaPagar;
    }

    public List<Integer> getControlePksItens() {
        return controlePksItens;
    }

    public void setControlePksItens(List<Integer> controlePksItens) {
        this.controlePksItens = controlePksItens;
    }

}
