package com.edu.mesler.currency.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Currency {
    Long id;
    String code;
    String fullName;
    String sign;
}
