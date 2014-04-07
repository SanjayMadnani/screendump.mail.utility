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
package fileUtil;

import java.io.File;

/**
 * Creates file path in a windows user name directory and then creates Image(.png) file there.
 * 
 * @author SANJAY
 */
public class FileUtil {
	private static String filePath = createFilePath();
	public static String folderName;

	public static File createFile(String fileName) {
		try {
			File file = new File(filePath);
			file.mkdirs();
			file = new File(filePath, fileName + ".png");
			file.createNewFile();
			return file;
		} catch (Exception e) {

		}
		return null;
	}

	public static String createFilePath() {
		folderName = "Public";
		File file = new File("C:\\Users");
		String[] directory = file.list();
		for (String dir : directory) {
			if (!dir.toLowerCase().contains("default") && !dir.toLowerCase().contains("desktop")
					&& !dir.toLowerCase().contains("public") && !dir.toLowerCase().contains("user")) {
				folderName = dir;
			}
		}
		return "C:\\Users\\" + folderName + "\\AppData\\Local\\JavaFX";
	}

}
