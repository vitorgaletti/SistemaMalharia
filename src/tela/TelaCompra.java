package tela;

import componente.MeuCampoBuscar;
import componente.MeuCampoCheckBox;
import componente.MeuCampoComboBox;
import componente.MeuCampoData;
import componente.MeuCampoInteiro;
import componente.MeuCampoMonetario;
import dao.DaoCondicaoDePagamento;
import dao.DaoFornecedor;
import dao.DaoMateriaPrima;
import dao.DaoCompra;
import dao.DaoPedidoDeCompra;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import static java.awt.event.KeyEvent.VK_ENTER;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
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
import pojo.ItensCompra;
import pojo.MateriaPrima;
import pojo.Compra;
import pojo.ContaPagar;
import pojo.ParametrosConsulta;
import pojo.PedidoDeCompra;
import rendererizador.CellRendererData;
import rendererizador.InteiroRender;
import rendererizador.MonetarioRender;
import rendererizador.RenderizadorQtde;
import rendererizador.RenderizadorStatus;
import rendererizador.RenderizadorTexto;
import tabela.TabelaItensCompra;
import tablemodel.TableItensCompra;
import util.FiltroPesquisa;



public class TelaCompra extends TelaCadastro {
    
    public static TelaCompra tela;
    private JPanel painel = new JPanel();
    private JPanel painel1 = new JPanel();
    public Compra compra = new Compra();
    public DaoCompra daoCompra = new DaoCompra(compra);
    public ItensCompra itensCompra = new ItensCompra();
    public static JButton botaoLote = new JButton ("Cadastro de Lote");
   
    public ParametrosConsulta parametrosConsulta =
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
        
