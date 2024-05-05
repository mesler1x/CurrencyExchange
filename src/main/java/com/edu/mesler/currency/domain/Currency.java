package com.edu.mesler.currency.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Currency {
    int id;
    String code;
    String fullName;
    String sign;
}
