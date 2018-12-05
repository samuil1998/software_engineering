import java.util.ArrayList;

public class Publisher {

    ArrayList<Subscriber> subs = new ArrayList<Subscriber>();

    public void addSubscriber(Subscriber subscriber)
    {
        subs.add(subscriber);
    }

    public void send(String message)
    {
        System.out.println(subs);
        for (Subscriber s : subs)
        {
            s.receive(message);
        }
    }

    public boolean ask(Subscriber s)
    {
        s.ask();
        return true;
    }
}