        public  ParametrosConsulta parametrosConsultaCondicaoDePagamento =
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
            new ParametrosConsulta("Consulta de Matéria-Prima",
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
        
        public ParametrosConsulta parametrosConsultaPedidoDeCompra =
                                  new ParametrosConsulta("Consulta de Pedido de Compra",
                                  DaoPedidoDeCompra.SQL_PESQUISAR_ATIVOS,
                                  new String[] {"Código", "Data", "Valor Líquido", "Status", "Fornecedor", "Cond.Pagamento"},
                                  new FiltroPesquisa[] {
                                                         new FiltroPesquisa("CÓDIGO", "ID", Integer.class),
                                                         new FiltroPesquisa("DATA", "DATA", String.class),
                                                         new FiltroPesquisa("VALOR LÍQUIDO", "VALORLIQUIDO", String.class),
                                                         new FiltroPesquisa("STATUS", "STATUS", String.class),
                                                         new FiltroPesquisa("FORNECEDOR", "PDC_FORNECEDOR", String.class),
                                                         new FiltroPesquisa("CONDIÇÃO DE PAGAMENTO", "PDC_CONDICAODEPAGAMENTO", String.class),
            
        },
                                        new DefaultTableCellRenderer[] { 
                                                                        new InteiroRender(), new CellRendererData(), new MonetarioRender(), new RenderizadorTexto(),
                                                                        new RenderizadorTexto(), new RenderizadorTexto()},
                                   this, this, true, false, false
        );
    
    public MeuCampoInteiro campoCodigo = new MeuCampoInteiro(5, true, false, "Código");
    public MeuCampoData campoData = new MeuCampoData(10, true, "Data");
    public MeuCampoMonetario campoValortotal = new MeuCampoMonetario(15, false, false, true, "Valor Total");
    public MeuCampoMonetario campoDesconto  = new MeuCampoMonetario(15, false, true, true, "Valor Desconto");
    public MeuCampoMonetario campoValorliquido = new MeuCampoMonetario(15, false, false, true, "Valor Líquido");
    public MeuCampoComboBox campoStatus = new MeuCampoComboBox(true, true, new String[][] {{"CA", "Compra Aberta"}, {"CF", "Compra Fechada"}}, "Status");
    public MeuCampoCheckBox campoContaPagar = new MeuCampoCheckBox(false,false,"Já gerou Conta Pagar ?");
    public MeuCampoBuscar campoFornecedor = new MeuCampoBuscar(TelaConsulta.class, TelaCadastroFornecedor.class, DaoFornecedor.SQL_COMBOBOX, parametrosConsulta, true, true, "Fornecedor");
    public  MeuCampoBuscar campoCondicaoDePagamento = new MeuCampoBuscar(TelaConsulta.class, TelaCadastroCondicaoDePagamento.class, DaoCondicaoDePagamento.SQL_COMBOBOX, parametrosConsultaCondicaoDePagamento, true, true, "Condição de Pagamento");
    public MeuCampoBuscar campoPedidoDeCompra = new MeuCampoBuscar(TelaConsulta.class, TelaPedidoDeCompra.class, DaoPedidoDeCompra.SQL_COMBOBOX, parametrosConsultaPedidoDeCompra, false, true, "Pedido de Compra");
     
    public MeuCampoBuscar campoItensMateriaPrima = new MeuCampoBuscar(TelaConsulta.class, TelaCadastroMateriaPrima.class, DaoMateriaPrima.SQL_COMBOBOX, parametrosConsultaItensMateriaPrima, false, true, "Matéria-Prima");
    public MeuCampoInteiro campoQuantidadeItens = new MeuCampoInteiro(5, true, true, "Quantidade");
    public MeuCampoMonetario campoValorunitarioItens = new MeuCampoMonetario(15, false, false, true, "Valor unitário");
    public MeuCampoMonetario campoDescontoItens = new MeuCampoMonetario(15, false, true, true, "Desconto");
    public MeuCampoMonetario campoValortotalItens = new MeuCampoMonetario(15, false, false, true, "Valor total");
    public MeuCampoInteiro QtdeEstoque = new MeuCampoInteiro(5, false, false, "Quantidade Disponível no Estoque");
    
    public JLabel frase = new JLabel("<html>Campo Obrigatório <font color=red>*</font><body>");
    
    public static TabelaItensCompra tabelaItensCompra = new TabelaItensCompra();
 
    
        public static void  getTela() {  
        if (tela == null) {   
            tela = new TelaCompra();
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
       
    }
        
    
    public TelaCompra() {
         
        super("Movimento de  Compra ");
        
         adicionarComponente(1, 1, 1, 1, campoCodigo,ESQUERDA);
         adicionarComponente(2, 1, 1, 1, campoData,ESQUERDA);
         adicionarComponente(5, 1, 1, 1, campoFornecedor,ESQUERDA);
         adicionarComponente(7, 1, 1, 1, campoValortotal,ESQUERDA);
         adicionarComponente(8, 1, 1, 1, campoDesconto,ESQUERDA);
         adicionarComponente(9, 1, 1, 1, campoValorliquido,ESQUERDA);
         adicionarComponente(11, 1, 1, 1, campoStatus,ESQUERDA);
         adicionarComponente(12, 1, 1, 1, campoContaPagar,ESQUERDA);
         adicionarComponente(10, 1, 1, 1, campoCondicaoDePagamento,ESQUERDA);
         adicionarComponente(4, 1, 1, 1, campoPedidoDeCompra,ESQUERDA);
         adicionarComponente(13, 1, 2, 2, tabelaItensCompra,ESQUERDA);
         adicionarComponente(4, 3, 1, 1, campoItensMateriaPrima,ESQUERDA);
         adicionarComponente(5, 3, 1, 1, QtdeEstoque,ESQUERDA);
         adicionarComponente(7, 3, 1, 1, campoQuantidadeItens,ESQUERDA);
         adicionarComponente(8, 3, 1, 1, campoValorunitarioItens,ESQUERDA);
         adicionarComponente(9, 3, 1, 1, campoDescontoItens,ESQUERDA);
         adicionarComponente(10, 3, 1, 1, campoValortotalItens, ESQUERDA);
         adicionarComponente(4, 7, 1, 1, botaoLote, ESQUERDA);
         adicionarComponente(14, 2, 1, 1, frase, ESQUERDA);
         botaoLote.setEnabled(false);
         botaoLote.setPreferredSize(new Dimension(150, 50));
         botaoLote.setBorder(BorderFactory.createLineBorder(Color.BLUE));
         habilitarCampos(false);
         pack();
         adicionaListeners();
        
    }

    public void setPersistencia() {
        compra.setId((int) campoCodigo.getValor());
        compra.setData((Date) campoData.getValor());
        compra.setValortotal(campoValortotal.getValor());
        compra.setDesconto(campoDesconto.getValor());
        compra.setValorliquido(campoValorliquido.getValor());
        compra.setStatus((String) campoStatus.getValor());
        compra.setCp((boolean) campoContaPagar.getValor());
        compra.getFornecedor().setId((int) campoFornecedor.getValor());
        compra.getCondicaodepagamento().setId((int) campoCondicaoDePagamento.getValor());
        compra.getPedidodecompra().setId((int) campoPedidoDeCompra.getValor());
        compra.setItensCompra(((TableItensCompra)tabelaItensCompra.getTabela().getModel()).getDados());
    }

    public void getPersistencia() {
        campoCodigo.setValor(compra.getId());
        campoData.setValor(compra.getData());
        campoValortotal.setValor(compra.getValortotal());
        campoDesconto.setValor(compra.getDesconto());
        campoValorliquido.setValor(compra.getValorliquido());
        campoStatus.setValor(compra.getStatus());
        campoContaPagar.setValor(compra.isCp());
        campoFornecedor.setValor(compra.getFornecedor().getId());
        campoCondicaoDePagamento.setValor(compra.getCondicaodepagamento().getId());
        campoPedidoDeCompra.setValor(compra.getPedidodecompra().getId());
        ((TableItensCompra) tabelaItensCompra.getTabela().getModel()).setDados(compra.getItensCompra());
        
       
    }

    public void adicionaListeners() {
           
        campoItensMateriaPrima.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent ie) {
                if (ie.getKeyCode()== VK_ENTER) {
                    tabelaItensCompra.getTableItensCompra().alteraMateriaPrima(campoItensMateriaPrima.getValor(), tabelaItensCompra.getLinhaSelecionada());
                    MateriaPrima materiaprima = tabelaItensCompra.getTableItensCompra().getMateriaPrima(tabelaItensCompra.getLinhaSelecionada());
                    campoValorunitarioItens.setValor(materiaprima.getValorunitario()); 
                    QtdeEstoque.setValor(materiaprima.getQuantidade());
                    atualizaCamposTotal(null);
                    DaoMateriaPrima daoMateriaPrima = new DaoMateriaPrima(materiaprima);
                    materiaprima.setId((int) campoItensMateriaPrima.getValor());
                    daoMateriaPrima.consultar();
                    botaoLote.setEnabled(materiaprima.isElote() && campoStatus.getValor() == "CF"); 
                    
                }
                
            }
        });
        
