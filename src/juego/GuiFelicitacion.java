package juego;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.Font;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.SwingConstants;

public class GuiFelicitacion extends JDialog {

	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			GuiFelicitacion dialog = new GuiFelicitacion();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public GuiFelicitacion() {
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int anchoVentana = 450;
		int altoVentana = 300;
		int centrarHorizontalmente = (int) ((dimension.getWidth() - anchoVentana) / 2);
		int centrarVerticalmente = (int) ((dimension.getHeight() - altoVentana) / 2);
		setBounds(centrarHorizontalmente, centrarVerticalmente, anchoVentana, altoVentana);

		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.GREEN);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(1, 1, 0, 0));
		{
			JLabel lblNewLabel = new JLabel("¡Ganaste!");
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel.setBackground(Color.GREEN);
			lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 40));
			contentPanel.add(lblNewLabel);
		}
	}

}
