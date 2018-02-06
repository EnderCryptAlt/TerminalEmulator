package net.ddns.endercrypt.terminal.graphicsmanager.converter;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class StandardColorConverter implements BufferedImageColorConverter
{
	private static final AlphaComposite ALPHA_COMPOSITE = AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.75f);

	@Override
	public void convert(BufferedImage target, BufferedImage source, Color color)
	{
		Graphics2D targetG2d = target.createGraphics();

		targetG2d.drawImage(source, 0, 0, null);
		targetG2d.setColor(color);
		targetG2d.setComposite(ALPHA_COMPOSITE);
		targetG2d.fillRect(0, 0, target.getWidth(), target.getHeight());

		targetG2d.dispose();
	}
}
