package com.dbrocato.datacapproject.rule;

import com.dbrocato.datacapproject.transaction.Transaction;

import java.util.function.Function;

/**
 * Single-function interface (to support lambda usage) that validates a Rule by determining whether or not a given
 * transaction can have the associated Rule applied to it.
 */
public interface RuleValidator
{
    /* Common rule validators can be defined on the back-end as static final implementations anywhere. I've defined
    them here for convenience. */
    RuleValidator LESS_THAN_FIFTY_USD = (transaction) -> transaction.getAmount() < 50f;
    RuleValidator GREATER_THAN_OR_EQUAL_TO_FIFTY_USD = (transaction) -> transaction.getAmount() >= 50f;

    Boolean isRuleApplicable(Transaction transaction);
}
