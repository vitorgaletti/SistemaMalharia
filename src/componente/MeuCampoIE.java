package componente;

import componente.MeuComponente;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.text.MaskFormatter;

public class MeuCampoIE extends JFormattedTextField implements MeuComponente {
    private boolean obrigatorio;
    private String nome;
   

    public MeuCampoIE(int tamanhoCampo, boolean obrigatorio, String nome) {
        this.obrigatorio = obrigatorio;
        this.nome = nome;
        setColumns(tamanhoCampo);
        try {
            MaskFormatter mf = new MaskFormatter("########-##"); 
            mf.install(this);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Não foi possível"
                    + " iniciar MeuCampoIE");
        }
    }

    @Override
    public boolean eObrigatorio() {
        return obrigatorio;
    }

    @Override
    public boolean eValido() {
      return getText().trim().length() == 11;
    }

    @Override
    public boolean eVazio() {
        return getText().trim().equals("");
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