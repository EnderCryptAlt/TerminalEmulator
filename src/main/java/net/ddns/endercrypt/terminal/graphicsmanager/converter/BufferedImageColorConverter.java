package net.ddns.endercrypt.terminal.graphicsmanager.converter;

import java.awt.Color;
import java.awt.image.BufferedImage;

public interface BufferedImageColorConverter
{
	public void convert(BufferedImage target, BufferedImage source, Color color);
}
