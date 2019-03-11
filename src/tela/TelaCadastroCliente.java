
package tela;

import componente.MeuCampoBuscar;
import componente.MeuCampoCEP;
import componente.MeuCampoCheckBox;
import componente.MeuCampoComboBox;
import componente.MeuCampoCPFCNPJ;
import componente.MeuCampoInteiro;
import componente.MeuCampoFormatado;
import componente.MeuCampoRGIE;
import componente.MeuCampoTelefone;
import componente.MeuCampoTexto;
import dao.DaoCliente;
import dao.DaoCidade;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.ParseException;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.text.MaskFormatter;
import pojo.Cliente;
import pojo.ParametrosConsulta;
import rendererizador.InteiroRender;
import rendererizador.RenderizadorTexto;
import static tela.TelaCadastro.ESQUERDA;

import util.FiltroPesquisa;


public class TelaCadastroCliente extends TelaCadastro implements ActionListener,ItemListener{
    
    public static TelaCadastroCliente tela;
    public Cliente cliente = new Cliente();
    public DaoCliente daoCliente = new DaoCliente(cliente);
    
     public ParametrosConsulta parametrosConsulta =
            new ParametrosConsulta("Consulta de Cidade",
                                   DaoCidade.SQL_PESQUISAR_ATIVOS,
                                   new String[] {"Código", "Nome", "Ativo", "Estado"},
                                   new FiltroPesquisa[] {
                                   new FiltroPesquisa("CÓDIGO", "ID", Integer.class),
                                   new FiltroPesquisa("NOME", "CIDADE_NOME", String.class),
                                   new FiltroPesquisa("ESTADO", "ESTADO_NOME", String.class),
            
        },
                                    new DefaultTableCellRenderer[] { 
                                                                    new InteiroRender(), new RenderizadorTexto(),
                                                                    new RenderizadorTexto(), new RenderizadorTexto()},
                                   this, this, false, false, false
        );
    
    private MaskFormatter mf;
    private MaskFormatter mf2;
    
    public MeuCampoInteiro campoCodigo = new MeuCampoInteiro(5, true, false, "Código");
    public MeuCampoTexto campoNomeRazaoSocial = new MeuCampoTexto(20, 50, true, "Nome/RazãoSocial" );
    public MeuCampoTexto campoApelidoNomeFantasia = new MeuCampoTexto(20, 50, false, "Apelido/NomeFantasia" );
    public MeuCampoComboBox campoTipoCliente = new MeuCampoComboBox(true,true,new String[][] {{"F", "Física"}, {"J","Jurídica"}},"TipoCliente");
    public MeuCampoCPFCNPJ campoCpfCnpj = new MeuCampoCPFCNPJ(18, false, "CPF/CNPJ", true);
    public MeuCampoRGIE campoRgIE = new MeuCampoRGIE(20, false, "RG/IE", true);
    public MeuCampoFormatado campoEndereco = new MeuCampoFormatado(20, 50,false,"Endereço");
    public MeuCampoFormatado campoNumero = new MeuCampoFormatado(10, 20, false,"Número");
    public MeuCampoTexto campoComplemento = new MeuCampoTexto(20,20,false,"Complemento");
    public MeuCampoTexto campoBairro = new MeuCampoTexto(20,20,false,"Bairro");
    public MeuCampoCEP campoCep = new MeuCampoCEP(9, false, "CEP");
    public MeuCampoTelefone campoFone1 = new MeuCampoTelefone(14, false, "Fone1");
    public MeuCampoTelefone campoFone2 = new MeuCampoTelefone(14, false , "Fone2");
    public MeuCampoFormatado campoEmail = new MeuCampoFormatado(20,50,false,"E-mail");
    public MeuCampoFormatado campoRedesocial = new MeuCampoFormatado(20,50,false,"Rede Social");
    public MeuCampoBuscar campoCidade = new MeuCampoBuscar(TelaConsulta.class, TelaCadastroCidade.class, DaoCidade.SQL_COMBOBOX, parametrosConsulta, true, true, "Cidade");
    public MeuCampoCheckBox campoAtivo = new MeuCampoCheckBox(true, true, "Ativo");
    
    public JLabel frase = new JLabel("<html>Campo Obrigatório <font color=red>*</font><body>");
    
