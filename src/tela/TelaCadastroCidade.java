
package tela;

import componente.MeuCampoBuscar;
import componente.MeuCampoCheckBox;

import componente.MeuCampoInteiro;
import componente.MeuCampoTexto;
import dao.DaoCidade;
import dao.DaoEstado;
import javax.swing.JLabel;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.table.DefaultTableCellRenderer;
import pojo.Cidade;
import pojo.ParametrosConsulta;
import rendererizador.InteiroRender;
import rendererizador.RenderizadorTexto;
import util.FiltroPesquisa;


public class TelaCadastroCidade extends TelaCadastro{
    
    public static TelaCadastroCidade tela;
    public Cidade cidade = new Cidade();
    public DaoCidade daoCidade = new DaoCidade(cidade);
    
    public ParametrosConsulta parametrosConsulta =
            new ParametrosConsulta("Consulta de Estado",
                                   DaoEstado.SQL_PESQUISAR_ATIVOS,
                                   new String[]{"Código", "Nome", "Sigla", "Ativo", "Estado"},
                                   new FiltroPesquisa[] { new FiltroPesquisa("CÓDIGO", "ID", String.class),
                                                          new FiltroPesquisa("NOME", "ESTADO_NOME", String.class),
                                                          new FiltroPesquisa("SIGLA", "SIGLA", String.class),
                                                          new FiltroPesquisa("PAÍS", "PAIS_NOME", String.class)
                     
                                   }, 
                                   new DefaultTableCellRenderer[] { 
                                                                    new InteiroRender(), new RenderizadorTexto(),
                                                                    new RenderizadorTexto(), new RenderizadorTexto(), new RenderizadorTexto()},
                                   this, this, false, false, false
     );
   
    public MeuCampoInteiro campoCodigo = new MeuCampoInteiro(5, true, false, "Código");
    public MeuCampoTexto campoNome = new MeuCampoTexto(20, 50, true, "Nome" );
    public MeuCampoBuscar campoEstado = new MeuCampoBuscar(TelaConsulta.class, TelaCadastroEstado.class, DaoEstado.SQL_COMBOBOX, parametrosConsulta, true, true, "Estado");
    public MeuCampoCheckBox campoAtivo = new MeuCampoCheckBox(true, true, "Ativo");
        
    public JLabel frase = new JLabel("<html>Campo Obrigatório <font color=red>*</font><body>");
    
    public static TelaCadastro getTela() {  
        if (tela == null) {   
            tela = new TelaCadastroCidade();
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
    
    public TelaCadastroCidade() {
        
        super("Cadastro de Cidade");
        adicionarComponente(1, 1, 1, 1, campoCodigo, ESQUERDA);
        adicionarComponente(2, 1, 1, 1, campoNome,ESQUERDA);
        adicionarComponente(3, 1, 1, 1, campoEstado, ESQUERDA);
        adicionarComponente(4, 1, 1, 1, campoAtivo, ESQUERDA);
        adicionarComponente(5, 2, 1, 1, frase, ESQUERDA);
        habilitarCampos(false);
        pack();
        
    }
    
    public void setPersistencia() {
      cidade.setId((int) campoCodigo.getValor());
      cidade.setNome((String) campoNome.getValor());
      cidade.getEstado().setId((int) campoEstado.getValor());
      cidade.setAtivo((boolean) campoAtivo.getValor());
             
    }
   
    public void getPersistencia() {
      campoCodigo.setValor(cidade.getId());
      campoNome.setValor(cidade.getNome());
      campoEstado.setValor(cidade.getEstado().getId());
      campoAtivo.setValor(cidade.isAtivo());  
     
    }
   
    @Override
    public boolean incluirBD() {
      setPersistencia();
      getPersistencia(); 
      return daoCidade.incluir();
    }
   
    @Override
    public boolean alterarBD() {
      setPersistencia();
      return daoCidade.alterar();       
    }
   
    @Override
    public boolean excluirBD() {
      setPersistencia();
      return daoCidade.excluir();       
   }


     @Override
     public void consultarBD(int pk) {
       super.consultarBD(pk);
       cidade.setId(pk);
       daoCidade.consultar();
       getPersistencia();
   }
      
    @Override
    public void consultar() {
       
            super.consultar();
            new TelaConsulta((parametrosConsulta) = new ParametrosConsulta("Consulta de Cidade",
            DaoCidade.SQL_PESQUISAR,
            new String[] {"Código", "Nome", "Ativo", "Estado"},
            new FiltroPesquisa[] {
                      new FiltroPesquisa("CÓDIGO", "ID", Integer.class),
                      new FiltroPesquisa("NOME", "CIDADE_NOME", String.class),
                      new FiltroPesquisa("ESTADO", "ESTADO_NOME", String.class),
            
        },
                                        new DefaultTableCellRenderer[] { 
                                                                        new InteiroRender(), new RenderizadorTexto(),
                                                                        new RenderizadorTexto(), new RenderizadorTexto()},
                                   this, this, false, false, false)
        );
        
    }   
    
    @Override
    public void preencherDados(int pk) {
      cidade.setId(pk);
      daoCidade.consultar();
      getPersistencia();
      super.preencherDados(pk);
   }
   
}
