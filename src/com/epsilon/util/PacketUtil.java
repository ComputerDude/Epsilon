package com.epsilon.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import net.minecraft.server.v1_11_R1.EntityLiving;
import net.minecraft.server.v1_11_R1.NBTBase;
import net.minecraft.server.v1_11_R1.NBTTagByte;
import net.minecraft.server.v1_11_R1.NBTTagCompound;
import net.minecraft.server.v1_11_R1.NBTTagDouble;
import net.minecraft.server.v1_11_R1.NBTTagFloat;
import net.minecraft.server.v1_11_R1.NBTTagInt;
import net.minecraft.server.v1_11_R1.NBTTagList;
import net.minecraft.server.v1_11_R1.NBTTagLong;
import net.minecraft.server.v1_11_R1.NBTTagShort;
import net.minecraft.server.v1_11_R1.NBTTagString;
import net.minecraft.server.v1_11_R1.PacketPlayOutTitle;
import net.minecraft.server.v1_11_R1.PacketPlayOutTitle.EnumTitleAction;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_11_R1.inventory.CraftItemStack;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import static net.minecraft.server.v1_11_R1.IChatBaseComponent.ChatSerializer.a;

public class PacketUtil {

    /**
     * Send an action bar message to the player.
     *
     * @param player the player to send the actionbar to.
     * @param text the new text in the action bar.
     */
    public static void sendActionBar(Player player, String text) {
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutTitle(EnumTitleAction
                .ACTIONBAR, a(toJsonObject(text))));
    }

    /**
     * Turn the argument into a JSON object, in the form {"text":"&lt;argument&gt;"}.
     */
    public static String toJsonObject(String s) {
        return "{\"text\":\"" + s.replace("\\", "\\\\").replace("\"", "\\\"") + "\"}";
    }

    /**
     * Set an entity's NBT data.
     *
     * @param entity the target entity.
     * @param path the path of the tag to set, separated by periods (eg {@code SpawnPotentials.0.Type})
     * @param value the new value of the tag -- lists are represented by {@code List<?>} and compounds are represented
     * by {@code Map<String, ?>}. Unexpected things may happen if this argument is null.
     */
    public static void setEntityNBT(Entity entity, String path, Object value) {
        final net.minecraft.server.v1_11_R1.Entity nmsEntity = ((CraftEntity) entity).getHandle();
        final NBTTagCompound root = new NBTTagCompound();
        // Write the entity's NBT to our root compound
        nmsEntity.c(root);
        setNBT(root, path, value);
        // Write our root compound to the entity's NBT
        ((EntityLiving) nmsEntity).a(root);
    }

    /**
     * Get an entity's NBT data.
     * @param entity the target entity.
     * @param path the path of the tag to get, separated by periods (eg {@code SpawnPotentials.0.Type})
     * @return the value of the requested NBT tag, or {@code null} if it does not exist -- lists are represented by
     * {@code List<?>} and compounds are represented by {@code Map<String, ?>}.
     */
    public static Object getEntityNBT(Entity entity, String path) {
        final NBTTagCompound root = new NBTTagCompound();
        ((CraftEntity) entity).getHandle().c(root);
        return getNBT(root, path);
    }

    /**
     * Set an item's NBT tag data.
     *
     * @param item the target item.
     * @param path the path of the tag to set, separated by periods, starting from the {@code tag} tag (eg {@code
     * CanDestroy.0})
     * @param value the new value of the tag -- lists are represented by {@code List<?>} and compounds are represented
     * by {@code Map<String, ?>}. Unexpected things may happen if this argument is null.
     * @return the new ItemStack.
     */
    public static ItemStack setItemNBT(ItemStack item, String path, Object value) {
        final net.minecraft.server.v1_11_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
        final NBTTagCompound root;
        if (nmsItem.hasTag()) {
            root = nmsItem.getTag();
        } else {
            root = new NBTTagCompound();
            nmsItem.setTag(root);
        }
        setNBT(root, path, value);
        return CraftItemStack.asCraftMirror(nmsItem);
    }

    /**
     * Get an item's NBT tag data.
     *
     * @param item the target item.
     * @param path the path of the tag to get, separated by periods, starting from the {@code tag} tag (eg {@code
     * CanDestroy.0})
     * @return the value of the requested NBT tag, or {@code null} if it does not exist -- lists are represented by
     * {@code List<?>} and compounds are represented by {@code Map<String, ?>}.
     */
    public static Object getItemNBT(ItemStack item, String path) {
        final net.minecraft.server.v1_11_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
        if (!nmsItem.hasTag()) return null;
        return getNBT(nmsItem.getTag(), path);
    }

    private static void setNBT(NBTTagCompound root, String path, Object value) {
        final String[] keys = path.split("\\.");
        NBTBase target = root;
        for (int i = 0; i < keys.length; i++) {
            if (i == keys.length - 1) {
                if (target instanceof NBTTagCompound) {
                    ((NBTTagCompound) target).set(keys[i], toNBTBase(value));
                } else if (target instanceof NBTTagList) {
                    final int index = Integer.parseInt(keys[i]);
                    if (index == ((NBTTagList) target).size() || index == -1) {
                        ((NBTTagList) target).add(toNBTBase(value));
                    } else {
                        ((NBTTagList) target).a(Integer.parseInt(keys[i]), toNBTBase(value));
                    }
                } else throw new ClassCastException("Not a compound or list");
            } else {
                if (target instanceof NBTTagCompound) {
                    final NBTBase next = ((NBTTagCompound) target).get(keys[i]);
                    if (next == null) {
                        final NBTTagCompound compound = new NBTTagCompound();
                        ((NBTTagCompound) target).set(keys[i], compound);
                        target = compound;
                    } else {
                        target = next;
                    }
                } else if (target instanceof NBTTagList) {
                    final int index = Integer.parseInt(keys[i]);
                    if (index == ((NBTTagList) target).size() || index == -1) {
                        final NBTTagCompound compound = new NBTTagCompound();
                        ((NBTTagList) target).add(compound);
                        target = compound;
                    } else {
                        target = ((NBTTagList) target).get(index);
                    }
                } else throw new ClassCastException("Not a compound or list");
            }
        }
    }

    private static Object getNBT(NBTTagCompound root, String path) {
        NBTBase target = root;
        for (String key : path.split("\\.")) {
            if (target instanceof NBTTagCompound) {
                target = ((NBTTagCompound) target).get(key);
            } else if (target instanceof NBTTagList) {
                target = ((NBTTagList) target).get(Integer.parseInt(key));
            } else return null;
            if (target == null) return null;
        }
        return toObject(target);
    }

    private static NBTBase toNBTBase(Object o) {
        if (o == null) return null;
        if (o instanceof Integer) return new NBTTagInt((Integer) o);
        if (o instanceof String) return new NBTTagString((String) o);
        if (o instanceof Float) return new NBTTagFloat((Float) o);
        if (o instanceof Double) return new NBTTagDouble((Double) o);
        if (o instanceof Short) return new NBTTagShort((Short) o);
        if (o instanceof Byte) return new NBTTagByte((Byte) o);
        if (o instanceof Long) return new NBTTagLong((Long) o);
        if (o instanceof List<?>) {
            final NBTTagList list = new NBTTagList();
            for (Object obj : (List<?>) o) list.add(toNBTBase(obj));
            return list;
        }
        if (o instanceof Map<?, ?>) {
            final NBTTagCompound compound = new NBTTagCompound();
            for (Map.Entry<?, ?> entry : ((Map<?, ?>) o).entrySet())
                compound.set((String) entry.getKey(), toNBTBase(entry.getValue()));
            return compound;
        }
        throw new ClassCastException(o.getClass().getName() + " cannot be turned into an NBTBase");
    }

    private static Object toObject(NBTBase nbt) {
        if (nbt == null) return null;
        if (nbt instanceof NBTTagInt) return ((NBTTagInt) nbt).e();
        if (nbt instanceof NBTTagString) return ((NBTTagString) nbt).c_();
        if (nbt instanceof NBTTagFloat) return ((NBTTagFloat) nbt).i();
        if (nbt instanceof NBTTagDouble) return ((NBTTagDouble) nbt).asDouble();
        if (nbt instanceof NBTTagShort) return ((NBTTagShort) nbt).f();
        if (nbt instanceof NBTTagByte) return ((NBTTagByte) nbt).g();
        if (nbt instanceof NBTTagLong) return ((NBTTagLong) nbt).d();
        if (nbt instanceof NBTTagList) {
            final List<Object> list = new ArrayList<>();
            for (int i = 0; i < ((NBTTagList) nbt).size(); i++) {
                list.add(toObject(((NBTTagList) nbt).get(i)));
            }
            return list;
        }
        if (nbt instanceof NBTTagCompound) {
            final Map<String, Object> map = new HashMap<>();
            for (String k : ((NBTTagCompound) nbt).c()) {
                map.put(k, toObject(((NBTTagCompound) nbt).get(k)));
            }
            return map;
        }
        throw new ClassCastException(nbt.getClass().getName() + " cannot be turned into an object");
    }

    public static void main(String[] args) {
        final Scanner in = new Scanner(System.in);
        NBTTagCompound compound = new NBTTagCompound();
        while (true) {
            System.out.println("compound = " + compound);
            System.out.print("Key> ");
            final String key = in.nextLine();
            System.out.print("Value> ");
            final String value = in.nextLine();
            if ("{}".equals(value)) setNBT(compound, key, new HashMap<String, Object>());
            else if ("[]".equals(value)) setNBT(compound, key, new ArrayList<>());
            else if ("null".equals(value)) setNBT(compound, key, null);
            else setNBT(compound, key, value);
        }
    }

}
