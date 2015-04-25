/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiladorpseu.Tokens;

/**
 *
 * @author Sebastian
 */
public class Token {

    public enum Ids {
        MIENTRAS_QUE, HAGA, FIN_MIENTRAS_QUE, HAGA_HASTA, FIN_HAGA_HASTA, PARA, FIN_PARA,
        
        SI, ENTONCES, FIN_SI,
        
        INICIO, FIN,
        
        LEA, ESCRIBA,
        
        IDENTIFIER,
        
        NUMERO, STRING,
        
        COMA,
                
        MENOS_BINARIO, MAS_BINARIO, MODULO, MENOR_QUE, MAYOR_QUE,
        
        PARENTESIS_ABRE, PARENTESIS_CIERRA,
        
        ASIGNACION,
        
        TIPO_DATO,
        
        NUEVA_LINEA,
        
        PROC, FIN_PROC,
        
        PUNTO, DOS_PUNTOS,
        
        UNKNOWN,
    }
    
    public Ids id;
    public String texto;
    public int lex_index;
    
    public Token(Ids id, String identificador, int lex_index) {
        this.id = id;
        this.texto = identificador;
        this.lex_index = lex_index - identificador.length();
    }
}
