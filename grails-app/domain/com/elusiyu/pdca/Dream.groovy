package com.elusiyu.pdca

class Dream {


    static constraints = {
        dream_hope_true_date nullable: true;
        dream_come_true_date nullable: true;
        tag_quantity nullable: true;
        current_quantity nullable: true;

        dream_desc nullable: true;
    }

    //TODO 这个类线上提交无法正确插入到数据库
    Integer dream_no; //梦想编号
    String dream_name
    String u_id;

    Date dream_hope_true_date //目标期望达成时间
    Date dream_come_true_date //目标真实达成时间
    Integer tag_quantity //目标的量化数值
    Integer current_quantity //当前实现的数值

    String dream_desc //为梦想描述


    Date dateCreated;
    Date lastUpdated;
}
