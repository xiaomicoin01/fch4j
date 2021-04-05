```
FEIP17: SafetyBox
Version: 3
Language: en-US
Author: C_armX, Deisler-JJ_Sboy，Free_Cash
Status: draft
Created date: 2021-03-07
Last modified date：2021-03-08
File hash: "a9c9879d7b33473a7b04f847a8a9ca067ce82410fcab98b7c936f92e4a5d45f3"
TXid: 
```

# FEIP17V3_SafetyBox(en-US)

## Contents
[Introduction](#introduction)

[General rules of FEIP type protocols](#general-rules-of-feip-type-protocols)

[Rules specific to this protocol](#rules-specific-to-this-protocol)

[Add an item](#add-an-item)

[Delete an item](#delete-an-item)


## Introduction

```
Protocol type: FEIP
Serial number: 17
Protocol name: SafetyBox
Version: 3
Description : Save encrpted important data on the blockchain of FCH.
Author: C_armX, Deisler-JJ_Sboy，Free_Cash
Language: en-US
Tags: FEIP, safety box, application protocol.
Previous version hash:"ea73a3501179224636d2939f1b2e2f581052d0632bacae3b677cc3805157de96"
```

## General rules of FEIP type protocols

1. Write important data in OP_RETURN for public witness under FEIP type protocols

2. The max size of OP_RETURN : 4096 bytes

3. Format : compacted json

4. Encoding : utf-8


## Rules specific to this protocol

1. This protocol helps users to write encrypted personal information in the blockchain of FCH.

2. Use the public key of the first output address to encrypt the message.

## Add an item

When user create a new item, the OP_RETURN contains the data as follows:

|field number|fieldname|type|lenth|content|required|
|:----|:----|:----|:----|:----|:----|
|1|type|String|4|Fixed: "FEIP"<br>Case insensitive|Y|
|2|sn|int|2|Serial number<br>Fixed: 17|Y|
|3|ver|int|1|Fixed: 3|Y|
|4|name|String|9|Fixed: "SafetyBox"<br>Case insensitive|N|
|5|hash|hex|32|Sha256 value of this protocol file|N|
|6|data.op|string|3|operation: "add" or "del"|Y|
|7|data.alg|string|1-32|The encrypt algorithm.<br>"ECC256k1-AES256CBC" is recommended.|Y|
|8|data.msg|string|1-2048|Encrypted message|Y|

### Raw data of data.msg

|field number|fieldname|type|lenth|content|required|
|:----|:----|:----|:----|:----|:----|
|1|type|string|16|Customized by the user or App|N|
|2|title|string|128|Title, account, or other.<br>Depends on "type".|N|
|3|content|string|1024|Text, password, or other. <br>Depends on "type". |Y|
|4|memo|string|256||N|

### Example to add an item
```
The address of first output: FEk41Kqjar45fLDriztUDTUkdki7mmcjWK
Publickey: 6vU3ZMpwggurw92AUy1Vi6WBxEnBPdjupXGKD7Q5Zcw8yvdJAf
Privatekey: L2bHRej6Fxxipvb4TiR5bu1rkT3tRp8yWEsUy4R1Zb8VMm2x7sd8

OP_RETURN content:

{
    "type": "FEIP",
    "sn": 17,
    "ver": 3,
    "name": "SafetyBox",
    "hash": "a9c9879d7b33473a7b04f847a8a9ca067ce82410fcab98b7c936f92e4a5d45f3",
    "data":{
        "op": "add",
        "alg": "ECC256k1-AES256CBC",
        "msg": "AjTU0rGQvDxhCs3F5x4Pcz3Bsiiry2LryPcKcPIZ2iDsD68U5he9FkM6AVUzEHTjmfBLkhfFu7rv4fveoyMi5YH+wQoiWDxgs/MYjGZBL/Fuq6XZ6IOCXfWyfwphE4uxhEg5TD9ZBRsrJbNxwbdfee5ev5Gvc8kwYROycs0sAG3rNdoJbEZZ7bs2DqvHbAWdG7w4gYLhP9o+C/xVTZHz7Ks9VHb6i04/1at40etlWXxPWSvkdDWxTtyWSSsY2jrbYjfe+ytXQRTRY4gYQdwg+9s="
        }
}

Raw information of data.msg:

{
    "type": "password",
    "title": "john@gmail.com",
    "content": "123456",
    "memo": "https://www.gmial.com"
}
```

## Delete an item

When user deletes an item, the OP_RETURN contains the data as follows:

|field number|fieldname|type|lenth|content|required|
|:----|:----|:----|:----|:----|:----|
|1|type|String|4|Fixed: "FEIP"<br>Case insensitive|Y|
|2|sn|int|2|Serial number<br>Fixed: 17|Y|
|3|version|int|1|Fixed: 3|Y|
|4|name|String|9|Fixed: "SafetyBox"<br>Case insensitive|N|
|5|hash|hex|32|Sha256 value of this protocol file|N|
|6|data.op|string|3|operation: "del"|Y|
|7|data.txid|string|64|The txid in which the item being deleting was added.|Y|

### Example of deleting an item
```
{
    "type": "FEIP",
    "sn": 17,
    "ver": 3,
    "name": "SafetyBox",
    "hash": "a9c9879d7b33473a7b04f847a8a9ca067ce82410fcab98b7c936f92e4a5d45f3",
    "data":{
        "op": "del",
        "txid": "/*the txid in which the item being deleted was added.*/"
    }
}
```
