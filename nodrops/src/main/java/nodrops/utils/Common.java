package nodrops.utils;

import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import nodrops.NoDrops;

public class Common {
	public static String colorchat(String message) {
		return ChatColor.translateAlternateColorCodes('&', message);
	}

	public static void sendAlertMessage(NoDrops instance, Player p, Boolean status) {
		UUID pUuid = p.getUniqueId();
		if (instance.getPlayerCooldown().containsKey(pUuid)) {
			if(!instance.getPlayerCooldown().get(pUuid).isCancelled()) {				
				return;
			}
			instance.getPlayerCooldown().remove(pUuid);
		}

		CooldownChat cooldown = new CooldownChat(instance, p);
		instance.getPlayerCooldown().put(pUuid, cooldown);
		cooldown.runTaskTimer(instance, 20, 20);

		sendToggleMessage(instance.getConfig(), p, status);
	}

	public static void sendToggleMessage(FileConfiguration c, Player p, Boolean status) {
		String defaultMessage = "&6[&bNoDrops&6] &8- &7Your drop item prevention is %status%&7, you can toggle by command &c%command%";
		String message = c.getString("message", defaultMessage);
		String cmd = c.getString("command", "toggledrop");
		String disabledStatusMessage = c.getString("statusString_disabled", "&4Disabled");
		String enabledStatusMessage = c.getString("statusString_enabled", "&aActivated");
		String statusMessage = status ? enabledStatusMessage : disabledStatusMessage;
		message = message.replace("%status%", statusMessage);
		message = message.replace("%command%", cmd);

		p.sendMessage(colorchat(message));
	}
}
