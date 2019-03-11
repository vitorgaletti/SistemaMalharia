
package pojo;

import java.util.Date;
import java.util.List;


public class Lote {
    
    private  int id;
    private  Date datavalidade;
    private  int quantidade; 
    private  String lote;
    private  boolean ativo;
    private  MateriaPrima materiaprima = new MateriaPrima();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDatavalidade() {
        return datavalidade;
    }

    public void setDatavalidade(Date datavalidade) {
        this.datavalidade = datavalidade;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public MateriaPrima getMateriaprima() {
        return materiaprima;
    }

    public void setMateriaprima(MateriaPrima materiaprima) {
        this.materiaprima = materiaprima;
    }
   

}
