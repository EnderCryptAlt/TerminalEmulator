package net.ddns.endercrypt.terminal.graphicsmanager;

import java.awt.Color;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import net.ddns.endercrypt.terminal.graphicsmanager.converter.BufferedImageColorConverter;
import net.ddns.endercrypt.terminal.tileset.ColoredTileImage;
import net.ddns.endercrypt.terminal.tileset.TileImage;

public class MultiColoredTile implements Iterable<Entry<Color, ColoredTileTracker>>
{
	private BufferedImageColorConverter colorConverter;
	private TileImage tileImage;

	private Map<Color, ColoredTileTracker> colors = new HashMap<>();

	public MultiColoredTile(BufferedImageColorConverter colorConverter, TileImage tileImage)
	{
		this.colorConverter = colorConverter;
		this.tileImage = tileImage;
	}

	public ColoredTileImage obtain(Color color)
	{
		ColoredTileTracker tile = colors.get(color);
		if (tile == null)
		{
			tile = new ColoredTileTracker(colorConverter, tileImage, color);
			colors.put(color, tile);
		}
		return tile.getTile();
	}

	@Override
	public Iterator<Entry<Color, ColoredTileTracker>> iterator()
	{
		return colors.entrySet().iterator();
	}
}
