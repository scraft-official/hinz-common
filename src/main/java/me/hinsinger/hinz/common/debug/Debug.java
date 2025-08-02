package me.hinsinger.hinz.common.debug;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import me.hinsinger.hinz.common.debug.source.DebugSource;
import me.hinsinger.hinz.common.system.SystemUtil;

public class Debug {
	public final static String DEBUG_FILTER_VARIABLE_NAME = "monolith-debug-filter";
	
	private static Set<String> DEBUG_FILTER = new HashSet<>();
	
	static {
		String sources = System.getProperty(DEBUG_FILTER_VARIABLE_NAME);
		
		if(sources != null && !"".equals(sources)) {
			DEBUG_FILTER.addAll(Arrays.stream(sources.split(","))
					.collect(Collectors.toSet())
				);
		}
		
		if(DEBUG_FILTER.size() > 0) {
			Debug.finfo("DEBUG_FILTER >> Selected %s debug source filters: %s", DEBUG_FILTER.size(), String.join(", ", DEBUG_FILTER.toArray(new String[0])));
		}
		else {
			Debug.warn("DEBUG_FILTER >> No debug source filter selected!");
			Debug.fwarn("DEBUG_FILTER >> To show debug messages use VM argument: -D%s=source1,source2,source3", DEBUG_FILTER_VARIABLE_NAME);
		}
	}
	
	public static void debug(DebugSource source, Object object) {
		String.format(DEBUG_FILTER_VARIABLE_NAME);
	}
	
	public static void info(Object object) {
		finfo(object.toString());
	}
	
	public static void warn(Object object) {
		fwarn(object.toString());
	}
	
	public static void error(Object object) {
		ferror(object.toString());
	}
	
	public static void fdebug(DebugSource source, String format, Object... args) {	
		if(!shouldDebug(source)) return;
		
		String trace = SystemUtil.getCallerTrace();
		String sourceIdentifier = source.getIdentifier();
		String sourceGroups = String.join(", ", source.getGroups());
		
		System.out.println("--");
		System.out.println(String.format(">> (DEBUG) [tr: %s] [sr: %s | %s]", trace,  sourceIdentifier, sourceGroups));
		System.out.println(String.format(">> " + format, args));
		System.out.println();
	}
	
	public static void finfo(String format, Object... args) {
		log("INFO", format, args);
	}
	
	public static void fwarn(String format, Object... args) {
		log("WARN", format, args);
	}
	
	public static void ferror(String format, Object... args) {
		log("ERROR", format, args);
	}
	
	private static void log(String prefix, String format, Object... args) {
		log(prefix + " : " + String.format(format, args));
	}
	
	private static void log(String text) {
		System.out.println(text);
	}
	
	private static boolean shouldDebug(DebugSource source) {
		if(DEBUG_FILTER.contains(source.getIdentifier()) || DEBUG_FILTER.contains("ALL") || DEBUG_FILTER.contains("ANY")) return true;
		
		for(String group : source.getGroups()) {
			if(DEBUG_FILTER.contains(group)) return true;
		}
		
		return false;
	}
	
	
}
