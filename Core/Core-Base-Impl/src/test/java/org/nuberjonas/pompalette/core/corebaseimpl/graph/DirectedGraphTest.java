package org.nuberjonas.pompalette.core.corebaseimpl.graph;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.nuberjonas.pompalette.core.coreapi.graph.Entity;
import org.nuberjonas.pompalette.core.coreapi.graph.EntityFactory;
import org.nuberjonas.pompalette.core.coreapi.graph.Relationship;
import org.nuberjonas.pompalette.core.coreapi.graph.RelationshipFactory;
import org.nuberjonas.pompalette.core.coreapi.graph.exceptions.EntityAlreadyExistsException;
import org.nuberjonas.pompalette.core.coreapi.graph.exceptions.EntityNotFoundException;
import org.nuberjonas.pompalette.core.coreapi.graph.exceptions.RelationshipAlreadyExistsException;
import org.nuberjonas.pompalette.core.coreapi.graph.exceptions.RelationshipNotFoundException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings("unchecked")
class DirectedGraphTest extends BaseTest {

    private DirectedGraph<Entity<String, String>, Relationship<String, String>, String, String> graph;
    private EntityFactory<Entity<String, String>, String, String> entityFactory;
    private RelationshipFactory<Entity<String, String>, Relationship<String, String>, String, String> relationshipFactory;

    @BeforeEach
    void setUp() {
        entityFactory = mock(EntityFactory.class);
        relationshipFactory = mock(RelationshipFactory.class);
        graph = new DirectedGraph<>(entityFactory, relationshipFactory);
    }

    @Test
    void addEntity_ShouldAddEntity_ForEntityObject() throws EntityAlreadyExistsException {
        var entity = createBasicEntity("data");

        graph.addEntity(entity);

        assertThat(graph.getEntity("data")).isEqualTo(entity);
    }

    @Test
    void addEntity_ShouldThrowEntityAlreadyExistsException_ForEntityWhichAlreadyExists(){
        var entity = createBasicEntity("data");
        graph.addEntity(entity);

        assertThatThrownBy(() -> graph.addEntity(entity))
                .isInstanceOf(EntityAlreadyExistsException.class)
                .hasMessage(String.format("The entity: '%s' already exists", entity));
    }

    @Test
    void addEntity_ShouldAddEntity_ForEntityData(){
        var expected = createBasicEntity("data");
        when(entityFactory.createEntity("data")).thenReturn(expected);

        var returnedActual = graph.addEntity("data");
        var actual = graph.getEntity("data");

        assertThat(actual).isEqualTo(returnedActual).isEqualTo(expected);
    }

    @Test
    void addEntity_ShouldThrowEntityAlreadyExistsException_ForEntityDataWhichAlreadyExists(){
        var entity = createBasicEntity("data");
        when(entityFactory.createEntity("data")).thenReturn(entity);
        graph.addEntity("data");

        assertThatThrownBy(() -> graph.addEntity("data"))
                .isInstanceOf(EntityAlreadyExistsException.class)
                .hasMessage(String.format("The entity: '%s' already exists", entity));
    }

