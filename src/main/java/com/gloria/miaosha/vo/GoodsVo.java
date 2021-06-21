package com.gloria.miaosha.vo;

import java.util.Date;

import com.gloria.miaosha.domain.Goods;
/**
 * 创建GoodsDao
 * 这里查数据库的时候，不只是查找的商品的信息，我们同时想把商品的秒杀信息也一起查出来，
 * 但是这两个不同数据在两个表里面，
 * 因此封装一个GoodsVo，将两张表的数据封装到一起。
 */
public class GoodsVo extends Goods{
	private Integer stockCount;
	private Date startDate;
	private Date endDate;
	private Double miaoshaPrice;
	
	
	public Double getMiaoshaPrice() {
		return miaoshaPrice;
	}
	public void setMiaoshaPrice(Double miaoshaPrice) {
		this.miaoshaPrice = miaoshaPrice;
	}
	public Integer getStockCount() {
		return stockCount;
	}
	public void setStockCount(Integer stockCount) {
		this.stockCount = stockCount;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
}
