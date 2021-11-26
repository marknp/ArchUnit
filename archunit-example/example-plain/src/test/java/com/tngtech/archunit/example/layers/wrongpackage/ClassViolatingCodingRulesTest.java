package com.tngtech.archunit.example.layers.wrongpackage;

import junit.framework.Assert;
import junit.framework.TestCase;

public class ClassViolatingCodingRulesTest extends TestCase {

    public void test() {
        Assert.assertNull(null);
    }
}