/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

import java.util.List;

/**
 *
 * @author Sebastian
 */
public class OutputStatement extends ParserStatement {
    private List<Expression> expressions;
    
    public OutputStatement(List<Expression> expressions) {
        this.expressions = expressions;
    }
}
