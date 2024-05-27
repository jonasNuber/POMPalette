package org.nuberjonas.pompalette.core.corebaseimpl.graph;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BasicRelationshipFactoryTest extends BaseTest {

    @Test
    void createRelationship_ShouldReturnRelationshipWithCorrectSourceAndDestinationAndData(){
        var relationshipFactory = new BasicRelationshipFactory<String, String>();
        var sourceEntity = createBasicEntity("source");
        var destinationEntity = createBasicEntity("destination");
        var data = "data";

        var relationship = relationshipFactory.createRelationship(sourceEntity, destinationEntity, data);

        assertThat(relationship.source()).isEqualTo(sourceEntity);
        assertThat(relationship.destination()).isEqualTo(destinationEntity);
        assertThat(relationship.data()).isEqualTo(data);
    }
}