package livelocks;

public class SharedResource {

    private Worker owner;

    public SharedResource(Worker owner) {
        this.owner = owner;
    }

    public Worker getOwner() {
        return owner;
    }

    /*
    * No need to make getter a synchronized method
    * As we are just fetching data not changing it */
    public synchronized void setOwner(Worker owner) {
        this.owner = owner;
    }
}
