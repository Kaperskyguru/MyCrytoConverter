package com.multimega.kaperskyguru.crytoconverter;

public class allCurrencies {

    private String Currency;

    private final double Btc;

    private final double Etherum;

    public allCurrencies(String Currency, double Btc, double Etherum) {
        this.Currency = Currency;
        this.Btc = Btc;
        this.Etherum = Etherum;
    }

    public double getBtc() {
        return Btc;
    }

    public double getEtherum() {
        return Etherum;
    }

    public String getCurrency() {
        return Currency;
    }

}
