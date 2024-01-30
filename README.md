Usually when a transaction comes to a System, we tend lock the balance table to update the balance and let some other transaction update the record with new balance.
The Issue with this approach is that no matter what you do the transaction will always be synchronous. There are ways to mitigate it 
•	let the account become suspense account and make balance offline.
•	let the bundled transaction come through a third party which has a primary job to club the transaction and post a single transaction.
•	use Caches to improve the performance of each transaction

They all have their side effects like 
•	some will not make balance real time 
•	For some transaction coming always from third party is not possible
•	Cache is indirectly making things faster but has the same problem of synchronization 

Balance Issue:
So, I tried to make some slight changes in the way things are 
•	Ideally, we need debit transaction to be validated for balance availability. I am credit needs not to check if balance becomes -ve post the transaction 
•	So let’s make all credit transactions offline. So, there is a table to check to store the credit transaction for all accounts 
•	the debit transaction will follow the same route. One transaction will update the record at a time. SO we will have one record per account. 
•	This will be debit table or Online table
•	So, if two transactions come one is debit and one is credit together. The credit will go to offline table and debit will go to online table. No locks
•	If 4 transactions come together for same account. 2 debit and 2 credit. Credit will insert 2 records in offline table, SO no Locks. Debit will go one by one in online table, So there is a lock and wait time till first debit transaction completes.
•	Now to check the balance you will have to create a view that will sum all the balance of offline table for an account - balance in online table.   

Using this approach, we can solve 50% of the problem. At least credit will never block debit transactions.

Another issue that I have found is that Banks needs to update multiple system like MIS, Notification, etc
All these are time consuming process unless they need to be real time/online. So here we can utilize Kafka a transaction has come, push it to Kafka, now there should be multiple consumers groups polling on the topic (there is a difference b/w consumer and consumer group). SO, the same message can be consumed by MIS, Notification etc. This way we can make things asynchronous and we don’t have to supply same details to multiple system. This won’t be the job of accounting service, Its job is to process the transaction and send a single notification with all details. Let the downstream systems consume the way they want message to be consumed.
The same applies to Accounts. Once the balance is updated, we can send out notification to Kafka, this way Account statement systems, audit system, MIS can get updated. 
We do not have to add EOD process, program or make transaction processing slower. 
The purpose will be update the balance/post accounting and send notification and let downstream system work on their work using Kafka  Messages

Service Details 
•	Exchange Rate Service: This will supply the exchange rate
•	Casa Service: This will update the balance. Credit offline and Debit online. Once done it will send the Notification via Kafka Service
•	Accounting Service: This will receive transaction, Update the exchange rate. Then call casa Service. Once all Done it will push the Notification via Kafka Service
•	Kafka Service: This will connect to topic and push the message


