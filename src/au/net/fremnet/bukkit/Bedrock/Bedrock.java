/*
    Bedrock Bukkit plugin for Minecraft
    Copyright (C) 2011 Shannon Wynter (http://fremnet.net/)

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package au.net.fremnet.bukkit.Bedrock;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.config.Configuration;

public class Bedrock extends JavaPlugin {
	protected final static Logger logger = Logger.getLogger("Minecraft");
    public static final String name = "Bedrock";
	
	static BedrockPlayerListener	playerListener			= new BedrockPlayerListener();
	static WeightedMaterialPicker	weightedMaterialPicker	= null;

	// Check this number of blocks fore and back, left and right (5*2^2)
	static Integer					FlattenSquare			= 5;
	// Maximum height to check for adminuim blocks to
	static Integer					FlattenHeight			= 4;
	// Only perform flattening checks when player is below this height
	static Integer					CheckBelow				= 7;
	// Check to see if there is a wall of adminuim before removing the blocks
	static boolean                  CheckWall               = false;
	static Integer                  CheckWallLevel          = 7;
	// Force Layer 0 to bedrock
	static boolean                  ForceLayerZero          = true;
	
	private void loadConfiguration() {
		Configuration cfg = getConfiguration();
		FlattenSquare  = cfg.getInt("flatten.square", 5);
		FlattenHeight  = cfg.getInt("flatten.height", 4);
		CheckBelow     = cfg.getInt("check.below", 7);
		CheckWall      = cfg.getBoolean("check.wall", false);
		ForceLayerZero = cfg.getBoolean("force.layer.zero", true);
		
		List<String> materialList = cfg.getStringList("materials", null);
		if (materialList.size() == 0) {
			materialList = cfg.getStringList("material", null);
			if (materialList.size() == 0) {
				log("Materials not configured, using defaults");
				materialList.add(Material.STONE.name() + ":1000");
				materialList.add(Material.DIAMOND_ORE.name() + ":0.1");
				materialList.add(Material.COAL_ORE.name() + ":1.0");
				materialList.add(Material.IRON_ORE.name() + ":0.8");
				materialList.add(Material.GOLD_ORE.name() + ":0.5");
				materialList.add(Material.REDSTONE_ORE.name() + ":0.5");
				materialList.add(Material.LAPIS_ORE.name() + ":0.5");
			}
			else {
				log("Renaming material to materials");
				cfg.removeProperty("material");
			}
			cfg.setProperty("materials", materialList);
		}

		weightedMaterialPicker = new WeightedMaterialPicker(materialList.size());
		
		for (Integer i = 0; i < materialList.size(); i++) {
			String[] split = materialList.get(i).split(":", 2);
			Material material = Material.getMaterial(split[0]);
			if (material == null) {
				log(split[0] + " is an unknown material, converting to stone");
				material = Material.STONE;
			}
			if (!material.isBlock()) {
				log(split[0] + " is not a block material, converting to stone");
				material = Material.STONE;
			}
			double weight = 0.0;
			try {
				weight = Double.parseDouble(split[1]);
			}
			catch (Exception e) {
				log(split[1] + " is not a valid weight, setting to 1");
				weight = 1.0;
			}
			
			weightedMaterialPicker.add(material, weight);
		}

		cfg.save();
	}
	
	@Override
	public void onDisable() {
		// TODO Auto-generated method stub
	}

	@Override
	public void onEnable() {
		PluginDescriptionFile pdfFile = this.getDescription();
		PluginManager pm = getServer().getPluginManager();
	
		loadConfiguration();
		
		pm.registerEvent(Event.Type.PLAYER_MOVE, playerListener, Event.Priority.Normal, this);

		log("Version " + pdfFile.getVersion()+ " - Copyright 2011 - Shannon Wynter (http://fremnet.net) is enabled");

	}

	public static void log(String txt) {
		logger.log(Level.INFO, String.format("[%s] %s", name, txt));
    }
}
