package com.xin.commons.multi.mongodb.model;

import lombok.Data;
import java.io.Serializable;

@Data
public class User implements Serializable {
        private static final long serialVersionUID = -3258839839160856613L;
        private String  id;
        private String userName;
        private String passWord;

}
