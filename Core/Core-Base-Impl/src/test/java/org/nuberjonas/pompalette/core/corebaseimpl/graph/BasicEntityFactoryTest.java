package org.nuberjonas.pompalette.core.corebaseimpl.graph;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BasicEntityFactoryTest {

    @Test
    void createEntity_ShouldReturnEntityWithCorrectData(){
        var entityFactory = new BasicEntityFactory<String, String>();
        var expected = "data";

        var entity = entityFactory.createEntity(expected);

        assertThat(entity.getData()).isEqualTo(expected);
        assertThat(entity.getRelationships()).isEmpty();
    }

}