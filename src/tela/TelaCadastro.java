
package tela;

import componente.MeuComponente;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import static tela.TelaSistema.meuJDesktopPane;


public class TelaCadastro extends JInternalFrame implements ActionListener {
    
    public JPanel jpComponentes = new JPanel();
    private JPanel jpBotoes = new JPanel();
    private boolean temDadosNaTela = false;
    
    private Icon iconIncluir = new ImageIcon("src/imagens/incluir.png");
    private Icon iconAlterar = new ImageIcon("src/imagens/alterar.png");
    private Icon iconExcluir = new ImageIcon("src/imagens/excluir.png");
    private Icon iconConsultar = new ImageIcon("src/imagens/consultar.png");
    private Icon iconConfirmar = new ImageIcon("src/imagens/confirmar.png");
    private Icon iconCancelar = new ImageIcon("src/imagens/cancelar.png");
    
    public static final int ESQUERDA = 0; //
    public static final int CIMA =1; //
    
    private JButton jbIncluir = new JButton("Incluir", iconIncluir);
    public JButton jbAlterar = new JButton("Alterar", iconAlterar);
    public JButton jbExcluir = new JButton("Excluir", iconExcluir);
    private JButton jbConsultar = new JButton("Consultar", iconConsultar);
    private JButton jbConfirmar = new JButton("Confirmar", iconConfirmar);
    private JButton jbCancelar = new JButton("Cancelar", iconCancelar);
    
    private final int PADRAO = 0;
    private final int INCLUINDO = 1;
    private final int ALTERANDO = 2;
    private final int EXCLUINDO = 3;
    public final int CONSULTANDO = 4;
    public int estadoTela = PADRAO;

    public List<JLabel> rotulos = new ArrayList();
    private List<MeuComponente> campos = new ArrayList();
    
    
    public TelaCadastro(String titulo) {
        
        super(titulo, true, true, true, false);
        getContentPane().add("West", jpComponentes);
        getContentPane().add("South", jpBotoes);
        
        jpComponentes.setLayout(new GridBagLayout());
        jpBotoes.setLayout(new GridLayout());
        
        adicionarBotao(jbIncluir);
        adicionarBotao(jbAlterar);
        adicionarBotao(jbExcluir);
        adicionarBotao(jbConsultar);
        adicionarBotao(jbConfirmar);
        adicionarBotao(jbCancelar);
        pack();
       
        setVisible(true);
        habilitarBotoes();
        
        
    }
    
    public void removeComponente(JComponent componente) {
            for (int i = 0; i < campos.size(); i++) {
                if (campos.get(i) == componente) {
                    jpComponentes.remove(rotulos.get(i));
                    jpComponentes.remove((JComponent) campos.get(i));
                    rotulos.remove(i);
                    campos.remove(i);
                }
            }
        }
    
    public JLabel getRotulo(JComponent componente) {
        for (int i = 0; i < campos.size(); i ++ ) {
            if (campos.get(i) == componente) {
                return rotulos.get(i);
            }
        }
        return null;
    }
    
    private void adicionarBotao(JButton botao) {
        jpBotoes.add(botao);
        botao.addActionListener(this);
    }
    
    public void habilitarBotoes() {
        
        jbIncluir.setEnabled(estadoTela == PADRAO);
        jbAlterar.setEnabled(estadoTela == PADRAO && temDadosNaTela);
        jbExcluir.setEnabled(estadoTela == PADRAO && temDadosNaTela);
        jbConsultar.setEnabled(estadoTela == PADRAO);
        jbConfirmar.setEnabled(estadoTela != PADRAO );
        jbCancelar.setEnabled(estadoTela != PADRAO );
    }
    
    public void adicionarComponente(int linha,
            int coluna,
            int linhas,
            int colunas,
            JComponent componente,
             int alinhamento) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridy = linha;
        gbc.gridx = coluna;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        if(alinhamento == ESQUERDA) {
            gbc.anchor = GridBagConstraints.EAST;
        } else {
            gbc.anchor = GridBagConstraints.WEST;
        }
        
