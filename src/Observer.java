import java.util.*;

// Observer interface
interface Observer {
    void notify(String video, String channelName);
}

// Concrete Observers
class SMS implements Observer {
    private String name;
    public SMS(String name) { this.name = name; }

    @Override
    public void notify(String video, String channelName) {
        System.out.println(name + " got SMS: New video '" + video + "' uploaded on " + channelName);
    }
}

class WhatsApp implements Observer {
    private String name;
    public WhatsApp(String name) { this.name = name; }

    @Override
    public void notify(String video, String channelName) {
        System.out.println(name + " got WhatsApp msg: New video '" + video + "' uploaded on " + channelName);
    }
}

// Observable interface
interface Observable {
    void addSubscriber(Observer o);
    void removeSubscriber(Observer o);
    void notifyAllSubscribers(String video);
}

// Concrete Observable    //vjvdfdgvvcgdcgv
class Channel implements Observable {
    private String name;
    private List<Observer> subs = new ArrayList<>();

    public Channel(String name) { this.name = name; }

    @Override
    public void addSubscriber(Observer o) {
        subs.add(o);
    }

    @Override
    public void removeSubscriber(Observer o) {
        subs.remove(o);
    }

    @Override
    public void notifyAllSubscribers(String video) {
        for (Observer o : subs) {
            o.notify(video, name);
        }
    }

    public void upload(String video) {
        System.out.println("\n[" + name + "] uploaded: " + video);
        notifyAllSubscribers(video);
    }
}


