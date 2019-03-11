package rendererizador;


import componente.MeuCampoMonetario;
import java.awt.Component;
import java.math.BigDecimal;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class RenderizadorMonetario extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable jtable, Object o, boolean bln, boolean bln1, int i, int i1) {
        MeuCampoMonetario mcm =  new MeuCampoMonetario(15, false, true, true, "");
        mcm.setValor((BigDecimal) o);
        mcm.setBorder(null);
        return mcm;
    }    
}