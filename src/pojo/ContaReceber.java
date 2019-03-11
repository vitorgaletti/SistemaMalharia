
package pojo;

import java.math.BigDecimal;
import java.util.Date;


public class ContaReceber {
    
    private     int   id;
    private     Date  data;
    private     BigDecimal valortotal;
    private     BigDecimal desconto;
    private     BigDecimal valorliquido;
    private     String  descricao;
    private     String  quitada;
    private     PedidoDeVendaVenda pedidodevendavenda = new PedidoDeVendaVenda();
    private     CondicaoDePagamento condicaodepagamento = new CondicaoDePagamento();

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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getQuitada() {
        return quitada;
    }

    public void setQuitada(String quitada) {
        this.quitada = quitada;
    }

    public PedidoDeVendaVenda getPedidodevendavenda() {
        return pedidodevendavenda;
    }

    public void setPedidodevendavenda(PedidoDeVendaVenda pedidodevendavenda) {
        this.pedidodevendavenda = pedidodevendavenda;
    }

    public CondicaoDePagamento getCondicaodepagamento() {
        return condicaodepagamento;
    }

    public void setCondicaodepagamento(CondicaoDePagamento condicaodepagamento) {
        this.condicaodepagamento = condicaodepagamento;
    }
    
    
}
