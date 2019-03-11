
package pojo;

import java.math.BigDecimal;
import java.util.List;


public class ItensCompra {
    
    private  int   id;
    private  int   quantidade;
    private  BigDecimal valorunitario = BigDecimal.ZERO;
    private  BigDecimal desconto = BigDecimal.ZERO;
    private  BigDecimal valortotal = BigDecimal.ZERO;
    private  Compra compra = new Compra();
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

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }

    public MateriaPrima getMateriaprima() {
        return materiaprima;
    }

    public void setMateriaprima(MateriaPrima materiaprima) {
        this.materiaprima = materiaprima;
    }
   

   

    

    


   
    
    
    
}
