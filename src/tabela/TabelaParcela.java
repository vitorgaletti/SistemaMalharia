package tabela;

import componente.MeuComponente;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import pojo.ParcelaContaPagar;
import rendererizador.RenderizadorCheckBox;
import rendererizador.RenderizadorData;
import rendererizador.RenderizadorMonetario;
import tablemodel.TableParcela;




public class TabelaParcela extends JPanel implements MeuComponente {
   private JScrollPane jsp = new JScrollPane();
   private TableParcela pcp = new TableParcela();
   
   
   private JTable tabela = new JTable(pcp) {
        @Override
        public Component prepareRenderer(TableCellRenderer renderer,
                int linha, int coluna) {
            Component c = super.prepareRenderer(renderer, linha, coluna);
            if (linha % 2 == 0) {
                  c.setBackground(Color.LIGHT_GRAY);
            } else {
                c.setBackground(getBackground());
            }
            if (isCellSelected(linha, coluna)) {
                 c.setBackground(new Color(27, 215, 252));
            }
            return c;
        }       
   };

   
   public TabelaParcela() {
       jsp.setViewportView(tabela);
       jsp.setPreferredSize(new Dimension(600,200));
       add(jsp);
  
       tabela.getColumnModel().getColumn(1).setCellRenderer(new RenderizadorData());
       tabela.getColumnModel().getColumn(2).setCellRenderer(new RenderizadorMonetario());
       tabela.getColumnModel().getColumn(3).setCellRenderer(new RenderizadorMonetario());
       tabela.getColumnModel().getColumn(4).setCellRenderer(new RenderizadorCheckBox());
       
  
      
   }
   
    

    @Override
    public boolean eObrigatorio() {
        return true;
    }

    @Override
    public boolean eValido() {
        return true;
    }

    @Override
    public boolean eVazio() {
        return pcp.getRowCount() == 0;
    }

    @Override
    public void limpar() {
        pcp.limparDados();
      
    }

    @Override
    public void habilitar(boolean status) {
        tabela.setEnabled(status);

    }

    @Override
    public Object getValor() {
       return  pcp.getDados();
    }

    @Override
    public void setValor(Object valor) {
      pcp.setDados((List<ParcelaContaPagar>) valor);
        
       
    }
    
   

    @Override
    public String getNome() {
        return "Parcelas";
    }
    
   
    public TableParcela getTableParcela() {
        return pcp;
    }
    
    public int getLinhaSelecionada() {
        return tabela.getSelectedRow();
    }
    
    public JTable getTabela() {
        return tabela;
    }
}