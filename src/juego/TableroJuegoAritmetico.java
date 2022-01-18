package juego;

import java.util.ArrayList;
import java.util.Random;

import grilla.Grilla;

public class TableroJuegoAritmetico {

	private Grilla<Integer> grilla;
	private int[] resultadosColumna;
	private int[] resultadosFila;
	private boolean jugable;
	private int tamano;

	/**
	 * Este constructor genera 2 posibles tableros. Si el tablero es "jugable" se
	 * genera un tablero con ceros, que servirá para que el jugador vaya completando
	 * y averiguando cuanto va sumando, ganará cuando sus sumas de filas y columnas
	 * coincidan con un tablero no jugable. Si no es jugable, crea un tablero con
	 * valores random, que el jugador deberá descifrar valiéndose de un tablero
	 * jugable.
	 * 
	 * @param tamano  El tamaño del tablero
	 * @param jugable Un tablero vacío para rellenar o sino uno con valores
	 */
	public TableroJuegoAritmetico(int tamano, boolean jugable) {

		this.grilla = new Grilla<Integer>(tamano, 0);
		this.resultadosColumna = new int[tamano];
		this.resultadosFila = new int[tamano];
		this.jugable = jugable;
		this.tamano = tamano;

		if (!jugable) {
			this.poblarJuego(tamano);
		}

	}

	
	/**
	 * Cuando se crea un tablero no-jugable, esta función permite completarlo con valores, para luego obtener las sumas
	 * de filas y columnas que deben ser el objetivo del jugador.
	 * @param tamano El tamaño del tablero, en cantidad de filas o columnas
	 * 
	 */
	private void poblarJuego(int tamano) {

		// Que en la grilla no aparezca un número mayor
		// al cuadrado del tamaño de la propia grila
		int nroMaximoAdecuado = (int) tamano;

		// Rellenar la grilla con valores random
		for (int fil = 0; fil < tamano; fil++) {

			for (int col = 0; col < tamano; col++) {

				Integer valorColumna = nuevoRandomAdecuado(nroMaximoAdecuado);
				this.grilla.setValor(fil, col, valorColumna);

				this.setSumaParcialFila(fil, valorColumna);
				this.setSumaParcialColumna(col, valorColumna);

			}

		}

	}

	/**
	 * Esta función es utilizada por el contructor de un tablero no jugable en el
	 * momento de poblarlo con valores. Un valor adecuado para completar en cada
	 * celda, es un número entre 1 y el cuadrado del ancho (o alto).
	 * 
	 * @param maximo Número máximo que debe generar el random
	 * @return Un valor random entre 1 y el máximo
	 */
	private Integer nuevoRandomAdecuado(int maximo) {
		Random randomizador = new Random();
		Integer valorAdecuado = randomizador.nextInt(maximo) + 1;
		return valorAdecuado;
	}

	/**
	 * Ayuda a modificar a la suma de una fila, en el momento de poblar un tablero
	 * no jugable
	 * 
	 * @param fila  Indice de la fila que está sumando
	 * @param valor Valor que hay que sumar
	 */
	private void setSumaParcialFila(int fila, int valor) {
		this.resultadosFila[fila] += valor;
	}

	/**
	 * Ayuda a modificar a la suma de una columna, en el momento de poblar un
	 * tablero no jugable
	 * 
	 * @param col   Indice de la columna que está sumando
	 * @param valor Valor que hay que sumar
	 */
	private void setSumaParcialColumna(int col, int valor) {
		this.resultadosColumna[col] += valor;
	}

	public int[] getSumaDeLasFilas() {
		return this.resultadosFila;
	}

	public int[] getSumaDeLasColumnas() {
		return this.resultadosColumna;
	}

