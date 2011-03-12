package au.net.fremnet.bukkit.Bedrock;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerMoveEvent;

public class BedrockPlayerListener extends PlayerListener {
	@Override
	public void onPlayerMove(PlayerMoveEvent event) {
		World world = event.getTo().getWorld();
		Location from = event.getFrom();
		Location to = event.getTo();

		System.out.println(String.format("Got movement %d: %d,%d > %d,%d",
			to.getBlockY(),
			to.getBlockX(),
			to.getBlockZ(),
			from.getBlockX(),
			from.getBlockZ()
		));

		
		// Don't bother checking below this height
		if (to.getBlockY() - 1 > Bedrock.CheckBelow) return;

		// Work out the range to check
		if (from.getBlockY() - 1 > Bedrock.CheckBelow) {
			// if player is just stepping below the CheckBelow threshold, check
			// everything
			int startX = to.getBlockX() - Bedrock.FlattenSquare;
			int endX = to.getBlockX() + Bedrock.FlattenSquare;
			int startZ = to.getBlockZ() - Bedrock.FlattenSquare;
			int endZ = to.getBlockZ() + Bedrock.FlattenSquare;
			replace(world, startX, startZ, endX, endZ);

		}
		else {
			if (to.getBlockX() != from.getBlockX()) {
				int startX = source(from.getBlockX(), to.getBlockX(), false);
				int endX = source(from.getBlockX(), to.getBlockX(), true);
				int startZ = source(from.getBlockZ(), to.getBlockZ(), false);
				int endZ = startZ + Bedrock.FlattenSquare * 2;
				replace(world, startX, startZ, endX, endZ);
			}
			if (to.getBlockZ() != from.getBlockZ()) {
				int startX = source(from.getBlockX(), to.getBlockX(), false);
				int endX = startX + Bedrock.FlattenSquare * 2;
				int startZ = source(from.getBlockZ(), to.getBlockZ(), false);
				int endZ = source(from.getBlockZ(), to.getBlockZ(), true);
				replace(world, startX, startZ, endX, endZ);
			}
		}
	}

	private void replace(World world, int startX, int startZ, int endX, int endZ) {
		for (int x = startX; x <= endX; x++)
			for (int z = startZ; z <= endZ; z++) {
				// Blocks above level 0
				for (int y = 1; y <= Bedrock.FlattenHeight; y++)
					if (world.getBlockAt(x, y, z).getType() == Material.BEDROCK)
						world.getBlockAt(x, y, z).setType(Bedrock.weightedMaterialPicker.get());
				if (world.getBlockAt(x, 0, z).getType() != Material.BEDROCK) world.getBlockAt(x, 0, z).setType(Material.BEDROCK);
			}
	}
	
	private int source(int from, int to, boolean inv) {
		if (from > to) {
			if (inv) {
				return from - Bedrock.FlattenSquare;
			}
			else {
				return to - Bedrock.FlattenSquare;
			}
		}
		if (inv) {
			return from + Bedrock.FlattenSquare;
		}
		return to + Bedrock.FlattenSquare;
	}
}
