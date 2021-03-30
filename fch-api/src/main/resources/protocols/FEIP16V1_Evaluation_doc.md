```
FEIP16: Evaluation
Version: 1
Language: en-US
Author: Deisler-JJ_Sboy, C_armX
Status: draft
Created date: 2021-02-5
Last modified date：2021-03-06
File hash: "4323a7b7b1bc19ab72c23da2f1260a5273eb38274cd55949f0ccad8bb9b7d8b0"
TXid: 
```

# FEIP16V1_Evaluation(en-US)

## Contents

[Introduction](#introduction)

[General rules of FEIP type protocols](#general-rules-of-feip-type-protocols)

[Rules specific to this protocol](#rules-specific-to-this-protocol)

[Example of positive evaluation](#example-of-positive-evaluation)

[Example of negative evaluation](#example-of-negative-evaluation)


## Introduction

```
Protocol type: FEIP
Serial number: 16
Protocol name: Evaluation
Version: 1
Description : Evaluate an CID to increase/decrease its reputation.
Author: Deisler-JJ_Sboy，C_armX
Language: en-US
Tags: FEIP, Evaluation, Reputation, basic protocol
Previous version hash: "none"
```

## General rules of FEIP type protocols

1. Write important data in OP_RETURN for public witness under FEIP type protocols.

2. The max size of OP_RETURN : 4096 bytes

3. Format : compacted json

4. Encoding : utf-8


## Rules specific to this protocol

1. FEIP16 provides a way for an CID to evaluate other CIDs.
2. Making evaluation consumes CoinDays.
3. The evaluation can be Positive or Negative, and The measure unit of an evaluation is CoinDays. For example: "100 CoinDays Negative evaluation" can be marked as "-100CD", and "88 CoinDays positive evaluation" can be marked as "88CD" or "+88CD".
4. The quantity of an evaluation is the CoinDays consumed in the transaction.
5. The evaluator should put the address of evaluatee at first output.

## OP_RETURN

The OP_RETURN of which contains the data as follows:

|field number|fieldname|type|lenth|content|required|
|:----|:----|:----|:----|:----|:----|
|1|type|String|4|Fixed: "FEIP"<br>Case insensitive|Y|
|2|sn|int|2|Serial number<br>Fixed: 16|Y|
|3|ver|int|1|Fixed: 1|Y|
|4|name|String|10|Fixed: "Evaluation"<br>Case insensitive|N|
|5|hash|hex|32|Sha256 value of this protocol file|N|
|6|data.sign|string|1|must be "+" or "-"|Y|


## Example of positive evaluation
```

Input address: FPL44YJRwPdd2ipziFvqq6y2tw4VnVvpAv
First output address: FS2AWq1dgdhCpNTwqfBbMBBJGNNj1LSboy

OP_RETURN content:
{
    "type": "FEIP",
    "sn": 16,
    "ver": 1,
    "name": "Evaluation",
    "hash": "4323a7b7b1bc19ab72c23da2f1260a5273eb38274cd55949f0ccad8bb9b7d8b0",
    "data":{
        "sign": "+",
        }
}

```

## Example of negative evaluation
```

Input address: FPL44YJRwPdd2ipziFvqq6y2tw4VnVvpAv
First output address: FS2AWq1dgdhCpNTwqfBbMBBJGNNj1LSboy

OP_RETURN content:

{
    "type": "FEIP",
    "sn": 3,
    "ver": 4,
    "name": "Evaluation",
    "hash": "4323a7b7b1bc19ab72c23da2f1260a5273eb38274cd55949f0ccad8bb9b7d8b0",
    "data":{
        "sign": "-",
        }
}

```