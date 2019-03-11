
package tela;

import componente.MeuJDesktopPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;



public class TelaSistema extends JFrame implements ActionListener{
    
   
   public static MeuJDesktopPane meuJDesktopPane = new MeuJDesktopPane();

   
   private ImageIcon ICON_PRINCIPAL= new ImageIcon("src/imagens/edu.jpeg"); 
   private Icon iconPais = new ImageIcon("src/imagens/pais.png");
   private Icon iconEstado = new ImageIcon("src/imagens/estado.png");
   private Icon iconCidade = new ImageIcon("src/imagens/cidade.png");
   private Icon iconCliente = new ImageIcon("src/imagens/cliente.png");
   private Icon iconFornecedor = new ImageIcon("src/imagens/fornecedor.png");
   private Icon iconMateriaprima = new ImageIcon("src/imagens/materiaprima.png");
   private Icon iconModelo = new ImageIcon("src/imagens/modelo.png");
   private Icon iconCor = new ImageIcon("src/imagens/cor.png");
   private Icon iconTamanho = new ImageIcon("src/imagens/tamanho.png");
   private Icon iconProdutoacabado = new ImageIcon("src/imagens/produtoacabado.png");
   private Icon iconProducao = new ImageIcon("src/imagens/producao.png");
   private Icon iconCondicaodepagamento = new ImageIcon("src/imagens/condicaodepagamento.png");
   private Icon iconPedidodecompra = new ImageIcon("src/imagens/pedidodecompra.png");
   private Icon iconCompra = new ImageIcon("src/imagens/compra.png");
   private Icon iconContaPagar = new ImageIcon("src/imagens/contapagar.png");
   private Icon iconPagamento = new ImageIcon("src/imagens/pagamento.png");
   private Icon iconCaixa = new ImageIcon("src/imagens/caixa.png");
   
   private Icon iconCadastro = new ImageIcon("src/imagens/cadastros.png");
   private Icon iconSair = new ImageIcon("src/imagens/sair.png");
   private Icon iconMovimentacoes = new ImageIcon("src/imagens/movimentacoes.png");
    
   private JMenuBar jmb = new JMenuBar();
   private JMenu jmCadastros = new JMenu("Cadastros");
   private JMenu jmMovimentacoes = new JMenu("Movimentações");
   
    
    
    JMenu Sair = new JMenu("Sair");
    
    //Cadastros
    private JMenuItem jmiPais = new JMenuItem("País", iconPais);
    private JMenuItem jmiEstado = new JMenuItem("Estado", iconEstado);
    private JMenuItem jmiCidade = new JMenuItem("Cidade", iconCidade);
    private JMenuItem jmiCliente = new JMenuItem("Cliente", iconCliente);
    private JMenuItem jmiFornecedor = new JMenuItem("Fornecedor", iconFornecedor);
    private JMenuItem jmiMateriaprima = new JMenuItem("Matéria-Prima",iconMateriaprima);
   
    private JMenuItem jmiModelo = new JMenuItem("Modelo",iconModelo);
    private JMenuItem jmiCor = new JMenuItem("Cor",iconCor);
    private JMenuItem jmiTamanho = new JMenuItem("Tamanho",iconTamanho);
    private JMenuItem jmiProdutoacabado = new JMenuItem("Produto Acabado",iconProdutoacabado);
    private JMenuItem jmiProducao = new JMenuItem("Produção",iconProducao);
    private JMenuItem jmiCondicaodepagamento = new JMenuItem("Condição de Pagamento",iconCondicaodepagamento);
    
    //Movimentações
   


