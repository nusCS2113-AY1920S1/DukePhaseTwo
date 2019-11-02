//@@author jessteoxizhi
package gazeeebo.commands.tasks;
import gazeeebo.storage.Storage;
import gazeeebo.tasks.Task;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

public class UndoTaskCommand {
    /**
     * Undo previous task command
     *
     * @param commandStack
     * @param list
     * @param storage
     * @return Previous Arraylist of Task before command executed
     * @throws IOException
     */
    static ArrayList<Task> undo(Stack<ArrayList<Task>> commandStack, ArrayList<Task> list, Storage storage) throws IOException {
        if (!commandStack.empty()) {
            list = commandStack.peek();
            System.out.println("You have undo the previous command.");
            commandStack.pop();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < list.size(); i++) {
                sb.append(list.get(i).toString() + "\n");
            }
            storage.writeToSaveFile(sb.toString());
        } else {
            System.out.println("You cannot undo the previous command.");
        }
        return list;
    }
}