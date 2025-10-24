package io.github.mcengine.extension.dlc.essential.example.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Handles the {@code /essentialdlcexample} command logic.
 */
public class EssentialDLCCommand implements CommandExecutor {

    /**
     * Executes the {@code /essentialdlcexample} command.
     *
     * @param sender  The source of the command.
     * @param command The command that was executed.
     * @param label   The alias used.
     * @param args    The command arguments.
     * @return {@code true} if the command was handled successfully.
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage("§aEssentialDLC example command executed!");
        return true;
    }
}
