
package tela;

import componente.MeuCampoBuscar;
import componente.MeuCampoCheckBox;
import componente.MeuCampoInteiro;
import componente.MeuCampoTexto;
import dao.DaoEstado;
import dao.DaoPais;
import javax.swing.JLabel;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.table.DefaultTableCellRenderer;
import pojo.Estado;
import pojo.ParametrosConsulta;
import rendererizador.InteiroRender;
import rendererizador.RenderizadorTexto;
import util.FiltroPesquisa;


public class TelaCadastroEstado extends TelaCadastro{
    
    public static TelaCadastroEstado tela;
    public Estado estado = new Estado();
    public DaoEstado daoEstado = new DaoEstado(estado);
    public ParametrosConsulta parametrosConsulta =
            new ParametrosConsulta("Consulta de País",
                                   DaoPais.SQL_PESQUISAR_ATIVOS,
                                   new String[]{"Código", "Nome", "Ativo"},
                                   new FiltroPesquisa[] { 
                                                          new FiltroPesquisa("CÓDIGO", "ID", String.class),
                                                          new FiltroPesquisa("NOME", "NOME", String.class),
                                                          new FiltroPesquisa("ATIVO", "ATIVO", String.class)
                                   }, 
                                   new DefaultTableCellRenderer[] { new InteiroRender(), new RenderizadorTexto(),
                                                                    new RenderizadorTexto(), new RenderizadorTexto()},
                                   this, this, false, false, false
     );
   
    public MeuCampoInteiro campoCodigo = new MeuCampoInteiro(5, true, false, "Código");
    public MeuCampoTexto campoNome = new MeuCampoTexto(20, 50, true, "Nome" );
    public MeuCampoTexto campoSigla = new MeuCampoTexto(5, 2, true, "Sigla" );
    public MeuCampoBuscar campoPais = new MeuCampoBuscar(TelaConsulta.class, TelaCadastroPais.class, DaoPais.SQL_COMBOBOX, parametrosConsulta, true, true, "País");
    
    public JLabel frase = new JLabel("<html>Campo Obrigatório <font color=red>*</font><body>");
    
    public MeuCampoCheckBox campoAtivo = new MeuCampoCheckBox(true, true, "Ativo");
    
      public static TelaCadastro getTela() {  
        if (tela == null) {   
            tela = new TelaCadastroEstado();
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
    
    
    public TelaCadastroEstado() {
        
        super("Cadastro de Estado");
        adicionarComponente(1, 1, 1, 1, campoCodigo, ESQUERDA);
        adicionarComponente(2, 1, 1, 1, campoNome, ESQUERDA);
        adicionarComponente(3, 1, 1, 1, campoSigla,ESQUERDA);
        adicionarComponente(4, 1, 1, 1, campoPais,ESQUERDA);
        adicionarComponente(5, 1, 1, 1, campoAtivo,ESQUERDA);
        adicionarComponente(6, 2, 1, 1, frase,ESQUERDA);
        habilitarCampos(false);
        pack();
        
    }
    
    public void setPersistencia() {
      estado.setId((int) campoCodigo.getValor());
      estado.setNome((String) campoNome.getValor());
      estado.setSigla((String) campoSigla.getValor());
      estado.getPais().setId((int) campoPais.getValor());
      estado.setAtivo((boolean) campoAtivo.getValor());
             
    }
   
    public void getPersistencia() {
      campoCodigo.setValor(estado.getId());
      campoNome.setValor(estado.getNome());
      campoSigla.setValor(estado.getSigla());
      campoPais.setValor(estado.getPais().getId());
      campoAtivo.setValor(estado.isAtivo());  
     
    }
   
    @Override
    public boolean incluirBD() {
      setPersistencia();
      getPersistencia(); 
      return daoEstado.incluir();
    }
   
    @Override
    public boolean alterarBD() {
      setPersistencia();
      return daoEstado.alterar();       
    }
   
    @Override
    public boolean excluirBD() {
      setPersistencia();
      return daoEstado.excluir();       
   }
   
    @Override
    public void consultarBD(int pk) {
       super.consultarBD(pk);
       estado.setId(pk);
       daoEstado.consultar();
       getPersistencia();
   }
   
    @Override
    public void consultar() {
        super.consultar();
        new TelaConsulta((parametrosConsulta) = new ParametrosConsulta("Consulta de Estado",
        DaoEstado.SQL_PESQUISAR,
        new String [] {"Código", "Nome", "Sigla", "Ativo", "País"},
        new FiltroPesquisa[]{
            new FiltroPesquisa("CÓDIGO", "ID", Integer.class),
            new FiltroPesquisa("NOME", "ESTADO_NOME", String.class),
            new FiltroPesquisa("SIGLA", "SIGLA", String.class),
            new FiltroPesquisa("PAÍS", "PAIS_NOME", String.class)
        },
                                    new DefaultTableCellRenderer[] { 
                                                                    new InteiroRender(), new RenderizadorTexto(),
                                                                    new RenderizadorTexto(), new RenderizadorTexto(), new RenderizadorTexto()},
                                   this, this, false, false, false)
        );
    
       
    }

    @Override
    public void preencherDados(int pk) {
      estado.setId(pk);
      daoEstado.consultar();
      getPersistencia();
      super.preencherDados(pk);
   }
   
   
    
  
}
