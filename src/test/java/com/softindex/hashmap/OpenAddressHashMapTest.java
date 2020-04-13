package com.softindex.hashmap;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;
import java.util.stream.IntStream;

public class OpenAddressHashMapTest {

    private HashMap map;
    private static final int MAX = 20000;
    private static final int MIN = 10000;
    private static final int amountOfElements = 500;

    @Before
    public void beforeEachMethod() {
        map = new OpenAddressHashMap();
    }

    @Test
    public void whenPutTwoSameKeysExpectSizeOneAndFirstPutReplaced() {
        map.put(1, 1L);
        map.put(1, 2L);

        Assert.assertEquals(1, map.size());
        Assert.assertEquals(2L, map.get(1));
    }

    @Test
    public void shouldResizeCapacityWhenCapacityIsLoaded() {
        IntStream.range(0, amountOfElements).forEach(index -> map.put((int) (Math.random() * ((MAX - MIN) + 1)) + MIN, index));
        Assert.assertTrue(((OpenAddressHashMap) map).getCapacity() > 16);
    }

    @Test
    public void whenPutDifferentKeysWithSameHashFunctionExpectSavingInNextNullBucket() {
        map.put(18, 1L);
        map.put(34, 2L);

        Assert.assertEquals(1L, map.get(18));
        Assert.assertEquals(2L, map.get(34));

        Assert.assertEquals(2, ((OpenAddressHashMap) map).getBucketNumber(18));
        Assert.assertEquals(3, ((OpenAddressHashMap) map).getBucketNumber(34));
    }

    @Test
    public void whenPutDifferentKeysWithSameHashFunctionAndNextBucketIsNotNullExpectSavingInNextNullBucket() {
        map.put(18, 1L);
        map.put(19, 2L);
        map.put(34, 3L);

        Assert.assertEquals(1L, map.get(18));
        Assert.assertEquals(2L, map.get(19));
        Assert.assertEquals(3L, map.get(34));

        Assert.assertEquals(2, ((OpenAddressHashMap) map).getBucketNumber(18));
        Assert.assertEquals(3, ((OpenAddressHashMap) map).getBucketNumber(19));
        Assert.assertEquals(4, ((OpenAddressHashMap) map).getBucketNumber(34));
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowExceptionWhenTryToGetNotSavedKey() {
        map.get(1);
    }

    @Test
    public void whenPutNegativeKeyExpectAbsHash() {
        map.put(-5, 4L);
        Assert.assertEquals(4L, map.get(-5));
    }

}
