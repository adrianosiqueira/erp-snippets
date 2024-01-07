module erp.snippets {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires org.slf4j;

    exports erp.snippets.dialog.credential;
    opens erp.snippets.dialog.credential;
}
