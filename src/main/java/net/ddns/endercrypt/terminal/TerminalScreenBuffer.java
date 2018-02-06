package net.ddns.endercrypt.terminal;

import java.awt.Color;
import java.awt.Graphics2D;

import net.ddns.endercrypt.terminal.tileset.TileImage;

public class TerminalScreenBuffer
{
	private int width = 0;
	private int height = 0;
	private Tile[][] array;

	private final Object lock = new Object();

	public TerminalScreenBuffer(int width, int height)
	{
		resize(width, height);
	}

	public TerminalScreenBuffer(TerminalScreenBuffer terminalScreenBuffer)
	{
		this.width = terminalScreenBuffer.width;
		this.height = terminalScreenBuffer.height;
		this.array = newArray(width, height);
		for (int y = 0; y < height; y++)
		{
			for (int x = 0; x < width; x++)
			{
				array[x][y].copyFrom(terminalScreenBuffer.array[x][y]);
			}
		}
	}

	private Tile[][] newArray(int newWidth, int newHeight)
	{
		Tile[][] newArray = new Tile[newWidth][newHeight];
		for (int y = 0; y < newHeight; y++)
		{
			for (int x = 0; x < newWidth; x++)
			{
				newArray[x][y] = new Tile();
			}
		}
		return newArray;
	}

	public int getWidth()
	{
		return width;
	}

	public int getHeight()
	{
		return height;
	}

	public void resize(int newWidth, int newHeight)
	{
		synchronized (lock)
		{
			// min size
			int minHeight = Math.min(newHeight, this.height);
			int minWidth = Math.min(newWidth, this.width);
			// set new size
			this.width = newWidth;
			this.height = newHeight;
			// copy to new array
			Tile[][] newArray = newArray(newWidth, newHeight);
			if (array != null)
			{
				for (int y = 0; y < minHeight; y++)
				{
					for (int x = 0; x < minWidth; x++)
					{
						newArray[x][y] = array[x][y];
					}
				}
			}
			array = newArray;
		}
	}

	public void clear(Color backgroundColor)
	{
		fill(null, backgroundColor);
	}

	public void fill(TileImage tile, Color backgroundColor)
	{
		for (int y = 0; y < height; y++)
		{
			for (int x = 0; x < width; x++)
			{
				set(x, y, tile, backgroundColor);
			}
		}
	}

	public void clear(int x, int y)
	{
		set(x, y, null, null);
	}

	public void set(int x, int y, TileImage tile, Color backgroundColor)
	{
		Tile tileObject = array[x][y];
		tileObject.tile = tile;
		if (backgroundColor != null)
		{
			tileObject.backgroundColor = backgroundColor;
		}
	}

	public void draw(Graphics2D g2d, int x_offset, int y_offset, int tileSize)
	{
		synchronized (lock)
		{
			for (int y = 0; y < height; y++)
			{
				for (int x = 0; x < width; x++)
				{
					Tile tile = array[x][y];
					tile.draw(g2d, x_offset + (x * tileSize), y_offset + (y * tileSize), tileSize);
				}
			}
		}
	}

	private class Tile
	{
		private TileImage tile;
		private Color backgroundColor = Color.BLACK;

		public Tile()
		{

		}

		public void copyFrom(Tile other)
		{
			tile = other.tile;
			backgroundColor = other.backgroundColor;
		}

		public void draw(Graphics2D g2d, int x, int y, int tileSize)
		{
			g2d.setColor(backgroundColor);
			g2d.fillRect(x, y, tileSize, tileSize);
			if (tile != null)
			{
				tile.draw(g2d, x, y, tileSize);
			}
		}
	}
}
