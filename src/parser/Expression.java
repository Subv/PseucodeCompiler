/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

import compiladorpseu.Tokens.Token;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Sebastian
 */
public class Expression {
    private List<Object> tokens = new ArrayList<Object>();
    
    public Expression() {
        
    }
    
    public void add(Object obj) {
        tokens.add(obj);
    }
    
    public boolean isEmpty() {
        return tokens.isEmpty();
    }
    
    public List<Object> getTokens() {
        return tokens;
    }
}
