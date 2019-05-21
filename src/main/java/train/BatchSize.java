package train;

public enum BatchSize {
    TRAINING(500),ANALYZING(100);

    int value;

    BatchSize(int value) {
        this.value = value;
    }
}
