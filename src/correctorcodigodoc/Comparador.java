package correctorcodigodoc;

import compiladorpseu.Tokens.Token;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JOptionPane;

/**
 * @author Sebastian
 * Compara dos textos algorítmicos teniendo en cuenta ciertas equivalencias 
 * entre palabras clave
 */
public class Comparador {
    public static class Error {
        private String texto, patron;
        private int linea, palabra;

        public String getTexto() {
            return texto;
        }

        public String getPatron() {
            return patron;
        }

        public int getLinea() {
            return linea;
        }

        public int getPalabra() {
            return palabra;
        }
        
        public Error(String texto, String patron, int linea, int palabra) {
            this.texto = texto;
            this.patron = patron;
            this.linea = linea;
            this.palabra = palabra;
        }
    }
    
    private String patron;
    private String texto;
    private Lexer lexer_patron;
    private Lexer lexer_texto;
    
    private static final HashMap<String, ArrayList<String>> Equivalencias;
    static {
        // Inicializador estático del mapa de equivalencias
        Equivalencias = new HashMap<String, ArrayList<String>>();
        
        ArrayList<String> equivs = new ArrayList<String>();
        
        // Equivalencias para "Entero"
        equivs.add("entero");
        equivs.add("enteros");
        Equivalencias.put("entero", (ArrayList<String>)equivs.clone());
        
        equivs.clear();
        
        // Equivalencias para "Escriba"
        equivs.add("escriba");
        equivs.add("escribir");
        equivs.add("escribe");
        Equivalencias.put("escriba", (ArrayList<String>)equivs.clone());
        
        equivs.clear();
   
        // Equivalencias para "Lea"
        equivs.add("lea");
        equivs.add("leo");
        equivs.add("leer");
        Equivalencias.put("lea", (ArrayList<String>)equivs.clone());
        
        equivs.clear();
        
        // Equivalencias para Mientras_que
        equivs.add("Mq");
        equivs.add("Mientras_Que");
        equivs.add("M_Q");
        Equivalencias.put("Mq", (ArrayList<String>)equivs.clone());
        
        equivs.clear();
        
        // Equivalencias para "haga"
        equivs.add("haga");
        equivs.add("hacer");
        Equivalencias.put("haga", (ArrayList<String>)equivs.clone());
        
        equivs.clear();
        
        // Equivalencias para "fin_mientras_que"
        equivs.add("fin_mientras_que");
        equivs.add("fin_mq");
        equivs.add("fmq");
        equivs.add("f_mq");
        equivs.add("f_m_q");
        Equivalencias.put("fin_mq", (ArrayList<String>)equivs.clone());
        
        equivs.clear();
        
        // Equivalencias para "escriba"
        equivs.add("escriba");
        equivs.add("escribir");
        equivs.add("escribe");
        Equivalencias.put("escriba", (ArrayList<String>)equivs.clone());
        
        equivs.clear();
        
        // Equivalencias para "Fin_Para"
        equivs.add("Fin_Para");
        equivs.add("F_Para");
        equivs.add("Fin_P");
        equivs.add("FP");
        equivs.add("F_P");
        Equivalencias.put("fin_para", (ArrayList<String>)equivs.clone());
        
        equivs.clear();
        
        // Equivalencias para "Haga_hasta"
        equivs.add("haga_hasta");
        equivs.add("hacer_hasta");
        equivs.add("HH");
        equivs.add("H_H");
        Equivalencias.put("haga_hasta", (ArrayList<String>)equivs.clone());
        
        equivs.clear();
        
        // Equivalencias para "Fin_HH"
        equivs.add("Fin_HH");
        equivs.add("Fin_HacerHasta");
        equivs.add("Fin_Hacer_hasta");
        equivs.add("F_HH");
        equivs.add("F_HacerHasta");
        equivs.add("F_Hacer_Hasta");
        Equivalencias.put("fin_hh", (ArrayList<String>)equivs.clone());
        
        equivs.clear();
    }
    
    public Comparador(String patron, String texto) {
        this.patron = patron;
        this.texto = texto;
        this.lexer_patron = new Lexer(patron);
        this.lexer_texto = new Lexer(texto);
    }
    
