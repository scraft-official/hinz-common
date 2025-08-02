package me.hinsinger.hinz.common.system;

public class SystemUtil {
	public static void wait(int millis) {
		try { Thread.sleep(millis); }
		catch (InterruptedException e) { e.printStackTrace(); }
	}
	
	public static String getCallerTrace() {
		StackTraceElement element;
		
		try { element = new Exception().getStackTrace()[2]; }
		catch(Exception e) { element = e.getStackTrace()[1]; }
		
		String[] name = element.getClassName().split("\\.");
		return name[name.length - 1] + ":" + element.getLineNumber();
	}
	
	public static String getName(Class<?> clazz) {
		return clazz.getSimpleName();
	}
	
	public static String getName(Object object) {
		return getName(object.getClass());
	}
}
