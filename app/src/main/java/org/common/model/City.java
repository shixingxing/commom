package org.common.model;

import java.util.ArrayList;

import lombok.Data;

@Data
public class City extends District {
    private ArrayList<District> districts;

}
