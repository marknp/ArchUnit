package com.tngtech.archunit.example.layers;

import junit.framework.Assert;
import junit.framework.TestCase;

public class WrongNameTest extends TestCase {

    public void test() {
        Assert.assertNull(null);
    }
}