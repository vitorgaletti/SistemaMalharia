
package pojo;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class MovimentoCaixa {
    
    private    int   id;
    private    String  descricao;
    private    String  debitocredito;
    private    BigDecimal valor;
    private    Timestamp datatransacao;
    private    Pagamento  pagamento = new Pagamento();
    private    Recebimento recebimento = new Recebimento();
    private    Caixa caixa = new Caixa();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDebitocredito() {
        return debitocredito;
    }

    public void setDebitocredito(String debitocredito) {
        this.debitocredito = debitocredito;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Timestamp getDatatransacao() {
        return datatransacao;
    }

    public void setDatatransacao(Timestamp datatransacao) {
        this.datatransacao = datatransacao;
    }

    public Pagamento getPagamento() {
        return pagamento;
    }

    public void setPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
    }

    public Recebimento getRecebimento() {
        return recebimento;
    }

    public void setRecebimento(Recebimento recebimento) {
        this.recebimento = recebimento;
    }

    public Caixa getCaixa() {
        return caixa;
    }

    public void setCaixa(Caixa caixa) {
        this.caixa = caixa;
    }
    
    
}
