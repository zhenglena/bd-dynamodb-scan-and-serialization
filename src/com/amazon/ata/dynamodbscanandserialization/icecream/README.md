# Still in the ice cream business...

**branch name:** dynamodbscanandserialization-classroom

**AWS account:** (account number for AWS account that ATA gave you --
[find on Conduit](https://access.amazon.com/aws/accounts) )

**role:** IibsAdminAccess-DO-NOT-DELETE

**RDE workflows:**
- `dynamodbscanandserialization-classroom-phase0`
- `dynamodbscanandserialization-classroom-phase1`
- `dynamodbscanandserialization-classroom-phase2`
- `dynamodbscanandserialization-classroom-phase3`

## Introduction
We are returning to the `IceCreamParlorService` you have seen in many previous lessons.

Today we aren't interested in making ice cream or serving sundaes. We came to check in on the
business. 

Recently the ice cream parlor started generating and persisting customer receipts. Finally we
can check out the sales! A `Receipt` is persisted in DynamoDB, and has the following fields:

- customerId - string, partition key, the purchasing customer
- purchaseDate - string, sort key, the time and date the sundae(s) were ordered
- totalSales - number, the total amount paid for the sundae(s) in the order

The `IceCreamParlorAdminService` will contain the new administrative APIs and will depend
on the `ReceiptDao` that will provide access to `Receipt`s.

## Phase 0: Preliminaries

1. Make sure `ada` is running with the credentials specified at the top of this README
(should be your AWS account that ATA gave you).
1. Create the table we'll be using for this activity by running this aws CLI command:
   ```none
   aws cloudformation create-stack --region us-west-2 --stack-name dynamodbscanandserialization-receiptstable --template-body file://cloudformation/dynamodbscanandserialization/classroom/ReceiptsTable.yaml --capabilities CAPABILITY_IAM
   ```
1. Make sure the aws command runs without error.
1. Log into your AWS account on Conduit and verify that the table exists and has
   sample data.
1. Discuss the different attributes with your team to make sure you all understand
   what they represent.
1. As a final verification, run the `dynamodbscanandserialization-classroom-phase0` RDE workflow
   and make sure it passes.

GOAL: Receipts table is created in your AWS Account, the attributes make sense, and your ice cream
parlor can generate a receipt.

Phase 0 is complete when:
- You understand the Receipt data type
- Receipts table exists with some sample data
- `dynamodbscanandserialization-classroom-phase0` RDE workflow passes

## Phase 1: Calculate total sales for time period

Management wants to know sales numbers for the ice cream parlor. They would like to be able to gather sales data for 
different time periods: daily, weekly, monthly, and annually.

You will implement the `getSalesForTimePeriod` API in the `IceCreamParlorAdminService` that will get sales
data for any time period. We've already stubbed it out. Your implementation should utilize the 
`getSalesBetweenDates` method in the `ReceiptDao`, which you will also need to implement.

The `ReceiptDao` has a `ZonedDateTimeConverter` member variable. We've been using this class in many in-class 
activities. Now, we have the knowledge to understand what's happening here. `ZonedDateTime` isn't one of the Java types 
that DynamoDB can understand, like a `String` or `Integer`, so DynamoDB can't just save it directly to the 
database. For types that DynamoDB can't directly save, you must provide a way for the type to be converted to a `String` 
on saves, and converted from a `String` back to its type on a read. This is serialization! 
Check out the `Receipt` class to see the `DynamoDbTypeConverted` annotation we added to the `getPurchaseDate`
method.

The `DynamoDBMapper` uses the converter to translate between `String` and a Java object.
The stored value is still a `String`, so any filters or comparisons we build will work on that `String`
value, not on our Java object. You can use the `ZonedDateTimeConverter` directly to build
a `String` representing any `ZonedDateTime`. The `ISO_DATE_TIME` format allows us to use `String`
methods to sort and compare dates.

Record your group's answers to the following questions in the class digest, then implement the methods.

* Why can't we use a query to restrict dates?
* What is Jeff Bezos's birthday in `ISO_DATE_TIME` format?

HINTS:
* [How can I use ZonedDateTimes in a filter expression?](./hints/hint-phase1.md)

GOAL: Implement `getSalesForTimePeriod` and `getSalesBetweenDates`
so we can request the total sales for a specific time period.

Phase 1 is complete when:
- `dynamodbscanandserialization-classroom-phase1` RDE workflow passes

## Phase 2: Retrieve customer receipts little by little

Now management wants to take a deeper look at individual receipts. We need to implement an API that will allow all 
receipts to be retrieved! Due to the potential size of the receipt table, we want to provide the ability to retrieve 
receipts in batches. 

The API is already stubbed out for you in `IceCreamParlorAdminService`. It is called `getCustomerReceipts`. The new 
API should utilize the `getReceiptsPaginated` method in the `ReceiptDao`. You will need to implement the logic to 
retrieve receipts in batches in the `getReceiptsPaginated` method.

Record your group's answers to the following questions in the class digest, then implement the methods.

* Why can't we use a query to retrieve this data?
* If we added a filter to this scan, and limited it to 10 results at a time:
  * How many results are guaranteed in the first page?
  * How many results are guaranteed in a middle page?
  * How many results are guaranteed in the last page?

GOAL: Implement `getCustomerReceipts` and `getReceiptsPaginated` so we can retrieve all receipts in batches.

Phase 2 is complete when:
- `dynamodbscanandserialization-classroom-phase2` RDE workflow passes

## Phase 3: Long live the sundaes!

In this phase, we'll be working on persisting the sundae order in the receipts. Each order at the ice cream 
parlor can contain multiple sundaes. The `Receipt` model does not currently have a field for the sundaes that 
were ordered, just the total purchase amount. We will need to add a field called `sundaes` to the class, and then, 
like the `ZonedDateTime`, create a conversion class for DynamoDB to use when reading and writing to the table. We would 
like to save the ordered sundaes as JSON. If an exception occurs while serializing/deserializing you can throw a
`SundaeSerializationException` (already defined).

When serializing, the mapper can determine the class of each object in the list.
When deserializing, there's no way to determine what Java class a `String` might represents,
so we have to pass a parameter to the `readValue` method.
Unfortunately, generic parameters *don't have a type*: they're compiler hints, not actual code.
Jackson provides `TypeReference` to specify genericized classes.

When updating the `Receipt` model, we will **not** be editing the `hashcode` and `equals` methods. 
However, you will need to remove the `@DynamoDBIgnore` annotation from the `getSundaes` method.

You will also need to update the `ReceiptDao` to add the ordered sundaes to the `Receipt` object before saving it in the
 `createCustomerReceipt` method.
 
Record your group's answers to the following question in the class digest, then implement the methods.
* What happens to the items already in the datastore?

GOAL: Update `Receipt` and `ReceiptDao` to include the sundae order.

Phase 3 is complete when:
 - `dynamodbscanandserialization-classroom-phase3` RDE workflow passes
 
## Extension - Limit and filter

Add a new API to the `IceCreamParlorAdminService` to get all `Receipt`s with a minimum total sales value. Include
functionality to paginate the results.