        if (componente instanceof MeuComponente) {
             String nome = "<html><body>" + ((MeuComponente) componente).getNome() + ":";
             if (((MeuComponente) componente).eObrigatorio()) {
                   nome = nome + "<font color=red>*</font>";
             }
             nome = nome +"</body></html>";
             JLabel rotulo = new JLabel(nome);
             jpComponentes.add(rotulo, gbc);
             rotulos.add(rotulo);
             if (alinhamento == ESQUERDA) {
                gbc.gridx++;
                gbc.insets = new Insets(5, 0, 5, 5);
            } else {
                gbc.gridy++;
                gbc.insets = new Insets(0, 0, 5, 5);
            }
             campos.add((MeuComponente) componente);
        }
        gbc.gridx++;
        gbc.anchor = GridBagConstraints.WEST;
        jpComponentes.add((JComponent) componente, gbc);
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        
        if (ae.getSource() == jbIncluir) {
            incluir();
           } else if(ae.getSource() == jbAlterar) {
               alterar();
           } else if(ae.getSource() == jbExcluir) {
               excluir();
           } else if(ae.getSource() == jbConsultar) {
               consultar();
           } else if(ae.getSource() == jbConfirmar) {
               confirmar();
           } else if(ae.getSource() == jbCancelar) {
               cancelar();
           } 
        
    }
    
    public void habilitarCampos(boolean status) {
        for (int i = 0; i < campos.size(); i++) {
            campos.get(i).habilitar(status);
        }
    }
     
     public void limparCampos() {
         for(int i =0; i < campos.size(); i++) {
             campos.get(i).limpar();
         }
     }
     
   
    public boolean verificarCampos() {
        String obrigatoriosNaoPreenchidos = "";
        for (int i = 0; i < campos.size(); i++) {
            if (campos.get(i).eObrigatorio() && campos.get(i).eVazio()) {
                obrigatoriosNaoPreenchidos = obrigatoriosNaoPreenchidos + campos.get(i).getNome() + "\n";                
            }
        }
        if (!obrigatoriosNaoPreenchidos.isEmpty()) {
            JOptionPane.showMessageDialog(null, 
                    "Os campos abaixo são obrigatórios e não foram informados:\n\n" +
                            obrigatoriosNaoPreenchidos);
        }
        
        String invalidos = "";
        for (int i = 0; i < campos.size(); i++) {
            if (!campos.get(i).eValido()) {
                invalidos = invalidos + campos.get(i).getNome() + "\n";                
            }
        }
        if (!invalidos.isEmpty()) {
            JOptionPane.showMessageDialog(null, 
                    "Os campos abaixo  não estão Válidos: \n\n" +
                            invalidos);
        }
        
        return (obrigatoriosNaoPreenchidos.isEmpty() && invalidos.isEmpty());
    
        
    }

    public void incluir() {
        estadoTela = INCLUINDO;
        habilitarCampos(true);
        limparCampos();
        habilitarBotoes();
    }

    public void alterar() {
        estadoTela = ALTERANDO;
        habilitarCampos(true);
        habilitarBotoes();
    }

    private void excluir() {
        estadoTela = EXCLUINDO;
        habilitarBotoes();
    }

    public void consultar() {
        estadoTela = CONSULTANDO;
        habilitarBotoes();
    }
    

    public  void confirmar() {
        if (estadoTela == INCLUINDO) {
            if (!verificarCampos() || !incluirBD()) {
                return;
            }
        }
        if (estadoTela == ALTERANDO) {
            if (!verificarCampos() || !alterarBD()) {
                return;
            }
        }
        if (estadoTela == EXCLUINDO) {
            if (JOptionPane.showConfirmDialog(null, "Deseja realmente excluir?", "",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                if (!excluirBD()) {
                    return;
                }
                limparCampos();
                temDadosNaTela = false;
            } else {
                return;
            }
        }         
        estadoTela = PADRAO;
        habilitarCampos(false);
        habilitarBotoes();
    }

    public void cancelar() {
        estadoTela = PADRAO;
        habilitarCampos(false);
        limparCampos();
        temDadosNaTela = false;
        habilitarBotoes();
    }
    
    
    public boolean incluirBD() {
        return true;
    }
    
    public boolean alterarBD() {
        return true;
    }
    
    public boolean excluirBD() {
        return true;
    }
    public void consultarBD(int pk) {
        estadoTela = PADRAO;
        temDadosNaTela = true;
        habilitarBotoes();
        jbCancelar.setEnabled(true);
       
    }
    
    public void pesquisaSemDados() {
        estadoTela = PADRAO;
        habilitarBotoes();
    }
    
    public void preencherDados(int pk) {
        estadoTela = PADRAO;
        temDadosNaTela = true;
        habilitarBotoes();
    }
}
