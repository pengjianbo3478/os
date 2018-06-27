package com.gyf.os.common;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
public class PoSplit {
	private final double splitAmt = 1000.00;
	private final double start = 941.00;
	private final double end = 1000.00;
	private List<Double> result = new ArrayList<Double>();
	private double totalAmt = 0.00;

	public PoSplit(double totalAmt) {
		this.totalAmt = totalAmt;
	}

	private double getListSum(List<Double> list) {
		DoubleSummaryStatistics stats = list.stream().mapToDouble((m) -> m).summaryStatistics();
		return stats.getSum();
	}

	private List<Double> getList(long len) {
		List<Double> result = new ArrayList<Double>();
		for (int j = 0; j < len; j++) {
			double g = ThreadLocalRandom.current().nextDouble(this.start, this.end);
			double i = new BigDecimal(g).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			result.add(i);
		}
		return result;
	}

	private void add() {
		if (this.totalAmt <= this.splitAmt) {
			double n = new BigDecimal(totalAmt).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			result.add(n);
			this.totalAmt = 0.00;
		}
		long len = (long) (totalAmt / splitAmt);
		List<Double> list = this.getList(len);
		this.result.addAll(list);
		this.totalAmt = this.totalAmt - this.getListSum(list);
	}

	public List<Double> split() {
		while (totalAmt != 0) {
			this.add();
		}
		return result;
	}

	public static void main(String[] args) {
		PoSplit s = new PoSplit(5999);
		List<Double> list = s.split();
		for (Double item : list) {
			System.out.println(item);
		}
		DoubleSummaryStatistics stats = list.stream().mapToDouble((m) -> m).summaryStatistics();
		System.out.println(stats.getSum());
	}
}
