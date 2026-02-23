/*
 * File originally made by: The Cheesy Poofs - FRC 254
 * Copyright (c) 2025 Team 254 (https://github.com/Team254)
 * Copyright (c) 2025-2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.util.commands;

import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import java.util.ArrayList;
import java.util.List;

/**
 * A command composition that runs a list of commands in sequence. But faster.
 *
 * <p>This command group will run the next command in the current loop instead of the next one.
 *
 * <p>The rules for command compositions apply: command instances that are passed to it cannot be
 * added to any other composition or scheduled individually, and the composition requires all
 * subsystems its components require.
 */
public class ChezySequenceCommandGroup extends Command {
  private final List<Command> commands = new ArrayList<>();
  private int currentCommandIndex = -1;
  private boolean runWhenDisabled = true;
  private InterruptionBehavior interruptBehavior = InterruptionBehavior.kCancelIncoming;

  /**
   * Creates a new {@link ChezySequenceCommandGroup}. The given commands will be run sequentially,
   * with the composition finishing when the last command finishes.
   *
   * @param commands The {@link Command}(s) to include in this composition.
   */
  public ChezySequenceCommandGroup(Command... commands) {
    addCommands(commands);
  }

  /**
   * Adds the given commands to the group.
   *
   * @param commands Commands to add, in order of execution.
   */
  public final void addCommands(Command... commands) {
    if (currentCommandIndex != -1) {
      throw new IllegalStateException(
          "Commands cannot be added to a composition while it's running");
    }

    CommandScheduler.getInstance().registerComposedCommands(commands);

    for (Command command : commands) {
      this.commands.add(command);
      addRequirements(command.getRequirements());
      runWhenDisabled &= command.runsWhenDisabled();
      if (command.getInterruptionBehavior() == InterruptionBehavior.kCancelSelf) {
        interruptBehavior = InterruptionBehavior.kCancelSelf;
      }
    }
  }

  @Override
  public final void initialize() {
    currentCommandIndex = 0;

    if (!commands.isEmpty()) {
      commands.get(0).initialize();
    }
  }

  @Override
  public final void execute() {
    if (commands.isEmpty()) {
      return;
    }

    Command currentCommand = commands.get(currentCommandIndex);

    currentCommand.execute();
    if (currentCommand.isFinished()) {
      currentCommand.end(false);
      currentCommandIndex++;
      if (currentCommandIndex < commands.size()) {
        commands.get(currentCommandIndex).initialize();

        // Go again to run the next loop.
        this.execute();
      }
    }
  }

  @Override
  public final void end(boolean interrupted) {
    if (interrupted
        && !commands.isEmpty()
        && currentCommandIndex > -1
        && currentCommandIndex < commands.size()) {
      commands.get(currentCommandIndex).end(true);
    }
    currentCommandIndex = -1;
  }

  @Override
  public final boolean isFinished() {
    return currentCommandIndex == commands.size();
  }

  @Override
  public boolean runsWhenDisabled() {
    return runWhenDisabled;
  }

  @Override
  public InterruptionBehavior getInterruptionBehavior() {
    return interruptBehavior;
  }

  @Override
  public void initSendable(SendableBuilder builder) {
    super.initSendable(builder);

    builder.addIntegerProperty("index", () -> currentCommandIndex, null);
  }
}
