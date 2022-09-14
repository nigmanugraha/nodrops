package nodrops;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import nodrops.commands.NoDropsCommand;
import nodrops.commands.ToggleDrop;
import nodrops.listeners.PlayerDrop;
import nodrops.listeners.PlayerJoin;
import nodrops.utils.CooldownChat;

public class NoDrops extends JavaPlugin {
	HashMap<UUID, Boolean> playerDropStatus;
	HashMap<UUID, CooldownChat> playerCooldownChat;

	private static NoDrops instance;
	private FileConfiguration config;

	public static NoDrops getInstance() {
		return instance;
	}

	public HashMap<UUID, Boolean> getPlayerStatus() {
		return this.playerDropStatus;
	}

	public HashMap<UUID, CooldownChat> getPlayerCooldown() {
		return this.playerCooldownChat;
	}
	
	public void configReload() {
		instance.reloadConfig();
		config = instance.getConfig();
	}
	
	public FileConfiguration getConfiguration() {
		return this.config;
	}

	@Override
	public void onEnable() {
		instance = this;
		playerDropStatus = new HashMap<UUID, Boolean>();
		playerCooldownChat = new HashMap<UUID, CooldownChat>();
		config = getConfig();
		config.options().copyDefaults();
		saveDefaultConfig();
		registerCommand();
		registerListener();
	}

	private void registerCommand() {
		try {
			final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");

			bukkitCommandMap.setAccessible(true);
			CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());

			String cmd = getConfig().getString("command", "toggledrop");
			String noDropCmd = "nodrops";
			commandMap.register(cmd, new ToggleDrop(instance, cmd));
			commandMap.register(noDropCmd, new NoDropsCommand(instance, noDropCmd));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void registerListener() {
		getServer().getPluginManager().registerEvents(new PlayerJoin(instance), this);
		getServer().getPluginManager().registerEvents(new PlayerDrop(instance), this);
	}
}
