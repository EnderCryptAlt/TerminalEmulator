package net.ddns.endercrypt.terminal.tileset.image;

import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.image.BufferedImage;

public class ImagePerformanceUtil
{
	private static final int TRANSPARENCY = Transparency.TRANSLUCENT;

	private static final GraphicsEnvironment graphicsEnviroment = GraphicsEnvironment.getLocalGraphicsEnvironment();
	private static final GraphicsDevice graphicsDevice = graphicsEnviroment.getDefaultScreenDevice();
	private static final GraphicsConfiguration graphicsConfiguration = graphicsDevice.getDefaultConfiguration();

	// COMPATIBLE IMAGE //

	public static BufferedImage makeCompatibleImage(int width, int height)
	{
		return graphicsConfiguration.createCompatibleImage(width, height, TRANSPARENCY);
	}

	public static BufferedImage makeCompatibleImage(BufferedImage image)
	{
		BufferedImage compatibleImage = makeCompatibleImage(image.getWidth(), image.getHeight());
		Graphics2D g2d = compatibleImage.createGraphics();
		g2d.drawImage(image, 0, 0, null);
		g2d.dispose();
		return compatibleImage;
	}
}
