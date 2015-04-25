/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

import compiladorpseu.Tokens.Token;
import java.util.List;

/**
 *
 * @author Sebastian
 */
public class Identifier {
    private List<Expression> parameters;
    private Token token;
    private boolean constant;
    
    public Identifier(Token token, List<Expression> parameters, boolean constant) {
        this.parameters = parameters;
        this.token = token;
        this.constant = constant;
    }
    
    public String getNombre() {
        return token.texto;
    }
    
    public List<Expression> getParameters() {
        return parameters;
    }
    
    public boolean isConstant() {
        return constant;
    }
}
