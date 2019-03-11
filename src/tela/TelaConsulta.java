package tela;

import banco.Conexao;
import com.toedter.calendar.JDateChooser;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import componente.MeuCampoBuscar;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import pojo.ParametrosConsulta;
import util.RestricaoCaracteres;
import util.RestricaoCaracteresPesquisar;
import util.RestricaoCaracteresPesquisarData;

public class TelaConsulta extends JInternalFrame implements MouseListener {

    private JPanel painel = new JPanel();
    private JPanel painelPesquisar = new JPanel();
    private JPanel painelOrdenar = new JPanel();
    private JPanel painelData = new JPanel();
    private JPanel painelCheckBoxData = new JPanel();
    private JPanel painelCheckBoxContaPagar = new JPanel();
    private JPanel painelPessoa = new JPanel();
    private JComboBox jcbPesquisar = new JComboBox();
    private JComboBox jcbOrdenar = new JComboBox();
    private JTextField textoPesquisar = new JTextField(20);
    private JDateChooser data = new JDateChooser();
    private JCheckBox checkBoxData = new JCheckBox();
    private JCheckBox checkBoxContaPagarGerada = new JCheckBox();
    private JCheckBox checkBoxCliente = new JCheckBox();
    private JCheckBox checkBoxFornecedor = new JCheckBox();
    private JCheckBox checkBoxFuncionario = new JCheckBox();
    private JButton botaoListar = new JButton("Listar");
    
    
    private JButton botaoLimpar = new JButton("Limpar");
    private JButton botaoData = new JButton("Pesquisar");
    private JTable tabela;
    private JScrollPane jsp;
    private DefaultTableModel dtm;
    
    public String guardaSql;
    
    public static Object chamador;
    
    public ParametrosConsulta parametrosConsulta;

    public TelaConsulta(ParametrosConsulta parametrosConsulta) {
        
        super(parametrosConsulta.getTituloConsulta(), true, true, true, true);
        setFrameIcon( new javax.swing.ImageIcon( getClass().getResource("/imagens/consultar.png") ) );
        this.parametrosConsulta = parametrosConsulta;
        TelaSistema.meuJDesktopPane.add(this);
        TelaSistema.meuJDesktopPane.moveToFront(this);
        TelaSistema.meuJDesktopPane.setSelectedFrame(this);
        dtm = new DefaultTableModel(new Object[][]{},
                parametrosConsulta.getColunas());
        tabela = new JTable(dtm) {
            @Override
            public boolean isCellEditable(int i, int i1) {
                return false;
            }
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int rowIndex, int vColIndex) {
                Component c = super.prepareRenderer(renderer, rowIndex, vColIndex);
                if (rowIndex % 2 == 0) {
                   c.setBackground(Color.WHITE);
                } else {
                   c.setBackground(getBackground());
                }
                if (isCellSelected(rowIndex, vColIndex)) {
                    c.setBackground(new Color(27, 215, 252));
                }            
                return c;
            }
        };
        jsp = new JScrollPane(tabela);
        painel.setLayout(new FlowLayout());
        painelPesquisar.setLayout(new FlowLayout());
        JLabel labelOrdenar = new JLabel("Ordenar : ");

        painel.add(jcbPesquisar);
        jcbPesquisar.setToolTipText("Selecione um filtro e digite o que deseja para iniciar uma pesquisa.");
        painel.add(textoPesquisar);
        painel.add(botaoListar);
        painel.add(botaoLimpar);
        botaoListar.setToolTipText("Listar todos os cadastros ordenados pelo código.");
        
        painelOrdenar.add(labelOrdenar);
        painelOrdenar.add(jcbOrdenar);
        painel.add(painelOrdenar);
        textoPesquisar.setEnabled(false);
        
        getContentPane().add("Center", painelPesquisar);
        getContentPane().add("North", painel);
        getContentPane().add("South", jsp);
        jsp.setPreferredSize(new Dimension(800, 400));
        if (dtm.getRowCount() <= 0) {
            jcbOrdenar.setEnabled(false);
        }
        setSize(1200, 520);
        setVisible(true);
        preencherComboBox();
        centralizaTela();
        adicionaListeners();
       
        if (parametrosConsulta.isPodeHabilitarData() == true) {
            adicionaListenersData();
        } 
        if (parametrosConsulta.isPodeHabilitarPessoa() == true) {
            adicionaListenersPessoa();
        }
        if (parametrosConsulta.isPodeHabilitarContaPagar() == true) {
            adicionaListenersContaPagar();
        }
     
