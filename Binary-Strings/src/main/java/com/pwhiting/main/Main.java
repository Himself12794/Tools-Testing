package com.pwhiting.main;

import java.util.Arrays;
import java.util.List;

import com.google.common.collect.Lists;

public class Main {

	public static void main(String[] args) {

		final String blurb = "What's a guy gotta do to get a girl in this town?\n";

		final String character = " a ";

		byte[] blurbToBinary = blurb.getBytes();

		byte[] charByte = character.getBytes();

		for (Byte[] byteArray : byteArraySplit(blurbToBinary, charByte)) {
			System.out.println(Arrays.toString(arrayConversion(byteArray)));
		}
	}

	private static Byte[] arrayConversion(byte[] array) {

		Byte[] value = new Byte[array.length];

		for (int i = 0; i < value.length; i++) {
			value[i] = array[i];
		}

		return value;
	}

	private static byte[] arrayConversion(Byte[] array) {

		byte[] value = new byte[array.length];

		for (int i = 0; i < value.length; i++) {
			value[i] = array[i];
		}

		return value;
	}

	public static List<Byte[]> byteArraySplit(byte[] value, byte[] pattern) {

		List<Byte[]> split = Lists.newArrayList();

		if (value.length >= pattern.length) {

			int charLength = pattern.length;

			int lastSplit = 0;

			for (int i = 0; i < value.length - pattern.length; i++) {

				byte[] selection = Arrays.copyOfRange(value, i, i + charLength);

				if (Arrays.equals(pattern, selection)) {
					i += charLength - 1;
					split.add(arrayConversion(Arrays.copyOfRange(value,
							lastSplit, i - charLength + 1)));
					lastSplit = i + 1;
				}

			}

			split.add(arrayConversion(Arrays.copyOfRange(value, lastSplit,
					value.length)));
		}

		return split;

	}

}
