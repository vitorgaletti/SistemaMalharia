package componente;

import componente.MeuComponente;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.MaskFormatter;
import javax.swing.text.PlainDocument;

public class MeuCampoCNPJ extends JFormattedTextField implements MeuComponente {
    private boolean obrigatorio;
    private String nome;
     
    public MeuCampoCNPJ(int tamanhoCampo, boolean obrigatorio, String nome) {
        this.obrigatorio = obrigatorio;
        this.nome = nome;
        setColumns(tamanhoCampo);
        try {
            MaskFormatter mf = new MaskFormatter("##.###.###/####-##");
            mf.install(this);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Não foi possível"
                    + " iniciar MeuCampoCNPJ");
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
        }
        boolean retorno = false;
        if (getText().indexOf("/") != -1) {            
               
        }            
           return validaCNPJ(getText()); 
        }
    
    public boolean validaCNPJ(String s_aux) {

           
        if (s_aux.replace(".", "").replace("/", "").replace("-", "").length() == 14) {
            
            String campoTexto = s_aux.replace(".", "").replace("/", "").replace("-", "");
            if (campoTexto.equals("11111111111111")
                    || campoTexto.equals("22222222222222")
                    || campoTexto.equals("33333333333333")
                    || campoTexto.equals("44444444444444")
                    || campoTexto.equals("55555555555555")
                    || campoTexto.equals("66666666666666")
                    || campoTexto.equals("77777777777777")
                    || campoTexto.equals("88888888888888")
                    || campoTexto.equals("99999999999999")
                    || campoTexto.equals("00000000000000")) {
                return false;
            } else {
                String str_cnpj = campoTexto;
                int soma = 0, aux, dig;
                String cnpj_calc = str_cnpj.substring(0, 12);
                if (str_cnpj.length() != 14) {
                    return false;
                }
                char[] chr_cnpj = str_cnpj.toCharArray();
                /* Primeira parte */
                for (int i = 0; i < 4; i++) {

                    if (chr_cnpj[i] - 48 >= 0 && chr_cnpj[i] - 48 <= 9) {
                        soma += (chr_cnpj[i] - 48) * (6 - (i + 1));
                    }
                }
                for (int i = 0; i < 8; i++) {

                    if (chr_cnpj[i + 4] - 48 >= 0 && chr_cnpj[i + 4] - 48 <= 9) {
                        soma += (chr_cnpj[i + 4] - 48) * (10 - (i + 1));
                    }
                }
                dig = 11 - (soma % 11);
                cnpj_calc += (dig == 10 || dig == 11)
                        ? "0" : Integer.toString(dig);
                /* Segunda parte */
                soma = 0;
                for (int i = 0; i
                        < 5; i++) {
                    if (chr_cnpj[i] - 48 >= 0 && chr_cnpj[i] - 48 <= 9) {
                        soma += (chr_cnpj[i] - 48) * (7 - (i + 1));
                    }
                }
                for (int i = 0; i
                        < 8; i++) {
                    if (chr_cnpj[i + 5] - 48 >= 0 && chr_cnpj[i + 5] - 48 <= 9) {
                        soma += (chr_cnpj[i + 5] - 48) * (10 - (i + 1));
                    }
                }
                dig = 11 - (soma % 11);
                cnpj_calc += (dig == 10 || dig == 11)
                        ? "0" : Integer.toString(dig);
                return str_cnpj.equals(cnpj_calc);
            }
        } else {
            return false;
        }

    }

    @Override
    public boolean eVazio() {
         boolean retorno = false;
         if (getText().equals("  .   .   /    -  ")) {
             retorno = true;
         }
           return retorno;
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




