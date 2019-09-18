# User Guide
![alt text](https://github.com/AY1920S1-CS2113T-T09-2/main/blob/master/docs/images/Ui.PNG)

Duchess is a calendar application tailor-made for NUS students. It provides an interface for students to manage both their school timetable and commitments schedule while providing useful contextual information and features.

## Features 

### Add different task types to todo list
Students can manage their own todo list and add tasks to it. They can tag the tasks with three different types: todo, event or deadline. The status of every tasks will be tracked as done or undone. New tasks will be set to an undone status, which can be changed to done.

### Organise and retrieve task list
Students will be able to:
* view the task list
* view the schedule for a paticular date
* search if a task exists
* delete tasks
* mark a task as done
* get reminders for tasks with deadlines

## Usage

### `todo <task>` - Add a todo task

Adds a todo task to the task list. The todo task is initially marked as undone.

Example of usage: 

`todo go buy running shoes`

Expected outcome:

```
Got it. I've added this task:
  [T][✘] go buy running shoes
Now you have 1 task in the list.
```

### `event <event> /at <start datetime> to <end datetime> ` - Add an event

Adds an event to the task list. The event is initially marked as undone.

Example of usage: 

`event student life fair /at 10/9/2019 1400 to 10/9/2019 1750`

Expected outcome:

```
Got it. I've added this task:
  [E][✘] student life fair (at: 10/09/2019 1400 to 10/09/2019 1750)
Now you have 1 task in your list.
```

### `deadline <task> /by <deadline>` - Add a deadline

Adds a deadline to the task list. The deadline is initially marked as undone.

Example of usage: 

`deadline geh1049 essay /by 20/9/2019 1800`

Expected outcome:

```
Got it. I've added this task:
  [D][✘] geh1049 essay (by: 20/9/2019 1800)
Now you have 1 task in your list.
```

### `list` - Displays task list

Displays task list in numbered bullet point format.

Example of usage: 

`list`

Expected outcome:

```
Here are the tasks in your list:
1. [T][✘] go buy running shoes
2. [E][✘] student life fair (at: 10/09/2019 1400 to 10/09/2019 1750)
3. [D][✘] geh1049 essay (by: 20/9/2019 1800)
```

### `done <task number>` - Mark task as done

Marks a task in the task list as done.

Example of usage: 

`done 1`

Expected outcome:

```
Nice! I've marked this task as done:
  [T][✓] go buy running shoes
```

### `delete <task number>` - Remove task from task list

Removes task from task list.

Example of usage: 

`delete 1`

Expected outcome:

```
Noted. I've removed this task:
  [T][✓] go buy running shoes
Now you have 2 tasks in the list.
```

### `schedule <date>` - Display schedule for a paticular date

Displays the schedule for a specific day. Time and task details are printed in a timetable. Ongoing events beginning before or lasting past the date are indicated below the timetable.

Example of usage: 

`schedule 17/9/2019`

Expected outcome:

```
Here is your schedule:
-----------------------------------------------------
|                   17/9/2019                       |
-----------------------------------------------------
|   Time    |                  Task                 |
-----------------------------------------------------
|   1400    |       [E][Γ✘] student life fair       |
-----------------------------------------------------
Here are your ongoing tasks:
1. [E][✘] health and wellness forum (at: 14/09/2019 1500 to 18/09/2019 1200)
```

### `reminder` - Display all tasks with deadlines

Display all tasks with deadlines.

Example of usage: 

`reminder`

Expected outcome:

```
You currently have these deadlines.
1. [D][✘] health and wellness transcript (by: 19/09/2019 0800)
```