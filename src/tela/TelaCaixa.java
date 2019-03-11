
package tela;

import componente.MeuCampoCheckBox;
import componente.MeuCampoData;
import componente.MeuCampoInteiro;
import componente.MeuCampoMonetario;
import dao.DaoCaixa;
import java.math.BigDecimal;
import java.util.Date;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import pojo.Caixa;



public class TelaCaixa extends TelaCadastro{
    
    public static TelaCaixa tela;
    public Caixa caixa = new Caixa();
    public DaoCaixa daoCaixa = new DaoCaixa(caixa);
    
    public MeuCampoInteiro campoCodigo = new MeuCampoInteiro(5, true, false, "Código");
    public MeuCampoData campoData= new MeuCampoData(10, true, "Data" );
    public MeuCampoMonetario campoSaldoInicial = new MeuCampoMonetario(15, false, false, true, "Saldo Inicial");
    public MeuCampoMonetario campoSaldoFinal = new MeuCampoMonetario(15, false, false, true, "Saldo Final");
    public MeuCampoMonetario campoDiferenca = new MeuCampoMonetario(15, false, false, true, "Diferença");
    public MeuCampoCheckBox campoAberto = new MeuCampoCheckBox(true, true, "Aberto");
    
 
    
     
    public static TelaCadastro getTela() {  
        if (tela == null) {   
            tela = new TelaCaixa();
            TelaSistema.meuJDesktopPane.add(tela);
            tela.addInternalFrameListener(new InternalFrameAdapter() { 
                @Override
                public void internalFrameClosed(InternalFrameEvent e) {
                    TelaSistema.meuJDesktopPane.remove(tela);
                    tela = null;
                }
            });
            //TelaSistema.meuJDesktopPane.add(tela);
        }
        
        //TelaSistema.meuJDesktopPane.setSelectedFrame(tela);
        TelaSistema.meuJDesktopPane.moveToFront(tela);
        return tela;
    }
    
   
    
    public TelaCaixa() {
        
        super("Movimento de Caixa");
        adicionarComponente(1, 1, 1, 1, campoCodigo, ESQUERDA);
        adicionarComponente(2, 1, 1, 1, campoData, ESQUERDA);
        adicionarComponente(3, 1, 1, 1, campoSaldoInicial, ESQUERDA);
        adicionarComponente(4, 1, 1, 1, campoSaldoFinal, ESQUERDA);
        adicionarComponente(5, 1, 1, 1, campoDiferenca, ESQUERDA);
        adicionarComponente(6, 1, 1, 1, campoAberto, ESQUERDA);
        
  
       
        
        habilitarCampos(false);
        pack();
        
       
        
    }
      public void setPersistencia() {
      caixa.setId((int) campoCodigo.getValor());
      caixa.setData((Date) campoData.getValor());
      caixa.setSaldoinicial((BigDecimal) campoSaldoInicial.getValor());
      caixa.setSaldofinal((BigDecimal) campoSaldoFinal.getValor());
      caixa.setDiferenca((BigDecimal) campoDiferenca.getValor());
      caixa.setAberto((boolean) campoAberto.getValor());
             
   }
   
   public void getPersistencia() {
      campoCodigo.setValor(caixa.getId());
      campoData.setValor(caixa.getData());
      campoSaldoInicial.setValor(caixa.getSaldofinal());
      campoSaldoFinal.setValor(caixa.getSaldofinal());
      campoDiferenca.setValor(caixa.getDiferenca());
      campoAberto.setValor(caixa.isAberto());  
     
   }
   
//  @Override
//   public boolean incluirBD() {
//      setPersistencia();
//      getPersistencia(); 
//      return daoPais.incluir();
//   }
//   
//   @Override
//   public boolean alterarBD() {
//      setPersistencia();
//      return daoPais.alterar();       
//   }
//   
//   @Override
//   public boolean excluirBD() {
//      setPersistencia();
//      return daoPais.excluir();       
//   }

//   @Override
//   public void consultar() {
//       super.consultar();
//       new TelaConsulta(this, 
//               "Consulta de País",
//               new String[] {"Código", "Nome", "Ativo"},
//               DaoPais.SQL_PESQUISAR); 
//   }   
//   
//   @Override
//   public void consultarBD(int pk) {
//       super.consultarBD(pk);
//       pais.setId(pk);
//       daoPais.consultar();
//       getPersistencia();
//   }
////   
//     @Override
//    public void consultar() {
//        FiltroPesquisa[] filtros = new FiltroPesquisa[]{
//            new FiltroPesquisa("CÓDIGO", "ID", Integer.class),
//            new FiltroPesquisa("NOME", "NOME", String.class),
//            
//        };
//        new TelaConsulta("Pesquisa de País",
//                daoPais.SQL_PESQUISAR,
//                new String[]{"CÓDIGO", "NOME", "ATIVO"}, filtros, this);
//    }
//   
//    @Override
//   public void preencherDados(int pk) {
//      pais.setId(pk);
//      daoPais.consultar();
//      getPersistencia();
//      super.preencherDados(pk);
//   }
   
   
    
  
}
