package org.javabip.verification.visitors.test;

import org.javabip.annotations.Guard;

class FakeSpecification {
    Integer bet;
    Integer operator;
    int pot;
    String secretNumber;
    Boolean win;
    Double[] values;

    public FakeSpecification(Integer bet, Integer operator, int pot, String secretNumber, Boolean win, Double[] values) {
        this.bet = bet;
        this.operator = operator;
        this.pot = pot;
        this.secretNumber = secretNumber;
        this.win = win;
        this.values = values;
    }

    public Integer getBet() {
        return bet;
    }

    public void setBet(Integer bet) {
        this.bet = bet;
    }

    public Integer getOperator() {
        return operator;
    }

    public void setOperator(Integer operator) {
        this.operator = operator;
    }

    public int getPot() {
        return pot;
    }

    public void setPot(int pot) {
        this.pot = pot;
    }

    public String getSecretNumber() {
        return secretNumber;
    }

    public void setSecretNumber(String secretNumber) {
        this.secretNumber = secretNumber;
    }

    @Guard(name = "WIN")
    public Boolean getWin() {
        return win;
    }

    public void setWin(Boolean win) {
        this.win = win;
    }
}