package train;

public enum BatchSize {
    TRAINING(500),ANALYZING(100);

    int size;

    BatchSize(int size) {
        this.size = size;
    }
}
