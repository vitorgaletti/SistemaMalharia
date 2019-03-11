
package pojo;


public class Cliente {
    
    private   int id;
    private   String nomerazaosocial;
    private   String apelidonomefantasia;
    private   String tipocliente;
    private   String cpfcnpj;
    private   String rgie;
    private   String endereco;
    private   String numero;
    private   String complemento;
    private   String bairro;
    private   String cep;
    private   String fone1;
    private   String fone2;
    private   String email;
    private   String redesocial;
    private   boolean ativo;
    private   Cidade cidade = new Cidade();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomerazaosocial() {
        return nomerazaosocial;
    }

    public void setNomerazaosocial(String nomerazaosocial) {
        this.nomerazaosocial = nomerazaosocial;
    }

    public String getApelidonomefantasia() {
        return apelidonomefantasia;
    }

    public void setApelidonomefantasia(String apelidonomefantasia) {
        this.apelidonomefantasia = apelidonomefantasia;
    }

    public String getTipocliente() {
        return tipocliente;
    }

    public void setTipocliente(String tipocliente) {
        this.tipocliente = tipocliente;
    }

    public String getCpfcnpj() {
        return cpfcnpj;
    }

    public void setCpfcnpj(String cpfcnpj) {
        this.cpfcnpj = cpfcnpj;
    }

    public String getRgie() {
        return rgie;
    }

    public void setRgie(String rgie) {
        this.rgie = rgie;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getFone1() {
        return fone1;
    }

    public void setFone1(String fone1) {
        this.fone1 = fone1;
    }

    public String getFone2() {
        return fone2;
    }

    public void setFone2(String fone2) {
        this.fone2 = fone2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRedesocial() {
        return redesocial;
    }

    public void setRedesocial(String redesocial) {
        this.redesocial = redesocial;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }
    
    
    
}
