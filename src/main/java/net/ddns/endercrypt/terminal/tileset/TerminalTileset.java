package net.ddns.endercrypt.terminal.tileset;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class TerminalTileset
{
	private final int tileSize;

	private TileImage[] tiles;

	public TerminalTileset(String file, int tileSize) throws IOException
	{
		this(new File(file), tileSize);
	}

	public TerminalTileset(InputStream inputStream, int tileSize) throws IOException
	{
		this(ImageIO.read(inputStream), tileSize);
	}

	public TerminalTileset(File file, int tileSize) throws IOException
	{
		this(ImageIO.read(file), tileSize);
	}

	public TerminalTileset(BufferedImage image, int tileSize)
	{
		this.tileSize = tileSize;
		int width = image.getWidth();
		int height = image.getHeight();
		int tileWidth = width / tileSize;
		int tileHeight = height / tileSize;
		int cutWidth = tileWidth * tileSize;
		int cutHeight = tileHeight * tileSize;
		if ((cutWidth != width) || (cutHeight != height))
		{
			throw new IllegalArgumentException("illegal tileset size " + width + "," + height + " cut was: " + cutWidth + "," + cutHeight);
		}
		int tileCount = tileWidth * tileHeight;
		tiles = new TileImage[tileCount];
		int index = 0;
		for (int y = 0; y < tileHeight; y++)
		{
			for (int x = 0; x < tileWidth; x++)
			{
				int posx = x * tileSize;
				int posy = y * tileSize;
				BufferedImage tileImage = image.getSubimage(posx, posy, tileSize, tileSize);
				tiles[index] = new TileImage(this, index, tileImage, tileSize);
				index++;
			}
		}
	}

	public int getTileSize()
	{
		return tileSize;
	}

	public int getTileCount()
	{
		return tiles.length;
	}

	public TileImage get(Enum<?> index)
	{
		return get(index.ordinal());
	}

	public TileImage get(int index)
	{
		return tiles[index];
	}
}
