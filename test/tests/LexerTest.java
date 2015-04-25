/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

import compiladorpseu.Tokens.Token;
import correctorcodigodoc.Lexer;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Sebastian
 */
public class LexerTest {
    
    public LexerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void TestNewLine() {
        Lexer lexer = new Lexer("\n");
        Token token = lexer.nextToken();
        assertEquals(token.id, Token.Ids.NUEVA_LINEA);
        assertEquals(token.texto, "\n");
    }
    
    @Test
    public void TestIdentifiers() {
        Lexer lexer = new Lexer("ID0 ID_1 _BAD_ID3 4BAD_ID_4");
        
        Token token = lexer.nextToken();
        assertEquals(token.id, Token.Ids.IDENTIFIER);
        assertEquals(token.texto, "ID0");
        
        token = lexer.nextToken();
        assertEquals(token.id, Token.Ids.IDENTIFIER);
        assertEquals(token.texto, "ID_1");
        
        token = lexer.nextToken();
        assertEquals(token.id, Token.Ids.UNKNOWN);
        assertEquals(token.texto, "_");
        
        token = lexer.nextToken();
        assertEquals(token.id, Token.Ids.IDENTIFIER);
        assertEquals(token.texto, "BAD_ID3");
        
        token = lexer.nextToken();
        assertEquals(token.id, Token.Ids.NUMERO);
        assertEquals(token.texto, "4");
        
        token = lexer.nextToken();
        assertEquals(token.id, Token.Ids.IDENTIFIER);
        assertEquals(token.texto, "BAD_ID_4");
    }
}
