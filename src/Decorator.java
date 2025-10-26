interface Base {
    int prepare();
}

// Concrete Component
class Tea implements Base {
    @Override
    public int prepare() {
        return 20; // base cost
    }
}

// Abstract Decorator
abstract class Extra implements Base {
    protected Base base;

    public Extra(Base base) {
        this.base = base;
    }

    public abstract int prepare();
}

// Concrete Decorator 1
class Milk extends Extra {
    public Milk(Base base) {
        super(base);
    }

    @Override
    public int prepare() {
        return base.prepare() + 10; // adds milk cost
    }
}

// Concrete Decorator 2
class Sugar extends Extra {
    public Sugar(Base base) {
        super(base);
    }

    @Override
    public int prepare() {
        return base.prepare() + 5; // adds sugar cost
    }
}


