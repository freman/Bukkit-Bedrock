package au.net.fremnet.libs.bukkit;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class Copyright {
	private static boolean shown = false;
	
	public static void show(JavaPlugin plugin, String copyright) {
		show(plugin, copyright, false);
	}
	
	public static void show(JavaPlugin plugin, String copyright, Boolean gpl) {
		PluginDescriptionFile pd = plugin.getDescription();
		if (!shown) {
			String out = pd.getName() + " - Copyright " + copyright;
			if (gpl) {
				out += " - Source released under GPL3";
			}
			System.out.println(out);

			if (!pd.getAuthors().isEmpty()) {
				System.out.println("Extra credit to: " + Utils.Join(pd.getAuthors(), ", "));
			}
			shown = true;
		}
	}
}
