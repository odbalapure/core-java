package livelocks;

public class Worker {

    private String name;
    private boolean active;

    public Worker(String name, boolean active) {
        this.name = name;
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public boolean isActive() {
        return active;
    }

    public synchronized void work(SharedResource sharedResource, Worker otherWorker) {

        while(active) {
            // check if resource is owned or not
            if(sharedResource.getOwner() != this) {
                try {
                    wait(10);
                } catch(InterruptedException e) {

                }
                continue;
            }

            // if other worker is active hand over the shared resource to that worker
            if(otherWorker.isActive()) {
                System.out.println(getName() + " : give the resource to the worker " + otherWorker.getName());
                sharedResource.setOwner(otherWorker);
                continue;
            }

            System.out.println(getName() + ": working on the common resource");
            active = false;
            sharedResource.setOwner(otherWorker);
        }
    }

}
