
package componente;

import javax.swing.JCheckBox;


public class MeuCampoCheckBox extends JCheckBox  implements MeuComponente{
    
    private boolean valorPadrao;
    private boolean podeHabilitar;
    private String nome;
    
    
    public MeuCampoCheckBox( boolean valorPadrao, boolean podeHabilitar, String nome) {
        this.valorPadrao = valorPadrao;
        this.podeHabilitar = podeHabilitar;
        this.nome = nome;
    }
    

    @Override
    public boolean eObrigatorio() {
        return true;
    }

    @Override
    public boolean eValido() {
        return true;
    }

    @Override
    public boolean eVazio() {
        return false;
    }

    @Override
    public void limpar() {
        setSelected(valorPadrao);
    }

    @Override
    public void habilitar(boolean status) {
        setEnabled(podeHabilitar && status);
    }

    @Override
    public Object getValor() {
        return isSelected();
    }

    @Override
    public void setValor(Object valor) {
        setSelected((boolean)valor);
    }

    @Override
    public String getNome() {
        return nome;
    }
    
    
    
}
