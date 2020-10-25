package com.dreamteam.database;

public enum Options {
    CREATE(1), READ(2), UPDATE(3), DELETE(4), PROCESS_ORDERS(5), REPORTS(6), QUIT(7),        // menu options
    QUANTITY(8),CAPACITY(9),WHOLESALE_COST(10),SALE_PRICE(11),SUPPLIER(12),DONE(13);    // update options

    private int value;

    private Options(int v) {
        this.value = v;
    }

    public int getValue() {
        return value;
    }

}