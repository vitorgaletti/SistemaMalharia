
package componente;


public interface MeuComponente {
    
    public boolean eObrigatorio();
    public boolean eValido();
    public boolean eVazio();
    public void limpar();
    public void habilitar(boolean status);
    public Object getValor();
    public void setValor(Object valor);
    public String getNome();
    
     
    
}
