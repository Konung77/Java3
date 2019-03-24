package lesson1;

import java.util.ArrayList;

public class Box<T extends Fruit> {
    private ArrayList<T> contains;

    public Box(ArrayList<T> contains) {
        this.contains = contains;
    }

    public void addFruit(T fruit) {
        contains.add(fruit);
    }

    public Float getWeight() {
        float sum = 0.0f;
        for (T fruit: contains) {
            sum += fruit.weight;
        }
        return sum;
    }

    public boolean compare(Box<?> box){
        return getWeight() == box.getWeight();
    }

    public void intersperse (Box<T> box) {
        for (T fruit: contains) {
            box.addFruit(fruit);
        }
        contains.clear();
    }
}
