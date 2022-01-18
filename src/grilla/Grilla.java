package grilla;

import java.util.ArrayList;

public class Grilla<T1> {

	private ArrayList<ArrayList<T1>> celdas;

	
	/**
	 * Constructor del TAD grilla. Este tipo de dato representa una grilla de 
	 * valores del mismo típo. Representados en filas y columna. La grilla es cuadrada
	 * (misma cantidad de columnas o que filas).
	 * 
	 * Se creó una clase genérica que pueda usar distintos tipos. El tipo genérico se
	 * representa como T1.
	 * 
	 * @param tamano Cantidad de filas y/o de columnas
	 * @param valorNulo El valor nulo o vacío, o por defecto que deberá tener la grilla, luego podrá ser cambiado
	 * (por ej, para valores numéricos el nulo es 0, para cadenas "", etc).
	 */
	public Grilla(int tamano, T1 valorNulo) {

		this.celdas = new ArrayList<ArrayList<T1>>();

		// Por cada fila, crear una columna con valores nulos, vacios o en ceros
		// Agregar cada columna a cada fila de la grilla
		for (int fil = 0; fil < tamano; fil++) {

			ArrayList<T1> columnaConValoresNulos = new ArrayList<T1>();

			for (int col = 0; col < tamano; col++) {
				columnaConValoresNulos.add(valorNulo);
			}

			this.celdas.add(columnaConValoresNulos);
		}

	}

	
	
	/**
	 * Obtiene el valor almacenado en la posición (fil,col)
	 * 
	 * @param fil Indice de la posición respecto a la FILA
	 * @param col Indice de la posición respecto a la COLUMNA
	 * @return El valor de dicha posición
	 */
	public T1 getValor(int fil, int col) {

		return this.celdas.get(fil).get(col);

	}

	
	
	
	/**
	 * Cambia el valor de una posición
	 * 
	 * @param fil   Indice de la posición respecto a la FILA
	 * @param col   Indice de la posición respecto a la COLUMNA
	 * @param valor Valor a modificar
	 */

	public void setValor(int fil, int col, T1 valor) throws IndexOutOfBoundsException {
		
		if (fil < 0) {
			throw new IndexOutOfBoundsException("El índice de la fila no puede ser negativo: (" + fil + ")");
		} else if (col < 0) {
			throw new IndexOutOfBoundsException("El índice de la columna no puede ser negativo: (" + col + ")");
		} else if (fil >= this.celdas.size()) {
			throw new IndexOutOfBoundsException("El índice de la fila no puede ser igual o mayor al tamaño actual de la grilla: (" + fil + ")");
		} else if (col >= this.celdas.get(fil).size()) {
			throw new IndexOutOfBoundsException("El índice de la columna no puede ser igual o mayor al tamaño actual de la grilla: (" + col + ")");
		} else {
			ArrayList<T1> filaParaModificar = this.celdas.get(fil);
			filaParaModificar.set(col, valor);
			this.celdas.set(fil, filaParaModificar);
		}

		

	}

	/**
	 * Obtener todos los valores de una fila
	 * 
	 * @param fil La fila que se quiere obtener
	 * @return Un ArrayList con los valores de dicha fila
	 */
	public ArrayList<T1> getValoresFila(int fil) {
		return this.celdas.get(fil);

	}

	/**
	 * Obtener todos los valores de una columna
	 * 
	 * @param col La Columna que se quiere obtener
	 * @return Un ArrayList con los valores de dicha columna
	 */
	public ArrayList<T1> getValoresColumna(int col) {

		ArrayList<T1> columna = new ArrayList<T1>();

		for (int i = 0; i < this.celdas.size(); i++) {

			columna.add(this.celdas.get(i).get(col));

		}

		return columna;
	}
	
	/**
	 * Obtener todos los valores de la grilla de forma "serial", como una colección
	 * única lineal. Leyendo los valores "de izquierda a derecha" y de "arriba a abajo"
	 * 
	 * 
	 * @return Un ArrayList con los valores de toda la grilla
	 */
	public ArrayList<T1> getValoresSerializados() {
		
		ArrayList<T1> resultadoSerializado = new ArrayList<T1>();
		
		for (int i = 0; i < this.celdas.size(); i++) {

			resultadoSerializado.addAll(this.getValoresColumna(i));

		}
		return resultadoSerializado;
	}

}
