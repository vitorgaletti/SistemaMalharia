
package pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


public class PedidoDeCompra {
    
    private   int  id;
    private   Date  data;
    private   BigDecimal valortotal;
    private   BigDecimal desconto;
    private   BigDecimal valorliquido;
    private   String     status;
    private   Fornecedor fornecedor = new Fornecedor();
    private   CondicaoDePagamento condicaodepagamento = new CondicaoDePagamento();
    private List<ItensPedidoDeCompra> itensPedidoDeCompra;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public List<ItensPedidoDeCompra> getItensPedidoDeCompra() {
        return itensPedidoDeCompra;
    }

    public void setItensPedidoDeCompra(List<ItensPedidoDeCompra> itensPedidoDeCompra) {
        this.itensPedidoDeCompra = itensPedidoDeCompra;
    }

    public List<Integer> getControlePksItens() {
        return controlePksItens;
    }

    public void setControlePksItens(List<Integer> controlePksItens) {
        this.controlePksItens = controlePksItens;
    }

    

    

 

  
    
    
}
