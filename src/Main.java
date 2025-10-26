
public class Main {
    public static void main(String[] args) {

//        client c= new client(new fastTalkable(),new slowWalkable());
//        c.walk();
//        c.talk();
//        Singelton obj1=Singelton.getInstance();
//        Singelton obj2=Singelton.getInstance();
//        System.out.println(obj1 == obj2);
//        Client c= new Client();
//        c.execute();
//clients c= new clients(new Adapter());
//c.convert("sumit");
//        Remote r1=new Remote(new LightCommand());
//        Remote r2=new Remote(new FanCommand());
//        r1.excute();
//        r1.undo();
//        r2.excute();
//        r2.undo();
//        Base tea = new Tea();
//        System.out.println("Plain Tea cost: " + tea.prepare());
//
//        Base milkTea = new Milk(tea);
//        System.out.println("Milk Tea cost: " + milkTea.prepare());
//
//        Base sweetMilkTea = new Sugar(new Milk(new Tea()));
//        System.out.println("Sweet Milk Tea cost: " + sweetMilkTea.prepare());



//
//                Channel techChannel = new Channel("TechWithSumit");
//
//                Observer o1 = new SMS("Amit");
//                Observer o2 = new WhatsApp("Priya");
//
//                techChannel.addSubscriber(o1);
//                techChannel.addSubscriber(o2);
//
//                techChannel.upload("Observer Pattern Explained");

//        IBurger burger1 = BurgerFactory.createBurger("simple");
//        burger1.make();
//
//        IBurger burger2 = BurgerFactory.createBurger("classic");
//        burger2.make();




                // Setup restaurants and menus
                RestaurantManager rm = new RestaurantManager();

                Restaurant r1 = new Restaurant("SpiceHouse");
                r1.addMenuItem(new MenuItem("Paneer Butter Masala", 220));
                r1.addMenuItem(new MenuItem("Garlic Naan", 40));
                rm.createRestaurant(r1);

                Restaurant r2 = new Restaurant("BurgerTown");
                r2.addMenuItem(new MenuItem("Classic Burger", 180));
                r2.addMenuItem(new MenuItem("Fries", 60));
                rm.createRestaurant(r2);

                // Create user
                User user = new User("Sumit", "Noida");

                // Create cart for a restaurant
                Cart cart = new Cart(r1, user);
                cart.addItem(r1.getMenu().get(0)); // Paneer
                cart.addItem(r1.getMenu().get(1)); // Naan

                System.out.println("Cart total: " + cart.totalPrice());

                // Choose payment method
                Payment payment = new CreditCardPayment("4111-xxxx-xxxx-1111");

                // Create order via factory (Delivery or Pickup)
                OrderFactory factory = new DeliveryOrderFactory(); // choose factory
                Order order = factory.createOrder(cart, payment);

                // Place order
                order.placeOrder();

                // Print order details / status
                System.out.println(order);

                // Another example: pickup order at BurgerTown
                Cart cart2 = new Cart(r2, user);
                cart2.addItem(r2.getMenu().get(0));
                cart2.addItem(r2.getMenu().get(1));

                Payment nb = new NetBankingPayment("sumit@bank");
                Order pickup = new PickupOrderFactory().createOrder(cart2, nb);
                pickup.placeOrder();
                System.out.println(pickup);
            }
        }
