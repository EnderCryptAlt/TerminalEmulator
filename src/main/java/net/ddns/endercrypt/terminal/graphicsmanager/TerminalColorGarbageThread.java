package net.ddns.endercrypt.terminal.graphicsmanager;

import java.awt.Color;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import net.ddns.endercrypt.terminal.tileset.TerminalTileset;

public class TerminalColorGarbageThread implements Runnable
{
	private int ageLimit;
	private Map<TerminalTileset, ColoredTileset> tilesets;

	public TerminalColorGarbageThread(int ageLimit, Map<TerminalTileset, ColoredTileset> tilesets)
	{
		this.ageLimit = ageLimit;
		this.tilesets = tilesets;
	}

	@Override
	public void run()
	{
		long maxAge = System.currentTimeMillis() - TimeUnit.SECONDS.toMillis(ageLimit);
		int totalTiles = 0;
		int cleanedTiles = 0;
		for (ColoredTileset coloredTileset : tilesets.values())
		{
			for (MultiColoredTile multiColoredTile : coloredTileset)
			{
				Iterator<Entry<Color, ColoredTileTracker>> entryIterator = multiColoredTile.iterator();
				while (entryIterator.hasNext())
				{
					totalTiles++;
					Entry<Color, ColoredTileTracker> Entry = entryIterator.next();
					ColoredTileTracker coloredTile = Entry.getValue();
					long time = coloredTile.getTime();
					if (time < maxAge)
					{
						cleanedTiles++;
						entryIterator.remove();
					}
				}
			}
		}
		System.out.println("Cleaned out " + cleanedTiles + " out of " + totalTiles + " tiles");
	}
}
