package com.dbrocato.datacapproject.transaction;

import com.dbrocato.datacapproject.paymentprocessor.PaymentProcessor;

/**
 * Simple object that contains data deserialized from the input files.
 */
public class Transaction
{
    private final TransactionType type;
    private final float amount;
    private final int referenceNumber;

    public Transaction(TransactionType type, float amount, int referenceNumber)
    {
        this.type = type;
        this.amount = amount;
        this.referenceNumber = referenceNumber;
    }

    public TransactionType getType()
    {
        return type;
    }

    public float getAmount()
    {
        return amount;
    }

    public int getReferenceNumber()
    {
        return referenceNumber;
    }

    @Override
    public String toString()
    {
        return "Type=" + (type == TransactionType.SALE ? "Sale" : "Void") + " Amount=" + PaymentProcessor.USD_DECIMAL_FORMAT.format(amount) + " Reference Number=" + referenceNumber;
    }
}
