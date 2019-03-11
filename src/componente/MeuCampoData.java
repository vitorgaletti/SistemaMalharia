package componente;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.text.MaskFormatter;

public class MeuCampoData extends JFormattedTextField implements MeuComponente {
    private boolean obrigatorio;
    private String nome;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");    

    public MeuCampoData(int tamanhoCampo, boolean obrigatorio, String nome) {
        this.obrigatorio = obrigatorio;
        this.nome = nome;
        setColumns(tamanhoCampo);
        try {
            MaskFormatter mf = new MaskFormatter("##/##/####");
            mf.install(this);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Não foi possível"
                    + " iniciar MeuCampoData");
        }
    }

    @Override
    public boolean eObrigatorio() {
        return obrigatorio;
    }

    @Override
    public boolean eValido() {
        try {
            sdf.setLenient(false);
            sdf.parse(getText());
            return true;
        } catch (Exception e) {
            return false;
        }
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
        try {
            return sdf.parse(getText());
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possível obter a data.");
            return null;
        }
    }

    @Override
    public void setValor(Object valor) {
        if(valor == null){
            setText("");
        } else {
            setText(sdf.format((Date) valor));
        }
    }

    @Override
    public String getNome() {
        return nome;
    }
    
   public Date getValorDate() {
       if(eVazio()) {
           return null;
       } else {
           try {
               SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
               return sdf.parse(getText());
           } catch(Exception e) {
               JOptionPane.showMessageDialog(null, "Não foi possível obter a data");
               e.printStackTrace();
               return null;
           }
       }
   }
}