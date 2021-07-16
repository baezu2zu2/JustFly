package bazu.justfly;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class JustFly extends JavaPlugin {

    public static final double PLUGIN_VERSION = 1.0;

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new Fly(), this);
        Bukkit.getPluginCommand("justfly").setExecutor(new Commands());
        Bukkit.getPluginCommand("justfly").setTabCompleter(new Commands());

        new BukkitRunnable() {
            @Override
            public void run() {
                List<Player> players = (List<Player>) Bukkit.getOnlinePlayers();
                for (Player player:players) {
                    if (!player.isSleeping()) {
                        Fly.tagFly(player);
                        Fly.notTagFly(player);
                    }
                }
            }
        }.runTaskTimer(this, 0, 10);

        new BukkitRunnable() {
            @Override
            public void run() {
                List<Player> players = (List<Player>) Bukkit.getOnlinePlayers();
                for (Player player:players) {
                    if (!player.isSleeping()) {
                        Detact.detactLand(player);
                    }
                }
            }
        }.runTaskTimer(this, 0, 10);

    }

    private static void printTextLoc(String label, Location loc){
        Bukkit.broadcastMessage(label+" : "+loc.getX()+", "+loc.getY()+", "+loc.getZ());
    }
}
