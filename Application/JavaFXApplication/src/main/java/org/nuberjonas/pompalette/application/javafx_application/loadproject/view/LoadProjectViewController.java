package org.nuberjonas.pompalette.application.javafx_application.loadproject.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.lang3.StringUtils;
import org.nuberjonas.pompalette.application.javafx_application.loadproject.viewmodel.LoadProjectViewModel;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

public class LoadProjectViewController {

    @FXML
    private TextField projectPathTextfield;
    @FXML
    private Button loadButton;

    private LoadProjectViewModel viewModel;
    private Stage primaryStage;

    public void init(LoadProjectViewModel viewModel, Stage primaryStage){
        this.viewModel = viewModel;
        this.primaryStage = primaryStage;

        viewModel.projectPathProperty().bind(projectPathTextfield.textProperty());
    }

    @FXML
    public void onLoadButtonPress(ActionEvent event){
        if(StringUtils.isNotEmpty(viewModel.projectPathProperty().get()) && StringUtils.isNotBlank(viewModel.projectPathProperty().get())){
            viewModel.initiateLoading();
        }
    }

    @FXML
    public void openFileChooser(ActionEvent event){
        var fileChooser = new FileChooser();
        fileChooser.setTitle("Select Maven Project Pom");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Maven Projects","pom.xml"));
        getProjectPathFromTextField().ifPresentOrElse(fileChooser::setInitialDirectory, () -> {});

        Optional.ofNullable(fileChooser.showOpenDialog(primaryStage))
                .ifPresentOrElse(f -> projectPathTextfield.setText(f.getAbsolutePath()), () -> projectPathTextfield.setText(projectPathTextfield.getText()));
    }

    private Optional<File> getProjectPathFromTextField(){
        var currentTextFieldInput = projectPathTextfield.getText();
        var path = Path.of(currentTextFieldInput);

        if(StringUtils.isEmpty(currentTextFieldInput)){
            return Optional.empty();
        } else if(Files.isDirectory(path)) {
            return Optional.of(new File(currentTextFieldInput));
        } else if(Files.isRegularFile(path)){
            return Optional.of(new File(currentTextFieldInput).getParentFile());
        } else {
            return Optional.empty();
        }
    }
}
