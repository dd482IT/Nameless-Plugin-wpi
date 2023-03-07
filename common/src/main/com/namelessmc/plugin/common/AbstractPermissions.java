package com.namelessmc.plugin.common;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.namelessmc.plugin.common.audiences.NamelessPlayer;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Collection;
import java.util.Set;

public abstract class AbstractPermissions
		implements AbstractDataSender.InfoProvider, AbstractDataSender.PlayerInfoProvider, Reloadable {

	public abstract boolean isUsable();

	public abstract Set<String> getGroups();

	public abstract Set<String> getPlayerGroups(final NamelessPlayer player);

	private JsonArray toJsonArray(final Collection<String> coll) {
		final JsonArray json = new JsonArray(coll.size());
		coll.stream().map(JsonPrimitive::new).forEach(json::add);
		return json;
	}

	@Override
	public void addInfoToJson(JsonObject json) {
		json.add("groups", this.toJsonArray(this.getGroups()));
	}

	@Override
	public void addInfoToJson(JsonObject json, NamelessPlayer player) {
		final Collection<String> groups = this.getPlayerGroups(player);
		if (groups != null) {
			json.add("groups", this.toJsonArray(groups));
		}
	}

	protected class ProviderNotUsableException extends IllegalStateException {

		public ProviderNotUsableException() {
			super("Provider was called un unusable state");
		}

	}

}
