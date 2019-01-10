package abused_master.abusedlib.event;

public class Event {

    private boolean isCanceled = false;

    public boolean canCancel() {
        return false;
    }

    public boolean isCanceled() {
        return isCanceled;
    }

    public void cancel() {
        if(!canCancel()) {
            throw new UnsupportedOperationException("Attempted to cancel a non cancelable event");
        }

        this.isCanceled = true;
    }
}
