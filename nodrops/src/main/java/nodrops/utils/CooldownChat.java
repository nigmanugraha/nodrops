package nodrops.utils;

import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import nodrops.NoDrops;

public class CooldownChat extends BukkitRunnable {

	NoDrops instance;
	int cooldown;
	UUID pUuid;

	public CooldownChat(NoDrops instance, Player p) {
		this.instance = instance;
		this.pUuid = p.getUniqueId();
		cooldown = instance.getConfig().getInt("cooldown_message");
	}

	@Override
	public void run() {
		cooldown--;
		if(cooldown <=0) {
			this.instance.getPlayerCooldown().remove(this.pUuid);
			this.cancel();
		}
	}

}
