package tablemodel;


import tabela.*;
import dao.DaoCompra;
import dao.DaoCondicaoDePagamento;
import dao.DaoItensPedidoDeCompra;
import dao.DaoMateriaPrima;
import static java.awt.PageAttributes.MediaType.C;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;
import pojo.Compra;
import pojo.CondicaoDePagamento;
import pojo.ContaPagar;
import pojo.ItensPedidoDeCompra;
import pojo.ParcelaContaPagar;
import tela.TelaContaPagar;

public class TableParcela extends AbstractTableModel {
    
    public ParcelaContaPagar parcelaContaPagar = new ParcelaContaPagar();
    public CondicaoDePagamento condicaoDePagamento = new CondicaoDePagamento();
    public ContaPagar contaPagar = new ContaPagar();
    private List<ParcelaContaPagar> parcelas = new ArrayList();
   
   
   private String[] colunas = {"N°de Parcelas","Vencimento","Valor", "Valor Pendente", "Quitada"};
    
   
  public TableParcela() {
       
    }
    
    @Override
    public int getRowCount() {
      return parcelas.size();
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
       parcelas.add(new ParcelaContaPagar());
       fireTableDataChanged();
    }
    
    public void removeLinha(int linha) {
        parcelas.remove(linha);
        
        fireTableDataChanged();
    }

    public void limparDados() {
       parcelas = new ArrayList();     
       fireTableDataChanged();
    }

    public List<ParcelaContaPagar> getDados() {
       return parcelas;
    }
    

    public void setDados(List<ParcelaContaPagar> parcelas) {
      this.parcelas = parcelas;
       fireTableDataChanged();
    }  
    


    @Override
    public Object getValueAt(int linha, int coluna) {
       switch (coluna) {
           case 0: return linha + 1;
           case 1: return parcelas.get(linha).getVencimento();
           case 2: return parcelas.get(linha).getValor();
           case 3: return parcelas.get(linha).getValorpendente();
           case 4: return parcelas.get(linha).isQuitada();
           
           
           default: JOptionPane.showMessageDialog(null,
                   "Coluna não tratada em TableItensCompra (getValueAt).");
                    return null;
       }
    }

    @Override
    public void setValueAt(Object valor, int linha, int coluna) {
       switch (coluna) {
           case 0: parcelas.get(linha);
           case 1: parcelas.get(linha).setVencimento((Date) valor);
           case 2: parcelas.get(linha).setValor((BigDecimal) valor);
           case 3: parcelas.get(linha).setValorpendente((BigDecimal) valor);
           case 4: parcelas.get(linha).setQuitada((boolean) valor);
           
           default: JOptionPane.showMessageDialog(null,
                   "Coluna não tratada em TableItensCompra (setValueAt).");
       }
    }
   
    public ParcelaContaPagar getParcelaContaPagar(int linha) {
        return parcelas.get(linha);
    }
    
   
    

   public void gerarParcela(int idCondicaoDePagamento ) {
      
        CondicaoDePagamento condicaodepagamento = new CondicaoDePagamento();
        DaoCondicaoDePagamento daoCondicaoDePagamento = new DaoCondicaoDePagamento(condicaodepagamento);
        condicaodepagamento.setId(idCondicaoDePagamento);
        daoCondicaoDePagamento.consultar();
        
        ParcelaContaPagar parcela = null;
        Calendar vencimento = Calendar.getInstance();
        
        BigDecimal somaparcela =  BigDecimal.ZERO;
        if(condicaodepagamento.getCarencia() == 30){
            vencimento.add(Calendar.MONTH, 1);
        } else {
            vencimento.add(Calendar.DAY_OF_MONTH, condicaodepagamento.getCarencia());
        }
       
       for(int i = 0; i < condicaodepagamento.getNparcelas(); i++) {
          
          parcela = new ParcelaContaPagar();
          parcela.setId(parcelaContaPagar.getId());
          parcela.setVencimento(vencimento.getTime());
          
          
          
          if(condicaodepagamento.getPrazoentreparcelas() == 30){
              vencimento.add(Calendar.MONTH,1);
          } else {
              vencimento.add(Calendar.DAY_OF_MONTH,condicaodepagamento.getPrazoentreparcelas());
          }
          BigDecimal nparcelas = new BigDecimal (condicaodepagamento.getNparcelas());
          parcela.setValor(TelaContaPagar.campoValorliquido.getValor().divide(nparcelas, BigDecimal.ROUND_HALF_EVEN));
          parcela.setValorpendente(TelaContaPagar.campoValorliquido.getValor().divide(nparcelas, BigDecimal.ROUND_HALF_EVEN));
          parcelas.add(parcela);
          somaparcela = somaparcela.add(parcela.getValor());
         }
         
            BigDecimal diferenca = BigDecimal.ZERO;
       
         if(somaparcela != TelaContaPagar.campoValorliquido.getValor()){
             diferenca = TelaContaPagar.campoValorliquido.getValor().subtract(somaparcela);
             
             parcela.setValor(parcela.getValor().add(diferenca));
             
            }
        
          
          setDados(parcelas);
         }
    
       
    
 }

