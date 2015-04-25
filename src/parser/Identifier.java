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
public class Identifier {
    private List<Expression> parameters;
    private String nombre;
    private boolean constant;
    
    public Identifier(String nombre, List<Expression> parameters, boolean constant) {
        this.parameters = parameters;
        this.nombre = nombre;
        this.constant = constant;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public List<Expression> getParameters() {
        return parameters;
    }
    
    public boolean isConstant() {
        return constant;
    }
}
