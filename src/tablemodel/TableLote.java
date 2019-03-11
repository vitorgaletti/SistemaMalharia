package tablemodel;

import static com.sun.glass.ui.Cursor.setVisible;
import tabela.*;
import dao.DaoMateriaPrima;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;
import pojo.ItensCompra;
import pojo.Lote;
import pojo.MateriaPrima;

public class TableLote extends AbstractTableModel {
    private List<Lote> dados = new ArrayList();
    private String[] colunas = {"Lote","Data Validade","Quantidade"};
    
     public TableLote() {
       
    }
    
    @Override
    public int getRowCount() {
        return dados.size(); 
    }

    @Override
    public int getColumnCount() {
       return colunas.length;
    }
    
    @Override
    public String getColumnName(int coluna) {
       return colunas[coluna];
    }
    
    public void adicionaLinha() {
        dados.add(new Lote());
        fireTableDataChanged();
    }
    
    public void removeLinha(int linha) {
        dados.remove(linha);
        fireTableDataChanged();
    }

    public void limparDados() {
       dados = new ArrayList();
       fireTableDataChanged();
    }

    public List<Lote> getDados() {
       return dados;
    }

    public void setDados(List<Lote> dados) {
       this.dados = dados;
       fireTableDataChanged();
    }    

    @Override
    public Object getValueAt(int linha, int coluna) {
       switch (coluna) {
           case 0 : return dados.get(linha).getLote();
           case 1: return dados.get(linha).getDatavalidade();
           case 2: return dados.get(linha).getQuantidade();
          
           
          default: JOptionPane.showMessageDialog(null,
                   "Coluna não tratada em TableItensCompra (getValueAt).");
                    return null;
       }
    }

    @Override
    public void setValueAt(Object valor, int linha, int coluna) {
       switch (coluna) {
           case 0: dados.get(linha).setLote((String) valor);
           case 1: dados.get(linha).setDatavalidade((Date) valor);
           case 2: dados.get(linha).setQuantidade((Integer) valor);
         
          
           
         
          
           
           default: JOptionPane.showMessageDialog(null,
                   "Coluna não tratada em TableItensCompra (setValueAt).");
       }
    }
    public Lote getLote(int linha) {
        return dados.get(linha);
    }
    
    public void alteraDataValidade(Object valor, int linha) {
        dados.get(linha).setDatavalidade((Date) valor);
        fireTableRowsUpdated(linha, linha);
    }
    
    public void alteraQuantidade(Object valor, int linha) {
        dados.get(linha).setQuantidade((int) valor);
        fireTableRowsUpdated(linha, linha);
    }

    public void alteraLote(Object valor, int linha) {
        dados.get(linha).setLote((String) valor);
        fireTableRowsUpdated(linha, linha);
    }
    
    public MateriaPrima getMateriaPrima(int linha) {
        return dados.get(linha).getMateriaprima();
    }
  
   
}