        aplicaRender();
        tabela.addMouseListener(this);
    }

    private void aplicaRender() {
        TableColumnModel tableModel = tabela.getColumnModel();
        tableModel.getColumn(0).setMaxWidth(60); 
        tableModel.getColumn(1).setPreferredWidth(200); 
        for (int i = 0; i < dtm.getColumnCount(); i++) {
            if (parametrosConsulta.getRenderizadores()[i] != null) {
                tableModel.getColumn(i).setCellRenderer(parametrosConsulta.getRenderizadores()[i]);
            }
        }
    }
    
    private void adicionaListenersPessoa() {
        getContentPane().add("West", painelPessoa);
        JLabel labelCliente = new JLabel("Cliente");
        JLabel labelFornecedor = new JLabel("Fornecedor");
        JLabel labelFuncionario = new JLabel("Funcionario");
      
        painelPessoa.add(labelCliente);
        painelPessoa.add(checkBoxCliente);
        painelPessoa.add(labelFornecedor);
        painelPessoa.add(checkBoxFornecedor);
        painelPessoa.add(labelFuncionario);
        painelPessoa.add(checkBoxFuncionario);
        
        checkBoxCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (checkBoxCliente.isSelected()) {
                    preencherDadosPesquisar();
                }
            }
        });
        checkBoxFornecedor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (checkBoxFornecedor.isSelected()) {
                    preencherDadosPesquisar();
                }         
            }
        });
        checkBoxFuncionario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (checkBoxFuncionario.isSelected()) {
                    preencherDadosPesquisar();
                }         
            }
        });
        setSize(1000, 520);
    }
    
    private void adicionaListenersData() {
        getContentPane().add("East", painelData);
        getContentPane().add("West", painelCheckBoxData);
        
        JPanel espacador1 = new JPanel();
        espacador1.setPreferredSize(new Dimension (65, 10));
        painelCheckBoxData.add(espacador1);
        JLabel labelCheckBoxData = new JLabel("Pesquisar pelo campo de Data:");
        painelCheckBoxData.add(labelCheckBoxData);
        painelCheckBoxData.add(checkBoxData);
  
        JPanel espacador = new JPanel();
        espacador.setPreferredSize(new Dimension (308, 10));
        JLabel labelData = new JLabel("Data:");
        painelData.add(labelData);
        painelData.add(data);
        painelData.add(botaoData);
        painelData.add(espacador);
        painelData.setVisible(false);
        setSize(1200, 520);
        checkBoxData.addChangeListener(new ChangeListener() {
        @Override
        public void stateChanged(ChangeEvent e) {
            if(checkBoxData.isSelected()) {
                painelData.setVisible(true);
                textoPesquisar.setEnabled(false);
                setSize(1200, 520);
            } else {
                painelData.setVisible(false);
                setSize(1200, 520);
               }
            }
        });
        
        data.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent fe) {
                botaoData.setEnabled(true);
            }

            @Override
            public void focusLost(FocusEvent fe) {
 
            }
        });
        
        botaoData.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                preencherDadosData();
                if (dtm.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(null, "Nenhum resultado encontrado.");
                    
                }
                if (dtm.getRowCount() > 0) {
                    jcbOrdenar.setEnabled(true);
                }
            }
        });
        data.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                 if(e.getKeyCode() == KeyEvent.VK_ENTER){
                     preencherDadosData();
                if (dtm.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(null, "Nenhum resultado encontrado.");
                   
                }
                if (dtm.getRowCount() > 0) {
                    jcbOrdenar.setEnabled(true);
                }
                 }
            }
        });
    }
    
    private void adicionaListenersContaPagar() {
        
        painel.add(painelCheckBoxContaPagar);
        JLabel labelContaPagar = new JLabel("Compra não Gerada: ");
      
        painelCheckBoxContaPagar.add(labelContaPagar);
        painelCheckBoxContaPagar.add(checkBoxContaPagarGerada);
        
        checkBoxContaPagarGerada.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (checkBoxContaPagarGerada.isSelected()) {
                    preencherDadosPesquisar();
                }
                if (dtm.getRowCount() > 0) {
                    jcbOrdenar.setEnabled(true);
                }
            }
        });
        setSize(1200, 520);
    }

    private void adicionaListeners() {
       
     
        textoPesquisar.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        if (textoPesquisar.getText().isEmpty()) {
                            while (dtm.getRowCount() > 0) {
                                dtm.removeRow(0);
                            }
                            jcbOrdenar.setEnabled(false);
                            return;
                        }
                        if (dtm.getRowCount() >= 0) {
                            jcbOrdenar.setEnabled(true);
                        }
                        preencherDadosPesquisar();
                    }
                });
            }
        });
        textoPesquisar.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent fe) {
                botaoData.setEnabled(true);
                botaoListar.setEnabled(false);
                if (jcbPesquisar.getSelectedItem().equals("Código")) {
                    textoPesquisar.setDocument(new RestricaoCaracteresPesquisar(20));
                } else if (jcbPesquisar.getSelectedItem().equals("Data")) {
                    textoPesquisar.setDocument(new RestricaoCaracteresPesquisarData(10));
                }
                else {
                    textoPesquisar.setDocument(new RestricaoCaracteres(20));
                }
            }

            @Override
            public void focusLost(FocusEvent fe) {
                botaoListar.setEnabled(true);
            }
        });
        jcbPesquisar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (jcbPesquisar.getSelectedIndex() == 0) {
                    textoPesquisar.setEnabled(false);
                } else {
                    textoPesquisar.setEnabled(true);
                }
            }
        });
        botaoListar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                jcbPesquisar.setSelectedIndex(0);
                checkBoxCliente.setSelected(false);
                checkBoxFornecedor.setSelected(false);
                checkBoxFuncionario.setSelected(false);
                checkBoxContaPagarGerada.setSelected(false);
                preencherDadosListar();
                if (dtm.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(null, "Não existem dados cadastrados.");
                }
                if (dtm.getRowCount() > 0) {
                    jcbOrdenar.setEnabled(true);
                }
            }
        });
        
        botaoLimpar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                while (dtm.getRowCount() > 0) {
                    dtm.removeRow(0);
                }
                jcbOrdenar.setEnabled(false);
                jcbPesquisar.setSelectedIndex(0);
                textoPesquisar.setText("");
                checkBoxCliente.setSelected(false);
                checkBoxFornecedor.setSelected(false);
                checkBoxFuncionario.setSelected(false);
                checkBoxContaPagarGerada.setSelected(false);
            }
        });
        jcbPesquisar.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent ie) {
                if (ie.getStateChange() == ItemEvent.SELECTED) {
                    textoPesquisar.requestFocus();
                }
            }
        });
        jcbOrdenar.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent ie) {
                if (ie.getStateChange() == ItemEvent.SELECTED) {
                    preencherDadosPesquisar();
                }
            }
        });
    }

    public void centralizaTela() {
        Dimension tamanhoTela = getSize();
        Dimension tamanhoJDesktopPane = TelaSistema.meuJDesktopPane.getSize();
        int x = (tamanhoJDesktopPane.width - tamanhoTela.width) / 2;
        int y = (tamanhoJDesktopPane.height - tamanhoTela.height) / 2;
        setLocation(x, y);
    }
    
    @Override
    public void mouseClicked(MouseEvent me) {
        if (me.getClickCount() == 2) {
            int linhaSelecionada = tabela.getSelectedRow();
            String codigo = (String) dtm.getValueAt(linhaSelecionada, 0);
            int pk = Integer.parseInt(codigo);
            chamador = parametrosConsulta.getChamador();
            if (chamador instanceof TelaCadastro) {
            
                ((TelaCadastro) chamador).consultarBD(pk);
            } else if (chamador instanceof MeuCampoBuscar) {
                ((MeuCampoBuscar) chamador).setValorDuploClique(pk);
            }
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
        jcbPesquisar.addItem("SELECIONE");
        jcbOrdenar.addItem("SELECIONE");
        for (int i = 0; i < parametrosConsulta.getFiltros().length; i++) {
            jcbPesquisar.addItem(parametrosConsulta.getFiltros()[i].nomeFiltro);
            jcbOrdenar.addItem(parametrosConsulta.getFiltros()[i].nomeFiltro);
        }
    }

    private void preencherDadosPesquisar() {
        String tempSql = parametrosConsulta.getSql();
        if (jcbPesquisar.getSelectedIndex() > 0) {
            tempSql = "SELECT * FROM (" + parametrosConsulta.getSql() + ") where ";
            Class tipo = parametrosConsulta.getFiltros()[jcbPesquisar.getSelectedIndex() - 1].tipo;
            if (tipo == Date.class) { 
                tempSql = tempSql + 
                          "       CAST(" +
                          "             LPAD(EXTRACT(DAY FROM data), 2, '0') || '/' || " +
                          "             LPAD(EXTRACT(MONTH FROM data), 2, '0') || '/' || " +
                          "             LPAD(EXTRACT(YEAR FROM data), 4, '0') " +
                          "             AS VARCHAR(10)) " +
                          "       LIKE '%";
                tempSql = tempSql + textoPesquisar.getText().toUpperCase()
                        + "%'";                
            } else if (tipo == String.class) {
                tempSql = tempSql + "UPPER("
                        + parametrosConsulta.getFiltros()[jcbPesquisar.getSelectedIndex() - 1].nomeAtributo
                        + ") LIKE '%";
                tempSql = tempSql + textoPesquisar.getText().toUpperCase()
                        + "%'";
            } else if (tipo == Integer.class) {
                tempSql = tempSql + "UPPER("
                        + parametrosConsulta.getFiltros()[jcbPesquisar.getSelectedIndex() - 1].nomeAtributo
                        + ") LIKE '%";
                tempSql = tempSql + textoPesquisar.getText().toUpperCase()
                        + "%'";
            } else {
                JOptionPane.showMessageDialog(null, "Não existe tipo definido.");
            }
            guardaSql = tempSql;
            
        }
        while (dtm.getRowCount() > 0) {
            dtm.removeRow(0);
        }
        
        if (checkBoxCliente.isSelected()) {
             tempSql = "SELECT * FROM (" + tempSql + ") WHERE ECLIENTE = 'S'";
             
        }
        if (checkBoxFornecedor.isSelected()) {
             tempSql = "SELECT * FROM (" + tempSql + ") WHERE EFORNECEDOR = 'S'";
             
        }
        if (checkBoxFuncionario.isSelected()) {
             tempSql = "SELECT * FROM (" + tempSql + ") WHERE EFUNCIONARIO = 'S'";
           
        }
        if (checkBoxContaPagarGerada.isSelected()) {
             tempSql = "SELECT * FROM (" + tempSql + ") WHERE CP = 'N'";
            
        }
        
        if (!checkBoxCliente.isSelected() && !checkBoxFornecedor.isSelected() && !checkBoxFuncionario.isSelected()) {
            while (dtm.getRowCount() > 0) {
                dtm.removeRow(0);
            }
        }   

        guardaSql = tempSql;
        
        if (jcbOrdenar.getSelectedIndex() > 0) {
            Class tipo = parametrosConsulta.getFiltros()[jcbOrdenar.getSelectedIndex() - 1].tipo;
            tempSql = guardaSql + " order by ";
            tempSql = tempSql + parametrosConsulta.getFiltros()[jcbOrdenar.getSelectedIndex() - 1].nomeAtributo;
            
        }
        
        List<Object[]> dados = Conexao.pesquisar(tempSql);
        for (int i = 0; i < dados.size(); i++) {
            dtm.addRow(dados.get(i));
        }
        
    }

    private void preencherDadosListar() {
        String tempSql = parametrosConsulta.getSql();
        guardaSql = tempSql;
        tempSql = parametrosConsulta.getSql() + " ORDER BY ID";

        while (dtm.getRowCount() > 0) {
            dtm.removeRow(0);
        }
        List<Object[]> dados = Conexao.pesquisar(tempSql);
        for (int i = 0; i < dados.size(); i++) {
            dtm.addRow(dados.get(i));
        }
        
    }
    
    private void preencherDadosData() {
        String tempSql = parametrosConsulta.getSql();
        tempSql = "SELECT * FROM (" + parametrosConsulta.getSql() + ") where ";
        tempSql = tempSql + 
                          "CAST(" +
                          "LPAD(EXTRACT(DAY FROM data), 2, '0') || '/' || " +
                          "LPAD(EXTRACT(MONTH FROM data), 2, '0') || '/' || " +
                          "LPAD(EXTRACT(YEAR FROM data), 4, '0') " +
                          "AS VARCHAR(10)) " +
                          "LIKE '%";
        String date = new SimpleDateFormat("dd/MM/yyyy").format(data.getDate());
        tempSql = tempSql + date
                        + "%'";

        
        while (dtm.getRowCount() > 0) {
            dtm.removeRow(0);
        }
        List<Object[]> dados = Conexao.pesquisar(tempSql);
        for (int i = 0; i < dados.size(); i++) {
            dtm.addRow(dados.get(i));
        }
       
    }


}
