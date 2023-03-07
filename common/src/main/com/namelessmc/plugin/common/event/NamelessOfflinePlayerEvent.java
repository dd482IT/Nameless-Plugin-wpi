package com.namelessmc.plugin.common.event;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.UUID;

public class NamelessOfflinePlayerEvent extends NamelessEvent {

	private final UUID uuid;

	public NamelessOfflinePlayerEvent(final UUID uuid) {
		this.uuid = uuid;
	}

	public UUID uuid() {
		return this.uuid;
	}

}
