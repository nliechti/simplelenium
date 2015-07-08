/**
 * Copyright (C) 2013-2015 all@code-story.net
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */
package net.codestory.simplelenium.configuration;

import com.google.common.base.Strings;
import net.codestory.simplelenium.driver.Browser;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by kag on 07/07/15.
 */
public class Configuration {

  private static Configuration instance = null;
  private Browser targetBrowser = null;
//  private Set<Browser> targetBrowsers = null;

  /**
   * Private constructor for singleton
   */
  private Configuration () {
    super();
  }

  /**
   * Access the configuration instance
   * @return the instance representing the test's configuration
   */
  public static Configuration getInstance () {
    if (instance == null) {
      synchronized (Configuration.class) {
        if (instance == null) {
          Set<Map.Entry<Object, Object>> entries = new HashSet<Map.Entry<Object,Object>>(System.getProperties().entrySet());
          for (Map.Entry<Object, Object> entry : entries) {
            Object value = entry.getValue();
            if (value instanceof String) {
              String strValue = (String)value;
              if (!Strings.isNullOrEmpty(strValue) && strValue.startsWith("${")) {
                // looks like a JVM argument passed via ant which was not filled -
                // remove it from the properties such that a meaningful default value
                // can be used
                System.clearProperty((String)entry.getKey());
              }
            }
          }
          instance = new Configuration();
        }
      }
    }
    return instance;
  }

  /**
   * Clears the configuration instance to allow a full
   * rebuild on the next access to {@link Configuration#getInstance()}
   */
  public static void clearConfiguration () {
    instance = null;
  }

  /**
   * @return the browser which should be used in the test run given
   * the program's parameter. If no browser is specified,
   * PhantomJS is used as the only standard browser
   */
  public Browser getTargetBrowser() {
    if (this.targetBrowser == null) {
      String browserProptery = System.getProperty("browser", "phantom_js");
      this.targetBrowser = Browser.valueOf(browserProptery.trim().toUpperCase());
    }
    return this.targetBrowser;
  }
}