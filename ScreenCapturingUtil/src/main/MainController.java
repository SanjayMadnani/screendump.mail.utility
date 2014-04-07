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
package main;

import imageUtil.ImageUtil;

import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Validate from and to email id if found proper then execution starts.
 * 
 * @author SANJAY
 * @see ImageUtil
 */
public class MainController {
	private static final ResourceBundle bundle = ResourceBundle.getBundle("configuration");

	public static void main(String[] args) {
		while (true) {
			try {
				if (!checkPattern(bundle.getString("FROM_EMAIL_ADDRESS").trim()))
					break;
				if (!checkPattern(bundle.getString("TO_EMAIL_ADDRESS").trim()))
					break;
				try {
					ImageUtil.capturedImage(); // return true or false
					Thread.sleep((getDelayMin() * 60 * 1000));
				} catch (Exception e) {

				}
			} catch (Exception e) {
				break;
			}
		}
	}

	private static int getDelayMin() {
		return Integer.parseInt(bundle.getString("INTERVAL").trim());
	}

	private static boolean checkPattern(String word) {
		try {
			word = word.split("\\s")[0];
			Pattern mailAddressPattern = Pattern.compile("[\\w\\.-]{3,30}@[\\w\\.-]{3,30}");
			Matcher eMailMatcher = mailAddressPattern.matcher(word);
			if (eMailMatcher.find())
				return true;
			return false;
		} catch (Exception e) {
			return false;
		}
	}
}
