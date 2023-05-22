package giselle.rs_cmig.common.network;

import java.util.function.Supplier;

import com.refinedmods.refinedstorage.api.network.INetwork;

import giselle.rs_cmig.common.RS_CMIG;
import giselle.rs_cmig.common.LevelBlockPos;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class SCraftingMonitorStartMonitoringMessage extends NetworkMessage
{
	protected SCraftingMonitorStartMonitoringMessage()
	{

	}

	public SCraftingMonitorStartMonitoringMessage(LevelBlockPos networkPos)
	{
		super(networkPos);
	}

	public static SCraftingMonitorStartMonitoringMessage decode(PacketBuffer buf)
	{
		SCraftingMonitorStartMonitoringMessage message = new SCraftingMonitorStartMonitoringMessage();
		NetworkMessage.decode(message, buf);
		return message;
	}

	public static void encode(SCraftingMonitorStartMonitoringMessage message, PacketBuffer buf)
	{
		NetworkMessage.encode(message, buf);
	}

	public static void handle(SCraftingMonitorStartMonitoringMessage message, Supplier<NetworkEvent.Context> ctx)
	{
		ctx.get().enqueueWork(() ->
		{
			ServerPlayerEntity player = ctx.get().getSender();
			INetwork network = RS_CMIG.getNetwork(player, message.getNetworkPos());

			if (network != null)
			{
				RS_CMIG.startMonitoring(player, network);
			}
		});
		ctx.get().setPacketHandled(true);
	}

}