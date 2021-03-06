/**
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2012-2017 the original author or authors.
 */
package org.assertj.core.api.offsettime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.assertj.core.util.FailureMessages.actualIsNull;

import java.time.OffsetTime;
import java.time.ZoneOffset;

import org.junit.Test;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

/**
 * @author Paweł Stawicki
 * @author Joel Costigliola
 * @author Marcin Zajączkowski
 */
@RunWith(Theories.class)
public class OffsetTimeAssert_isAfterOrEqualTo_Test extends OffsetTimeAssertBaseTest {

  @Theory
  public void test_isAfterOrEqual_assertion(OffsetTime referenceTime, OffsetTime timeBefore, OffsetTime timeEqual,
                                            OffsetTime timeAfter) {
    // GIVEN
    testAssumptions(referenceTime, timeBefore, timeEqual, timeAfter);
    // WHEN
    assertThat(timeAfter).isAfterOrEqualTo(referenceTime);
    assertThat(timeAfter).isAfterOrEqualTo(referenceTime.toString());
    assertThat(timeEqual).isAfterOrEqualTo(referenceTime);
    assertThat(timeEqual).isAfterOrEqualTo(referenceTime.toString());
    // THEN
    verify_that_isAfterOrEqual_assertion_fails_and_throws_AssertionError(timeBefore, referenceTime);
  }

  @Test
  public void test_isAfterOrEqual_assertion_error_message() {
    thrown.expectAssertionError("%n" +
                                "Expecting:%n" +
                                "  <03:00:05Z>%n" +
                                "to be after or equals to:%n" +
                                "  <03:03:03Z>");
    assertThat(OffsetTime.of(3, 0, 5, 0, ZoneOffset.UTC)).isAfterOrEqualTo(OffsetTime.of(3, 3, 3, 0, ZoneOffset.UTC));
  }

  @Test
  public void should_fail_if_actual_is_null() {
    expectException(AssertionError.class, actualIsNull());
    OffsetTime actual = null;
    assertThat(actual).isAfterOrEqualTo(OffsetTime.now());
  }

  @Test
  public void should_fail_if_offsetTime_parameter_is_null() {
    expectException(IllegalArgumentException.class, "The OffsetTime to compare actual with should not be null");
    assertThat(OffsetTime.now()).isAfterOrEqualTo((OffsetTime) null);
  }

  @Test
  public void should_fail_if_offsetTime_as_string_parameter_is_null() {
    expectException(IllegalArgumentException.class,
                    "The String representing the OffsetTime to compare actual with should not be null");
    assertThat(OffsetTime.now()).isAfterOrEqualTo((String) null);
  }

  private static void verify_that_isAfterOrEqual_assertion_fails_and_throws_AssertionError(OffsetTime timeToCheck,
                                                                                           OffsetTime reference) {
    try {
      assertThat(timeToCheck).isAfterOrEqualTo(reference);
    } catch (AssertionError e) {
      // AssertionError was expected, test same assertion with String based parameter
      try {
        assertThat(timeToCheck).isAfterOrEqualTo(reference.toString());
      } catch (AssertionError e2) {
        // AssertionError was expected (again)
        return;
      }
    }
    fail("Should have thrown AssertionError");
  }

}
