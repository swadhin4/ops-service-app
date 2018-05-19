/* Copyright (C) 2014 Generix Software Labs, Inc. All rights reserved */
package com.ops.app.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.time.DateUtils;


public final class RandomUtils {

	private static int CURRENT_YEAR = Calendar.getInstance().get(Calendar.YEAR);

	private RandomUtils() {};

	/**
	 * Return Random Integer between start and end integers
	 * 
	 * @param start
	 * @param end
	 * @return int
	 */
	public static int randomIntgerBetween(final int start, final int end) {
		return start + (int) Math.round(Math.random() * (end - start));
	}

	/**
	 * Return Random Double between start and end double
	 * 
	 * @param start
	 * @param end
	 * @return double
	 */
	public static double randomDoubleBetween(final double start, final double end) {
		return start + Math.round((Math.random() * (end - start)));
	}

	/**
	 * <p>
	 * Returns the next pseudorandom, uniformly distributed int value from the
	 * Math.random() sequence.
	 * </p>
	 * <b>N.B. All values are >= 0.<b>
	 * 
	 * @return the random int
	 */
	public static int randomIntger() {
		return org.apache.commons.lang.math.RandomUtils.nextInt();
	}

	/**
	 * <p>
	 * Returns a pseudorandom, uniformly distributed int value between
	 * <code>0</code> (inclusive) and the specified value (exclusive), from the
	 * Math.random() sequence.
	 * </p>
	 * 
	 * @param n
	 *          the specified exclusive max-value
	 * @return the random int
	 */
	public static int randomIntger(final int number) {
		return org.apache.commons.lang.math.RandomUtils.nextInt(number);
	}

	/**
	 * <p>
	 * Returns the next pseudorandom, uniformly distributed float value between
	 * <code>0.0</code> and <code>1.0</code> from the Math.random() sequence.
	 * </p>
	 * 
	 * @return the random double
	 */
	public static double randomDouble() {
		return org.apache.commons.lang.math.RandomUtils.nextDouble();
	}

	/**
	 * <p>
	 * Returns the next pseudorandom, uniformly distributed boolean value from the
	 * Math.random() sequence.
	 * </p>
	 * 
	 * @return the random boolean
	 */
	public static boolean randomBoolean() {
		return org.apache.commons.lang.math.RandomUtils.nextBoolean();
	}

	/**
	 * <p>
	 * Creates a random string whose length is the number of characters specified.
	 * </p>
	 * 
	 * <p>
	 * Characters will be chosen from the set of alphabetic characters.
	 * </p>
	 * 
	 * @param count
	 *          the length of random string to create
	 * @return the random string
	 */
	public static String randomAlphabetic(final int count) {
		return RandomStringUtils.randomAlphabetic(count);
	}

	/**
	 * <p>
	 * Creates a random string whose length is the number of characters specified.
	 * </p>
	 * 
	 * <p>
	 * Characters will be chosen from the set of alpha-numeric characters.
	 * </p>
	 * 
	 * @param count
	 *          the length of random string to create
	 * @return the random string
	 */
	public static String randomAlphanumeric(final int count) {
		return RandomStringUtils.randomAlphanumeric(count);
	}

	/**
	 * <p>
	 * Creates a random string whose length is the number of characters specified.
	 * </p>
	 * 
	 * <p>
	 * Characters will be chosen from the set of characters specified.
	 * </p>
	 * 
	 * @param count
	 *          the length of random string to create
	 * @param chars
	 *          the String containing the set of characters to use, may be null
	 * @return the random string
	 * @throws IllegalArgumentException
	 *           if <code>count</code> &lt; 0.
	 */
	public static String randomStringFromRange(final int count, final String patternString) {
		return RandomStringUtils.random(count, patternString);
	}

	/**
	 * Return's a random date from 1990 to current year
	 * 
	 * @return Date
	 */
	public static Date randomDate() {
		GregorianCalendar gc = new GregorianCalendar();
		int year = randomIntgerBetween(1900, CURRENT_YEAR);
		gc.set(GregorianCalendar.YEAR, year);
		int dayOfYear = randomIntgerBetween(1, gc.getActualMaximum(GregorianCalendar.DAY_OF_YEAR));
		gc.set(GregorianCalendar.DAY_OF_YEAR, dayOfYear);
		return getDate(gc.get(GregorianCalendar.YEAR) + "-" + gc.get(GregorianCalendar.MONTH) + "-"
				+ gc.get(GregorianCalendar.DAY_OF_MONTH));
	}

	/**
	 * Return's a random date from start year to end year
	 * 
	 * @param start
	 * @param end
	 * @return Date
	 */
	public static Date randomDateBetween(final int start, final int end) {
		GregorianCalendar gc = new GregorianCalendar();
		int year = randomIntgerBetween(start, end);
		gc.set(GregorianCalendar.YEAR, year);
		int dayOfYear = randomIntgerBetween(1, gc.getActualMaximum(GregorianCalendar.DAY_OF_YEAR));
		gc.set(GregorianCalendar.DAY_OF_YEAR, dayOfYear);
		return getDate(gc.get(GregorianCalendar.YEAR) + "-" + gc.get(GregorianCalendar.MONTH) + "-"
				+ gc.get(GregorianCalendar.DAY_OF_MONTH));
	}

	/**
	 * Random Date after given date, if null then current date is used.
	 * 
	 * @param date
	 * @param count
	 *          No days to add, randomly from 1 to count
	 * 
	 * @return date
	 */

	public static Date randomDateAfter(final Date date, final int count) {
		int nextDay = randomIntger(count);
		if (date == null) {
			return DateUtils.addDays(new Date(), nextDay);
		}
		return DateUtils.addDays(date, nextDay);
	}

	/**
	 * Random Date before given date, if null then current date is used.
	 * 
	 * @param date
	 * @param count
	 *          No days to subtract, randomly from 1 to count
	 * 
	 * @return date
	 */
	public static Date randomDateBefore(final Date date, final int count) {
		int nextDay = randomIntger(count);
		if (date == null) {
			return DateUtils.addDays(new Date(), -nextDay);
		}
		return DateUtils.addDays(date, -nextDay);
	}

	private static Date getDate(final String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
