package tela;

import componente.MeuCampoBuscar;
import componente.MeuCampoComboBox;
import componente.MeuCampoData;
import componente.MeuCampoInteiro;
import componente.MeuCampoMonetario;
import dao.DaoCondicaoDePagamento;
import dao.DaoFornecedor;
import dao.DaoMateriaPrima;
import dao.DaoPedidoDeCompra;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import static java.awt.event.KeyEvent.VK_ENTER;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import pojo.Compra;
import pojo.ItensPedidoDeCompra;
import pojo.MateriaPrima;
import pojo.ParametrosConsulta;
import pojo.PedidoDeCompra;
import rendererizador.CellRendererData;
import rendererizador.InteiroRender;
import rendererizador.MonetarioRender;
import rendererizador.RenderizadorQtde;
import rendererizador.RenderizadorTexto;
import tabela.TabelaItensPedidoDeCompra;
import tablemodel.TableItensPedidoDeCompra;
import util.FiltroPesquisa;



public class TelaPedidoDeCompra extends TelaCadastro {
    
    public static TelaPedidoDeCompra tela;
    
    public PedidoDeCompra pedidodecompra = new PedidoDeCompra();
    public DaoPedidoDeCompra daoPedidoDeCompra = new DaoPedidoDeCompra(pedidodecompra);
    public ItensPedidoDeCompra itensPedidoDeCompra = new ItensPedidoDeCompra();
    
    public ParametrosConsulta parametrosConsultaFornecedor =
            new ParametrosConsulta("Consulta de Fornecedor",
                                    DaoFornecedor.SQL_PESQUISAR_ATIVOS,
                                    new String[] {"Código", "Razão Social", "CNPJ", "Ativo", "Cidade"},
                                    new FiltroPesquisa[] {
                                    new FiltroPesquisa("CÓDIGO", "ID", Integer.class),
                                    new FiltroPesquisa("RAZAO SOCIAL", "FORNECEDOR_RAZAOSOCIAL", String.class),
                                    new FiltroPesquisa("CNPJ", "FORNECEDOR_CNPJ", String.class),
                                    new FiltroPesquisa("ATIVO", "ATIVO", String.class),
                                    new FiltroPesquisa("CIDADE", "FORNECEDOR_CIDADE", String.class),
            
        },
                                             new DefaultTableCellRenderer[] { 
                                                                            new InteiroRender(), new RenderizadorTexto(),
                                                                            new RenderizadorTexto(), new RenderizadorTexto(), new RenderizadorTexto()},
                                   this, this, false, false, false
        );
        
        public ParametrosConsulta parametrosConsultaCondicaoDePagamento =
            new ParametrosConsulta("Consulta de Condição de Pagamento",
                                   DaoCondicaoDePagamento.SQL_PESQUISAR,
                               new String[] {"Código", "Descrição"},
                               new FiltroPesquisa[] {new FiltroPesquisa("CÓDIGO", "ID", Integer.class),
                                                     new FiltroPesquisa("DESCRIÇÃO", "DESCRICAO", String.class)
            
                                },
                                new DefaultTableCellRenderer[] { 
                                                                 new InteiroRender(), new RenderizadorTexto(), new InteiroRender()},
                                this, this, false, false, false
        );
         
