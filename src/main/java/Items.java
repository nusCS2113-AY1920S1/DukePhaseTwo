public class Items {
    String name;
    int amount;

    Items(String name, int amount) {
        this.name = name;
        this.amount = amount;
    }

    @Override
    public String toString() { return (this.name + " : " + this.amount);}
}

