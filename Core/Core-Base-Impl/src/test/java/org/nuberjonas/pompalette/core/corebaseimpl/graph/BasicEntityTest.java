package org.nuberjonas.pompalette.core.corebaseimpl.graph;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BasicEntityTest extends BaseTest{

    @Test
    void new_ShouldHaveDataAndNoRelationships(){
        var expected = "data";
        var entity = createBasicEntity(expected);

        var actual = entity.getData();

        assertThat(actual).isEqualTo(expected);
        assertThat(entity.getRelationships()).isEmpty();
    }

    @Test
    void addRelationship_ShouldAddNewRelationship(){
        var sourceEntity = createBasicEntity("source");
        var destinationEntity = createBasicEntity("destination");
        var expected = createBasicRelationship(sourceEntity, destinationEntity, "relationship");

        var relationshipsAdded = sourceEntity.addRelationship(expected);

        assertThat(relationshipsAdded).isTrue();
        assertThat(sourceEntity.getRelationships()).containsExactly(expected);
    }

    @Test
    void addRelationship_ShouldNotAddRelationshipTwice(){
        var sourceEntity = createBasicEntity("source");
        var destinationEntity = createBasicEntity("destination");
        var expected = createBasicRelationship(sourceEntity, destinationEntity, "relationship");

        var relationshipsAdded = sourceEntity.addRelationship(expected);
        var relationshipNotAddedAgain = !sourceEntity.addRelationship(expected);

        assertThat(relationshipsAdded).isTrue();
        assertThat(relationshipNotAddedAgain).isTrue();
        assertThat(sourceEntity.getRelationships()).containsExactly(expected);
    }

    @Test
    void removeRelationship_ShouldRemoveRelationship(){
        var sourceEntity = createBasicEntity("source");
        var destinationEntity = createBasicEntity("destination");
        var relationship = createBasicRelationship(sourceEntity, destinationEntity, "relationship");
        sourceEntity.addRelationship(relationship);

        var relationshipRemoved = sourceEntity.removeRelationship(relationship);

        assertThat(relationshipRemoved).isTrue();
        assertThat(sourceEntity.getRelationships()).isEmpty();
    }

    @Test
    void removeRelationship_ShouldNotRemoveRelationship_ForUnknown(){
        var sourceEntity = createBasicEntity("source");
        var destinationEntity = createBasicEntity("destination");
        var relationship = createBasicRelationship(sourceEntity, destinationEntity, "relationship");
        var otherRelationship = createBasicRelationship(sourceEntity, destinationEntity, "otherRelationship");
        sourceEntity.addRelationship(relationship);

        var relationshipNotRemoved = !sourceEntity.removeRelationship(otherRelationship);

        assertThat(relationshipNotRemoved).isTrue();
        assertThat(sourceEntity.getRelationships()).containsExactly(relationship);
    }

    @Test
    void containsRelationship_ShouldReturnTrue_WhenRelationshipExists(){
        var sourceEntity = createBasicEntity("source");
        var destinationEntity = createBasicEntity("destination");
        var relationship = createBasicRelationship(sourceEntity, destinationEntity, "relationship");

        sourceEntity.addRelationship(relationship);
        var containsRelationship = sourceEntity.containsRelationship(relationship);

        assertThat(containsRelationship).isTrue();
    }

    @Test
    void containsRelationship_ShouldReturnFalse_WhenRelationshipDoesNotExist(){
        var sourceEntity = createBasicEntity("source");
        var destinationEntity = createBasicEntity("destination");
        var relationship = createBasicRelationship(sourceEntity, destinationEntity, "relationship");

        var containsRelationship = sourceEntity.containsRelationship(relationship);

        assertThat(containsRelationship).isFalse();
    }

    @Test
    void equals_ShouldBeEqualToEntityWithSameDataAndRelationships(){
        var firstEntity = createBasicEntity("source");
        var secondEntity = createBasicEntity("source");

        assertThat(secondEntity).isEqualTo(firstEntity);
    }

    @Test
    void equals_ShouldBeEqualToEntityWithSameDataAndDifferentRelationships(){
        var firstEntity = createBasicEntity("source");
        var secondEntity = createBasicEntity("source");

        assertThat(secondEntity).isEqualTo(firstEntity);
    }

    @Test
    void equals_ShouldNotBeEqualToEntityWithDifferentData(){
        var firstEntity = createBasicEntity("data");
        var secondEntity = createBasicEntity("otherData");

        assertThat(secondEntity).isNotEqualTo(firstEntity);
    }
}