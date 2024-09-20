module org.example.secondbindproject {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires opencsv;

    opens org.example.secondbindproject to javafx.fxml;
    exports org.example.secondbindproject;
}