package org.poifinder.controllers.JavaFirstVersion;

/**
 * Consente di passare una funzione lambda come argomento.
 * @param <T>
 */
@FunctionalInterface
public interface ControllerRequestHandler<T> {
    T handle() throws Exception;
}
