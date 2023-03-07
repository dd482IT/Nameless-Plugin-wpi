package com.namelessmc.plugin.common.command;

import com.namelessmc.plugin.common.ApiProvider;
import com.namelessmc.plugin.common.LanguageHandler;
import com.namelessmc.plugin.common.NamelessPlugin;
import com.namelessmc.plugin.common.Permission;
import com.namelessmc.plugin.common.audiences.NamelessCommandSender;
import com.namelessmc.plugin.common.logger.AbstractLogger;
import net.kyori.adventure.text.Component;
import org.checkerframework.checker.initialization.qual.UnknownInitialization;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.configurate.CommentedConfigurationNode;

import java.util.Collections;
import java.util.List;

import static com.namelessmc.plugin.common.LanguageHandler.Term.COMMAND_NO_PERMISSION;

public abstract class CommonCommand {

	private final NamelessPlugin plugin;
	private final LanguageHandler.Term usageTerm;
	private final LanguageHandler.Term descriptionTerm;
	private final Permission permission;
	private final String actualName;

	public CommonCommand(final NamelessPlugin plugin,
						 final String configName,
						 final LanguageHandler.Term usageTerm,
						 final LanguageHandler.Term descriptionTerm,
						 final Permission permission) {
		this.plugin = plugin;
		this.usageTerm = usageTerm;
		this.descriptionTerm = descriptionTerm;
		this.permission = permission;
		final CommentedConfigurationNode config = plugin.config().commands();
		this.actualName = config.node(configName).getString();
	}

	public String actualName(CommonCommand this) {
		return this.actualName;
	}

	public Component usage() {
		if (this.actualName == null) {
			throw new IllegalStateException("Cannot get usage for disabled command");
		}
		return this.language().get(this.usageTerm, "command", this.actualName);
	}

	public Component description() {
		return this.language().get(this.descriptionTerm);
	}

	public Permission permission() {
		return this.permission;
	}

	protected NamelessPlugin plugin() {
		return this.plugin;
	}

	protected AbstractScheduler scheduler() {
		return this.plugin.scheduler();
	}

	protected LanguageHandler language() {
		return this.plugin.language();
	}

	protected ApiProvider apiProvider() {
		return this.plugin.apiProvider();
	}

	protected AbstractLogger logger() { return this.plugin.logger(); }

	protected abstract void execute(final NamelessCommandSender sender, final String[] args);

	public void verifyPermissionThenExecute(NamelessCommandSender sender, String[] args) {
		if (!sender.hasPermission(this.permission)) {
			sender.sendMessage(this.language().get(COMMAND_NO_PERMISSION));
			return;
		}

		this.execute(sender, args);
	}

	public List<String> complete(final NamelessCommandSender sender, final String[] args) {
		return Collections.emptyList();
	}

	public static List<CommonCommand> commands(final NamelessPlugin plugin) {
		return List.of(
				new GetNotificationsCommand(plugin),
				new NamelessPluginCommand(plugin),
				new RegisterCommand(plugin),
				new ReportCommand(plugin),
				new UserInfoCommand(plugin),
				new StoreChangeCreditsCommand(plugin),
				new StoreViewCreditsCommand(plugin),
				new SuggestCommand(plugin),
				new VerifyCommand(plugin)
		);
	}

}
