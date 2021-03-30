```
FEIP3: CID
Version: 4
Language: en-US
Author: C_armX, Deisler-JJ_Sboy，Free_Cash
Status: draft
Created date: 2021-02-5
Last modified date：2021-03-06
File hash: "c296a4c88c7335d042638b0f875e1b4ffa56fe3970afe1ccdbd64d78fb302f3d"
TXid: 
```

# FEIP3V4_CID(en-US)

## Contents

[Introduction](#introduction)

[General rules of FEIP type protocols](#general-rules-of-feip-type-protocols)

[Rules specific to this protocol](#rules-specific-to-this-protocol)

[Example of Registering a CID](#example-of-registering-a-cid)

[Example of Unregistering a CID](#example-of-unregistering-a-cid)



## Introduction

```

Protocol type: FEIP
Serial number: 3
Protocol name: CID
Version: 4
Description : Register or unregister a human friendly identity for an address.
Author: C_armX, Deisler-JJ_Sboy，Free_Cash
Language: en-US
Tags: FEIP, CID, identity, human friendly, basic protocol
Previous version hash:"a43b4d30cf30acfc13d298435118e466da88c679f07de61fd494330b84c63a18"

```

## General rules of FEIP type protocols

1. Write important data in OP_RETURN for public witness under FEIP type protocols.

2. The max size of OP_RETURN : 4096 bytes

3. Format : compacted json

4. Encoding : utf-8


## Rules specific to this protocol

1. CID(Crypto Identity)：“name"+"_"+suffix, e.g. CY_vpAv.

2. Suffix：The last four letters of the address. If the new CID is the same as any CID that has been registered, increase the length of suffix until the new CID is unique, e.g. CY_kvpAv.

3. When an address registers a new cid, its previous cid is automatically unregistered.

4. Once a CID is registered by an address, it cannot be registered by other addresses, even if the CID has been unregistered.

5. An address can re-register its unregistered CID.



## OP_RETURN

The OP_RETURN of which contains the data as follows:

|field number|fieldname|type|lenth<br>bytes|content|required|
|:----|:----|:----|:----|:----|:----|
|1|type|String|4|Fixed: "FEIP"<br>Case insensitive|Y|
|2|sn|int|1|Serial number<br>Fixed: 3|Y|
|3|ver|int|1|Fixed: 4|Y|
|4|name|String|3|Fixed: "CID"<br>Case insensitive|N|
|5|hash|hex|32|Sha256 value of this protocol file|N|
|6|data.op|string|6-8|operation: "register" or "unregister"|Y|
|7|data.name|string|1-32|Nick name given by the user|Y when operation is register,<br>N when operation is unregister|


## Example of Registering a CID
```

Address: FPL44YJRwPdd2ipziFvqq6y2tw4VnVvpAv
CID：CY_vpAv
OP_RETURN content:

{
    "type": "FEIP",
    "sn": 3,
    "ver": 4,
    "name": "CID",
    "hash": "c296a4c88c7335d042638b0f875e1b4ffa56fe3970afe1ccdbd64d78fb302f3d",
    "data":{
        "op": "register",
        "name": "CY"
        }
}

```

## Example of Unregistering a CID
```

Address: FPL44YJRwPdd2ipziFvqq6y2tw4VnVvpAv

OP_RETURN content:

{
    "type": "FEIP",
    "sn": 3,
    "ver": 4,
    "name": "CID",
    "hash": "c296a4c88c7335d042638b0f875e1b4ffa56fe3970afe1ccdbd64d78fb302f3d",
    "data":{
        "op": "unregister"
        }
}

```
