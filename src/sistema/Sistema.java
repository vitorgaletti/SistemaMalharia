
package sistema;



import de.javasoft.plaf.synthetica.SyntheticaAluOxideLookAndFeel;
import java.text.ParseException;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import tela.TelaSistema;

public class Sistema {
    
    public static void main(String args[]) {
        
        TelaSistema  telaSistema = new TelaSistema();
        
    
    try{
     
        UIManager.setLookAndFeel(new SyntheticaAluOxideLookAndFeel());
       
    } catch (ParseException ex) {
      ex.printStackTrace();
    } catch (UnsupportedLookAndFeelException ex) {
    ex.printStackTrace();
    }

  }
    
}
