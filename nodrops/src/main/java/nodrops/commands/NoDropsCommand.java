package nodrops.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;

import nodrops.NoDrops;
import nodrops.utils.Common;

public class NoDropsCommand extends BukkitCommand {
	NoDrops instance;

	public NoDropsCommand(NoDrops instance, String name) {
		super(name);
		this.instance = instance;
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		if (!sender.hasPermission("nodrops.reload")) {
			sender.sendMessage(Common.colorchat("&cYou don't have permission!"));
			return false;
		}
		
		if(args.length < 0) {
			return false;
		}
		String p1 = args[0];
		switch(p1){
			case "reload":
				instance.configReload();
				sender.sendMessage(Common.colorchat("&6[&bNoDrops&6] &8- &7config reloaded"));
				return false;
			default:
				return false;
		}
		
	}

}
