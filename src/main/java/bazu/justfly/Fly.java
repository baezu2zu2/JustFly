package bazu.justfly;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class Fly implements Listener {
    public static final String PLAYER_TAG = "justFly";
    public static final String NOT_FLY = "notFly";
    public static final String FLY = "fly";

    @EventHandler
    public void fly(PlayerToggleSneakEvent event) {
        if (event.getPlayer().getScoreboardTags().contains(PLAYER_TAG)) {
            if (!event.isSneaking()) {
                event.getPlayer().addScoreboardTag(FLY);
                event.getPlayer().removeScoreboardTag(NOT_FLY);
            } else {
                event.getPlayer().addScoreboardTag(NOT_FLY);
                event.getPlayer().removeScoreboardTag(FLY);
            }
        }
    }

    @EventHandler
    public void sleep(PlayerBedEnterEvent event){
        event.getPlayer().removePotionEffect(PotionEffectType.LEVITATION);
        event.getPlayer().removePotionEffect(PotionEffectType.SLOW_FALLING);
    }

    public static void tagFly(){
        List<Player> players = (List<Player>) Bukkit.getOnlinePlayers();
        for (Player player:players) {
            if (player.getScoreboardTags().contains(FLY)){

                player.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 20, 3,
                        false, false));
                player.removePotionEffect(PotionEffectType.SLOW_FALLING);

            }else if (player.getScoreboardTags().contains(NOT_FLY)){
                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 20, 0,
                        false, false));
                player.removePotionEffect(PotionEffectType.LEVITATION);
            }
        }
    }

    public static void tagFly(Player player){
        if (player.getScoreboardTags().contains(FLY)){

            player.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 20, 3,
                    false, false));
            player.removePotionEffect(PotionEffectType.SLOW_FALLING);

        }else if (player.getScoreboardTags().contains(NOT_FLY)){
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 20, 0,
                    false, false));
            player.removePotionEffect(PotionEffectType.LEVITATION);
        }
    }

    public static void notTagFly(){
        List<Player> players = (List<Player>) Bukkit.getOnlinePlayers();
        for (Player player:players) {
            if (!player.getScoreboardTags().contains(FLY) && !player.getScoreboardTags().contains(NOT_FLY)){

                player.removePotionEffect(PotionEffectType.LEVITATION);
                player.removePotionEffect(PotionEffectType.SLOW_FALLING);

            }
        }
    }

    public static void notTagFly(Player player){
        if (!player.getScoreboardTags().contains(FLY) && !player.getScoreboardTags().contains(NOT_FLY)){

            player.removePotionEffect(PotionEffectType.LEVITATION);
            player.removePotionEffect(PotionEffectType.SLOW_FALLING);

        }
    }
}
