package duke.models.counter;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import duke.commands.Command;
import duke.commands.CommandManager;

import duke.exceptions.DukeException;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;


public class CounterTest {

    @Test
    public void runCounterLogic_CommandType_CommandTable() throws DukeException {

        Command c1 = CommandManager.manageCommand("add patient :name :NRIC :room :remark");
        Command c2 = CommandManager.manageCommand("add task :Walk the dog");
        Command c3 = CommandManager.manageCommand("assign deadline task :patient name :#2 :02/02/2002 2222");
        Command c4 = CommandManager.manageCommand("assign period task :#2 :#1 :01/02/2003 1234 :06/05/2004 2312");
        Command c5 = CommandManager.manageCommand("list patients");
        Command c6 = CommandManager.manageCommand("list tasks");
        Command c7 = CommandManager.manageCommand("delete assigned task :#2 :#5");
        Command c8 = CommandManager.manageCommand("delete patient :#123");
        Command c9 = CommandManager.manageCommand("delete task :#10");
        Command c10 = CommandManager.manageCommand("find patient :name");
        Command c11 = CommandManager.manageCommand("find task :Walk the dog");
        Command c13 = CommandManager.manageCommand("update patient :name :field :new data");
        Command c14 = CommandManager.manageCommand("update task :task name :new description");


        assertEquals(c1.getClass().getSimpleName(), "AddPatientCommand");
        assertEquals(c2.getClass().getSimpleName(), "AddTaskCommand");
        assertEquals(c3.getClass().getSimpleName(), "AssignDeadlineTaskCommand");
        assertEquals(c4.getClass().getSimpleName(), "AssignPeriodTaskCommand");
        assertEquals(c5.getClass().getSimpleName(), "ListPatientsCommand");
        assertEquals(c6.getClass().getSimpleName(), "ListTasksCommand");
        assertEquals(c7.getClass().getSimpleName(), "DeleteAssignedTaskCommand");
        assertEquals(c8.getClass().getSimpleName(), "DeletePatientCommand");
        assertEquals(c9.getClass().getSimpleName(), "DeleteTaskCommand");
        assertEquals(c10.getClass().getSimpleName(), "FindPatientCommand");
        assertEquals(c11.getClass().getSimpleName(), "FindTaskCommand");
        assertEquals(c13.getClass().getSimpleName(), "UpdatePatientCommand");
        assertEquals(c14.getClass().getSimpleName(), "UpdateTaskCommand");

        Map<String, Integer> commandTable = new HashMap<>();
        String[] commandName = {"AddPatientCommand", "AddTaskCommand", "AssignDeadlineTaskCommand",
                "AssignPeriodTaskCommand", "ListPatientsCommand", "ListTasksCommand",
                "DeleteAssignedTaskCommand", "DeletePatientCommand", "DeleteTaskCommand",
                "FindPatientCommand", "FindTaskCommand", "UpdatePatientCommand", "UpdateTaskCommand"};

        for (int i = 0; i < 13; i++) {
            int count = commandTable.containsKey(commandName[i])
                    ? commandTable.get(commandName[i]) : 0;
            commandTable.put(commandName[i], count + 1);
        }

        for (int value : commandTable.values()) {
            assertEquals(value, 1);
        }

        assertEquals(commandTable.size(), 13);


    }


}
