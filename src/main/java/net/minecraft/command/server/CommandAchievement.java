package net.minecraft.command.server;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

/*
 * This is a skeleton file representing 1.11.2's
 * net/minecraft/command/server/CommandAchievement. Actual implementation will
 * be obtained at runtime.
 */
public class CommandAchievement extends CommandBase {
  public CommandAchievement() {}

  public String getName() { return null; }

  public int getRequiredPermissionLevel() { return 0; }

  public String getUsage(ICommandSender sender) { return null; }

  public void execute(MinecraftServer var1, ICommandSender sender,
                      String[] args) {}

  public List<String> getTabCompletions(MinecraftServer server,
                                        ICommandSender sender, String[] args,
                                        @Nullable BlockPos targetPos) {
    return null;
  }

  public boolean isUsernameIndex(String[] args, int index) { return false; }
}
