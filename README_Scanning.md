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

Consider the following questions, then implement your solution:

* Why can't we use a query to restrict dates?
* What is Jeff Bezos's birthday in `ISO_DATE_TIME` format?

HINTS:
* [How can I use ZonedDateTimes in a filter expression?](src/com/amazon/ata/dynamodbscanandserialization/icecream/hints/hint_phase1.md)

GOAL: Implement `getSalesForTimePeriod` and `getSalesBetweenDates`
so we can request the total sales for a specific time period.

Phase 1 is complete when:
- `Phase1Test` passes

## Phase 2: Retrieve customer receipts little by little

Now management wants to take a deeper look at individual receipts. We need to implement an API that will allow all
receipts to be retrieved! Due to the potential size of the receipt table, we want to provide the ability to retrieve
receipts in batches.

The API is already stubbed out for you in `IceCreamParlorAdminService`. It is called `getCustomerReceipts`. The new
API should utilize the `getReceiptsPaginated` method in the `ReceiptDao`. You will need to implement the logic to
retrieve receipts in batches in the `getReceiptsPaginated` method.

Consider the following questions, then implement your solution:

* Why can't we use a query to retrieve this data?
* If we added a filter to this scan, and limited it to 10 results at a time:
    * How many results are guaranteed in the first page?
    * How many results are guaranteed in a middle page?
    * How many results are guaranteed in the last page?

GOAL: Implement `getCustomerReceipts` and `getReceiptsPaginated` so we can retrieve all receipts in batches.

Phase 2 is complete when:
- `Phase2Test` passes

## Extension - Limit and filter

Add a new API to the `IceCreamParlorAdminService` to get all `Receipt`s with a minimum total sales value. Include
functionality to paginate the results.
