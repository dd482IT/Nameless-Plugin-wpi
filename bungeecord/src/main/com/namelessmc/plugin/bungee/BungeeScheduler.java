package com.namelessmc.plugin.bungee;

import com.namelessmc.plugin.common.command.AbstractScheduledTask;
import com.namelessmc.plugin.common.command.AbstractScheduler;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.scheduler.ScheduledTask;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class BungeeScheduler extends AbstractScheduler {

	private final BungeeNamelessPlugin plugin;

	BungeeScheduler(final BungeeNamelessPlugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public void runAsync(final Runnable runnable) {
		ProxyServer.getInstance().getScheduler()
				.runAsync(this.plugin, runnable);
	}

	@Override
	public void runSync(final Runnable runnable) {
		ProxyServer.getInstance().getScheduler()
				.schedule(this.plugin, runnable, 0, TimeUnit.NANOSECONDS);
	}

	@Override
	public BungeeScheduledTask runTimer(Runnable runnable, Duration interval) {
		long l = interval.toNanos();
		final ScheduledTask task = ProxyServer.getInstance().getScheduler()
				.schedule(this.plugin, runnable, l, l, TimeUnit.NANOSECONDS);
		return new BungeeScheduledTask(task);
	}


	@Override
	public BungeeScheduledTask runDelayed(Runnable runnable, Duration delay) {
		long l = delay.toNanos();
		final ScheduledTask task = ProxyServer.getInstance().getScheduler()
				.schedule(this.plugin, runnable, l, TimeUnit.NANOSECONDS);
		return new BungeeScheduledTask(task);
	}

	public static class BungeeScheduledTask extends AbstractScheduledTask {

		private final ScheduledTask task;

		private BungeeScheduledTask(final ScheduledTask task) {
			this.task = task;
		}

		@Override
		public void cancel() {
			this.task.cancel();
		}


	}
}
