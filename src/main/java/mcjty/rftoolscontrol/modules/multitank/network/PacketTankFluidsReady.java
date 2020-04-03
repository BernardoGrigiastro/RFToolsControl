package mcjty.rftoolscontrol.modules.multitank.network;


import mcjty.lib.network.IClientCommandHandler;
import mcjty.lib.network.NetworkTools;
import mcjty.lib.typed.Type;
import mcjty.lib.varia.Logging;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class PacketTankFluidsReady {

    public BlockPos pos;
    public List<FluidStack> list;
    public String command;

    public PacketTankFluidsReady(PacketBuffer buf) {
        pos = buf.readBlockPos();
        command = buf.readString(32767);

        int size = buf.readInt();
        if (size != -1) {
            list = new ArrayList<>(size);
            for (int i = 0 ; i < size ; i++) {
                FluidStack fluidStack = null;
                if (buf.readBoolean()) {
                    fluidStack = NetworkTools.readFluidStack(buf);
                }
                FluidStack item = fluidStack;
                list.add(item);
            }
        } else {
            list = null;
        }
    }

    public PacketTankFluidsReady(BlockPos pos, String command, List<FluidStack> list) {
        this.pos = pos;
        this.command = command;
        this.list = new ArrayList<>();
        this.list.addAll(list);
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeBlockPos(pos);
        buf.writeString(command);

        if (list == null) {
            buf.writeInt(-1);
        } else {
            buf.writeInt(list.size());
            for (FluidStack item : list) {
                if (item == null) {
                    buf.writeBoolean(false);
                } else {
                    buf.writeBoolean(true);
                    NetworkTools.writeFluidStack(buf, item);
                }
            }
        }
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context ctx = supplier.get();
        ctx.enqueueWork(() -> {
            TileEntity te = ctx.getSender().getEntityWorld().getTileEntity(pos);
            if(!(te instanceof IClientCommandHandler)) {
                Logging.log("TileEntity is not a ClientCommandHandler!");
                return;
            }
            IClientCommandHandler clientCommandHandler = (IClientCommandHandler) te;
            if (!clientCommandHandler.receiveListFromServer(command, list, Type.create(FluidStack.class))) {
                Logging.log("Command " + command + " was not handled!");
            }
        });
        ctx.setPacketHandled(true);
    }
}