        public ParametrosConsulta parametrosConsultaItensMateriaPrima =
            new ParametrosConsulta("Consulta de Matéria Prima",
                                   DaoMateriaPrima.SQL_PESQUISAR_ATIVOS,
                                   new String[] {"Código", "Nome", "Quantidade", "Valor Unitário", "Valor Total", "Possui Lote", "Ativo"},
                                   new FiltroPesquisa[] {new FiltroPesquisa("CÓDIGO", "ID", Integer.class),
                                   new FiltroPesquisa("NOME", "MATERIAPRIMA_NOME", String.class),
                                   new FiltroPesquisa("LOTE", "LOTE", String.class),
                                   new FiltroPesquisa("ATIVO", "ATIVO", String.class),
            
                                },
                                new DefaultTableCellRenderer[] { 
                                                                new InteiroRender(), new RenderizadorTexto(), new RenderizadorQtde(), new MonetarioRender(),
                                                                new MonetarioRender(), new RenderizadorTexto(), new RenderizadorTexto()},
                                this, this, false, false, false
        ); 
        
        
    public MeuCampoInteiro campoCodigo = new MeuCampoInteiro(5, true, false, "Código");
    public MeuCampoData campoData = new MeuCampoData(10, true, "Data");
    public MeuCampoMonetario campoValortotal = new MeuCampoMonetario(15, false, false, true, "Valor Total");
    public MeuCampoMonetario campoDesconto  = new MeuCampoMonetario(15, false, true, true, "Valor Desconto");
    public MeuCampoMonetario campoValorliquido = new MeuCampoMonetario(15, false, false, true, "Valor Líquido");
    public MeuCampoComboBox campoStatus = new MeuCampoComboBox(true, true, new String[][] {{"PA", "Pedido Aberto"}, {"PF", "Pedido Fechado"}}, "Status");
    public MeuCampoBuscar campoFornecedor = new MeuCampoBuscar(TelaConsulta.class, TelaCadastroFornecedor.class, DaoFornecedor.SQL_COMBOBOX, parametrosConsultaFornecedor, true, true, "Fornecedor");
    public MeuCampoBuscar campoCondicaoDePagamento = new MeuCampoBuscar(TelaConsulta.class, TelaCadastroCondicaoDePagamento.class, DaoCondicaoDePagamento.SQL_COMBOBOX, parametrosConsultaCondicaoDePagamento, true, true, "Condição de Pagamento");
    
    public MeuCampoBuscar campoItensMateriaPrima = new MeuCampoBuscar(TelaConsulta.class, TelaCadastroMateriaPrima.class, DaoMateriaPrima.SQL_COMBOBOX, parametrosConsultaItensMateriaPrima, false, true, "Matéria-Prima");
    public MeuCampoInteiro campoQuantidadeItens = new MeuCampoInteiro(5, true, true, "Quantidade");
    public static MeuCampoMonetario campoValorunitarioItens = new MeuCampoMonetario(15, false, false, true, "Valor unitário");
    public MeuCampoMonetario campoDescontoItens = new MeuCampoMonetario(15, false, true, true, "Desconto");
    public MeuCampoMonetario campoValortotalItens = new MeuCampoMonetario(15, false, false, true, "Valor total");
    
    public static TabelaItensPedidoDeCompra tabelaItensPedidoDeCompra = new TabelaItensPedidoDeCompra();
    public JLabel frase = new JLabel("<html>Campo Obrigatório <font color=red>*</font><body>");
    public MeuCampoInteiro QtdeEstoque = new MeuCampoInteiro(5, false, false, "Quantidade Disponível no Estoque");
    
    public JPanel painelBorda = new JPanel();
    
    public static TelaCadastro getTela() {  
        if (tela == null) {   
            tela = new TelaPedidoDeCompra();
            TelaSistema.meuJDesktopPane.add(tela);
            tela.addInternalFrameListener(new InternalFrameAdapter() { 
                @Override
                public void internalFrameClosed(InternalFrameEvent e) {
                    TelaSistema.meuJDesktopPane.remove(tela);
                    tela = null;
                }
            });
            
        }
        
        
        TelaSistema.meuJDesktopPane.moveToFront(tela);
        return tela;
    }
    
  

