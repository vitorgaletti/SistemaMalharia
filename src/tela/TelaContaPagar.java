package tela;

import componente.MeuCampoBuscar;
import componente.MeuCampoCheckBox;
import componente.MeuCampoData;
import componente.MeuCampoInteiro;
import componente.MeuCampoMonetario;
import componente.MeuCampoTexto;
import dao.DaoCompra;
import dao.DaoCondicaoDePagamento;
import dao.DaoContaPagar;
import dao.DaoFornecedor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import static java.awt.event.KeyEvent.VK_ENTER;
import java.math.BigDecimal;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.table.DefaultTableCellRenderer;
import pojo.Compra;
import pojo.ContaPagar;
import pojo.ParametrosConsulta;
import rendererizador.CellRendererData;
import rendererizador.InteiroRender;
import rendererizador.MonetarioRender;
import rendererizador.RenderizadorStatus;
import rendererizador.RenderizadorTexto;
import tabela.TabelaParcela;
import tablemodel.TableParcela;
import util.FiltroPesquisa;


public class TelaContaPagar extends TelaCadastro {
    
    public static TelaContaPagar tela;
    public ContaPagar contaPagar = new ContaPagar();
    public DaoContaPagar daoContaPagar = new DaoContaPagar(contaPagar);
    
    public ParametrosConsulta parametrosConsultaCompra =
            new ParametrosConsulta("Consulta de Compra",
                                  DaoCompra.SQL_PESQUISAR_ATIVOS, 
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
    
    public MeuCampoInteiro campoCodigo = new MeuCampoInteiro(5, true, false, "Código");
    public MeuCampoData campoData = new MeuCampoData(10, true, "Data");
    public MeuCampoMonetario campoValortotal = new MeuCampoMonetario(15, false, true, true, "Valor Total");
    public MeuCampoMonetario campoDesconto = new MeuCampoMonetario(15, false, true, true, "Desconto");
    public static MeuCampoMonetario campoValorliquido = new MeuCampoMonetario(15, false, false, true, "Valor Liquido");
    public MeuCampoTexto campoDescricao= new MeuCampoTexto(20, 50, false, "Descrição ");
    public MeuCampoCheckBox campoQuitada = new MeuCampoCheckBox(false, true, "Quitada");
    public MeuCampoBuscar campoFornecedor = new MeuCampoBuscar(TelaConsulta.class, TelaCadastroFornecedor.class, DaoFornecedor.SQL_COMBOBOX, parametrosConsultaFornecedor, false, true, "Fornecedor");
    public MeuCampoBuscar campoCondicaodepagamento = new MeuCampoBuscar(TelaConsulta.class, TelaCadastroCondicaoDePagamento.class, DaoCondicaoDePagamento.SQL_COMBOBOX, parametrosConsultaCondicaoDePagamento, true, true, "Condição de Pagamento");
    public MeuCampoBuscar campoCompra = new MeuCampoBuscar(TelaConsulta.class, TelaCompra.class, DaoCompra.SQL_COMBOBOX, parametrosConsultaCompra, false, true, "Compra");
    public MeuCampoMonetario campoValorPendente = new MeuCampoMonetario(15, false, true, true, "Valor Pendente");
    public JLabel frase = new JLabel("<html>Campo Obrigatório <font color=red>*</font><body>");
    public TabelaParcela tabelaParcela = new TabelaParcela();
    
    public JButton botaoGerarParcela = new JButton("Gerar Parcelas");
    
      public static TelaCadastro getTela() {  
        if (tela == null) {   
            tela = new TelaContaPagar();
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

    public TelaContaPagar() {
        super("Movimento de Conta Pagar");
        adicionarComponente(1, 1, 1, 1, campoCodigo,ESQUERDA);
        adicionarComponente(2, 1, 1, 1, campoData,ESQUERDA);
        adicionarComponente(4, 3, 1, 1, campoValortotal,ESQUERDA);
        adicionarComponente(5, 3, 1, 1, campoDesconto,ESQUERDA);
        adicionarComponente(6, 3, 1, 1, campoValorliquido,ESQUERDA);
        adicionarComponente(7, 3, 1, 1, campoValorPendente,ESQUERDA);
        adicionarComponente(5, 1, 1, 1, campoDescricao,ESQUERDA);
        adicionarComponente(10, 1, 1, 1, campoQuitada,ESQUERDA);
        adicionarComponente(3, 1, 1, 1, campoCompra,ESQUERDA);
        adicionarComponente(4, 1, 1, 1, campoFornecedor,ESQUERDA);
        adicionarComponente(6, 1, 1, 1, campoCondicaodepagamento,ESQUERDA);
        adicionarComponente(12, 1, 2, 2, tabelaParcela,ESQUERDA);
        adicionarComponente(7, 2, 1, 1, botaoGerarParcela,ESQUERDA);
        adicionarComponente(13, 2, 1, 1, frase,ESQUERDA);
        botaoGerarParcela.setEnabled(false);
        habilitarCampos(false);
        pack();
        adicionaListeners();
      
      }
    public TelaContaPagar(ContaPagar contaPagar) {
        this();
        this.contaPagar = contaPagar;
        getPersistencia();
    }
    

    public void setPersistencia() {
      
        contaPagar.setId((int) campoCodigo.getValor());
        contaPagar.setData((Date) campoData.getValor());
        contaPagar.setValortotal(campoValortotal.getValor());
        contaPagar.setDesconto(campoDesconto.getValor());
        contaPagar.setValorliquido(campoValorliquido.getValor());
        contaPagar.setValorpendente(campoValorPendente.getValor()); 
        contaPagar.setDescricao((String) campoDescricao.getValor());
        contaPagar.setQuitada((boolean) campoQuitada.getValor());
        contaPagar.getCompra().setId((int) campoCompra.getValor());
        contaPagar.getFornecedor().setId((int) campoFornecedor.getValor());
        contaPagar.getCondicaodepagamento().setId((int) campoCondicaodepagamento.getValor());
        contaPagar.setParcelaContaPagar(((TableParcela)tabelaParcela.getTabela().getModel()).getDados());
        
        
    }

    public void getPersistencia() {
          
        campoCodigo.setValor(contaPagar.getId());
        campoData.setValor(contaPagar.getData());
        campoValortotal.setValor(contaPagar.getValortotal());
        campoDesconto.setValor(contaPagar.getDesconto());
        campoValorliquido.setValor(contaPagar.getValorliquido());
        campoValorPendente.setValor(contaPagar.getValorpendente());
        campoDescricao.setValor(contaPagar.getDescricao());
        campoQuitada.setValor(contaPagar.isQuitada());  
        campoCompra.setValor(contaPagar.getCompra().getId());
        campoFornecedor.setValor(contaPagar.getFornecedor().getId());
        campoCondicaodepagamento.setValor(contaPagar.getCondicaodepagamento().getId());
        ((TableParcela) tabelaParcela.getTabela().getModel()).setDados(contaPagar.getParcelaContaPagar());
    }
    
    
    @Override
    public void incluir() {
        super.incluir();
        botaoGerarParcela.setEnabled(true);
        campoData.setValor(new Date());
        
    }
    
    @Override
    public void alterar(){
        super.alterar();
        botaoGerarParcela.setEnabled(true);
    }

    @Override
    public boolean incluirBD() {
        setPersistencia();
        
        getPersistencia(); 
        return daoContaPagar.incluir();
    }

    @Override
    public boolean alterarBD() {
        setPersistencia();
        return daoContaPagar.alterar();
        
    }

    @Override
    public boolean excluirBD() {
        setPersistencia();
        return daoContaPagar.excluir();
    }
  
    @Override
    public void consultarBD(int pk) {
       super.consultarBD(pk);
       contaPagar.setId(pk);
       daoContaPagar.consultar();
       getPersistencia();
   }
   
    @Override
    public void cancelar(){
       super.cancelar();
       tabelaParcela.limpar();
       botaoGerarParcela.setEnabled(false);
       ((TableParcela) tabelaParcela.getTabela().getModel()).limparDados();
    }
  
    @Override
    public void consultar() {
       
            super.consultar();
            new TelaConsulta((parametrosConsultaCompra) = new ParametrosConsulta("Consulta de Conta Pagar",
            DaoContaPagar.SQL_PESQUISAR,
            new String[] {"Código", "Data", "Descrição", "Valor Líquido", "Quitada", "Fornecedor", "Cond.Pagamento", "Compra"},
            new FiltroPesquisa[] {
                      new FiltroPesquisa("CÓDIGO", "ID", Integer.class),
                        new FiltroPesquisa("DATA", "DATA", String.class),
                        new FiltroPesquisa("DESCRIÇÃO", "CP_DESCRICAO", String.class),
                        new FiltroPesquisa("VALOR LÍQUIDO", "VALORLIQUIDO", String.class),
                        new FiltroPesquisa("QUITADA", "QUITADA", String.class),
                        new FiltroPesquisa("FORNECEDOR", "CP_FORNECEDOR", String.class),
                        new FiltroPesquisa("CONDIÇÃO DE PAGAMENTO", "CP_CONDICAODEPAGAMENTO", String.class),
                        new FiltroPesquisa("COMPRA", "COMPRA", String.class),
                        
            
        },
                                        new DefaultTableCellRenderer[] { 
                                                                        new InteiroRender(), new CellRendererData(), new RenderizadorTexto(), new MonetarioRender(), new RenderizadorStatus(),
                                                                        new RenderizadorTexto(), new RenderizadorTexto(), new InteiroRender()},
                                   this, this, true, false, false)
        );
        
    }  

    @Override
    public void preencherDados(int pk) {
        super.preencherDados(pk);
        contaPagar.setId(pk);
        daoContaPagar.consultar();
        getPersistencia();
    }
    
    public void preencher1() {
                    if(!campoCompra.getValor().equals(0)){
                    Compra compra = new Compra();
                    DaoCompra daoCompra = new DaoCompra(compra);
                    compra.setId((int) campoCompra.getValor());
                    daoCompra.consultar();
                    campoFornecedor.setValor(compra.getFornecedor().getId());
                    campoCondicaodepagamento.setValor(compra.getCondicaodepagamento().getId());
                    campoValortotal.setValor(compra.getValorliquido());
                    campoValorliquido.setValor(compra.getValorliquido());
                    campoValorPendente.setValor(compra.getValorliquido());
                    }
    }
    
    public void calcular(){
        BigDecimal desconto = ((BigDecimal) campoDesconto.getValor());
        BigDecimal valorTotal = ((BigDecimal) campoValortotal.getValor().subtract(desconto));
        campoValorliquido.setValor(valorTotal);
        campoValorPendente.setValor(valorTotal);
         if (valorTotal.compareTo(BigDecimal.ZERO) <= -1) {
           JOptionPane.showMessageDialog(null, "Valor negativo, verifique!!!");
            campoDesconto.requestFocus();
         }
  } 
    
 
    
    public void adicionaListeners() {
          
            botaoGerarParcela.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               BigDecimal valorTotal = (BigDecimal) campoValortotal.getValor(); 
               if(valorTotal.compareTo(BigDecimal.ZERO) == 0){
                    JOptionPane.showMessageDialog(null, "Campo Valor Total Vazio ");
                } else if (campoCondicaodepagamento.getValor().equals(-1)){
                    JOptionPane.showMessageDialog(null, "Selecione uma Condição de Pagamento");
                }  else {
                     ((TableParcela) tabelaParcela.getTabela().getModel()).limparDados();
                     ((TableParcela) tabelaParcela.getTabela().getModel()).gerarParcela((int)campoCondicaodepagamento.getValor());
                            
                }
            }
        });
      
            campoCompra.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent ie) {
                if (ie.getKeyCode() == VK_ENTER) {
                    Compra compra = new Compra();
                    DaoCompra daoCompra = new DaoCompra(compra);
                    compra.setId((int) campoCompra.getValor());
                    daoCompra.consultar();
                    campoFornecedor.setValor(compra.getFornecedor().getId());
                    campoCondicaodepagamento.setValor(compra.getCondicaodepagamento().getId());
                    campoValortotal.setValor(compra.getValorliquido());
                    campoValorliquido.setValor(compra.getValorliquido());
                    campoValorPendente.setValor(compra.getValorliquido());
                                    
                  
                    }
                 
                 }
        
            });
            
            
            campoDesconto.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        calcular();
                    }
                });
            }
        });
            
            campoValortotal.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        calcular();
                    }
                });
            }
        });
        
  }
     
}

