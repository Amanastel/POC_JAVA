package com.featureflag.featureflag.config;

import org.togglz.core.Feature;
import org.togglz.core.annotation.Label;

public enum FeatureFlags implements Feature {

    @Label("Price Increase for 19/03/2024")
    PRICE_INCREASE,

    @Label("New description")
    DESCRIPTION_UPDATE;

}