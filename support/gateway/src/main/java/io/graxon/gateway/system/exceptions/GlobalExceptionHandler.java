package io.graxon.gateway.system.exceptions;

import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

/**
 *
 */
@Component
public class GlobalExceptionHandler extends AbstractErrorWebExceptionHandler {

    /**
     *
     * @param errorAttributes
     * @param resources
     * @param applicationContext
     * @param configurer
     */
    public GlobalExceptionHandler(
            final ErrorAttributes errorAttributes,
            final WebProperties.Resources resources,
            final ApplicationContext applicationContext,
            final ServerCodecConfigurer configurer) {
        super(errorAttributes, resources, applicationContext);
        setMessageReaders(configurer.getReaders());
        setMessageWriters(configurer.getWriters());
    }

    /**
     *
     * @param errorAttributes
     * @return
     */
    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }

    /**
     *
     * @param request
     * @return
     */
    private Mono<ServerResponse> renderErrorResponse(ServerRequest request) {
        var throwable = getError(request);
        var httpStatus = determineHttpStatus(throwable);
        var errorMessage = new ErrorMessage(httpStatus.value(), throwable.getMessage());
        return ServerResponse.status(httpStatus)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(errorMessage);

        /*
        ErrorAttributeOptions options = ErrorAttributeOptions.of(ErrorAttributeOptions.Include.MESSAGE);
        Map<String, Object> errorPropertiesMap = getErrorAttributes(request, options);
        Throwable throwable = getError(request);
        HttpStatusCode httpStatus = determineHttpStatus(throwable);

        errorPropertiesMap.put("yolo", throwable.getMessage());
        errorPropertiesMap.put("status", httpStatus.value());
        errorPropertiesMap.remove("error");

        return ServerResponse.status(httpStatus)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(errorPropertiesMap));

         */
    }

    /**
     *
     * @param throwable
     * @return
     */
    private HttpStatusCode determineHttpStatus(Throwable throwable) {
        return switch (throwable) {
            case ResponseStatusException responseStatusException -> responseStatusException.getStatusCode();
            case CustomRequestAuthException ignored -> HttpStatus.UNAUTHORIZED;
            case null, default -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
    }
}
