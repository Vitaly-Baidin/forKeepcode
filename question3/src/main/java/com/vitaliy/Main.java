package com.vitaliy;

import java.net.InetSocketAddress;
import java.util.Date;

public class Main {

    void processTask(ChannelHandlerContext ctx) {
        InetSocketAddress lineAddress = new InetSocketAddress(getIpAddress(), getUdpPort());
        CommandType typeToRemove;

        for (Command currentCommand : getAllCommands()) {
            sendCommand(ctx, lineAddress, currentCommand);
        }
        sendKeepAliveOkAndFlush(ctx);
    }

    private void sendCommand(ChannelHandlerContext ctx,InetSocketAddress lineAddress, Command command) {
        if (!command.isAttemptsNumberExhausted()) {
            sendCommandToContext(ctx, lineAddress, command.getCommandText());
            try {
                createProcessUssdMessage(lineAddress, command);
            } catch (Exception ignored) {
            }
            Log.ussd.write(String.format("sent: ip: %s; порт: %d; %s",
                    lineAddress.getHostString(),
                    lineAddress.getPort(),
                    command.getCommandText()));
            command.setSendDate(new Date());
            command.incSendCounter();
        } else {
            removeCommand(command);
        }
    }

    private void removeCommand(Command command) {
        typeToRemove = command.getCommandType();
        deleteCommand(typeToRemove);
    }

    private void createProcessUssdMessage(InetSocketAddress lineAddress, Command command) {
        AdminController.getInstance().processUssdMessage(
                new DblIncomeUssdMessage(lineAddress.getHostName(),
                        lineAddress.getPort(),
                        0,
                        EnumGoip.getByModel(getGoipModel()),
                        currentCommand.getCommandText()),
                false);
    }


}

