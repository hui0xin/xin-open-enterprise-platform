package com.xin.commons.multi.shardingjdbc.service.impl;

import com.xin.commons.multi.shardingjdbc.bean.PerpetualCoin;
import com.xin.commons.multi.shardingjdbc.mapper.PerpetualCoinDao;
import com.xin.commons.multi.shardingjdbc.service.PerpetualCoinService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @ClassName: PerpetualCoinServiceImpl
 * @Description:
 * @author: xin
 * @date: 2018/6/4下午3:40
 */
@Slf4j
@Service
public class PerpetualCoinServiceImpl implements PerpetualCoinService {

    @Autowired
    private PerpetualCoinDao perpetualCoinDao;

    @Override
    //@DataSource(DBConfig.masterDataSource)
    public List<PerpetualCoin> getPerpetualCoinList() {

        return perpetualCoinDao.getPerpetualCoinList();
    }
   // @DataSource(DBConfig.slaveDataSource)
    @Override
    public PerpetualCoin getPerpetualCoinById(Long id) {

        return perpetualCoinDao.getPerpetualCoinById(id);
    }
    //@DataSource(DBConfig.historyDataSource)
    @Override
    public int savePerpetualCoin(PerpetualCoin perpetualCoin) {

        return perpetualCoinDao.insertPerpetualCoin(perpetualCoin);
    }
    //@DataSource(DBConfig.masterDataSource)
    @Override
    public int updatePerpetualCoin(PerpetualCoin perpetualCoin) {

        return perpetualCoinDao.updatePerpetualCoin(perpetualCoin);
    }
    //@DataSource(DBConfig.slaveDataSource)
    @Override
    public int deletePerpetualCoin(Long id) {

        return perpetualCoinDao.deletePerpetualCoin(id);
    }
    //@DataSource(DBConfig.historyDataSource)
    @Override
    @Transactional
    public int transactionPerpetualCoin(PerpetualCoin perpetualCoin,Long id) {

//        //插入一条
//        int restult = perpetualCoinDao.insertPerpetualCoin(perpetualCoin);
//        //查出来
//        PerpetualCoin perpetualCoin2 = perpetualCoinDao.getPerpetualCoinById(id);
//
//        //更新一条
//        int mmmm = 8888888;
//        perpetualCoin2.setModifyDate(new Date());
//        perpetualCoin2.setSymbol(993455667*mmmm);
//        perpetualCoin2.setSymbolName("rtyuiofghjklfghjklfghjiklortyuiopdfghjkldfghjkd" +
//                "yuiopghjklfghjkghjklfghjfghjdfghjkdfghjkdfghjdfghjfghjkdfghjklsdfghjkdfghjksdfghjksdfghj");
//        int restult2 = perpetualCoinDao.updatePerpetualCoin(perpetualCoin2);
//        //删除一条
//        int restult3 = perpetualCoinDao.deletePerpetualCoin(id);

        return 1;
    }


}
