package nodrops.commands;

import java.util.UUID;

import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import nodrops.NoDrops;
import nodrops.utils.Common;

public class ToggleDrop extends BukkitCommand {
	NoDrops instance;

	public ToggleDrop(NoDrops instance, String name) {
		super(name);
		this.instance = instance;
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(Common.colorchat("&cYou can't execute this command from console!"));
			return false;
		}
		Player p = (Player) sender;
		UUID pUuid = p.getUniqueId();
		FileConfiguration config = instance.getConfiguration();
		if (!this.instance.getPlayerStatus().containsKey(pUuid)) {
			Boolean configuredStatus = config.getBoolean("default_status", true);
			this.instance.getPlayerStatus().put(pUuid, configuredStatus);
		}
		
		Boolean newStatus = !this.instance.getPlayerStatus().get(pUuid);
		this.instance.getPlayerStatus().replace(pUuid, newStatus);
		Common.sendToggleMessage(config, p, newStatus);
		return false;
	}

}
