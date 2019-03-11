package util;

import java.util.Date;

public class Utilitarios {
   public static java.sql.Date converteParaBanco(Date data) {
     
        return new java.sql.Date(data.getTime());
    }

    public static Date converteDoBanco(java.sql.Date data) {
            
        return new Date(data.getTime());
    }    
}