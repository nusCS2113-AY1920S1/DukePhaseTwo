package moomoo.command;

import moomoo.command.category.AddCategoryCommand;
import moomoo.command.category.AddExpenditureCommand;
import moomoo.command.category.DeleteCategoryCommand;
import moomoo.command.category.DeleteExpenditureCommand;
import moomoo.command.category.ListCategoryCommand;
import moomoo.command.category.SortCategoryCommand;
import moomoo.feature.Budget;
import moomoo.feature.MooMooException;
import moomoo.feature.Ui;
import moomoo.feature.category.Category;
import moomoo.feature.category.CategoryList;
import moomoo.feature.category.Expenditure;
import moomoo.feature.storage.CategoryStorage;
import moomoo.feature.storage.ExpenditureStorage;
import moomoo.feature.storage.Storage;
import moomoo.stubs.CategoryListStub;
import moomoo.stubs.ScheduleListStub;
import moomoo.stubs.StorageStub;
import moomoo.stubs.UiStub;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GraphTotalCommandTest {
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BLACK = "\u001B[30m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_WHITE = "\u001B[37m";
    
    private final String fullBlock = "\u2588"; //"H";
    private final String halfBlock = "\u258c"; //"l";
    private final String topBorder = "\u252c";//"v";
    private final String bottomBorder = "\u2534";//"^";
    private String horizontalAxisTop = "";
    private String horizontalAxisBottom = "";
    private String output = "";
    
    
    @Test
    void testGraphTotalCommandEmpty() throws MooMooException {
        ScheduleListStub newCalendar = new ScheduleListStub();
        Budget newBudget = new Budget();
        StorageStub newStorage = new StorageStub();
        CategoryListStub newCatList = new CategoryListStub();
        
        Command testGraph = new GraphTotalCommand();
        Throwable thrown = assertThrows(MooMooException.class, () -> {
            testGraph.execute(newCalendar, newBudget, newCatList, newStorage);
        });
        assertEquals("OOPS!!! MooMoo cannot find any category data :(", thrown.getMessage());
    }
    
    @Test
    void testGraphTotalCommand() throws MooMooException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        LocalDate date = LocalDate.parse("25/10/2019", formatter);
        
        CategoryList newCatList = new CategoryList();
        Category shoes = new Category("shoes");
        newCatList.add(shoes);
        Category food = new Category("food");
        newCatList.add(food);
        Category transportation = new Category("transportation");
        newCatList.add(transportation);
        
        Budget newBudget = new Budget();
        
        ScheduleListStub newCalendar = new ScheduleListStub();
        StorageStub newStorage = new StorageStub();
        Command testGraph = new GraphTotalCommand();
        testGraph.execute(newCalendar, newBudget, newCatList, newStorage);
        assertEquals("              " + ANSI_YELLOW + "" + ANSI_RESET + "\n"
                + ANSI_CYAN + "shoes" + "            " + "0.0%\n" + ANSI_RESET
                + "food" + "             " + "0.0%\n"
                + ANSI_CYAN + "transportation" + "   " + "0.0%\n" + ANSI_RESET
                + "              " + ANSI_YELLOW + "" + ANSI_RESET + "\n", Ui.getOutput());
    }
    
    
}
