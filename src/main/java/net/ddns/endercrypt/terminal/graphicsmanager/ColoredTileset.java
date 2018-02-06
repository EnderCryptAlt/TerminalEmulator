package net.ddns.endercrypt.terminal.graphicsmanager;

import java.awt.Color;
import java.util.Arrays;
import java.util.Iterator;

import net.ddns.endercrypt.terminal.graphicsmanager.converter.BufferedImageColorConverter;
import net.ddns.endercrypt.terminal.tileset.ColoredTileImage;
import net.ddns.endercrypt.terminal.tileset.TerminalTileset;

public class ColoredTileset implements Iterable<MultiColoredTile>
{
	//private BufferedImageColorConverter colorConverter;
	private MultiColoredTile[] tiles;

	public ColoredTileset(BufferedImageColorConverter colorConverter, TerminalTileset tileset)
	{
		//this.colorConverter = colorConverter;
		int tileCount = tileset.getTileCount();
		tiles = new MultiColoredTile[tileCount];
		for (int i = 0; i < tileCount; i++)
		{
			tiles[i] = new MultiColoredTile(colorConverter, tileset.get(i));
		}
	}

	public ColoredTileImage obtain(int index, Color color)
	{
		return tiles[index].obtain(color);
	}

	@Override
	public Iterator<MultiColoredTile> iterator()
	{
		return Arrays.stream(tiles).iterator();
	}
}
