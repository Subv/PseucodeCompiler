/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package correctorcodigodoc;

import parser.ParserStatement;
import parser.DeclarationStatement;
import parser.NullStatement;
import compiladorpseu.Tokens.Token;
import java.util.ArrayList;
import java.util.List;
import parser.Expression;
import parser.Identifier;

/**
 *
 * @author Sebastian
 */
public class Parser {
    public static class ParseException extends Exception {
        public String message;
        
        public ParseException(String what) {
            this.message = what;
        }
    }
    
    private Lexer lexer;
    private Token current_token;
    
    public Parser(Lexer lexer) {
        this.lexer = lexer;
        this.current_token = lexer.nextToken();
    }
    
    public ParserStatement parseStatement() throws ParseException {
        if (current_token == null)
            return null;
        
        Token next_token = lexer.nextToken();
        ParserStatement statement = null;
        
        switch (current_token.id) {
            case INICIO: // Nada que hacer
            case PROC: // Nada que hacer
            case FIN: // Nada que hacer
            case NUEVA_LINEA: // Nada que hacer
            case DOS_PUNTOS:
                statement = new NullStatement();
                break;
                
            case TIPO_DATO:
                // El siguiente token debe ser un identificador o una lista
                List<Identifier> tokens = parseIdentifierList(next_token);
                if (tokens.isEmpty()) {
                    throw new ParseException("Error de sintaxis en la lista de identificadores");
                }
                statement = new DeclarationStatement(tokens);
                
                // Ya nos comimos el último token, por tanto tenemos que retroceder una posición
                next_token = lexer.prevToken();
                break;
        }
        
        current_token = next_token;
        return statement;
    }
    
    /*
     * Parsea una lista de identificadores de la forma I1,I2,I3,I4...In
     * Tira una excepción si la lista está malformada
     * @param token_inicial El token que inicia la lista
     * @returns Lista de identificadores parseada
     */
    public List<Identifier> parseIdentifierList(Token token_inicial) throws ParseException {
        List<Identifier> ret = new ArrayList<Identifier>();
        
        Token token = token_inicial;
        
        while (token.id == Token.Ids.IDENTIFIER) {
            ret.add(parseIdentifier(token));
            
            token = lexer.nextToken();
            
            // La lista termina con una nueva linea
            if (token.id == Token.Ids.NUEVA_LINEA)
                break;
            
            // Si lo que sigue no es una coma, tenemos un error de sintaxis
            if (token.id != Token.Ids.COMA) {
                throw new ParseException("Error de sintaxis en la lista de identificadores");
            }
            
            token = lexer.nextToken();
        }
        
        return ret;
    }
    
    /*
     * Parsea un identifier a partir de un token
     * el identifier puede ser un array o una variable normal
     * @param token_inicial El token por el que comienza el identifier
     * @returns Identifier parseado
     */
    public Identifier parseIdentifier(Token token_inicial) throws ParseException {
        Token next_token = lexer.peekToken();
        
        if (next_token.id == Token.Ids.PARENTESIS_ABRE) {
            // El identificador es un arreglo, parsea los parametros hasta el (
            // Comete el (
            lexer.nextToken();
            List<Expression> parametros = parseExpressionList(lexer.nextToken(), Token.Ids.PARENTESIS_CIERRA);
            if (parametros.isEmpty()) {
                throw new ParseException("Error de sintaxis en la lista de identificadores");
            }
            return new Identifier(parametros);
        }
        
        return new Identifier(null);
    }
    
    public List<Expression> parseExpressionList(Token token_inicial, Token.Ids terminador) {
        return null;
    }
    
    public Expression parseExpression() {
        return null;
    }
}
