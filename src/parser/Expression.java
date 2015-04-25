/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

import compiladorpseu.Tokens.Token;
import java.util.ArrayList;

/**
 *
 * @author Sebastian
 */
public class Expression {
    private ArrayList<Token> tokens = new ArrayList<Token>();
    
    public Expression() {
        
    }
    
    public void addToken(Token token) {
        tokens.add(token);
    }
    
    public boolean isEmpty() {
        return tokens.isEmpty();
    }
}
