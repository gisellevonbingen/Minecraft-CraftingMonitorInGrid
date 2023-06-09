package giselle.rs_cmig.common.network;

import java.util.function.Supplier;

import com.refinedmods.refinedstorage.screen.grid.GridScreen;

import giselle.rs_cmig.client.IGridScreenExtension;
import giselle.rs_cmig.common.LevelBlockPos;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

public class CGridShowButtonMessage extends NetworkContainerMessage
{
	protected CGridShowButtonMessage()
	{

	}

	public CGridShowButtonMessage(LevelBlockPos networkPos, int containerId)
	{
		super(networkPos, containerId);
	}

	public static CGridShowButtonMessage decode(FriendlyByteBuf buf)
	{
		CGridShowButtonMessage message = new CGridShowButtonMessage();
		NetworkContainerMessage.decode(message, buf);
		return message;
	}

	public static void encode(CGridShowButtonMessage message, FriendlyByteBuf buf)
	{
		NetworkContainerMessage.encode(message, buf);
	}

	public static void handle(CGridShowButtonMessage message, Supplier<NetworkEvent.Context> ctx)
	{
		ctx.get().enqueueWork(() ->
		{
			Minecraft minecraft = Minecraft.getInstance();

			if (minecraft.screen instanceof GridScreen screen && screen.getMenu().containerId == message.getContainerId())
			{
				((IGridScreenExtension) screen).rs_cmig$setNetworkPos(message.getNetworkPos());
			}

		});
		ctx.get().setPacketHandled(true);
	}

}
