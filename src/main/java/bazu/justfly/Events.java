package bazu.justfly;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;


public class Events implements Listener {
    @EventHandler
    public static void onConsume(PlayerItemConsumeEvent event){
        if (event.getItem().getType().equals(Material.MILK_BUCKET)){
            Flying.tagFly();
        }
    }
}
