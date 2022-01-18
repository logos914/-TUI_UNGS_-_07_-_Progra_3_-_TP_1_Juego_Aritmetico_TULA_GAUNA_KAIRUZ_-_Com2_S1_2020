/**
 * 
 */
package grilla;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import grilla.Grilla;

/**
 * @author ignacio
 *
 */
public class GrillaTest {

	
	private Integer nulo;
	private int tamano;
	private Grilla<Integer> grillaTesteable;
	
	
	/**
	 * Test de Grilla de números
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUpNumeros() throws Exception {
		this.nulo = 0;
		this.tamano = 4;
		this.grillaTesteable = new Grilla<Integer>(tamano,nulo);
		
	}

	@Test
	public void testValoresVacios() {
		
		ArrayList<Integer> todosLosValores = grillaTesteable.getValoresSerializados();
		boolean acumulador = true;
		for (int i = 0; i < tamano; i++) {
			acumulador = acumulador && ( todosLosValores.get(i) == nulo );
		}
		assertTrue(acumulador);
		
	}
	
	@Test (expected = Exception.class)
	public void errorDeIndiceFilaEnGet() {
		this.grillaTesteable.getValor(4, 3);
	}
	
	@Test (expected = Exception.class)
	public void errorDeIndiceColumnaEnGet() {
		this.grillaTesteable.getValor(3, 4);
	}
	
	@Test (expected = Exception.class)
	public void errorDeIndiceFilaEnSet() {
		this.grillaTesteable.setValor(4, 3, 1);
	}
	
	@Test (expected = Exception.class)
	public void errorDeIndiceColumnaEnSet() {
		this.grillaTesteable.setValor(3, 4, 1);
	}
	
	@Test
	public void calculoCorrectoColumna() {
		int columna = 2;
		Integer valor = 14;
		this.grillaTesteable.setValor(0, columna, valor);
		this.grillaTesteable.setValor(1, columna, valor);
		this.grillaTesteable.setValor(2, columna, valor);
		this.grillaTesteable.setValor(3, columna, valor);
		
		ArrayList<Integer> listadoColumna = this.grillaTesteable.getValoresColumna(columna);
		
		boolean acumulador = true;
		for (int i = 0; i < tamano; i++) {
			acumulador = acumulador && ( listadoColumna.get(i) == valor );
		}
		assertTrue(acumulador);
		
	}
	
	@Test
	public void calculoCorrectoFila() {
		int fila = 3;
		Integer valor = 14;
		this.grillaTesteable.setValor(fila, 0, valor);
		this.grillaTesteable.setValor(fila, 1, valor);
		this.grillaTesteable.setValor(fila, 2, valor);
		this.grillaTesteable.setValor(fila, 3, valor);
		
		ArrayList<Integer> listadoFila = this.grillaTesteable.getValoresFila(fila);
		
		boolean acumulador = true;
		for (int i = 0; i < tamano; i++) {
			acumulador = acumulador && ( listadoFila.get(i) == valor );
		}
		assertTrue(acumulador);
		
	}
	
}
