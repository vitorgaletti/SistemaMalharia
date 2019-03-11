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

public class MeuCampoCEP extends JFormattedTextField implements MeuComponente {
    private boolean obrigatorio;
    private String nome;
   
    public MeuCampoCEP(int tamanhoCampo, boolean obrigatorio, String nome) {
        this.obrigatorio = obrigatorio;
        this.nome = nome;
        setColumns(tamanhoCampo);
        try {
            MaskFormatter mf = new MaskFormatter("#####-###"); 
            mf.install(this);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Não foi possível"
                    + " iniciar MeuCampoCEP");
        }
    }

    @Override
    public boolean eObrigatorio() {
        return obrigatorio;
    }

    @Override
    public boolean eValido() {
       if (eVazio()) {
            return true;
        }else {
           return getText().trim().length() == 9;
       }
    }

    @Override
       public boolean eVazio() {
          
        if (getText().equals("     -    ")) {
            
        }
       return true;
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