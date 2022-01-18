package juego;

import java.awt.EventQueue;


public class Jugar {

	public static void main(String[] args) {
		
		GuiJuego interfaceGrafica = new GuiJuego();
		Arbitro controlador = new Arbitro(interfaceGrafica);
		
		
		// TODO Auto-generated method stub
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					
					interfaceGrafica.setVisible(true);
					controlador.inicializarEscuchaEventosMenu();
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
		
		
		
		
		
	}

}
