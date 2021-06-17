package com.xin.commons.datadog;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Metrics {

	/**
	 * 耗时监控指标
	 */
	public enum Time {

		/**
		 * 抓取指数耗时
		 */
		INDEX_FETCH,

		/**
		 * 调用外部接口的时间特性统计
		 */
		API_CLIENT_TIME,

		/**
		 * API sync处理时间
		 */
		SPOTS_PROCESS_SYNC, CONTRACTS_PROCESS_SYNC, OPTIONS_PROCESS_SYNC,

		/**
		 * API Async处理时间
		 */
		SPOTS_PROCESS_ASYNC, CONTRACTS_PROCESS_ASYNC, OPTIONS_PROCESS_ASYNC,

		/**
		 * MatchDetails Batch落库耗时
		 */
		SPOTS_FLUSH_MATCH_DETAILS, CONTRACTS_FLUSH_MATCH_DETAILS, OPTIONS_FLUSH_MATCH_DETAILS,

		/**
		 * 从 Trading 到 MatchDetails Batch落库耗时
		 */
		FLUSH_MATCH_DETAILS_FROM_TRADING,

		/**
		 * Clearing Batch落库耗时
		 */
		SPOTS_FLUSH_CLEARINGS, CONTRACTS_FLUSH_CLEARINGS, OPTIONS_FLUSH_CLEARINGS,

		/**
		 * Order Batch落库耗时
		 */
		SPOTS_FLUSH_ORDERS, CONTRACTS_FLUSH_ORDERS, OPTIONS_FLUSH_ORDERS,


		/**
		 * 生成快照耗时
		 */
		SPOTS_DUMP_SNAPSHOT, CONTRACTS_DUMP_SNAPSHOT, OPTIONS_DUMP_SNAPSHOT,

		/**
		 * WS广播耗时 / 针对单个用户Push耗时
		 */
		WS_BROADCAST, WS_PUSH,

		/**
		 * Trading发送消息耗时
		 */
		SPOTS_FLUSH_CHANGES, CONTRACTS_FLUSH_CHANGES, OPTIONS_FLUSH_CHANGES,

		/**
		 * Trading处理单个Event耗时
		 */
		SPOTS_PROCESS_EVENT, CONTRACTS_PROCESS_EVENT, OPTIONS_PROCESS_EVENT,

		/**
		 * 批量处理Event耗时
		 */
		SPOTS_PROCESS_EVENTS, CONTRACTS_PROCESS_EVENTS, OPTIONS_PROCESS_EVENTS,

		/**
		 * update prices
		 */
		UPDATE_PRICES,

		/**
		 * update ticker
		 */
		UPDATE_TICKER,

		/**
		 * Sequence处理Batch落库耗时
		 */
		SPOTS_FLUSH_EVENTS, CONTRACTS_FLUSH_EVENTS, OPTIONS_FLUSH_EVENTS, FLUSH_EVENTS;

		Time() {
			this.value = normalize(this.name());
		}

		final String value;
	}

	/**
	 * 计数器指标监控
	 */
	public enum Count {
		/**
		 * 获取指数成功
		 */
		INDEX_FETCH_OK,

		/**
		 * 获取指数失败
		 */
		INDEX_FETCH_FAILED,

		/**
		 * Clearing落库
		 */
		FLUSH_CLEARING,

		/**
		 * Order落库
		 */
		FLUSH_ORDER,

		/**
		 * 已发送消息
		 */
		MESSAGE_SENT,

		/**
		 * 已接收消息
		 */
		MESSAGE_RECEIVED,

		/**
		 * 已生成通知
		 */
		NOTIFY_GENERATED,

		/**
		 * 已发送通知
		 */
		NOTIFY_SENT,

		/**
		 * 已发送通知
		 */
		ADAPTER_ORDERBOOK,

		/**
		 * 行情更新TICK数量
		 */
		QUOTATION_TICK_UPDATE,

		/**
		 * 行情更新BAR数量
		 */
		QUOTATION_BAR_UPDATE,

		/**
		 * 行情落库BAR数量
		 */
		QUOTATION_BAR_PERSIS,

		/**
		 * 行情更price数量
		 */
		QUOTATION_PRICE_UPDATE,

		/**
		 * 行情更ticker数量
		 */
		QUOTATION_TICKER_UPDATE,

		/**
		 * WS新连接
		 */
		WS_NEW,

		/**
		 * WS推送消息数量
		 */
		WS_PUSH,

		/**
		 * WS客户端超时
		 */
		WS_TIMEOUT_CLOSE,

		/**
		 * WS TOKEN验证错误
		 */
		WS_TOKEN_FAILED,

		/**
		 * WS广播消息数量
		 */
		WS_BROADCAST,

		/**
		 * 超过限制频次总额的量
		 */
		RATELIMIT_EXCEED_TOTAL_ORDER,

		/**
		 * 超过限制频次单用户的量
		 */
		RATELIMIT_EXCEED_USER_ORDER,


		;

		Count() {
			this.value = normalize(this.name());
		}

		final String value;
	}

	/**
	 * 变动数值监控指标（例如账户余额等）
	 */
	public enum Gauge {

		/**
		 * 当前处理的SequenceId
		 */
		SEQUENCE_ID,

		/**
		 * 等待执行的任务数量
		 */
		TASK_PENDING,

		/**
		 * 用户爆仓仓位
		 */
		LIQUIDATE_POSITION,

		/**
		 * 用户穿仓转移仓位
		 */
		LIQUIDATE_TRANSFER,

		/**
		 * 用户穿仓损失的保证金
		 */
		LIQUIDATE_OVERLOSS,

		/**
		 * 系统持仓统计
		 */
		POSITION_STATISTICS,

		/**
		 * 系统用户可用余额
		 */
		SYSTEMUSER_AVAILABLE,

		/**
		 * 系统用户冻结余额
		 */
		SYSTEMUSER_SPOTS_FROZEN,

		/**
		 * 风险基金仓位
		 */
		INSURANCE_QUANTITY,
		/**
		 * 频次限制中，取样的窗口累计量
		 */
		RATELIMIT_TOTAL_ORDER,

		/**
		 * 等待处理的transfer-in/out消息
		 */
		TRANSFER_PENDING,

		/**
		 * 交易所钱包余额
		 */
		VENDOR_BALANCE,

		/**
		 * WS连接数量
		 */
		WS_CONNECTION,
		/**
		 * 风险基金余额
		 */
		INSURANCE_BALANCE,
		/**
		 * 系统总资产余额
		 */
		TRANSFER_HOLDER_BALANCE,
		/**
		 * 用户总负债
		 */
		USER_TOTAL_DEBT,
		/**
		 * 用户总未实现盈亏
		 */
		USER_TOTAL_UNREALIZED_PNL,
		/**
		 * 用户总未实现亏损
		 */
		USER_TOTAL_UNREALIZED_L,
		/**
		 * 用户单向总持仓
		 */
		USER_TOTAL_QUANTITY_LONG,
		/**
		 * 用户单向最大持仓
		 */
		USER_MAX_QUANTITY,
		/**
		 * 用户真实杠杆倍数
		 */
		USER_REAL_LEVERAGE,
		/**
		 * 指数价格
		 */
		MARKET_INDEX;

		Gauge() {
			this.value = normalize(this.name());
		}

		final String value;
	}

	private static String normalize(String upperName) {
		String s = upperName.toLowerCase();
		String[] ss = s.split("\\-", 2);
		if (ss.length == 2) {
			return ss[0] + "." + ss[1];
		}
		return s;
	}

}
