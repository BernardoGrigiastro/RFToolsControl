package mcjty.rftoolscontrol.items.craftingcard;

import io.netty.buffer.ByteBuf;
import mcjty.lib.network.TypedMapTools;
import mcjty.lib.thirteen.Context;
import mcjty.lib.typed.Key;
import mcjty.lib.typed.Type;
import mcjty.lib.typed.TypedMap;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

import java.util.function.Supplier;

public class PacketUpdateNBTItemCard implements IMessage {

    public TypedMap args;

    public PacketUpdateNBTItemCard() {
    }

    public PacketUpdateNBTItemCard(ByteBuf buf) {
        fromBytes(buf);
    }

    public PacketUpdateNBTItemCard(TypedMap arguments) {
        this.args = arguments;
    }

    protected boolean isValidItem(ItemStack itemStack) {
        return itemStack.getItem() instanceof CraftingCardItem;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        args = TypedMapTools.readArguments(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        TypedMapTools.writeArguments(buf, args);
    }

    public void handle(Supplier<Context> supplier) {
        Context ctx = supplier.get();
        ctx.enqueueWork(() -> {
            EntityPlayerMP playerEntity = ctx.getSender();
            ItemStack heldItem = playerEntity.getHeldItem(EnumHand.MAIN_HAND);
            if (heldItem.isEmpty()) {
                return;
            }
            // To avoid people messing with packets
            if (!isValidItem(heldItem)) {
                return;
            }
            NBTTagCompound tagCompound = heldItem.getTagCompound();
            if (tagCompound == null) {
                tagCompound = new NBTTagCompound();
                heldItem.setTagCompound(tagCompound);
            }
            for (Key<?> akey : args.getKeys()) {
                String key = akey.getName();
                if (Type.STRING.equals(akey.getType())) {
                    tagCompound.setString(key, (String) args.get(akey));
                } else if (Type.INTEGER.equals(akey.getType())) {
                    tagCompound.setInteger(key, (Integer) args.get(akey));
                } else if (Type.LONG.equals(akey.getType())) {
                    tagCompound.setLong(key, (Long) args.get(akey));
                } else if (Type.DOUBLE.equals(akey.getType())) {
                    tagCompound.setDouble(key, (Double) args.get(akey));
                } else if (Type.BOOLEAN.equals(akey.getType())) {
                    tagCompound.setBoolean(key, (Boolean) args.get(akey));
                } else if (Type.BLOCKPOS.equals(akey.getType())) {
                    throw new RuntimeException("BlockPos not supported for PacketUpdateNBTItem!");
                } else if (Type.ITEMSTACK.equals(akey.getType())) {
                    throw new RuntimeException("ItemStack not supported for PacketUpdateNBTItem!");
                } else {
                    throw new RuntimeException(akey.getType().getType().getSimpleName() + " not supported for PacketUpdateNBTItem!");
                }
            }
        });
        ctx.setPacketHandled(true);
    }
}