package geneticalgorithm;

public class Target<T> {

    private final T target;

    public Target(T target) {
        this.target = target;
    }

    public T getTarget() {
        return target;
    }

    public Class<?> getType(){
        return this.target.getClass();
    }
}
