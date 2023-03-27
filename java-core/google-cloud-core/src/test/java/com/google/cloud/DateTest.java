/*
 * Copyright 2017 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.cloud;

import static com.google.common.testing.SerializableTester.reserializeAndAssert;
import static com.google.common.truth.Truth.assertThat;

import com.google.common.testing.EqualsTester;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/** Unit tests for {@link Date}. */
@RunWith(JUnit4.class)
public class DateTest {

  private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

  @Test
  public void parseDate() {
    Date date = Date.parseDate("2016-09-18");
    assertThat(date.getYear()).isEqualTo(2016);
    assertThat(date.getMonth()).isEqualTo(9);
    assertThat(date.getDayOfMonth()).isEqualTo(18);
  }

  @Test
  public void testToString() {
    Date date = Date.fromYearMonthDay(10, 9, 5);
    assertThat(date.toString()).isEqualTo("0010-09-05");
    date = Date.fromYearMonthDay(2016, 12, 31);
    assertThat(date.toString()).isEqualTo("2016-12-31");
    date = Date.fromYearMonthDay(1, 1, 1);
    assertThat(date.toString()).isEqualTo("0001-01-01");
  }

  @Test
  public void equalAndHashCode() {
    Date d1 = Date.fromYearMonthDay(2016, 9, 18);
    Date d2 = Date.fromYearMonthDay(2016, 9, 18);
    Date d3 = Date.fromYearMonthDay(2016, 9, 19);
    EqualsTester tester = new EqualsTester();
    tester.addEqualityGroup(d1, d2);
    tester.addEqualityGroup(d3);
  }

  @Test
  public void validOrdering() {
    Date d1 = Date.fromYearMonthDay(2016, 9, 18);
    Date d2 = Date.fromYearMonthDay(2016, 9, 19);
    Date d3 = Date.fromYearMonthDay(2016, 9, 20);
    Date d4 = Date.fromYearMonthDay(2016, 10, 1);
    Date d5 = Date.fromYearMonthDay(2017, 1, 1);
    assertDescending(d5, d4, d3, d2, d1);
  }

  @Test
  public void serialization() {
    reserializeAndAssert(Date.fromYearMonthDay(2017, 4, 20));
  }

  @Test
  public void testToJavaUtilDate() throws ParseException {
    Date gcDate = Date.parseDate("2016-09-18");
    java.util.Date juDate1 = SIMPLE_DATE_FORMAT.parse("2016-09-18");
    java.util.Date juDate2 = Date.toJavaUtilDate(gcDate);
    assertThat(juDate1).isEqualTo(juDate2);
  }

  @Test
  public void testFromJavaUtilDate() throws ParseException {
    java.util.Date juDate = SIMPLE_DATE_FORMAT.parse("2016-09-18");
    Date gcDate = Date.fromJavaUtilDate(juDate);
    assertThat(gcDate.getYear()).isEqualTo(2016);
    assertThat(gcDate.getMonth()).isEqualTo(9);
    assertThat(gcDate.getDayOfMonth()).isEqualTo(18);
  }

  private void assertDescending(Date... dates) {
    for (int i = 0; i < dates.length - 1; i++) {
      assertThat(dates[i]).isEquivalentAccordingToCompareTo(dates[i]);
      for (int j = i + 1; j < dates.length; j++) {
        assertThat(dates[i]).isGreaterThan(dates[j]);
      }
    }
  }
}
