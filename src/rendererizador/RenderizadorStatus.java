package rendererizador;

import componente.MeuJTextField;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

public class RenderizadorStatus extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable jtable, Object o, boolean bln, boolean bln1, int i, int i1) {
        MeuJTextField mjtf =  new MeuJTextField (5, true, false, "");  
        mjtf.setText((String) o);
        mjtf.setBorder(null);
        mjtf.setHorizontalAlignment(SwingConstants.CENTER);
        return mjtf;
    }    
}