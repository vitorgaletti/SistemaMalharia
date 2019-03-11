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

public class MeuCampoRGIE extends MeuCampoFormatado2 {

    
     
 public MeuCampoRGIE(int tamanho, boolean obrigatorio, String nome, boolean podeHabilitar) {
        super(tamanho, obrigatorio, nome, podeHabilitar);
        try {
            MaskFormatter mf = new MaskFormatter("##.###.###-#"); 
            mf.install(this);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Não foi possível"
                    + " iniciar MeuCampoRG");
        }
    }



    @Override
    public boolean eValido() {
        if (eVazio()) {
            return true;
        }
        
       boolean retorno = false;  
       if (getText().contains(".")) {            
               return validaRG();
        } else {            
            return validaIE();
        }
    }

    public boolean eVazio() {
       
        boolean retorno = false;
        if (getText().equals("  .   .   - ") | getText().equals("        -  ")) {  
            retorno = true;
        }
        return retorno;

    }

    
    public boolean validaRG() {
        
        return getText().trim().length() == 12;
    }
  
    public boolean validaIE() {
        return getText().trim().length() == 11;
    }
    
}