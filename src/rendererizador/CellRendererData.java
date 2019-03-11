package rendererizador;

import java.awt.Component;
import java.text.SimpleDateFormat;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

public class CellRendererData extends DefaultTableCellRenderer 
{
    private SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy");

    @Override
    public Component getTableCellRendererComponent(JTable tabela, Object valor, boolean isSelected, boolean hasFocus, int linha, int coluna)
    {
        try 
        {
            if (valor == null) 
            {
                valor = 0;
            }

            if (valor instanceof String) 
            {        
               
                setText(new SimpleDateFormat("dd/MM/yyyy").format(sdf1.parse((String) valor)) + "");
               
            }

            setHorizontalAlignment(SwingConstants.CENTER);
        } 
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return this;
    }
}