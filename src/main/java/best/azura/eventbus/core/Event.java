package best.azura.eventbus.core;

import dev.blend.event.api.EventBus;

public interface Event{
    default void call() {
        EventBus.INSTANCE.post(this);
    }
}