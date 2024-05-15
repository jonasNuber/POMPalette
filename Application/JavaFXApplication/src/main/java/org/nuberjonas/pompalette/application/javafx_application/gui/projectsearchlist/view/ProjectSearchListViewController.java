package org.nuberjonas.pompalette.application.javafx_application.gui.projectsearchlist.view;

import com.brunomnsilva.smartgraph.graph.Vertex;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import org.nuberjonas.pompalette.application.javafx_application.events.LoadDependencyGraphEvent;
import org.nuberjonas.pompalette.application.javafx_application.gui.projectsearchlist.viewmodel.ProjectSearchListViewModel;
import org.nuberjonas.pompalette.core.model.domain.graph.relationship.DependencyRelationship;
import org.nuberjonas.pompalette.core.model.domain.project.MavenProject;
import org.nuberjonas.pompalette.core.model.domain.project.Project;
import org.nuberjonas.pompalette.core.model.domain.project.dependecies.Dependency;
import org.nuberjonas.pompalette.core.model.domain.project.dependecies.DependencyScope;
import org.nuberjonas.pompalette.core.model.domain.project.dependecies.DependencyType;
import org.nuberjonas.pompalette.core.model.domain.project.dependecies.DependencyVersion;
import org.nuberjonas.pompalette.core.model.domain.searchlist.ProjectSearchListElement;
import org.nuberjonas.pompalette.infrastructure.eventbus.EventBus;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ProjectSearchListViewController {
    @FXML
    private VBox root;
    @FXML
    private TextField searchBar;
    @FXML
    private ListView<ProjectSearchListElement> listView;
    private final Map<ProjectSearchListElement, Boolean> expandedCells = new HashMap<>();

    private ProjectSearchListViewModel viewModel;

    public void init(ProjectSearchListViewModel viewModel) {
        this.viewModel = viewModel;

        listView.itemsProperty().bindBidirectional(viewModel.itemsProperty());
        listView.setCellFactory(param -> createListCell());
        listView.setOnMousePressed(this::forwardEventToParent);
        listView.setOnMouseDragged(this::forwardEventToParent);
        listView.setOnMouseClicked(this::forwardEventToParent);

        searchBar.textProperty().addListener((observable, oldValue, newValue) -> viewModel.filterItems(newValue));
        searchBar.setOnMousePressed(this::forwardEventToParent);
        searchBar.setOnMouseDragged(this::forwardEventToParent);
        searchBar.setOnMouseClicked(this::forwardEventToParent);
    }

    private ListCell<ProjectSearchListElement> createListCell(){
        return new ListCell<>() {
            @Override
            protected void updateItem(ProjectSearchListElement searchListElement, boolean empty) {
                super.updateItem(searchListElement, empty);

                if (empty || searchListElement == null) {
                    setGraphic(null);
                } else {
                    if(searchListElement.projectVertex().element() instanceof Dependency){
                        setGraphic(getDependencyTitleFor(searchListElement.projectVertex()));
                    } else {
                        setGraphic(getProjectTitleFor(searchListElement.projectVertex()));
                    }


                    setPrefHeight(70.0);

                    setOnMouseClicked(event -> {
                        if(event.getButton() == MouseButton.MIDDLE){
                            propagateRightMouseDrag(event);
                        } else if (!event.isConsumed()) {
                            handleItemClick(event, searchListElement);
                        }
                    });
                    setOnMouseDragged(event -> {
                        if (!event.isConsumed()) {
                            propagateRightMouseDrag(event);
                        }
                    });
                }
            }

            private void handleItemClick(MouseEvent event, ProjectSearchListElement searchListElement) {
                boolean isExpanded = expandedCells.getOrDefault(searchListElement, false);

                if (isExpanded) {
                    if(searchListElement.projectVertex().element() instanceof Dependency){
                        setGraphic(getDependencyTitleFor(searchListElement.projectVertex()));
                    } else {
                        setGraphic(getProjectTitleFor(searchListElement.projectVertex()));
                    }
                    expandedCells.put(searchListElement, false);

                    setPrefHeight(70.0);
                } else {
                    Text titleText = null;
                    TextFlow expandedText = null;

                    if(searchListElement.projectVertex().element() instanceof Dependency){
                        titleText = getDependencyTitleFor(searchListElement.projectVertex());
                        expandedText = getDependencyInformationFor(searchListElement);
                    } else {
                        titleText = getProjectTitleFor(searchListElement.projectVertex());
                        expandedText = getProjectInformationFor(searchListElement);
                    }

                    var dependencyGraphButton = new Button("Show");
                    dependencyGraphButton.setOnMouseClicked(e -> EventBus.getInstance().publish(new LoadDependencyGraphEvent(searchListElement.projectVertex())));

                    ScrollPane scrollPane = new ScrollPane();
                    scrollPane.setFitToWidth(true);
                    scrollPane.setContent(new VBox(expandedText, dependencyGraphButton));

                    VBox vbox = new VBox(titleText, scrollPane);
                    vbox.setSpacing(10);

                    setPrefHeight(400);
                    setGraphic(vbox);
                    expandedCells.put(searchListElement, true);
                }
            }

            private void propagateRightMouseDrag(MouseEvent event) {
                if (event.isSecondaryButtonDown() || event.getButton() == MouseButton.MIDDLE) {
                    event.consume();
                    forwardEventToParent(event);
                }
            }
        };
    }

    private Text getDependencyTitleFor(Vertex<Project> projectVertex){
        var title = new Text(((Dependency)projectVertex.element()).dependencyCoordinates().toString());
        title.setStyle("-fx-font-weight: bold;");
        title.setLineSpacing(5);
        title.wrappingWidthProperty().bind(listView.widthProperty().subtract(20));

        return title;
    }

    private Text getProjectTitleFor(Vertex<Project> projectVertex){
        var title = new Text(projectVertex.element().getCoordinates().toString());
        title.setStyle("-fx-font-weight: bold;");
        title.setLineSpacing(5);
        title.wrappingWidthProperty().bind(listView.widthProperty().subtract(20));

        return title;
    }


    private TextFlow getDependencyInformationFor(ProjectSearchListElement element) {
        var versions = collectDependencyInfo(element, DependencyRelationship::dependencyVersion);
        var scopes = collectDependencyInfo(element, DependencyRelationship::scope);
        var types = collectDependencyInfo(element, DependencyRelationship::type);


        var versionsHeader = new Text("Versions:\n");
        versionsHeader.setStyle("-fx-font-weight: bold;");
        var versionsText = new Text(versions.stream().map(DependencyVersion::toString).collect(Collectors.joining(", ")) + "\n\n");
        versionsText.wrappingWidthProperty().bind(listView.widthProperty().subtract(20));

        var scopesHeader = new Text("Scopes:\n");
        scopesHeader.setStyle("-fx-font-weight: bold;");
        var scopesText = new Text(scopes.stream().map(DependencyScope::toString).collect(Collectors.joining(", ")) + "\n\n");
        scopesText.wrappingWidthProperty().bind(listView.widthProperty().subtract(20));

        var typesHeader = new Text("Types:\n");
        typesHeader.setStyle("-fx-font-weight: bold;");
        var typesText = new Text(types.stream().map(DependencyType::toString).collect(Collectors.joining(", ")) + "\n\n");
        typesText.wrappingWidthProperty().bind(listView.widthProperty().subtract(20));

        return new TextFlow(versionsHeader, versionsText, scopesHeader, scopesText, typesHeader, typesText);
    }


    private <T> Set<T> collectDependencyInfo(ProjectSearchListElement element, Function<DependencyRelationship, T> mapper) {
        var result = new HashSet<T>();
        for (var relationship : element.relationships()) {
            result.add(mapper.apply(relationship));
        }
        return result;
    }

    private TextFlow getProjectInformationFor(ProjectSearchListElement element){
        var projectInformation = ((MavenProject)element.projectVertex().element()).getProjectInformation();

        var nameHeader = new Text("Name:\n");
        nameHeader.setStyle("-fx-font-weight: bold;");
        var nameText = new Text(((MavenProject)element.projectVertex().element()).getName() + "\n\n");
        nameText.wrappingWidthProperty().bind(listView.widthProperty().subtract(20));

        var packagingHeader = new Text("Packaging:\n");
        packagingHeader.setStyle("-fx-font-weight: bold;");
        var packagingText = new Text(projectInformation.packaging() + "\n\n");
        packagingText.wrappingWidthProperty().bind(listView.widthProperty().subtract(20));

        var descriptionHeader = new Text("Description:\n");
        descriptionHeader.setStyle("-fx-font-weight: bold;");
        var descriptionText = new Text(projectInformation.description() + "\n\n");
        descriptionText.wrappingWidthProperty().bind(listView.widthProperty().subtract(20));

        var urlHeader = new Text("URL:\n");
        urlHeader.setStyle("-fx-font-weight: bold;");
        var urlText = new Text(projectInformation.url() + "\n\n");
        urlText.wrappingWidthProperty().bind(listView.widthProperty().subtract(20));

        var modulesHeader = new Text("Modules:\n");
        modulesHeader.setStyle("-fx-font-weight: bold;");
        var modulesText = new Text(String.join(", ", projectInformation.modules()) + "\n\n");
        modulesText.wrappingWidthProperty().bind(listView.widthProperty().subtract(20));

        return new TextFlow(nameHeader, nameText, packagingHeader, packagingText, descriptionHeader, descriptionText, urlHeader, urlText, modulesHeader, modulesText);
    }

    private void forwardEventToParent(MouseEvent event) {
        if (event.isSecondaryButtonDown() || event.getButton() == MouseButton.MIDDLE) {
            event.consume();
            root.fireEvent(event.copyFor(root, root.getParent()));
        }
    }
}
