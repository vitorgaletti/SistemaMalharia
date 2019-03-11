package componente;

import javax.swing.JOptionPane;
import javax.swing.text.MaskFormatter;

public class MeuCampoCPFCNPJ extends MeuCampoFormatado2 {

    public MeuCampoCPFCNPJ(int tamanho, boolean obrigatorio, String nome, boolean podeHabilitar) {
        super(tamanho, obrigatorio, nome, podeHabilitar);
        try {
            MaskFormatter mf = new MaskFormatter("###.###.###-##");
            mf.install(this);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Não foi possível cria o campo CPF!");
        }
    }

    @Override
    public boolean eValido() {
        if (eVazio()) {
             return true;
        }
         boolean retorno = false;
        if (getText().indexOf("/") != -1) {    
             
               return validaCNPJ(getText());
        } else { 
            
            return validaCPF(getText());
        }
    }
      
    public boolean validaCPF(String pega_valor) {
        if (!pega_valor.substring(0, 1).equals("")) {  // metodo subString (0,1) casa 0 , ou seja , começa a partir dessa casa e  que determina que ele pega a casa 1
            //JOptionPane.showMessageDialog(null, ""+pega_valor.substring(0,1));
            try {
                int d1, d2;
                int digito1, digito2, resto;
                int digitoCPF;
                String DigResultado;
                pega_valor = pega_valor.replace('.', ' ');
                // tira os pontos e poem espaço
                pega_valor = pega_valor.replace('-', ' ');
                pega_valor = pega_valor.replaceAll(" ", "");
                 
            if (pega_valor.equals("11111111111")
                    || pega_valor.equals("22222222222")
                    || pega_valor.equals("33333333333")
                    || pega_valor.equals("44444444444")
                    || pega_valor.equals("55555555555")
                    || pega_valor.equals("66666666666")
                    || pega_valor.equals("77777777777")
                    || pega_valor.equals("88888888888")
                    || pega_valor.equals("99999999999")
                    || pega_valor.equals("00000000000")) {
                 
                return false;
            } else {
                
                d1 = d2 = 0;

                digito1 = digito2 = resto = 0;

                //085564619
                for (int nCount = 1; nCount < pega_valor.length() - 1; nCount++) {
                    digitoCPF = Integer.valueOf(pega_valor.substring(nCount - 1, nCount)).intValue();
                    //0                                              //1 -1 = 0 , 1 (0,1) .. (1,2) .. (2,3)
                    //multiplique a ultima casa por 2 a seguinte por 3 a seguinte por 4 e assim por diante.     
                    d1 = d1 + (11 - nCount) * digitoCPF;
                    // 0 = 0 + (11 - 1) * 0 primeiro digito
                    //248

                    //para o segundo digito repita o procedimento incluindo o primeiro digito calculado no passo anterior.     
                    d2 = d2 + (12 - nCount) * digitoCPF;
                    //248
                    
                }
                    
                //Primeiro resto da divisão por 11.     
                resto = (d1 % 11);
                        //248 / 11 = 22 = 242 >> 248-242 = 6 >> resto

                //Se o resultado for 0 ou 1 o digito é 0 caso contrário o digito é 11 menos o resultado anterior.     
                if (resto < 2) {
                    digito1 = 0;
                } else {
                    digito1 = 11 - resto;
                    //6 = 5  
                }

                // primeiro digito verificador multiplicado por 2, que depois é somado a d2
                d2 += 2 * digito1;
                // adiciona ao d2 o primeiro digito verificador (5)

                //Segundo resto da divisão por 11.     
                resto = (d2 % 11);
                        //302  / 11 = 297 >> 302-297 = 5 >> resto

                //Se o resultado for 0 ou 1 o digito é 0 caso contrário o digito é 11 menos o resultado anterior.     
                if (resto < 2) {
                    digito2 = 0;
                } else {
                    digito2 = 11 - resto;
                    // 5 = 6
                }

                //Digito verificador do CPF que está sendo validado.     
                String DigVerificador = pega_valor.substring(pega_valor.length() - 2, pega_valor.length());
                                                            // 11-2 = 9 , a partir da casa 10 ,ou seja , pega o que sobrou , que no caso foi 56
                //JOptionPane.showMessageDialog(null, ""+DigVerificador);

                //Concatenando o primeiro resto com o segundo.     
                DigResultado = String.valueOf(digito1) + String.valueOf(digito2);
                                                //5                         //6

              //comparar o digito verificador do cpf com o primeiro resto + o segundo resto.     
                return DigVerificador.equals(DigResultado);
            }    //56                    //56 retorna true ; o CPF é válido
            } catch (Exception e) {
                System.err.println("Erro !" + e);
                return false;
            }
        } else {
            return false;

        }

    }
         
    public boolean validaCNPJ(String s_aux) {
//------- Rotina para CPF
             
        if (s_aux.replace(".", "").replace("/", "").replace("-", "").length() == 14) {
            
            String campoTexto = s_aux.replace(".", "").replace("/", "").replace("-", "");
            if (campoTexto.equals("11111111111111")
                    || campoTexto.equals("22222222222222")
                    || campoTexto.equals("33333333333333")
                    || campoTexto.equals("44444444444444")
                    || campoTexto.equals("55555555555555")
                    || campoTexto.equals("66666666666666")
                    || campoTexto.equals("77777777777777")
                    || campoTexto.equals("88888888888888")
                    || campoTexto.equals("99999999999999")
                    || campoTexto.equals("00000000000000")) {
                return false;
            } else {
                String str_cnpj = campoTexto;
                int soma = 0, aux, dig;
                String cnpj_calc = str_cnpj.substring(0, 12);
                if (str_cnpj.length() != 14) {
                    return false;
                }
                char[] chr_cnpj = str_cnpj.toCharArray();
                /* Primeira parte */
                for (int i = 0; i < 4; i++) {

                    if (chr_cnpj[i] - 48 >= 0 && chr_cnpj[i] - 48 <= 9) {
                        soma += (chr_cnpj[i] - 48) * (6 - (i + 1));
                    }
                }
                for (int i = 0; i < 8; i++) {

                    if (chr_cnpj[i + 4] - 48 >= 0 && chr_cnpj[i + 4] - 48 <= 9) {
                        soma += (chr_cnpj[i + 4] - 48) * (10 - (i + 1));
                    }
                }
                dig = 11 - (soma % 11);
                cnpj_calc += (dig == 10 || dig == 11)
                        ? "0" : Integer.toString(dig);
                /* Segunda parte */
                soma = 0;
                for (int i = 0; i
                        < 5; i++) {
                    if (chr_cnpj[i] - 48 >= 0 && chr_cnpj[i] - 48 <= 9) {
                        soma += (chr_cnpj[i] - 48) * (7 - (i + 1));
                    }
                }
                for (int i = 0; i
                        < 8; i++) {
                    if (chr_cnpj[i + 5] - 48 >= 0 && chr_cnpj[i + 5] - 48 <= 9) {
                        soma += (chr_cnpj[i + 5] - 48) * (10 - (i + 1));
                    }
                }
                dig = 11 - (soma % 11);
                cnpj_calc += (dig == 10 || dig == 11)
                        ? "0" : Integer.toString(dig);
                return str_cnpj.equals(cnpj_calc);
            }
        } else {
            return false;
        }

    }

    @Override
    public boolean eVazio() {
        boolean retorno = false;
        if (getText().equals("   .   .   -  ") | getText().equals("  .   .   /    -  ")) {
            retorno = true;
        }
        return retorno;

//return getText().replace(".","").replace("-", "").replace("/","").trim().isEmpty();
    }
}


