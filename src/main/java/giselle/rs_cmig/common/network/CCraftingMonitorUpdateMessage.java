package giselle.rs_cmig.common.network;

import java.util.function.Supplier;

import com.refinedmods.refinedstorage.network.craftingmonitor.CraftingMonitorUpdateMessage;
import com.refinedmods.refinedstorage.screen.BaseScreen;

import giselle.rs_cmig.client.screen.CMIGCraftingMonitorScreen;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

public class CCraftingMonitorUpdateMessage
{
	private final CraftingMonitorUpdateMessage impl;

	public CCraftingMonitorUpdateMessage(CraftingMonitorUpdateMessage impl)
	{
		this.impl = impl;
	}

	public static CCraftingMonitorUpdateMessage decode(FriendlyByteBuf buf)
	{
		CraftingMonitorUpdateMessage impl = CraftingMonitorUpdateMessage.decode(buf);
		return new CCraftingMonitorUpdateMessage(impl);
	}

	public static void encode(CCraftingMonitorUpdateMessage message, FriendlyByteBuf buf)
	{
		CraftingMonitorUpdateMessage.encode(message.getImpl(), buf);
	}

	public static void handle(CCraftingMonitorUpdateMessage message, Supplier<NetworkEvent.Context> ctx)
	{
		ctx.get().enqueueWork(() ->
		{
			BaseScreen.executeLater(CMIGCraftingMonitorScreen.class, screen -> screen.setTasks(message.getImpl().getTasks()));
		});
		ctx.get().setPacketHandled(true);
	}

	public CraftingMonitorUpdateMessage getImpl()
	{
		return this.impl;
	}

}
