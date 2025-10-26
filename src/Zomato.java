import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Zomato-like simplified LLD console app in one file.
 * Save as ZomatoApp.java and run: javac ZomatoApp.java && java ZomatoApp


/* -------------------------
   Domain classes & managers
   ------------------------- */

class MenuItem {
    private static final AtomicInteger ID_GEN = new AtomicInteger(1);
    private final int id;
    private String name;
    private int price; // in rupees

    public MenuItem(String name, int price) {
        this.id = ID_GEN.getAndIncrement();
        this.name = Objects.requireNonNull(name);
        this.price = price;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getPrice() { return price; }

    public void setName(String name) { this.name = Objects.requireNonNull(name); }
    public void setPrice(int price) { this.price = price; }

    @Override
    public String toString() {
        return "MenuItem{" + id + ", '" + name + '\'' + ", ₹" + price + '}';
    }
}

class Restaurant {
    private final String name;
    private final List<MenuItem> menu = new ArrayList<>();

    public Restaurant(String name) {
        this.name = Objects.requireNonNull(name);
    }

    public String getName() { return name; }
    public List<MenuItem> getMenu() { return Collections.unmodifiableList(menu); }

    public void addMenuItem(MenuItem item) {
        this.menu.add(Objects.requireNonNull(item));
    }

    @Override
    public String toString() {
        return "Restaurant{" + name + ", menuSize=" + menu.size() + '}';
    }
}

class RestaurantManager {
    private final List<Restaurant> restaurants = new ArrayList<>();

    public void createRestaurant(Restaurant r) {
        restaurants.add(Objects.requireNonNull(r));
    }

    public Restaurant searchRestaurant(String name) {
        for (Restaurant r : restaurants) {
            if (r.getName().equalsIgnoreCase(name)) return r;
        }
        return null;
    }

    public List<Restaurant> listAll() {
        return Collections.unmodifiableList(restaurants);
    }
}

class User {
    private final String name;
    private String address;

    public User(String name, String address) {
        this.name = Objects.requireNonNull(name);
        this.address = Objects.requireNonNull(address);
    }

    public String getName() { return name; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = Objects.requireNonNull(address); }

    @Override
    public String toString() {
        return "User{" + name + ", " + address + '}';
    }
}

class Cart {
    private final Restaurant restaurant;
    private final User user;
    private final List<MenuItem> items = new ArrayList<>();

    public Cart(Restaurant restaurant, User user) {
        this.restaurant = Objects.requireNonNull(restaurant);
        this.user = Objects.requireNonNull(user);
    }

    public void addItem(MenuItem item) {
        // Basic validation: ensure item belongs to same restaurant menu list (optional)
        if (!restaurant.getMenu().contains(item)) {
            throw new IllegalArgumentException("Item does not belong to cart's restaurant menu");
        }
        items.add(item);
    }

    public List<MenuItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public int totalPrice() {
        int sum = 0;
        for (MenuItem m : items) sum += m.getPrice();
        return sum;
    }

    public Restaurant getRestaurant() { return restaurant; }
    public User getUser() { return user; }

    @Override
    public String toString() {
        return "Cart{" + user.getName() + "@" + restaurant.getName() + ", items=" + items.size() + '}';
    }
}

/* -------------------------
   Payment strategy
   ------------------------- */
interface Payment {
    boolean pay(int amount); // returns true if success
    String getPaymentDetails();
}

class CreditCardPayment implements Payment {
    private final String cardNumber;

    public CreditCardPayment(String cardNumber) {
        this.cardNumber = Objects.requireNonNull(cardNumber);
    }

    @Override
    public boolean pay(int amount) {
        // Simulate payment processing
        System.out.println("Processing credit card payment of ₹" + amount + " using " + cardNumber);
        return true;
    }

    @Override
    public String getPaymentDetails() {
        return "CreditCard(" + cardNumber + ")";
    }
}

class NetBankingPayment implements Payment {
    private final String accountId;

    public NetBankingPayment(String accountId) {
        this.accountId = Objects.requireNonNull(accountId);
    }

    @Override
    public boolean pay(int amount) {
        System.out.println("Processing netbanking payment of ₹" + amount + " using " + accountId);
        return true;
    }

    @Override
    public String getPaymentDetails() {
        return "NetBanking(" + accountId + ")";
    }
}

/* -------------------------
   Order + Factory
   ------------------------- */

enum OrderStatus {
    CREATED, PAID, DISPATCHED, COMPLETED, CANCELLED, FAILED
}

interface Order {
    void placeOrder(); // triggers payment and moves status
    int getId();
    OrderStatus getStatus();
}

abstract class AbstractOrder implements Order {
    private static final AtomicInteger ID_GEN = new AtomicInteger(1000);
    protected final int id;
    protected final Cart cart;
    protected final Payment payment;
    protected OrderStatus status = OrderStatus.CREATED;
    protected final Date createdAt = new Date();

    protected AbstractOrder(Cart cart, Payment payment) {
        this.id = ID_GEN.getAndIncrement();
        this.cart = Objects.requireNonNull(cart);
        this.payment = Objects.requireNonNull(payment);
    }

    @Override
    public int getId() { return id; }

    @Override
    public OrderStatus getStatus() { return status; }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", type=" + this.getClass().getSimpleName() +
                ", user=" + cart.getUser().getName() +
                ", restaurant=" + cart.getRestaurant().getName() +
                ", total=₹" + cart.totalPrice() +
                ", status=" + status +
                ", payment=" + payment.getPaymentDetails() +
                ", createdAt=" + createdAt +
                '}';
    }
}

class DeliveryOrder extends AbstractOrder {
    public DeliveryOrder(Cart cart, Payment payment) { super(cart, payment); }

    @Override
    public void placeOrder() {
        if (status != OrderStatus.CREATED) {
            System.out.println("Order " + id + " cannot be placed in status " + status);
            return;
        }
        System.out.println("Placing DeliveryOrder id=" + id);
        boolean ok = payment.pay(cart.totalPrice());
        status = ok ? OrderStatus.PAID : OrderStatus.FAILED;
        if (ok) {
            // simulate dispatch
            status = OrderStatus.DISPATCHED;
            System.out.println("Order " + id + " dispatched for delivery.");
        } else {
            System.out.println("Payment failed for order " + id);
        }
    }
}

class PickupOrder extends AbstractOrder {
    public PickupOrder(Cart cart, Payment payment) { super(cart, payment); }

    @Override
    public void placeOrder() {
        if (status != OrderStatus.CREATED) {
            System.out.println("Order " + id + " cannot be placed in status " + status);
            return;
        }
        System.out.println("Placing PickupOrder id=" + id);
        boolean ok = payment.pay(cart.totalPrice());
        status = ok ? OrderStatus.PAID : OrderStatus.FAILED;
        if (ok) {
            status = OrderStatus.COMPLETED; // pickup is immediate
            System.out.println("Order " + id + " ready for pickup.");
        } else {
            System.out.println("Payment failed for order " + id);
        }
    }
}

interface OrderFactory {
    Order createOrder(Cart cart, Payment payment);
}

class DeliveryOrderFactory implements OrderFactory {
    @Override
    public Order createOrder(Cart cart, Payment payment) {
        return new DeliveryOrder(cart, payment);
    }
}

class PickupOrderFactory implements OrderFactory {
    @Override
    public Order createOrder(Cart cart, Payment payment) {
        return new PickupOrder(cart, payment);
    }
}
