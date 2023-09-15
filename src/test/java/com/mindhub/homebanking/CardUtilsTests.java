package com.mindhub.homebanking;


import com.mindhub.homebanking.utils.CardUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
public class CardUtilsTests {
    @Test
    public void cardNumberIsCreated(){
        String cardNumber = CardUtils.getCardNumber();
        assertThat(cardNumber,is(not(emptyOrNullString())));
    }
    @Test
    public void cardNumberLength(){
        String cardNumber = CardUtils.getCardNumber();
        assertThat(cardNumber.length(), is(equalTo(19)));
    }

    @Test
    public void cvvIsCreated(){
        int cvv = CardUtils.getCVV();
        assertThat(cvv,is(notNullValue()));
    }
    @Test
    public void cvvLength(){
        int cvv = CardUtils.getCVV();
        assertThat(cvv, lessThan(1000));
    }
}
