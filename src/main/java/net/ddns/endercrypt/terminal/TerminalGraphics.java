package net.ddns.endercrypt.terminal;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.io.IOException;

import net.ddns.endercrypt.terminal.graphicsmanager.TerminalColorManager;
import net.ddns.endercrypt.terminal.tileset.TileImage;
import net.ddns.endercrypt.terminal.tileset.custom.TextFontTileset;

public class TerminalGraphics
{
	private static final TextFontTileset textTiles;
	static
	{
		try
		{
			textTiles = new TextFontTileset();
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	private final TerminalVariables variables;

	private TerminalScreenBuffer back;
	private TerminalScreenBuffer front;

	private Dimension tiles = new Dimension(0, 0);
	private int tileSize = 32;

	public TerminalGraphics(TerminalVariables variables)
	{
		this.variables = variables;

		back = new TerminalScreenBuffer(0, 0);
		front = new TerminalScreenBuffer(0, 0);
	}

	public void setTileSize(int tileSize)
	{
		if (tileSize <= 1)
		{
			throw new IllegalArgumentException("tileSize should not be less than 1");
		}
		this.tileSize = tileSize;
		attemptResize();
	}

	public int getTileSize()
	{
		return tileSize;
	}

	public boolean attemptResize()
	{
		Dimension screenSize = variables.jPanel.getSize();
		Dimension tilesNew = new Dimension(screenSize.width / tileSize, screenSize.height / tileSize);
		if (tiles.equals(tilesNew) == false)
		{
			tiles = tilesNew;
			back.resize(tiles.width, tiles.height);
			front.resize(tiles.width, tiles.height);
			return true;
		}
		return false;
	}

	public Dimension getSize()
	{
		return new Dimension(tiles);
	}

	public void refresh()
	{
		// switch buffers
		front = new TerminalScreenBuffer(back);
		// repaint
		variables.jFrame.repaint();
	}

	public void clear(Color backgroundColor)
	{
		back.clear(backgroundColor);
	}

	public void fill(TileImage tile, Color backgroundColor)
	{
		back.fill(tile, backgroundColor);
	}

	public void set(int x, int y, TileImage tile)
	{
		set(x, y, tile, null);
	}

	public void set(int x, int y, TileImage tile, Color backgroundColor)
	{
		if ((x < 0) || (y < 0) || (x >= back.getWidth()) || (y >= back.getHeight()))
		{
			return;
		}
		back.set(x, y, tile, backgroundColor);
	}

	public void print(int x, int y, String text)
	{
		print(x, y, text, null, null, null);
	}

	public void print(int x, int y, String text, Color backgroundColor)
	{
		print(x, y, text, null, null, backgroundColor);
	}

	public void print(int x, int y, String text, TerminalColorManager colorManager, Color color, Color backgroundColor)
	{
		for (char c : text.toCharArray())
		{
			if (x >= back.getWidth())
			{
				return;
			}
			TileImage tile = textTiles.get(c);
			if ((colorManager != null) && (color != null))
			{
				tile = colorManager.obtain(textTiles, textTiles.calculateIndex(c), color);
			}
			set(x, y, tile, backgroundColor);
			x++;
		}
	}

	public void draw(Graphics2D g2d, int x, int y) throws InterruptedException
	{
		front.draw(g2d, x, y, tileSize);
	}
}
