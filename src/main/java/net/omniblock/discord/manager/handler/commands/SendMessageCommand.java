package net.omniblock.discord.manager.handler.commands;

import java.awt.Color;
import java.util.concurrent.TimeUnit;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.omniblock.discord.manager.DHandlers;
import net.omniblock.discord.manager.handler.DiscordCommandHandler.DiscordCommand;
import net.omniblock.discord.manager.utils.MessageUtils;
import net.omniblock.packets.network.Packets;
import net.omniblock.packets.network.structure.packet.PlayerSendMessagePacket;
import net.omniblock.packets.network.structure.type.PacketSenderType;

public class SendMessageCommand implements DiscordCommand {

	@Override
	public boolean execute(Message message, String[] command) {
		
		if(command.length == 0)
			return false;
		
		if(command.length >= 1 && command.length <= 2) {
			
			if(command[0].equalsIgnoreCase("sendmessage")) {
				
				Message cache = message.getChannel().sendMessage(
						new EmbedBuilder()
							.setColor(Color.RED)
							.setTitle("💢 ¡Ups te ha faltado un Argumento!")
							.setDescription(
									message.getAuthor().getAsMention() + " El comando `sendmessage` debe tener especificado como parámetros " + 
									"el nombre del jugador y el mensaje que le enviarás, también puedes usar colores. \n" + 
									"**Ejemplo:** `::sendmessage Notch ¡Hola Notch!`")
							.setFooter("💣 Este mensaje se autodestruirá en 20 segundos!", null)
							.build()).complete();
				
				MessageUtils.deleteMessageAfter(cache, TimeUnit.SECONDS, 20);
				return true;
				
			}
			
			return false;
			
		}
		
		if(command[0].equalsIgnoreCase("sendmessage")) {
			
			StringBuffer buff = new StringBuffer();
			boolean author = true, discord = true;
			
			for(int i = 2; i < command.length; i++) {
				
				if(command[i].equals("--noauthor")) {
					
					author = false;
					continue;
					
				}
				
				if(command[i].equals("--nodiscord")) {
					
					discord = false;
					continue;
					
				}
				
				buff.append(command[i] + " ");
				continue;
				
			}
			
			String msg = discord ? "&8&lD&8iscord &9&l» &a" + (author ? "@" + message.getAuthor().getName() + "#" + message.getAuthor().getDiscriminator() + " &8&l: &7" + buff.toString() 
																   	  :  buff.toString()) 
								 :  author ? "@" + message.getAuthor().getName() + "#" + message.getAuthor().getDiscriminator() + " &8&l: &7" + buff.toString() 
								 		   : buff.toString();
			
			Packets.STREAMER.streamPacket(
					new PlayerSendMessagePacket()
						.setPlayername(command[1])
						.setMessage(msg)
						.build().setReceiver(PacketSenderType.OMNICORD)
					);
			
			Message cache = DHandlers.BOT.cmdChannel.sendMessage(
					new EmbedBuilder()
						.setTitle("💯 Se ha ejecutado un comando correctamente!")
						.setDescription(
								"\n \n**Ejecutor: ** " + message.getAuthor().getAsMention() + " \n" +
								"**Comando: ** Envíar un Mensaje `sendmessage` \n" +
								"**Jugador: **" + command[1] + " \n" +
								"**Mensaje: ** " + buff.toString())
						.setFooter("💣 Este mensaje se autodestruirá en 30 segundos!", null)
						.build()).complete();
			
			MessageUtils.deleteMessageAfter(cache, TimeUnit.SECONDS, 30);
			return true;
			
		}
		
		return false;
	}

}
