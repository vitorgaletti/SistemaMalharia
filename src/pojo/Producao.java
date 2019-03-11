
package pojo;

import java.util.Date;
import java.util.List;

public class Producao {
    
    
    private    int  id;
    private    Date  data;
    private    Date  previsao;
    private    String descricao;
    private    int  quantidade;
    private    String status;
    private    ProdutoAcabado produtoacabado = new ProdutoAcabado();
    private List<ItensProducao> itensProducao;
    private List<Integer> controlePksItens;

    
    public List<ItensProducao> getItensProducao() {
        return itensProducao;
    }
    
    public List<Integer> getControlePksItens() {
        return controlePksItens;
    }
    
    public void setControlePksItens(List<Integer> controlePksItens) {
        this.controlePksItens = controlePksItens;
    }
    
    public void setItensProducao(List<ItensProducao> itensProducao) {
        this.itensProducao = itensProducao;
    }
    
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

    public Date getPrevisao() {
        return previsao;
    }

    public void setPrevisao(Date previsao) {
        this.previsao = previsao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ProdutoAcabado getProdutoacabado() {
        return produtoacabado;
    }

    public void setProdutoacabado(ProdutoAcabado produtoacabado) {
        this.produtoacabado = produtoacabado;
    }

    
}