    public TelaPedidoDeCompra() {
        super("Movimento de Pedido De Compra ");
        adicionarComponente(1, 1, 1, 1, campoCodigo,ESQUERDA);
        adicionarComponente(2, 1, 1, 1, campoData,ESQUERDA);
        adicionarComponente(3, 1, 1, 1, campoFornecedor,ESQUERDA);
        adicionarComponente(4, 1, 1, 1, campoValortotal,ESQUERDA);
        adicionarComponente(5, 1, 1, 1, campoDesconto,ESQUERDA);
        adicionarComponente(6, 1, 1, 1, campoValorliquido,ESQUERDA);
        adicionarComponente(7, 1, 1, 1, campoStatus,ESQUERDA);
        adicionarComponente(8, 1, 1, 1, campoCondicaoDePagamento,ESQUERDA);
        adicionarComponente(9, 1, 2, 2, tabelaItensPedidoDeCompra,ESQUERDA);
        adicionarComponente(3, 3, 1, 1, campoItensMateriaPrima,ESQUERDA);
        adicionarComponente(4, 3, 1, 1, QtdeEstoque,ESQUERDA);
        adicionarComponente(5, 3, 1, 1, campoQuantidadeItens,ESQUERDA);
        adicionarComponente(6, 3, 1, 1, campoValorunitarioItens,ESQUERDA);
        adicionarComponente(7, 3, 1, 1, campoDescontoItens,ESQUERDA);
        adicionarComponente(8, 3, 1, 1, campoValortotalItens, ESQUERDA);
        adicionarComponente(10, 2, 1, 1, frase, ESQUERDA);
        
        habilitarCampos(false);
        pack();
        adicionaListeners();
    }
    

    public void setPersistencia() {
        pedidodecompra.setId((int) campoCodigo.getValor());
        pedidodecompra.setData((Date) campoData.getValor());
        pedidodecompra.setValortotal(campoValortotal.getValor());
        pedidodecompra.setDesconto(campoDesconto.getValor());
        pedidodecompra.setValorliquido(campoValorliquido.getValor());
        pedidodecompra.setStatus((String) campoStatus.getValor());
        pedidodecompra.getFornecedor().setId((int) campoFornecedor.getValor());
        pedidodecompra.getCondicaodepagamento().setId((int) campoCondicaoDePagamento.getValor());
        pedidodecompra.setItensPedidoDeCompra(((TableItensPedidoDeCompra)tabelaItensPedidoDeCompra.getTabela().getModel()).getDados());
    }

    public void getPersistencia() {
        campoCodigo.setValor(pedidodecompra.getId());
        campoData.setValor(pedidodecompra.getData());
        campoValortotal.setValor(pedidodecompra.getValortotal());
        campoDesconto.setValor(pedidodecompra.getDesconto());
        campoValorliquido.setValor(pedidodecompra.getValorliquido());
        campoStatus.setValor(pedidodecompra.getStatus());
        campoFornecedor.setValor(pedidodecompra.getFornecedor().getId());
        campoCondicaoDePagamento.setValor(pedidodecompra.getCondicaodepagamento().getId());
        ((TableItensPedidoDeCompra) tabelaItensPedidoDeCompra.getTabela().getModel()).setDados(pedidodecompra.getItensPedidoDeCompra());
    }
    
   
    
    @Override
    public void incluir() {
        super.incluir();
        campoData.setValor(new Date());
    }

