package componente;

import banco.Conexao;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class MeuCampoDBComboBox extends JPanel implements MeuComponente {
   private boolean preenchendo = false;
   private JComboBox jcb = new JComboBox();
   private Icon iconGettela = new ImageIcon("src/imagens/add.png");
   private JButton jb = new  JButton(iconGettela);
   
   
   private boolean podeHabilitar;
   private List<Integer> pks;
   private String sql;
   private boolean obrigatorio;
   private String nome;
 
  
   
   public MeuCampoDBComboBox(final Class tela, String sql, boolean obrigatorio,boolean podeHabilitar, String nome) {
       this.obrigatorio = obrigatorio;
       this.sql = sql;
       this.nome = nome;
       this.podeHabilitar = podeHabilitar;
       setLayout(new FlowLayout());
       add(jcb);
       add(jb);
       
       preencher();
     
       jb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    Method metodoGetTela = tela.getMethod("getTela", new Class[]{});
                    metodoGetTela.invoke(null, new Object[]{});
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
      
        
       jcb.addFocusListener(new FocusAdapter() {
        @Override
        public void focusGained(FocusEvent fe) {
            preencher();
        }
     });
       
      
       
  }

  public boolean estaPreenchendo() {
      return preenchendo;
  }
   
   public void preencher()  {
        
         
    try {
            preenchendo = true;
            jcb.removeAllItems();
            jcb.addItem("SELECIONE");
            pks = new ArrayList();
            pks.add(-1);
            List<String[]> dados = Conexao.executaQuery(sql);
            ResultSet rs = Conexao.getConexao().createStatement().executeQuery(sql);
            while (rs.next()) {
                 
                pks.add(rs.getInt(1));
                jcb.addItem(rs.getString(2));
                
            }
            preenchendo = false;
                       
        } catch (Exception e) {
            
            JOptionPane.showMessageDialog(null, "Não foi possível preencher o MeuCampoDBComboBox");
            e.printStackTrace();
            return;
         }
         

   }
      
    @Override
    public boolean eObrigatorio() {
        return obrigatorio;
    }

    @Override
    public boolean eValido() {
        return true;
    }

    @Override
    public boolean eVazio() {
        return(jcb.getSelectedIndex() < 0);
    }

    @Override
    public void limpar() {
        jcb.setSelectedIndex(0);
    }

    @Override
    public void habilitar(boolean status) {
        jcb.setEnabled(podeHabilitar && status);
        jb.setEnabled( podeHabilitar && status);
       
    }

    @Override
    public Object getValor() {
      
      return pks.get(jcb.getSelectedIndex());
      
    }
        
    @Override
    public void setValor(Object valor) {
         
        for (int i = 0; i < pks.size(); i++) {
           
             if (pks.get(i) == (int)valor) {
                jcb.setSelectedIndex(i);
               
               return; 
            }
             
        }
        
 }
    
    @Override
    public String getNome() {
        return nome;
    }

     public String getValorTexto() {
        return (String) jcb.getSelectedItem();
    }
    
    public void addItemListener(ItemListener il) {
        jcb.addItemListener(il);
    }

}