package moneypractice;

import java.util.Objects;

class Money {
    private final int dollar;
    private final int cents;
    private final Currency currency;

    // Constructor expects float since dollar cents implementation is unintuitive
    // and should be kept inside the class
    Money(Currency currency, float value) {
        this.currency = Objects.requireNonNull(currency, "Currency cannot be null");

        this.dollar = (int) value;
        this.cents = (int) ((value - dollar) * 100); // Truncate cents eg. 1.9999 becomes 1.99
    }

    @Override
    public String toString() {
        int absCents = Math.abs(cents);
        int absDollar = Math.abs(dollar);
        String centsString = absCents < 10 ? "0" + absCents : "" + absCents;

        // negative number
        if (dollar < 0 || cents < 0) {
            return String.format("%s -%d.%s", currency, absDollar, centsString);
        }

        return String.format("%s %d.%s", currency, absDollar, centsString);
    }

    // "You may not combine the two int fields into a single number to ease
    // computations."
    // meaning di pwede i turn into cents then divide by 100
    public Money add(Money other) {
        if (this.currency != other.currency) {
            throw new IllegalArgumentException("Cannot add different currencies");
        }

        int totalDollar = dollar + other.dollar;
        int totalCents = cents + other.cents;

        if (totalCents >= 100) {
            totalDollar += 1;
            totalCents -= 100;
        }

        return new Money(currency, totalDollar + (totalCents / 100f));
    }

    public Money subtract(Money other) {
        if (this.currency != other.currency) {
            throw new IllegalArgumentException("Cannot subtract different currencies");
        }

        int totalDollar = this.dollar - other.dollar;
        int totalCents = this.cents - other.cents;

        if (totalCents < 0) {
            totalDollar -= 1;
            totalCents += 100;
        }

        return new Money(this.currency, totalDollar + (totalCents / 100f));
    }

    // would this violate the you may not combine rule?
    // is this bad since it's dependent to add?
    // public Money subtract(Money other) {
    // if (this.currency != other.currency) {
    // throw new IllegalArgumentException("Cannot subtract different currencies");
    // }

    // return this.add(new Money(this.currency, -other.getDollar() - other.getCents()));
    // }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Money money = (Money) o;
        return dollar == money.dollar && cents == money.cents && currency == money.currency;
    }

    @Override
    public int hashCode() {
        return Objects.hash(currency, dollar, cents);
    }

}