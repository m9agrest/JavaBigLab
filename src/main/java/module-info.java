module ru.mina987.biglab {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.slf4j;

    opens ru.mina987.biglab to javafx.fxml;
    exports ru.mina987.biglab;
    exports ru.mina987.biglab.controllers;
    opens ru.mina987.biglab.controllers to javafx.fxml;
}