package juego;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TableroJuegoAritmeticoTest {

	TableroJuegoAritmetico tableroJugable;
	TableroJuegoAritmetico tableroNoJugable;
	
	@Before
	public void setUp() throws Exception {
		
		this.tableroJugable = new TableroJuegoAritmetico(1, true);
		this.tableroNoJugable = new TableroJuegoAritmetico(1, false);
		
		
		
	}

	@Test
	public void ganar() {
		
		this.tableroJugable.cambiarValorCelda(0, 0, 1);
		assertTrue(this.tableroJugable.haGanado(tableroNoJugable));

	}
	
	
	
	@Test (expected = Exception.class)
	public void noQuierasPonerNumerosNegativos() {
		this.tableroJugable.cambiarValorCelda(0, 0, -1);
	}
	
	@Test (expected = Exception.class)
	public void comparacionIncorrecta() {
		this.tableroNoJugable.haGanado(tableroJugable);
	}
	
	@Test (expected = Exception.class)
	public void comparacionTautologica() {
		this.tableroJugable.haGanado(tableroJugable);
	}
	
	@Test (expected = Exception.class)
	public void comparacionTautologicaIncorrecta() {
		this.tableroNoJugable.haGanado(tableroNoJugable);
	}
	
	@Test (expected = Exception.class)
	public void noQuierasCambiarUnTableroNoJugable() {
		this.tableroNoJugable.cambiarValorCelda(0, 0, 1);
	}
	
	@Test (expected = Exception.class)
	public void ustedNoPuedeAveriguarNingunValorDelNoJugable() {
		this.tableroNoJugable.getTodosLosValoresSerializadamente();
	}
	
	@Test
	public void peroSiPuedeAveriguarSusSumasColumnas() {
		// La suma de la única de columna de un tablero 1x1 es 1
		assertEquals(1,this.tableroNoJugable.getSumaDeLasColumnas()[0]);
	}
	
	@Test
	public void peroSiPuedeAveriguarSusSumasFilas() {
		// La suma de la única de fila de un tablero 1x1 es 1
		assertEquals(1,this.tableroNoJugable.getSumaDeLasFilas()[0]);
	}
	
	@Test
	public void PuedeAveriguarSusSumasColumnaJugable() {
		// La suma de la única de columna de un tablero 1x1 sin completar es 0
		assertEquals(0,this.tableroJugable.getSumaDeLasColumnas()[0]);
	}
	
	@Test
	public void peroSiPuedeAveriguarSusSumasFilasJugable() {
		// La suma de la única de fila de un tablero 1x1 sin completar es 0
		assertEquals(0,this.tableroJugable.getSumaDeLasFilas()[0]);
	}

}
