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
        Lexer lexer = new Lexer("ID0 ID_1 __ID3 4BAD_ID_4");
        
        Token token = lexer.nextToken();
        assertEquals(Token.Ids.IDENTIFIER, token.id);
        assertEquals("ID0", token.texto);
        
        token = lexer.nextToken();
        assertEquals(Token.Ids.IDENTIFIER, token.id);
        assertEquals("ID_1", token.texto);
        
        token = lexer.nextToken();
        assertEquals(Token.Ids.IDENTIFIER, token.id);
        assertEquals("__ID3", token.texto);
        
        token = lexer.nextToken();
        assertEquals(Token.Ids.NUMERO, token.id);
        assertEquals("4", token.texto);
        
        token = lexer.nextToken();
        assertEquals(Token.Ids.IDENTIFIER, token.id);
        assertEquals("BAD_ID_4", token.texto);
    }
    
    @Test
    public void TestMenos() {
        Lexer lexer = new Lexer("65 135, -30-25, -653");
        
        Token token = lexer.nextToken();
        assertEquals(Token.Ids.NUMERO, token.id);
        assertEquals("65", token.texto);
        
        token = lexer.nextToken();
        assertEquals(Token.Ids.NUMERO, token.id);
        assertEquals("135", token.texto);
        
        token = lexer.nextToken();
        assertEquals(Token.Ids.COMA, token.id);
        assertEquals(",", token.texto);
        
        token = lexer.nextToken();
        assertEquals(Token.Ids.NUMERO, token.id);
        assertEquals("-30", token.texto);
        
        token = lexer.nextToken();
        assertEquals(Token.Ids.MENOS_BINARIO, token.id);
        assertEquals("-", token.texto);
        
        token = lexer.nextToken();
        assertEquals(Token.Ids.NUMERO, token.id);
        assertEquals("25", token.texto);
        
        token = lexer.nextToken();
        assertEquals(Token.Ids.COMA, token.id);
        assertEquals(",", token.texto);
        
        token = lexer.nextToken();
        assertEquals(Token.Ids.NUMERO, token.id);
        assertEquals("-653", token.texto);
        
        
        lexer = new Lexer("x-35, -x, -x-65");
        
        token = lexer.nextToken();
        assertEquals(Token.Ids.IDENTIFIER, token.id);
        assertEquals("x", token.texto);
        
        token = lexer.nextToken();
        assertEquals(Token.Ids.MENOS_BINARIO, token.id);
        assertEquals("-", token.texto);
        
        token = lexer.nextToken();
        assertEquals(Token.Ids.NUMERO, token.id);
        assertEquals("35", token.texto);
        
        token = lexer.nextToken();
        assertEquals(Token.Ids.COMA, token.id);
        assertEquals(",", token.texto);
        
        token = lexer.nextToken();
        assertEquals(Token.Ids.MENOS_UNARIO, token.id);
        assertEquals("-", token.texto);
        
        token = lexer.nextToken();
        assertEquals(Token.Ids.IDENTIFIER, token.id);
        assertEquals("x", token.texto);
        
        token = lexer.nextToken();
        assertEquals(Token.Ids.COMA, token.id);
        assertEquals(",", token.texto);
        
        token = lexer.nextToken();
        assertEquals(Token.Ids.MENOS_UNARIO, token.id);
        assertEquals("-", token.texto);
        
        token = lexer.nextToken();
        assertEquals(Token.Ids.IDENTIFIER, token.id);
        assertEquals("x", token.texto);
        
        token = lexer.nextToken();
        assertEquals(Token.Ids.MENOS_BINARIO, token.id);
        assertEquals("-", token.texto);
        
        token = lexer.nextToken();
        assertEquals(Token.Ids.NUMERO, token.id);
        assertEquals("65", token.texto);
    }
    
    @Test
    public void TestString() {
        Lexer lexer = new Lexer("'He\"llo \"the\"re!' \" OH'AI \"");
        
        Token token = lexer.nextToken();
        assertEquals(Token.Ids.STRING, token.id);
        assertEquals("He\"llo \"the\"re!", token.texto);
        
        token = lexer.nextToken();
        assertEquals(Token.Ids.STRING, token.id);
        assertEquals(" OH'AI ", token.texto);
    }
}