    @Test
    void getEntity_ShouldReturnCorrectEntity_ForEntityData(){
        var expected = createBasicEntity("data");
        graph.addEntity(expected);

        var actual = graph.getEntity("data");

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void getEntity_ShouldThrowEntityNotFoundException_ForNotExistingEntity(){
        assertThatThrownBy(() -> graph.getEntity("data"))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("The entity 'data' was not found");
    }

    @Test
    void getEntities_ShouldReturnASetOfEntities_ForAllExistingEntities(){
        var firstEntity = createBasicEntity("first");
        var secondEntity = createBasicEntity("second");
        graph.addEntity(firstEntity);
        graph.addEntity(secondEntity);

        var actual = graph.getEntities();

        assertThat(actual).containsExactly(firstEntity, secondEntity);
    }

    @Test
    void getEntities_ShouldReturnAnEmptySet_ForNoExistingEntities(){
        assertThat(graph.getEntities()).isEmpty();
    }

    @Test
    void getRelationshipSourceOf_ShouldReturnSourceEntity_ForExistingRelationship(){
        var expectedSource = createBasicEntity("source");
        var destination = createBasicEntity("destination");
        var relationship = createBasicRelationship(expectedSource, destination, "relationship");
        when(relationshipFactory.createRelationship(expectedSource, destination, "relationship")).thenReturn(relationship);
        graph.addEntity(expectedSource);
        graph.addEntity(destination);
        graph.addRelationship(expectedSource, destination, "relationship");

        var actualSource = graph.getRelationshipSourceOf(relationship);

        assertThat(actualSource).isEqualTo(expectedSource);
    }

    @Test
    void getRelationshipSourceOf_ShouldThrowEntityNotFoundException_ForNotExistingSourceEntity(){
        var source = createBasicEntity("source");
        var destination = createBasicEntity("destination");
        var relationship = createBasicRelationship(source, destination, "relationship");

        assertThatThrownBy(() -> graph.getRelationshipSourceOf(relationship))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("The entity 'source' was not found");
    }

    @Test
    void getRelationshipSourceOf_ShouldThrowEntityNotFoundException_ForNotExistingDestinationEntity(){
        var source = createBasicEntity("source");
        var destination = createBasicEntity("destination");
        var relationship = createBasicRelationship(source, destination, "relationship");
        graph.addEntity(source);

        assertThatThrownBy(() -> graph.getRelationshipSourceOf(relationship))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("The entity 'destination' was not found");
    }

    @Test
    void getRelationshipSourceOf_ShouldThrowRelationshipNotFoundException_ForNotExistingRelationship(){
        var source = createBasicEntity("source");
        var destination = createBasicEntity("destination");
        var relationship = createBasicRelationship(source, destination, "relationship");
        graph.addEntity(source);
        graph.addEntity(destination);

        assertThatThrownBy(() -> graph.getRelationshipSourceOf(relationship))
                .isInstanceOf(RelationshipNotFoundException.class)
                .hasMessage("Relationship 'BasicRelationship[source=source, destination=destination, data=relationship]' from entity 'source' to entity 'destination' was not found");
    }

    @Test
    void getRelationshipDestinationOf_ShouldReturnDestinationEntity_ForExistingRelationship(){
        var source = createBasicEntity("source");
        var expectedDestination = createBasicEntity("destination");
        var relationship = createBasicRelationship(source, expectedDestination, "relationship");
        when(relationshipFactory.createRelationship(source, expectedDestination, "relationship")).thenReturn(relationship);
        graph.addEntity(source);
        graph.addEntity(expectedDestination);
        graph.addRelationship(source, expectedDestination, "relationship");

        var actualDestination = graph.getRelationshipDestinationOf(relationship);

        assertThat(actualDestination).isEqualTo(expectedDestination);
    }

    @Test
    void getRelationshipDestinationOf_ShouldThrowEntityNotFoundException_ForNotExistingSourceEntity(){
        var source = createBasicEntity("source");
        var destination = createBasicEntity("destination");
        var relationship = createBasicRelationship(source, destination, "relationship");

        assertThatThrownBy(() -> graph.getRelationshipDestinationOf(relationship))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("The entity 'source' was not found");
    }

    @Test
    void getRelationshipDestinationOf_ShouldThrowEntityNotFoundException_ForNotExistingDestinationEntity(){
        var source = createBasicEntity("source");
        var destination = createBasicEntity("destination");
        var relationship = createBasicRelationship(source, destination, "relationship");
        graph.addEntity(source);

        assertThatThrownBy(() -> graph.getRelationshipDestinationOf(relationship))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("The entity 'destination' was not found");
    }

    @Test
    void getRelationshipDestinationOf_ShouldThrowRelationshipNotFoundException_ForNotExistingRelationship(){
        var source = createBasicEntity("source");
        var destination = createBasicEntity("destination");
        var relationship = createBasicRelationship(source, destination, "relationship");
        graph.addEntity(source);
        graph.addEntity(destination);

        assertThatThrownBy(() -> graph.getRelationshipDestinationOf(relationship))
                .isInstanceOf(RelationshipNotFoundException.class)
                .hasMessage("Relationship 'BasicRelationship[source=source, destination=destination, data=relationship]' from entity 'source' to entity 'destination' was not found");
    }

    @Test
    void removeEntity_ShouldRemoveEntity_ForEntityObject(){
        var entity = createBasicEntity("entity");
        graph.addEntity(entity);

        var isRemoved = graph.removeEntity(entity);

        assertThat(isRemoved).isTrue();
        assertThat(graph.getEntities()).isEmpty();
    }

    @Test
    void removeEntity_ShouldRemoveTheEntitiesRelationships_ForEntityObject(){
        var entity = createBasicEntity("entity");
        var someDestination = createBasicEntity("destination");
        var someOtherDestination = createBasicEntity("otherDestination");
        var entityToSomeDestination = createBasicRelationship(entity, someDestination, "relationship");
        var someDestinationToEntity = createBasicRelationship(someDestination, entity, "relationship");
        var someDestinationToSomeOtherDestination = createBasicRelationship(someDestination, someOtherDestination, "relationship");
        when(relationshipFactory.createRelationship(entity, someDestination, "relationship")).thenReturn(entityToSomeDestination);
        when(relationshipFactory.createRelationship(someDestination, entity, "relationship")).thenReturn(someDestinationToEntity);
        when(relationshipFactory.createRelationship(someDestination, someOtherDestination, "relationship")).thenReturn(someDestinationToSomeOtherDestination);

        graph.addEntity(entity);
        graph.addEntity(someDestination);
        graph.addEntity(someOtherDestination);
        graph.addRelationship(entity, someDestination, "relationship");
        graph.addRelationship(someDestination, entity, "relationship");
        graph.addRelationship(someDestination, someOtherDestination, "relationship");
        assertThat(graph.getRelationships()).contains(entityToSomeDestination, someDestinationToEntity, someDestinationToSomeOtherDestination);

        graph.removeEntity(entity);

        assertThat(graph.getRelationships()).containsExactly(someDestinationToSomeOtherDestination);
    }

    @Test
    void removeEntity_ShouldThrowEntityNotFoundException_ForEntityObject(){
        var entity = createBasicEntity("entity");

        assertThatThrownBy(() -> graph.removeEntity(entity))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("The entity 'entity' was not found");
    }

    @Test
    void removeEntity_ShouldRemoveEntity_ForEntityData(){
        var expectedEntity = createBasicEntity("entity");
        graph.addEntity(expectedEntity);

        var actualEntity = graph.removeEntity("entity");

        assertThat(actualEntity).isEqualTo(expectedEntity);
        assertThat(graph.getEntities()).isEmpty();
    }

    @Test
    void removeEntity_ShouldRemoveTheEntitiesRelationships_ForEntityData(){
        var entity = createBasicEntity("entity");
        var someDestination = createBasicEntity("destination");
        var someOtherDestination = createBasicEntity("otherDestination");
        var entityToSomeDestination = createBasicRelationship(entity, someDestination, "relationship");
        var someDestinationToEntity = createBasicRelationship(someDestination, entity, "relationship");
        var someDestinationToSomeOtherDestination = createBasicRelationship(someDestination, someOtherDestination, "relationship");
        when(relationshipFactory.createRelationship(entity, someDestination, "relationship")).thenReturn(entityToSomeDestination);
        when(relationshipFactory.createRelationship(someDestination, entity, "relationship")).thenReturn(someDestinationToEntity);
        when(relationshipFactory.createRelationship(someDestination, someOtherDestination, "relationship")).thenReturn(someDestinationToSomeOtherDestination);

        graph.addEntity(entity);
        graph.addEntity(someDestination);
        graph.addEntity(someOtherDestination);
        graph.addRelationship(entity, someDestination, "relationship");
        graph.addRelationship(someDestination, entity, "relationship");
        graph.addRelationship(someDestination, someOtherDestination, "relationship");
        assertThat(graph.getRelationships()).contains(entityToSomeDestination, someDestinationToEntity, someDestinationToSomeOtherDestination);

        graph.removeEntity("entity");

        assertThat(graph.getRelationships()).containsExactly(someDestinationToSomeOtherDestination);
    }

    @Test
    void removeEntity_ShouldThrowEntityNotFoundException_ForEntityData(){
        assertThatThrownBy(() -> graph.removeEntity("entity"))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("The entity 'entity' was not found");
    }

    @Test
    void removeAllEntities_ShouldRemoveAllEntities_ForACollectionOfEntities(){
        var firstEntity = createBasicEntity("first");
        var secondEntity = createBasicEntity("second");
        var thirdEntity = createBasicEntity("third");
        graph.addEntity(firstEntity);
        graph.addEntity(secondEntity);
        graph.addEntity(thirdEntity);
        assertThat(graph.getEntities()).contains(firstEntity, secondEntity, thirdEntity);

        var isRemoved = graph.removeAllEntities(List.of(firstEntity, secondEntity));

        assertThat(isRemoved).isTrue();
        assertThat(graph.getEntities()).containsExactly(thirdEntity);
    }

    @Test
    void removeAllEntities_ShouldThrowEntityNotFoundException_ForEntityNotInGraph(){
        var firstEntity = createBasicEntity("first");
        var secondEntity = createBasicEntity("second");
        graph.addEntity(firstEntity);

        assertThatThrownBy(() -> graph.removeAllEntities(List.of(firstEntity, secondEntity)))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("The entity 'second' was not found");
    }

    @Test
    void addRelationship_ShouldAddRelationshipWhenNotExists_ForEntityObjects(){
         var source = createBasicEntity("source");
         var destination = createBasicEntity("destination");
         var expectedRelationship = createBasicRelationship(source, destination, "relationship");
         when(relationshipFactory.createRelationship(source, destination, "relationship")).thenReturn(expectedRelationship);
         graph.addEntity(source);
         graph.addEntity(destination);

         var returnedActualRelationship = graph.addRelationship(source, destination, "relationship");

         assertThat(returnedActualRelationship).isEqualTo(expectedRelationship);
         assertThat(graph.getRelationships()).containsExactly(expectedRelationship);
    }

    @Test
    void addRelationship_ShouldThrowEntityNotFoundException_ForSourceEntityObjectNotInGraph(){
        var source = createBasicEntity("source");
        var destination = createBasicEntity("destination");

        assertThatThrownBy(() -> graph.addRelationship(source, destination, "relationship"))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("The entity 'source' was not found");
    }

    @Test
    void addRelationship_ShouldThrowEntityNotFoundException_ForDestinationEntityObjectNotInGraph(){
        var source = createBasicEntity("source");
        var destination = createBasicEntity("destination");
        graph.addEntity(source);

        assertThatThrownBy(() -> graph.addRelationship(source, destination, "relationship"))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("The entity 'destination' was not found");
    }

    @Test
    void addRelationship_ShouldThrowRelationshipAlreadyExistsException_ForRelationshipWhichAlreadyExists() {
        var source = createBasicEntity("source");
        var destination = createBasicEntity("destination");
        var expectedRelationship = createBasicRelationship(source, destination, "relationship");
        when(relationshipFactory.createRelationship(source, destination, "relationship")).thenReturn(expectedRelationship);
        graph.addEntity(source);
        graph.addEntity(destination);

        graph.addRelationship(source, destination, "relationship");

        assertThatThrownBy(() -> graph.addRelationship(source, destination, "relationship"))
                .isInstanceOf(RelationshipAlreadyExistsException.class)
                .hasMessage("Relationship: 'relationship' from entity 'source' to entity 'destination' already exists");
    }

    @Test
    void addRelationship_ShouldAddRelationshipWhenNotExists_ForEntityData(){
        var source = createBasicEntity("source");
        var destination = createBasicEntity("destination");
        var expectedRelationship = createBasicRelationship(source, destination, "relationship");
        when(relationshipFactory.createRelationship(source, destination, "relationship")).thenReturn(expectedRelationship);
        when(entityFactory.createEntity("source")).thenReturn(source);
        when(entityFactory.createEntity("destination")).thenReturn(destination);
        graph.addEntity(source);
        graph.addEntity(destination);

        var returnedActualRelationship = graph.addRelationship("source", "destination", "relationship");

        assertThat(returnedActualRelationship).isEqualTo(expectedRelationship);
        assertThat(graph.getRelationships()).containsExactly(expectedRelationship);
    }

    @Test
    void addRelationship_ShouldThrowEntityNotFoundException_ForSourceEntityDataNotInGraph(){
        assertThatThrownBy(() -> graph.addRelationship("source", "destination", "relationship"))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("The entity 'source' was not found");
    }

    @Test
    void addRelationship_ShouldThrowEntityNotFoundException_ForDestinationEntityDataNotInGraph(){
        var source = createBasicEntity("source");
        when(entityFactory.createEntity("source")).thenReturn(source);
        graph.addEntity(source);

        assertThatThrownBy(() -> graph.addRelationship("source", "destination", "relationship"))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("The entity 'destination' was not found");
    }

    @Test
    void addRelationship_ShouldThrowRelationshipAlreadyExistsException_ForRelationshipWhichAlreadyExists_EntityData() {
        var source = createBasicEntity("source");
        var destination = createBasicEntity("destination");
        var expectedRelationship = createBasicRelationship(source, destination, "relationship");
        when(relationshipFactory.createRelationship(source, destination, "relationship")).thenReturn(expectedRelationship);
        when(entityFactory.createEntity("source")).thenReturn(source);
        when(entityFactory.createEntity("destination")).thenReturn(destination);
        graph.addEntity(source);
        graph.addEntity(destination);

        graph.addRelationship("source", "destination", "relationship");

        assertThatThrownBy(() -> graph.addRelationship("source", "destination", "relationship"))
                .isInstanceOf(RelationshipAlreadyExistsException.class)
                .hasMessage("Relationship: 'relationship' from entity 'source' to entity 'destination' already exists");
    }

    @Test
    void getRelationships_ShouldReturnAllRelationships_ForGraph(){
        var entity = createBasicEntity("entity");
        var someDestination = createBasicEntity("destination");
        var someOtherDestination = createBasicEntity("otherDestination");
        var entityToSomeDestination = createBasicRelationship(entity, someDestination, "relationship");
        var someDestinationToEntity = createBasicRelationship(someDestination, entity, "relationship");
        var someDestinationToSomeOtherDestination = createBasicRelationship(someDestination, someOtherDestination, "relationship");
        when(relationshipFactory.createRelationship(entity, someDestination, "relationship")).thenReturn(entityToSomeDestination);
        when(relationshipFactory.createRelationship(someDestination, entity, "relationship")).thenReturn(someDestinationToEntity);
        when(relationshipFactory.createRelationship(someDestination, someOtherDestination, "relationship")).thenReturn(someDestinationToSomeOtherDestination);

        graph.addEntity(entity);
        graph.addEntity(someDestination);
        graph.addEntity(someOtherDestination);
        graph.addRelationship(entity, someDestination, "relationship");
        graph.addRelationship(someDestination, entity, "relationship");
        graph.addRelationship(someDestination, someOtherDestination, "relationship");

        assertThat(graph.getRelationships()).contains(entityToSomeDestination, someDestinationToEntity, someDestinationToSomeOtherDestination);
    }
    
    @Test
    void getRelationships_ShouldReturnEmptySet_ForNoRelationshipsInGraph(){
        assertThat(graph.getRelationships()).isEmpty();
    }

    @Test
    void getRelationshipsOf_ShouldReturnRelationships_ForSpecifiedEntityObject(){
        var entity = createBasicEntity("entity");
        var someDestination = createBasicEntity("destination");
        var someOtherDestination = createBasicEntity("otherDestination");
        var entityToSomeDestination = createBasicRelationship(entity, someDestination, "relationship");
        var someDestinationToEntity = createBasicRelationship(someDestination, entity, "relationship");
        var someDestinationToSomeOtherDestination = createBasicRelationship(someDestination, someOtherDestination, "relationship");
        when(relationshipFactory.createRelationship(entity, someDestination, "relationship")).thenReturn(entityToSomeDestination);
        when(relationshipFactory.createRelationship(someDestination, entity, "relationship")).thenReturn(someDestinationToEntity);
        when(relationshipFactory.createRelationship(someDestination, someOtherDestination, "relationship")).thenReturn(someDestinationToSomeOtherDestination);
        graph.addEntity(entity);
        graph.addEntity(someDestination);
        graph.addEntity(someOtherDestination);
        graph.addRelationship(entity, someDestination, "relationship");
        graph.addRelationship(someDestination, entity, "relationship");
        graph.addRelationship(someDestination, someOtherDestination, "relationship");

        var actualRelationships = graph.getRelationshipsOf(entity);

        assertThat(actualRelationships).containsExactly(entityToSomeDestination, someDestinationToEntity);
    }

    @Test
    void getRelationshipsOf_ShouldReturnEmptySet_ForNoRelationships_EntityObject(){
        var entity = createBasicEntity("entity");
        graph.addEntity(entity);

        var actualRelationships = graph.getRelationshipsOf(entity);

        assertThat(actualRelationships).isEmpty();
    }

    @Test
    void getRelationshipsOf_ShouldThrowEntityNotFoundException_ForNotFoundEntityObject(){
        var entity = createBasicEntity("entity");

        assertThatThrownBy(() -> graph.getRelationshipsOf(entity))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("The entity 'entity' was not found");
    }

    @Test
    void getRelationshipsOf_ShouldReturnRelationships_ForSpecifiedEntityData(){
        var entity = createBasicEntity("entity");
        var someDestination = createBasicEntity("destination");
        var someOtherDestination = createBasicEntity("otherDestination");
        var entityToSomeDestination = createBasicRelationship(entity, someDestination, "relationship");
        var someDestinationToEntity = createBasicRelationship(someDestination, entity, "relationship");
        var someDestinationToSomeOtherDestination = createBasicRelationship(someDestination, someOtherDestination, "relationship");
        when(relationshipFactory.createRelationship(entity, someDestination, "relationship")).thenReturn(entityToSomeDestination);
        when(relationshipFactory.createRelationship(someDestination, entity, "relationship")).thenReturn(someDestinationToEntity);
        when(relationshipFactory.createRelationship(someDestination, someOtherDestination, "relationship")).thenReturn(someDestinationToSomeOtherDestination);
        graph.addEntity(entity);
        graph.addEntity(someDestination);
        graph.addEntity(someOtherDestination);
        graph.addRelationship(entity, someDestination, "relationship");
        graph.addRelationship(someDestination, entity, "relationship");
        graph.addRelationship(someDestination, someOtherDestination, "relationship");

        var actualRelationships = graph.getRelationshipsOf("entity");

        assertThat(actualRelationships).containsExactly(entityToSomeDestination, someDestinationToEntity);
    }

    @Test
    void getRelationshipsOf_ShouldReturnEmptySet_ForNoRelationships_EntityData(){
        var entity = createBasicEntity("entity");
        graph.addEntity(entity);

        var actualRelationships = graph.getRelationshipsOf("entity");

        assertThat(actualRelationships).isEmpty();
    }

    @Test
    void getRelationshipsOf_ShouldThrowEntityNotFoundException_ForNotFoundEntityData(){
        assertThatThrownBy(() -> graph.getRelationshipsOf("entity"))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("The entity 'entity' was not found");
    }

    @Test
    void getRelationshipsBetween_ShouldReturnRelationshipsBetweenTwoEntities_ForEntityObjects(){
        var entity = createBasicEntity("entity");
        var someDestination = createBasicEntity("destination");
        var someOtherDestination = createBasicEntity("otherDestination");
        var entityToSomeDestination = createBasicRelationship(entity, someDestination, "relationship");
        var someDestinationToEntity = createBasicRelationship(someDestination, entity, "relationship");
        var someDestinationToSomeOtherDestination = createBasicRelationship(someDestination, someOtherDestination, "relationship");
        when(relationshipFactory.createRelationship(entity, someDestination, "relationship")).thenReturn(entityToSomeDestination);
        when(relationshipFactory.createRelationship(someDestination, entity, "relationship")).thenReturn(someDestinationToEntity);
        when(relationshipFactory.createRelationship(someDestination, someOtherDestination, "relationship")).thenReturn(someDestinationToSomeOtherDestination);
        graph.addEntity(entity);
        graph.addEntity(someDestination);
        graph.addEntity(someOtherDestination);
        graph.addRelationship(entity, someDestination, "relationship");
        graph.addRelationship(someDestination, entity, "relationship");
        graph.addRelationship(someDestination, someOtherDestination, "relationship");

        var actualRelationships = graph.getRelationshipsBetween(someDestination, someOtherDestination);

        assertThat(actualRelationships).containsExactly(someDestinationToSomeOtherDestination);
    }

    @Test
    void getRelationshipsBetween_ShouldReturnEmptySet_ForNoRelationshipsBetweenEntities_EntityObjects(){
        var source = createBasicEntity("source");
        var destination = createBasicEntity("destination");
        graph.addEntity(source);
        graph.addEntity(destination);

        var actualRelationships = graph.getRelationshipsBetween(source, destination);

        assertThat(actualRelationships).isEmpty();
    }

    @Test
    void getRelationshipsBetween_ShouldThrowEntityNotFoundException_ForSourceEntityObjectNotFound(){
        var source = createBasicEntity("source");
        var destination = createBasicEntity("destination");

        assertThatThrownBy(() -> graph.getRelationshipsBetween(source, destination))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("The entity 'source' was not found");
    }

    @Test
    void getRelationshipsBetween_ShouldThrowEntityNotFoundException_ForDestinationEntityObjectNotFound(){
        var source = createBasicEntity("source");
        var destination = createBasicEntity("destination");
        graph.addEntity(source);

        assertThatThrownBy(() -> graph.getRelationshipsBetween(source, destination))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("The entity 'destination' was not found");
    }
}