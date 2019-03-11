
package pojo;

import java.math.BigDecimal;


public class ItensPedidoDeVendaVenda {
    
    private  int   id;
    private  int   quantidade;
    private  BigDecimal valorunitario;
    private  BigDecimal desconto;
    private  BigDecimal valortotal;
    private  ProdutoAcabado produtoacabado = new ProdutoAcabado();
    private  PedidoDeVendaVenda pedidodevendavenda = new PedidoDeVendaVenda();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getValorunitario() {
        return valorunitario;
    }

    public void setValorunitario(BigDecimal valorunitario) {
        this.valorunitario = valorunitario;
    }

    public BigDecimal getDesconto() {
        return desconto;
    }

    public void setDesconto(BigDecimal desconto) {
        this.desconto = desconto;
    }

    public BigDecimal getValorttotal() {
        return valortotal;
    }

    public void setValorttotal(BigDecimal valorttotal) {
        this.valortotal = valorttotal;
    }

    public ProdutoAcabado getProdutoacabado() {
        return produtoacabado;
    }

    public void setProdutoacabado(ProdutoAcabado produtoacabado) {
        this.produtoacabado = produtoacabado;
    }

    public PedidoDeVendaVenda getPedidodevendavenda() {
        return pedidodevendavenda;
    }

    public void setPedidodevendavenda(PedidoDeVendaVenda pedidodevendavenda) {
        this.pedidodevendavenda = pedidodevendavenda;
    }
    
    
}
