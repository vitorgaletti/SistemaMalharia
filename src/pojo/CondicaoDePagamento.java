
package pojo;


public class CondicaoDePagamento {
    
    
    private  int   id;
    private  String descricao;
    private  int    carencia;
    private  int    nparcelas;
    private  int    prazoentreparcelas;

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

    public int getCarencia() {
        return carencia;
    }

    public void setCarencia(int carencia) {
        this.carencia = carencia;
    }

    public int getNparcelas() {
        return nparcelas;
    }

    public void setNparcelas(int nparcelas) {
        this.nparcelas = nparcelas;
    }

    public int getPrazoentreparcelas() {
        return prazoentreparcelas;
    }

    public void setPrazoentreparcelas(int prazoentreparcelas) {
        this.prazoentreparcelas = prazoentreparcelas;
    }
    
    
}
