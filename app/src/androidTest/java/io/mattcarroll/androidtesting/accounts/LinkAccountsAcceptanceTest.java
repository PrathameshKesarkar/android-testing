package io.mattcarroll.androidtesting.accounts;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import io.mattcarroll.androidtesting.IntentServiceIdlingResource;
import io.mattcarroll.androidtesting.SplashActivity;
import io.mattcarroll.androidtesting.overview.TransactionListItemViewModel;
import io.mattcarroll.androidtesting.usersession.UserSession;

import static android.support.test.espresso.Espresso.registerIdlingResources;
import static android.support.test.espresso.Espresso.unregisterIdlingResources;
import static junit.framework.Assert.fail;

public class LinkAccountsAcceptanceTest {
    private static final String INTENT_SERVICE_IDLING_RESOURCE_NAME =
            "IntentServiceIdlingResource_LinkAccountsAcceptanceTest";
    private static final String VALID_EMAIL = "me@email.com";
    private static final String VALID_PASSWORD = "password";

    private static final String BANK_NAME = "Acme";
    private static final String ACCOUNT_NUMBER = "0000111100001234";
    private static final String ACCOUNT_NUMBER_LAST_DIGITS = "1234";
    private static final String ACCOUNT_NAME = "Account 1";
    private static final String DISPLAY_NAME = "Acme Account 1";
    private static final String BALANCE = "$3.50";
    private static final String AMOUNT_SPENT_THIS_MONTH = "$2.00";

    private static final long TODAY = new Date().getTime();
    private static final long YEARS_AGO = 0;

    private static final String TRANSACTION_1_TITLE = "Test transaction 1";
    private static final int TRANSACTION_1_AMOUNT_IN_CENTS = -2_00;
    private static final String TRANSACTION_1_AMOUNT_AS_STRING = "-$2.00";
    private static final Transaction TRANSACTION_1 = new Transaction(TRANSACTION_1_TITLE, TRANSACTION_1_AMOUNT_IN_CENTS, TODAY);

    private static final String TRANSACTION_2_TITLE = "Test transaction 2";
    private static final int TRANSACTION_2_AMOUNT_IN_CENTS = -1_50;
    private static final String TRANSACTION_2_AMOUNT_AS_STRING = "-$1.50";
    private static final Transaction TRANSACTION_2 = new Transaction(TRANSACTION_2_TITLE, TRANSACTION_2_AMOUNT_IN_CENTS, YEARS_AGO);
    private static final List<Transaction> TEST_TRANSACTIONS = Arrays.asList(
            TRANSACTION_1, TRANSACTION_2
    );
    private static final BankAccount TEST_ACCOUNT =
            new BankAccount(BANK_NAME, ACCOUNT_NAME, ACCOUNT_NUMBER, TEST_TRANSACTIONS);

    @Rule
    public final ActivityTestRule<SplashActivity> splashActivityTestRule =
            new ActivityTestRule<>(SplashActivity.class);

    private Context appContext;
    private IntentServiceIdlingResource idlingResource;

    @Before
    public void setup() {
        appContext = InstrumentationRegistry.getTargetContext();
        idlingResource = new IntentServiceIdlingResource(appContext, INTENT_SERVICE_IDLING_RESOURCE_NAME);
        registerIdlingResources(idlingResource);
        if (AccountPersistenceService.accountsFile(appContext).exists()) {
            deleteAccounts();
        }
    }

    @After
    public void teardown() {
        unregisterIdlingResources(idlingResource);
        deleteAccounts();
        UserSession.getInstance().logout();
    }

    private void deleteAccounts() {
        //noinspection ResultOfMethodCallIgnored
        AccountPersistenceService.accountsFile(appContext).delete();
        AccountsApi.getInstance().removeAllAccounts();
    }

    /**
     * This is the end-to-end test you'll be fixing. You shouldn't need to modify this method.
     * Instead, focus on each of the failing helper methods.
     */
    @Test
    public void whenUserLinksAccountItAppearsInOverview() {
        injectFakeAccounts(TEST_ACCOUNT);

        login(VALID_EMAIL, VALID_PASSWORD);
        openManageAccountsScreen();
        linkAccount(BANK_NAME, ACCOUNT_NUMBER, VALID_PASSWORD);
        goBackToOverviewScreen();
        verifyAccountOverviewIsShown(DISPLAY_NAME, ACCOUNT_NUMBER_LAST_DIGITS, BALANCE, AMOUNT_SPENT_THIS_MONTH);
        verifyTransactionsAreShown(
                new ExpectedTransaction(TRANSACTION_1_TITLE, TRANSACTION_1_AMOUNT_AS_STRING),
                new ExpectedTransaction(TRANSACTION_2_TITLE, TRANSACTION_2_AMOUNT_AS_STRING)
        );
    }

