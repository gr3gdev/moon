package io.github.gr3gdev.fenrir.annotation;

import io.github.gr3gdev.fenrir.http.ConditionalRequest;
import io.github.gr3gdev.fenrir.http.HttpMethod;
import io.github.gr3gdev.fenrir.http.HttpStatus;
import io.github.gr3gdev.fenrir.http.impl.NoneConditionalRequest;
import io.github.gr3gdev.fenrir.validator.RouteValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for execute a methode when the route match with the path.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Listener {
    /**
     * Listener's reference, must be unique in the same {@link Route}.
     *
     * @return String
     */
    String ref() default "{methodName}";

    /**
     * The response code HTTP, 200 by default.
     *
     * @return HttpStatus
     */
    HttpStatus responseCode() default HttpStatus.OK;

    /**
     * The HTTP method, GET by default.
     *
     * @return HttpMethod
     */
    HttpMethod method() default HttpMethod.GET;

    /**
     * The path to intercept.
     *
     * @return String
     */
    String path();

    /**
     * The content-type for the response, text/html by default.
     *
     * @return String
     */
    String contentType() default "text/html";

    /**
     * Find a content-type dynamically from the request.
     *
     * @return Class of {@link ConditionalRequest}
     */
    Class<? extends ConditionalRequest> conditionalContentType() default NoneConditionalRequest.class;

    /**
     * The validators to use for the request.
     *
     * @return Array of Class
     */
    Class<? extends RouteValidator>[] validators() default {};
}
