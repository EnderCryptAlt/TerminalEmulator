package net.ddns.endercrypt.terminal.graphicsmanager;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import net.ddns.endercrypt.terminal.graphicsmanager.converter.BufferedImageColorConverter;
import net.ddns.endercrypt.terminal.graphicsmanager.converter.StandardColorConverter;
import net.ddns.endercrypt.terminal.tileset.ColoredTileImage;
import net.ddns.endercrypt.terminal.tileset.TerminalTileset;
import net.ddns.endercrypt.terminal.tileset.TileImage;

public class TerminalColorManager
{
	private ExecutorService executorService = Executors.newCachedThreadPool();

	private BufferedImageColorConverter colorConverter;
	private Map<TerminalTileset, ColoredTileset> tilesets = new HashMap<>();

	public static TerminalColorManager standard()
	{
		return new TerminalColorManager(new StandardColorConverter());
	}

	public TerminalColorManager(BufferedImageColorConverter colorConverter)
	{
		this.colorConverter = colorConverter;
	}

	public void clean(int ageLimit)
	{
		executorService.submit(new TerminalColorGarbageThread(ageLimit, tilesets));
	}

	public ColoredTileImage obtain(TileImage tileImage, int r, int g, int b)
	{
		return obtain(tileImage, new Color(r, g, b));
	}

	public ColoredTileImage obtain(TileImage tileImage, Color color)
	{
		return obtain(tileImage.getTileset(), tileImage.getIndex(), color);
	}

	public ColoredTileImage obtain(TerminalTileset tileset, int index, int r, int g, int b)
	{
		return obtain(tileset, index, new Color(r, g, b));
	}

	public ColoredTileImage obtain(TerminalTileset tileset, int index, Color color)
	{
		ColoredTileset coloredTileset = tilesets.get(tileset);
		if (coloredTileset == null)
		{
			coloredTileset = new ColoredTileset(colorConverter, tileset);
			tilesets.put(tileset, coloredTileset);
		}
		return coloredTileset.obtain(index, color);
	}
}