    @Override
    public boolean incluirBD() {
        setPersistencia();
        getPersistencia();
        boolean retorno = daoPedidoDeCompra.incluir(); 
        if(campoStatus.getValor() == "PF"){
        if (JOptionPane.showConfirmDialog(null, "Deseja inclur uma Compra Agora ?", "",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
         TelaCompra telaCompra = new TelaCompra();
         getDesktopPane().add(telaCompra);
         telaCompra.moveToFront();
         TelaPedidoDeCompra.tela.dispose();
     
         
          }
        }
        
         return retorno;
     }

    
    @Override
    public boolean alterarBD() {
        setPersistencia();
        getPersistencia(); 
        boolean retorno = daoPedidoDeCompra.alterar();
        if(campoStatus.getValor() == "PF"){
        if (JOptionPane.showConfirmDialog(null, "Deseja inclur uma Compra Agora ?", "",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
        TelaCompra telaCompra = new TelaCompra();
        getDesktopPane().add(telaCompra);
        telaCompra.moveToFront();
        telaCompra.incluir();
        TelaPedidoDeCompra.tela.dispose();
        
            
            }
        
        }
         
        return retorno;
    }

    @Override
    public boolean excluirBD() {
        setPersistencia();
        return daoPedidoDeCompra.excluir();
    }

   
   @Override
   public void consultarBD(int pk) {
       super.consultarBD(pk);
       pedidodecompra.setId(pk);
       daoPedidoDeCompra.consultar();
       getPersistencia();
   }
   

    @Override
    public void consultar() {
       
            super.consultar();
            new TelaConsulta((parametrosConsultaFornecedor) = new ParametrosConsulta("Consulta de Pedido De Compra",
            DaoPedidoDeCompra.SQL_PESQUISAR,
            new String[] {"Código", "Data", "Valor Líquido", "Status", "Fornecedor", "Cond.Pagamento"},
            new FiltroPesquisa[] {
                      new FiltroPesquisa("CÓDIGO", "ID", Integer.class),
                        new FiltroPesquisa("DATA", "DATA", Date.class),
                        new FiltroPesquisa("VALOR LÍQUIDO", "VALORLIQUIDO", String.class),
                        new FiltroPesquisa("STATUS", "STATUS", String.class),
                        new FiltroPesquisa("FORNECEDOR", "PDC_FORNECEDOR", String.class),
                        new FiltroPesquisa("CONDIÇÃO DE PAGAMENTO", "PDC_CONDICAODEPAGAMENTO", String.class),
            
        },
                                        new DefaultTableCellRenderer[] { 
                                                                        new InteiroRender(), new CellRendererData(), new MonetarioRender(), new RenderizadorTexto(),
                                                                        new RenderizadorTexto(), new RenderizadorTexto()},
                                   this, this, true, false, false)
        );
        
    }   

    @Override
    public void preencherDados(int pk) {
        pedidodecompra.setId(pk);
        daoPedidoDeCompra.consultar();
        getPersistencia();
        super.preencherDados(pk);
    }

    public void adicionaListeners() {
        campoItensMateriaPrima.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent ie) {
                if (ie.getKeyCode() == VK_ENTER) {
                    tabelaItensPedidoDeCompra.getTableItensPedidoDeCompra().alteraMateriaPrima(campoItensMateriaPrima.getValor(), tabelaItensPedidoDeCompra.getLinhaSelecionada());
                    MateriaPrima materiaprima = tabelaItensPedidoDeCompra.getTableItensPedidoDeCompra().getMateriaPrima(tabelaItensPedidoDeCompra.getLinhaSelecionada());
                    campoValorunitarioItens.setValor(materiaprima.getValorunitario()); //*checar 
                    QtdeEstoque.setValor(materiaprima.getQuantidade());
                    
                     atualizaCamposTotal(null);
                }
            }
        });
        
