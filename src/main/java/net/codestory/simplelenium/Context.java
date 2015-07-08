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
package net.codestory.simplelenium;

import net.codestory.simplelenium.driver.Browser;
import net.codestory.simplelenium.driver.DriverInitializer;
import net.codestory.simplelenium.driver.SeleniumDriver;
import net.codestory.simplelenium.driver.ThreadSafeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.function.Supplier;

/**
 * Created by kag on 08/07/15.
 */
public class Context {

  private static ThreadLocal<SeleniumDriver> perThreadDriver = new ThreadLocal<SeleniumDriver>();

  private static final ThreadLocal<Browser> currentBrowser = new ThreadLocal<>();

  public static SeleniumDriver getCurrentWebDriver() {
    return perThreadDriver.get();
  }

  public static void setCurrentWebDriver(RemoteWebDriver driver) {
    perThreadDriver.set(ThreadSafeDriver.makeThreadSafe(driver));
  }

  public static Browser getCurrentBrowser() {
    return currentBrowser.get();
  }

  public static void setCurrentBrowser(Browser browser) {
    currentBrowser.set(browser);
  }
}