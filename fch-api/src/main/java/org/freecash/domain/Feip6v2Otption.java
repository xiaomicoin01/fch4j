package org.freecash.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

@Getter
@AllArgsConstructor
public enum Feip6v2Otption {
    /**
     * 授权
     */
    AUTHORITION("authorition","授权"),
    /**
     * 解除授权
     */
    DEPRIVATION("deprivation","解除授权"),
    /**
     * 永久授权
     */
    IRREVOCABLE_AUTHORITION("irrevocable authorition","永久授权");

    private String value;
    private String desp;

    public static Feip6v2Otption acqureByValue(String value) throws Exception{
        return Arrays.stream(Feip6v2Otption.values()).filter(i-> Objects.equals(i.getValue(),value)).findFirst()
                .orElseThrow(()->new Exception("位置操作："+value));
    }

}
