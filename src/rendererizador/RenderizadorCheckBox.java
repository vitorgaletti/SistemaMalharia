package rendererizador;



import componente.MeuCampoCheckBox;
import java.awt.Component;
import java.util.Date;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

public class RenderizadorCheckBox extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable jtable, Object o, boolean bln, boolean bln1, int i, int i1) {
        MeuCampoCheckBox mccb = new MeuCampoCheckBox(false, true, "");
        mccb.setValor((boolean) o);
        mccb.setBorder(null);
        setHorizontalAlignment(SwingConstants.CENTER);
        return mccb;
    }    
}