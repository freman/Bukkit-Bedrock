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

import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import au.net.fremnet.libs.bukkit.Copyright;

public class Bedrock extends JavaPlugin {
	static BedrockPlayerListener	playerListener			= new BedrockPlayerListener();
	static WeightedMaterialPicker	weightedMaterialPicker	= new WeightedMaterialPicker(7);

	// Check this number of blocks fore and back, left and right (5*2^2)
	static Integer					FlattenSquare			= 5;
	// Maximum height to check for adminuim blocks to
	static Integer					FlattenHeight			= 4;
	// Only perform flattening checks when player is below this height
	static Integer					CheckBelow				= 7;
	static Boolean                  CopyrightShown          = false;

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
		Copyright.show(this, "2011 - Shannon Wynter (http://fremnet.net)", true);

	}

}
