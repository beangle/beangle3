/*
 * Beangle, Agile Development Scaffold and Toolkits.
 *
 * Copyright © 2005, The Beangle Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.security.util;

import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SessionDaemon extends TimerTask {
  public static void start(String name,int intervalSeconds, Task... tasks) {
    System.out.println("Starting "+name+" Daemon, Running within every " + intervalSeconds  + " seconds");
    SessionDaemon daemon = new SessionDaemon(Arrays.asList(tasks));
    new Timer(name+" Daemon", true).schedule(daemon,
        new java.util.Date(System.currentTimeMillis()), intervalSeconds*1000);
  }

  private final List<Task> tasks;

  public SessionDaemon(List<Task> tasks) {
    super();
    this.tasks = tasks;
  }

  @Override
  public void run() {
    for (Task task : tasks) {
      try {
        task.run();
      }catch(Exception e){
        e.printStackTrace();
      }
    }
  }

}
