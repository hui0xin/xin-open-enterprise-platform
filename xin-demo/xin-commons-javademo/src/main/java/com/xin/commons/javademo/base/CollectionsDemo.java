package com.xin.commons.javademo.base;

import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CollectionsDemo {

    /**
     * int binarySearch(List list, Object key)//对List进行二分查找，返回索引，注意List必须是有序的
     * int max(Collection coll)//根据元素的自然顺序，返回最大的元素。 类比int min(Collection coll)
     * int max(Collection coll, Comparator c)//根据定制排序，返回最大元素，排序规则由Comparatator类控制。类比int min(Collection coll, Comparator c)
     * void fill(List list, Object obj)//用指定的元素代替指定list中的所有元素
     * int frequency(Collection c, Object o)//统计元素出现次数
     * int indexOfSubList(List list, List target)//统计target在list中第一次出现的索引，找不到则返回-1，类比int lastIndexOfSubList(List source, list target)
     * boolean replaceAll(List list, Object oldVal, Object newVal)//用新元素替换旧元素
     */
    public static void main(String[] args) {
        List<String> list = getList();
        System.out.println("start："+list);
        Collections.reverse(list); //反转
        System.out.println("reverse："+list);

        Collections.shuffle(list); //随机排序
        System.out.println("shuffle："+list);

        Collections.sort(list); //按自然排序的升序排序
        System.out.println("sort："+list);

        Collections.swap(list,0,5); //交换两个索引位置的元素
        System.out.println("swap："+list);

        //旋转。
        //当distance为正数时，将list后distance个元素整体移到前面。
        //当distance为负数时，将 list的前distance个元素整体移到后面
        Collections.rotate(list,2);
        System.out.println("rotate1："+list);
        Collections.rotate(list,-2);
        System.out.println("rotate2："+list);

        List<Person> personList = getPeronList();
        System.out.println("personList："+personList);

        Collections.sort(personList, Comparator.comparing(Person::getAge)); //定制排序，由Comparator控制排序逻辑
        System.out.println("Comparator-sort："+personList);

//        Collections.sort(personList, new Comparator<Person>() {
//            public int compare(Person o1, Person o2) {
//                return o2.getAge()-o1.getAge();
//            }
//        });

    }

    public static List<String> getList() {
        List list = new ArrayList();
        for(int i=0 ;i<10 ;i++){
            list.add("list"+ (int)(1+Math.random()*(100-1+1)));
        }
        return list;
    }

    public static List<Person> getPeronList() {
        List<Person> list = new ArrayList();
        for(int i=0 ;i<10 ;i++){
            Person person = new Person();
            int mm = (int)(1+Math.random()*(100-1+1));
            person.setAge(mm);
            person.setName("陌生人"+mm);
            list.add(person);
        }
        return list;
    }
}

@Data
class Person {
    private String name;
    private Integer age;
}
