package juego;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

public class Arbitro {

	private TableroJuegoAritmetico jugador;
	private TableroJuegoAritmetico objetivo;
	private GuiJuego interfaceGrafica;
	private GuiFelicitacion interfaceFelicitadora;

	
	/**
	 *  Constructor del controlador
	 * @param interfaceGrafica DEbe recibir una interface gráfica
	 * a la que esté asociado para escuchar eventos
	 */
	public Arbitro(GuiJuego interfaceGrafica) {

		this.interfaceGrafica = interfaceGrafica;

	}

	/**
	 * Solicita a la interface gráfica que agregue los listener de este controlador a
	 * los items del menu Nuevo Juego.
	 */
	public void inicializarEscuchaEventosMenu() {
		this.interfaceGrafica.inicializarEmisionEventosMenu(new EscucharNuevoJuego());
	}

	/**
	 * Solicita a la interface gráfica que agregue los listener de este controlador a
	 * a cada grilla que el usuario puede modificar en ese momento y obtener así los valores
	 * que el mismo usuario está cambiando para saber si ganó o no.
	 */
	public void inicializarEscuchaEventosGrilla() {
		this.interfaceGrafica.inicializarEmisionEventosCeldas(new EscucharCambiosDelUsuarioEnElTablero());
		}
	

	/**
	 * Las acciones que realiza el controlador cuando se crea un nuevo juego. Aquí se construyen
	 * dos modelos de tablero, uno jugable y otro no-jugable. Se obtiene del modelo objetivo las sumas
	 * a las que debe llegar el usuario y se envian a la gráfica para sean mostradas de forma adecuada
	 * al usuario
	 * @param dificultad el tamaño del tablero elegi por el usuario al indicar un juego nuevo
	 */
	public void comenzarJuegoNuevo(int dificultad) {
		jugador = new TableroJuegoAritmetico(dificultad, true);
		objetivo = new TableroJuegoAritmetico(dificultad, false);
		System.out.print("Pediste un nuevo juego: " + dificultad);
		interfaceGrafica.crearNuevoTablero(dificultad, objetivo.getSumaDeLasFilas(), objetivo.getSumaDeLasColumnas());
		inicializarEscuchaEventosGrilla();
	}

	/**
	 * Clase interna que recibe los eventos producidos por una modificación en el valor contenido
	 * en una grilla modificable por el usuario
	 *
	 */
	class EscucharCambiosDelUsuarioEnElTablero implements DocumentListener {
		public void changedUpdate(DocumentEvent e) {
			cambiosEnElTablero(e);
			System.out.println("un changedUpdate");
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			cambiosEnElTablero(e);
			System.out.println("un insertUpdate");
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			cambiosEnElTablero(e);
			System.out.println("un removeUpdate");
		}
		
	
		
		
		/**
		 * Función en común que comparten los 3 diferentes eventos de la interface DocumentListener
		 * Aquí se obtiene el valor ingresado por el usuario en el tablero, y su posición.
		 * Se valida el dato y posteriormente se envía al modelo para que rehaga las suma y compare
		 * si el usuario ya ha ganado. Dependiendo el resultado, se le solicita a la interface gráfica
		 * que redibuje lo necesario.
		 * @param e
		 */
		private void cambiosEnElTablero(DocumentEvent e) {
				
				

				// Se obtiene el índice que ocupa en la posición del tablero, la grilla modificada
	        	int indice = (int) e.getDocument().getProperty("indice");
	        	
	        	int tamano = jugador.getTamano();
	        	int posFila = indice / tamano;
	        	int posCol = indice % tamano;
	        	int numeroNuevo = 0;
	        	
	        	
	        	// Validación de que el usuario haya escrito un valor que pueda entenderse cómo un número
	        	// que sea numérico y positivo
	        	try {
	        		numeroNuevo = Integer.valueOf(e.getDocument().getText(0, e.getDocument().getLength() ));
	        		
	        		if (numeroNuevo < 0) {
	        			numeroNuevo = 0;
	        		}
	        		
	        	} catch (NumberFormatException excep) {
	        		numeroNuevo = 0;
	        		interfaceGrafica.setMensaje("Sólo números por favor");
	        		
	        		
	        		
	        	} catch (BadLocationException excep) {
	        		numeroNuevo = 0;
	        		interfaceGrafica.setMensaje("Error de índice o lenght del getText del document");
	        		
	        	}
	        	
	        	
	        	
	        	// Se llama al modelo para avisarle que hay que cambiar el valor
	        	// y luego pregunta si se ha ganado
	        	jugador.cambiarValorCelda(posFila, posCol, numeroNuevo );
	        	if (jugador.haGanado(objetivo)) {
	        		interfaceGrafica.setMensaje("Ganaste");
	        		interfaceFelicitadora = new GuiFelicitacion();
	        		interfaceFelicitadora.setVisible(true);
	        	} else {
	        		interfaceGrafica.setMensaje("Aún No");
	        	}
	        	
	        	// Aquí también se le solicita al modelo la comparación de las sumas...
	        	boolean[] resultadoFilas = TableroJuegoAritmetico.comparaValores(jugador.getSumaDeLasFilas(), objetivo.getSumaDeLasFilas());
	    	    boolean[] resultadoColumnas = TableroJuegoAritmetico.comparaValores(jugador.getSumaDeLasColumnas(), objetivo.getSumaDeLasColumnas());

	    	    //... para luego solicitarle a la GUI que repite las celdas según fue correcto o no.
	        	interfaceGrafica.actualizarColoresSumas(resultadoFilas, resultadoColumnas);
	        	
			}

	}

	/**
	 * Clase interna que recibe los eventos producidos por un clic en un item del menú
	 * nuevo juego y la lógica necesaria para recibir, cual es el tamaño del tablero elegido.
	 *
	 */
	class EscucharNuevoJuego implements ActionListener {
		
		
		
		public void actionPerformed(ActionEvent e) {
			 JMenuItem Itemsolicitante = (JMenuItem) e.getSource();
			 int indice = interfaceGrafica.getIndiceMenuItemNuevo(Itemsolicitante);
			comenzarJuegoNuevo(indice + 2);
			interfaceGrafica.validate();
			interfaceGrafica.repaint();

		}
	}

}
