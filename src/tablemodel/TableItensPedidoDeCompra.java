package tablemodel;

import tabela.*;
import dao.DaoMateriaPrima;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;
import pojo.ItensPedidoDeCompra;
import pojo.MateriaPrima;

public class TableItensPedidoDeCompra extends AbstractTableModel {
    private List<ItensPedidoDeCompra> dados = new ArrayList();
    private String[] colunas = {"Materia-Prima", "Quantidade",
                                "Valor Unitário", "Desconto", "Valor Total"};

    public TableItensPedidoDeCompra() {
        dados.add(new ItensPedidoDeCompra());
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
        dados.add(new ItensPedidoDeCompra());
        fireTableDataChanged();
    }
    
    public void removeLinha(int linha) {
        dados.remove(linha);
        fireTableDataChanged();
    }

    public void limparDados() {
       dados = new ArrayList();
       dados.add(new ItensPedidoDeCompra());
       fireTableDataChanged();
    }

    public List<ItensPedidoDeCompra> getDados() {
       return dados;
    }

    public void setDados(List<ItensPedidoDeCompra> dados) {
       this.dados = dados;
       fireTableDataChanged();
    }    

    @Override
    public Object getValueAt(int linha, int coluna) {
       switch (coluna) {
           case 0: return dados.get(linha).getMateriaprima().getNome();
           case 1: return dados.get(linha).getQuantidade();
           case 2: return dados.get(linha).getValorunitario();
           case 3: return dados.get(linha).getDesconto();
           case 4: return dados.get(linha).getValortotal();
           default: JOptionPane.showMessageDialog(null,
                   "Coluna não tratada em TableItensPedidoDeCompra (getValueAt).");
                    return null;
       }
    }

    @Override
    public void setValueAt(Object valor, int linha, int coluna) {
       switch (coluna) {
           case 0: dados.get(linha).getMateriaprima().setId((Integer) valor);
           case 1: dados.get(linha).setQuantidade((Integer) valor);
           case 2: dados.get(linha).setValorunitario((BigDecimal) valor);
           case 3: dados.get(linha).setDesconto((BigDecimal) valor);
           case 4: dados.get(linha).setValortotal((BigDecimal) valor);
           default: JOptionPane.showMessageDialog(null,
                   "Coluna não tratada em TableItensPedidoDeCompra (setValueAt).");
       }
    }
    
    public ItensPedidoDeCompra getItensPedidoDeCompra(int linha) {
        return dados.get(linha);
    }
    
    public void alteraMateriaPrima(Object valor, int linha) {
        MateriaPrima materiaprima = dados.get(linha).getMateriaprima();
        DaoMateriaPrima daoMateriaPrima = new DaoMateriaPrima(materiaprima);
        materiaprima.setId((int) valor);
        daoMateriaPrima.consultar();
        fireTableRowsUpdated(linha, linha);
    }
        public void alteraValorUnitario(Object valor, int linha) {
        dados.get(linha).setValorunitario((BigDecimal) valor);
        fireTableRowsUpdated(linha, linha);
    }
    
    public void alteraValorTotal(Object valor, int linha) {
        dados.get(linha).setValortotal((BigDecimal) valor);
        fireTableRowsUpdated(linha, linha);
    }

    public MateriaPrima getMateriaPrima(int linha) {
        return dados.get(linha).getMateriaprima();
    }
    
    public void alteraQuantidade(Object valor, int linha) {
        dados.get(linha).setQuantidade((int) valor);
        fireTableRowsUpdated(linha, linha);
    }
    
    public void alteraDesconto(Object valor, int linha) {
        dados.get(linha).setDesconto((BigDecimal) valor);
        fireTableRowsUpdated(linha, linha);
    }
  
   
}