package net.ddns.endercrypt.terminal.tileset;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import net.ddns.endercrypt.terminal.tileset.image.ImagePerformanceUtil;

public class TileImage
{
	private final TerminalTileset tileset;
	private final int index;
	private final BufferedImage image;
	private final int tileSize;

	public TileImage(TerminalTileset tileset, int index, BufferedImage image, int tileSize)
	{
		this.tileset = tileset;
		this.index = index;
		this.image = ImagePerformanceUtil.makeCompatibleImage(image);
		this.tileSize = tileSize;

		if ((image.getWidth() != tileSize) || (image.getHeight() != tileSize))
		{
			throw new IllegalArgumentException("wrong tile size, expected " + tileSize + " but was " + image.getWidth() + "," + image.getHeight());
		}
	}

	public TerminalTileset getTileset()
	{
		return tileset;
	}

	public int getIndex()
	{
		return index;
	}

	public BufferedImage getImage()
	{
		return image;
	}

	public int getTileSize()
	{
		return tileSize;
	}

	public void draw(Graphics2D g2d, int x, int y, int size)
	{
		g2d.drawImage(image, x, y, size, size, null);
	}
}
