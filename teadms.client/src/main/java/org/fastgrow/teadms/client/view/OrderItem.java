package org.fastgrow.teadms.client.view;

import org.fastgrow.teadms.api.model.OrderItemDto;

public class OrderItem {
    public OrderItemDto data;
    private boolean invalidAmountString;

    public OrderItem(OrderItemDto data) {
        this.data = data;
    }

    public void parseAmount(String amount) {
        try {
            int value = Integer.parseInt(amount);
            if (value < 1) {
                invalidAmountString = true;
                return;
            }
            data.amount = Integer.parseInt(amount);
            invalidAmountString = false;
        } catch (NumberFormatException e) {
            invalidAmountString = true;
        }
    }

    public boolean isInvalidAmountString() {
        return invalidAmountString;
    }

    public void more() {
        ++data.amount;
        invalidAmountString = false;
    }

    public void less() {
        if (data.amount > 1) {
            --data.amount;
            invalidAmountString = false;
        }
    }
}
