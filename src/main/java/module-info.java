module app.dryden.sudoku {
    requires javafx.controls;
    requires javafx.fxml;


    opens app.dryden.sudoku to javafx.fxml;
    exports app.dryden.sudoku;
}