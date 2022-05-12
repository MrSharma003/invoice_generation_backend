package com.invoice.webservices;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
public class PaymentService {
    @Autowired
    PaymentService() {
        Stripe.apiKey = "sk_test_51KyWA6SElEIcYj8iKQgOSzGjSP6ySJFBMaWoSYXU5F2pAaUvFqjftpRV2UcgcDc7flQO7WmUGDOaXdcnOgaU1jPk00FqkZvrjC";
    }

    public Charge chargeNewCard(String token, double amount) throws StripeException {
        Map<String, Object> chargeParams = new HashMap<String, Object>();
        chargeParams.put("amount", (int)(amount * 100));
        chargeParams.put("currency", "USD");
        chargeParams.put("source", token);
        Charge charge = Charge.create(chargeParams);
        return charge;
    }
}
