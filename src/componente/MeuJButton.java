package componente;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class MeuJButton extends JButton implements MeuComponente {
    public MeuJButton(String texto) {
        super(texto);
        
    }
    
    public MeuJButton(String localImagem, boolean areaFilled, boolean borderPainted, boolean focusable) {
        setIcon(new ImageIcon(getClass().getResource(localImagem)));
        setContentAreaFilled(areaFilled);
        setBorderPainted(borderPainted);
        setFocusable(focusable);
        
    }

    @Override
    public boolean eObrigatorio() {
        return false;
    }

    @Override
    public boolean eValido() {
       return true;
    }

    @Override
    public boolean eVazio() {
        return true;
    }

    @Override
    public void limpar() {
        
    }

    @Override
    public void habilitar(boolean status) {
       setEnabled(status);
    }

    @Override
    public Object getValor() {
       return "" + getText();
    }

    @Override
    public void setValor(Object valor) {
       setText("" + valor);
    }

    @Override
    public String getNome() {
       return "";
    }
}