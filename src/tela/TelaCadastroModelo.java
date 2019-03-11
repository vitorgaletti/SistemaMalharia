
package tela;

import componente.MeuCampoCheckBox;
import componente.MeuCampoInteiro;
import componente.MeuCampoTexto;
import dao.DaoModelo;
import javax.swing.JLabel;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.table.DefaultTableCellRenderer;
import pojo.Modelo;
import pojo.ParametrosConsulta;
import rendererizador.InteiroRender;
import rendererizador.RenderizadorTexto;
import util.FiltroPesquisa;


public class TelaCadastroModelo extends TelaCadastro{
    
    public static TelaCadastroModelo tela;
    public Modelo modelo = new Modelo();
    public DaoModelo daoModelo = new DaoModelo(modelo);
    public ParametrosConsulta parametrosConsulta;
    public MeuCampoInteiro campoCodigo = new MeuCampoInteiro(5, true, false, "Código");
    public MeuCampoTexto campoNome = new MeuCampoTexto(20, 50, true, "Nome" );
    public MeuCampoCheckBox campoAtivo = new MeuCampoCheckBox(true, true, "Ativo");
    
    public JLabel frase = new JLabel("<html>Campo Obrigatório <font color=red>*</font><body>");
    
      public static TelaCadastro getTela() {  
        if (tela == null) {   
            tela = new TelaCadastroModelo();
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
    
    
    public TelaCadastroModelo() {
        
        super("Cadastro de Modelo");
        adicionarComponente(1, 1, 1, 1, campoCodigo,ESQUERDA);
        adicionarComponente(2, 1, 1, 1, campoNome,ESQUERDA);
        adicionarComponente(3, 1, 1, 1, campoAtivo,ESQUERDA);
        adicionarComponente(4, 2, 1, 1, frase,ESQUERDA);
        habilitarCampos(false);
        pack();
        
    }
    
    public void setPersistencia() {
      modelo.setId((int) campoCodigo.getValor());
      modelo.setNome((String) campoNome.getValor());
      modelo.setAtivo((boolean) campoAtivo.getValor());
             
    }
   
    public void getPersistencia() {
      campoCodigo.setValor(modelo.getId());
      campoNome.setValor(modelo.getNome());
      campoAtivo.setValor(modelo.isAtivo());  
     
    }
   
    @Override
    public boolean incluirBD() {
      setPersistencia();
      getPersistencia(); 
      return daoModelo.incluir();
   }
   
    @Override
    public boolean alterarBD() {
      setPersistencia();
      return daoModelo.alterar();       
    }
   
    @Override
    public boolean excluirBD() {
      setPersistencia();
      return daoModelo.excluir();       
   }

    @Override
    public void consultarBD(int pk) {
       super.consultarBD(pk);
       modelo.setId(pk);
       daoModelo.consultar();
       getPersistencia();
   }
   
    
    @Override
    public void consultar() {
        super.consultar();
        new TelaConsulta(parametrosConsulta = new ParametrosConsulta("Consulta de Modelo",
                               DaoModelo.SQL_PESQUISAR,
                               new String[] {"Código", "Nome" ,"Ativo"},
                               new FiltroPesquisa[] {new FiltroPesquisa("CÓDIGO", "ID", Integer.class),
                               new FiltroPesquisa("NOME", "NOME", String.class),
                               new FiltroPesquisa("ATIVO", "ATIVO", String.class),
            
                                },
                                new DefaultTableCellRenderer[] { 
                                                                new InteiroRender(), new RenderizadorTexto(),
                                                                new RenderizadorTexto()},
                                this, this, false, false, false)
        );
    }
   
    @Override
    public void preencherDados(int pk) {
      modelo.setId(pk);
      daoModelo.consultar();
      getPersistencia();
      super.preencherDados(pk);
   }
    
}
