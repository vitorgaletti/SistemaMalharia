
package pojo;

import java.math.BigDecimal;


public class ItensPedidoDeCompra {
    
    private  int   id;
    private  int   quantidade;
    private  BigDecimal valorunitario = BigDecimal.ZERO;
    private  BigDecimal desconto = BigDecimal.ZERO;
    private  BigDecimal valortotal = BigDecimal.ZERO;
    private  PedidoDeCompra pedidodecompra = new PedidoDeCompra();
    private  MateriaPrima materiaprima = new MateriaPrima();

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

    public PedidoDeCompra getPedidodecompra() {
        return pedidodecompra;
    }

    public void setPedidodecompra(PedidoDeCompra pedidodecompra) {
        this.pedidodecompra = pedidodecompra;
    }

    public MateriaPrima getMateriaprima() {
        return materiaprima;
    }

    public void setMateriaprima(MateriaPrima materiaprima) {
        this.materiaprima = materiaprima;
    }


   
   

   
   
    
}
