module org.example.binarytree {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.binarytree to javafx.fxml;
    exports org.example.binarytree;
}