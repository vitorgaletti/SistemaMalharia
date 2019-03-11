
package pojo;

import java.math.BigDecimal;
import java.util.List;


public class MateriaPrima {
    
    private   int id;
    private   String nome;
    private   int    quantidade;
    private   BigDecimal  valorunitario;
    private   BigDecimal  valortotal;
    private   boolean  elote;
    private   boolean  ativo;
    private List<Lote> lote;
    private List<Integer> controlePksItens;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    public BigDecimal getValortotal() {
        return valortotal;
    }

    public void setValortotal(BigDecimal valortotal) {
        this.valortotal = valortotal;
    }

    public boolean isElote() {
        return elote;
    }

    public void setElote(boolean elote) {
        this.elote = elote;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public List<Lote> getLote() {
        return lote;
    }

    public void setLote(List<Lote> lote) {
        this.lote = lote;
    }

    public List<Integer> getControlePksItens() {
        return controlePksItens;
    }

    public void setControlePksItens(List<Integer> controlePksItens) {
        this.controlePksItens = controlePksItens;
    }

   

    


}
