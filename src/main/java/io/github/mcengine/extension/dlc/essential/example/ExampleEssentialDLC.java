package io.github.mcengine.extension.dlc.essential.example;

import io.github.mcengine.api.core.MCEngineCoreApi;
import io.github.mcengine.api.core.extension.logger.MCEngineExtensionLogger;
import io.github.mcengine.api.essential.extension.dlc.IMCEngineEssentialDLC;

import io.github.mcengine.extension.dlc.essential.example.command.EssentialDLCCommand;
import io.github.mcengine.extension.dlc.essential.example.listener.EssentialDLCListener;
import io.github.mcengine.extension.dlc.essential.example.tabcompleter.EssentialDLCTabCompleter;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Main class for the Essential DLC example module.
 * <p>
 * Registers the {@code /essentialdlcexample} command and related event listeners.
 */
public class ExampleEssentialDLC implements IMCEngineEssentialDLC {

    /**
     * Custom extension logger for this module, with contextual labeling.
     */
    private MCEngineExtensionLogger logger;

    /**
     * Initializes the Essential DLC example module.
     * Called automatically by the MCEngine core plugin.
     *
     * @param plugin The Bukkit plugin instance.
     */
    @Override
    public void onLoad(Plugin plugin) {
        // Initialize contextual logger once and keep it for later use.
        this.logger = new MCEngineExtensionLogger(plugin, "DLC", "EssentialExampleDLC");

        try {
            // Register event listener
            PluginManager pluginManager = Bukkit.getPluginManager();
            pluginManager.registerEvents(new EssentialDLCListener(plugin, this.logger), plugin);

            // Reflectively access Bukkit's CommandMap
            Field commandMapField = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            commandMapField.setAccessible(true);
            CommandMap commandMap = (CommandMap) commandMapField.get(Bukkit.getServer());

            // Define the /essentialdlcexample command
            Command essentialDlcExampleCommand = new Command("essentialdlcexample") {

                /**
                 * Handles command execution for /essentialdlcexample.
                 */
                private final EssentialDLCCommand handler = new EssentialDLCCommand();

                /**
                 * Handles tab-completion for /essentialdlcexample.
                 */
                private final EssentialDLCTabCompleter completer = new EssentialDLCTabCompleter();

                @Override
                public boolean execute(CommandSender sender, String label, String[] args) {
                    return handler.onCommand(sender, this, label, args);
                }

                @Override
                public List<String> tabComplete(CommandSender sender, String alias, String[] args) {
                    return completer.onTabComplete(sender, this, alias, args);
                }
            };

            essentialDlcExampleCommand.setDescription("Essential DLC example command.");
            essentialDlcExampleCommand.setUsage("/essentialdlcexample");

            // Dynamically register the /essentialdlcexample command
            commandMap.register(plugin.getName().toLowerCase(), essentialDlcExampleCommand);

            this.logger.info("Enabled successfully.");
        } catch (Exception e) {
            this.logger.warning("Failed to initialize ExampleEssentialDLC: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Called when the Essential DLC example module is disabled/unloaded.
     *
     * @param plugin The Bukkit plugin instance.
     */
    @Override
    public void onDisload(Plugin plugin) {
        if (this.logger != null) {
            this.logger.info("Disabled.");
        }
    }

    /**
     * Sets the unique ID for this module.
     *
     * @param id the assigned identifier (ignored; a fixed ID is used for consistency)
     */
    @Override
    public void setId(String id) {
        MCEngineCoreApi.setId("mcengine-essential-dlc-example");
    }
}
