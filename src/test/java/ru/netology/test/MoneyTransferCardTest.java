package ru.netology.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;


import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.data.DataHelper.getFirstCardInfo;
import static ru.netology.data.DataHelper.getSecondCardInfo;


public class MoneyTransferCardTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    DashboardPage login() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        return verificationPage.validVerify(verificationCode);
    }

    @Test
    void shouldTransferFromTheFirstCardToTheSecond() {
        DashboardPage dashboardPage = login();
        var firstCard = getFirstCardInfo();
        var secondCard = getSecondCardInfo();
        int sum = 5000;
        var balanceForFirstCard = dashboardPage.getCardBalance(firstCard) - sum;
        var balanceForSecondCard = dashboardPage.getCardBalance(secondCard) + sum;
        var moneytransferCard = dashboardPage.chooseCardForTransfer(secondCard);
        dashboardPage = moneytransferCard.MoneyTransfer(Integer.parseInt(String.valueOf(sum)), firstCard);
        var actualBalanceForFirstCard = dashboardPage.getCardBalance(firstCard);
        var actualBalanceForSecondCard = dashboardPage.getCardBalance(secondCard);
        assertEquals(balanceForFirstCard, actualBalanceForFirstCard);
        assertEquals(balanceForSecondCard, actualBalanceForSecondCard);

    }


    @Test
    void shouldTransferFromTheSecondCardToTheFirst() {
        DashboardPage dashboardPage = login();
        var firstCard = getFirstCardInfo();
        var secondCard = getSecondCardInfo();
        int sum = 15000;
        var balanceForFirstCard = dashboardPage.getCardBalance(secondCard) - sum;
        var balanceForSecondCard = dashboardPage.getCardBalance(firstCard) + sum;
        var moneytransferForCard = dashboardPage.chooseCardForTransfer(firstCard);
        dashboardPage = moneytransferForCard.MoneyTransfer(Integer.parseInt(String.valueOf(sum)), secondCard);
        var actualBalanceForFirstCard = dashboardPage.getCardBalance(secondCard);
        var actualBalanceForSecondCard = dashboardPage.getCardBalance(firstCard);
        assertEquals(balanceForFirstCard, actualBalanceForFirstCard);
        assertEquals(balanceForSecondCard, actualBalanceForSecondCard);

    }

    @Test
    void shouldTransferFromTheSecondCardToTheFirstOverLimit() {
        DashboardPage dashboardPage = login();
        var firstCard = getFirstCardInfo();
        var secondCard = getSecondCardInfo();
        int sum = 500;
        var balanceForFirstCard = dashboardPage.getCardBalance(secondCard) - sum;
        var balanceForSecondCard = dashboardPage.getCardBalance(firstCard) + sum;
        var moneytransferForCard = dashboardPage.chooseCardForTransfer(firstCard);
        dashboardPage = moneytransferForCard.MoneyTransfer(Integer.parseInt(String.valueOf(sum)), secondCard);
        var actualBalanceForFirstCard = dashboardPage.getCardBalance(secondCard);
        var actualBalanceForSecondCard = dashboardPage.getCardBalance(firstCard);
        assertEquals(balanceForFirstCard, actualBalanceForFirstCard);
        assertEquals(balanceForSecondCard, actualBalanceForSecondCard);

    }

}

