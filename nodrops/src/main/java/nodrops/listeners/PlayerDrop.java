package nodrops.listeners;

import java.util.List;
import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

import nodrops.NoDrops;
import nodrops.utils.Common;

public class PlayerDrop implements Listener {
	NoDrops instance;

	public PlayerDrop(NoDrops instance) {
		this.instance = instance;
	}

	@EventHandler
	public void playerDropEvent(PlayerDropItemEvent e) {
		if (e.getPlayer().isOp()) {
			return;
		}
		Player p = e.getPlayer();
		UUID pUuid = p.getUniqueId();
		ItemStack item = e.getItemDrop().getItemStack();
		FileConfiguration config = this.instance.getConfiguration();
		List<String> registeredItems = config.getStringList("items");

		if (!registeredItems.contains(item.getType().name())) {
			return;
		}
		if (!this.instance.getPlayerStatus().containsKey(pUuid)) {
			Boolean configuredStatus = config.getBoolean("default_status", true);
			this.instance.getPlayerStatus().put(pUuid, configuredStatus);
		}
		Boolean status = this.instance.getPlayerStatus().get(pUuid);
		if (!status) {
			Common.sendAlertMessage(instance, p, status);
			return;
		}

		e.setCancelled(true);
		Common.sendAlertMessage(instance, p, status);
	}

}
