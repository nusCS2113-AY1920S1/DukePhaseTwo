package duke.data;

import duke.commons.exceptions.DukeException;
import duke.commons.exceptions.DukeDuplicateTaskException;
import duke.commons.Messages;
import duke.commons.exceptions.DukeTaskNotFoundException;
import duke.data.tasks.Task;
import duke.data.tasks.TaskWithDates;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * A list of Tasks that enforces uniqueness between its elements.
 */
public class UniqueTaskList implements Iterable<Task> {
    private ObservableList<Task> internalList;

    public UniqueTaskList() {
        internalList = FXCollections.observableArrayList();
    }

    public Task get(int index) throws IndexOutOfBoundsException {
        return internalList.get(index);
    }

    public int size() {
        return internalList.size();
    }

    /**
     * Returns true if the list contains an equivalent Task as the given argument.
     */
    public boolean contains(Task toCheck) {
        return internalList.stream().anyMatch(toCheck::isSameTask);
    }

    /**
     * Adds a Task to the list.
     * The Task must not already exist in the list.
     */
    public void add(Task toAdd) throws DukeException {
        if (contains(toAdd)) {
            throw new DukeDuplicateTaskException();
        } else if (hasAnomaly(toAdd)) {
            throw new DukeException(Messages.ANOMALY_FOUND);
        }
        internalList.add(toAdd);
    }

    /**
     * Checks if task clashes with other tasks.
     */
    private boolean hasAnomaly(Task toAdd) {
        if (toAdd instanceof TaskWithDates) {
            LocalDateTime dateTime = ((TaskWithDates) toAdd).getStartDate();
            if (dateTime != null) {
                for (Task t : getChronoList()) {
                    if (((TaskWithDates) t).getStartDate().isEqual(dateTime)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Replaces the Task {@code target} in the list with {@code editedTask}.
     * {@code target} must exist in the list.
     * The Task identity of {@code editedTask} must not be the same as another existing Task in the list.
     */
    public void setTask(Task target, Task editedTask) throws DukeException {
        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new DukeTaskNotFoundException();
        }

        if (!target.isSameTask(editedTask) && contains(editedTask)) {
            throw new DukeDuplicateTaskException();
        }

        internalList.set(index, editedTask);
    }

    /**
     * Removes the equivalent Task from the list.
     * The Task must exist in the list.
     */
    public void remove(Task toRemove) throws DukeException {
        if (!internalList.remove(toRemove)) {
            throw new DukeTaskNotFoundException();
        }
    }

    public Task remove(int index) throws IndexOutOfBoundsException {
        return internalList.remove(index);
    }

    public void setTasks(UniqueTaskList replacement) {
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code Tasks}.
     * {@code Tasks} must not contain duplicate Tasks.
     */
    public void setTasks(List<Task> tasks) throws DukeException {
        if (!tasksAreUnique(tasks)) {
            throw new DukeDuplicateTaskException();
        }

        internalList.setAll(tasks);
    }

    @Override
    public Iterator<Task> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueTaskList // instanceof handles nulls
                && internalList.equals(((UniqueTaskList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code Tasks} contains only unique Tasks.
     */
    private boolean tasksAreUnique(List<Task> tasks) {
        for (int i = 0; i < tasks.size() - 1; i++) {
            for (int j = i + 1; j < tasks.size(); j++) {
                if (tasks.get(i).isSameTask(tasks.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    public FilteredList<Task> getFilteredList() {
        return new FilteredList<>(internalList, (Task t) -> (t instanceof TaskWithDates)
                && (((TaskWithDates) t).getStartDate() != null));
    }

    public SortedList<Task> getChronoList() {
        return new SortedList<Task>(getFilteredList(),
                Comparator.comparing((Task t) -> ((TaskWithDates) t).getStartDate()));
    }
}
