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
    
    public Identifier(String nombre, List<Expression> parameters) {
        this.parameters = parameters;
        this.nombre = nombre;
    }
}
