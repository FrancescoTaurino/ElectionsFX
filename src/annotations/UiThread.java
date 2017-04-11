package annotations;

import java.lang.annotation.*;

/**
 * States that a method can only be executed by the event dispatch thread.
 */

@Documented
@Target({ElementType.METHOD, ElementType.CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
public @interface UiThread {
}