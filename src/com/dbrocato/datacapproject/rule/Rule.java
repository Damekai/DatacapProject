package com.dbrocato.datacapproject.rule;

import com.dbrocato.datacapproject.transaction.Transaction;

/**
 * A Rule defines an operation that can be performed using a transaction to generate a value, so long as that
 * transaction is appropriate for the Rule to be applied to.
 */
public class Rule
{
    protected final RuleValidator ruleValidator;
    protected final RuleApplier ruleApplier;

    public Rule(RuleValidator ruleValidator, RuleApplier ruleApplier)
    {
        this.ruleValidator = ruleValidator;
        this.ruleApplier = ruleApplier;
    }

    public boolean isRuleApplicable(Transaction transaction)
    {
        return ruleValidator.isRuleApplicable(transaction);
    }

    public float applyRule(Transaction transaction)
    {
        return ruleApplier.applyRule(transaction);
    }
}
