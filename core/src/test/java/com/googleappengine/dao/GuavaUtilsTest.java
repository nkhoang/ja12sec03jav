package com.googleappengine.dao;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ListMultimap;
import junit.framework.Assert;
import org.junit.Test;

/**
 * Real world usage for Guava Lib.
 */
public class GuavaUtilsTest {
    /**
     * Test {@link com.google.common.collect.Multiset}.
     */
    @Test
    public void testMultiSet() {

    }

    /**
     * Test {@link com.google.common.collect.ListMultimap}
     */
    @Test
    public void testListMultiMap() {
        // collection person by age.
        ListMultimap<Integer, Person> personAgeCollection = ArrayListMultimap.create();
        Person p1 = new Person("Hoang", 10);
        Person p2 = new Person("Khanh", 11);
        Person p3 = new Person("Hoang", 20);
        Person p4 = new Person("Dong", 11);

        personAgeCollection.put(p1.getAge(), p1);
        personAgeCollection.put(p2.getAge(), p2);
        personAgeCollection.put(p3.getAge(), p3);
        personAgeCollection.put(p4.getAge(), p4);

        Assert.assertEquals(2, personAgeCollection.get(11).size());
        Assert.assertTrue(personAgeCollection.containsEntry(p1.getAge(), p1));
        Assert.assertTrue(personAgeCollection.remove(p2.getAge(), p2));
        Assert.assertEquals(1, personAgeCollection.get(11).size());
    }

    @Test
    public void testBiMap() {
        BiMap<String, String> dataMap = HashBiMap.create();
        dataMap.put("number", "dea123");
        dataMap.put("dataEntered", "10/10/2012");
        dataMap.put("keyCode", "code00001");
        dataMap.put("isActive", "true");
        // key and value are both unique.
        dataMap.forcePut("isMember", "true");

        Assert.assertEquals("dea123", dataMap.get("number"));
        BiMap<String, String> inverseMap = dataMap.inverse();
        Assert.assertEquals("keyCode", inverseMap.get("code00001"));
        Assert.assertEquals("isMember", inverseMap.get("true"));
    }

    class Person {
        private String name;
        private Integer age;

        public Person(String name, Integer age) {
            this.name = name;
            this.age = age;
        }

        ;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }
    }
}
