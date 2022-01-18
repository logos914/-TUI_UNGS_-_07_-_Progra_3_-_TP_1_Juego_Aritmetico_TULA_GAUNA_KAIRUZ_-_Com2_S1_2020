package juego;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentListener;


@SuppressWarnings("serial")
public class GuiJuego extends JFrame {

	private JPanel panel;
	private JMenuBar menuBar;
	private JMenu menuJuego;
	private JMenu subMenuJuegoNuevo;
	private ArrayList<JMenuItem> listadoMenuItemsNuevo;
	private JPanel panelTablero;
	private ArrayList<JTextField> celdasGUIJugador;
	private ArrayList<JTextField> celdasGUISumaFilas;
	private ArrayList<JTextField> celdasGUISumaColumnas;
	private Component margenIzquierdo;
	private Component margenDerecho;
	private JLabel labelMensajes;

	/**
	 * Constructor de la GUI.
	 */
	public GuiJuego() {
		

		setTitle("Juego Aritmético");
		getContentPane().setLayout(new BorderLayout(0, 0));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Vuelve a ubicar la ventana pero de forma centrada
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int anchoVentana = 600;
		int altoVentana = 600;
		int centrarHorizontalmente = (int) ((dimension.getWidth() - anchoVentana) / 2);
		int centrarVerticalmente = (int) ((dimension.getHeight() - altoVentana) / 2);
		setBounds(centrarHorizontalmente, centrarVerticalmente, anchoVentana, altoVentana);

		panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));

		// BARRA DE MENU
		menuBar = new JMenuBar();
		panel.add(menuBar, BorderLayout.NORTH);

		menuJuego = new JMenu("Juego");
		menuBar.add(menuJuego);

		subMenuJuegoNuevo = new JMenu("Nuevo");
		menuJuego.add(subMenuJuegoNuevo);

		// CREA LOS SUBMENUS DE NUEVO JUEGO zXz (desde 2 hasta un máximo de
		// tableroMaximo)
		int tableroMaximo = 8;
		listadoMenuItemsNuevo = new ArrayList<JMenuItem>();
		for (int i = 2; i <= tableroMaximo; i++) {

			JMenuItem menuItemNuevo = new JMenuItem(i + " x " + i);
			listadoMenuItemsNuevo.add(menuItemNuevo);
			subMenuJuegoNuevo.add(listadoMenuItemsNuevo.get(listadoMenuItemsNuevo.size() - 1));
		}

		this.panelTablero = new JPanel();
		panel.add(panelTablero, BorderLayout.CENTER);
		this.panelTablero.setLayout(new GridLayout(1, 1, 0, 0));

		margenIzquierdo = Box.createHorizontalStrut(35);
		panel.add(margenIzquierdo, BorderLayout.WEST);

		margenDerecho = Box.createHorizontalStrut(35);
		panel.add(margenDerecho, BorderLayout.EAST);

		labelMensajes = new JLabel("...");
		panel.add(labelMensajes, BorderLayout.SOUTH);

		celdasGUIJugador = new ArrayList<JTextField>();
		celdasGUISumaColumnas = new ArrayList<JTextField>();
		celdasGUISumaFilas = new ArrayList<JTextField>();

	}

	/**
	 * Esta función permite calcular el índice que ocupa en el arreglo de la grilla
	 * del usuario una determinada celda
	 * 
	 * @param celda a la cual se quiere averiguar su posición
	 * @return Un entero con la posición que ocupa en el arreglo de la grilla del
	 *         usuario
	 */
	public int getIndiceGUIJugador(JTextField celda) {
		return this.celdasGUIJugador.indexOf(celda);
	}

	/**
	 * Le indica a cada celda del tablero quien va a estar escuchando las
	 * modificaciones que se realizan sobre dicha celda. Adicionalmente se carga una
	 * propieda que contiene el índice de la celda, para conocer a quien representa
	 * en el tablero.
	 * 
	 * @param documentListener Enviado desde algún controlador, que ejecutará alguna
	 *                         acción al dispararse el evento
	 */
	public void inicializarEmisionEventosCeldas(DocumentListener documentListener) {

		for (JTextField celda : this.celdasGUIJugador) {

			celda.getDocument().putProperty("indice", this.getIndiceGUIJugador(celda));
			celda.getDocument().addDocumentListener(documentListener);
		}

	}

	/**
	 * Le indica a cada item del menu quien va a estar escuchando los eventos que se
	 * disparan desde cada uno.
	 * 
	 * @param actionEscucha Enviado desde algún controlador, que ejecutará alguna
	 *                      acción al dispararse el evento
	 */
	public void inicializarEmisionEventosMenu(ActionListener actionEscucha) {

		for (JMenuItem botonNuevo : this.getListadoMenuItemsNuevo()) {

			botonNuevo.addActionListener(actionEscucha);

		}

	}

	/**
	 * 
	 * @return Devuelve el ArrayList que contiene todos los items del menú de nuevo juego
	 */
	private ArrayList<JMenuItem> getListadoMenuItemsNuevo() {
		return listadoMenuItemsNuevo;
	}

	/**
	 * 
	 * Esta función permite calcular el índice que ocupa en el arreglo de items del menú nuevo juego
	 * @param menuitem al cual se quiere averiguar su posición
	 * @return Un entero con la posición que ocupa en el arreglo del listado de items del menú nuevo
	 */
	public int getIndiceMenuItemNuevo(JMenuItem menuitem) {
		return this.getListadoMenuItemsNuevo().indexOf(menuitem);
	}

	/**
	 * Permite escribir un mensaje en la etiqueta inferior.
	 * @param mensaje El mensaje que se quiere mostrar
	 */
	public void setMensaje(String mensaje) {
		this.labelMensajes.setText(mensaje);
	}

	
	/**
	 * Esta función se encarga de crear y dibujar los Jtextfield (editables y no editables) 
	 * que representan un tablero extendido, sobre un gridLayout dinámico. Dependiendo del tamaño
	 * @param tamano Entero que representa el tamaño en alto y/o ancho del tablero
	 * @param sumaFila Listado de números enteros representando lo que deben sumar las filas ordenamente
	 * @param sumaCol Listado de números enteros representando lo que deben sumar las columnas ordenamente
	 */
	public void crearNuevoTablero(int tamano, int[] sumaFila, int[] sumaCol) {

		this.panelTablero.removeAll();
		this.celdasGUIJugador.clear();
		this.celdasGUISumaColumnas.clear();
		this.celdasGUISumaFilas.clear();
		this.panelTablero.setLayout(new GridLayout(tamano + 1, tamano + 1, 20, 20));

		for (int fila = 0; fila < tamano + 1; fila++) {

			for (int col = 0; col < tamano + 1; col++) {
				JTextField celda = null;

				if (fila < tamano || col < tamano) {

					// @TO-DO: Borrar el texto x default. Es para debug
					celda = new JTextField();

					celda.setColumns(3);
					celda.setHorizontalAlignment(JTextField.CENTER);
					// Modifica el tamaño de la fuente de los textField dependiendo cuan grande es
					// la grilla extendida
					if (tamano < 4) {
						celda.setFont(new Font("Dialog", Font.PLAIN, 28));
					} else {
						celda.setFont(new Font("Dialog", Font.PLAIN, 18));
					}

					// Coloca en la última columna de la grilla extendida, el resultado que debe
					// alcanzar la suma de dicha fila.
					if (col == tamano) {
						celda.setEditable(false);
						celda.setText(String.valueOf(sumaFila[fila]));
						this.celdasGUISumaFilas.add(celda);
					}

					// Coloca en la última fila de la grilla extendida, el resultado que debe
					// alcanzar la suma de dicha columna.
					else if (fila == tamano) {
						celda.setEditable(false);
						celda.setText(String.valueOf(sumaCol[col]));
						this.celdasGUISumaColumnas.add(celda);
					}

					else {
						this.celdasGUIJugador.add(celda);
					}

					this.panelTablero.add(celda);

				}

			}

		}
	}

	/**
	 * Colorea las celdas no-editables para indicar si los valores expresados en el tablero del jugador suman
	 * lo indicado por dichas celdas no-editables.
	 * @param resultadofilas Un listado contiene ordenadamente la correctitud o no de las filas que representan la suma
	 * @param resultadocolumnas Un listado contiene ordenadamente la correctitud o no de las c que representan la suma
	 */
	public void actualizarColoresSumas(boolean[] resultadofilas, boolean[] resultadocolumnas) {

		for (int fila = 0; fila < this.celdasGUISumaFilas.size(); fila++) {
			if (resultadofilas[fila]) {
				this.celdasGUISumaFilas.get(fila).setBackground(Color.GREEN);
			} else {
				this.celdasGUISumaFilas.get(fila).setBackground(Color.RED);
			}
		}

		for (int col = 0; col < this.celdasGUISumaColumnas.size(); col++) {
			if (resultadocolumnas[col]) {
				this.celdasGUISumaColumnas.get(col).setBackground(Color.GREEN);
			} else {
				this.celdasGUISumaColumnas.get(col).setBackground(Color.RED);
			}
		}

	}

}
