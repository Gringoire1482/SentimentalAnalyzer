package train;

public enum BatchSize {
    TRAINING(2000),ANALYZING(100);

    int value;

    BatchSize(int value) {
        this.value = value;
    }
}
