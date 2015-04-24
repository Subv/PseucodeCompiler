package correctorcodigodoc;

import compiladorpseu.Tokens.Token;
import java.util.HashMap;
import javax.swing.JOptionPane;

/**
 *
 * @author Sebastian
 */
public class Lexer {
    private char[] codigo;
    private char ultimo_caracter;
    private Token ultimo_token;
    private int lex_index;
    private int lex_linea;
    
    private final static HashMap<String, Token.Ids> TiposToken;
    private final static HashMap<Character, Token.Ids> TokensAtomicos;
    static {
        TiposToken = new HashMap<String, Token.Ids>();
        TokensAtomicos = new HashMap<Character, Token.Ids>();
        
        TokensAtomicos.put('(', Token.Ids.PARENTESIS_ABRE);
        TokensAtomicos.put(')', Token.Ids.PARENTESIS_CIERRA);
        TokensAtomicos.put(',', Token.Ids.COMA);
        TokensAtomicos.put('\n', Token.Ids.NUEVA_LINEA);
        TokensAtomicos.put('←', Token.Ids.ASIGNACION);
        
        if (TokensAtomicos.containsKey(' ')) {
            JOptionPane.showMessageDialog(null, "ye");
        }
        
        TiposToken.put("lea", Token.Ids.LEA);
        TiposToken.put("leo", Token.Ids.LEA);
        TiposToken.put("leer", Token.Ids.LEA);
        
        TiposToken.put("escriba", Token.Ids.ESCRIBA);
        TiposToken.put("escribe", Token.Ids.ESCRIBA);
        TiposToken.put("escribir", Token.Ids.ESCRIBA);
        TiposToken.put("escribo", Token.Ids.ESCRIBA);
        
        TiposToken.put("inicio", Token.Ids.INICIO);
        TiposToken.put("fin", Token.Ids.FIN);
        TiposToken.put("mientras_que", Token.Ids.MIENTRAS_QUE);
        TiposToken.put("fin_mientras_que", Token.Ids.FIN_MIENTRAS_QUE);
        TiposToken.put("haga", Token.Ids.HAGA);
        TiposToken.put("si", Token.Ids.SI);
        TiposToken.put("entonces", Token.Ids.ENTONCES);
        TiposToken.put("fin_si", Token.Ids.FIN_SI);
        TiposToken.put("para", Token.Ids.PARA);
        TiposToken.put("fin_para", Token.Ids.FIN_PARA);
        
        TiposToken.put("entero", Token.Ids.TIPO_DATO);
        TiposToken.put("enteros", Token.Ids.TIPO_DATO);
        
        TiposToken.put("real", Token.Ids.TIPO_DATO);
        TiposToken.put("reales", Token.Ids.TIPO_DATO);
    }
    
    public Lexer(String program) {
        this.codigo = program.toCharArray();
        this.ultimo_caracter = ' ';
        this.lex_index = 0;
        this.ultimo_token = null;
        this.lex_linea = 0;
    }
    
    private Token nextTokenInternal() {
        if (!hasNextChar())
            return null;
        
        // Salta los espacios en blanco
        while (ultimo_caracter == ' ')
            ultimo_caracter = getChar();
        
        // Si encontramos un comentario, salta a la siguiente linea
        if (ultimo_caracter == '/') {
            if (hasNextChar() && peekChar() == '/') {
                eatUntilNextLine();
                // Devuelve un token de nueva linea
                return new Token(Token.Ids.NUEVA_LINEA, "\n");
            }
        }
        
        // Identifica los casos especiales , ( ) ← \n
        if (TokensAtomicos.containsKey(ultimo_caracter)) {
            Token ret = new Token(TokensAtomicos.get(ultimo_caracter), String.valueOf(ultimo_caracter));
            eatNextChar();
            return ret;
        }
        
        // Parsea una palabra vorazmente
        if (Character.isLetter(ultimo_caracter)) {
            String identificador = String.valueOf(ultimo_caracter);
            while (hasNextChar() && Character.isLetterOrDigit(ultimo_caracter = getChar()))
                identificador += ultimo_caracter;
            
            // Verifica si la palabra es una keyword o una variable
            if (TiposToken.containsKey(identificador.toLowerCase())) {
                return new Token(TiposToken.get(identificador.toLowerCase()), identificador);
            } else {
                return new Token(Token.Ids.IDENTIFIER, identificador);
            }
        } else {
            // Parsea el menos unario y binario
            if (ultimo_caracter == '-') {
                // Si el token anterior era un número o una variable, tenemos un menos binario
                if (ultimo_token != null && (ultimo_token.id == Token.Ids.NUMERO || ultimo_token.id == Token.Ids.IDENTIFIER)) {
                    // Nos comemos el -
                    eatNextChar();
                    return new Token(Token.Ids.MENOS_BINARIO, "-");
                }
                
                // De lo contrario tenemos un menos unario, que es un número, fallback al siguiente caso
            }
            
            // Parsea un número vorazmente
            if (Character.isDigit(ultimo_caracter) || ultimo_caracter == '-') {
                String numero = "";
                do {
                    numero += ultimo_caracter;
                    ultimo_caracter = getChar();
                } while (hasNextChar() && (Character.isDigit(ultimo_caracter) || ultimo_caracter == '.'));
                
                return new Token(Token.Ids.NUMERO, numero);
            }
        }
        
        if (hasNextChar())
            eatNextChar();
        
        return new Token(Token.Ids.UNKNOWN, "");
    }
    
    public Token nextToken() {
        Token next_token = nextTokenInternal();
        ultimo_token = next_token;
        return next_token;
    }
    
    private char getChar() {
        return codigo[lex_index++];
    }
    
    private boolean hasNextChar() {
        return lex_index < codigo.length;
    }
    
    private void eatNextChar() {
        ultimo_caracter = getChar();
    }
    
    private char peekChar() {
        return codigo[lex_index];
    }
    
    private void eatUntilNextLine() {
        while (hasNextChar() && getChar() != '\n') {}
        eatNextChar();
    }
}