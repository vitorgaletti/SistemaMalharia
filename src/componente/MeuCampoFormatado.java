
package componente;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JTextField;


public class MeuCampoFormatado extends JTextField implements MeuComponente{
    
    private boolean obrigatorio;
    private String nome;

    
    
    public MeuCampoFormatado(int tamanho,int qtdeCaracteres, boolean obrigatorio, String nome) {
        
        this.obrigatorio = obrigatorio;
        this.nome = nome;
        setColumns(tamanho);
        setHorizontalAlignment(JTextField.LEFT);
        
     }

    @Override
    public boolean eObrigatorio() {
        return obrigatorio;
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
        setText("");
    }

    @Override
    public void habilitar(boolean status) {
        setEnabled(status);
    }

    @Override
    public Object getValor() {
        return getText();
    }

    @Override
    public void setValor(Object valor) {
          setText((String) valor);
    }

    @Override
    public String getNome() {
        return nome;
    }

   
    
}
