package org.nuberjonas.pompalette.mapping.mappingapi.mapper;

import java.lang.reflect.ParameterizedType;

public interface MapperService<S, D> {

    D mapToDestination(S toMap);

    S mapToSource(D toMap);

    @SuppressWarnings("unchecked")
    default Class<S> sourceType() {
        ParameterizedType superClass = (ParameterizedType) getClass().getGenericInterfaces()[0];
        return (Class<S>) superClass.getActualTypeArguments()[0];
    }

    @SuppressWarnings("unchecked")
    default Class<D> destinationType() {
        ParameterizedType superClass = (ParameterizedType) getClass().getGenericInterfaces()[0];
        return (Class<D>) superClass.getActualTypeArguments()[1];
    }
}
