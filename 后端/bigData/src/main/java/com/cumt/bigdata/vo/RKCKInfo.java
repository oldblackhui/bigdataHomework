package com.cumt.bigdata.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: ahui
 * @date: 2021/12/26 - 20:40
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RKCKInfo {
    private static final long serialVersionUID = 1L;
    private String RKMC;
    private String CKMC;
    private String RKlng;
    private String RKlat;
    private String CKlng;
    private String CKlat;
}
