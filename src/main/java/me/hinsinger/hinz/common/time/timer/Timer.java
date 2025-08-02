package me.hinsinger.hinz.common.time.timer;

import java.util.concurrent.TimeUnit;

public class Timer {
	private long timespan;
	
	public static Timer ofSeconds(int value) {
		return new TimerBuilder().seconds(value).build();
	}
	
	public static Timer ofMinutes(int value) {
		return new TimerBuilder().minutes(value).build();
	}
	
	public static Timer ofHours(int value) {
		return new TimerBuilder().hours(value).build();
	}
	
	public static Timer ofDays(int value) {
		return new TimerBuilder().days(value).build();
	}
	
	public static TimerBuilder builder() {
		return new TimerBuilder();
	}
	
	private Timer(long timespanInMilliseconds) {
		this.timespan = timespanInMilliseconds;
	}
	
	public Timer increment(int value, TimeUnit unit) {
		long toIncrease = TimeUnit.MILLISECONDS.convert(value, unit);
		timespan += toIncrease;
		return this;
	}
	
	public Timer decrement(int value, TimeUnit unit) {
		long toDecrease = TimeUnit.MILLISECONDS.convert(value, unit);
		timespan -= toDecrease;
		return this;
	}
	
	public Timer copy() {
		return new Timer(timespan);
	}
	
	public long toMillis() {
		return timespan;
	}
	
	public float toSeconds() {
		return (float) (timespan / 1000);
	}
	
	public float toMinutes() {
		return toSeconds() / 60;
	}
	
	public float toHours() {
		return toMinutes() / 60;
	}
	
	public float toDays() {
		return toHours() / 24;
	}
	
	/**
	 * @param format in "DD:HH:MM:SS:MS"
	 * @return formatted text with Timer values
	 */
	public String format(String format) {
        boolean hasDays = format.contains("DD");
        boolean hasHours = format.contains("HH");
        boolean hasMinutes = format.contains("MM");
        boolean hasSeconds = format.contains("SS");
        boolean hasMillis = format.contains("MS");

        int millis = (int) (timespan % 1000);
        int seconds = (int) toSeconds();
        int minutes = 0, hours = 0, days = 0;
   

        if (hasMinutes || hasHours || hasDays) {
            minutes = seconds / 60;
            seconds = seconds % 60;
        }

        if (hasHours || hasDays) {
            hours = minutes / 60;
            minutes = minutes % 60;
        }

        if (hasDays) {
            days = hours / 24;
            hours = hours % 24;
        }

        if (hasDays) {
            format = format.replace("DD", String.format("%02d", days));
        }
        if (hasHours) {
            format = format.replace("HH", String.format("%02d", hours));
        }
        if (hasMinutes) {
            format = format.replace("MM", String.format("%02d", minutes));
        }
        if (hasSeconds) {
            format = format.replace("SS", String.format("%02d", seconds));
        }
        if (hasMillis) {
            format = format.replace("ms", String.format("%03d", millis));
        }

        return format;
    }
	
	public static class TimerBuilder {
		private int seconds;
		private int minutes;
		private int hours;
		private int days;
		
		private TimerBuilder() {};
		
		public TimerBuilder seconds(int seconds) {
			this.seconds = seconds;
			return this;
		}
		
		public TimerBuilder minutes(int minutes) {
			this.minutes = minutes;
			return this;
		}
		
		public TimerBuilder hours(int hours) {
			this.hours = hours;
			return this;
		}
		
		public TimerBuilder days(int days) {
			this.days = days;
			return this;
		}
		
		public Timer build() {
			return new Timer(0)
					.increment(days, TimeUnit.DAYS)
					.increment(hours, TimeUnit.HOURS)
					.increment(minutes, TimeUnit.MINUTES)
					.increment(seconds, TimeUnit.SECONDS);
		}
	}
}
