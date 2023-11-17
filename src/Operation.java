enum Operation {
//    addition, subtraction, multiplication, division;
    addition("+"), subtraction("-"), multiplication("*"), division("/");

    private String operation;

    Operation(String operation) {
        this.operation = operation;
    }

    String getOperation() {
        return operation;
    }

}
