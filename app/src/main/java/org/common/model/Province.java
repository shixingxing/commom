package org.common.model;

import java.util.ArrayList;

import lombok.Data;

@Data
public class Province extends District {
    private ArrayList<City> cities;

}
