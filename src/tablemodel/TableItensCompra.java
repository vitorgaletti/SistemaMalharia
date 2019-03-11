package tablemodel;


import tabela.*;
import dao.DaoItensPedidoDeCompra;
import dao.DaoMateriaPrima;
import dao.DaoPedidoDeCompra;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;
import pojo.Compra;
import pojo.ItensCompra;
import pojo.ItensPedidoDeCompra;
import pojo.MateriaPrima;
import pojo.PedidoDeCompra;


public class TableItensCompra extends AbstractTableModel {
    private List<ItensCompra> dados = new ArrayList();
    
    private String[] colunas = {"Materia-Prima", "Quantidade",
                                "Valor Unitário", "Desconto", "Valor Total"};
    
   public TableItensCompra() {
        dados.add(new ItensCompra());
      
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
       dados.add(new ItensCompra());
       fireTableDataChanged();
    }
    
    public void removeLinha(int linha) {
        dados.remove(linha);
        fireTableDataChanged();
    }

    public void limparDados() {
       dados = new ArrayList();
       dados.add(new ItensCompra());
       fireTableDataChanged();
    }

    public List<ItensCompra> getDados() {
       return dados;
    }
    
    public void setDados(List<ItensCompra> dados) {
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
                   "Coluna não tratada em TableItensCompra (getValueAt).");
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
                   "Coluna não tratada em TableItensCompra (setValueAt).");
       }
    }
   
    public ItensCompra getItensCompra(int linha) {
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
    
    
   
   public void insereItensPedidoDeCompra(int idPedidoDeCompra ) {
      try {
       PedidoDeCompra pedidodecompra = new PedidoDeCompra();
       DaoPedidoDeCompra daoPedidoDeCompra = new DaoPedidoDeCompra(pedidodecompra);
       pedidodecompra.setId(idPedidoDeCompra);
       daoPedidoDeCompra.consultar();
    
       List<ItensCompra> itens = new ArrayList();
       ItensCompra itensCompra;
       ItensPedidoDeCompra itensPedidoCompra;
      
      
       for(int i = 0; i < pedidodecompra.getItensPedidoDeCompra().size(); i++) {
           
           itensPedidoCompra = pedidodecompra.getItensPedidoDeCompra().get(i);
           itensCompra = new ItensCompra();
           itensCompra.setMateriaprima(itensPedidoCompra.getMateriaprima());
           itensCompra.setQuantidade(itensPedidoCompra.getQuantidade());
           itensCompra.setValorunitario(itensPedidoCompra.getValorunitario());
           itensCompra.setDesconto(itensPedidoCompra.getDesconto());
           itensCompra.setValortotal(itensPedidoCompra.getValortotal());
           itens.add(itensCompra);
           
          }
          
         setDados(itens);
         
         }catch (NullPointerException e) {
                limparDados();
         }
          
      }
       
   
  }


