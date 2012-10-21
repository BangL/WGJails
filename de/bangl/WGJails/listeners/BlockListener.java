package de.bangl.WGJails.listeners;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import de.bangl.WGJails.PermissionsManager.Permission;
import de.bangl.WGJails.WGJailsPlugin;

public class BlockListener implements Listener {

	@EventHandler (priority = EventPriority.LOWEST)
	public void onBlockPlace(BlockPlaceEvent e) {
		if (!canPlayerBuildHere(e.getBlock(), e.getPlayer())) 
			e.setCancelled(true);
	}
	
	@EventHandler (priority = EventPriority.LOWEST)
	public void onBlockBreak(BlockBreakEvent e) {
		if (!canPlayerBuildHere(e.getBlock(), e.getPlayer())) 
			e.setCancelled(true);
	}
	
	@EventHandler (priority = EventPriority.LOWEST)
	public void onBlockDamage(BlockDamageEvent e) {
		if (!canPlayerBuildHere(e.getBlock(), e.getPlayer())) 
			e.setCancelled(true);	
	}
	
	public boolean canPlayerBuildHere(Block b, Player p) {
		if (!WGJailsPlugin.jail.isInside(b.getLocation()))
			return true;
		
		if (!p.hasPermission(Permission.BUILD.node))
			return false;
		return true;
	}
	
}
