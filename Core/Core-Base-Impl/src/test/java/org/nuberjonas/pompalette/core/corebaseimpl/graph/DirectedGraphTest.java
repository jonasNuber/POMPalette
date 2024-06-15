package org.nuberjonas.pompalette.core.corebaseimpl.graph;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.nuberjonas.pompalette.core.coreapi.graph.Entity;
import org.nuberjonas.pompalette.core.coreapi.graph.EntityFactory;
import org.nuberjonas.pompalette.core.coreapi.graph.Relationship;
import org.nuberjonas.pompalette.core.coreapi.graph.RelationshipFactory;
import org.nuberjonas.pompalette.core.coreapi.graph.exceptions.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
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
    void addEntity_ShouldThrowEntityAlreadyExistsException_ForEntityObjectWhichAlreadyExists(){
        var entity = createBasicEntity("data");
        graph.addEntity(entity);

        var thrown = catchThrowable(() -> graph.addEntity(entity));

        assertThat(thrown)
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

        var thrown = catchThrowable(() -> graph.addEntity("data"));

        assertThat(thrown)
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
        var thrown = catchThrowable(() -> graph.getEntity("data"));

        assertThat(thrown)
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

        var thrown = catchThrowable(() -> graph.getRelationshipSourceOf(relationship));

        assertThat(thrown)
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("The entity 'source' was not found");
    }

    @Test
    void getRelationshipSourceOf_ShouldThrowEntityNotFoundException_ForNotExistingDestinationEntity(){
        var source = createBasicEntity("source");
        var destination = createBasicEntity("destination");
        var relationship = createBasicRelationship(source, destination, "relationship");
        graph.addEntity(source);

        var thrown = catchThrowable(() -> graph.getRelationshipSourceOf(relationship));

        assertThat(thrown)
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

        var thrown = catchThrowable(() -> graph.getRelationshipSourceOf(relationship));

        assertThat(thrown)
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

        var thrown = catchThrowable(() -> graph.getRelationshipDestinationOf(relationship));

        assertThat(thrown)
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("The entity 'source' was not found");
    }

    @Test
    void getRelationshipDestinationOf_ShouldThrowEntityNotFoundException_ForNotExistingDestinationEntity(){
        var source = createBasicEntity("source");
        var destination = createBasicEntity("destination");
        var relationship = createBasicRelationship(source, destination, "relationship");
        graph.addEntity(source);

        var thrown = catchThrowable(() -> graph.getRelationshipDestinationOf(relationship));

        assertThat(thrown)
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

        var thrown = catchThrowable(() -> graph.getRelationshipDestinationOf(relationship));

        assertThat(thrown)
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

        var thrown = catchThrowable(() -> graph.removeEntity(entity));

        assertThat(thrown)
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
        var thrown = catchThrowable(() -> graph.removeEntity("entity"));

        assertThat(thrown)
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
    void removeAllEntities_ShouldThrowNotAllEntitiesRemovedException_ForEntityNotInGraph(){
        var firstEntity = createBasicEntity("first");
        var secondEntity = createBasicEntity("second");
        graph.addEntity(firstEntity);

        var thrown = catchThrowable(() -> graph.removeAllEntities(List.of(firstEntity, secondEntity)));
        var enclosedExceptions = ((NotAllEntitiesRemovedException) thrown).getExceptions();

        assertThat(thrown)
                .isInstanceOf(NotAllEntitiesRemovedException.class)
                .hasMessageContaining("Not all entities could be removed");
        assertThat(enclosedExceptions).hasSize(1);
        assertThat(enclosedExceptions.getFirst())
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("The entity 'second' was not found");
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

        var thrown = catchThrowable(() -> graph.addRelationship(source, destination, "relationship"));

        assertThat(thrown)
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("The entity 'source' was not found");
    }

    @Test
    void addRelationship_ShouldThrowEntityNotFoundException_ForDestinationEntityObjectNotInGraph(){
        var source = createBasicEntity("source");
        var destination = createBasicEntity("destination");
        graph.addEntity(source);

        var thrown = catchThrowable(() -> graph.addRelationship(source, destination, "relationship"));

        assertThat(thrown)
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

        var thrown = catchThrowable(() -> graph.addRelationship(source, destination, "relationship"));

        assertThat(thrown)
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
        var thrown = catchThrowable(() -> graph.addRelationship("source", "destination", "relationship"));

        assertThat(thrown)
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("The entity 'source' was not found");
    }

    @Test
    void addRelationship_ShouldThrowEntityNotFoundException_ForDestinationEntityDataNotInGraph(){
        var source = createBasicEntity("source");
        when(entityFactory.createEntity("source")).thenReturn(source);
        graph.addEntity(source);

        var thrown = catchThrowable(() -> graph.addRelationship("source", "destination", "relationship"));

        assertThat(thrown)
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

        var thrown = catchThrowable(() -> graph.addRelationship("source", "destination", "relationship"));

        assertThat(thrown)
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

        var thrown = catchThrowable(() -> graph.getRelationshipsOf(entity));

        assertThat(thrown)
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
        var thrown = catchThrowable(() -> graph.getRelationshipsOf("entity"));

        assertThat(thrown)
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

        var thrown = catchThrowable(() -> graph.getRelationshipsBetween(source, destination));

        assertThat(thrown)
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("The entity 'source' was not found");
    }

    @Test
    void getRelationshipsBetween_ShouldThrowEntityNotFoundException_ForDestinationEntityObjectNotFound(){
        var source = createBasicEntity("source");
        var destination = createBasicEntity("destination");
        graph.addEntity(source);

        var thrown = catchThrowable(() -> graph.getRelationshipsBetween(source, destination));

        assertThat(thrown)
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("The entity 'destination' was not found");
    }

    @Test
    void getRelationshipsBetween_ShouldReturnRelationshipsBetweenTwoEntities_ForEntitiesData(){
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

        var actualRelationships = graph.getRelationshipsBetween("destination", "otherDestination");

        assertThat(actualRelationships).containsExactly(someDestinationToSomeOtherDestination);
    }

    @Test
    void getRelationshipsBetween_ShouldReturnEmptySet_ForNoRelationshipsBetweenEntities_EntitiesData(){
        var source = createBasicEntity("source");
        var destination = createBasicEntity("destination");
        graph.addEntity(source);
        graph.addEntity(destination);

        var actualRelationships = graph.getRelationshipsBetween("source", "destination");

        assertThat(actualRelationships).isEmpty();
    }

    @Test
    void getRelationshipsBetween_ShouldThrowEntityNotFoundException_ForSourceEntityDataNotFound(){
        var thrown = catchThrowable(() -> graph.getRelationshipsBetween("source", "destination"));

        assertThat(thrown)
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("The entity 'source' was not found");
    }

    @Test
    void getRelationshipsBetween_ShouldThrowEntityNotFoundException_ForDestinationEntityDataNotFound(){
        var source = createBasicEntity("source");
        graph.addEntity(source);

        var thrown = catchThrowable(() -> graph.getRelationshipsBetween("source", "destination"));

        assertThat(thrown)
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("The entity 'destination' was not found");
    }

    @Test
    void getIncomingRelationshipsOf_ShouldReturnIncomingRelationships_ForEntityObject(){
        var source = createBasicEntity("source");
        var destination = createBasicEntity("destination");
        var expectedIncoming = createBasicRelationship(destination, source, "incoming");
        var outgoing = createBasicRelationship(source, destination, "outgoing");
        when(relationshipFactory.createRelationship(destination, source, "incoming")).thenReturn(expectedIncoming);
        when(relationshipFactory.createRelationship(source, destination, "outgoing")).thenReturn(outgoing);
        graph.addEntity(source);
        graph.addEntity(destination);
        graph.addRelationship(destination, source, "incoming");
        graph.addRelationship(source, destination, "outgoing");

        var actualIncomingRelationships = graph.getIncomingRelationshipsOf(source);

        assertThat(actualIncomingRelationships).containsExactly(expectedIncoming);
    }

    @Test
    void getIncomingRelationshipsOf_ShouldReturnEmptySet_ForNoIncomingRelationshipsInEntityObject(){
        var source = createBasicEntity("source");
        graph.addEntity(source);

        var incomingRelationships = graph.getIncomingRelationshipsOf(source);

        assertThat(incomingRelationships).isEmpty();
    }

    @Test
    void getIncomingRelationshipsOf_ShouldThrowEntityNotFoundException_ForNotFoundEntityObject(){
        var source = createBasicEntity("source");

        var thrown = catchThrowable(() -> graph.getIncomingRelationshipsOf(source));

        assertThat(thrown)
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("The entity 'source' was not found");
    }

    @Test
    void getIncomingRelationshipsOf_ShouldReturnIncomingRelationships_ForEntitiesData(){
        var source = createBasicEntity("source");
        var destination = createBasicEntity("destination");
        var expectedIncoming = createBasicRelationship(destination, source, "incoming");
        var outgoing = createBasicRelationship(source, destination, "outgoing");
        when(relationshipFactory.createRelationship(destination, source, "incoming")).thenReturn(expectedIncoming);
        when(relationshipFactory.createRelationship(source, destination, "outgoing")).thenReturn(outgoing);
        graph.addEntity(source);
        graph.addEntity(destination);
        graph.addRelationship(destination, source, "incoming");
        graph.addRelationship(source, destination, "outgoing");

        var actualIncomingRelationships = graph.getIncomingRelationshipsOf("source");

        assertThat(actualIncomingRelationships).containsExactly(expectedIncoming);
    }

    @Test
    void getIncomingRelationshipsOf_ShouldReturnEmptySet_ForNoIncomingRelationshipsInEntitiesData(){
        var source = createBasicEntity("source");
        graph.addEntity(source);

        var incomingRelationships = graph.getIncomingRelationshipsOf("source");

        assertThat(incomingRelationships).isEmpty();
    }

    @Test
    void getIncomingRelationshipsOf_ShouldThrowEntityNotFoundException_ForNotFoundEntityData(){
        var thrown = catchThrowable(() -> graph.getIncomingRelationshipsOf("source"));

        assertThat(thrown)
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("The entity 'source' was not found");
    }

    @Test
    void getOutgoingRelationshipsOf_ShouldReturnOutgoingRelationships_ForEntityObject(){
        var source = createBasicEntity("source");
        var destination = createBasicEntity("destination");
        var incoming = createBasicRelationship(destination, source, "incoming");
        var expectedOutgoing = createBasicRelationship(source, destination, "outgoing");
        when(relationshipFactory.createRelationship(destination, source, "incoming")).thenReturn(incoming);
        when(relationshipFactory.createRelationship(source, destination, "outgoing")).thenReturn(expectedOutgoing);
        graph.addEntity(source);
        graph.addEntity(destination);
        graph.addRelationship(destination, source, "incoming");
        graph.addRelationship(source, destination, "outgoing");

        var actualOutgoingRelationships = graph.getOutgoingRelationshipsOf(source);

        assertThat(actualOutgoingRelationships).containsExactly(expectedOutgoing);
    }

    @Test
    void getOutgoingRelationshipsOf_ShouldReturnEmptySet_ForNoOutgoingRelationshipsInEntitiesObject(){
        var source = createBasicEntity("source");
        graph.addEntity(source);

        var outgoingRelationships = graph.getOutgoingRelationshipsOf(source);

        assertThat(outgoingRelationships).isEmpty();
    }

    @Test
    void getOutgoingRelationshipsOf_ShouldThrowEntityNotFoundException_ForNotFoundEntityObject(){
        var source = createBasicEntity("source");

        var thrown = catchThrowable(() -> graph.getOutgoingRelationshipsOf(source));

        assertThat(thrown)
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("The entity 'source' was not found");
    }

    @Test
    void getOutgoingRelationshipsOf_ShouldReturnOutgoingRelationships_ForEntitiesData(){
        var source = createBasicEntity("source");
        var destination = createBasicEntity("destination");
        var incoming = createBasicRelationship(destination, source, "incoming");
        var expectedOutgoing = createBasicRelationship(source, destination, "outgoing");
        when(relationshipFactory.createRelationship(destination, source, "incoming")).thenReturn(incoming);
        when(relationshipFactory.createRelationship(source, destination, "outgoing")).thenReturn(expectedOutgoing);
        graph.addEntity(source);
        graph.addEntity(destination);
        graph.addRelationship(destination, source, "incoming");
        graph.addRelationship(source, destination, "outgoing");

        var actualOutgoingRelationships = graph.getOutgoingRelationshipsOf("source");

        assertThat(actualOutgoingRelationships).containsExactly(expectedOutgoing);
    }

    @Test
    void getOutgoingRelationshipsOf_ShouldReturnEmptySet_ForNoOutgoingRelationshipsInEntitiesData(){
        var source = createBasicEntity("source");
        graph.addEntity(source);

        var outgoingRelationships = graph.getOutgoingRelationshipsOf("source");

        assertThat(outgoingRelationships).isEmpty();
    }

    @Test
    void getOutgoingRelationshipsOf_ShouldThrowEntityNotFoundException_ForNotFoundEntitiesData(){
        var thrown = catchThrowable(() -> graph.getOutgoingRelationshipsOf("source"));

        assertThat(thrown)
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("The entity 'source' was not found");
    }

    @Test
    void removeRelationship_ShouldRemoveRelationship_ForExistingRelationship(){
        var source = createBasicEntity("source");
        var destination = createBasicEntity("destination");
        var relationship = createBasicRelationship(source, destination, "relationship");
        when(relationshipFactory.createRelationship(source, destination, "relationship")).thenReturn(relationship);
        graph.addEntity(source);
        graph.addEntity(destination);
        graph.addRelationship(source, destination, "relationship");
        assertThat(graph.getRelationships()).containsExactly(relationship);

        var isRemoved = graph.removeRelationship(relationship);

        assertThat(isRemoved).isTrue();
        assertThat(graph.getRelationships()).isEmpty();
        assertThat(graph.getOutgoingRelationshipsOf(source)).isEmpty();
    }

    @Test
    void removeRelationship_ShouldThrowRelationshipNotFoundException_ForARelationshipNotInTheGraph(){
        var source = createBasicEntity("source");
        var destination = createBasicEntity("destination");
        var relationship = createBasicRelationship(source, destination, "relationship");
        graph.addEntity(source);
        graph.addEntity(destination);
        assertThat(graph.getRelationships()).isEmpty();

        var thrown = catchThrowable(() -> graph.removeRelationship(relationship));

        assertThat(thrown)
                .isInstanceOf(RelationshipNotFoundException.class)
                .hasMessage("Relationship '"+ relationship.toString() +"' from entity 'source' to entity 'destination' was not found");
    }

    @Test
    void removeRelationship_ShouldThrowEntityNotFoundException_ForSourceEntityNotInTheGraph(){
        var source = createBasicEntity("source");
        var destination = createBasicEntity("destination");
        var relationship = createBasicRelationship(source, destination, "relationship");

        var thrown = catchThrowable(() -> graph.removeRelationship(relationship));

        assertThat(thrown)
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("The entity 'source' was not found");
    }

    @Test
    void removeRelationship_ShouldThrowEntityNotFoundException_ForDestinationEntityNotInTheGraph(){
        var source = createBasicEntity("source");
        var destination = createBasicEntity("destination");
        var relationship = createBasicRelationship(source, destination, "relationship");
        graph.addEntity(source);

        var thrown = catchThrowable(() -> graph.removeRelationship(relationship));

        assertThat(thrown)
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("The entity 'destination' was not found");
    }

    @Test
    void removeAllRelationships_ShouldRemoveRelationships_ForExistingRelationships(){
        var source = createBasicEntity("source");
        var destination = createBasicEntity("destination");
        var otherDestination = createBasicEntity("otherDestination");
        var firstRelationship = createBasicRelationship(source, destination, "firstRelationship");
        var secondRelationship = createBasicRelationship(source, otherDestination, "secondRelationship");
        var thirdRelationship = createBasicRelationship(destination, otherDestination, "thirdRelationship");
        when(relationshipFactory.createRelationship(source, destination, "firstRelationship")).thenReturn(firstRelationship);
        when(relationshipFactory.createRelationship(source, otherDestination, "secondRelationship")).thenReturn(secondRelationship);
        when(relationshipFactory.createRelationship(destination, otherDestination, "thirdRelationship")).thenReturn(thirdRelationship);
        graph.addEntity(source);
        graph.addEntity(destination);
        graph.addEntity(otherDestination);
        graph.addRelationship(source, destination, "firstRelationship");
        graph.addRelationship(source, otherDestination, "secondRelationship");
        graph.addRelationship(destination, otherDestination, "thirdRelationship");
        assertThat(graph.getRelationships()).containsExactlyInAnyOrder(firstRelationship, secondRelationship, thirdRelationship);

        var isRemoved = graph.removeAllRelationships(List.of(firstRelationship, secondRelationship));

        assertThat(isRemoved).isTrue();
        assertThat(graph.getRelationships()).containsExactly(thirdRelationship);
    }

    @Test
    void removeAllRelationships_ShouldThrowEntityNotFoundException_ForSourceEntity(){
        var source = createBasicEntity("source");
        var destination = createBasicEntity("destination");
        var otherDestination = createBasicEntity("otherDestination");
        var firstRelationship = createBasicRelationship(source, destination, "firstRelationship");
        var secondRelationship = createBasicRelationship(source, otherDestination, "secondRelationship");

        var thrown = catchThrowable(() -> graph.removeAllRelationships(List.of(firstRelationship, secondRelationship)));

        assertThat(thrown)
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("The entity 'source' was not found");
    }

    @Test
    void removeAllRelationships_ShouldThrowEntityNotFoundException_ForDestinationEntity(){
        var source = createBasicEntity("source");
        var destination = createBasicEntity("destination");
        var otherDestination = createBasicEntity("otherDestination");
        var firstRelationship = createBasicRelationship(source, destination, "firstRelationship");
        var secondRelationship = createBasicRelationship(source, otherDestination, "secondRelationship");
        graph.addEntity(source);

        var thrown = catchThrowable(() -> graph.removeAllRelationships(List.of(firstRelationship, secondRelationship)));

        assertThat(thrown)
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("The entity 'destination' was not found");
    }

    @Test
    void removeAllRelationships_ShouldThrowRelationshipNotFoundException_ForNotExistingRelationship(){
        var source = createBasicEntity("source");
        var destination = createBasicEntity("destination");
        var firstRelationship = createBasicRelationship(source, destination, "firstRelationship");
        graph.addEntity(source);
        graph.addEntity(destination);

        var thrown = catchThrowable(() -> graph.removeAllRelationships(List.of(firstRelationship)));

        assertThat(thrown)
                .isInstanceOf(RelationshipNotFoundException.class)
                .hasMessage("Relationship '"+ firstRelationship.toString() +"' from entity 'source' to entity 'destination' was not found");
    }

//    @Test
//    void removeAllRelationships_ShouldRemoveAllRelationships_BeforeAEntityNotFoundExceptionIsThrown_ForNotExistingEntity(){
//        var source = createBasicEntity("source");
//        var destination = createBasicEntity("destination");
//        var otherDestination = createBasicEntity("otherDestination");
//        var firstRelationship = createBasicRelationship(source, destination, "firstRelationship");
//        var secondRelationship = createBasicRelationship(destination, otherDestination, "secondRelationship");
//        when(graph.addRelationship(source, destination, "firstRelationship")).thenReturn(firstRelationship);
//        graph.addEntity(source);
//        graph.addEntity(destination);
//        graph.addRelationship(source, destination, "firstRelationship");
//
//
//
//    }
}