    private JMenuItem jmiPedidodecompra = new JMenuItem("Pedido de Compra",iconPedidodecompra);
    private JMenuItem jmiCompra = new JMenuItem("Compra",iconCompra);
    private JMenuItem jmiContapagar = new JMenuItem("Conta Pagar",iconContaPagar);

    
    
     
    public TelaSistema() {
        
        setTitle("Sistema Malharia");
       
        getContentPane().add(meuJDesktopPane);
        
        setIconImage(ICON_PRINCIPAL.getImage());
      
        
        jmb.add(jmCadastros);
        jmCadastros.setIcon(iconCadastro);
        jmb.add(jmMovimentacoes);
        jmMovimentacoes.setIcon(iconMovimentacoes);
        jmb.add(Sair);
        Sair.setLocale(Locale.ENGLISH);
        Sair.setIcon(iconSair);
        
        AdicionaListener();
    
        adicionaItensMenu();
        setExtendedState(MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setJMenuBar(jmb);
        
        setVisible(true);
    }
    
    public void AdicionaListener(){
        
        Sair.addMenuListener(new MenuListener() {
        @Override
        public void menuSelected(MenuEvent e) {
            if (JOptionPane.showConfirmDialog(null, "Deseja realmente Sair do Sistema?", "",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    System.exit(0);
            }
        }

        @Override
        public void menuDeselected(MenuEvent e) {
             return;
        }

        @Override
        public void menuCanceled(MenuEvent e) {
            return;
        }
        
        });
        }
    
    public static void centraliza(JInternalFrame janela){  
       
        int larguraDesk = meuJDesktopPane.getWidth();    
        int alturaDesk = meuJDesktopPane.getHeight();    
        int larguraIFrame = janela.getWidth();    
        int alturaIFrame = janela.getHeight();    
  
        janela.setLocation(larguraDesk / 2 - larguraIFrame / 2, alturaDesk / 2 - alturaIFrame / 2);  
    } 
    
    public void adicionaItensMenu()  {
        adicionaItemNoMenu(jmCadastros, jmiPais);
        adicionaItemNoMenu(jmCadastros, jmiEstado);
        adicionaItemNoMenu(jmCadastros, jmiCidade);
        adicionaItemNoMenu(jmCadastros, jmiCliente);
        adicionaItemNoMenu(jmCadastros, jmiFornecedor);
        adicionaItemNoMenu(jmCadastros, jmiMateriaprima);

        adicionaItemNoMenu(jmCadastros, jmiModelo);
        adicionaItemNoMenu(jmCadastros, jmiCor);
        adicionaItemNoMenu(jmCadastros, jmiTamanho);
        adicionaItemNoMenu(jmCadastros, jmiProdutoacabado);
        adicionaItemNoMenu(jmCadastros, jmiProducao);
        adicionaItemNoMenu(jmCadastros, jmiCondicaodepagamento);

        adicionaItemNoMenu(jmMovimentacoes, jmiPedidodecompra);
        adicionaItemNoMenu(jmMovimentacoes, jmiCompra);
        adicionaItemNoMenu(jmMovimentacoes, jmiContapagar);

        
        
        
    }
    
   public void adicionaItemNoMenu(JMenu menu, JMenuItem menuItem) {
        menu.add(menuItem);
        menuItem.addActionListener(this);        
    }
  

    @Override
    public void actionPerformed(ActionEvent ae) {
       
        if(ae.getSource() == jmiPais) {
            TelaCadastroPais.getTela();
           centraliza(TelaCadastroPais.getTela());
        } else if (ae.getSource() == jmiEstado) {
            TelaCadastroEstado.getTela();
             
        } else if (ae.getSource() == jmiCidade) {
            TelaCadastroCidade.getTela();
        
        } else if (ae.getSource() == jmiCliente) {
            TelaCadastroCliente.getTela();
        
        } else if (ae.getSource() == jmiFornecedor) {
            TelaCadastroFornecedor.getTela();
        
        } else if (ae.getSource() == jmiMateriaprima) {
             TelaCadastroMateriaPrima.getTela(); 
             centraliza(TelaCadastroMateriaPrima.getTela());

        
        } else if (ae.getSource() == jmiModelo) {
             TelaCadastroModelo.getTela();
        
        } else if (ae.getSource() == jmiCor) {
             TelaCadastroCor.getTela();
        
        } else if (ae.getSource() == jmiTamanho) {
             TelaCadastroTamanho.getTela();
        
        } else if (ae.getSource() == jmiProdutoacabado) {
             TelaCadastroProdutoAcabado.getTela();
        
        } else if (ae.getSource() == jmiProducao) {
             TelaCadastroProducao.getTela();
        
        } else if (ae.getSource() == jmiCondicaodepagamento) {
            TelaCadastroCondicaoDePagamento.getTela();
        
        } else if (ae.getSource() == jmiPedidodecompra) {
             TelaPedidoDeCompra.getTela();
       
        }  else if (ae.getSource() == jmiCompra) {
            TelaCompra.getTela();
            
        
        } else if (ae.getSource() == jmiContapagar) {
            TelaContaPagar.getTela();

        }
    
    
       }
    
        
    }
    
    

