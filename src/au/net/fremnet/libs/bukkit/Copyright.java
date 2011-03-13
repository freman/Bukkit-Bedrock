package au.net.fremnet.libs.bukkit;

import java.io.File;
import java.net.URISyntaxException;
import java.util.HashMap;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class Copyright { 
	private static HashMap<String, Boolean> shown = new HashMap<String, Boolean>();
	
	public static void show(JavaPlugin plugin, String copyright) {
		show(plugin, copyright, false);
	}
	
	public static void show(JavaPlugin plugin, String copyright, Boolean gpl) {
		PluginDescriptionFile pd = plugin.getDescription();
		String className = plugin.getClass().getSimpleName();
		if (!shown.containsKey(className)) {
			String out = pd.getName() + " - Copyright " + copyright;
			if (gpl) {
				out += " - Source released under GPL3";
			}
			System.out.println(out);

			if (!pd.getAuthors().isEmpty()) {
				System.out.println("Extra credit to: " + Utils.Join(pd.getAuthors(), ", "));
			}
			
			try {
				File jarFile = new File(plugin.getClass().getProtectionDomain().getCodeSource().getLocation().toURI());
				if (!jarFile.getName().equalsIgnoreCase(className + ".jar")) {
					plugin.getServer().getLogger().warning(pd.getName() + ": Plugin may not be authentic!");
				}
			}
			catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			shown.put(className, true);
		}
	}
}
