package bazu.justfly;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

import static bazu.justfly.Flying.*;

public class Commands implements CommandExecutor, TabCompleter {
    String[] arguments = new String[]{"start", "end", "add", "remove"};
    private String[] tutorials = new String[]{
            "JustFly",
            "(야생 기준)난이도 : 중상",
            "저스트플라이는 하늘을 날며 야생을 하도록 의도된 플러그인입니다.",
            "웅크리기를 누르면 하락하고, 웅크리기를 떼면 상승합니다.",
            "저스트플라이의 명령어 입니다.",
            "-/justfly "+arguments[0]+"로 모든 플레이어를 게임에 참가시킵니다.",
            "-/justfly "+arguments[1]+"로 모든 플레이어를 게임에서 제외시킵니다.",
            "-/justfly "+arguments[2]+"로 특정 플레이어만 게임에 참가시킵니다.",
            "-/justfly "+arguments[3]+"로 특정 플레이어만 게임에서 제외시킵니다.",
            "\n",
            "재밌게 즐겨주세요!",
            "by "+ ChatColor.GRAY +"bazu1229"+ChatColor.WHITE
    };

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
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
                } else if (args[0].equalsIgnoreCase(arguments[3])){
                    if (Bukkit.getPlayer(args[1]) != null){
                        justFlyEnd(Bukkit.getPlayer(args[1]));
                    }
                }
            }
        }else if (label.equalsIgnoreCase("helpJustFly")){
            tutorial(sender);
        }
        return true;
    }

    private void tutorial(CommandSender sender){
        new BukkitRunnable(){

            @Override
            public void run() {
                Player player = null;

                if (sender instanceof Player){
                    player = (Player)sender;
                }
                for (String tutorial:tutorials) {
                    sender.sendMessage(tutorial);
                    if (player != null){
                        player.playSound(player.getLocation(), Sound.BLOCK_ANCIENT_DEBRIS_PLACE, 10, 1);
                    }

                    try {
                        Thread.sleep(700);
                    } catch (InterruptedException ignored) { }
                }
            }
        }.runTask(JustFly.getInst());
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
            justFlyStart(player);
        }
    }

    private static void justFlyStart(Player player){
        player.addScoreboardTag(PLAYER_TAG);
        if (!player.isSneaking()) {
            player.addScoreboardTag(FLY);
        }else {
            player.addScoreboardTag(NOT_FLY);
        }

        player.playSound(player.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 10, 1);
        player.sendTitle(ChatColor.AQUA+"JustFly", ChatColor.GRAY+"bazu1229"+ChatColor.WHITE+"만듬", 10, 20, 10);
        player.sendMessage("웅크리기를 조절해서 내려가거나 올라가세요!");
    }

    private static void justFlyEnd(){
        List<Player> players = (List<Player>) Bukkit.getOnlinePlayers();
        for (Player player : players) {
            justFlyEnd(player);
        }
    }

    private static void justFlyEnd(Player player){
        player.removeScoreboardTag(PLAYER_TAG);
        player.removeScoreboardTag(FLY);
        player.removeScoreboardTag(NOT_FLY);

        player.sendMessage("저스트 플라이가 끝났습니다!");
    }
}
