package com.namelessmc.plugin.velocity;

import com.namelessmc.plugin.common.command.AbstractScheduledTask;
import com.namelessmc.plugin.common.command.AbstractScheduler;
import com.velocitypowered.api.scheduler.ScheduledTask;
import com.velocitypowered.api.scheduler.Scheduler;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class VelocityScheduler extends AbstractScheduler {

	private final VelocityNamelessPlugin plugin;
	private final Scheduler scheduler;

	VelocityScheduler(final VelocityNamelessPlugin plugin,
					  final Scheduler scheduler) {
		this.plugin = plugin;
		this.scheduler = scheduler;
	}

	@Override
	public void runAsync(final Runnable runnable) {
		this.scheduler
				.buildTask(this.plugin, runnable)
				.schedule();
	}

	@Override
	public void runSync(final Runnable runnable) {
		// Velocity has no "main thread", we can just run it in the current thread
		runnable.run();
	}

	@Override
	public VelocityScheduledTask runTimer(Runnable runnable, Duration interval) {
		final ScheduledTask task = this.scheduler
				.buildTask(this.plugin, runnable)
				.delay(interval.toNanos(), TimeUnit.NANOSECONDS)
				.repeat(interval.toNanos(), TimeUnit.NANOSECONDS)
				.schedule();
		return new VelocityScheduledTask(task);
	}

	@Override
	public VelocityScheduledTask runDelayed(Runnable runnable, Duration delay) {
		final ScheduledTask task = this.scheduler
				.buildTask(this.plugin, runnable)
				.delay(delay.toNanos(), TimeUnit.NANOSECONDS)
				.schedule();
		return new VelocityScheduledTask(task);
	}

	public static class VelocityScheduledTask extends AbstractScheduledTask {

		private final ScheduledTask task;

		private VelocityScheduledTask(final ScheduledTask task) {
			this.task = task;
		}

		@Override
		public void cancel() {
			this.task.cancel();
		}

	}


}
