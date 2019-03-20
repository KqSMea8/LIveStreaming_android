package com.fanwe.live.utils;

import java.math.BigDecimal;
import java.text.NumberFormat;

import android.text.TextUtils;

import com.fanwe.library.utils.SDDateUtil;
import com.fanwe.library.utils.SDNumberUtil;
import com.fanwe.library.utils.SDTypeParseUtil;

public class SDFormatUtil
{

	public static String formatMoneyChina(String money)
	{
		if (!TextUtils.isEmpty(money))
		{
			String moneyRound = String.valueOf(SDNumberUtil.scaleHalfUp(SDTypeParseUtil.getDouble(money), 2));
			money = new BigDecimal(moneyRound).toPlainString();
			if (money.contains("."))
			{
				int decimalIndex = money.indexOf(".");
				String decimalPart = money.substring(decimalIndex + 1);
				if ("0".equals(decimalPart) || "00".equals(decimalPart))
				{
					money = money.substring(0, decimalIndex);
				}
			}
			return "¥" + money;
		} else
		{
			return "¥0";
		}
	}

	public static String formatMoneyChina(double money)
	{
		return formatMoneyChina(String.valueOf(money));
	}

	public static String formatNumberString(String formatString, int number)
	{
		if (formatString != null && formatString.length() > 0)
		{
			NumberFormat format = NumberFormat.getNumberInstance();
			format.setMaximumFractionDigits(number);
			try
			{
				return format.format(Double.valueOf(formatString));
			} catch (Exception e)
			{
				return null;
			}

		} else
		{
			return formatString;
		}
	}

	public static String formatNumberDouble(double formatDouble, int number)
	{
		return formatNumberString(String.valueOf(formatDouble), number);
	}

	public static String formatDuring(long mss)
	{
		long days = SDDateUtil.getDuringDay(mss);
		long hours = SDDateUtil.getDuringHours(mss);
		long minutes = SDDateUtil.getDuringMinutes(mss);
		long seconds = SDDateUtil.getDuringSeconds(mss);
		StringBuilder sb = new StringBuilder();
		if (days > 0)
		{
			sb.append(days + "天");
		}
		if (hours > 0)
		{
			sb.append(hours + "小时");
		}
		if (minutes > 0)
		{
			sb.append(minutes + "分钟");
		}
		return sb.toString();
	}

}
