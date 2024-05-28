module org.nuberjonas.pompalette.core.corebaseimpl {
    requires org.nuberjonas.pompalette.core.coreapi;
    requires org.nuberjonas.pompalette.core.corevalidation;

    requires org.slf4j;
    requires ch.qos.logback.core;
    requires ch.qos.logback.classic;

    exports org.nuberjonas.pompalette.core.corebaseimpl.graph;
}