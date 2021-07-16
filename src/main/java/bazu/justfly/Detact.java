package bazu.justfly;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.BoundingBox;

import java.util.List;

import static bazu.justfly.Fly.PLAYER_TAG;


public class Detact {

    private static double maxdown = 0.2;

    public static boolean landDetact(Player player){
        Location plyFeetLoc = player.getLocation().clone();

        Location plyM5Loc = player.getLocation().clone();
        plyM5Loc.add(0, 0.6, 0);

        if (isBlock(plyM5Loc, player)){
            return true;
        }

        for (double y = 0;y <= maxdown;y+=0.1) {
            plyFeetLoc.add(0, -y, 0);

            if ((isBlock(plyFeetLoc, player) || plyFeetLoc.getBlock().isLiquid())){
                return true;
            }
        }

        return false;
    }

    public static boolean getXSpan(Location loc, Player player, int i){
        if (Math.abs(i) != 1){
            throw new IllegalArgumentException();
        }

        Location plyFeetLoc = loc.clone();
        Location plyFeetSideLoc = plyFeetLoc.clone();

        Location plyM5Loc = player.getLocation().clone();
        plyM5Loc.add(0.3*i, -0.6, 0);

        if (isBlock(plyM5Loc, player)){
            return true;
        }

        for (double y = 0;y <= maxdown;y+=0.1) {
            plyFeetLoc.add(-0.1*i, -y, 0);
            plyFeetSideLoc.add(0.3*i, -y, 0);

            if (!isBlock(plyFeetLoc, player)) {

                if ((isBlock(plyFeetSideLoc, player) || plyFeetSideLoc.getBlock().isLiquid())) {
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean getZSpan(Location loc, Player player, int i){
        if (Math.abs(i) != 1){
            throw new IllegalArgumentException();
        }

        Location plyFeetLoc = loc.clone();
        Location plyFeetSideLoc = plyFeetLoc.clone();

        Location plyM5Loc = player.getLocation().clone();
        plyM5Loc.add(0, -0.6, 0.3*i);

        if (isBlock(plyM5Loc, player)){
            return true;
        }

        for (double y = 0;y <= maxdown;y+=0.1) {
            plyFeetLoc.add(0, -y, -0.1*i);
            plyFeetSideLoc.add(0, -y, 0.3 * i);

            if (!isBlock(plyFeetLoc, player)) {


                if ((isBlock(plyFeetSideLoc, player) || plyFeetSideLoc.getBlock().isLiquid())) {
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean getSideSpan(Location loc, Player player, int i, int j){
        if (Math.abs(i) != 1){
            throw new IllegalArgumentException();
        }

        Location plyFeetLoc = loc.clone();
        Location plyFeetSideLoc = plyFeetLoc.clone();

        Location plyM5Loc = player.getLocation().clone();
        plyM5Loc.add(0.3*i, -0.6,0.3*i);

        if (isBlock(plyM5Loc, player)){
            return true;
        }

        for (double y = 0;y <= maxdown;y+=0.1) {
            plyFeetLoc.add(-0.1*i, -y, -0.1*j);
            plyFeetSideLoc.add(0.3*i, -y, 0.3*j);

            if (!(isBlock(plyFeetLoc, player))) {

                if ((isBlock(plyFeetSideLoc, player) || plyFeetSideLoc.getBlock().isLiquid())) {
                    return true;
                }
            }
        }

        return false;
    }

    public static void playerWither(Player player, int amf, String message){
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.RED + message));

        player.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 20, amf-1,
                false, false));
    }


    private static boolean isBlock(Location loc, Player player){
        Block block = loc.getBlock();
        boolean rst = block.getType().isSolid()
                || Tag.CLIMBABLE.isTagged(block.getType())
                || Tag.CARPETS.isTagged(block.getType());

        if (block.getType().equals(Material.NETHERITE_BLOCK)){
            rst = false;
        }else if (Tag.FENCES.isTagged(block.getType()) || Tag.FENCE_GATES.isTagged(block.getType())){
            rst = true;
        }else if (Tag.DOORS.isTagged(loc.getBlock().getType()) && Tag.DOORS.isTagged(player.getLocation().getBlock().getType())){
            rst = false;
        }

        return rst;
    }

    @Deprecated
    private static Location settingLoc(Location loc, Location playerLoc, double x, double y, double z){
        Location rst = loc.clone();
        rst.setX(playerLoc.getX()+x);
        rst.setY(playerLoc.getY()+y);
        rst.setZ(playerLoc.getZ()+z);
        return rst;
    }

    public static void detactLand(List<Player> players){
        for (Player player:players) {
            if (player.getScoreboardTags().contains(PLAYER_TAG)){

                if (landDetact(player)){
                    playerWither(player, 4, "땅에 닿았습니다! 빨리 올라오세요!");

                } else if (detactXZSpan(player)){
                    playerWither(player, 5, "걸치지 말아주세요!");

                } else if (Detact.detactSideSpan(player)){
                    playerWither(player, 6, "끝에 걸치지 말아주세요!");

                }
            }
        }
    }

    public static void detactLand(Player player){
        if (player.getScoreboardTags().contains(PLAYER_TAG)){
            if (landDetact(player)){
                playerWither(player, 4, "땅에 닿았습니다! 빨리 올라오세요!");

            } else if (detactXZSpan(player)){
                playerWither(player, 5, "걸치지 말아주세요!");

            } else if (Detact.detactSideSpan(player)){
                playerWither(player, 6, "끝에 걸치지 말아주세요!");

            }
        }
    }

    private static boolean detactXZSpan(Player player){
        int[] dsts = new int[]{-1, 1};
        for (int i:dsts) {

            if (Detact.getXSpan(player.getLocation(), player, i) || Detact.getZSpan(player.getLocation(), player, i)){
                return true;
            }
        }

        return false;
    }

    private static boolean detactSideSpan(Player player){
        int[] dsts = new int[]{-1, 1};
        for (int i:dsts) {
            for (int j:dsts) {
                if (Detact.getSideSpan(player.getLocation(), player, i, j)){
                    return true;
                }
            }
        }

        return false;
    }
}