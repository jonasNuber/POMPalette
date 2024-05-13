package org.nuberjonas.pompalette.application.javafx_application.controls;

import org.nuberjonas.pompalette.application.javafx_application.gui.dependencygraph.viewmodel.DependencyGraphViewModel;
import org.nuberjonas.pompalette.application.javafx_application.gui.loadproject.viewmodel.LoadProjectViewModel;
import org.nuberjonas.pompalette.application.javafx_application.gui.projectgraph.viewmodel.ProjectGraphViewModel;
import org.nuberjonas.pompalette.core.model.ModelFactory;
import org.nuberjonas.pompalette.core.model.application.ProjectGraphService;

public final class ViewModelFactory {

    private ModelFactory modelFactory;
    private LoadProjectViewModel loadProjectViewModel;
    private ProjectGraphViewModel projectGraphViewModel;
    private DependencyGraphViewModel dependencyGraphViewModel;

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
            projectGraphViewModel = new ProjectGraphViewModel(modelFactory.getProjectGraph(), new ProjectGraphService());
        }

        return projectGraphViewModel;
    }

    public DependencyGraphViewModel getDependencyGraphViewModel(){
        if(dependencyGraphViewModel == null){
            dependencyGraphViewModel = new DependencyGraphViewModel(modelFactory.getDependencyGraph());
        }

        return dependencyGraphViewModel;
    }
}
