module com.example.brick_breaker_game {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.brick_breaker_game to javafx.fxml;
    exports com.example.brick_breaker_game;
}