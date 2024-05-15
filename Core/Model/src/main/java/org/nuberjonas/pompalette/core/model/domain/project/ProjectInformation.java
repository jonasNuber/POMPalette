package org.nuberjonas.pompalette.core.model.domain.project;

import java.util.List;

public record ProjectInformation(String packaging, String description, String url, List<String> modules) { }
