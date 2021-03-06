package com.thepooe.petto;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.Random;

/**
 * Created by U6043820 on 1/11/2017.
 */
public class PettoCommandCreate implements CommandExecutor {

    /**
     * Create a pet for the user that uses the command
     * @param usr
     * @param cmd
     * @param label
     * @param args
     * @return
     */
    @Override
    public boolean onCommand(CommandSender usr, Command cmd, String label, String args[]) {

        String commandReceiver = args[0];

        // Check if the command is correctly used
        if (cmd.getName().equalsIgnoreCase("petto")
                && label.equalsIgnoreCase("create")) {

            // If the command is being executed from player
            if (usr instanceof Player) {
                try {
                    return summonPet((Player) usr, EntityType.WOLF);
                } catch (Exception e) {
                    usr.sendMessage("[ERROR]: Cannot summon the pet");
                    return false;
                }
            }

            // If the command is being executed from console
            if (usr instanceof ConsoleCommandSender) {
                // Try to parse the String into Player class
                Player player;
                try {
                    player = Bukkit.getServer().getPlayer(commandReceiver);
                } catch (Exception e) {
                    usr.sendMessage("[ERROR]: Cannot find the player " + commandReceiver + ".");
                    return true;
                }
                return summonPet(player, EntityType.WOLF);
            }
        }
        // Not entering any loop, exiting false
        return false;
    }

    /**
     * Summon a pet around the player radius
     * @param player A pet to be summoned
     * @param petType A pet type
     * @return
     */
    private boolean summonPet(Player player, EntityType petType) {
        Location playerLocation = player.getLocation();
        World playerWorld = player.getWorld();
        Random r = new Random();

        int closeRadius = 10;
        int farRadius = 100;

        int x = r.nextInt(farRadius - closeRadius) + closeRadius;
        int z = r.nextInt(farRadius - closeRadius) + closeRadius;

        Location L = new Location(
                playerWorld,
                x,
                playerWorld.getHighestBlockYAt(playerLocation),
                z);
        L.getWorld().spawnEntity(L, EntityType.WOLF);
        return true;
    }
}
