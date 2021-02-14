package com.infoshare.formobjects;

import com.infoshare.domain.TypeOfHelp;
import com.infoshare.util.ValidatorEnum;
import com.infoshare.validator.RegExpPattern;

public class NeedRequestSearchForm {
    @RegExpPattern(regexp = ValidatorEnum.POLISHSIGNS)
    private String city;
    private TypeOfHelp typeOfHelp;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public TypeOfHelp getTypeOfHelp() {
        return typeOfHelp;
    }

    public void setTypeOfHelp(TypeOfHelp typeOfHelp) {
        this.typeOfHelp = typeOfHelp;
    }
}
