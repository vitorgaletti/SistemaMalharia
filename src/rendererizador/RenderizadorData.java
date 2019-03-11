package rendererizador;

import componente.MeuCampoData;
import java.awt.Component;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

public class RenderizadorData extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable jtable, Object o, boolean bln, boolean bln1, int i, int i1) {
        MeuCampoData mcd = new MeuCampoData(6, false, "");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");    
        mcd.setValor((Date) o);
        mcd.setBorder(null);
        setHorizontalAlignment(SwingConstants.RIGHT);
        return mcd;
    }    
}