package org.nuberjonas.pompalette.mapping.mappingapi.mapper;

import java.lang.reflect.ParameterizedType;

public interface MapperService<S, D> {

    D mapToDestination(S toMap);

    S mapToSource(D toMap);

    @SuppressWarnings("unchecked")
    default Class<S> sourceType() {
        return (Class<S>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @SuppressWarnings("unchecked")
    default Class<D> destinationType() {
        return (Class<D>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    }
}
