// SPDX-License-Identifier: BSD-3-Clause
// Copyright (c) 1999-2004 Brian Wellington (bwelling@xbill.org)

package org.xbill.DNS;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Configuration options for dnsjava.
 *
 * <p>Boolean options:
 *
 * <ul>
 *   <li>BINDTTL - Print TTLs in BIND format
 *   <li>multiline - Print records in multiline format<br>
 *   <li>noPrintIN - Don't print the class of a record if it's IN<br>
 * </ul>
 *
 * <p>Valued options:
 *
 * <ul>
 *   <li>tsigfudge=n - Sets the default TSIG fudge value (in seconds)
 *   <li>sig0validity=n - Sets the default SIG(0) validity period (in seconds)
 * </ul>
 *
 * @author Brian Wellington
 */
public final class Options {

  private static Map<String, String> table;

  static {
    try {
      refresh();
    } catch (SecurityException e) {
      // Ignore
    }
  }

  private Options() {}

  public static void refresh() {
    String s = System.getProperty("dnsjava.options");
    if (s != null) {
      StringTokenizer st = new StringTokenizer(s, ",");
      while (st.hasMoreTokens()) {
        String token = st.nextToken();
        int index = token.indexOf('=');
        if (index == -1) {
          set(token);
        } else {
          String option = token.substring(0, index);
          String value = token.substring(index + 1);
          set(option, value);
        }
      }
    }
  }

  /** Clears all defined options */
  public static void clear() {
    table = null;
  }

  /** Sets an option to {@code true}. */
  public static void set(String option) {
    if (table == null) {
      table = new HashMap<>();
    }
    table.put(option.toLowerCase(), "true");
  }

  /** Sets an option to the supplied value */
  public static void set(String option, String value) {
    if (table == null) {
      table = new HashMap<>();
    }
    table.put(option.toLowerCase(), value.toLowerCase());
  }

  /** Removes an option */
  public static void unset(String option) {
    if (table == null) {
      return;
    }
    table.remove(option.toLowerCase());
  }

  /** Checks if an option is defined */
  public static boolean check(String option) {
    if (table == null) {
      return false;
    }
    return table.get(option.toLowerCase()) != null;
  }

  /** Returns the value of an option */
  public static String value(String option) {
    if (table == null) {
      return null;
    }
    return table.get(option.toLowerCase());
  }

  /** Returns the value of an option as an integer, or -1 if not defined. */
  public static int intValue(String option) {
    String s = value(option);
    if (s != null) {
      try {
        int val = Integer.parseInt(s);
        if (val > 0) {
          return val;
        }
      } catch (NumberFormatException e) {
        // Ignore
      }
    }
    return -1;
  }

  static boolean multiline() {
    if (table == null) {
      return false;
    }

    return table.get("multiline") != null;
  }
}
