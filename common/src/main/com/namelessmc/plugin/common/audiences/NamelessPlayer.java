package com.namelessmc.plugin.common.audiences;

import com.namelessmc.java_api.NamelessAPI;
import com.namelessmc.plugin.common.Permission;
import net.kyori.adventure.audience.Audience;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.UUID;

public abstract class NamelessPlayer extends NamelessCommandSender {

	private final UUID uuid;
	private final String username;

	public NamelessPlayer(final Audience audience,
						  final UUID uuid,
						  final String username) {
		super(audience);
		this.uuid = uuid;
		this.username = username;
	}

	public UUID uuid() {
		return this.uuid;
	}
	
	public String websiteUuid() {
		return NamelessAPI.javaUuidToWebsiteUuid(this.uuid);
	}

	public String username() {
		return this.username;
	}

}
