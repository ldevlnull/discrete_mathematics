public class NoSuchNodeException extends RuntimeException {

    public NoSuchNodeException(double val) {
        super("Node with value "+val+" not found!");
    }
}
