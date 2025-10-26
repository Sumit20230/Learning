interface IBurger {
    void make();
}

// Concrete Products
class SimpleBurger implements IBurger {
    public void make() {
        System.out.println("Simple Burger has been created");
    }
}

class ClassicBurger implements IBurger {
    public void make() {
        System.out.println("Classic Burger has been created");
    }
}

// Simple Factory
class BurgerFactory {
    public static IBurger createBurger(String type) {
        if (type.equalsIgnoreCase("simple")) {
            return new SimpleBurger();
        } else if (type.equalsIgnoreCase("classic")) {
            return new ClassicBurger();
        } else {
            throw new IllegalArgumentException("Unknown burger type");
        }
    }
}

// Client


