package tabela;

import componente.MeuComponente;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
import pojo.Lote;
import rendererizador.RenderizadorData;
import rendererizador.RenderizadorInteiro;
import tablemodel.TableLote;



public class TabelaLote extends JPanel implements MeuComponente {
   private JScrollPane jsp = new JScrollPane();
   private TableLote imp = new TableLote();
   private Icon iconIncluir = new ImageIcon("src/imagens/add.png"); 
   private Icon iconExcluir = new ImageIcon("src/imagens/remove.png");
   
   private JTable tabela = new JTable(imp) {
        @Override
        public Component prepareRenderer(TableCellRenderer renderer,
                int linha, int coluna) {
            Component c = super.prepareRenderer(renderer, linha, coluna);
            if (linha % 2 == 0) {
                c.setBackground(Color.WHITE);
              
            } else {
                c.setBackground(getBackground());
            }
            if (isCellSelected(linha, coluna)) {
                c.setBackground(Color.WHITE);
            }
            return c;
        }       
   };

   
    public TabelaLote(){
        
        setLayout(new FlowLayout());
        
        jsp.setOpaque(false);
        jsp.getViewport().setOpaque(false);
        jsp.setViewportView(tabela);
        setOpaque(false);
        jsp.setPreferredSize(new Dimension (600,200));
        add(jsp);
        

       tabela.getColumnModel().getColumn(1).setCellRenderer(new RenderizadorData());
    }

    @Override
    public boolean eObrigatorio() {
        return false;
    }

    @Override
    public boolean eValido() {
        return true;
    }

    @Override
    public boolean eVazio() {
        return imp.getRowCount() == 0;
    }

    @Override
    public void limpar() {
        imp.limparDados();

    }

    @Override
    public void habilitar(boolean status) {
        tabela.setEnabled(status);

    }

    @Override
    public Object getValor() {
        return imp.getDados();
        
    }

    @Override
    public void setValor(Object valor) {
           imp.setDados((List<Lote>) valor);
           
    }
    
   

    @Override
    public String getNome() {
        return "Lotes";
    }
    
    public TableLote getTableLote() {
        return imp;
    }
  
    
    public int getLinhaSelecionada() {
        return tabela.getSelectedRow();
    }
    
    public JTable getTabela() {
        return tabela;
    }
}