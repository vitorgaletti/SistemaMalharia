package tela;

import banco.Conexao;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AttributeSet;
import javax.swing.text.PlainDocument;

import util.FiltroPesquisa;

public class TelaConsultaVelha extends JInternalFrame implements MouseListener {

    private JPanel painel = new JPanel();
    private JComboBox jcb = new JComboBox();
    
    private JTextField textoFiltro = new JTextField(20);
//    private Icon iconePesquisa = new ImageIcon("src/img/confirmar.png");
    private JButton botaoPesquisar = new JButton("Pesquisar");
    private JButton botaoListar = new JButton("Listar");
    private JButton botaoLimpar = new JButton("Limpar");
    private JTable tabela;
    private JScrollPane jsp;
    private DefaultTableModel dtm;
    private String sql;
    private FiltroPesquisa[] filtros;
    public static TelaCadastro telaChamadora;
    public static TelaConsultaVelha tela;
    
    public TelaConsultaVelha(String titulo, String sql, String[] colunas, FiltroPesquisa[] filtros, TelaCadastro telaChamadora) {        
        super(titulo, false, true, true, true);
        this.telaChamadora = telaChamadora;
        TelaSistema.meuJDesktopPane.add(this);
        TelaSistema.meuJDesktopPane.moveToFront(this);
        TelaSistema.meuJDesktopPane.setSelectedFrame(this);
        this.sql = sql;
        this.filtros = filtros;
        dtm = new DefaultTableModel(new Object[][]{},
                colunas);
        tabela = new JTable(dtm) {
            public boolean isCellEditable(int i, int i1) {
                return false;
            }
        };
        jsp = new JScrollPane(tabela);
        painel.setLayout(new FlowLayout());
        painel.add(jcb);
   
        painel.add(textoFiltro);
        painel.add(botaoPesquisar);
        painel.add(botaoListar);
        painel.add(botaoLimpar);
        getContentPane().add("North", painel);
        getContentPane().add("Center", jsp);
        jsp.setPreferredSize(new Dimension(1000, 600));
        pack();
        setVisible(true);
        preencherComboBox();
        //preencherDados();       
        adicionaListeners();
       
    }
    
    public void adicionaListeners() {
        jcb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Class tipo = filtros[jcb.getSelectedIndex() -1].tipo;
                
                if (tipo == Integer.class) {
                    textoFiltro.setDocument(new MeuDocument(5));
                }else if(tipo == Date.class) {
                    
                }  else  {
                    textoFiltro.setDocument(new MeuDocument1(20));
                }  
            }
        });
         botaoPesquisar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                
                if ((textoFiltro.getText().trim().equals("") || (jcb.getSelectedIndex() == 0))) {
                       JOptionPane.showMessageDialog(null,"O Campo está Vazio ou Selecione algum Filtro.");
                          return;
                 
                } else {
                 
                   preencherDados();
                }
            }
             
        });
        
        tabela.addMouseListener(this);
        
        botaoListar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
//                if(textoFiltro.getText().equals("")) {
//                   JOptionPane.showMessageDialog(null, "Nenhum Dado Registrado ! "); 
//                }
                 preencherDados();
            }
        });
        tabela.addMouseListener(this);
        
        KeyAdapter KeyAdapterTextoFiltro = new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        preencherTabela(); 
                    }
                });
            }
        };
        textoFiltro.addKeyListener(KeyAdapterTextoFiltro);
        
       botaoLimpar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                 
              limparTabela();
                }
            });
    }
      
    

   
    

    @Override
    public void mouseClicked(MouseEvent me) {
//JOptionPane.showMessageDialog(null, "Entrei");
        if (me.getClickCount() == 2) {
            int linhaSelecionada = tabela.getSelectedRow();
            String codigo = (String) dtm.getValueAt(linhaSelecionada, 0);
            int pk = Integer.parseInt(codigo);
            telaChamadora.preencherDados(pk);
            dispose();
            TelaSistema.meuJDesktopPane.remove(this);
        }
    }

    @Override
    public void mousePressed(MouseEvent me) {
    }

    @Override
    public void mouseReleased(MouseEvent me) {
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }

    private void preencherComboBox() {
        jcb.addItem("SELECIONE");
        for (int i = 0; i < filtros.length; i++) {
            jcb.addItem(filtros[i].nomeFiltro);
        }
    }

    
    public void preencherTabela() {
            String vazio = textoFiltro.getText();

                        if (!vazio.isEmpty() && jcb.getSelectedIndex() > 0) {
                             preencherDados();
                        }
                        if (vazio.isEmpty()) {
                                limparTabela();
                            }
                        
        }
    
    public void limparTabela() {
        while (dtm.getRowCount() > 0) {
                       dtm.removeRow(0);
                }
    }

 private void preencherDados() {
        String Sql = sql;
        if (jcb.getSelectedIndex() > 0) {
            Sql = "SELECT * FROM (" + sql + ") as X where ";
            Class tipo = filtros[jcb.getSelectedIndex() - 1].tipo;
            if (tipo == Integer.class) {
//                Sql = Sql
//                        + filtros[jcb.getSelectedIndex() - 1].nomeAtributo
//                        + " = ";
//                Sql = Sql + textoFiltro.getText();
                Sql = Sql + "UPPER("
                        + filtros[jcb.getSelectedIndex() - 1].nomeAtributo
                        + ") LIKE '%";
                
                Sql = Sql + textoFiltro.getText().toUpperCase()
                        + "%'";
     
            } else if (tipo == String.class) {
                Sql = Sql + "UPPER("
                        + filtros[jcb.getSelectedIndex() - 1].nomeAtributo
                        + ") LIKE '%";
                Sql = Sql + textoFiltro.getText().toUpperCase()
                        + "%'";
            } else {
                JOptionPane.showMessageDialog(null, "NÃO EXISTE TIPO DEFINIDO.");
            }
        }
        while (dtm.getRowCount() > 0) {
            dtm.removeRow(0);
        }
       
        List<Object[]> dados = Conexao.pesquisar(Sql);
        for (int i = 0; i < dados.size(); i++) {
            dtm.addRow(dados.get(i));
        }
       
    }
   
}

class MeuDocument extends PlainDocument {

    private Integer tamanho;

    public MeuDocument(int tamanho) {
        this.tamanho = tamanho;
    }

    @Override
    public void insertString(int offs, String str, AttributeSet a) {
        try {
            if ((getLength() + str.length()) > tamanho) {
                return;
            }
            Pattern padrao = Pattern.compile("[0-9,' ']");
            Matcher matcher = padrao.matcher(str);
            if (!matcher.find()) {
                return;
            }
            super.insertString(offs, str.toUpperCase(), a);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
}
 

 class MeuDocument1 extends PlainDocument {
        
        private Integer tamanho;
        
        public MeuDocument1(int tamanho) {
            this.tamanho = tamanho;
        }
        
        @Override
        public void insertString(int offs, String str, AttributeSet a) {
            
            try {
                if((getLength() + str.length()) > tamanho) {
                    return; 
                } 
                Pattern padrao = Pattern.compile("[aA-zZ,'',0-9, '/', '.', '-']");
                Matcher matcher = padrao.matcher(str);
                
                if(!matcher.find()) {
                    return;
                }
                
                super.insertString(offs, str.toUpperCase(), a);
            } catch (Exception e) {
                e.printStackTrace();
            }
            
        }
 }




 

 
