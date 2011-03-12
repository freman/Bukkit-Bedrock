package au.net.fremnet.bukkit.Bedrock;

import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Bedrock extends JavaPlugin {
	static BedrockPlayerListener	playerListener			= new BedrockPlayerListener();
	static WeightedMaterialPicker	weightedMaterialPicker	= new WeightedMaterialPicker(7);

	// Check this number of blocks fore and back, left and right (5*2^2)
	static Integer					FlattenSquare			= 5;
	// Maximum height to check for adminuim blocks to
	static Integer					FlattenHeight			= 4;
	// Only perform flattening checks when player is below this height
	static Integer					CheckBelow				= 7;

	static {
		// Define a static list of weights for materials
		weightedMaterialPicker.add(Material.STONE, 50);
		weightedMaterialPicker.add(Material.LAPIS_ORE, 1);
		weightedMaterialPicker.add(Material.COAL_ORE, 1);
		weightedMaterialPicker.add(Material.IRON_ORE, 0.8);
		weightedMaterialPicker.add(Material.GOLD_ORE, 0.5);
		weightedMaterialPicker.add(Material.REDSTONE_ORE, 0.5);
		weightedMaterialPicker.add(Material.DIAMOND_ORE, 0.1);
	}

	@Override
	public void onDisable() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onEnable() {
		PluginDescriptionFile pdfFile = this.getDescription();
		PluginManager pm = getServer().getPluginManager();

		pm.registerEvent(Event.Type.PLAYER_MOVE, playerListener, Event.Priority.Normal, this);

		System.out.println(pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled :)");

	}

}
