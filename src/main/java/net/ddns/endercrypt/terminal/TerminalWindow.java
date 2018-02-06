package net.ddns.endercrypt.terminal;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Objects;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import net.ddns.endercrypt.library.keyboardmanager.KeyboardManager;

public class TerminalWindow
{
	private final TerminalVariables variables = new TerminalVariables();

	private TerminalGraphics terminalGraphics;
	private KeyboardManager keyboardManager;

	private Color backgroundColor = Color.BLACK;

	@SuppressWarnings("serial")
	public TerminalWindow(String title)
	{
		// create jpanel
		variables.jPanel = new JPanel()
		{
			{
				setPreferredSize(new Dimension(1000, 500));
			}

			@Override
			protected void paintComponent(Graphics g)
			{
				TerminalWindow.this.paintComponent((Graphics2D) g, getSize());
			}
		};

		// create graphics object
		terminalGraphics = new TerminalGraphics(variables);

		// create jpanel
		variables.jFrame = new JFrame(title);
		variables.jFrame.add(variables.jPanel);
		variables.jFrame.pack();
		variables.jFrame.setLocationRelativeTo(null);
		variables.jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		variables.jFrame.setVisible(true);

		// keyboard
		keyboardManager = new KeyboardManager();
		keyboardManager.install(variables.jFrame);

		// detect resize
		terminalGraphics.attemptResize();
	}

	public TerminalGraphics getTerminalGraphics()
	{
		return terminalGraphics;
	}

	public KeyboardManager getTerminalKeyboard()
	{
		return keyboardManager;
	}

	public void setBackgroundColor(Color backgroundColor)
	{
		Objects.requireNonNull(backgroundColor);
		this.backgroundColor = backgroundColor;
	}

	private void paintComponent(Graphics2D g2d, Dimension screenSize)
	{
		// background color
		g2d.setColor(backgroundColor);
		g2d.fillRect(0, 0, screenSize.width, screenSize.height);
		// draw
		Dimension size = terminalGraphics.getSize();
		int width = size.width * terminalGraphics.getTileSize();
		int height = size.height * terminalGraphics.getTileSize();
		int x = (screenSize.width - width) / 2;
		int y = (screenSize.height - height) / 2;
		try
		{
			getTerminalGraphics().draw(g2d, x, y);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
}
