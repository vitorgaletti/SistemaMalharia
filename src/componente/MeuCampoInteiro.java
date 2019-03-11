
package componente;

import javax.swing.JTextField;


public class MeuCampoInteiro extends JTextField implements MeuComponente{
    
    private boolean obrigatorio;
    private boolean podeHabilitar;
    private String nome;
    
    
    public MeuCampoInteiro(int tamanho, boolean obrigatorio, boolean podeHabilitar, String nome) {
        
        this.obrigatorio = obrigatorio;
        this.podeHabilitar = podeHabilitar;
        this.nome = nome;
        setColumns(tamanho);
        setHorizontalAlignment(JTextField.RIGHT);
        
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
        setText("0");
    }

    @Override
    public void habilitar(boolean status) {
        setEnabled(podeHabilitar && status);
    }

    @Override
    public Object getValor() {
        
          
         return (Integer.parseInt("0" + getText()));
    }

    @Override
    public void setValor(Object valor) {
         
          setText(String.valueOf((Integer) valor));
    }

    @Override
    public String getNome() {
        return nome;
    }
    
}
