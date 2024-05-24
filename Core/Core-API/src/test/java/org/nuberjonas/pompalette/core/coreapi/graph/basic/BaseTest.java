package org.nuberjonas.pompalette.core.coreapi.graph.basic;

public class BaseTest {

    protected BasicEntity<String, String> createBasicEntity(String data) {
        return new BasicEntity<>(data);
    }

    protected BasicRelationship<String, String> createBasicRelationship(BasicEntity<String, String> source,
                                                                      BasicEntity<String, String> destination,
                                                                      String label) {
        return new BasicRelationship<>(source, destination, label);
    }
}
