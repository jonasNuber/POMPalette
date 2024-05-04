package org.nuberjonas.pompalette.application.javafx_application.controls;

import org.nuberjonas.pompalette.application.javafx_application.gui.loadproject.viewmodel.LoadProjectViewModel;
import org.nuberjonas.pompalette.application.javafx_application.gui.projectgraph.viewmodel.ProjectGraphViewModel;
import org.nuberjonas.pompalette.core.model.ModelFactory;

public final class ViewModelFactory {

    private ModelFactory modelFactory;
    private LoadProjectViewModel loadProjectViewModel;
    private ProjectGraphViewModel projectGraphViewModel;

    public ViewModelFactory(ModelFactory modelFactory) {
        this.modelFactory = modelFactory;
    }

    public LoadProjectViewModel getLoadProjectViewModel(){
        if(loadProjectViewModel == null){
            loadProjectViewModel = new LoadProjectViewModel();
        }
        return loadProjectViewModel;
    }

    public ProjectGraphViewModel getProjectGraphViewModel(){
        if(projectGraphViewModel == null){
            projectGraphViewModel = new ProjectGraphViewModel();
        }

        return projectGraphViewModel;
    }
}
