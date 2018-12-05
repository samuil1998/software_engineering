public interface Subscriber {

    void receive(String message);

    boolean ask();
}
