package principal;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.XMLFormatter;

import logs.FiltroLogSoloMultiplicar;
import logs.FiltroResultadosNulos;
import logs.FormatoHTML;
import logs.FormatoResultadosNulos;
import logs.LogConsolaMasFichero;
import menu.Menu;
import operaciones.Operaciones;


public class Calculadora{
	private static final LogManager logManager = LogManager.getLogManager();
	private static final Logger LOGGER = Logger.getLogger(Calculadora.class.getName());
	static{
		 try {
			 logManager.readConfiguration(new FileInputStream("./logs/configLog.properties"));
		 } catch (IOException exception) {
			 LOGGER.log(Level.SEVERE, "Error al cargar la configuración",exception);
		 }
	}
    public static void main(String[] args) { 
    	
    	configurarLog();
    	
        int resultado = 0;
        String operacion = "";
        int[] operandos = new int [2];
        
        Menu menu = new Menu();
        Operaciones operaciones = new Operaciones();
        
        do{
            operandos = menu.pedirNumeros();
            operacion = menu.menuOpciones();
            
            try {
            
            	if (operacion.equalsIgnoreCase("+")){
            		resultado = operaciones.sumar(operandos[0], operandos[1]);
            		System.out.println ("Resultado: " + resultado);
            	} else if (operacion.equalsIgnoreCase("-")){
            		resultado = operaciones.restar(operandos[0], operandos[1]);
            		System.out.println ("Resultado: " + resultado);
            	} else if (operacion.equalsIgnoreCase("*")){
            		resultado = operaciones.multiplicar(operandos[0], operandos[1]);
            		System.out.println ("Resultado: " + resultado);
            	} else if (operacion.equalsIgnoreCase("/")){
            		resultado = operaciones.dividir(operandos[0], operandos[1]);
            		System.out.println ("Resultado: " + resultado);
            	} else if (operacion.equalsIgnoreCase("%")){
            		resultado = operaciones.resto(operandos[0], operandos[1]);
            		System.out.println ("Resultado: " + resultado);
            	} else {
            		System.out.println ("Operación no válida");
            	}
            	//LOGGER.log(Level.FINE, "Operación: " + operacion + " | operando 1: " + operandos[0] + " | operando 2: " + operandos[1] + " | Resultado: " + resultado);
            	String cadenaLog = "Operación: " + operacion + " | operando 1: " + operandos[0] + " | operando 2: " + operandos[1] + " | Resultado: " + resultado;
            	LOGGER.log(Level.FINE, cadenaLog);
            }catch(ArithmeticException e) {
        	System.out.println("Operaciones aritmeticas no validas "+ e.getMessage());
        	LOGGER.log(Level.WARNING, "Ha ocurrido un ArithmeticException." ,e);
            }
        }   while (menu.repetir());
        
    }
    
    public static void configurarLog() {
    	
    	//LOGGER.setUseParentHandlers(false);
    	
        Handler fileHandler  = null;
        //Handler consoleHandler  = new ConsoleHandler();
        
        //LOGGER.addHandler(consoleHandler);
        
        try {
        	fileHandler  = new FileHandler("./logs/resultadosNulos.html");
        }catch (IOException exception){
            LOGGER.log(Level.SEVERE, "Ocurrió un error en FileHandler.", exception);
        }
        fileHandler.setFormatter(new FormatoResultadosNulos());
        
        LOGGER.addHandler(fileHandler);
        
        //consoleHandler.setLevel(Level.WARNING);
        fileHandler.setLevel(Level.FINE);
        
        fileHandler.setFilter(new FiltroResultadosNulos());
        //LOGGER.setLevel(Level.FINE);
    }
    
}