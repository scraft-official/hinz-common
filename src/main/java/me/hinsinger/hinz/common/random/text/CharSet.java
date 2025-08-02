package me.hinsinger.hinz.common.random.text;

public interface CharSet {
	public String getAlphabet();
	public int getLength();
	public char getCharAt(int index);
}
