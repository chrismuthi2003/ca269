import java.time.Period;
import java.time.LocalDate;

class Task
{
    final String title;
    String description;
    LocalDate scheduled;
    LocalDate deadline;
    State state;

    Task(String title, State state)
    {
        this.title = title;
        this.state = state;
    }

    public String getTitle()
    {
        return title;
    }
    public void setDescription(String description)
    {
        this.description = description;
    }
    public void setScheduled(LocalDate scheduled)
    {
        this.scheduled = scheduled;
    }
    public void setDeadline(LocalDate deadline)
    {
        this.deadline = deadline;
    }
    public State getState()
    {
        return state;
    }
    public void setState(State state)
    {
        this.state = state;
    }

    public String toString()
    {
        String message = this.title + " (" + this.state + ")";
        if(scheduled != null)
        {
            message += " scheduled: " + scheduled;
        }
        if(deadline != null)
        {
            message += " deadline: " + deadline;
        }
        return message;
    }


    public static void main(String args[])
    {
        Task t1 = new Task("Test", State.TODO);
        LocalDate now = LocalDate.now();
        t1.setScheduled(now);
        System.out.println(t1);

        Task t2 = new Chore("Test", State.TODO, LocalDate.now(), LocalDate.now().plus(Period.ofDays(7)));
        System.out.println(t2);
        t2.setState(State.DONE);
        System.out.println(t2);

        Task t3 = new RepeatedTask("Test", State.TODO);
        System.out.println(t3);
        t3.setState(State.DONE);
        System.out.println(t3);

        Task t4 = new SharedTask("Test", "Alice");
        System.out.println(t4);

        Task t5 = new Dependency("Test", State.TODO, t1);
        System.out.println(t5);
        t5.setState(State.DONE);
        System.out.println(t5);
        t1.setState(State.DONE);
        t5.setState(State.DONE);
        System.out.println(t5);
    }
}

class Chore extends Task
{
    LocalDate repeat;

    public void setRepeat(LocalDate repeat)
    {
        this.repeat = repeat;
    }

       Chore(String title, State state, LocalDate scheduled, LocalDate repeat)
    {
        super(title, state);
        setScheduled(scheduled);
        setRepeat(repeat);
    }

    public void setState(State state)
    {
        super.setState(state);
        if(state == State.DONE)
        {
            LocalDate repeat_new = repeat.plus(Period.ofDays(7));
            setScheduled(repeat);
            setRepeat(repeat_new);
            this.state = State.TODO;
        }
    }
}

class RepeatedTask extends Task
{
    RepeatedTask(String title, State state)
    {
        super(title, state);
    }

    public void setState(State state)
    {
        if(state == State.DONE)
        {
            this.state = State.TODO;
        }
    }
}

class SharedTask extends Task
{
    String name;

    SharedTask(String title, String name)
    {
        super(title, State.WAIT);
        this.name = name;
    }

    public String toString()
    {
        return super.toString() + " shared with: " + name;
    }
}

class Dependency extends Task
{
    Task dependent;

    Dependency(String title, State state, Task dependent)
    {
        super(title, state);
        this.dependent = dependent;
    }

    public String toString()
    {
        return super.toString() + " dependent on: " + dependent.toString();
    }

    public void setState(State state)
    {
        if(state == State.DONE)
        {
            if(dependent.state != State.DONE)
            {
                return;
            }
        }
        this.state = state;
    }
}


enum State
{
    TODO, BEGN, HALT, WAIT, DONE;
}
