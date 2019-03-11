package tabela;

import componente.MeuComponente;
import dao.DaoMateriaPrima;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import pojo.ItensCompra;
import pojo.ItensPedidoDeCompra;
import pojo.MateriaPrima;
import rendererizador.RenderizadorInteiro;
import rendererizador.RenderizadorMonetario;
import tablemodel.TableItensCompra;




public class TabelaItensCompra extends JPanel implements MeuComponente {
   private JScrollPane jsp = new JScrollPane();
   private TableItensCompra ic = new TableItensCompra();
   
 
   private Icon iconIncluir = new ImageIcon("src/imagens/add.png"); 
   private Icon iconExcluir = new ImageIcon("src/imagens/remove.png");
   
   private JTable tabela = new JTable(ic) {
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
   private JButton botaoAdicionar = new JButton(iconIncluir);
   private JButton botaoRemover = new JButton(iconExcluir);
   
   public TabelaItensCompra() {
       jsp.setViewportView(tabela);
       jsp.setPreferredSize(new Dimension(600,200));
       add(jsp);
       add(botaoAdicionar);
       add(botaoRemover);
       botaoAdicionar.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent ae) {

                       ic.adicionaLinha();
                       tabela.addRowSelectionInterval(ic.getRowCount() - 1,ic.getRowCount() - 1);
             
           }
       });
       botaoRemover.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent ae) {
               if (tabela.getSelectedRow() >= 0) {
                   if (ic.getRowCount() > 1) {
                      ic.removeLinha(tabela.getSelectedRow());
                      tabela.addRowSelectionInterval(ic.getRowCount() - 1,ic.getRowCount() - 1);
                   }
               } else {
                   JOptionPane.showMessageDialog(null,
                           "Selecione uma linha para poder excluí-la.");
                    
               }
           }
       });
       tabela.getColumnModel().getColumn(1).setCellRenderer(new RenderizadorInteiro());
       tabela.getColumnModel().getColumn(2).setCellRenderer(new RenderizadorMonetario());
       tabela.getColumnModel().getColumn(3).setCellRenderer(new RenderizadorMonetario());
       tabela.getColumnModel().getColumn(4).setCellRenderer(new RenderizadorMonetario());
      
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
        return ic.getRowCount() == 0;
    }

    @Override
    public void limpar() {
        ic.limparDados();
        tabela.addRowSelectionInterval(0, 0);
    }

    @Override
    public void habilitar(boolean status) {
        tabela.setEnabled(status);
        botaoAdicionar.setEnabled(status);
        botaoRemover.setEnabled(status);
    }

    @Override
    public Object getValor() {
       return  ic.getDados();
    }

    @Override
    public void setValor(Object valor) {
      ic.setDados((List<ItensCompra>) valor);
   }
   
    @Override
    public String getNome() {
        return "Itens";
    }
    
   
    public TableItensCompra getTableItensCompra() {
        return ic;
    }
    
    public int getLinhaSelecionada() {
        return tabela.getSelectedRow();
    }
    
    public JTable getTabela() {
        return tabela;
    }
}