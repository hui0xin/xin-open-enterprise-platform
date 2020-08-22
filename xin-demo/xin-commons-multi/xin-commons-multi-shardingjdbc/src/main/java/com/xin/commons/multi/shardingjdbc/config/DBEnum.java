package com.xin.commons.multi.shardingjdbc.config;

public enum DBEnum {

    MASTERDB("0","主库",1),
    SLAVEDB("1","从库",1),
    HISTORYDB("2","归档库",1);

    private String server;
    private Integer symbol;
    private String name;

    DBEnum(String server,String name,Integer symbol){
       this.server=server;
       this.symbol=symbol;
       this.name=name;
    }

    public String getServer(){
        return server;
    }
    public Integer getSymbol(){
        return symbol;
    }

}