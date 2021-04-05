```
FEIP7: Message
Version: 4
Language: en-US
Author: C_armX, Deisler-JJ_Sboy
Status: draft
Created date: 2021-04-03
Last modified date：2021-04-03
File hash: ""
TXid: 
```

# FEIP7V4_Message(en-US)

## Contents
[Introduction](#introduction)

[General rules of FEIP type protocols](#general-rules-of-feip-type-protocols)

[Rules specific to this protocol](#rules-specific-to-this-protocol)

[Send a message](#send-a-message)

## Introduction

```
Protocol type: FEIP
Serial number: 7
Protocol name: Message
Version: 4
Description : Send encrypted message via the blockchain of FCH.
Author: C_armX, Deisler-JJ_Sboy
Language: en-US
Tags: FEIP, encrypted message, application protocol.
Previous version hash:"0d5f0ecff1f5cd64463bcd08a595e561ded0ea499b2d2e3d5db2d59f950476d8"
```

## General rules of FEIP type protocols

1. Write important data in OP_RETURN for public witness under FEIP type protocols

2. The max size of OP_RETURN : 4096 bytes

3. Format : compacted json

4. Encoding : utf-8


## Rules specific to this protocol

1. This protocol helps users to send p2p encrypted message via the blockchain of FCH.

2. The receiver is the first output address,the public key of which encrypts the message.

## Send a message

When user create a new item, the OP_RETURN contains the data as follows:

|field number|fieldname|type|lenth|content|required|
|:----|:----|:----|:----|:----|:----|
|1|type|String|4|Fixed: "FEIP"<br>Case insensitive|Y|
|2|sn|int|2|Serial number<br>Fixed: 7|Y|
|3|ver|int|1|Fixed: 4|Y|
|4|name|String|13|Fixed: "Message"<br>Case insensitive|N|
|5|hash|hex|32|Sha256 value of this protocol file|N|
|6|data.alg|string|1-32|The encrypt algorithm.<br>"ECC256k1-AES256CBC" is recommended.|Y|
|7|data.msg|string|1-2048|Encrypted message|Y|

### Example of sending a message
```
The address of first output: FEk41Kqjar45fLDriztUDTUkdki7mmcjWK
Publickey: 6vU3ZMpwggurw92AUy1Vi6WBxEnBPdjupXGKD7Q5Zcw8yvdJAf
Privatekey: L2bHRej6Fxxipvb4TiR5bu1rkT3tRp8yWEsUy4R1Zb8VMm2x7sd8

OP_RETURN content:

{
    "type": "FEIP",
    "sn": 7,
    "ver": 4,
    "name": "Message",
    "hash": "",
    "data":{
        "alg": "ECC256k1-AES256CBC",
        "msg": "AjTU0rGQvDxhCs3F5x4Pcz3Bsiiry2LryPcKcPIZ2iDsD68U5he9FkM6AVUzEHTjmfBLkhfFu7rv4fveoyMi5YH+wQoiWDxgs/MYjGZBL/Fuq6XZ6IOCXfWyfwphE4uxhEg5TD9ZBRsrJbNxwbdfee5ev5Gvc8kwYROycs0sAG3rNdoJbEZZ7bs2DqvHbAWdG7w4gYLhP9o+C/xVTZHz7Ks9VHb6i04/1at40etlWXxPWSvkdDWxTtyWSSsY2jrbYjfe+ytXQRTRY4gYQdwg+9s="
        }
}
