package net.ddns.endercrypt.terminal.tileset.custom;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import net.ddns.endercrypt.terminal.tileset.TerminalTileset;

public abstract class CustomTerminalTileset extends TerminalTileset
{

	public CustomTerminalTileset(String file, int tileSize) throws IOException
	{
		super(file, tileSize);
	}

	public CustomTerminalTileset(InputStream inputStream, int tileSize) throws IOException
	{
		super(inputStream, tileSize);
	}

	public CustomTerminalTileset(File file, int tileSize) throws IOException
	{
		super(file, tileSize);
	}

	public CustomTerminalTileset(BufferedImage image, int tileSize)
	{
		super(image, tileSize);
	}

	public abstract int calculateIndex(int index);

}
