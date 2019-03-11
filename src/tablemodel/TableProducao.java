package tablemodel;

import tabela.*;
import dao.DaoMateriaPrima;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;
import pojo.ItensProducao;
import pojo.MateriaPrima;

public class TableProducao extends AbstractTableModel {
    private List<ItensProducao> dados = new ArrayList();
    private String[] colunas = {"Materia-Prima", "Quantidade"};

    public TableProducao() {
        dados.add(new ItensProducao());
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
        dados.add(new ItensProducao());
        fireTableDataChanged();
    }
    
    public void removeLinha(int linha) {
        dados.remove(linha);
        fireTableDataChanged();
    }

    public void limparDados() {
       dados = new ArrayList();
       dados.add(new ItensProducao());
       fireTableDataChanged();
    }

    public List<ItensProducao> getDados() {
       return dados;
    }

    public void setDados(List<ItensProducao> dados) {
       this.dados = dados;
       fireTableDataChanged();
    }    

    @Override
    public Object getValueAt(int linha, int coluna) {
       switch (coluna) {
           case 0: return dados.get(linha).getMateriaprima().getNome();
           case 1: return dados.get(linha).getQuantidade();
           default: JOptionPane.showMessageDialog(null,
                   "Coluna não tratada em TableModelItem (getValueAt).");
                    return null;
       }
    }

    @Override
    public void setValueAt(Object valor, int linha, int coluna) {
       switch (coluna) {
           case 0: dados.get(linha).getMateriaprima().setId((Integer) valor);
           case 1: dados.get(linha).setQuantidade((Integer) valor);
           default: JOptionPane.showMessageDialog(null,
                   "Coluna não tratada em TableModelItem (setValueAt).");
       }
    }
    
    public ItensProducao getItensProducao(int linha) {
        return dados.get(linha);
    }
    
    public void alteraMateriaPrima(Object valor, int linha) {
        
         MateriaPrima materiaprima = dados.get(linha).getMateriaprima();
        DaoMateriaPrima daoMateriaPrima = new DaoMateriaPrima(materiaprima);
        materiaprima.setId((int) valor);
        daoMateriaPrima.consultar();
        fireTableRowsUpdated(linha, linha);
    }

    public MateriaPrima getMateriaPrima(int linha) {
        return dados.get(linha).getMateriaprima();
    }
    
    public void alteraQuantidade(Object valor, int linha) {
        dados.get(linha).setQuantidade((int) valor);
        fireTableRowsUpdated(linha, linha);
    }
     
  
   
}