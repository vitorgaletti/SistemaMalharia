package tela;

import componente.MeuCampoBuscar;
import componente.MeuCampoComboBox;
import componente.MeuCampoData;
import componente.MeuCampoInteiro;
import componente.MeuCampoTexto;
import dao.DaoProducao;
import dao.DaoMateriaPrima;
import dao.DaoProdutoAcabado;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import static java.awt.event.KeyEvent.VK_ENTER;
import java.util.Date;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import pojo.Producao;
import pojo.ItensProducao;
import pojo.MateriaPrima;
import pojo.ParametrosConsulta;
import rendererizador.CellRendererData;
import rendererizador.InteiroRender;
import rendererizador.MonetarioRender;
import rendererizador.RenderizadorQtde;
import rendererizador.RenderizadorStatus;
import rendererizador.RenderizadorTexto;
import tabela.TabelaProducao;
import tablemodel.TableProducao;
import util.FiltroPesquisa;


public class TelaCadastroProducao extends TelaCadastro {
    
    public static TelaCadastroProducao tela;
    public Producao producao = new Producao();
    public DaoProducao daoProducao = new DaoProducao(producao);
    public ItensProducao itemProducao = new ItensProducao();
    
    public ParametrosConsulta parametrosConsultaProduoAcabado =
            new ParametrosConsulta("Consulta de Produto Acabado",
                                   DaoProdutoAcabado.SQL_PESQUISAR_ATIVOS,
                                   new String[] {"Código", "Descrição", "Quantidade", "Valor", "Ativo", "Modelo" , "Cor", "Tamanho"},
                                   new FiltroPesquisa[] {
                                                         new FiltroPesquisa("CÓDIGO", "ID", Integer.class),
                                                         new FiltroPesquisa("DESCRICAO", "PRODUTOACABADO_DESCRICAO", String.class),
                                                         new FiltroPesquisa("MODELO", "PRODUTOACABADO_MODELO", String.class),
                                                         new FiltroPesquisa("COR", "PRODUTOACABADO_COR", String.class),
                                                         new FiltroPesquisa("TAMANHO", "PRODUTOACABADO_TAMANHO", String.class),
            
        },
                                        new DefaultTableCellRenderer[] { 
                                                                        new InteiroRender(), new RenderizadorTexto(),
                                                                        new RenderizadorQtde(), new MonetarioRender(), new RenderizadorTexto(), new RenderizadorTexto(),new RenderizadorTexto(),new RenderizadorTexto(),},
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
    public MeuCampoData campoPrevisao = new MeuCampoData(10, true, "Previsão");
    public MeuCampoTexto campoDescricao= new MeuCampoTexto(20, 50, false, "Descrição");
    public  MeuCampoInteiro campoQuantidade = new MeuCampoInteiro(5,true,true,"Quantidade á ser Produzida");
    public MeuCampoComboBox campoStatus = new MeuCampoComboBox(true, true, new String[][] {{"A","Em Andamento"}, {"F", "Finalizado"}},  "Status");
    public MeuCampoBuscar campoProdutoAcabado = new MeuCampoBuscar(TelaConsulta.class, TelaCadastroProdutoAcabado.class, DaoProdutoAcabado.SQL_COMBOBOX, parametrosConsultaProduoAcabado, true, true, "Produto Acabado");
    
    public MeuCampoBuscar campoItensMateriaPrima = new MeuCampoBuscar(TelaConsulta.class, TelaCadastroMateriaPrima.class, DaoMateriaPrima.SQL_COMBOBOX, parametrosConsultaItensMateriaPrima, true, true, "Matéria-Prima");
    public static final MeuCampoInteiro campoQuantidadeItem = new MeuCampoInteiro(5, true, true,"Quantidade de Item");
    public static final MeuCampoInteiro QtdEstoque = new MeuCampoInteiro(5, false, false, "Quantidade Disponível no Estoque");
    
    public JLabel frase = new JLabel("<html>Campo Obrigatório <font color=red>*</font><body>");
    
    public static TabelaProducao tabelaItem = new TabelaProducao();
    
      public static TelaCadastro getTela() {  
        if (tela == null) {   
            tela = new TelaCadastroProducao();
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

    public TelaCadastroProducao() {
        super("Cadastro de Produção");
        adicionarComponente(1, 1, 1, 1, campoCodigo,ESQUERDA);
        adicionarComponente(2, 1, 1, 1, campoData,ESQUERDA);
        adicionarComponente(5, 1, 1, 1, campoProdutoAcabado,ESQUERDA);
        adicionarComponente(4, 1, 1, 1, campoDescricao,ESQUERDA);
        adicionarComponente(3, 1, 1, 1, campoPrevisao,ESQUERDA);
        adicionarComponente(6, 1, 1, 1, campoQuantidade,ESQUERDA);
        adicionarComponente(7, 1, 1, 1, campoStatus,ESQUERDA);
        adicionarComponente(9, 1, 2, 2, tabelaItem,ESQUERDA);
        adicionarComponente(5, 3, 1, 1, campoItensMateriaPrima,ESQUERDA);
        adicionarComponente(6, 3, 1, 1, QtdEstoque,ESQUERDA);
        adicionarComponente(7, 3, 1, 1, campoQuantidadeItem,ESQUERDA);
        adicionarComponente(10, 2, 1, 1, frase,ESQUERDA);
    
        habilitarCampos(false);
        pack();
        adicionaListeners();
    }

    public void setPersistencia() {
      
        producao.setId((int) campoCodigo.getValor());
        producao.setData((Date) campoData.getValorDate());
        producao.setDescricao((String) campoDescricao.getValor());
        producao.setPrevisao((Date) campoPrevisao.getValor());
        producao.setQuantidade((int) campoQuantidade.getValor());
        producao.setStatus((String) campoStatus.getValor());
        producao.getProdutoacabado().setId((int) campoProdutoAcabado.getValor());
        producao.setItensProducao(((TableProducao)tabelaItem.getTabela().getModel()).getDados());
        
        
    }

    public void getPersistencia() {
          
        campoCodigo.setValor(producao.getId());
        campoData.setValor(producao.getData());
        campoDescricao.setValor(producao.getDescricao());
        campoPrevisao.setValor(producao.getPrevisao());
        campoQuantidade.setValor(producao.getQuantidade());
        campoStatus.setValor(producao.getStatus());
        campoProdutoAcabado.setValor(producao.getProdutoacabado().getId());
        ((TableProducao) tabelaItem.getTabela().getModel()).setDados(producao.getItensProducao());
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
        return daoProducao.incluir();
    }

    @Override
    public boolean alterarBD() {
        setPersistencia();
        return daoProducao.alterar();
    }

    @Override
    public boolean excluirBD() {
        setPersistencia();
        return daoProducao.excluir();
    }

    @Override
    public void consultarBD(int pk) {
       super.consultarBD(pk);
       producao.setId(pk);
       daoProducao.consultar();
       getPersistencia();
   }
   
    @Override
    public void consultar() {
        super.consultar();
        new TelaConsulta(parametrosConsultaProduoAcabado = new ParametrosConsulta("Consulta de Produção",
                                                    DaoProducao.SQL_PESQUISAR, 
                                                    new String[]{"Código", "Data", "Previsão", "Produto Acabado", "Quantidade", "Status"},
                                                    new FiltroPesquisa[] { new FiltroPesquisa("CÓDIGO", "ID", String.class),
                                                                           new FiltroPesquisa("DATA", "DATA", Date.class),
                                                                           new FiltroPesquisa("PREVISÃO", "PREVISAO", String.class),
                                                                           new FiltroPesquisa("PRODUTO ACABADO", "PRODUCAO_PRODUTOACABADO", String.class),
                                                                           new FiltroPesquisa("STATUS", "STATUS", String.class),
                                                    },
                                                    new DefaultTableCellRenderer[] { 
                                                                                     new InteiroRender(), new CellRendererData(), new CellRendererData(), new RenderizadorTexto(),
                                                                                     new RenderizadorQtde(), new RenderizadorStatus()},
                                                    this, this, true, false, false));
    }

    @Override
    public void preencherDados(int pk) {
        producao.setId(pk);
        daoProducao.consultar();
        getPersistencia();
        super.preencherDados(pk);
    }
    
    public void adicionaListeners() {
            campoItensMateriaPrima.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent ie) {
                if (ie.getKeyCode() == VK_ENTER) {
                    tabelaItem.getTableModel().alteraMateriaPrima(campoItensMateriaPrima.getValor(), tabelaItem.getLinhaSelecionada());
                    MateriaPrima materiaprima = tabelaItem.getTableModel().getMateriaPrima(tabelaItem.getLinhaSelecionada());
                    QtdEstoque.setValor(materiaprima.getQuantidade());
                   
                }
            }
        });
         
            campoQuantidadeItem.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                  SwingUtilities.invokeLater(new Runnable() {
                      @Override
                      public void run() {
                         tabelaItem.getTableModel().alteraQuantidade(campoQuantidadeItem.getValor(), tabelaItem.getLinhaSelecionada()); 
                      }
                  });
            }
            });
        
            tabelaItem.getTabela().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent lse) {
                if (!lse.getValueIsAdjusting() && tabelaItem.getTabela().getSelectedRow() >= 0) {
                    
                    ItensProducao itensProducao = tabelaItem.getTableModel().getItensProducao(tabelaItem.getTabela().getSelectedRow());
                    campoQuantidadeItem.setValor(itensProducao.getQuantidade());
                    
                }
            }
        });
    }
       
       
    @Override
    public boolean verificarCampos() {

        if (!super.verificarCampos()) {
           return false;
        }
        List<ItensProducao> itens = ((TableProducao) tabelaItem.getTabela().getModel()).getDados();
        for (int i = 0; i < itens.size(); i++) {
            if (itens.get(i).getMateriaprima().getId() == 0) {
                JOptionPane.showMessageDialog(null,
                        "Exite(m) Matéria-Prima(s) não selecionado(s).");                
                return false;
            }
            if (itens.get(i).getQuantidade() <= 0) {
                JOptionPane.showMessageDialog(null,
                        "Existe(m) Matéria-Prima(s) com quantidade inválida(s).");
                return false;
            }
            
        }
        return true;
    }

      
}