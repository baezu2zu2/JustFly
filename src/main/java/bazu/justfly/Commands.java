package bazu.justfly;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static bazu.justfly.Fly.*;

public class Commands implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player){
            if (label.equalsIgnoreCase("justfly")) {
                if (args.length == 1){
                    if (args[0].equalsIgnoreCase("start")) {
                        justFlyStart((Player)sender);

                    } else if (args[0].equalsIgnoreCase("end")) {
                        justFlyEnd((Player) sender);
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
                completer.add("start");
                completer.add("end");
            }
        }

        completer.sort(null);

        return completer;
    }

    private static void justFlyStart(Player sender){
        List<Player> players = (List<Player>) Bukkit.getOnlinePlayers();

        if (!sender.getScoreboardTags().contains(PLAYER_TAG)) {

            for (Player player : players) {
                player.addScoreboardTag(PLAYER_TAG);
                if (!player.isSneaking()) {
                    player.addScoreboardTag(FLY);
                }else {
                    player.addScoreboardTag(NOT_FLY);
                }
            }

            Bukkit.broadcastMessage("저스트 플라이가 시작되었습니다!");
            Bukkit.broadcastMessage("웅크리기를 조절해서 내려가거나 올라가세요!");

        } else {
            sender.sendMessage("저스트 플라이가 이미 시작되었습니다!");
        }
    }

    private static void justFlyEnd(Player sender){
        List<Player> players = (List<Player>) Bukkit.getOnlinePlayers();
        if (sender.getScoreboardTags().contains(PLAYER_TAG)) {
            for (Player player : players) {
                player.removeScoreboardTag(PLAYER_TAG);
                player.removeScoreboardTag(FLY);
                player.removeScoreboardTag(NOT_FLY);
            }

            Bukkit.broadcastMessage("저스트 플라이가 끝났습니다!");

        } else {
            sender.sendMessage("저스트 플라이가 이미 끝났습니다!");
        }
    }
}
