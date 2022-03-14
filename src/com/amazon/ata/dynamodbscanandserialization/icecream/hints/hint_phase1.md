## How can I use ZonedDateTimes in a filter expression?

First, you must add the `ZonedDateTime` to your attribute value map. You can create a new `AtttributeValue` by
translating the `ZonedDateTime` to a `String`.
- `new AttributeValue().withS(methodToConvertToString(dateTimeObject))`

Second, you can now use your attribute created above, in the filter expression. You can use logical operators
to compare the purchase dates. To retrieve all receipts with a `purchaseDate` after your attribute value
you might write a filter expression like: 
- `purchaseDate > :purchaseDate`.