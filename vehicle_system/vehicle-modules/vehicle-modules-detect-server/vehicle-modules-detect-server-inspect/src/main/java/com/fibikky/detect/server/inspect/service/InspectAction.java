package com.fibikky.detect.server.inspect.service;

/**
 * @author 16861
 */
@FunctionalInterface
public interface InspectAction {
    public void invoke(InspectTask task);
}
