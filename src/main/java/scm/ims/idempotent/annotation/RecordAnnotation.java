package scm.ims.idempotent.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RecordAnnotation {
    String desc() default "无描述信息";

    String key() default "";
}
