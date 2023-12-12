create or replace view Current_Account_Balance as
  Select customer_account_balance.customer_Acc_no Account_no,  NVL(summed_balance.balance + customer_account_balance.account_balance,0) accountBalance
  from (
    select customer_Acc_no customer_Acc_no, Sum(Amount) balance
    from customer_account_offline_balance
    where Status = 'U'
    group by customer_Acc_no) summed_balance, customer_account_balance
  where  summed_balance.customer_Acc_no(+) = customer_account_balance.customer_Acc_no