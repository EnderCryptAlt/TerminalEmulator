package net.ddns.endercrypt.terminal.graphicsmanager;

import java.awt.Color;

import net.ddns.endercrypt.terminal.graphicsmanager.converter.BufferedImageColorConverter;
import net.ddns.endercrypt.terminal.tileset.ColoredTileImage;
import net.ddns.endercrypt.terminal.tileset.TerminalTileset;
import net.ddns.endercrypt.terminal.tileset.TileImage;

public class ColoredTileTracker
{
	private long time;
	private final ColoredTileImage coloredTileImage;
	private final Color color;

	public ColoredTileTracker(BufferedImageColorConverter colorConverter, TerminalTileset tileset, int index, Color color)
	{
		this(colorConverter, tileset.get(index), color);
	}

	public ColoredTileTracker(BufferedImageColorConverter colorConverter, TileImage tileImage, Color color)
	{
		touch();
		this.color = color;
		// generate image
		coloredTileImage = new ColoredTileImage(colorConverter, tileImage, color);
	}

	private void touch()
	{
		time = System.currentTimeMillis();
	}

	public long getTime()
	{
		return time;
	}

	public ColoredTileImage getTile()
	{
		touch();
		return coloredTileImage;
	}

	public Color getColor()
	{
		return color;
	}
}