        botaoLote.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
               TelaCadastroLote telaLote = new TelaCadastroLote();
               getDesktopPane().add(telaLote);
               telaLote.moveToFront();
               
              
               
            }
        });
            
        campoPedidoDeCompra.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent ie) {
              if (ie.getKeyCode() == VK_ENTER) {
                  ((TableItensCompra) tabelaItensCompra.getTabela().getModel()).insereItensPedidoDeCompra((int)campoPedidoDeCompra.getValor());
                    PedidoDeCompra pedidoDeCompra = new PedidoDeCompra();
                    DaoPedidoDeCompra daoPedidoDeCompra = new DaoPedidoDeCompra(pedidoDeCompra);
                    pedidoDeCompra.setId((int) campoPedidoDeCompra.getValor());
                    daoPedidoDeCompra.consultar();
                    campoValortotal.setValor(pedidoDeCompra.getValortotal());
                    campoDesconto.setValor(pedidoDeCompra.getDesconto());
                    campoValorliquido.setValor(pedidoDeCompra.getValorliquido());
                    campoFornecedor.setValor(pedidoDeCompra.getFornecedor().getId());
                    campoCondicaoDePagamento.setValor(pedidoDeCompra.getCondicaodepagamento().getId());
                   
                    
              }
               
            }
            
        });

           
        campoQuantidadeItens.addKeyListener(new KeyAdapter() {
        @Override
        public void keyTyped(KeyEvent e) {
                      SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                      tabelaItensCompra.getTableItensCompra().alteraQuantidade(campoQuantidadeItens.getValor(), tabelaItensCompra.getLinhaSelecionada());
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
                tabelaItensCompra.getTableItensCompra().alteraValorUnitario(campoValorunitarioItens.getValor(), tabelaItensCompra.getLinhaSelecionada());
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
                tabelaItensCompra.getTableItensCompra().alteraDesconto(campoDescontoItens.getValor(), tabelaItensCompra.getLinhaSelecionada());
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

        tabelaItensCompra.getTabela().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
        @Override
        public void valueChanged(ListSelectionEvent lse) {
                if (!lse.getValueIsAdjusting() && tabelaItensCompra.getTabela().getSelectedRow() >= 0) {
                    ItensCompra itensCompra = tabelaItensCompra.getTableItensCompra().getItensCompra(tabelaItensCompra.getTabela().getSelectedRow());
                  
                    campoQuantidadeItens.setValor(itensCompra.getQuantidade());
                    campoValorunitarioItens.setValor(itensCompra.getValorunitario());
                    campoDescontoItens.setValor(itensCompra.getDesconto());
                    campoValortotalItens.setValor(itensCompra.getValortotal());
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
            tabelaItensCompra.getTableItensCompra().alteraValorUnitario(campoValorunitarioItens.getValor(), tabelaItensCompra.getLinhaSelecionada());
            tabelaItensCompra.getTableItensCompra().alteraValorTotal(campoValortotalItens.getValor(), tabelaItensCompra.getLinhaSelecionada());
            
        }
        
        List<ItensCompra> itens = tabelaItensCompra.getTableItensCompra().getDados();
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
        List<ItensCompra> itens = ((TableItensCompra) tabelaItensCompra.getTabela().getModel()).getDados();
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
    
    @Override
    public void habilitarBotoes() {
        
        super.habilitarBotoes();
        if (campoStatus != null && campoStatus.getValor().equals("CF") ) {
              
            jbAlterar.setEnabled(false);
            jbExcluir.setEnabled(false);
        }
    } 
    
    @Override
    public boolean incluirBD() {
        botaoLote.setEnabled(false); 
        setPersistencia();
        getPersistencia(); 
         
        boolean retorno = daoCompra.incluir();
         
        if(campoStatus.getValor() == "CF"){
        if (JOptionPane.showConfirmDialog(null, "Deseja inclur uma Conta Pagar Agora ?", "",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
        TelaContaPagar telaContaPagar = new TelaContaPagar();
        getDesktopPane().add(telaContaPagar);
        telaContaPagar.moveToFront();
           
           }
        }
        
       return retorno;
    }
    
    @Override
    public boolean alterarBD() {
        setPersistencia();
        getPersistencia();
        return daoCompra.alterar();
    }
    
    @Override
    public boolean excluirBD() {
        setPersistencia();
        return daoCompra.excluir();
    }
    
    @Override
    public void consultarBD(int pk) {
       compra.setId(pk);
       daoCompra.consultar();
       getPersistencia();
       super.consultarBD(pk);
    }
     
    @Override
    public void incluir() {
        super.incluir();
        campoData.setValor(new Date());
    }
    
    public void cancelar() {
        super.cancelar();
        botaoLote.setEnabled(false);
        tabelaItensCompra.limpar();
    }
    
    public void preencherDados2(){
       
                    if(!campoPedidoDeCompra.getValor().equals(0)){
                    ((TableItensCompra) tabelaItensCompra.getTabela().getModel()).insereItensPedidoDeCompra((int)campoPedidoDeCompra.getValor());
                    PedidoDeCompra pedidoDeCompra = new PedidoDeCompra();
                    DaoPedidoDeCompra daoPedidoDeCompra = new DaoPedidoDeCompra(pedidoDeCompra);
                    pedidoDeCompra.setId((int) campoPedidoDeCompra.getValor());
                    daoPedidoDeCompra.consultar();
                    campoValortotal.setValor(pedidoDeCompra.getValortotal());
                    campoDesconto.setValor(pedidoDeCompra.getDesconto());
                    campoValorliquido.setValor(pedidoDeCompra.getValorliquido());
                    campoFornecedor.setValor(pedidoDeCompra.getFornecedor().getId());
                    campoCondicaoDePagamento.setValor(pedidoDeCompra.getCondicaodepagamento().getId());
                    }
    }
   
    @Override
    public void consultar() {
            super.consultar();
            botaoLote.setEnabled(false);
            new TelaConsulta(parametrosConsultaItensMateriaPrima = new ParametrosConsulta("Consulta de Compra",
            DaoCompra.SQL_PESQUISAR,
            new String[] {"Código", "Data", "Valor Líquido", "Status", "Fornecedor", "Cond.Pagamento", "Gerou.CP"},
            new FiltroPesquisa[] {
                        new FiltroPesquisa("CÓDIGO", "ID", Integer.class),
                        new FiltroPesquisa("DATA", "DATA", Date.class),
                        new FiltroPesquisa("VALOR LÍQUIDO", "VALORLIQUIDO", String.class),
                        new FiltroPesquisa("STATUS", "STATUS", String.class),
                        new FiltroPesquisa("FORNECEDOR", "PDC_FORNECEDOR", String.class),
                        new FiltroPesquisa("CONDIÇÃO DE PAGAMENTO", "PDC_CONDICAODEPAGAMENTO", String.class),
                        new FiltroPesquisa("GEROU CONTA PAGAR", "CP", String.class),
            
                        },
                         new DefaultTableCellRenderer[] { 
                                                        new InteiroRender(), new CellRendererData(), new MonetarioRender(), new RenderizadorStatus(),
                                                        new RenderizadorTexto(), new RenderizadorTexto(), new RenderizadorStatus()},
                         this, this, true, false, true
            ));
        
        
    }  
    
}


