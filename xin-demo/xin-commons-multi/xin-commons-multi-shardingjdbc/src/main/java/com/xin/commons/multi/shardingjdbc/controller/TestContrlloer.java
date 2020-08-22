package com.xin.commons.multi.shardingjdbc.controller;

import com.xin.commons.multi.shardingjdbc.bean.PerpetualCoin;
import com.xin.commons.multi.shardingjdbc.service.PerpetualCoinService;
import com.xin.commons.support.response.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @ClassName: TestContrlloer
 * @Description:
 * @author: xin
 * @date: 2018/6/4下午4:56
 */

@RestController
@RequestMapping("/test")
@Slf4j
public class TestContrlloer {

    @Autowired
    private PerpetualCoinService perpetualCoinService;

    @GetMapping(value = "/get")

    public ResponseResult get() {

        List<PerpetualCoin> list = perpetualCoinService.getPerpetualCoinList();

        return ResponseResult.success(list);
    }
    @GetMapping(value = "/save")
    public ResponseResult save() {

        PerpetualCoin model = new  PerpetualCoin();
        model.setId(90L);
        model.setSymbol(90);
        model.setSymbolSum(111111L);
        model.setSymbolName("btc");
        model.setIndexMarketFrom(12);
        model.setSeqencing(1);
        model.setConfigType(1);

        model.setSymbolMark("ddddd");
        model.setMarketFrom("12");
        model.setFuturesMarketFrom("dddd");
        model.setPricePoint(1);
        model.setCreatedDate(new Date());
        model.setModifyDate(new Date());
        model.setInvalidSwitch(Byte.parseByte("1"));

        int result = perpetualCoinService.savePerpetualCoin(model);
        System.out.println("______________________save__success!!!!!_______________________");
        return ResponseResult.success(result);
    }
    @GetMapping(value = "/update/{id}")
    public ResponseResult update(@PathVariable("id") long id) {
        long id2 = id;
        System.out.println("****************************"+id2+"");
        PerpetualCoin model = perpetualCoinService.getPerpetualCoinById(id);
        model.setModifyDate(new Date());
        int restult = perpetualCoinService.updatePerpetualCoin(model);
        System.out.println("_____________________update___success!!!!!_______________________");
        return ResponseResult.success(model);
    }

    @GetMapping(value = "/get/{id}")
    public ResponseResult getByid(@PathVariable("id") long id) {
        long id2 = id;
        System.out.println("****************************"+id2+"");
        PerpetualCoin model = perpetualCoinService.getPerpetualCoinById(id);
        return ResponseResult.success(model);
    }

    @GetMapping(value = "/delete/{id}")
    public ResponseResult delete(@PathVariable("id") long id) {
        int delete = perpetualCoinService.deletePerpetualCoin(id);
        System.out.println("_____________________delete___success!!!!!_______________________");
        return ResponseResult.success(delete);
    }

    @GetMapping(value = "/transcation")
    public ResponseResult transcation() {

        PerpetualCoin model = new  PerpetualCoin();
        model.setId(60L);
        model.setSymbol(90);
        model.setSymbolSum(1111L);
        model.setSymbolName("btc");
        model.setIndexMarketFrom(12);
        model.setSeqencing(1);
        model.setConfigType(1);

        model.setSymbolMark("bbbb");
        model.setMarketFrom("12");
        model.setFuturesMarketFrom("bbbbb");
        model.setPricePoint(1);
        model.setCreatedDate(new Date());
        model.setModifyDate(new Date());
        model.setInvalidSwitch(Byte.parseByte("1"));

        int result = perpetualCoinService.transactionPerpetualCoin(model,60L);

        System.out.println("-----------------transcation!!!!!_______________________");
        return ResponseResult.success(result);
    }

}