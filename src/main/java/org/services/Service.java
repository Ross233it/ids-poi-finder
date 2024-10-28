package org.services;

import java.util.Map;
public interface Service<D>{

    public D create(Map<String, Object> objectData);
}