    public ArrayList<Error> CompararLexer() {
        ArrayList<Error> errores_detectados = new ArrayList<Error>();
        
        ArrayList<Token> tokens_patron = new ArrayList<Token>();
        ArrayList<Token> tokens_texto = new ArrayList<Token>();
        
        Token tk;
        while ((tk = lexer_patron.nextToken()) != null)
            tokens_patron.add(tk);
        
        while ((tk = lexer_texto.nextToken()) != null)
            tokens_texto.add(tk);
        
        int linea = 0;
        int caracteres_contados = 0;
        for (int i = 0, j = 0; i < tokens_patron.size() && j < tokens_texto.size(); ++i, ++j) {
            Token token_patron = tokens_patron.get(i);
            Token token_texto = tokens_texto.get(j);
            
            if (token_texto.id == Token.Ids.NUEVA_LINEA) {
                linea += 1;
            }
            
            // Si no son la misma estructura, hay un error
            if (token_patron.id != token_texto.id) {
                errores_detectados.add(new Error(token_texto.texto, token_patron.texto, linea, 0));
            } else {
                // Verifica si el contenido de los tokens es equivalente
                if (!token_patron.texto.trim().toLowerCase().equals(token_texto.texto.trim().toLowerCase())) {
                    // @TODO
                    errores_detectados.add(new Error(token_texto.texto, token_patron.texto, linea, 0));
                }
            }
        }
        
        return errores_detectados;
    }
    
    public ArrayList<Error> CompararCaracteres() {
        ArrayList<Error> errores_detectados = new ArrayList<Error>();
        
        String[] lineas_patron = patron.split("\n");
        String[] lineas_texto = texto.split("\n");
        
        // Compara linea por linea los textos
        for (int i = 0; i < lineas_patron.length && i < lineas_texto.length; ++i) {
           
            String linea_patron = lineas_patron[i].trim().toLowerCase();
            String linea_texto = lineas_texto[i].trim().toLowerCase();
            
            if (!linea_patron.equals(linea_texto)) {
                // Si las lineas no son iguales, busca la diferencia palabra por palabra
                
                char[] palabras_patron = linea_patron.replace(" ", "").toCharArray();
                char[] palabras_texto = linea_texto.replace(" ", "").toCharArray();
                
                for (int j = 0; j < palabras_patron.length && j < palabras_texto.length; ++j) {
                    String palabra_patron = String.valueOf(palabras_patron[j]).toLowerCase();
                    String palabra_texto = String.valueOf(palabras_texto[j]).toLowerCase();
                    
                    // Detecta solo un error en cada línea para prevenir errores falsos por caracteres extra
                    if (!palabra_patron.equals(palabra_texto)) {
                        errores_detectados.add(new Error(palabra_texto, palabra_patron, i, j));
                        break;
                    }
                }
            }
        }
        
        return FiltrarErrores(errores_detectados);
    }
    
    /*
     * Filtra los errores detectados en el paso preliminar, omitiendo errores simples, 
     * como "Leo" en vez de "Lea", etc
     * @param preliminares Los errores detectados en la fase de detección preliminar
     * @returns Un ArrayList con los errores filtrados
     */
    public ArrayList<Error> FiltrarErrores(ArrayList<Error> preliminares) {
       ArrayList<Error> filtrados = new ArrayList<Error>();
       
       for (Error preliminar : preliminares) {
           // Si no existe una equivalencia para esta palabra, no podemos hacer nada, 
           // el error es legítimo
           if (!TieneEquivalencia(preliminar.patron)) {
               filtrados.add(preliminar);
               continue;
           }
           
           // Si existe al menos una equivalencia, 
           // buscamos si la palabra del estudiante está entre las equivalencias
           if (!EsEquivalente(preliminar.patron, preliminar.texto)) {
               // Si las palabras son equivalentes, ignoramos el error,
               // si no lo son, entonces el error es legítimo.
               filtrados.add(preliminar);
           }
       }
       
       return filtrados;
    }
    
    /*
     * Determina si la palabra clave tiene alguna lista de equivalencias
     */
    public boolean TieneEquivalencia(String patron) {
        return Equivalencias.containsKey(patron);
    }
    
    /*
     * Determina si el texto es equivalente a la palabra clave basado en la 
     * lista de equivalencia
     */
    public boolean EsEquivalente(String patron, String texto) {
        for (String eq : Equivalencias.get(patron)) {
            if (eq.trim().toLowerCase().equals(texto.trim().toLowerCase()))
                return true;
        }
        
        return false;
    }
}
