package rendererizador;


import componente.MeuCampoInteiro;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class RenderizadorInteiro extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable jtable, Object o, boolean bln, boolean bln1, int i, int i1) {
        MeuCampoInteiro mci =  new MeuCampoInteiro(5, true, true, "");
        mci.setValor((int) o);
        mci.setBorder(null);
        return mci;
    }    
}