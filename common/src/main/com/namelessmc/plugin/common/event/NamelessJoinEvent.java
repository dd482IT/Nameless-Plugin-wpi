package com.namelessmc.plugin.common.event;

import com.namelessmc.plugin.common.audiences.NamelessPlayer;
import org.checkerframework.checker.nullness.qual.NonNull;

public class NamelessJoinEvent extends NamelessEvent {

	private final NamelessPlayer player;

	public NamelessJoinEvent(final NamelessPlayer player) {
		this.player = player;
	}

	public NamelessPlayer player() {
		return this.player;
	}

}