     public static TelaCadastro getTela() {  
        if (tela == null) {   
            tela = new TelaCadastroCliente();
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
    

       public TelaCadastroCliente() {
           
           super("Cadastro de Cliente");
           
           mf = new MaskFormatter();
           mf2 = new MaskFormatter();
           campoTipoCliente.addItemListener(this);
           campoTipoCliente.add(campoCpfCnpj);
           campoTipoCliente.add(campoRgIE);
        
           
          adicionarComponente(1, 1, 1, 1, campoCodigo, ESQUERDA);
          adicionarComponente(5, 1, 1, 1, campoNomeRazaoSocial, ESQUERDA);
          adicionarComponente(6, 1, 1, 1, campoApelidoNomeFantasia, ESQUERDA);
          adicionarComponente(3, 1, 1, 1, campoTipoCliente, ESQUERDA);
          adicionarComponente(5, 4, 1, 1, campoCpfCnpj, ESQUERDA);
          adicionarComponente(6, 4, 1, 1, campoRgIE, ESQUERDA);
          adicionarComponente(8, 1, 1, 1, campoEndereco, ESQUERDA);
          adicionarComponente(8, 4, 1, 1, campoNumero,ESQUERDA);
          adicionarComponente(10, 1, 1, 1, campoComplemento,ESQUERDA);
          adicionarComponente(11, 1, 1, 1, campoBairro,ESQUERDA);
          adicionarComponente(10, 4, 1, 1, campoCep,ESQUERDA);
          adicionarComponente(13, 1, 1, 1, campoFone1,ESQUERDA);
          adicionarComponente(13, 4, 1, 1, campoFone2,ESQUERDA);
          adicionarComponente(15, 1, 1, 1, campoEmail,ESQUERDA);
          adicionarComponente(15, 4, 1, 1, campoRedesocial,ESQUERDA);
          adicionarComponente(16, 1, 1, 1, campoCidade,ESQUERDA);
          adicionarComponente(17, 1, 1, 1, campoAtivo,ESQUERDA);
          adicionarComponente(18, 2, 1, 1, frase,ESQUERDA);
          pack();
          habilitarCampos(false);
}
         @Override
         public void itemStateChanged(ItemEvent e) {
         if (e.getSource() == campoTipoCliente && campoTipoCliente.getValor() == "F") {
             try {
                mf.uninstall();
                mf2.uninstall();
                mf.setMask("###.###.###-##");
                mf2.setMask("##.###.###-#");
                mf.install(campoCpfCnpj);
                mf2.install(campoRgIE);
            
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(null, "Não foi possivel setar a mascara CPF");
            }
            
        } else {
            try {
                mf.uninstall();
                mf2.uninstall();
                mf.setMask("##.###.###/####-##");
                mf2.setMask("########-##"); 
                mf.install(campoCpfCnpj);
                mf2.install(campoRgIE);
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(null, "Não foi possivel setar a mascara CNPJ");
            }


        }
    }
    
    public void setPersistencia() {
      cliente.setId((int) campoCodigo.getValor());
      cliente.setNomerazaosocial((String) campoNomeRazaoSocial.getValor());
      cliente.setApelidonomefantasia((String) campoApelidoNomeFantasia.getValor());
      cliente.setTipocliente((String) campoTipoCliente.getValor());
      cliente.setCpfcnpj((String) campoCpfCnpj.getValor());       
      cliente.setRgie((String) campoRgIE.getValor());       
      cliente.setEndereco((String) campoEndereco.getValor());
      cliente.setNumero((String) campoNumero.getValor());
      cliente.setComplemento((String) campoComplemento.getValor());
      cliente.setBairro((String) campoBairro.getValor());
      cliente.setCep((String) campoCep.getValor());
      cliente.setFone1((String) campoFone1.getValor());
      cliente.setFone2((String) campoFone2.getValor());
      cliente.setEmail((String) campoEmail.getValor());
      cliente.setRedesocial((String) campoRedesocial.getValor());
      cliente.getCidade().setId((int) campoCidade.getValor());
      cliente.setAtivo((boolean) campoAtivo.getValor());
             
   }
   
   public void getPersistencia() {
      campoCodigo.setValor(cliente.getId());
      campoNomeRazaoSocial.setValor(cliente.getNomerazaosocial());
      campoApelidoNomeFantasia.setValor(cliente.getApelidonomefantasia());
      campoTipoCliente.setValor(cliente.getTipocliente());
      campoCpfCnpj.setValor(cliente.getCpfcnpj());
      campoRgIE.setValor(cliente.getRgie());
      campoEndereco.setValor(cliente.getEndereco());
      campoNumero.setValor(cliente.getNumero());
      campoComplemento.setValor(cliente.getComplemento());
      campoBairro.setValor(cliente.getBairro());
      campoCep.setValor(cliente.getCep());
      campoFone1.setValor(cliente.getFone1());
      campoFone2.setValor(cliente.getFone2());
      campoEmail.setValor(cliente.getEmail());
      campoRedesocial.setValor(cliente.getRedesocial());
      campoCidade.setValor(cliente.getCidade().getId());
      campoAtivo.setValor(cliente.isAtivo());  
     
   }
   
    @Override
    public boolean incluirBD() {
      setPersistencia();
      getPersistencia(); 
      return daoCliente.incluir();
   }
   
    @Override
    public boolean alterarBD() {
      setPersistencia();
      return daoCliente.alterar();       
   }
   
    @Override
    public boolean excluirBD() {
      setPersistencia();
      return daoCliente.excluir();       
   }


   
    @Override
    public void consultarBD(int pk) {
       super.consultarBD(pk);
       cliente.setId(pk);
       daoCliente.consultar();
       getPersistencia();
   }
   
    @Override
    public void consultar() {
       
            super.consultar();
            new TelaConsulta((parametrosConsulta) = new ParametrosConsulta("Consulta de Cliente",
            DaoCliente.SQL_PESQUISAR,
            new String[] {"Código", "Nome/Razão Social", "Tipo Cliente", "CPF/CNPJ", "Ativo", "Cidade"},
            new FiltroPesquisa[] {
                                new FiltroPesquisa("CÓDIGO", "ID", Integer.class),
                                new FiltroPesquisa("NOME/RAZAOSOCIAL", "CLIENTE_NOMERAZAOSOCIAL", String.class),
                                new FiltroPesquisa("TIPO CLIENTE", "CLIENTE_TIPOCLIENTE", String.class),
                                new FiltroPesquisa("CPF/CNPJ", "CLIENTE_CPFCNPJ", String.class),
                                new FiltroPesquisa("ATIVO", "CLIENTE_ATIVO", String.class),
                                new FiltroPesquisa("CIDADE", "CLIENTE_CIDADE", String.class),
            
        },
                                new DefaultTableCellRenderer[] { 
                                                                new InteiroRender(), new RenderizadorTexto(),
                                                                new RenderizadorTexto(), new RenderizadorTexto(), new RenderizadorTexto(), new RenderizadorTexto()},
                                this, this, false, false, false)
        );
        
    }   
    @Override
   public void preencherDados(int pk) {
      cliente.setId(pk);
      daoCliente.consultar();
      getPersistencia();
      super.preencherDados(pk);
   }

  
     
}