	/**
	 * Utilizada de forma interna cómo código defensivo. Se utiliza para validar que
	 * es el tablero jugable.
	 * 
	 * 
	 * @return verdadero en caso de ser el tablero jugable
	 */
	private boolean validacionInterna() {

		if (this.jugable && this.getTamano() > 0) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * Utilizada de forma interna cómo código defensivo. Se utiliza para validar que
	 * el tablero es es jugable y que se pueden realizar operaciones de
	 * comparaciones con otro tablero.
	 * 
	 * @param Otro tablero con que se quiere probar si se puede comparar
	 * @return verdadero en caso de ser el tablero jugable y comparable
	 */
	private boolean validacionParaComparar(TableroJuegoAritmetico comparable) {

		boolean veredicto = true;

		// El tablero actual es jugable
		veredicto = veredicto && this.validacionInterna();

		// El tablero a comparar es no-jugable
		veredicto = veredicto && !comparable.validacionInterna();

		// El tamaño de ambos tableros debe ser igual
		veredicto = veredicto && (this.getTamano() == comparable.getTamano());

		return veredicto;
	}

	
	/**
	 * Utilizado cuando es necesario blanquear los resultados de sumas de filas y columnas, previo a recalcularlas
	 * porque se ha realizado una modificación en el tablero jugable.
	 */
	private void ponerEnCeroResultadoSumas() {

		for (int i = 0; i < this.getTamano(); i++) {

			this.resultadosColumna[i] = 0;
			this.resultadosFila[i] = 0;
		}
	}

	
	/**
	 * Recalcula la suma de filas y columnas, posterior a realizarse una modificaión en el tablero
	 * @throws UnsupportedOperationException Esta excepción se arroja cuando se intenta recalcular las sumas de un tablero
	 * que no es jugable. Sólo los tableros jugables pueden necesiar el recalculo de la suma.
	 */
	private void reCalcularSumas() throws UnsupportedOperationException {

		if (this.validacionInterna()) {

			this.ponerEnCeroResultadoSumas();

			for (int fil = 0; fil < this.getTamano(); fil++) {

				for (int col = 0; col < tamano; col++) {

					Integer valorCelda = this.grilla.getValor(fil, col);

					this.setSumaParcialFila(fil, valorCelda);
					this.setSumaParcialColumna(col, valorCelda);

				}

			}

		} else {
			throw new UnsupportedOperationException("No se recalculan las sumas de un tablero no-jugable");
		}
	}

	
	/**
	 * Función auxiliar para la comparación de valores entre posiciones iguales de dos listas numéricas
	 * @param listaX Una lista representada como un arreglo de números
	 * @param listaY Otra  lista representada como un arreglo de números
	 * @return Una lista com misma cantidad de elementos, en cada posición true o false, dependiendo si ambos valores
	 * eran iguales en el momento de la comparación o si no lo fueron.
	 * @throws ArithmeticException Esta excepción es arrojada cuando las listas no poseen la misma cantidad de elementos.
	 */
	static boolean[] comparaValores(int[] listaX, int[] listaY) throws ArithmeticException {

		if (listaX.length != listaY.length) {
			throw new ArithmeticException("Sólo se pueden comparar listas que tengan la misma cantidad de elementos");
		} else {
			boolean[] listaDeComparaciones = new boolean[listaX.length];

			for (int i = 0; i < listaX.length; i++) {
				if (listaX[i] == listaY[i]) {
					listaDeComparaciones[i] = true;
				} else {
					listaDeComparaciones[i] = false;
				}
			}

			return listaDeComparaciones;
		}

	}

	/**
	 * Función auxiliar para conocer si todos los valores de una lista son true
	 * @param lista Un arreglo con valores booleanos
	 * @return Verdadero si todos los valores de la lista enviada por parametro son verdaderos. Falso si algún valor no lo és.
	 */
	static boolean todoCorrecto(boolean[] lista) {
		boolean acumulador = true;

		for (boolean valor : lista) {
			acumulador = acumulador && valor;
		}

		return acumulador;

	}

	/**
	 * Sirve para comparar un tablero jugable, con uno no-jugable y probar si el tablero jugable
	 * ha ganado o es correcto con respecto al objetivo (el objetivo es tener las mismas sumas que no-jugable).
	 * 
	 * @param tableroObjetivo Un tablero no-jugable, al cual el jugable debe conseguir los mismos valores de suma x fila y columnas
	 * @return Verdadero en caso de ganar (coincidencia de sumas). Falso en caso de que no.
	 * @throws UnsupportedOperationException La imposibilidad de calcular este tablero con el objetivo. Sea porque no tienen 
	 * el mismo tamaño (no se corresponden), porque el tablero jugable no lo es, o viceversa.
	 */
	public boolean haGanado ( TableroJuegoAritmetico tableroObjetivo) throws UnsupportedOperationException {
		
		if (this.validacionParaComparar(tableroObjetivo)) {
	
			this.reCalcularSumas();
			
			boolean[] comparacionColumnas = comparaValores(this.getSumaDeLasColumnas() , tableroObjetivo.getSumaDeLasColumnas());
			
			boolean[] comparacionFilas = comparaValores(this.getSumaDeLasFilas() , tableroObjetivo.getSumaDeLasFilas());
			
			boolean veredicto = todoCorrecto(comparacionColumnas) && todoCorrecto(comparacionFilas);
			
			veredicto = veredicto && this.valoresEnCeroNoCuentanParaGanar();
				
			return veredicto;
	
	
		} else {
			throw new UnsupportedOperationException("No se puede analizar si este tablero ha ganado con " + tableroObjetivo);
		}
		
		
		
		
		
	}

	/**
	 * Getter del tamaño del tablero
	 * @return Entero con la cantidad de filas (o de columnas)
	 */
	public int getTamano() {
		return this.tamano;
	}

	
	
	/**
	 * Sirve para cambiar un valor de una celda. Sólo para un tablero jugable
	 * 
	 * @param fil Indice de la posición fila
	 * @param col ïndice de la posición columna
	 * @param valor Nuevo valor a escribir en dicha celda del tablero
	 * @throws UnsupportedOperationException Se devuelve esta excepción cuando se intenta cambiar el valor de un tablero
	 * no-jugable
	 * @throws IndexOutOfBoundsException Cuando los indices son incorrectos
	 */
	
	public void cambiarValorCelda(int fil, int col, Integer valor)
			throws UnsupportedOperationException, IndexOutOfBoundsException {

		if (this.validacionInterna()) {

			 
			if (valor < 0) {
				throw new IndexOutOfBoundsException("El nuevo valor no puede ser negativo: (" + valor + ")");
			} 
			 else {
				this.grilla.setValor(fil, col, valor);
			}

		} else {

			throw new UnsupportedOperationException("No se pueden cambiar valores de un tablero no-jugable");
		}
	}
	
	public ArrayList<Integer> getTodosLosValoresSerializadamente() {
		
		if (this.validacionInterna()) {
			ArrayList<Integer> valores = this.grilla.getValoresSerializados();
			return valores;
		} 
		else {
			throw new UnsupportedOperationException("No se pueden conocer los valores de un tablero no-jugable");
		}
	}
	
	private boolean valoresEnCeroNoCuentanParaGanar() {
		
		ArrayList<Integer> todosLosValoresDelTablero = this.getTodosLosValoresSerializadamente();
		boolean acumulador = true;
		
		for (Integer valor: todosLosValoresDelTablero) {
			
			acumulador = acumulador && (valor != 0);
			
		}
		
		return acumulador;
	}

}
