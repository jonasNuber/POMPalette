package org.nuberjonas.pompalette.core.corebaseimpl.graph;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BasicRelationshipTest extends BaseTest{

    @Test
    void new_ShouldHaveSourceAndDestinationAndData(){
        var sourceEntity = createBasicEntity("source");
        var destinationEntity = createBasicEntity("destination");
        var expected = "relationship";

        var relationship = createBasicRelationship(sourceEntity, destinationEntity, expected);

        assertThat(relationship.source()).isEqualTo(sourceEntity);
        assertThat(relationship.destination()).isEqualTo(destinationEntity);
        assertThat(relationship.data()).isEqualTo(expected);
    }

    @Test
    void equals_ShouldBeEqualForRelationshipWithSameSourceAndDestinationAndData(){
        var firstSource = createBasicEntity("source");
        var firstDestination = createBasicEntity("destination");
        var secondSource = createBasicEntity("source");
        var secondDestination = createBasicEntity("destination");

        var actual = createBasicRelationship(firstSource, firstDestination, "relationship");
        var expected = createBasicRelationship(secondSource, secondDestination, "relationship");

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void equals_ShouldNotBeEqualForDifferentData(){
        var firstSource = createBasicEntity("source");
        var firstDestination = createBasicEntity("destination");
        var secondSource = createBasicEntity("source");
        var secondDestination = createBasicEntity("destination");

        var actual = createBasicRelationship(firstSource, firstDestination, "relationship");
        var expected = createBasicRelationship(secondSource, secondDestination, "otherRelationship");

        assertThat(actual).isNotEqualTo(expected);
    }

    @Test
    void equals_ShouldNotBeEqualForDifferentSourceEntity(){
        var firstSource = createBasicEntity("source");
        var firstDestination = createBasicEntity("destination");
        var secondSource = createBasicEntity("otherSource");
        var secondDestination = createBasicEntity("destination");

        var actual = createBasicRelationship(firstSource, firstDestination, "relationship");
        var expected = createBasicRelationship(secondSource, secondDestination, "relationship");

        assertThat(actual).isNotEqualTo(expected);
    }

    @Test
    void equals_ShouldNotBeEqualForDifferentDestinationEntity(){
        var firstSource = createBasicEntity("source");
        var firstDestination = createBasicEntity("destination");
        var secondSource = createBasicEntity("source");
        var secondDestination = createBasicEntity("otherDestination");

        var actual = createBasicRelationship(firstSource, firstDestination, "relationship");
        var expected = createBasicRelationship(secondSource, secondDestination, "relationship");

        assertThat(actual).isNotEqualTo(expected);
    }
}