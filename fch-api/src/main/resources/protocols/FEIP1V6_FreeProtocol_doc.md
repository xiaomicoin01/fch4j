```
FEIP1: FreeProtocol
Version: 6
Language: en-US
Author: C_armX, Deisler-JJ_Sboy
Status: draft
Created date: 2021-02-5
Last modified date：2021-03-10
File hash: "abd3afdb9ff4a9ed6c70396268007568cac9bd4271677f4a6ce2261467f9fdc5"
TXid: 
```
# FEIP1V6_FreeProtocol(en-US)

## Contents
[Introduction](#Introduction)

[General rules of FEIP type protocols](#general-rules-of-feip-type-protocols)

[Rules specific to this protocol](#rules-specific-to-this-protocol)

[Register](#register)

[Unregister](#unregister)

[Like/Unlike](#like-unlike)



## Introduction

```
Protocol type: FEIP
Serial number: 1
Protocol name: FreeProtocol
Version number: 6
Description : Register, unregister or evaluate protocols freely by writing data in OP_RETURN.
Author: C_armX, Deisler-JJ_Sboy
Language: en-US
Tags: FEIP, free protocol, basic protocol
Previous version hash:"bd0429aaf6d7784958a36473f2f925d8d2ca76125cc610adf2afb1956a1d56b2"

```

## General rules of FEIP type protocols

1. Write important data in OP_RETURN for public witness under FEIP type protocols.

2. The max size of OP_RETURN : 4096 bytes

3. Format : compacted json

4. Encoding : utf-8


## Rules specific to this protocol

1. Title in protocol text: type + serial number + "V" + version number +'_' + protocol name + '(' + language + ')', e.g.: FEIP3V4_CID(en-US)

2. Anyone can send a transaction to register a protocol by writing data in the format given by [Register](#register) .

3. Anyone can only unregister his/her own protocols by writing data in the format given by [Unregister](#unregister).

4. Anyone can evaluate any protocol by writing data in the format given by [Like/Unlike](#like-unlike).


## Register
Register a protocol by send a transaction, the OP_RETURN of which contains the data as follows:


|field number|fieldname|type|lenth|content|required|
|:----|:----|:----|:----|:----|:----|
|1|type|String|4|Fixed: "FEIP"<br>Case insensitive|Y|
|2|sn|int|1|Serial number<br>Fixed: 1|Y|
|3|ver|int|1|Fixed: 6|Y|
|4|name|String|18|Fixed:"FreeProtocol"<br>Case insensitive|N|
|5|hash|hex|32|Fixed:"abd3afdb9ff4a9ed6c70396268007568cac9bd4271677f4a6ce2261467f9fdc5"|N|
|6|data.type|String|8|Type of the protocol being registered.|N|
|7|data.sn|String|8|Serial number of the protocol being registered, counting from 1.|N|
|8|data.ver|String|4|Version number of the protocol being registered. counting from 1.|N|
|9|data.name|String|64|Name of the protocol being registered.|N|
|10|data.desc|String|256|Short description of the protocol being registered.|N|
|11|data.authors|String array|128|FCH addresses of the authors of the protocol being registered.|N|
|12|data.hash|hex|64|The sha256 value of the protocol file being registered.<br>|Y|
|13|data.lang|String|5|The language of the protocol being registered, formatted with i18n(internationalization),such as "en-US".|N|
|14|data.op|String|8|Fixed: "register"<br>Case insensitive|Y|
|15|data.tags|String array|128|Tags about the protocol being registered.|N|
|16|data.preHash|hex|64|“Hash” of previous version.|N|
|17|data.fileUrl|String array|128|locations to find the protocol file.|N|

**Register Example:**
```
{
    "type": "FEIP",
    "sn": 1,
    "ver": 6,
    "name": "FreeProtocol",
    "hash": "abd3afdb9ff4a9ed6c70396268007568cac9bd4271677f4a6ce2261467f9fdc5",
    "data": {
        "type": "FEIP",
        "sn": 3,
        "ver": 4,
        "name": "CID",
        "desc": "Register or unregister a human friendly identity for an address.",
        "authors": ["FPL44YJRwPdd2ipziFvqq6y2tw4VnVvpAv","FS2AWq1dgdhCpNTwqfBbMBBJGNNj1LSboy","FLx88wdsbLQyZRmbqtpeXA9u5FG9EyCash"],
        "hash": "1403e5b7100d8e24724f12cd1ea0b722086c02250a7c66b711947f14546cfcfd",
        "lang": "zh-CN",
        "op": "register",
        "tags": ["FEIP", "CID", "identity", "human friendly", "basic protocol"],
        "preHash":"bd0429aaf6d7784958a36473f2f925d8d2ca76125cc610adf2afb1956a1d56b2",
        "fileUrl": ["https://github.com/freecashorg/FEIP/FEIP3_CID/"]
    }
}
```

## Unregister
Unregister a protocol by send a transaction, the OP_RETURN of which contains the data as follows:


|field number|fieldname|type|lenth|content|required|
|:----|:----|:----|:----|:----|:----|
|1|type|String|4|Fixed: "FEIP"<br>Case insensitive|Y|
|2|sn|int|1|Serial number<br>Fixed: 1|Y|
|3|ver|int|1|Fixed: 6|Y|
|4|name|String|18|FreeProtocol"<br>Case insensitive|N|
|5|hash|hex|32|Fixed:"abd3afdb9ff4a9ed6c70396268007568cac9bd4271677f4a6ce2261467f9fdc5"|N|
|6|data.hash|hex|64|The sha256 value of the protocol file being unregistered.|Y|
|7|data.op|String|10|Fixed: "unregister"<br>Case insensitive|Y|


**Unregister Example:**
```
{
    "type": "FEIP",
    "sn": 1,
    "ver": 6,
    "name": "FreeProtocol",
    "hash": "abd3afdb9ff4a9ed6c70396268007568cac9bd4271677f4a6ce2261467f9fdc5",
    "data": {
        "hash": "bd0429aaf6d7784958a36473f2f925d8d2ca76125cc610adf2afb1956a1d56b2",
        "op": "unregister"
    }
}

```

## Like/unlike

Evaluate a protocol by send a transaction, the OP_RETURN of which contains the data as follows:

|field number|fieldname|type|lenth|content|required|
|:----|:----|:----|:----|:----|:----|
|1|type|String|4|Fixed: "FEIP"<br>Case insensitive|Y|
|2|sn|int|1|Serial number<br>Fixed: 1|Y|
|3|ver|int|1|Fixed: 6|Y|
|4|name|String|18|Fixed:"FreeProtocol"<br>Case insensitive|N|
|5|hash|hex|32|Fixed:"abd3afdb9ff4a9ed6c70396268007568cac9bd4271677f4a6ce2261467f9fdc5"|N|
|6|data.hash|hex|64|The sha256 value of the protocol file being unregistered.|Y|
|7|data.op|String|10|Fixed: "unregister"<br>Case insensitive|Y|
|8|data.like|int|1|Evaluation:1 or -1|Y|

**Like/unlike Example:**
```
{
    "type": "FEIP",
    "sn": 1,
    "ver": 6,
    "name": "FreeProtocol",
    "hash": "abd3afdb9ff4a9ed6c70396268007568cac9bd4271677f4a6ce2261467f9fdc5",
    "data": {
        "hash": "1403e5b7100d8e24724f12cd1ea0b722086c02250a7c66b711947f14546cfcfd",
        "op": "unregister",
        "like": "+"
    }
}
```
