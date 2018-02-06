package net.ddns.endercrypt.terminal.tileset.custom;

import java.io.IOException;
import net.ddns.endercrypt.terminal.tileset.TerminalTileset;
import net.ddns.endercrypt.terminal.tileset.TileImage;

public class TextFontTileset extends CustomTerminalTileset // from ' ' to '~'
{
	public TextFontTileset() throws IOException
	{
		super(TerminalTileset.class.getResourceAsStream("/Fonts/TextFont.png"), 10);
	}

	@Override
	public int calculateIndex(int index)
	{
		index -= ' ';
		if ((index < 0) || (index > 16 * 6))
		{
			index = 31; // ?
		}
		return index;
	}

	public TileImage get(char index)
	{
		return super.get(calculateIndex(index));
	}
}
