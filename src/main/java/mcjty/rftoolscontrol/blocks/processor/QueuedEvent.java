package mcjty.rftoolscontrol.blocks.processor;

import mcjty.rftoolscontrol.logic.Parameter;
import mcjty.rftoolscontrol.logic.compiled.CompiledEvent;

import javax.annotation.Nullable;

public class QueuedEvent {
    private final int cardIndex;
    private final CompiledEvent compiledEvent;

    @Nullable private final String ticket;
    @Nullable private final Parameter parameter;

    public QueuedEvent(int cardIndex, CompiledEvent compiledEvent, @Nullable String ticket, @Nullable Parameter parameter) {
        this.cardIndex = cardIndex;
        this.compiledEvent = compiledEvent;
        this.ticket = ticket;
        this.parameter = parameter;
    }

    public int getCardIndex() {
        return cardIndex;
    }

    public CompiledEvent getCompiledEvent() {
        return compiledEvent;
    }

    public String getTicket() {
        return ticket;
    }

    @Nullable
    public Parameter getParameter() {
        return parameter;
    }
}
