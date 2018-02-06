package net.ddns.endercrypt.terminal.tileset;

import java.awt.Color;
import java.awt.image.BufferedImage;

import net.ddns.endercrypt.terminal.graphicsmanager.converter.BufferedImageColorConverter;

public class ColoredTileImage extends TileImage
{
	private Color color;

	public ColoredTileImage(BufferedImageColorConverter colorConverter, TileImage tileImage, Color color)
	{
		super(tileImage.getTileset(), tileImage.getIndex(), colorise(colorConverter, tileImage.getImage(), color), tileImage.getTileSize());
	}

	private static BufferedImage colorise(BufferedImageColorConverter colorConverter, BufferedImage image, Color color)
	{
		BufferedImage coloredImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
		colorConverter.convert(coloredImage, image, color);
		return coloredImage;
	}

	public Color getColor()
	{
		return color;
	}

}
