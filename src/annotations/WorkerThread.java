package annotations;

import java.lang.annotation.*;

/**
 * States that a method cannot be executed by the event dispatch thread.
 */

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface WorkerThread {
}