package com.stripe.android.util;

import android.support.annotation.NonNull;

import com.stripe.android.model.BankAccount;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Utility class for static functions useful for networking and data transfer. You probably will
 * not need to call functions from this class in your code.
 */
public class StripeNetworkUtils {

    /**
     * A utility function to map the fields of a {@link Card} object into a {@link Map} we
     * can use in network communications.
     *
     * @param card the {@link Card} to be read
     * @return a {@link Map} containing the appropriate values read from the card
     */
    @NonNull
    public static Map<String, Object> hashMapFromCard(Card card) {
        Map<String, Object> tokenParams = new HashMap<>();

        Map<String, Object> cardParams = new HashMap<>();
        cardParams.put("number", StripeTextUtils.nullIfBlank(card.getNumber()));
        cardParams.put("cvc", StripeTextUtils.nullIfBlank(card.getCVC()));
        cardParams.put("exp_month", card.getExpMonth());
        cardParams.put("exp_year", card.getExpYear());
        cardParams.put("name", StripeTextUtils.nullIfBlank(card.getName()));
        cardParams.put("currency", StripeTextUtils.nullIfBlank(card.getCurrency()));
        cardParams.put("address_line1", StripeTextUtils.nullIfBlank(card.getAddressLine1()));
        cardParams.put("address_line2", StripeTextUtils.nullIfBlank(card.getAddressLine2()));
        cardParams.put("address_city", StripeTextUtils.nullIfBlank(card.getAddressCity()));
        cardParams.put("address_zip", StripeTextUtils.nullIfBlank(card.getAddressZip()));
        cardParams.put("address_state", StripeTextUtils.nullIfBlank(card.getAddressState()));
        cardParams.put("address_country", StripeTextUtils.nullIfBlank(card.getAddressCountry()));

        // Remove all null values; they cause validation errors
        removeNullParams(cardParams);

        tokenParams.put(Token.TYPE_CARD, cardParams);
        return tokenParams;
    }

    @NonNull
    public static Map<String, Object> hashMapFromBankAccount(@NonNull BankAccount bankAccount) {
        Map<String, Object> tokenParams = new HashMap<>();
        Map<String, Object> accountParams = new HashMap<>();

        accountParams.put("country", bankAccount.getCountryCode());
        accountParams.put("currency", bankAccount.getCurrency());
        accountParams.put("account_number", bankAccount.getAccountNumber());
        accountParams.put("routing_number",
                StripeTextUtils.nullIfBlank(bankAccount.getRoutingNumber()));
        accountParams.put("account_holder_name",
                StripeTextUtils.nullIfBlank(bankAccount.getAccountHolderName()));
        accountParams.put("account_holder_type",
                StripeTextUtils.nullIfBlank(bankAccount.getAccountHolderType()));

        // Remove all null values; they cause validation errors
        removeNullParams(accountParams);

        tokenParams.put(Token.TYPE_BANK_ACCOUNT, accountParams);
        return tokenParams;
    }

    static void removeNullParams(Map<String, Object> mapToEdit) {
        // Remove all null values; they cause validation errors
        for (String key : new HashSet<>(mapToEdit.keySet())) {
            if (mapToEdit.get(key) == null) {
                mapToEdit.remove(key);
            }
        }
    }

}
