package me.hinsinger.projects.hinz.common.time.timestamp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import me.hinsinger.projects.hinz.common.json.JsonUtil;
import me.hinsinger.projects.hinz.common.time.timestamp.json.deserializer.GsonTimestampDeserializer;
import me.hinsinger.projects.hinz.common.time.timestamp.json.deserializer.JacksonTimestampDeserializer;
import me.hinsinger.projects.hinz.common.time.timestamp.json.serializer.GsonTimestampSerializer;
import me.hinsinger.projects.hinz.common.time.timestamp.json.serializer.JacksonTimestampSerializer;

public class Timestamp {
	static {
		JsonUtil.registerAdapter(Timestamp.class, new JacksonTimestampSerializer(), new JacksonTimestampDeserializer());
		JsonUtil.registerAdapter(Timestamp.class, new GsonTimestampSerializer(), new GsonTimestampDeserializer());
	}

	/**
	 * long epoch - in milliseconds
	 */
	private long epoch;
	
	public static Timestamp of(long epochInMillis) {
		return new Timestamp(epochInMillis);
	}
	
	public static Timestamp now() {
		return new Timestamp();
	}
	
	public static Timestamp tomorrow() {
		return new Timestamp().addDays(1);
	}
	
	public static Timestamp yesterday() {
		return new Timestamp().subDays(1);
	}
	
	private Timestamp() {
		this(currentEpoch());
	}
	
	private Timestamp(long epochInMillis) {
		this.epoch = epochInMillis;
	}
	
	public boolean isPast() {
		return epoch < currentEpoch();
	}
	
	public boolean isFuture() {
		return epoch > currentEpoch();
	}
	
	public boolean isOlderThan(Timestamp timestamp) {
		return timestamp.epoch < timestamp.epoch;
	}
	
	public boolean isYoungerThan(Timestamp timestamp) {
		return timestamp.epoch > timestamp.epoch;
	}
	
	public Timestamp addMilliseconds(long value) {
		this.epoch += value;
		return this;
	}
	
	public Timestamp addSeconds(int value) {
		return addMilliseconds(1000 * value);
	}
	
	public Timestamp addMinutes(int value) {
		return addSeconds(60 * value);
	}
	
	public Timestamp addHours(int value) {
		return addMinutes(60 * value);
	}
	
	public Timestamp addDays(int value) {
		return addHours(24 * value);
	}
	
	public Timestamp subMilliseconds(long value) {
		this.epoch -= value;
		return this;
	}
	
	public Timestamp subSeconds(int value) {
		return subMilliseconds(1000 * value);
	}
	
	public Timestamp subMinutes(int value) {
		return subSeconds(60 * value);
	}
	
	public Timestamp subHours(int value) {
		return subMinutes(60 * value);
	}
	
	public Timestamp subDays(int value) {
		return subHours(24 * value);
	}
	
	public long differenceInMs(Timestamp timestamp) {
		return TimeUnit.MILLISECONDS.convert((timestamp.epoch - this.epoch), TimeUnit.MILLISECONDS);
	}
	
	public long difference(Timestamp timestamp, TimeUnit unit) {
		return unit.convert((timestamp.epoch - this.epoch), TimeUnit.MILLISECONDS);
	}
	
	public Timestamp copy() {
		return new Timestamp(epoch);
	}
	
	public String format(String format) {
		return new SimpleDateFormat(format).format(toDate());
	}
	
	/**
	 * @return epoch in unix format (seconds since 1970.01.01)
	 */
	public int toUnix() {
		return (int) (this.epoch / 1000);
	}
	
	public long toUnixMillis() {
		return this.epoch;
	}
	
	public Date toDate() {
		return new Date(toUnixMillis());
	}
	
	private static long currentEpoch() {
		return System.currentTimeMillis();
	}
	
	
}