        campoQuantidadeItens.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
            SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                        tabelaItensPedidoDeCompra.getTableItensPedidoDeCompra().alteraQuantidade(campoQuantidadeItens.getValor(), tabelaItensPedidoDeCompra.getLinhaSelecionada());
                        atualizaCamposTotal(null);
                    }
                });
            }
        });
        
        campoValorunitarioItens.addKeyListener(new KeyAdapter() {
        @Override
                      public void keyTyped(KeyEvent e) {
                      SwingUtilities.invokeLater(new Runnable() {
                      @Override
                      public void run() {
                        tabelaItensPedidoDeCompra.getTableItensPedidoDeCompra().alteraValorUnitario(campoValortotalItens.getValor(), tabelaItensPedidoDeCompra.getLinhaSelecionada());
                        atualizaCamposTotal(null);
                    }
                });
             }
       });
                
       campoDescontoItens.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
            SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                        tabelaItensPedidoDeCompra.getTableItensPedidoDeCompra().alteraDesconto(campoDescontoItens.getValor(), tabelaItensPedidoDeCompra.getLinhaSelecionada());
                        atualizaCamposTotal(campoDescontoItens);
                    }
                });
            }
        });
      
        campoDesconto.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        atualizaCamposTotal(campoDesconto);
                    }
                });
            }
        });

        tabelaItensPedidoDeCompra.getTabela().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent lse) {
                if (!lse.getValueIsAdjusting() && tabelaItensPedidoDeCompra.getTabela().getSelectedRow() >= 0) {
                    ItensPedidoDeCompra itensPedidoDeCompra = tabelaItensPedidoDeCompra.getTableItensPedidoDeCompra().getItensPedidoDeCompra(tabelaItensPedidoDeCompra.getTabela().getSelectedRow());
                    
                    campoQuantidadeItens.setValor(itensPedidoDeCompra.getQuantidade());
                    campoValorunitarioItens.setValor(itensPedidoDeCompra.getValorunitario());
                    campoDescontoItens.setValor(itensPedidoDeCompra.getDesconto());
                    campoValortotalItens.setValor(itensPedidoDeCompra.getValortotal());
                }
            }
        });
    }

    public void atualizaCamposTotal(JComponent quemChamou) {
        BigDecimal quantidadeItem = BigDecimal.valueOf(new Double((int) campoQuantidadeItens.getValor()));
        BigDecimal valorUnitarioItem = (BigDecimal) campoValorunitarioItens.getValor();
        BigDecimal descontoItem = (BigDecimal) campoDescontoItens.getValor();
        BigDecimal valorTotalItem = quantidadeItem.multiply(valorUnitarioItem.subtract(descontoItem));
        if (quemChamou != null && valorTotalItem.compareTo(BigDecimal.ZERO) == -1) {
            JOptionPane.showMessageDialog(null, "Valor negativo, verifique!!!");
            quemChamou.requestFocus();
        } else {
            campoValortotalItens.setValor(valorTotalItem);
            tabelaItensPedidoDeCompra.getTableItensPedidoDeCompra().alteraValorUnitario(campoValorunitarioItens.getValor(), tabelaItensPedidoDeCompra.getLinhaSelecionada());
            tabelaItensPedidoDeCompra.getTableItensPedidoDeCompra().alteraValorTotal(campoValortotalItens.getValor(), tabelaItensPedidoDeCompra.getLinhaSelecionada());
        }
        
        List<ItensPedidoDeCompra> itens = tabelaItensPedidoDeCompra.getTableItensPedidoDeCompra().getDados();
        BigDecimal valorTotal = BigDecimal.ZERO;
        for (int i = 0; i < itens.size(); i++) {
            valorTotal = valorTotal.add(itens.get(i).getValortotal());
        }
        campoValortotal.setValor(valorTotal);
        campoValorliquido.setValor(valorTotal.subtract((BigDecimal) campoDesconto.getValor()));
    }
    
    @Override
    public boolean verificarCampos() {

        if (!super.verificarCampos()) {
           return false;
        }
        List<ItensPedidoDeCompra> itens = ((TableItensPedidoDeCompra) tabelaItensPedidoDeCompra.getTabela().getModel()).getDados();
        for (int i = 0; i < itens.size(); i++) {
            if (itens.get(i).getMateriaprima().getId() == 0) {
                JOptionPane.showMessageDialog(null,
                        "Exite(m) matéria-prima(s) não selecionado(s).");                
                return false;
            }
            if (itens.get(i).getQuantidade() <= 0) {
                JOptionPane.showMessageDialog(null,
                        "Existe(m) matéria-prima(s) com quantidade inválida(s).");
                return false;
            }
            if (itens.get(i).getValorunitario().compareTo(BigDecimal.ZERO) <= 0 ) {
                JOptionPane.showMessageDialog(null,
                        "Existe(m) matéria-prima(s) com valor unitário invalido(s).");
                return false;
            }
        }
        return true;
    }
    
    
}
