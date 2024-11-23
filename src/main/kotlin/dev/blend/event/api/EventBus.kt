package dev.blend.event.api

import best.azura.eventbus.core.Event
import best.azura.eventbus.handler.EventExecutable
import best.azura.eventbus.handler.EventHandler
import best.azura.eventbus.handler.Listener
import java.util.concurrent.CopyOnWriteArrayList

object EventBus {

    private val executables = CopyOnWriteArrayList<EventExecutable>()
    
    fun subscribe(any: Any) {
        if (subscribed(any))
            return
        any::class.java.declaredMethods.forEach { method ->
            if (method.isAnnotationPresent(EventHandler::class.java) && method.parameterCount > 0) {
                executables.add(
                    EventExecutable(
                        method,
                        any,
                        method.getDeclaredAnnotation(EventHandler::class.java).value
                    )
                )
            }
        }
        any::class.java.declaredFields.forEach { field ->
            if (field.isAnnotationPresent(EventHandler::class.java) && Listener::class.java.isAssignableFrom(field.type)) {
                executables.add(
                    EventExecutable(
                        field,
                        any,
                        field.getDeclaredAnnotation(EventHandler::class.java).value
                    )
                )
            }
        }
        executables.sortBy {
            it.priority
        }
    }
    fun unsubscribe(any: Any) {
        if (!subscribed(any))
            return
        executables.removeIf {
            it.parent == any
        }
    }

    fun <E : Event> post(event: E): E {
        executables.forEach { it.call(event) }
        return event
    }

    fun subscribed(any: Any): Boolean {
        return executables.stream().anyMatch {
            it.parent.equals(any)
        }
    }


}