package com.dbrocato.datacapproject.rule;

import com.dbrocato.datacapproject.transaction.Transaction;

/**
 * Implements the Rule described repeatedly in the spec that adds a flat rate to a portion of the transaction amount
 * to determine the fee.
 */
public class StandardRuleApplier implements RuleApplier
{
    private final float flatRate;
    private final float shareOfTransactionAmount;

    public StandardRuleApplier(float flatRate, float shareOfTransactionAmount)
    {
        this.flatRate = flatRate;
        this.shareOfTransactionAmount = shareOfTransactionAmount;
    }

    @Override
    public Float applyRule(Transaction transaction)
    {
        return flatRate + transaction.getAmount() * shareOfTransactionAmount;
    }
}
