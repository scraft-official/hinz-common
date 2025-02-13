package me.hinsinger.projects.hinz.common.random.text.impl;

import lombok.Getter;
import me.hinsinger.projects.hinz.common.random.text.CharSet;

public enum RandomCharSet implements CharSet {
	NUMERIC_ONLY("0123456789"),
	LOWER_ONLY("abcdefghijklmnopqrstuvwxyz"),
	UPPER_ONLY("ABCDEFGHIJKLMONPQRSTUVWXYZ"),
	LOWER_AND_UPPER("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMONPQRSTUVWXYZ"),
	LOWER_AND_NUMERIC("abcdefghijklmnopqrstuvwxyz0123456789"),
	UPPER_AND_NUMERIC("ABCDEFGHIJKLMONPQRSTUVWXYZ0123456789"),
	LOWER_UPPER_NUMERIC("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMONPQRSTUVWXYZ0123456789"),
	BASE_64("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/")
	;
	
	@Getter private String alphabet;
	
	private RandomCharSet(String alphabet) {
		this.alphabet = alphabet;
	}

	@Override
	public int getLength() {
		return alphabet.length();
	}

	@Override
	public char getCharAt(int index) {
		return alphabet.charAt(index);
	}
}
