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

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class BedrockPlayerListener implements Listener {
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		World world = event.getTo().getWorld();
		Location from = event.getFrom();
		Location to = event.getTo();

		int
			toY   = (int)Math.floor(to.getY()),
			toX   = (int)Math.floor(to.getX()),
			toZ   = (int)Math.floor(to.getZ()),
			fromY = (int)Math.ceil(from.getY());

		// Don't bother checking below this height
		if (toY > Bedrock.CheckBelow) return;

		// Work out the range to check
		if (fromY > Bedrock.CheckBelow) {
			// if player is just stepping below the CheckBelow threshold, check
			// everything
			int startX = toX - Bedrock.FlattenSquare;
			int endX = toX + Bedrock.FlattenSquare;
			int startZ = toZ - Bedrock.FlattenSquare;
			int endZ = toZ + Bedrock.FlattenSquare;
			replace(world, startX, startZ, endX, endZ);

		}
		else {
			/**
			 * Turns out it's really hard to work out when a player has left one block
			 * and moved to another, here I check to see if the difference between
			 * from and too is greater than 0.1 before trying to work out what
			 * the change actually has been. then I go one step further by adding and
			 * subtracting one block to make sure at least 2 blocks with are covered
			 * (just in case a call was missed)
			 */
			if (Math.abs(to.getX() - from.getX()) > 0.1) {
				int startX = source(from.getX(), to.getX(), true) - 1;
				int endX = source(from.getX(), to.getX(), false) + 1;
				int startZ = toZ - Bedrock.FlattenSquare;
				int endZ = toZ + Bedrock.FlattenSquare;
				replace(world, startX, startZ, endX, endZ);
			}
			if (Math.abs(to.getZ() - from.getZ()) > 0.01) {
				int startX = toX - Bedrock.FlattenSquare;
				int endX = toX + Bedrock.FlattenSquare;
				int startZ = source(from.getZ(), to.getZ(), true) - 1;
				int endZ = source(from.getZ(), to.getZ(), false) + 1;
				replace(world, startX, startZ, endX, endZ);
			}
		}
	}

	private void replace(World world, int startX, int startZ, int endX, int endZ) {
		for (int x = startX; x <= endX; x++)
			for (int z = startZ; z <= endZ; z++) {
				// Blocks above level 0
				for (int y = 1; y <= Bedrock.FlattenHeight; y++)
					if (world.getBlockAt(x, y, z).getType().equals(Material.BEDROCK)) {
						if (Bedrock.CheckWall && world.getBlockAt(x, Bedrock.CheckWallLevel, z).getType().equals(Material.BEDROCK)) continue;
						world.getBlockAt(x, y, z).setType(Bedrock.weightedMaterialPicker.get());
					}
						
				if (world.getBlockAt(x, 0, z).getType() != Material.BEDROCK && Bedrock.ForceLayerZero)
					world.getBlockAt(x, 0, z).setType(Material.BEDROCK);
			}
	}
	
	private int source(Double from, Double to, boolean inv) {
		double result;
		if (from > to) {
			if (inv) {
				result =  from - Bedrock.FlattenSquare;
			}
			else {
				result = to - Bedrock.FlattenSquare;
			}
		}
		else if (inv) {
			result = from + Bedrock.FlattenSquare;
		}
		else {
			result = to + Bedrock.FlattenSquare;
		}
		
		return (int) Math.floor(result);
	}
}
