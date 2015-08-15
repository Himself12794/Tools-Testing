package com.pwhiting.main;

import java.util.Arrays;

import com.pwhiting.util.Util;

public class Main {

	public static void main(String[] args) {

		final String blurb = "What's a guy gotta do to get a girl in this town?\n";

		final String character = " a ";

		byte[] blurbToBinary = blurb.getBytes();

		byte[] charByte = character.getBytes();

		for (byte[] byteArray : Util.byteArraySplit(blurbToBinary, charByte)) {
			System.out.println(Arrays.toString(byteArray));
		}
	}

}
