# Still in the ice cream business...

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

1. Create the table we'll be using for this activity by running this aws CLI command:
   ```none
   aws cloudformation create-stack --region us-west-2 --stack-name dynamodbscanandserialization-receiptstable --template-body file://cloudformation/dynamodbscanandserialization/ReceiptsTable.yaml --capabilities CAPABILITY_IAM
   ```
1. Make sure the aws command runs without error.
1. Log into your AWS account and verify that the table exists and has
   sample data.
1. Discuss the different attributes with your team to make sure you all understand
   what they represent.
1. As a final verification, run the `Phase0Test` and make sure it passes.

GOAL: Receipts table is created in your AWS Account, the attributes make sense, and your ice cream
parlor can generate a receipt.

Phase 0 is complete when:
- You understand the Receipt data type
- Receipts table exists with some sample data
- `Phase0Test` passes

You will be using this project over two days. Depending on the day, please follow the appropriate README.

[Serialization README](./README_Serialization.md)

[Scanning README](./README_Scanning.md)