package bazu.justfly;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

import static bazu.justfly.Flying.PLAYER_TAG;


public class Detect {

    public static void playerWither(Player player, int amf, String message){
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.RED + message));

        player.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 20, amf-1,
                false, false));
    }

    public static void detactLand(Player player){
        if (player.getScoreboardTags().contains(PLAYER_TAG)){
            if (!player.isSleeping()) {
                if (player.isOnGround()) {
                    playerWither(player, 4, "땅에 닿았습니다! 빨리 올라오세요!");

                }
            }
        }
    }
}