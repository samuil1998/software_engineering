import com.trafficmon.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;


public class MockAccountsService implements AccountsService {

    private List<Account> accounts = new ArrayList<Account>();

    public Account accountFor(Vehicle vehicle) throws AccountNotRegisteredException
    {
        for (Account account : accounts)
        {
            if (account.getAssociatedVehicle().equals(vehicle))
            {
                return account;
            }
        }

        throw new AccountNotRegisteredException(vehicle);
    }

    public void addAccount(String name, Vehicle vehicle, BigDecimal balance)
    {
        Account account = new Account(name, vehicle, balance);
        accounts.add(account);
    }
}
