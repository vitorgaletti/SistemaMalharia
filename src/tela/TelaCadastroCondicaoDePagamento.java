
package tela;

            
import componente.MeuCampoFormatado;
import componente.MeuCampoInteiro;
import dao.DaoCondicaoDePagamento;
import javax.swing.JLabel;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.table.DefaultTableCellRenderer;
import pojo.CondicaoDePagamento;
import pojo.ParametrosConsulta;
import rendererizador.InteiroRender;
import rendererizador.RenderizadorTexto;
import util.FiltroPesquisa;



public class TelaCadastroCondicaoDePagamento extends TelaCadastro{
    
    public static TelaCadastroCondicaoDePagamento tela;
    public CondicaoDePagamento condicaodepagamento = new CondicaoDePagamento();
    public DaoCondicaoDePagamento daoCondicaoDePagamento = new DaoCondicaoDePagamento(condicaodepagamento);
    public ParametrosConsulta parametrosConsulta;
    public MeuCampoInteiro campoCodigo = new MeuCampoInteiro(5, true, false, "Código");
    public MeuCampoFormatado campoDescricao = new MeuCampoFormatado(20, 50, true, "Descrição" );
    public MeuCampoInteiro campoCarencia = new MeuCampoInteiro(5,true,true,"Carência");
    public MeuCampoInteiro campoNparcelas = new MeuCampoInteiro(5,true,true,"Número de Parcelas");
    public MeuCampoInteiro campoPrazoEntreParcelas = new MeuCampoInteiro(5,true,true,"Prazo entre Parcelas");
    
    public JLabel frase = new JLabel("<html>Campo Obrigatório <font color=red>*</font><body>");
    
    public static TelaCadastro getTela() {  
        if (tela == null) {   
            tela = new TelaCadastroCondicaoDePagamento();
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
    
   
    
    public TelaCadastroCondicaoDePagamento() {
        
        super("Cadastro de Condição de Pagamento ");
        adicionarComponente(1, 1, 1, 1, campoCodigo,ESQUERDA);
        adicionarComponente(2, 1, 1, 1, campoDescricao,ESQUERDA);
        adicionarComponente(3, 1, 1, 1, campoCarencia,ESQUERDA);
        adicionarComponente(4, 1, 1, 1, campoNparcelas,ESQUERDA);
        adicionarComponente(5, 1, 1, 1, campoPrazoEntreParcelas,ESQUERDA);
        adicionarComponente(6, 2, 1, 1, frase,ESQUERDA);
        habilitarCampos(false);
        pack();
        
    }
    
    public void setPersistencia() {
      condicaodepagamento.setId((int) campoCodigo.getValor());
      condicaodepagamento.setDescricao((String) campoDescricao.getValor());
      condicaodepagamento.setCarencia((int) campoCarencia.getValor());
      condicaodepagamento.setNparcelas((int) campoNparcelas.getValor());
      condicaodepagamento.setPrazoentreparcelas((int) campoPrazoEntreParcelas.getValor());
     
             
   }
   
    public void getPersistencia() {
      campoCodigo.setValor(condicaodepagamento.getId());
      campoDescricao.setValor(condicaodepagamento.getDescricao());
      campoCarencia.setValor(condicaodepagamento.getCarencia());  
      campoNparcelas.setValor(condicaodepagamento.getNparcelas());  
      campoPrazoEntreParcelas.setValor(condicaodepagamento.getPrazoentreparcelas());  
     
   }
   
    @Override
    public boolean incluirBD() {
      setPersistencia();
      getPersistencia(); 
      return daoCondicaoDePagamento.incluir();
   }
   
    @Override
    public boolean alterarBD() {
      setPersistencia();
      return daoCondicaoDePagamento.alterar();       
   }
   
    @Override
    public boolean excluirBD() {
      setPersistencia();
      return daoCondicaoDePagamento.excluir();       
    }


    @Override
    public void consultarBD(int pk) {
       super.consultarBD(pk);
       condicaodepagamento.setId(pk);
       daoCondicaoDePagamento.consultar();
       getPersistencia();
   }
  
    @Override
    public void consultar() {
        super.consultar();
        new TelaConsulta(parametrosConsulta = new ParametrosConsulta("Consulta de Condição de Pagamento",
                               DaoCondicaoDePagamento.SQL_PESQUISAR,
                               new String[] {"Código", "Descrição"},
                               new FiltroPesquisa[] {new FiltroPesquisa("CÓDIGO", "ID", Integer.class),
                                                     new FiltroPesquisa("DESCRIÇÃO", "DESCRICAO", String.class)
            
                                },
                                new DefaultTableCellRenderer[] { 
                                                                new InteiroRender(), new RenderizadorTexto(), new InteiroRender()},
                                this, this, false, false, false)
        );
    }
    @Override
    public void preencherDados(int pk) {
      condicaodepagamento.setId(pk);
      daoCondicaoDePagamento.consultar();
      getPersistencia();
      super.preencherDados(pk);
   }
   
    
}
