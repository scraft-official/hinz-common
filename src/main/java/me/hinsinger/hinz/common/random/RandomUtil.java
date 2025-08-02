package me.hinsinger.hinz.common.random;

import java.util.Random;
import java.util.UUID;

import me.hinsinger.hinz.common.huid.HUID;
import me.hinsinger.hinz.common.random.text.CharSet;
import me.hinsinger.hinz.common.random.text.impl.RandomCharSet;
import me.hinsinger.hinz.common.random.uuid.UUIDGenerationAlgorithm;
import me.hinsinger.hinz.common.random.uuid.impl.DefaultRandomUUIDGenerator;

public class RandomUtil {
	private static CharSet DEFAULT_RANDOM_TEXT_CHARSET =  RandomCharSet.LOWER_AND_UPPER;
	private static UUIDGenerationAlgorithm DEFAULT_RANDOM_UUID_GENERATOR = new DefaultRandomUUIDGenerator();
	
	private static Random RANDOM = new Random();
	
	/**
	 * @param length - text length
	 * @param charset - the charset to use for random generation (RandomCharSet.class for default impls)
	 * @return
	 */
	public static String text(int length, CharSet charset) {
		StringBuilder builder = new StringBuilder();
		
		for(int i = 0; i < length; i++) {
			char ch = charset.getCharAt(integer(0, charset.getLength()));
			builder.append(ch);
		}
		
		return builder.toString();
	}
	
	public static String text(int length) {
		return text(length, DEFAULT_RANDOM_TEXT_CHARSET);
	}
	
	/**
	 * @param min (inclusive)
	 * @param max (exclusive)
	 * @return random number between <min,max)
	 */
	public static int integer(int min, int max) {
		return RANDOM.nextInt(min, max);
	}
	
	/**
	 * @return <MIN_VALUE, MAX_VALUE)
	 */
	public static int integer() {
		return integer(Integer.MIN_VALUE, Integer.MAX_VALUE);
	}
	
	/**
	 * @return <0, MAX_VALUE)
	 */
	public static int integerPositive() {
		return integer(0, Integer.MAX_VALUE);
	}
	
	/**
	 * @return <MIN_VALUE, 0)
	 */
	public static int integerNegative() {
		return integer(Integer.MIN_VALUE, 0);
	}
	
	public static float decimal(float min, float max) {
		return RANDOM.nextFloat(min, max);
	}
	
	public static UUID uuid(UUIDGenerationAlgorithm algorithm) {
		return algorithm.generate();
	}
	
	public static UUID uuid() {
		return uuid(DEFAULT_RANDOM_UUID_GENERATOR);
	}
	
	public static HUID huid(UUIDGenerationAlgorithm algorithm) {
		return HUID.fromUUID(uuid(algorithm));
	}
	
	public static HUID huid() {
		return HUID.fromUUID(uuid());
	}
	
}
