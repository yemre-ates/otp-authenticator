package com.yea.authenticator.util;

import java.text.DecimalFormat;
import java.util.Random;

public class Utils {
	
	// Utility class. New instance olusturmamaliyiz(common kullanilacak item'lar burada, singleton olması mantikli, extend eden class da new'leyemez.)
	//Effective Java kitabi section 3.
	private Utils() {
		throw new AssertionError();
	}

	public static String generateOtp(){
		
		 return new DecimalFormat("000000").format(new Random().nextInt(999999));
		 
	}
	
}
