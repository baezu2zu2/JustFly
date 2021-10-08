package bazu.justfly;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class JustFly extends JavaPlugin {

    private static JustFly inst;

    public static final BukkitRunnable FLY = new BukkitRunnable() {
        @Override
        public void run() {
            List<Player> players = (List<Player>) Bukkit.getOnlinePlayers();
            for (Player player:players) {
                Flying.tagFly(player);
                Flying.notTagFly(player);
            }
        }
    };

    public static final BukkitRunnable DETECT = new BukkitRunnable() {
        @Override
        public void run() {
            List<Player> players = (List<Player>) Bukkit.getOnlinePlayers();
            for (Player player:players) {
                Detect.detactLand(player);
            }
        }
    };

    public static BukkitRunnable fly = FLY;

    public static BukkitRunnable detect = DETECT;

    public static JustFly getInst() {
        return inst;
    }

    @Override
    public void onEnable() {

        inst = this;

        Bukkit.getPluginManager().registerEvents(new Flying(), this);
        Bukkit.getPluginManager().registerEvents(new Events(), this);
        Bukkit.getPluginCommand("justfly").setExecutor(new Commands());
        Bukkit.getPluginCommand("justfly").setTabCompleter(new Commands());

        fly.runTaskTimer(this, 0, 10);

        detect.runTaskTimer(this, 0, 10);

    }

    private static void printTextLoc(String label, Location loc){
        Bukkit.broadcastMessage(label+" : "+loc.getX()+", "+loc.getY()+", "+loc.getZ());
    }
}
