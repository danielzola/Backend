package com.bat.velo.dto;

import java.math.BigInteger;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
public class IncentiveContibutionDTO {

    protected String sourceUserName;
    protected String sourceName;
    protected BigInteger totalAmount;
}
