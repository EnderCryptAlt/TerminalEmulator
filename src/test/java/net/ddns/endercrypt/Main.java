package net.ddns.endercrypt;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.io.IOException;

import net.ddns.endercrypt.library.keyboardmanager.BindType;
import net.ddns.endercrypt.library.keyboardmanager.KeyboardEvent;
import net.ddns.endercrypt.library.keyboardmanager.KeyboardManager;
import net.ddns.endercrypt.library.keyboardmanager.binds.AnyLetters;
import net.ddns.endercrypt.library.keyboardmanager.binds.SpecificKey;
import net.ddns.endercrypt.terminal.TerminalGraphics;
import net.ddns.endercrypt.terminal.TerminalWindow;
import net.ddns.endercrypt.terminal.graphicsmanager.TerminalColorManager;
import net.ddns.endercrypt.terminal.tileset.TerminalTileset;

public class Main
{
	private static TerminalTileset baseTiles;
	private static TerminalTileset phoebusTiles;

	private static TerminalWindow window;
	private static TerminalColorManager colorManager;
	private static TerminalGraphics graphics;
	private static KeyboardManager keyboard;

	private static String text = "";

	private static int blink = 10;

	public static void main(String[] args) throws IOException, InterruptedException
	{
		baseTiles = new TerminalTileset("tilesets/Base.png", 64);
		phoebusTiles = new TerminalTileset("tilesets/Phoebus_16x16.png", 16);

		window = new TerminalWindow("Terminal Emulator Test");
		window.setBackgroundColor(Color.BLACK);
		colorManager = TerminalColorManager.standard();
		graphics = window.getTerminalGraphics();
		graphics.setTileSize(16);
		keyboard = window.getTerminalKeyboard();
		keyboard.bind(new AnyLetters(BindType.PRESS, true), Main::typeText);
		keyboard.bind(new SpecificKey(BindType.PRESS, KeyEvent.VK_BACK_SPACE), Main::erase);
		keyboard.bind(new SpecificKey(BindType.PRESS, KeyEvent.VK_ENTER), (e) -> text = "");
		keyboard.bind(new SpecificKey(BindType.PRESS, KeyEvent.VK_H), (e) -> graphics.setTileSize(graphics.getTileSize() + 1));

		run();
	}

	private static void typeText(KeyboardEvent event)
	{
		char c = (char) event.getKeyCode();
		c = Character.toLowerCase(c);
		if (event.isShiftHeld())
		{
			c = Character.toUpperCase(c);
		}
		text = text + c;
	}

	private static void erase(KeyboardEvent event)
	{
		if (text.length() > 0)
		{
			text = text.substring(0, text.length() - 1);
		}
	}

	private static void run() throws InterruptedException
	{
		int frame = 0;
		while (true)
		{
			frame++;
			// keyboard
			keyboard.triggerHeldKeys();
			// update
			update();
			// draw
			draw();
			// wait
			Thread.sleep(50);
			// clean old tiles
			if (frame % 100 == 0)
			{
				colorManager.clean(10);
			}
			// resize
			graphics.attemptResize();
		}
	}

	private static void update()
	{
		final int range = 10;
		if (blink > 0)
		{
			blink--;
			if (blink == 0)
			{
				blink = -range;
			}
		}
		else
		{
			blink++;
			if (blink == 0)
			{
				blink = range;
			}
		}
	}

	private static void draw() throws InterruptedException
	{
		//Color rng = new Color((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255));

		Dimension size = graphics.getSize();

		// start drawing
		//graphics.graphicsMode();

		// clear
		graphics.clear(Color.BLACK);

		for (int y = 0; y < size.height; y++)
		{
			for (int x = 0; x < size.width; x++)
			{
				graphics.set(x, y, phoebusTiles.get(1));
			}
		}

		/*
		// text
		graphics.print(2, 2, ">" + text, null);
		if (blink > 0)
		{
			int c = Math.min(255, text.length() * 5);
			graphics.set(3 + text.length(), 2, colorManager.obtain(baseTiles, 9, new Color(c, 255 - c, 0)), null);
		}
		
		// lever
		if (blink < 0)
			graphics.set(5, 5, phoebusTiles.get(149));
		else
			graphics.set(5, 5, phoebusTiles.get(162));
		
		// rectangle
		drawRectangle(1, 1, 4 + text.length(), 3, Color.GREEN);
		
		*/

		// draw
		graphics.refresh();
	}

	private static void drawRectangle(int x1, int y1, int x2, int y2, Color color)
	{
		// hor
		for (int x = x1 + 1; x < x2; x++)
		{
			graphics.set(x, y1, colorManager.obtain(baseTiles, 205, color));
			graphics.set(x, y2, colorManager.obtain(baseTiles, 205, color));
		}
		// ver
		for (int y = y1 + 1; y < y2; y++)
		{
			graphics.set(x1, y, colorManager.obtain(baseTiles, 186, color));
			graphics.set(x2, y, colorManager.obtain(baseTiles, 186, color));
		}
		// corners
		graphics.set(x1, y1, colorManager.obtain(baseTiles, 201, color));
		graphics.set(x2, y1, colorManager.obtain(baseTiles, 187, color));
		graphics.set(x1, y2, colorManager.obtain(baseTiles, 200, color));
		graphics.set(x2, y2, colorManager.obtain(baseTiles, 188, color));
	}
}
