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
        var moneyTransferCard = dashboardPage.chooseCardForTransfer(secondCard);
        dashboardPage = moneyTransferCard.MoneyTransfer(Integer.parseInt(String.valueOf(sum)), firstCard);
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
        var moneyTransferForCard = dashboardPage.chooseCardForTransfer(firstCard);
        dashboardPage = moneyTransferForCard.MoneyTransfer(Integer.parseInt(String.valueOf(sum)), secondCard);
        var actualBalanceForFirstCard = dashboardPage.getCardBalance(secondCard);
        var actualBalanceForSecondCard = dashboardPage.getCardBalance(firstCard);
        assertEquals(balanceForFirstCard, actualBalanceForFirstCard);
        assertEquals(balanceForSecondCard, actualBalanceForSecondCard);

    }
    @Test
    void shouldTransferFromTheFirstCardToTheFirstCar() {
        DashboardPage dashboardPage = login();
        var firstCard = getFirstCardInfo();
        int sum = 500;
        var balanceForFirstCard = dashboardPage.getCardBalance(firstCard) - sum + sum;
        var moneyTransferCard = dashboardPage.chooseCardForTransfer(firstCard);
        dashboardPage = moneyTransferCard.MoneyTransfer(Integer.parseInt(String.valueOf(sum)), firstCard);
        var actualBalanceForFirstCard = dashboardPage.getCardBalance(firstCard);
        assertEquals(balanceForFirstCard, actualBalanceForFirstCard);

    }
    @Test
    void shouldTransferZeroAmount() {
        DashboardPage dashboardPage = login();
        var firstCard = getFirstCardInfo();
        var secondCard = getSecondCardInfo();
        int sum = 0;
        var balanceForFirstCard = dashboardPage.getCardBalance(secondCard) - sum;
        var balanceForSecondCard = dashboardPage.getCardBalance(firstCard) + sum;
        var moneyTransferForCard = dashboardPage.chooseCardForTransfer(firstCard);
        dashboardPage = moneyTransferForCard.MoneyTransfer(Integer.parseInt(String.valueOf(sum)), secondCard);
        var actualBalanceForFirstCard = dashboardPage.getCardBalance(secondCard);
        var actualBalanceForSecondCard = dashboardPage.getCardBalance(firstCard);
        assertEquals(balanceForFirstCard, actualBalanceForFirstCard);
        assertEquals(balanceForSecondCard, actualBalanceForSecondCard);

    }

}

