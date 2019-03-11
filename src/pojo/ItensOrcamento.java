
package pojo;

import java.math.BigDecimal;


public class ItensOrcamento {
    
    private    int   id;
    private    int   quantidade;
    private    BigDecimal  valorunitario;
    private    BigDecimal  desconto;
    private    BigDecimal  valortotal;
    private    ProdutoAcabado produtoacabado = new ProdutoAcabado();
    private    Orcamento orcamento = new Orcamento();

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

    public BigDecimal getValortotal() {
        return valortotal;
    }

    public void setValortotal(BigDecimal valortotal) {
        this.valortotal = valortotal;
    }

    public ProdutoAcabado getProdutoacabado() {
        return produtoacabado;
    }

    public void setProdutoacabado(ProdutoAcabado produtoacabado) {
        this.produtoacabado = produtoacabado;
    }

    public Orcamento getOrcamento() {
        return orcamento;
    }

    public void setOrcamento(Orcamento orcamento) {
        this.orcamento = orcamento;
    }
    
    
}
