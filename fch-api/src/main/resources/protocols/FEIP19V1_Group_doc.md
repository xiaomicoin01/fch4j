```
FEIP19: Group
Version: 1
Language: en-US
Author: C_armX, Deisler-JJ_Sboy
Status: draft
Created date: 2021-04-03
Last modified date：2021-04-03
File hash: "9b1f76a0f42be93b89df1b712547f1c55ecaaa9e0cf95288ba7bf9a6f6131515"
TXid: 
```

# FEIP19V3_Group(en-US)

## Contents
[Introduction](#introduction)

[General rules of FEIP type protocols](#general-rules-of-feip-type-protocols)

[Rules specific to this protocol](#rules-specific-to-this-protocol)

[Create a group](#create-a-group)

[Update group information](#update-group-information)

[Join in a group](#join-in-a-group)

[Leave a group](#leave-a-group)


## Introduction

```
Protocol type: FEIP
Serial number: 19
Protocol name: Group
Version: 1
Description :  Public group protocol
Author: C_armX, Deisler-JJ_Sboy
Language: en-US
Tags: FEIP, group, application protocol
Previous version hash:"unknown"
```

## General rules of FEIP type protocols

1. Write important data in OP_RETURN for public witness under FEIP type protocols

2. The max size of OP_RETURN : 4096 bytes

3. Format : compacted json

4. Encoding : utf-8

## Rules specific to this protocol
1. Everyone can create groups. The creator has no privilege.
2. The hash of the creating transaction is the group id, called gid.
3. Group information includes name, description, CoinDays.
4. Everyone can update the information of any group, by consuming more CoinDays.
5. Everyone can join or leave any group.

## Create a group
|field number|fieldname|type|lenth|content|required|
|:----|:----|:----|:----|:----|:----|
|1|type|String|4|Fixed: "FEIP"<br>Case insensitive|Y|
|2|sn|int|2|Serial number<br>Fixed: 19|Y|
|3|ver|int|1|Fixed: 1|Y|
|4|name|String|5|Fixed: "Group"<br>Case insensitive|N|
|5|hash|hex|32|Sha256 value of this protocol file|N|
|6|data.op|string|6|operation: "create"|Y|
|7|data.name|string|1-32|Group name|Y|
|8|data.desc|string|1-2048|Description of this group|Y|

### Example of creating a group
```
{
    "type": "FEIP",
    "sn": 19,
    "ver": 3,
    "name": "Group",
    "hash": "9b1f76a0f42be93b89df1b712547f1c55ecaaa9e0cf95288ba7bf9a6f6131515",
    "data":{
        "op": "create",
        "name": "test",
        "desc": "This is a test group"
    }
}
```

## Update group information
|field number|fieldname|type|lenth|content|required|
|:----|:----|:----|:----|:----|:----|
|1|type|String|4|Fixed: "FEIP"<br>Case insensitive|Y|
|2|sn|int|2|Serial number<br>Fixed: 19|Y|
|3|ver|int|1|Fixed: 1|Y|
|4|name|String|5|Fixed: "Group"<br>Case insensitive|N|
|5|hash|hex|32|Sha256 value of this protocol file|N|
|6|data.op|string|6|operation: "update"|Y|
|7|data.gid|string|32|Group ID|Y|
|8|data.name|string|1-32|Group name|Y|
|9|data.desc|string|1-2048|Description of this group|Y|

### Example of updating group information
```
{
    "type": "FEIP",
    "sn": 19,
    "ver": 1,
    "name": "Group",
    "hash": "9b1f76a0f42be93b89df1b712547f1c55ecaaa9e0cf95288ba7bf9a6f6131515",
    "data":{
        "op": "update",
        "gid": "1111111122222222333333334444444411111111222222223333333344444444",
        "name": "test",
        "desc": "This is a test group"
    }
}
```

## Join in a group
|field number|fieldname|type|lenth|content|required|
|:----|:----|:----|:----|:----|:----|
|1|type|String|4|Fixed: "FEIP"<br>Case insensitive|Y|
|2|sn|int|2|Serial number<br>Fixed: 19|Y|
|3|ver|int|1|Fixed: 1|Y|
|4|name|String|5|Fixed: "Group"<br>Case insensitive|N|
|5|hash|hex|32|Sha256 value of this protocol file|N|
|6|data.op|string|4|operation: "join"|Y|
|7|data.gid|string|32|Group ID|Y|

### Example of joining in a group
```
{
    "type": "FEIP",
    "sn": 19,
    "ver": 1,
    "name": "Group",
    "hash": "9b1f76a0f42be93b89df1b712547f1c55ecaaa9e0cf95288ba7bf9a6f6131515",
    "data":{
        "op": "join",
        "gid": "1111111122222222333333334444444411111111222222223333333344444444"
    }
}
```

## Leave a group
|field number|fieldname|type|lenth|content|required|
|:----|:----|:----|:----|:----|:----|
|1|type|String|4|Fixed: "FEIP"<br>Case insensitive|Y|
|2|sn|int|2|Serial number<br>Fixed: 19|Y|
|3|ver|int|1|Fixed: 1|Y|
|4|name|String|5|Fixed: "Group"<br>Case insensitive|N|
|5|hash|hex|32|Sha256 value of this protocol file|N|
|6|data.op|string|5|operation: "leave"|Y|
|7|data.gid|string|32|Group ID|Y|

### Example of leaving a group
```
{
    "type": "FEIP",
    "sn": 19,
    "ver": 1,
    "name": "Group",
    "hash": "9b1f76a0f42be93b89df1b712547f1c55ecaaa9e0cf95288ba7bf9a6f6131515",
    "data":{
        "op": "leave",
        "gid": "1111111122222222333333334444444411111111222222223333333344444444"
    }
}
```
