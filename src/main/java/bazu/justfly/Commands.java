package bazu.justfly;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

import static bazu.justfly.Fly.*;

public class Commands implements CommandExecutor, TabCompleter {
    String[] arguments = new String[]{"start", "end", "add", "remove"};

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player){
            if (label.equalsIgnoreCase("justfly")) {
                if (args.length == 1){
                    if (args[0].equalsIgnoreCase(arguments[0])) {
                        justFlyStart();

                    } else if (args[0].equalsIgnoreCase(arguments[1])) {
                        justFlyEnd();
                    }
                }else if (args.length == 2){
                    if (args[0].equalsIgnoreCase(arguments[2])){
                        if (Bukkit.getPlayer(args[1]) != null) {
                            justFlyStart(Bukkit.getPlayer(args[1]));
                        }
                    }else if (args[0].equalsIgnoreCase(arguments[3])){
                        if (Bukkit.getPlayer(args[1]) != null){
                            justFlyEnd(Bukkit.getPlayer(args[1]));
                        }
                    }
                }
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        ArrayList<String> completer = new ArrayList<>();

        if (label.equalsIgnoreCase("justfly")){
            if (args.length == 1){
                for (String str:arguments) {
                    try {
                        if (str.substring(0, args[0].length()).equalsIgnoreCase(args[0])) {
                            completer.add(str);
                        }
                    }catch (StringIndexOutOfBoundsException ignored){}
                }
            }else if (args.length == 2
                    && (args[0].equalsIgnoreCase(arguments[2]) || args[0].equalsIgnoreCase(arguments[3]))){
                for (Player player:Bukkit.getOnlinePlayers()) {
                    String str = player.getName();

                    try {
                        if (str.substring(0, args[1].length()).equalsIgnoreCase(args[1])) {
                            completer.add(str);
                        }
                    }catch (StringIndexOutOfBoundsException ignored){}
                }
            }
        }

        completer.sort(null);

        return completer;
    }

    private static void justFlyStart(){
        List<Player> players = (List<Player>) Bukkit.getOnlinePlayers();

        for (Player player : players) {
            player.addScoreboardTag(PLAYER_TAG);
            if (!player.isSneaking()) {
                player.addScoreboardTag(FLY);
            } else {
                player.addScoreboardTag(NOT_FLY);
            }
            player.sendTitle(ChatColor.AQUA+"JustFly", ChatColor.GRAY+"bazu1229"+ChatColor.WHITE+" 만듬", 10, 20, 10);
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 0);
        }

        Bukkit.broadcastMessage("웅크리기를 조절해서 내려가거나 올라가세요.");
    }

    private static void justFlyStart(Player player){
        player.addScoreboardTag(PLAYER_TAG);
        if (!player.isSneaking()) {
            player.addScoreboardTag(FLY);
        }else {
            player.addScoreboardTag(NOT_FLY);
        }

        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 0);
        player.sendTitle(ChatColor.AQUA+"JustFly", ChatColor.GRAY+"bazu1229"+ChatColor.WHITE+"만듬", 10, 20, 10);
        player.sendMessage("웅크리기를 조절해서 내려가거나 올라가세요!");
    }

    private static void justFlyEnd(){
        List<Player> players = (List<Player>) Bukkit.getOnlinePlayers();
        for (Player player : players) {
            player.removeScoreboardTag(PLAYER_TAG);
            player.removeScoreboardTag(FLY);
            player.removeScoreboardTag(NOT_FLY);
        }

        Bukkit.broadcastMessage("저스트 플라이가 끝났습니다!");
    }

    private static void justFlyEnd(Player player){
        player.removeScoreboardTag(PLAYER_TAG);
        player.removeScoreboardTag(FLY);
        player.removeScoreboardTag(NOT_FLY);

        player.sendMessage("저스트 플라이가 끝났습니다!");
    }
}