    private void injectFakeAccounts(@NonNull BankAccount... accounts) {
        BankAccountsDatasource.getInstance().injectAccounts(accounts);
    }

    private void verifyTransactionsAreShown(@NonNull ExpectedTransaction... transactions) {
        for (ExpectedTransaction transaction : transactions) {
            verifyTransactionIsShown(transaction.description, transaction.amount);
        }
    }

    /***********************************************************************************************
     * DO NOT MODIFY ABOVE THIS LINE ***************************************************************
     ***********************************************************************************************/

    /**
     * Login Screen
     *    • LoginFragment.java
     *    • fragment_login.xml
     *
     *  Email                       R.id.edittext_email
     *  Password                    R.id.edittext_password
     *  Sign In button              R.id.button_sign_in
     *
     *
     * Accounts Overview
     *    • AccountsOverviewFragment.java
     *    • fragment_accounts_overview.xml
     *
     *  Floating Action Button      R.id.fab_manage_accounts
     *   (launches Manage Accounts)
     *  Accounts pager              R.id.recyclerview_accounts
     *  Transactions list view      R.id.listview_transactions
     *  Transactions adapter        TransactionListAdapter.java
     *
     *
     * Manage Accounts Screen
     *    • ManageAccountsActivity.java
     *    • activity_manage_accounts.xml
     *
     *  Link Account button         R.id.button_link_account
     *
     *
     * Link Account Screen
     *    • LinkAccountActivity.java
     *    • activity_link_account.xml
     *
     *  Bank Name                   R.id.edittext_bank_name
     *  Account Number              R.id.edittext_account_number
     *  Password                    R.id.edittext_password
     *  Link Account button         R.id.button_link_account
     *
     */

    private void login(@NonNull String email, @NonNull String password) {
        fail("TODO Complete login using the given credentials.");
    }

    private void openManageAccountsScreen() {
        fail("TODO Open Manage Accounts screen.");
    }

    private void linkAccount(@NonNull String bankName,
                             @NonNull String accountNumber,
                             @NonNull String validPassword) {
        fail("TODO Link account by submitting the given information.");
    }

    private void goBackToOverviewScreen() {
        fail("TODO Navigate back to the Overview screen.");
    }

    private void verifyAccountOverviewIsShown(@NonNull String displayName,
                                              @NonNull String accountNumberLastDigits,
                                              @NonNull String balance,
                                              @NonNull String amountSpentThisMonth) {
        fail("TODO Verify that the given information is shown in an item in the Accounts pager.");
        // Hint: http://lmgtfy.com/?q=RecyclerViewActions
    }

    private void verifyTransactionIsShown(@NonNull String description, @NonNull String amount) {
        fail("TODO Verify the given transaction info is shown in an item in the list view.");
    }

    /***********************************************************************************************
     * DO NOT MODIFY BELOW THIS LINE ***************************************************************
     ***********************************************************************************************/

    // Offers the ability to match a TransactionListItemViewModel based on the 'description' and 'amount' fields.
    // You shouldn't need to modify this method.
    private Matcher<? super Object> hasDescriptionAndAmount(@NonNull String description, @NonNull String amount) {
        return new TransactionListItemViewModelMatcher(description, amount);
    }

    private static class ExpectedTransaction {
        final String description;
        final String amount;

        ExpectedTransaction(@NonNull String description, @NonNull String amount) {
            this.description = description;
            this.amount = amount;
        }
    }

    /**
     * Custom matcher for matching TransactionListItemViewModels by description and amount.
     * You shouldn't need to modify this.
     */
    private static class TransactionListItemViewModelMatcher extends BoundedMatcher<Object, TransactionListItemViewModel> {
        private final String description;
        private final String amount;

        public TransactionListItemViewModelMatcher(@NonNull String description, @NonNull String amount) {
            super(TransactionListItemViewModel.class);
            this.description = description;
            this.amount = amount;
        }

        @Override
        protected boolean matchesSafely(TransactionListItemViewModel item) {
            return description.equals(item.description()) && amount.equals(item.amount());
        }

        @Override
        public void describeTo(Description description) {
            description.appendText("expected description '" + this.description + "'" +
                    " and amount '" + this.amount + "'");
        }
    }
}
