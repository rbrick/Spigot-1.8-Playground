package me.rbrickis.testing;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;

/**
 * Created by Ryan on 11/30/2014
 * <p/>
 * Project: Spigot-1.8
 */
public class Main extends JavaPlugin implements Listener {


    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);

        /*
          Animated armor stand! Simple, but cool.
         */
        final ArmorStand armorStand = Bukkit.getWorld("world").spawn(new Location(Bukkit.getWorld("world"), 0, 90, 0), ArmorStand.class);
        armorStand.setArms(true);
        armorStand.setBasePlate(false);
        armorStand.setGravity(false);

        armorStand.setHelmet(skullWithOwner("rbrick"));
        armorStand.setLeftArmPose(new EulerAngle(-3, 0, 0));
        armorStand.setRightArmPose(new EulerAngle(-3, 0, 0));

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new BukkitRunnable() {
            boolean subtract = false;
            @Override
            public void run() {
              if(armorStand.isValid()) {
                  if (armorStand.getLeftArmPose().getX() <= 1.0 && armorStand.getRightArmPose().getZ() <= 1.0 && !subtract) {
                      // add
                      EulerAngle rightArm = armorStand.getRightArmPose().add(0, 0, 0.1);
                      EulerAngle leftArm = armorStand.getLeftArmPose().add(0, 0, 0.1);
                      armorStand.setLeftArmPose(leftArm);
                      armorStand.setRightArmPose(rightArm);
                      System.out.println(String.format("Left Arm Pose: %f , Right Arm Pose: %f", leftArm.getZ(), rightArm.getZ()));

                      if (rightArm.getZ() >= 1.0 && leftArm.getZ() >= 1.0) {
                          subtract = true;
                      }

                  } else if (subtract) {
                      EulerAngle rightArm = armorStand.getRightArmPose().subtract(0, 0, 0.1);
                      EulerAngle leftArm = armorStand.getLeftArmPose().subtract(0, 0, 0.1);
                      armorStand.setLeftArmPose(leftArm);
                      armorStand.setRightArmPose(rightArm);
                      System.out.println(String.format("Left Arm Pose: %f , Right Arm Pose: %f", leftArm.getZ(), rightArm.getZ()));

                      if (rightArm.getZ() <= -1.0 && leftArm.getZ() <= -1.0) {
                          subtract = false;
                      }
                  }

              } else {
                  this.cancel();
              }
            }
        }, 20, 5);


    }

    public ItemStack skullWithOwner(String name) {
        ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
        SkullMeta meta = (SkullMeta) head.getItemMeta();
        meta.setOwner(name);
        head.setItemMeta(meta);
        return head;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        sendToActionBar(e.getPlayer());
        sendTabMessage(e.getPlayer());
    }

    /*
       Header & Footers in tab.
     */
    public void sendTabMessage(Player player) {
      WrappedServerPlayTabPacket tabPacket = new WrappedServerPlayTabPacket("{\"text\":\"§aWelcome to, §6HCSoups.com!\"}", "{\"text\":\"§aThis is a test!\"}");
      ReflectionUtils.sendPacket(player, tabPacket.getPacket());
    }

    /*
      Sends a message above the action bar (hot bar).
     */
    public void sendToActionBar(Player player) {
        WrappedServerPlayChatPacket chatPacket = new WrappedServerPlayChatPacket("{\"text\":\"§cWelcome, §a" + player.getName() + "\"}", (byte) 2);
        ReflectionUtils.sendPacket(player, chatPacket.getPacket());
    }

}
