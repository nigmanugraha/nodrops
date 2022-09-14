package nodrops.listeners;

import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import nodrops.NoDrops;
import nodrops.utils.Common;

public class PlayerJoin implements Listener {
	NoDrops instance;

	public PlayerJoin(NoDrops instance) {
		this.instance = instance;
	}

	@EventHandler
	public void playerJoinEvent(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		UUID pUuid = p.getUniqueId();
		FileConfiguration config = this.instance.getConfiguration();
		if (this.instance.getPlayerStatus().containsKey(pUuid)) {
			Boolean status = this.instance.getPlayerStatus().get(pUuid);
			Common.sendToggleMessage(config, p, status);
			return;
		}
		Boolean configuredStatus = config.getBoolean("default_status", true);
		this.instance.getPlayerStatus().put(pUuid, configuredStatus);
		Common.sendToggleMessage(config, p, configuredStatus);
	}
}
