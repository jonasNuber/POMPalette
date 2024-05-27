package org.nuberjonas.pompalette.core.corebaseimpl.graph;

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
