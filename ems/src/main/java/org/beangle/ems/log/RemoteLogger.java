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
package org.beangle.ems.log;

import org.beangle.commons.bean.Disposable;
import org.beangle.commons.bean.Initializing;
import org.beangle.commons.lang.Strings;
import org.beangle.commons.web.util.HttpUtils;
import org.beangle.ems.app.EmsApp;
import org.beangle.security.Securities;

import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class RemoteLogger implements Initializing, Disposable {
  String url;
  int queueSize = 512;
  private BlockingQueue<BusinessLogEvent> queue;
  private boolean started;
  private Worker worker;

  @Override
  public void destroy() {
    worker.interrupt();
  }

  @Override
  public void init() throws Exception {
    queue = new ArrayBlockingQueue<BusinessLogEvent>(queueSize);
    started = true;
    worker = new Worker(this);
    worker.setDaemon(true);
    worker.setName("Beangle async business logstore worker");
    worker.start();
  }

  public BusinessLogEvent newEvent(String summary) {
    BusinessLogEvent entry = new BusinessLogEvent();
    entry.appName = EmsApp.getName();
    entry.operator = Securities.getUsername();
    entry.operateAt = Instant.now();
    entry.level = Level.Info;
    entry.entry = Securities.getResource();
    var s = Securities.getSession();
    entry.agent = s.getAgent().getOs() + " " + s.getAgent().getName();
    entry.summary = summary;
    return entry;
  }

  public void publish(BusinessLogEvent event) {
    queue.offer(event);
  }

  public static class BusinessLogEvent {
    String appName;
    String operator;
    Instant operateAt;
    String summary;
    String details;
    String resources;
    String ip;
    String agent;
    String entry;
    Level level;

    public BusinessLogEvent from(String ip) {
      this.ip = ip;
      return this;
    }

    public BusinessLogEvent operateOn(String resources, String details) {
      this.resources = resources;
      this.details = details;
      return this;
    }

    public BusinessLogEvent operateOn(Object resources, Map<String, ?> params) {
      this.resources = resources.toString();
      var paramList = new ArrayList<String>();
      for (Map.Entry<String, ?> entry : params.entrySet()) {
        if (!entry.getKey().startsWith("_")) {
          String value = (entry.getKey().contains("password")) ? "*****" : entry.getValue().toString();
          paramList.add(entry.getKey() + " = " + value);
        }
      }
      Collections.sort(paramList);
      StringBuilder sb = new StringBuilder();
      for (String s : paramList) {
        sb.append(s).append("\n");
      }
      var details = Strings.abbreviate(sb.toString(), 4000);
      this.details = details;
      return this;
    }

  }

  public static enum Level {
    Info, Warn, Error
  }

  private static class Worker extends Thread {
    boolean stopped = false;
    RemoteLogger logger;

    public Worker(RemoteLogger logger) {
      this.logger = logger;
    }

    @Override
    public void run() {
      while (logger.started && !stopped) {
        try {
          var elements = new ArrayList<BusinessLogEvent>();
          var e0 = logger.queue.take();
          elements.add(e0);
          logger.queue.drainTo(elements);
          var iter = elements.iterator();
          while (iter.hasNext()) {
            var e = iter.next();
            publish(logger.url, e);
          }
        } catch (InterruptedException e) {
          stopped = true;
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }

    public void publish(String url, BusinessLogEvent event) {
      var builder = BusinessLogProto.BusinessLogEvent.newBuilder();
      builder.setOperator(event.operator);
      builder.setOperateAt(event.operateAt.toEpochMilli());
      builder.setSummary(event.summary);
      builder.setDetails(event.details);
      builder.setResources(event.resources);
      builder.setIp(event.ip);
      builder.setAgent(event.agent);
      builder.setEntry(event.entry);
      builder.setLevel(event.level.ordinal() + 1);
      builder.setAppName(event.appName);
      var os = new ByteArrayOutputStream();
      try {
        builder.build().writeTo(os);
        URL upload = new URL(url.replace("{level}", Strings.uncapitalize(event.level.toString())));
        HttpUtils.invoke(upload, os.toByteArray(), "application/x-protobuf");
        System.out.println("log push to "+url.toString());
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  public void setUrl(String url) {
    this.url = url;
  }
}
