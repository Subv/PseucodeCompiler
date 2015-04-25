/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

import compiladorpseu.Tokens.Token;
import correctorcodigodoc.Lexer;
import correctorcodigodoc.Parser;
import correctorcodigodoc.Parser.ParseException;
import parser.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import parser.ParserStatement;

/**
 *
 * @author Sebastian
 */
public class ParserTest {
    
    public ParserTest() {
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
    public void TestDeclaracion() throws ParseException {
        Lexer lexer = new Lexer("ENTERO j \n ENTERO i \n ENTERO j, w \n ENTERO B(100, 99) \n ENTERO L(B(1,2), 3)");
        Parser parser = new Parser(lexer);
        ParserStatement statement = parser.parseStatement();
        assertEquals(true, statement instanceof DeclarationStatement);
        assertEquals(1, ((DeclarationStatement)statement).getIdentifiers().size());
        assertEquals("j", ((DeclarationStatement)statement).getIdentifiers().get(0).getNombre());

        statement = parser.parseStatement();
        assertEquals(true, statement instanceof NullStatement);

        statement = parser.parseStatement();
        assertEquals(true, statement instanceof DeclarationStatement);
        assertEquals(1, ((DeclarationStatement)statement).getIdentifiers().size());
        assertEquals("i", ((DeclarationStatement)statement).getIdentifiers().get(0).getNombre());

        statement = parser.parseStatement();
        assertEquals(true, statement instanceof NullStatement);

        statement = parser.parseStatement();
        assertEquals(true, statement instanceof DeclarationStatement);
        assertEquals(2, ((DeclarationStatement)statement).getIdentifiers().size());
        assertEquals("j", ((DeclarationStatement)statement).getIdentifiers().get(0).getNombre());
        assertEquals("w", ((DeclarationStatement)statement).getIdentifiers().get(1).getNombre());

        statement = parser.parseStatement();
        assertEquals(true, statement instanceof NullStatement);

        statement = parser.parseStatement();
        assertEquals(true, statement instanceof DeclarationStatement);
        assertEquals(1, ((DeclarationStatement)statement).getIdentifiers().size());
        assertEquals("B", ((DeclarationStatement)statement).getIdentifiers().get(0).getNombre());
        assertEquals(2, ((DeclarationStatement)statement).getIdentifiers().get(0).getParameters().size());
        assertEquals(1, ((DeclarationStatement)statement).getIdentifiers().get(0).getParameters().get(0).getTokens().size());
        assertEquals(1, ((DeclarationStatement)statement).getIdentifiers().get(0).getParameters().get(1).getTokens().size());
        assertEquals(true, ((Identifier)((DeclarationStatement)statement).getIdentifiers().get(0).getParameters().get(0).getTokens().get(0)).isConstant());
        assertEquals(true, ((Identifier)((DeclarationStatement)statement).getIdentifiers().get(0).getParameters().get(1).getTokens().get(0)).isConstant());
        assertEquals("100", ((Identifier)((DeclarationStatement)statement).getIdentifiers().get(0).getParameters().get(0).getTokens().get(0)).getNombre());
        assertEquals("99", ((Identifier)((DeclarationStatement)statement).getIdentifiers().get(0).getParameters().get(1).getTokens().get(0)).getNombre());

        statement = parser.parseStatement();
        assertEquals(true, statement instanceof NullStatement);

        statement = parser.parseStatement();
        assertEquals(true, statement instanceof DeclarationStatement);
        assertEquals(1, ((DeclarationStatement)statement).getIdentifiers().size());
        assertEquals("L", ((DeclarationStatement)statement).getIdentifiers().get(0).getNombre());
        assertEquals(2, ((DeclarationStatement)statement).getIdentifiers().get(0).getParameters().size());

        assertEquals(1, ((DeclarationStatement)statement).getIdentifiers().get(0).getParameters().get(0).getTokens().size());
        assertEquals(false, ((Identifier)((DeclarationStatement)statement).getIdentifiers().get(0).getParameters().get(0).getTokens().get(0)).isConstant());
        assertEquals("B", ((Identifier)((DeclarationStatement)statement).getIdentifiers().get(0).getParameters().get(0).getTokens().get(0)).getNombre());
        Identifier B = (Identifier)((DeclarationStatement)statement).getIdentifiers().get(0).getParameters().get(0).getTokens().get(0);
        assertEquals(2, B.getParameters().size());
        assertEquals(1, B.getParameters().get(0).getTokens().size());
        assertEquals("1", ((Identifier)B.getParameters().get(0).getTokens().get(0)).getNombre());
        assertEquals(1, B.getParameters().get(1).getTokens().size());
        assertEquals("2", ((Identifier)B.getParameters().get(1).getTokens().get(0)).getNombre());

        assertEquals(1, ((DeclarationStatement)statement).getIdentifiers().get(0).getParameters().get(1).getTokens().size());
        assertEquals(true, ((Identifier)((DeclarationStatement)statement).getIdentifiers().get(0).getParameters().get(1).getTokens().get(0)).isConstant());
        assertEquals("3", ((Identifier)((DeclarationStatement)statement).getIdentifiers().get(0).getParameters().get(1).getTokens().get(0)).getNombre());
    }
}
