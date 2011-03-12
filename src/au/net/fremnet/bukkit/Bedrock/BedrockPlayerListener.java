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
		int startX, startZ;
		int endX, endZ;
		
		// Don't bother checking below this height
		if (to.getBlockY() > Bedrock.CheckBelow)
			return;
		
		// Work out the range to check
		if (from.getBlockY() > Bedrock.CheckBelow) {
			// if player is just stepping below the CheckBelow threshold, check everything
			startX = to.getBlockX() - Bedrock.FlattenSquare;
			endX   = to.getBlockX() + Bedrock.FlattenSquare;
			startZ = to.getBlockY() - Bedrock.FlattenSquare;
			endZ   = to.getBlockY() + Bedrock.FlattenSquare;
		}
		else {
			// Otherwise check just the blocks that'll now be in range
			startX = Math.min(to.getBlockX(), from.getBlockX()) - Bedrock.FlattenSquare;
			endX   = Math.max(to.getBlockX(), from.getBlockX()) + Bedrock.FlattenSquare;
			startZ = Math.min(to.getBlockZ(), from.getBlockZ()) - Bedrock.FlattenSquare;
			endZ   = Math.max(to.getBlockZ(), from.getBlockZ()) + Bedrock.FlattenSquare;
		}
		
		for (int x = startX; x <= endX; x++)
			for (int z = startZ; z <= endZ; z++) {
				// Blocks above level 0
				for (int y = 1; y <= Bedrock.FlattenHeight; y++)
					if (world.getBlockAt(x, y, z).getType() == Material.BEDROCK)
						world.getBlockAt(x, y, z).setType(Bedrock.weightedMaterialPicker.get());
				if (world.getBlockAt(x, 0, z).getType() != Material.BEDROCK)
					world.getBlockAt(x, 0, z).setType(Material.BEDROCK);
			}
	}
}
