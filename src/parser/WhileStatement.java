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
public class WhileStatement extends ParserStatement {
    private List<ParserStatement> body;
    private Expression condition;
    
    public WhileStatement(Expression condition, List<ParserStatement> body) {
        this.body = body;
        this.condition = condition;
    }
}
