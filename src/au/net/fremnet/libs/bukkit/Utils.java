package au.net.fremnet.libs.bukkit;

import java.util.ArrayList;

public class Utils {
	public static String Join(ArrayList<String> coll, String delimiter) {
	    if (coll.isEmpty())
		return "";
	 
	    StringBuilder sb = new StringBuilder();
	 
	    for (String x : coll)
		sb.append(x + delimiter);
	 
	    sb.delete(sb.length()-delimiter.length(), sb.length());
	 
	    return sb.toString();
	}
}
